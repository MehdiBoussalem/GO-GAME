package jeudego;

/**
 * Représente un méga pion, dérivant de {@link Pion}.
 */
public class MegaPion extends Pion{

	public MegaPion(int x, int y, Joueur joueur) {
		super(x, y, joueur);
	}

	@Override
	public String toString() {
		return super.toString().toUpperCase();
	}
}
