package PO_Projet;

public abstract class Tower {
	// -----------------------------------Attributs----------------------------------
	// Position où la tour est placée
	public Position position;
	// ditance de tir de la tour
	private double range;
	// Vitesse d'attaque
	private int atkSpeed;
	// Temps de recharge courant
	private int reload;
	// Niveau de la tour
	private int level;
	// Cout de l'amélioration
	private int upgradingCost;
	// Le monstre ciblé
	private Monster target;
	// Texture
	private String imagePathBase;
	private String imagePathCanon;
	// L'angle d'affichage
	private double angle;
	// Peut cibler les ennemis aeriens
	private boolean aerialLock;
	// Peut cibler les ennemis terrestes
	private boolean terrestLock;
	// Le monde où est la tour
	private World world;

	// ---------------------------------Constructeur---------------------------------
	public Tower(Position p, World w) {
		this.position = p;
		this.level = 1;
		this.world = w;
		this.angle = 0;
		this.upgradingCost = 100;
		this.reload = 0;
	}

	public void setTowerParameter(int atkSpeed, double range, String imgBase, String imgCanon, boolean aerialLock,
			boolean terrestLock) {
		this.atkSpeed = atkSpeed;
		this.range = range;
		this.imagePathBase = imgBase;
		this.imagePathCanon = imgCanon;
		this.aerialLock = aerialLock;
		this.terrestLock = terrestLock;
	}
	// --------------------------------Getter/Setter----------------------------------
	/**
	 * Fonction de mise a jour des propriétés de la tour
	 */
	public Projectile update() {
		draw();
		return shot();
	}
	// -------------------------------------------------------------------------------
	public void setImagePathBase(String imagePathBase) {
		this.imagePathBase = imagePathBase;
	}
	// -------------------------------------------------------------------------------
	public void setImagePathCanon(String imagePathCanon) {
		this.imagePathCanon = imagePathCanon;
	}
	// -------------------------------------------------------------------------------
	public int getLevel() {
		return level;
	}
	// -------------------------------------------------------------------------------
	public boolean isAerialLock() {
		return aerialLock;
	}
	// -------------------------------------------------------------------------------
	public boolean isTerrestLock() {
		return terrestLock;
	}
	// -------------------------------------------------------------------------------
	public World getW() {
		return world;
	}
	// -------------------------------------------------------------------------------
	public double getAngle() {
		return angle;
	}
	// -------------------------------------------------------------------------------
	public double getRange() {
		return range;
	}
	// -------------------------------------------------------------------------------
	/**
	 * Determine les monstres proches de la tour
	 */
	public Monster monstreProche() {
		// On regarde si dans tout les monstres du monde, il y en a au moins un qui est
		// dans le champs effecif
		for (Monster m : world.getMonsters()) {
			if (Math.sqrt(Math.pow((m.p.x - this.position.x), 2) + Math.pow(m.p.y - this.position.y, 2)) < this.range) {
				if (m.isAerial() && this.aerialLock || !m.isAerial() && this.terrestLock)
					return m;
			}
		}
		return null;
	}

	// -------------------------------------------------------------------------------
	/**
	 * Fonction qui gere le tir de projectiles
	 * 
	 * @param monster la liste de monstre actuellement dans le niveau
	 */
	public Projectile shot() {
		this.target = monstreProche();
		if (target != null && reload <= 0) {
			reload = atkSpeed;
			return projectile(new Vecteur(this.position.x, this.position.y, target.p.x, target.p.y));
		} else {
			if (reload > 0)
				reload--;
			return null;
		}
	}

	// -------------------------------------------------------------------------------
	/**
	 * Affiche la tour sur le plateau de jeu.
	 */
	public void draw() {
		StdDraw.picture(this.position.x, this.position.y, imagePathBase, world.getSquareWidth(),
				world.getSquareHeight());
		if (target != null) {
			Vecteur toTarget = new Vecteur(this.position.x, this.position.y, this.target.p.x, this.target.p.y);
			this.angle = toTarget.angle();
			StdDraw.picture(this.position.x, this.position.y, imagePathCanon, world.getSquareWidth(),
					world.getSquareHeight(), angle);
		} else {
			StdDraw.picture(this.position.x, this.position.y, imagePathCanon, world.getSquareWidth(),
					world.getSquareHeight(), angle);
		}
	}

	// -------------------------------------------------------------------------------
	/**
	 * Fonction qui monte le niveau des tours si le joueur a la quantité d'or
	 * suffisante
	 */
	public void upgrade() {
		if (this.level < 3) {
			this.level++;
			world.gold = world.gold - upgradingCost;
			range *= 1.2;
			atkSpeed -= atkSpeed/4;
			upgradePictures();
		}
	}

	// -------------------------------------------------------------------------------
	/**
	 * Fonction abstraite qui sera instanciée dans les classes filles changer les
	 * images de la tour
	 */
	public abstract void upgradePictures();

	// -------------------------------------------------------------------------------
	/**
	 * Fonction abstraite qui sera instanciée dans les classes filles retourner leur
	 * projectile propre
	 */
	public abstract Projectile projectile(Vecteur deplacement);

	// -------------------------------------------------------------------------------
	
}