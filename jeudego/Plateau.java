package jeudego;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Représente le plateau du jeu de go.
 */
public class Plateau implements Serializable {
	private int dimension;
	private  Intersection[][] tab;
	private ArrayList<Chaine> chaines;
	private ArrayList<Joueur> joueurs;
	private int nbPions;
	private int nbTours;

	/**
	 * Dernier pion placé sur le plateau
	 */
	private Pion dernierCoup;
	/**
	 * Vrai si le dernier coup est un suicide, première étape d'un KO
	 */
	private boolean dernierCoupSuicide;
	//private static Logger logger = LoggerUtility.getLogger(Plateau.class, "text");

	public Plateau(int dimension, Joueur j1, Joueur j2, Joueur j3) {
		this.dimension = dimension;
		this.tab = new Intersection[dimension][dimension];
		for (int i=0; i<dimension; i++) {
			for (int j=0; j<dimension; j++) {
				tab[i][j] = new Intersection(i, j);
			}
		}
		chaines = new ArrayList<>();
		joueurs = new ArrayList<>();
		joueurs.add(j1);
		joueurs.add(j2);
		joueurs.add(j3);
		nbPions = 0;
		nbTours = 0;
		dernierCoupSuicide = false;
	}

	/**
	 * Permet de placer un pion sur le plateau selon la conformité des règles.
	 * @param x La coordonnée en abscisse du pion que l'on veut placer
	 * @param y La coordonnée en ordonnée du pion que l'on veut placer
	 * @param joueur Le joueur qui veut placer ce pion
	 * @return Vrai si le pion a été posé sur le plateau, faux sinon
	 */
	public boolean placer(int x, int y, Joueur joueur) {
		boolean result = false;
		if (placementAppartenance(x, y, joueur)) {
			if (tab[x][y].getPion() == null) {
				if (placementLibertes(x, y, joueur)) {
					//logger.info(joueur.getNom() + " place un pion en x=" + x + ",y=" + y + ".");
					dernierCoupSuicide = false;
					Pion pion = new Pion(x, y, joueur);
					verifChaines(pion);
					tab[x][y].ajouterPion(pion);
					actualiserLibertes();
					dernierCoup = pion;
					result = true;
				} else if (placementSuicide(x, y, joueur)) {
					if (dernierCoupSuicide) {
						if (!(dernierCoup.getX() == x - 1 && dernierCoup.getY() == y) && !(dernierCoup.getX() == x + 1 && dernierCoup.getY() == y) && !(dernierCoup.getX() == x && dernierCoup.getY() == y - 1) && !(dernierCoup.getX() == x && dernierCoup.getY() == y + 1)) {
							//logger.info(joueur.getNom() + " place un pion par suicide en x=" + x + ",y=" + y + ".");
							result = true;
							Pion pion = new Pion(x, y, joueur);
							tab[x][y].ajouterPion(pion);
							if (x > 0) {
								Chaine chaineTemp = chainePion(intersectionGauche(x, y).getPion());
								if (chaineTemp.getLibertes() == 1) {
									capture(chaineTemp);
								}
							}
							if (x < dimension - 1) {
								Chaine chaineTemp = chainePion(intersectionDroite(x, y).getPion());
								if (chaineTemp.getLibertes() == 1) {
									capture(chaineTemp);
								}
							}
							if (y > 0) {
								Chaine chaineTemp = chainePion(intersectionInferieure(x, y).getPion());
								if (chaineTemp.getLibertes() == 1) {
									capture(chaineTemp);
								}
							}
							if (y < dimension - 1) {
								Chaine chaineTemp = chainePion(intersectionSuperieure(x, y).getPion());
								if (chaineTemp.getLibertes() == 1) {
									capture(chaineTemp);
								}
							}
							verifChaines(pion);
							actualiserLibertes();
							dernierCoup = pion;
						} else {
							//logger.info(joueur.getNom() + ": placement d'un pion impossible en situation de KO.");
						}
					} else {
						result = true;
						Pion pion = new Pion(x, y, joueur);
						tab[x][y].ajouterPion(pion);
						if (x > 0) {
							Chaine chaineTemp = chainePion(intersectionGauche(x, y).getPion());
							if (chaineTemp.getLibertes() == 1) {
								capture(chaineTemp);
							}
						}
						if (x < dimension - 1) {
							Chaine chaineTemp = chainePion(intersectionDroite(x, y).getPion());
							if (chaineTemp.getLibertes() == 1) {
								capture(chaineTemp);
							}
						}
						if (y > 0) {
							Chaine chaineTemp = chainePion(intersectionInferieure(x, y).getPion());
							if (chaineTemp.getLibertes() == 1) {
								capture(chaineTemp);
							}
						}
						if (y < dimension - 1) {
							Chaine chaineTemp = chainePion(intersectionSuperieure(x, y).getPion());
							if (chaineTemp.getLibertes() == 1) {
								capture(chaineTemp);
							}
						}
						verifChaines(pion);
						actualiserLibertes();
						dernierCoup = pion;
						dernierCoupSuicide = true;
					}
				}
			}
		} else {
			//logger.info(joueur.getNom() + ": placement d'un pion impossible dans le territoire d'un méga pion ennemi");
			result = false;
		}
		if (result) {
			nbPions++;
			nbTours++;
		}
		return result;
	}
	public boolean peutplacer(int x, int y, Joueur joueur) {
		boolean result = false;
		if (placementAppartenance(x, y, joueur)) {
			if (tab[x][y].getPion() == null) {
				if (placementLibertes(x, y, joueur)) {
					//logger.info(joueur.getNom() + " place un pion en x=" + x + ",y=" + y + ".");
					dernierCoupSuicide = false;
					Pion pion = new Pion(x, y, joueur);
					dernierCoup = pion;
					result = true;
				}
			}
		} else {
			//logger.info(joueur.getNom() + ": placement d'un pion impossible dans le territoire d'un méga pion ennemi");
			result = false;
		}
		return result;
	}

	/**
	 * Permet de placer un méga pion et provoque ainsi la suppression des pions ennemis adjacents sur le plateau et un
	 * gain de points pour le joueur.
	 * @param x La coordonnée en abscisse du méga pion que l'on veut placer
	 * @param y La coordonnée en ordonnée du méga pion que l'on veut placer
	 * @param joueur Le joueur qui veut placer ce méga pion
	 * @return Vrai si le méga pion a été posé sur le plateau, faux sinon
	 */

	/*
		- un méga pion ne doit pas être dans le territoire d'un autre méga pion
		- les méga pions peuvent partager le même territoire
				-- -- --
				MR XX MN
				-- MB --
		- les trois méga pions partagent leur territoire en XX
	 */
	public boolean placerMegaPion(int x, int y, Joueur joueur) {
		boolean result = false;
		if (joueur.getNbMegaPions() > 0) {
			if (placementAppartenance(x, y, joueur)) {
				int gain = 0;
				if (x > 0) {
					if (intersectionGauche(x, y).getPion() == null) {
						intersectionGauche(x, y).setAppartient(true);
					} else {
						Pion pionGauche = intersectionGauche(x, y).getPion();
						if (!pionGauche.getJoueur().equals(joueur)) {
							intersectionGauche(x, y).supprimerPion();
							intersectionGauche(x, y).setAppartient(true);
							Chaine chaine = chainePion(pionGauche);
							chaine.supprimerPion(pionGauche);
							gain++;
							if (chaine.getPions().isEmpty()) {
								chaines.remove(chaine);
							}
						}
					}
				}
				if (x < dimension - 1) {
					if (intersectionDroite(x, y).getPion() == null) {
						intersectionDroite(x, y).setAppartient(true);
					} else {
						Pion pionDroit = intersectionDroite(x, y).getPion();
						if (!pionDroit.getJoueur().equals(joueur)) {
							intersectionDroite(x, y).supprimerPion();
							intersectionDroite(x, y).setAppartient(true);
							Chaine chaine = chainePion(pionDroit);
							chaine.supprimerPion(pionDroit);
							gain++;
							if (chaine.getPions().isEmpty()) {
								chaines.remove(chaine);
							}
						}
					}
				}
				if (y > 0) {
					if (intersectionInferieure(x, y).getPion() == null) {
						intersectionInferieure(x, y).setAppartient(true);
					} else {
						Pion pionInferieur = intersectionInferieure(x, y).getPion();
						if (!pionInferieur.getJoueur().equals(joueur)) {
							intersectionInferieure(x, y).supprimerPion();
							intersectionInferieure(x, y).setAppartient(true);
							Chaine chaine = chainePion(pionInferieur);
							chaine.supprimerPion(pionInferieur);
							gain++;
							if (chaine.getPions().isEmpty()) {
								chaines.remove(chaine);
							}
						}
					}
				}
				if (y < dimension - 1) {
					if (intersectionSuperieure(x, y).getPion() == null) {
						intersectionSuperieure(x, y).setAppartient(true);
					} else {
						Pion pionSuperieur = intersectionSuperieure(x, y).getPion();
						if (!pionSuperieur.getJoueur().equals(joueur)) {
							intersectionSuperieure(x, y).supprimerPion();
							intersectionSuperieure(x, y).setAppartient(true);
							Chaine chaine = chainePion(pionSuperieur);
							chaine.supprimerPion(pionSuperieur);
							gain++;
							if (chaine.getPions().isEmpty()) {
								chaines.remove(chaine);
							}
						}
					}
				}
				result = true;
				Pion megaPion = new MegaPion(x, y, joueur); // TEST MEGA
				tab[x][y].ajouterPion(megaPion);
				verifChaines(megaPion);
				actualiserLibertes();
				dernierCoup = megaPion;
				dernierCoupSuicide = false;
				nbPions++;
				nbTours++;
				joueur.setNbMegaPions(joueur.getNbMegaPions() - 1);
				if (gain > 0) {
					joueur.setScore(joueur.getScore() + gain);
					System.out.println(joueur.getNom() + " gagne " + gain + " points grâce à son mégapion");
				}
			}
		}
		return result;
	}
	public int getContenue(){
		int cpt=0;
		for(int i=0;i< tab.length;i++){
			for(int j=0;j< tab.length;j++){
				if(getTab()[i][j] != null){
					cpt++;
				}
			}
		}
		return cpt;
	}

	/**
	 * Vérifie pour le placement d'un pion sans liberté que celui-ci est lié à une chaîne qui possède au moins 1 liberté.
	 * @param x La coordonnée en abscisse du pion que l'on veut placer
	 * @param y La coordonnée en ordonnée du pion que l'on veut placer
	 * @param joueur Le joueur qui veut placer ce pion
	 * @return Vrai si le pion est lié à une autre chaîne qui reste libre, faux sinon
	 */
	public boolean placementLibertes(int x, int y, Joueur joueur) {
		boolean result = false;
		if (x > 0) {
			if (tab[x-1][y].getPion() == null) {
				result = true;
			} else if (tab[x-1][y].getPion().getJoueur().equals(joueur) && chainePion(tab[x-1][y].getPion()).getLibertes() > 1) {
				result = true;
			}
		}
		if (x < dimension-1) {
			if (tab[x+1][y].getPion() == null) {
				result = true;
			} else if (tab[x+1][y].getPion().getJoueur().equals(joueur) && chainePion(tab[x+1][y].getPion()).getLibertes() > 1) {
				result = true;
			}
		}
		if (y > 0) {
			if (tab[x][y-1].getPion() == null) {
				result = true;
			} else if (tab[x][y-1].getPion().getJoueur().equals(joueur) && chainePion(tab[x][y-1].getPion()).getLibertes() > 1) {
				result = true;
			}
		}
		if (y < dimension-1) {
			if (tab[x][y+1].getPion() == null) {
				result = true;
			} else if (tab[x][y+1].getPion().getJoueur().equals(joueur) && chainePion(tab[x][y+1].getPion()).getLibertes() > 1) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * Vérifie si le pion que l'on veut placer est dans le territoire d'un méga pion ennemi.
	 * @param x La coordonnée du pion en abscisse que l'on veut placer
	 * @param y La coordonnée du pion en ordonnée que l'on veut placer
	 * @param joueur Le joueur qui veut placer ce pion
	 * @return Vrai si le pion n'est pas dans le territoire d'un méga pion ennemi, ou si il est dans le territoire du
	 * sien, faux sinon
	 */
	public boolean placementAppartenance(int x, int y, Joueur joueur) {
		boolean result = true;
		if (tab[x][y].isAppartient()) { // donc présence d'un méga pion sur l'un des 4 côtés
			if (x > 0 && tab[x-1][y].getPion() != null) {
				if (tab[x-1][y].getPion().getClass().getSimpleName().equals("MegaPion") && !tab[x-1][y].getPion().getJoueur().equals(joueur)) {
					// Si il y a un méga pion à gauche et qu'il n'appartient pas au joueur alors placement impossible
					result = false;
				}
			}
			if (x < dimension-1 && tab[x+1][y].getPion() != null) {
				if (tab[x+1][y].getPion().getClass().getSimpleName().equals("MegaPion") && !tab[x+1][y].getPion().getJoueur().equals(joueur)) {
					result = false;
				}
			}
			if (y > 0 && tab[x][y-1].getPion() != null) {
				if (tab[x][y-1].getPion().getClass().getSimpleName().equals("MegaPion") && !tab[x][y-1].getPion().getJoueur().equals(joueur)) {
					result = false;
				}
			}
			if (y < dimension-1 && tab[x][y+1].getPion() != null) {
				if (tab[x][y+1].getPion().getClass().getSimpleName().equals("MegaPion") && !tab[x][y+1].getPion().getJoueur().equals(joueur)) {
					result = false;
				}
			}
		}
		if (!result) {
			System.out.println("Placement impossible, cette intersection appartient à un méga pion ennemi !");
		}
		return result;
	}

	/**
	 * Vérifie si le placement d'un pion d'un joueur à ces coordonnées provoque un suicide.
	 * @param x La coordonnée du pion en abscisse que l'on veut placer
	 * @param y La coordonnée du pion en ordonnée que l'on veut placer
	 * @param joueur Le joueur qui veut placer ce pion
	 * @return Vrai si c'est une situation de suicide, faux sinon
	 */
	public boolean placementSuicide(int x, int y, Joueur joueur) {
		boolean result = false;
		if (x > 0) {
			if (chainePion((tab[x-1][y].getPion())).getLibertes() == 1) {
				result = true;
			}
		}
		if (x < dimension-1) {
			if (chainePion((tab[x+1][y].getPion())).getLibertes() == 1) {
				result = true;
			}
		}
		if (y > 0) {
			if (chainePion((tab[1][y-1].getPion())).getLibertes() == 1) {

				result = true;
			}
		}
		if (y < dimension-1) {
			if (chainePion((tab[1][y+1].getPion())).getLibertes() == 1) {
				result = true;
			}
		}
		dernierCoupSuicide = true;
		return result;
	}

	/**
	 * Vérifie si le pion qui vient d'être placé sur le plateau est lié à une ou plusieurs chaînes déjà existantes.
	 * @param nouveauPion Le pion qui vient d'être placé sur le plateau
	 */
	public void verifChaines(Pion nouveauPion) {
		ArrayList<Chaine> temp = new ArrayList<>();
		for (Chaine chaine : chaines) {
			if (nouveauPion.getJoueur().equals(chaine.getPions().get(0).getJoueur())) {
				for (Pion pion : chaine.getPions()) {
					if (estAdjacent(pion, nouveauPion) && !temp.contains(chaine)) {
						temp.add(chaine);
					}
				}
			}
		}
		fusion(temp, nouveauPion);
	}

	/**
	 * Fusionne les chaînes adjacentes sur le plateau dans une nouvelle {@link Chaine}.
	 * @param temp La liste des chaînes qui sont adjacentes au nouveau pion placé sur le plateau
	 * @param nouveauPion Le pion qui vient d'être placé sur le plateau
	 */
	private void fusion(ArrayList<Chaine> temp, Pion nouveauPion) {
		Chaine c = new Chaine(nouveauPion);
		for (Chaine chaine : temp) {
			c.fusionChaine(chaine);
			chaines.remove(chaine);
		}
		temp.clear();
		chaines.add(c);
	}

	/**
	 * Actualise les libertés de l'ensemble des chaînes du plateau. Déclenche ainsi une capture si une chaîne n'a plus
	 * de liberté.
	 */
	public void actualiserLibertes() {
		ArrayList<Intersection> intersections = new ArrayList<>();
		ArrayList<Chaine> chainesASupprimer = new ArrayList<>();
		for (Chaine chaine : chaines) {
			for (Pion pion : chaine.getPions()) {
				if (pion.getX() > 0 && tab[pion.getX()-1][pion.getY()].getPion() == null) {
					if (!intersections.contains(tab[pion.getX()-1][pion.getY()])) {
						intersections.add(tab[pion.getX()-1][pion.getY()]);
					}
				}
				if (pion.getX() < dimension-1 && tab[pion.getX()+1][pion.getY()].getPion() == null) {
					if (!intersections.contains(tab[pion.getX()+1][pion.getY()])) {
						intersections.add(tab[pion.getX()+1][pion.getY()]);
					}
				}
				if (pion.getY() > 0 && tab[pion.getX()][pion.getY()-1].getPion() == null) {
					if (!intersections.contains(tab[pion.getX()][pion.getY()-1])) {
						intersections.add(tab[pion.getX()][pion.getY()-1]);
					}
				}
				if (pion.getY() < dimension-1 && tab[pion.getX()][pion.getY()+1].getPion() == null) {
					if (!intersections.contains(tab[pion.getX()][pion.getY()+1])) {
						intersections.add(tab[pion.getX()][pion.getY()+1]);
					}
				}
			}
			chaine.setLibertes(intersections.size());
			if (intersections.size() == 0) {
				chainesASupprimer.add(chaine);
			}
			intersections.clear();
		}
		nbPions -= chainesASupprimer.size();
		while (!chainesASupprimer.isEmpty()) {
			capture(chainesASupprimer.get(0));
			chainesASupprimer.remove(0);
		}
	}

	/**
	 * Supprime une chaîne capturée du plateau et attribue des points de capture au joueur qui a le plus de pions autour
	 * de cette chaîne.
	 * @param chaineCapturee La chaîne capturée, qui n'a plus de libertés
	 */
	public void capture(Chaine chaineCapturee) {
		Joueur joueurCapture = chaineCapturee.getPions().get(0).getJoueur();
		ArrayList<Pion> pionsAdjacents = new ArrayList<>();
		HashMap<Joueur, Integer> compteurPoints = new HashMap<>();

		//compteurPoints.put(joueurs.get(0), 0);
		//compteurPoints.put(joueurs.get(1), 0);  |
		//compteurPoints.put(joueurs.get(2), 0);  V

		for (Joueur joueur : joueurs) {
			compteurPoints.put(joueur, 0);
		}

		for (Pion pion : chaineCapturee.getPions()) {
			if (pion.getX() > 0) {
				Pion pionGauche = intersectionGauche(pion.getX(), pion.getY()).getPion();
				if (!pionsAdjacents.contains(pionGauche) && !pionGauche.getJoueur().equals(joueurCapture)) {
					pionsAdjacents.add(pionGauche);
					compteurPoints.put(pionGauche.getJoueur(), compteurPoints.get(pionGauche.getJoueur())+1);
				}
			}
			if (pion.getX() < dimension-1) {
				Pion pionDroit = intersectionDroite(pion.getX(), pion.getY()).getPion();
				if (!pionsAdjacents.contains(pionDroit) && !pionDroit.getJoueur().equals(joueurCapture)) {
					pionsAdjacents.add(pionDroit);
					compteurPoints.put(pionDroit.getJoueur(), compteurPoints.get(pionDroit.getJoueur())+1);
				}
			}
			if (pion.getY() > 0) {
				Pion pionInferieur = intersectionInferieure(pion.getX(), pion.getY()).getPion();
				if (!pionsAdjacents.contains(pionInferieur) && !pionInferieur.getJoueur().equals(joueurCapture)) {
					pionsAdjacents.add(pionInferieur);
					compteurPoints.put(pionInferieur.getJoueur(), compteurPoints.get(pionInferieur.getJoueur())+1);
				}
			}
			if (pion.getY() < dimension-1) {
				Pion pionSuperieur = intersectionSuperieure(pion.getX(), pion.getY()).getPion();
				if (!pionsAdjacents.contains(pionSuperieur) && !pionSuperieur.getJoueur().equals(joueurCapture)) {
					pionsAdjacents.add(pionSuperieur);
					compteurPoints.put(pionSuperieur.getJoueur(), compteurPoints.get(pionSuperieur.getJoueur())+1);
				}
			}
		}

		// Supression de la chaine du plateau
		for (Pion pion : chaineCapturee.getPions()) {
			tab[pion.getX()][pion.getY()] = new Intersection(pion.getX(), pion.getY());
		}
		// Supression de la chaine de la liste des chaines
		chaines.remove(chaineCapturee);

		compteurPoints.remove(joueurCapture);
		int max = -1;
		Joueur gagnant = null;
		ArrayList<Joueur> gagnants = new ArrayList<>();
		for (Map.Entry<Joueur, Integer> entry : compteurPoints.entrySet()) {
			if (entry.getValue() > max) {
				gagnant = entry.getKey();
				max = entry.getValue();
				gagnants.clear();
				gagnants.add(gagnant);
			} else if (!entry.getKey().equals(gagnant) && entry.getValue() == max) {
				gagnants.add(entry.getKey());
			}
		}

		for (Joueur joueur : gagnants) {
			System.out.println(joueur.getNom() + " gagne " + chaineCapturee.getPions().size() / gagnants.size() + " points de capture !");
			joueur.setScore(joueur.getScore() + (chaineCapturee.getPions().size()) / gagnants.size());
		}
		actualiserLibertes();
	}

	/**
	 * À la fin de la partie, calcule les territoires que les joueurs ont formés.
	 */
	public void verifTerritoires() {
		ArrayList<Intersection> intersectionsVisitees = new ArrayList<>();
		ArrayList<Intersection> intersections = new ArrayList<>();
		for (int i=0; i<dimension; i++) {
			for (int j=0; j<dimension; j++) {
				if (!intersectionsVisitees.contains(tab[i][j])) {
					parcoursTerritoire(i, j, intersections);
					for (Joueur joueur : joueurs) {
						if (intersections.size() > 0) {
							if (confirmerTerritoire(intersections, joueur)) {
								break;
							}
						}
					}
					intersectionsVisitees.addAll(intersections);
					intersections.clear();
				}
			}
		}
	}

	/**
	 * Parcourt récursivement les intersections adjacentes d'un potentiel territoire à partir de coordonnées initiales.
	 * @param x La coordonnée en abscisse de l'intersection initiale
	 * @param y La coordonnée en ordonnée de l'intersection initiale
	 * @param intersections La liste des intersections adjacentes du potentiel territoire
	 */
	public void parcoursTerritoire(int x, int y, ArrayList<Intersection> intersections) {
		if (tab[x][y].getPion() == null && !intersections.contains(tab[x][y])) {
			intersections.add(tab[x][y]);

			if (x > 0) {
				parcoursTerritoire(x-1, y, intersections);
			}
			if (x < dimension-1) {
				parcoursTerritoire(x+1, y, intersections);
			}
			if (y > 0) {
				parcoursTerritoire(x, y-1, intersections);
			}
			if (y < dimension-1) {
				parcoursTerritoire(x, y+1, intersections);
			}
		}
	}

	/**
	 * Valide la conformité d'un territoire en vérifiant que les pions qui le bordent appartiennent au même joueur.
	 * @param intersections La liste des intersections du potentiel territoire
	 * @param joueur Le joueur pour lequel on vérifie si ce territoire lui appartient
	 * @return Vrai si le territoire est bien formé, faux sinon
	 */
	public boolean confirmerTerritoire(ArrayList<Intersection> intersections, Joueur joueur) {
		boolean result = true;
		for (Intersection intersection : intersections) {
			if (intersection.getX() > 0) {
				if (!intersections.contains(intersectionGauche(intersection.getX(), intersection.getY())) && !intersectionGauche(intersection.getX(), intersection.getY()).getPion().getJoueur().equals(joueur)) {
					// si l'intersection gauche n'est pas contenue dans le territoire detecte et que c'est un pion qui n'appartient pas au joueur
					result = false;
				}
			}
			if (intersection.getX() < dimension-1) {
				if (!intersections.contains(intersectionDroite(intersection.getX(), intersection.getY())) && !intersectionDroite(intersection.getX(), intersection.getY()).getPion().getJoueur().equals(joueur)) {
					result = false;
				}
			}
			if (intersection.getY() > 0) {
				if (!intersections.contains(intersectionInferieure(intersection.getX(), intersection.getY())) && !intersectionInferieure(intersection.getX(), intersection.getY()).getPion().getJoueur().equals(joueur)) {
					result = false;
				}
			}
			if (intersection.getY() < dimension-1) {
				if (!intersections.contains(intersectionSuperieure(intersection.getX(), intersection.getY())) && !intersectionSuperieure(intersection.getX(), intersection.getY()).getPion().getJoueur().equals(joueur)) {
					result = false;
				}
			}
		}
		if (result) {
			for (Intersection intersection : intersections) {
				intersection.setAppartient(true);
			}
			joueur.setScore(joueur.getScore() + intersections.size());
			System.out.println(joueur + " remporte " + intersections.size() + " points de capture de territoire !");
		}
		return result;
	}

	// methode de chaine pour l'IA : à mettre dans l'IA plutôt ?
	/**
	 * Pour une intersection donnée vérifie si elle permet de former une chaîne
	 * @param x La coordonnée en abscisse de l'intersection à tester
	 * @param y La coordonnée en ordonnée de l'intersection à tester
	 * @param joueur Le joueur pour lequel on veut former une chaîne
	 * @return Vrai si cette position créera une nouvelle chaîne, faux sinon
	 */
	public boolean estAdjacentChaine(int x, int y, Joueur joueur) {
		boolean result = false;
		for (Chaine chaine: chaines) {
			if (chaine.getPions().get(0).getJoueur().equals(joueur)) {
				for (Pion pion : chaine.getPions()) {
					if (estAdjacent(pion, new Pion(x, y, joueur))) {
						result = true;
					}
				}
			}
		}
		return result;
	}

	/**
	 * Vérifie si deux pions donnés sont adjacents.
	 * @param pion Le premier pion
	 * @param nouveauPion Le second pion
	 * @return Vrai si les deux pions sont adjacents, faux sinon
	 */
	public boolean estAdjacent(Pion pion, Pion nouveauPion) {
		boolean result = false;
		if ((nouveauPion.getX()-1 == pion.getX()) && (nouveauPion.getY() == pion.getY())) {
			result = true;
		} else if ((nouveauPion.getX()+1 == pion.getX()) && (nouveauPion.getY() == pion.getY())) {
			result = true;
		} else if ((nouveauPion.getX() == pion.getX()) && (nouveauPion.getY()-1 == pion.getY())) {
			result = true;
		} else if ((nouveauPion.getX() == pion.getX()) && (nouveauPion.getY()+1 == pion.getY())) {
			result = true;
		}
		return result;
	}

	/**
	 * Pour un pion donné, renvoie la chaîne à laquelle il appartient.
	 * @param pion Le pion dont on recherche la chaîne
	 * @return La chaîne contenant ce pion
	 */
	public Chaine chainePion(Pion pion) {
		Chaine result = null;
		for (Chaine chaine : chaines) {
			if (chaine.getPions().contains(pion)) {
				result = chaine;
			}
		}
		return result;
	}

	/**
	 * Renvoie l'intersection adjacente gauche par rapport à une intersection donnée.
	 * @param x La coordonnée en abscisse de l'intersection dont on veut visiter l'intersection adjacente gauche
	 * @param y La coordonnée en ordonnée de l'intersection dont on veut visiter l'intersection adjacente gauche
	 * @return L'intersection adjacente gauche
	 */
	public Intersection intersectionGauche(int x, int y) {
		return tab[x-1][y];
	}

	/**
	 * Renvoie l'intersection adjacente droite par rapport à une intersection donnée.
	 * @param x La coordonnée en abscisse de l'intersection dont on veut visiter l'intersection adjacente droite
	 * @param y La coordonnée en ordonnée de l'intersection dont on veut visiter l'intersection adjacente droite
	 * @return L'intersection adjacente droite
	 */
	public Intersection intersectionDroite(int x, int y) {
		return tab[x+1][y];
	}

	/**
	 * Renvoie l'intersection adjacente inférieure par rapport à une intersection donnée.
	 * @param x La coordonnée en abscisse de l'intersection dont on veut visiter l'intersection adjacente inférieure
	 * @param y La coordonnée en ordonnée de l'intersection dont on veut visiter l'intersection adjacente inférieure
	 * @return L'intersection adjacente inférieure
	 */
	public Intersection intersectionInferieure(int x, int y) {
		return tab[x][y-1];
	}

	/**
	 * Renvoie l'intersection adjacente supérieure par rapport à une intersection donnée.
	 * @param x La coordonnée en abscisse de l'intersection dont on veut visiter l'intersection adjacente supérieure
	 * @param y La coordonnée en ordonnée de l'intersection dont on veut visiter l'intersection adjacente supérieure
	 * @return L'intersection adjacente supérieure
	 */
	public Intersection intersectionSuperieure(int x, int y) {
		return tab[x][y+1];
	}

	public int getNbPions() {
		return nbPions;
	}

	public int getNbTours() {
		return nbTours;
	}

	// Méthodes pour tester en console
	// À supprimer
	public String toString() {
		int cpt = 0;
		String result = "";
		for (int i=0; i<dimension; i++) {
			for (int j=0; j<dimension; j++) {
				if (cpt == dimension) {
					result += "\n";
					cpt = 0;
				}
				if (tab[j][i].getPion() == null) {
					result += "--------------------------" + "\t";
					cpt++;
				} else {
					result += tab[j][i].getPion().toString() + "\t";
					cpt++;
				}
			}
		}
		result += "\n\n";
		return result;
	}

	public void affChaines() {
		for (Chaine chaine : chaines) {
			System.out.println(chaine);
		}
	}

	public void affScore() {
		for (Joueur joueur : joueurs) {
			System.out.println("Joueur " + joueur + " (" + joueur.getCouleur() + ") : " + joueur.getScore() + " points");
		}
	}

	public void affAppartenance() {
		int cpt = 0;
		String result = "";
		for (int i=0; i<dimension; i++) {
			for (int j=0; j<dimension; j++) {
				if (cpt == dimension) {
					result += "\n";
					cpt = 0;
				}
				cpt++;
				result += tab[j][i].isAppartient() + "\t";
			}
		}
		System.out.println(result);
	}
	public Joueur getJoueur(int i){
		if (i<=3){
			return joueurs.get(i);
		}
		else{
			return null;
		}
	}
	public Intersection[][] getTab(){
		return tab;
	}

	public int getDimension() {
		return dimension;
	}

	/*public int getDimension() {
		return dimension;
	}
	public void placer(int x, int y, Joueur joueur) {
		if (tab[x][y] == null) {
			int libertes = verifLibertesPion(x, y);
			Pion pion = new Pion(x, y, joueur, libertes);
			if (tab[x][y].getPion() == null) {
				//int libertes = calculLibertesPion(x, y);
				Pion pion = new Pion(x, y, joueur);
				verifChaines(pion);
				verifCapture();

				tab[x][y] = pion;
				tab[x][y].ajouterPion(pion);
				actualiserLibertes();
			}
		}*/
}
