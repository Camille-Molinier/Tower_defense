package PO_Projet;

public class SniperTower extends Tower {

    // ---------------------------------Constructeur---------------------------------
    /**
     * Classe de la tour de sniper
     * 
     * @param p la position où on pose la tour de sniper
     * @param w le monde dans lequel la tour est placée
     */
    public SniperTower(Position p, World w) {
        super(p, w);
        setTowerParameter(15, 0.3, "src\\images\\SniperTowerBase.png", "src\\images\\SniperTowerLance.png", true, true);
    }
    
	// ---------------------------------Fonctions------------------------------------
    /**
     * 
     */
    public void upgradePictures() {
        if (getLevel() == 2) {
            setImagePathBase("src\\images\\SniperTowerBaseLv2.png");
            setImagePathCanon("src\\images\\SniperTowerLanceLV2.png");
        }
        else {
            setImagePathBase("src\\images\\SniperTowerBaseLv3.png");
            setImagePathCanon("src\\images\\SniperTowerLanceLV3.png");
        }

    }
    // ----------------------------------------------------------------------------
    public Projectile projectile(Vecteur deplacement) {
        return new SniperTowerProjectile(new Position(position), deplacement, this);
    }

}