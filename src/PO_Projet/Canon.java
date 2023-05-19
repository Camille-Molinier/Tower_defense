package PO_Projet;

public class Canon extends Tower {
    // ---------------------------------Constructeur---------------------------------
    /**
     * Classe du canon
     * 
     * @param p la position où on pose le canon
     * @param w le monde dans lequel le Canon est placé
     */
    public Canon(Position p, World w) {
        super(p, w);
        setTowerParameter(45, 0.2, "src\\images\\CanonBase.png", "src\\images\\CanonLanceLV1.png", false, true);
    }

    // ---------------------------------Fonctions------------------------------------
    /**
     * Fonction qui change les images de la tour
     */
    public void upgradePictures() {
        if (getLevel() == 2) {
            setImagePathBase("src\\images\\CanonBaseLv2.png");
            setImagePathCanon("src\\images\\CanonLanceLV2.png");
        } else {
            setImagePathBase("src\\images\\CanonBaseLv3.png");
            setImagePathCanon("src\\images\\CanonLanceLV3.png");
        }

    }
    // ----------------------------------------------------------------------------
    /**
     * Fonction qui retourne le projectile propre a la tour
     * 
     * @param deplacement le vecteur de direction du projectile
     */
    public Projectile projectile(Vecteur deplacement) {
        return new CanonProjectile(new Position(position), deplacement, this);
    }
}
