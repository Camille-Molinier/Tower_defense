package PO_Projet;

import java.util.Set;

public class Truck extends Monster {
	// ---------------------------------Constructeur---------------------------------
	/**
	 * Classe du camion de transport
	 * 
	 * @param p      la postion de l'entité
	 * @param chemin la liste des chemins du monde
	 * @param base   la position de la base
	 */
	public Truck(Position p, Set<Position> chemins, Position base, World w) {
		super(p, chemins, base, w, 0.03, 900, 0.005, 20, false, true, "src\\images\\Truck.png", false);
		//setMonsterParamater(0.03, 900, 0.005, 20, false, true, "src\\images\\Truck.png", false);
	}

	// ------------------------------------------------------------------------------
	/**
	 * Revoie si le monstre a été tué Le Camion relache 3 Terrorists a sa mort
	 */
	@Override
	public void killed() {
		if (this.hp <= 0) {
			for (int i = 0; i < 3; i++) {
				getW().addWaitingMonster(new Terrorist(new Position(this.p), getW().getPath(), getObjectif(), getW()));
			}
		}
	}
}
