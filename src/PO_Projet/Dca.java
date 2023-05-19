package PO_Projet;

public class Dca extends Tower {
    // ---------------------------------Constructeur---------------------------------
    /**
     * Classe du canon
     * 
     * @param p la position où on pose la Dca
     * @param w le monde dans lequel la Dca est placée
     */
    public Dca(Position p, World w) {
        super(p, w);
        setTowerParameter(45, 0.2, "src\\images\\DcaBase.png", "src\\images\\DcaLance.png", true, false);
    }
    
    // ---------------------------------Fonctions------------------------------------
    /**
     * Fonction qui change les images de la tour
     */
    public void upgradePictures() {
        if (getLevel() == 2) {
            setImagePathBase("src\\images\\DcaBaseLv2.png");
            setImagePathCanon("src\\images\\DcaLanceLv2.png");
        } else {
            setImagePathBase("src\\images\\DcaBaseLv3.png");
            setImagePathCanon("src\\images\\DcaLanceLV3.png");
        }

    }
    // ----------------------------------------------------------------------------
    /**
     * Fonction qui retourne le projectile propre a la tour
     * 
     * @param deplacement le vecteur de direction du projectile
     */
    public Projectile projectile(Vecteur deplacement) {
        return new DcaProjectile(new Position(position), deplacement, this);
    }
}
