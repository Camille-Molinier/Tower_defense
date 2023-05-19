package PO_Projet;

public class DcaProjectile extends Projectile {
    // ---------------------------------Constructeur---------------------------------
    /**
     * Classe du projectile tiré par une Dca
     * 
     * @param position   la position de départ du projectile
     * @param delacement le sens dans lequel le projectile doit aller
     */
    public DcaProjectile(Position position, Vecteur deplacement, Tower mother) {
        super(mother);
        setProjectileParameter(position, 600, 0.025, deplacement, "src\\images\\DcaLance.png", false);
    }
}
