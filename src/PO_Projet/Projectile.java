package PO_Projet;

public abstract class Projectile {
	// -----------------------------------Attributs----------------------------------
	// Position du projectile
	public Position position;
	// Dégats du projectile
	private int dammage;
	// vitesse du projectile
	private double speed;
	// Sens de deplacement
	public Vecteur deplacement;
	// Tour de spawn
	private Tower mother;
	// Texture
	private String imagePath;
	// Peut cibler les ennemis aeriens
	private boolean aerialLock;
	// Peut cibler les ennemis terrestes
	private boolean terrestLock;
	// Crée des dommage de zone
	private boolean areaDamage;

	// ---------------------------------Constructeur---------------------------------
	public Projectile(Tower mother) {
		this.mother = mother;
		this.aerialLock = mother.isAerialLock();
		this.terrestLock = mother.isTerrestLock();
		this.areaDamage = false;
	}

	public void setProjectileParameter(Position p, int dammage, double speed, Vecteur deplacement, String img, boolean areaDamage){
		this.position = p;
        this.dammage = (mother.getLevel() * dammage/2) + dammage/2;
        this.speed = speed;
        this.deplacement = deplacement;
		this.imagePath = img;
		this.areaDamage = areaDamage;
	}
	// -------------------------------Getter/Setter----------------------------------
	/**
	 * Renvoie les dégats infligés par le projectile
	 */
	public int getDammage() {
		return dammage;
	}
	// ------------------------------------------------------------------------------
	/**
	 * Renvoie si le projectile vise les unites aériennes
	 */
	public boolean isAerialLock() {
		return aerialLock;
	}
	// ------------------------------------------------------------------------------
	/**
	 * Renvoie si le projectile vise les unites terriennes
	 */
	public boolean isTerrestLock() {
		return terrestLock;
	}
	// ------------------------------------------------------------------------------
	/**
	 * Renvoie si le projectile fait des dégats de zone
	 */
	public boolean isAreaDamage() {
		return areaDamage;
	}
	// ------------------------------------------------------------------------------
	/**
	 * Renvoie la tour qui a généré le projectile
	 */
	public Tower getMother() {
		return mother;
	}
	
	// ---------------------------------Fonctions------------------------------------
	/**
	 * Fonction qui renvoie la distance par raport à la tour mere
	 */
	public double motherDist() {
		return (Math.sqrt(
				Math.pow((mother.position.x - this.position.x), 2) + Math.pow(mother.position.y - this.position.y, 2)));
	}
	// ------------------------------------------------------------------------------
	/**
	 * Déplace le monstre en fonction de sa vitesse sur l'axe des x et des y et de
	 * sa direction.
	 */
	private void move() {
		position.x += deplacement.x * speed;
		position.y += deplacement.y * speed;
	}
	// ------------------------------------------------------------------------------
	/**
	 * Fonction pour afficher le projectile sur le plateau de jeu.
	 */
	public void draw(){
		StdDraw.picture(position.x, position.y, imagePath, mother.getW().getSquareWidth(), mother.getW().getSquareHeight(), mother.getAngle());
    }
	// ------------------------------------------------------------------------------
	/**
	 * Fonction de mise a jour des propriétés du projectile
	 */
	public void update() {
		move();
		draw();
	}

	
}
