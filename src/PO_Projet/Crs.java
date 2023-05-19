package PO_Projet;

import java.util.Set;

public class Crs extends Monster {
	// -----------------------------------Attributs----------------------------------
	private Monster bouclier;

	// ---------------------------------Constructeur---------------------------------
	/**
	 * Classe du Crs
	 * 
	 * @param p      la postion de l'entité
	 * @param chemin la liste des chemins du monde
	 * @param base   la position de la base
	 * @param height la hauteur du monstre
	 * @param weight la largeur du monstre
	 */
	public Crs(Position p, Set<Position> chemins, Position base, World w) {
		super(p, chemins, base, w, 0.015, 45000, 0.004, 20, false, false, "src\\images\\Crs.png", false);
		this.bouclier = new Bouclier(new Position(p), chemins, base, w); // Le CRS possede un bouclier avec lequel il
																			// est lié
	}

	// ---------------------------------Fonctions------------------------------------
	/**
	 * Déplace le monstre en fonction de sa vitesse sur l'axe des x et des y. Le CRS
	 * se déplace en coordination avec son bouclier, il faut donc remplacer la
	 * fonction
	 */
	@Override
	public void move() {
		// Mesure sur quel axe le monstre se dirige.
		double dx = nextP.x - p.x;
		double dy = nextP.y - p.y;
		setAngle(Vecteur.angle(dx, dy));
		if (dy + dx != 0) {
			// Mesure la distance à laquelle le monstre à pu se déplacer.
			double ratioX = dx / (Math.abs(dx) + Math.abs(dy));
			double ratioY = dy / (Math.abs(dx) + Math.abs(dy));
			if ((getW().monstersContains(bouclier) || getW().waitingMonstersContains(bouclier)) // Si le bouclier est en
																								// jeu et pas trop
																								// proche du CRS
					&& distanceBouclier() >= getHitbox()) {
				p.x += ratioX * speed;
				p.y += ratioY * speed;
				bouclier.hp = 1000000000;
				if (distanceBouclier() >= 2 * getHitbox()) // Si le bouclier est trop éloigné du CRS
					bouclier.p = new Position(p);
			} else if (!getW().monstersContains(bouclier) && !getW().waitingMonstersContains(bouclier)) { 
				// Si le bouclier n'est pas en jeu
				bouclier.hp = 1000000000;
				bouclier.p = new Position(p);
				bouclier.speed = speed;
				getW().addWaitingMonster(bouclier);
			} else { // Si le bouclier est en jeu mais trop proche du CRS, on baisse la vitesse du
						// CRS
				p.x += ratioX * speed / 3;
				p.y += ratioY * speed / 3;
			}
		}
		if (Math.abs(dx) < getWidth() / 4 && Math.abs(dy) < getHeight() / 4) {
			addStep();
		}
	}
	// ------------------------------------------------------------------------------
	/**
	 * Determine la distance du Crs par rapport à son bouclier
	 */
	private double distanceBouclier() {
		return Math.sqrt(Math.pow(bouclier.p.x - p.x, 2) + Math.pow(bouclier.p.y - p.y, 2));
	}
	// ------------------------------------------------------------------------------
	/**
	 * Revoie quand le monstre a été tué. Si le CRS est tué, on enleve les PV du
	 * bouclier
	 */
	@Override
	public void killed() {
		bouclier.hp = 0;
	}
}
