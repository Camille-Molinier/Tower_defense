package PO_Projet;

import java.util.Set;

public class AttackDrone extends Monster {
	// --------------------------------Constructeurs---------------------------------
	/**
	 * Classe du Drone volant
	 * 
	 * @param p      la postion de l'entité
	 * @param chemin la liste des chemins du monde
	 * @param base   la position de la base
	 * @param w      le monde dans lequel le monstre evolue
	 */
	public AttackDrone(Position p, Set<Position> chemins, Position base, World w) {
		super(p, chemins, base, w, 0.05, 250, 0.01, 20, true, true, "src\\images\\AttackDrone.png", false);
	}
}
