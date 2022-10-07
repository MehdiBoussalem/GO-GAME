package jeudego;

/**
 * Représente le pion d'un joueur sur le plateau.
 */
public class Pion {
	private int x;
	private int y;
	private Joueur joueur;
	
	public Pion(int x, int y, Joueur joueur) {
		this.x = x;
		this.y = y;
		this.joueur = joueur;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Joueur getJoueur() {
		return joueur;
	}
	
	@Override
	public String toString() {
		return "Pion [x=" + x + ", y=" + y + ", joueur=" + joueur + "]";
	}
}
