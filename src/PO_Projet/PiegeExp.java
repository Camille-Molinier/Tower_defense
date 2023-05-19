package PO_Projet;

public class PiegeExp extends PassiveTower {
    // ---------------------------------Constructeur---------------------------------
    /**
     * Classe des pièges explosifs
     * 
     * @param p la position où est placé le piege
     * @param w le monde dans lequel elle est placée
     */
    public PiegeExp(Position p, World w) {
        super(p, w, "src\\images\\piegeExp.png");
    }

    // ---------------------------------Fonctions------------------------------------
    /**
     * Fonction qui applique les effets
     */
    public void effect(Monster m) {
        if (!m.isAerial() && m.getEffect() != this) {
            if (m.getEffect() != null)
                m.getEffect().uneffect(m);
            m.getW().AOEDamage(m, 100);
            if (!m.isVehicule())
                m.getW().AOEDamage(m, 400);
            m.setEffect(this);;
            setDestroyed(true);
        }

    }
    // -------------------------------------------------------------------------------
    /**
     * Fonction qui retire les effets
     */
    public void uneffect(Monster m) {
        m.setEffect(null);
    }
}
