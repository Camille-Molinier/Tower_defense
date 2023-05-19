package PO_Projet;

import java.util.HashSet;
import java.util.Set;

public class SuperCopter extends Monster {
	// -----------------------------------Attributs----------------------------------
	// Chemin vers l'images des pales du SuperCopter
	String imagePathPales;
	// Angle de direction des pales
	int pales;

	// --------------------------------Constructeurs---------------------------------
	/**
	 * Classe du SuperCopter
	 * 
	 * @param p      la postion de l'entité
	 * @param chemin la liste des chemins du monde
	 * @param base   la position de la base
	 * @param w      le monde dans lequel le monstre evolue
	 */
	public SuperCopter(Position p, Set<Position> chemins, Position base, World w) {
		super(p, new HashSet<Position>(), base, w, 0.03, 2000, 0.0075, 20, true, true, "src\\images\\SuperCopter.png", false); 
		//En envoyant un Set vide, on signale que le monstre ignore les chemins
		//setMonsterParamater(0.03, 1600, 0.005, 20, true, true, "src\\images\\SuperCopter.png", false);
		this.imagePathPales = "src\\images\\SuperCopterPales.png";
		this.pales = 0;
		
	}

	// ------------------------------------------------------------------------------
	/**
	 * Affiche un SuperCopter
	 */
	@Override
	public void draw() {
		super.draw();
		StdDraw.picture(p.x, p.y, imagePathPales, getWidth(), getHeight(), pales);
		pales += 60;
	}
}
