package PO_Projet;

import java.util.Set;

public class Terrorist extends Monster {
	// ---------------------------------Constructeur---------------------------------
	/**
	 * Classe du terroriste
	 * 
	 * @param p      la postion de l'entité
	 * @param chemin la liste des chemins du monde
	 * @param base   la position de la base
	 */
	public Terrorist(Position p, Set<Position> chemins, Position base, World w) {
		super(p, chemins, base, w, 0.03, 400, 0.005, 15, false, false, "src\\images\\Terrorist.png", false);
		//setMonsterParamater(0.03, 400, 0.005, 15, false, false, "src\\images\\Terrorist.png", false);
	}
}
