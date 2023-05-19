package PO_Projet;

import java.util.Set;

public class Scout extends Monster {

	// ---------------------------------Constructeur---------------------------------
	/**
	 * Classe de l'eclaireur
	 * 
	 * @param p      la postion de l'entité
	 * @param chemin la liste des chemins du monde
	 * @param base   la position de la base
	 * @param height la hauteur du monstre
	 * @param weight la largeur du monstre
	 */
	public Scout(Position p, Set<Position> chemins, Position base, World w) {
		super(p, chemins, base, w, 0.03, 900, 0.005, 100, false, false, "src\\images\\Scout2.png", true);
		//setMonsterParamater(0.03, 900, 0.005, 100, false, false, "src\\images\\Scout.png", true);
	}

}
