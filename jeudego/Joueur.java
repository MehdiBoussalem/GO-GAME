package jeudego;

import java.util.Scanner;

/**
 * Représente le joueur et ses informations dans le jeu ainsi que sa dernière action sur le plateau.
 */
public class Joueur {
	private String nom;
	private String couleur;
	private double score;
	private int nbMegaPions;
	private boolean passer;
	private boolean abandonner;
	private boolean peutJouer;
	private boolean joueMegaPion;

	public Joueur(String nom, String couleur,boolean peutJouer) {
		this.nom = nom;
		this.couleur = couleur;
		switch (couleur) {
			case "orange":
				score = 7.5;
				break;
			case "blanc":
				score = 3.25;
				break;
			default:
				score = 0;
		}
		nbMegaPions = 1;
		passer = false;
		abandonner = false;
		this.peutJouer = peutJouer;
		joueMegaPion = false;
	}

	public String getNom() {
		return nom;
	}

	public String getCouleur() {
		return couleur;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public int getNbMegaPions() {
		return nbMegaPions;
	}

	public void setNbMegaPions(int nbMegaPions) {
		this.nbMegaPions = nbMegaPions;
	}

	public boolean isPasser() {
		return passer;
	}

	public void setPasser(boolean passer) {
		this.passer = passer;
	}

	public boolean isAbandonner() {
		return abandonner;
	}

	public void setAbandonner(boolean abandonner) {
		this.abandonner = abandonner;
	}

	@Override
	public String toString() {
		return nom;
	}

	public boolean getPeutJouer() {
		return peutJouer;
	}

	public void setPeutJouer(boolean b) {
		this.peutJouer = b;
	}

	public void setNom(String nomJoueur) {
		this.nom = nomJoueur;
	}
	public boolean getJoueMegaPion() {
		return joueMegaPion;
	}

	public void setJoueMegapion(boolean b) {
		this.joueMegaPion = b;
	}
}
