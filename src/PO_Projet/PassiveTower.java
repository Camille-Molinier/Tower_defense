package PO_Projet;

public abstract class PassiveTower {
    // -----------------------------------Attributs----------------------------------
    // Position où la tour est placée
    public Position position;
    // Texture
    private String imagePath;
    // Monde dans lequel la tour est placée
    private World world;
    // Si ce piege a été detruit par son utilisation
    private boolean destroyed;

    // ---------------------------------Constructeur---------------------------------
    /**
     * Classe de Passive Tower
     * 
     * @param p   la position de la tour
     * @param w   le monde dans lequel la tour evolue
     * @param img le chemin vers l'image de la tour
     */
    public PassiveTower(Position p, World w, String img) {
        this.world = w;
        this.position = p;
        destroyed = false;
        imagePath = img;
    }

    // ---------------------------------Getter/Setter---------------------------------
    /**
     * Indique si le piège est détruit
     * @return
     */
    public boolean isDestroyed() {
        return destroyed;
    }
    // -------------------------------------------------------------------------------
    /**
     * modifie l'attribut indiquant la destrucion ou non de la tour
     */
    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    // -----------------------------------Fonctions-----------------------------------
    /**
     * Fonction abstraite qui sera instanciée dans les classes filles pour gérer les
     * placements effets crées par les tours passives
     */
    public abstract void effect(Monster m);
    // -------------------------------------------------------------------------------
    /**
     * Fonction abstraite qui sera instanciée dans les classes filles pour gérer les
     * retraits effets crées par les tours passives
     */
    public abstract void uneffect(Monster m);
    // -------------------------------------------------------------------------------
    /**
     * Affiche la tour passive sur le plateau de jeu.
     */
    public void draw() {
        StdDraw.picture(this.position.x, this.position.y, imagePath, world.getSquareWidth(), world.getSquareHeight());
    }

}
