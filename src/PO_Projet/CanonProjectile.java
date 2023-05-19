package PO_Projet;

public class CanonProjectile extends Projectile {
    // ---------------------------------Constructeur---------------------------------
    /**
     * Classe du projectile tiré par un canon
     * 
     * @param position   la position de départ du projectile
     * @param delacement le sens dans lequel le projectile doit aller
     */
    public CanonProjectile(Position position, Vecteur deplacement, Tower mother) {
        super(mother);
        setProjectileParameter(position, 600, 0.025, deplacement, "src\\images\\CanonProjectile.png", false);
    }
}
