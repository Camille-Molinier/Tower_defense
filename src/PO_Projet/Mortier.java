package PO_Projet;

public class Mortier extends Tower {
	// ---------------------------------Constructeur---------------------------------
	/**
	 * Classe du mortier
	 * 
	 * @param p la position du mortier
	 * @param w le monde dans lequel le mortier est placé
	 */
	public Mortier(Position p, World w) {
		super(p, w);
		setTowerParameter(100, 0.3, "src\\images\\MortarBase.png", "src\\images\\MortarLance.png", false, true);
	}

	// ---------------------------------Fonctions------------------------------------
	/**
	 * Fonction qui change les images de la tour
	 */
	public void upgradePictures() {
		if (getLevel() == 2) {
			setImagePathBase("src\\images\\MortarBaseLv2.png");
			setImagePathCanon("src\\images\\MortarLanceLv2.png");
		} else {
			setImagePathBase("src\\images\\MortarBaseLv3.png");
			setImagePathCanon("src\\images\\MortarLanceLv2.png");
		}

	}
	// ------------------------------------------------------------------------------
	/**
	 * Affiche la tour sur le plateau de jeu.
	 * 
	 * @Override Le mortier ne possede d'image de canon au niveau 3, il faut donc
	 *           ecraser la fonction
	 */
	@Override
	public void draw() {
		if (getLevel() > 2)
			StdDraw.picture(this.position.x, this.position.y, "src\\images\\MortarBaseLv3.png", getW().getSquareWidth(),
					getW().getSquareHeight());
		else
			super.draw();
	}
	// ------------------------------------------------------------------------------
	/**
	 * Fonction qui retourne le projectile propre a la tour
	 * 
	 * @param deplacement le vecteur de direction du projectile
	 */
	public Projectile projectile(Vecteur deplacement) {
		return new MortierProjectile(new Position(position), deplacement, this);
	}

}
