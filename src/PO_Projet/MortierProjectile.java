package PO_Projet;

public class MortierProjectile extends Projectile {
    // ---------------------------------Constructeur---------------------------------
    /**
     * Classe du projectile tiré par un Mortier
     * 
     * @param position   la position de départ du projectile
     * @param delacement le sens dans lequel le projectile doit aller
     */
    public MortierProjectile(Position position, Vecteur deplacement, Tower mother) {
        super(mother);
        setProjectileParameter(position, 500, 0.0075, deplacement, "src\\images\\MortarProjectile.png", true);
    }

}
