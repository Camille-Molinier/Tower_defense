package PO_Projet;

public class Position {
	// -----------------------------------Attributs----------------------------------
	double x;
	double y;

	// ---------------------------------Constructeur---------------------------------
	/**
	 * Classe qui permet d'avoir la position sur l'axe des x et des y des monstres
	 * et des tours
	 * 
	 * @param x
	 * @param y
	 */
	public Position(double x, double y) {
		this.x = x;
		this.y = y;
	}
	// ------------------------------------------------------------------------------
	/**
	 * Classe qui permet d'avoir la position sur l'axe des x et des y des monstres
	 * et des tours
	 * 
	 * @param p une position
	 */
	public Position(Position p) {
		x = p.x;
		y = p.y;
	}
	
	// ---------------------------------Fonctions------------------------------------
	/**
	 * Methode pour savoir si deux positions sont les mêmes
	 * 
	 * @param p la position a comparer
	 * @return true si p a les mêmes coordonnées que la position courante false
	 *         sinon
	 */
	public boolean equals(Position p) {
		return x == p.x && y == p.y;
	}
	// ------------------------------------------------------------------------------
	/**
	 * Mesure la distance euclidienne entre 2 positions.
	 * 
	 * @param p
	 * @return la distance entre la position courante et p
	 */
	public double dist(Position p) {
		return Math.sqrt(Math.pow(x - p.x, 2) + Math.pow(y - p.y, 2));
	}
	// ------------------------------------------------------------------------------
	/**
	 * Retourne la position du point sur l'axe des x et des y
	 */
	public String toString() {
		return "(" + Double.toString(x) + "," + Double.toString(y) + ")";
	}
}
