package PO_Projet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class Monster {
	// -----------------------------------Attributs----------------------------------
	// Position du monstre à l'instant t
	public Position p;
	// Vitesse du monstre
	// NB : La vitesse maximale d'un monstre que le jeu peut supporter est de 0.020
	public double speed;
	// HP du monstre
	public int hp;
	// Position du monstre à l'instant t+1
	public Position nextP;
	// Taille de la hitbox du monstre
	private double hitbox;
	// Chemin que suivra le monstre (généré par le pathfinding)
	private List<Position> chemin;
	// Etape dans le chemin que le monstre cherche a atteindre
	public int etape;
	// Taille et Largeur des cases du jeu ou le monstre evolue
	private double height;
	private double width;
	// Position de la base ou l'objectif du monstre (peut etre différent de la base)
	private Position objectif;
	// Indique si le monstre peut detruire des cases
	private boolean breach;
	// Indique si le monstre est aerien ou non
	private boolean aerial;
	// Indique si le monstre est un veicule ou non
	private boolean vehicule;
	// Indique si le monstre subit un effet
	private PassiveTower effect;
	// Nombre de piece gagne en tuant ce monstre
	private int reward;
	// L'angle du monstre
	private double angle;
	// Texture
	private String imagePath;
	// World dans lequel evolue le monstre
	private World w;

	// --------------------------------Constructeurs---------------------------------
	/**
	 * Classe du monstre
	 * 
	 * @param p        la position de départ du monstre
	 * @param chemins  ensemble des cases que le monstre peut parcourir
	 * @param base     objectif final du monstre
	 * @param w        monde dans lequel le monstre evolue
	 * @param hitbox   taille de la hitbox du monstre
	 * @param hp       point de vie maximum du monstre
	 * @param speed    vitesse initiale du monstre
	 * @param reward   nombre de piece que le monstre donne à sa mort
	 * @param aerial   si le monstre est aérien
	 * @param vehicule si le monstre est un véhicule
	 * @param img      chemin vers l'image du monstre
	 * @param breach   si le monstre peut créer des chemins
	 */
	public Monster(Position p, Set<Position> chemins, Position base, World w, double hitbox, int hp, double speed,
			int reward, boolean aerial, boolean vehicule, String img, boolean breach) {
		this.p = new Position(p);
		this.etape = 0;
		this.height = w.getSquareHeight();
		this.width = w.getSquareWidth();
		this.objectif = base;
		if (chemins != null && chemins.size() > 0) {
			this.chemin = PathFinding.Algorithme_de_Dijkstra(chemins, p, base, height, width);
			this.nextP = chemin.get(etape);
		} else if (chemins != null) { // Si le Set est vide alors le monstre ignore les chemins
			chemin = new ArrayList<Position>();
			chemin.add(base);
			this.nextP = chemin.get(etape);
		} else {
			this.chemin = null;
			this.nextP = null;
		}
		this.effect = null;
		this.w = w;
		this.hitbox = hitbox;
		this.hp = hp;
		this.speed = speed;
		this.reward = reward;
		this.aerial = aerial;
		this.vehicule = vehicule;
		this.imagePath = img;
		this.breach = breach;
	}
	
	// ------------------------------Getter/Setter---------------------------------
	/**
	 * Renvoie la taille de la hitBox du monstre
	 */
	public double getHitbox() {
		return hitbox;
	}
	// ----------------------------------------------------------------------------
	/**
	 * TODO : ??
	 * @return
	 */
	public boolean isBreach() {
		return breach;
	}
	// ----------------------------------------------------------------------------
	/**
	 * Renvoie si le monstre est aérien
	 * @return true si le monstres est aérien false sinon
	 */
	public boolean isAerial() {
		return aerial;
	}
	// ----------------------------------------------------------------------------
	/**
	 * Renvoie si le monstre est un vehicule
	 * @return true si le monstres est un véhicule false sinon
	 */
	public boolean isVehicule() {
		return vehicule;
	}
	// ----------------------------------------------------------------------------
	/**
	 * Renvoie la quantité de gold gagnée lorsque le monstre est tué
	 */
	public int getReward() {
		return reward;
	}
	// ----------------------------------------------------------------------------
	/**
	 * Renvoie le monde dans lequel évolue le monstre
	 */
	public World getW() {
		return w;
	}
	// ----------------------------------------------------------------------------
	/**
	 * Renvoie la position de l'objectif du monstre
	 */
	public Position getObjectif() {
		return objectif;
	}
	// ----------------------------------------------------------------------------
	/**
	 * Renvoie la largeure du monstre
	 */
	public double getHeight() {
		return height;
	}
	// ----------------------------------------------------------------------------
	/**
	 * Renvoie la hauteur du monstre
	 */

	public double getWidth() {
		return width;
	}
	// ----------------------------------------------------------------------------
	/**
	 * Modife l'angle du monstre
	 * @param angle : la valeure que l'on veux mettre
	 */
	public void setAngle(double angle) {
		this.angle = angle;
	}
	// ----------------------------------------------------------------------------
	/**
	 * Renvoie l'effet subit par le monstre
	 * @return l'effect subit null sinon
	 */
	public PassiveTower getEffect() {
		return effect;
	}
	// ----------------------------------------------------------------------------
	/**
	 * Modifie l'effet subit par le monstre
	 */
	public void setEffect(PassiveTower effect) {
		this.effect = effect;
	}

	// ---------------------------------Fonctions------------------------------------
	/**
	 * Déplace le monstre en fonction de sa vitesse sur l'axe des x et des y
	 */
	public void move() {
		// Mesure sur quel axe le monstre se dirige.
		double dx = nextP.x - p.x;
		double dy = nextP.y - p.y;
		this.angle = Vecteur.angle(dx, dy);
		if (dy + dx != 0) {
			// Mesure la distance à laquelle le monstre à pu se déplacer.
			double ratioX = dx / (Math.abs(dx) + Math.abs(dy));
			double ratioY = dy / (Math.abs(dx) + Math.abs(dy));
			p.x += ratioX * speed;
			p.y += ratioY * speed;
		}
		if (Math.abs(dx) < width / 4 && Math.abs(dy) < height / 4) { // Si le monstre est proche de son objectif
			addStep(); // On change son objectif
		}
	}
	// ----------------------------------------------------------------------------
	/**
	 * Fonction qui change l'objectif du monstre par sa prochaine étape
	 */
	public void addStep() {
		etape++;
		if (etape < chemin.size())
			nextP = chemin.get(etape);
		else
			nextP = objectif;
	}
	// ----------------------------------------------------------------------------
	/**
	 * Affiche le monstre sur le plateau de jeu.
	 */
	public void draw() {
		StdDraw.picture(p.x, p.y, imagePath, width, height, angle);
	}
	// ----------------------------------------------------------------------------
	/**
	 * Fonction de mise a jour du monstre
	 */
	public void update() {
		move();
		draw();
	}
	// ----------------------------------------------------------------------------
	/**
	 * Fonction qui permet de recalculer le pathfinding d'un monstre, utile lors
	 * d'un changement du terrain
	 * 
	 * @param chemin la liste des chemins
	 * @param base   la position de la base
	 */
	public void recalculateObjectif(Set<Position> chemins, Position base) {
		etape = 0;
		this.chemin = PathFinding.Algorithme_de_Dijkstra(chemins, p, base, height, width);
		nextP = chemin.get(etape);
	}
	// ----------------------------------------------------------------------------
	/**
	 * S'execute quand le monstre est mort
	 * 
	 * @Note Fonction vide car elle ne sert que pour certains monstres, les monstres
	 *       en questions feront appel a un Override
	 */
	public void killed() {/*... */}

}
