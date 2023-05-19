package PO_Projet;

import java.util.Set;

public class Bomber extends Monster {
	// ---------------------------------Constructeur---------------------------------
	/**
	 * Classe du Bombardier
	 * 
	 * @param p      la postion de l'entité
	 * @param chemin la liste des chemins du monde
	 * @param weight la largeur du monstre
	 */
	public Bomber(Position p, Set<Position> chemins, Position base, World w) {
		super(p, chemins, base, w, 0.03, 900, 0.005, 20, true, true, "src\\images\\Bomber.png", false);
	}

	// ---------------------------------Fonctions------------------------------------
	/**
	 * Revoie si le monstre a été tué Pour le Bombardier, 3 AttackDrones sont
	 * rajoutés dans la liste des monstres du World
	 */
	@Override
	public void killed() {
		if (this.hp <= 0) {
			for (int i = 0; i < 3; i++) { // On ajoute 3 Drones sur le plateau
				getW().addWaitingMonster(
						new AttackDrone(new Position(this.p), getW().getPath(), getObjectif(), getW()));
			}
		}
	}
}
