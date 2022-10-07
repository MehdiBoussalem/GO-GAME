package jeudego;

import java.util.ArrayList;

/**
 * Cette classe représente une chaîne de pions sur le plateau.
 */
public class Chaine {
	private ArrayList<Pion> pions;
	private int libertes;
	
	public Chaine(Pion pion) {
		this.pions = new ArrayList<Pion>();
		pions.add(pion);
	}

	/**
	 * Fusionne la chaîne actuelle avec une autre chaîne.
	 * @param ancienneChaine La chaîne qui fusionne
	 */
	public void fusionChaine(Chaine ancienneChaine) {
		pions.addAll(ancienneChaine.getPions());
	}

	/**
	 * Retire un pion donné de la chaîne de pions.
	 * @param pion Le pion qui doit être supprimé
	 */
	public void supprimerPion(Pion pion) {
		pions.remove(pion);
	}

	public ArrayList<Pion> getPions() {
		return pions;
	}

	public int getLibertes() {
		return libertes;
	}

	public void setLibertes(int libertes) {
		this.libertes = libertes;
	}

	// À supprimer plus tard
	public String toString() {
		String result = "La chaîne est composée de: ";
		for (Pion pion : pions) {
			result += pion.toString() + "\n\t\t\t   ";
		}
		result += "Libertés de la chaîne: " + libertes;
		result += "\n\t\t\t   Nombre de pions: " + pions.size() + "\n";
		return result;
	}
}
