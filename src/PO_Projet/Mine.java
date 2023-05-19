package PO_Projet;

public class Mine extends PassiveTower {
    // ---------------------------------Constructeur---------------------------------
    /**
     * Classe des mines
     * 
     * @param p la position où est placé la mine
     * @param w le monde dans lequel elle est placée
     */
    public Mine(Position p, World w) {
        super(p, w, "src\\images\\mine.png");
    }

 // ----------------------------------Fonctions--------------------------------------
    /**
     * Fonction qui applique les effets
     */
    public void effect(Monster m) {
        if (!m.isAerial() && m.getEffect() != this) { // Si le monstre est terreste
            if (m.getEffect() != null) // On eleve les precedent effets du monstre
                m.getEffect().uneffect(m);
            m.getW().AOEDamage(m, 100); // On applique un dommage de zone
            if (m.isVehicule()) // Si c'est un véhicule
                m.getW().AOEDamage(m, 1500); // On applique des dommages supérieur
            m.setEffect(this);
            setDestroyed(true); // On détruit le piege
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
