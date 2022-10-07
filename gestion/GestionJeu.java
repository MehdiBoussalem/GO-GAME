package gestion;

import jeudego.*;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Détermine quand les joueurs peuvent jouer et la fin de la partie.
 */
public class
GestionJeu {
    private Plateau plateau;
    private ArrayList<Joueur> joueurs;
    private boolean stop;
    private int tours;

    public GestionJeu(int dim, Joueur j1, Joueur j2, Joueur j3) {
        plateau = new Plateau(dim, j1, j2, j3);
        joueurs = new ArrayList<>();
        joueurs.add(j1);
        joueurs.add(j2);
        joueurs.add(j3);
        stop = true;
        tours = 0;
    }

    /**
     * Initialise la partie et fait jouer les joueurs tour par tour.
     */
    public void initialiser() {
        stop = false;
        while (!stop) {
            for (Joueur joueur : joueurs) {
                if (!finPartieAbandon() && !finPartiePasse()) {
                    if (!joueur.isAbandonner()) { // sinon on passe automatiquement son tour
                        System.out.println("Tour de " + joueur.getNom() + ". Quelle action voulez vous effectuer ?"); // log
                        Scanner scannerAction = new Scanner(System.in);
                        String action = scannerAction.next();


                        // Remplacé par un clic sur le plateau
                        if (action.equals("p")) {
                            boolean placement = false;
                            do {
                                try {
                                    System.out.println("Saisir les coordonnées en X et Y");
                                    Scanner scannerX = new Scanner(System.in);
                                    Scanner scannerY = new Scanner(System.in);
                                    int x = scannerX.nextInt();
                                    int y = scannerY.nextInt();
                                    placement = plateau.placer(x, y, joueur); // renvoie faux si le placement est impossible
                                    if (!placement) {
                                        System.out.println("Impossible de poser un pion ici");
                                    }
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    System.err.println("Coordonnées hors du plateau");
                                }
                            } while (!placement);
                            System.out.println("Le pion a bien été placé");
                            joueur.setPasser(false);


                        // Remplacé par (bouton + clic ?)
                        } else if (action.equals("mp") && joueur.getNbMegaPions() > 0) { // mp = méga pion
                            boolean placement = false;
                            do {
                                try {
                                    System.out.println("Saisir les coordonnées en X et Y du Méga pion");
                                    Scanner scannerX = new Scanner(System.in);
                                    Scanner scannerY = new Scanner(System.in);
                                    int x = scannerX.nextInt();
                                    int y = scannerY.nextInt();
                                    placement = plateau.placerMegaPion(x, y, joueur);
                                    joueur.setPasser(false);
                                    if (!placement) {
                                        System.out.println("Impossible de poser un méga pion ici");
                                    }
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    System.err.println("Coordonnées hors du plateau");
                                }
                            } while(!placement);
                            System.out.println("Le méga pion a bien été placé");
                            joueur.setPasser(false);


                        // Remplacé par un clic sur le bouton PASSER
                        } else if (action.equals("passer")) {
                            System.out.println("Vous avez passé votre tour");
                            joueur.setPasser(true);


                        // Remplacé par un clic sur le bouton ABANDONNER
                        } else if (action.equals("abandonner")) {
                            System.out.println("Vous avez abandonné la partie");
                            joueur.setAbandonner(true);
                            joueur.setPasser(true);
                        } else {
                            System.out.println("Commande non reconnue");
                        }
                        System.out.println(plateau);
                    }
                } else {
                    stop = true;
                    //break;
                }
            }
        }
        finPartie();
    }


    /**
     * Vérifie si un joueur a abandonné et si il y a assez de joueurs pour continuer la partie.
     * @return Retourne vrai s'il n'y a plus assez de joueurs pour jouer, faux sinon.
     */
    public boolean finPartieAbandon() {
        int abandon = 0;
        int enJeu = 0;
        for (Joueur joueur : joueurs) {
           if (joueur.isAbandonner()) {
                abandon++;
            } else {
               enJeu++;
           }
        }
        if (enJeu > abandon) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Vérifie si tous les joueurs ont successivement passé leur tour.
     * @return Vrai si tous les joueurs ont successivement passé leur tour, faux sinon.
     */
    public boolean finPartiePasse() {
        for (Joueur joueur : joueurs) {
            if (!joueur.isPasser()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Affiche les informations de fin de partie.
     */
    public void finPartie() {
        System.out.println("Partie terminée !");
        System.out.println(plateau);
        plateau.verifTerritoires();
        plateau.affChaines();
        plateau.affScore();
        plateau.affAppartenance();
    }
}
