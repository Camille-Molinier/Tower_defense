package PO_Projet;

import java.util.Set;

public class Bouclier extends Monster {
	// --------------------------------Constructeurs---------------------------------
	/**
	 * Classe du bouclier
	 * 
	 * @param p      la postion de l'entité
	 * @param chemin la liste des chemins du monde
	 * @param weight la largeur du monstre
	 */
	public Bouclier(Position p, Set<Position> chemins, Position base, World w) {
		super(p, chemins, base, w, 0.02, 1000000000, 0.001, 20, false, false, "", false);
	}

	// ---------------------------------Fonctions------------------------------------
	/*
	 * Affiche un Bouclier
	 */
	@Override
	public void draw() {
		// Le bouclier est invisible pour le joueur mais est bien présent sur le terrain
	}

}
