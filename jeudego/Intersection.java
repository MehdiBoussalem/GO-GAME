package jeudego;

/**
 * Représente une intersection sur le plateau, un pion peut être posé dessus, ou non.
 */
public class Intersection {
	private int x;
	private int y;
	private Pion pion;
	/**
	 * Appartenance à un territoire d'un joueur ou de son méga pion
	 */
	private boolean appartient;
	
	public Intersection(int x, int y) {
		this.x = x;
		this.y = y;
		appartient = false;
	}

	/**
	 * Ajoute un pion sur l'intersection.
	 * @param pion Le pion à ajouter
	 */
	public void ajouterPion(Pion pion) {
		this.pion = pion;
	}

	public void supprimerPion() {
		pion = null;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Pion getPion() {
		return pion;
	}

	public boolean isAppartient() {
		return appartient;
	}

	public void setAppartient(boolean appartient) {
		this.appartient = appartient;
	}

	@Override
	public String toString() {
		return "\nIntersection [x=" + x + ", y=" + y + ", pion=" + pion + "]";
	}
}
