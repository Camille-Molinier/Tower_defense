package PO_Projet;

public class Barbele extends PassiveTower {
    // -----------------------------------Attributs----------------------------------
    private double effectRate = 0.65;

    // ---------------------------------Constructeur---------------------------------
    /**
     * Classe des barbelés
     * 
     * @param p la position où est placé le barbelé
     * @param w le monde dans lequel il est placé
     */
    public Barbele(Position p, World w) {
        super(p, w, "src\\images\\barbeles2.png");
    }

 // ----------------------------------Fonctions-------------------------------------
    /**
     * Fonction qui applique les effets. Dans le cas du barbele, le monstre obtient
     * un malus de vitesse
     * 
     * @param m le monstre qui doit recevoir l'effet
     */
    public void effect(Monster m) {
        if (!m.isAerial()) { // On test si le monstre est bien terreste
            if (m.getEffect() != null) // Si le monstre a déja un effet, on l'enleve
                m.getEffect().uneffect(m);
            // Application de l'effet
            m.speed = m.speed * effectRate;
            m.setEffect(this);
        }
    }
    // -------------------------------------------------------------------------------
    /**
     * Fonction qui retire les effets. Dans le cas du barbele, le monstre perd son
     * malus de vitesse
     * 
     * @param m le monstre a qui on doit enlever l'effet
     */
    public void uneffect(Monster m) {
    	//retrait de l'effet
        m.speed = m.speed / effectRate;
        m.setEffect(null);
    }
}
