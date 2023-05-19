package PO_Projet;

public class Vecteur {
    // -----------------------------------Attributs----------------------------------
    public double x;
    public double y;

    // ---------------------------------Constructeur---------------------------------
    /**
     * Classe representant des vecteurs
     * 
     * @param x la coordonnée du vecteur sur l'axe des abscisses
     * @param y la coordonnée du vecteur sur l'axe des ordonnés
     */
    public Vecteur(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // -----------------------------------------------------------------------------
    /**
     * Classe representant des vecteurs normés a partir de deux points
     * 
     * @param x1 la coordonnée du premier point sur l'axe des abscisses
     * @param y1 la coordonnée du premier point sur l'axe des ordonnés
     * @param x2 la coordonnée du deuxième point sur l'axe des abscisses
     * @param y2 la coordonnée du deuxième point sur l'axe des ordonés
     */
    public Vecteur(double x1, double y1, double x2, double y2) {
        this.x = x2 - x1;
        this.y = y2 - y1;
        double norme = Math.sqrt(x * x + y * y);
        this.x = this.x / norme;
        this.y = this.y / norme;
    }

    // -----------------------------------------------------------------------------
    public double angle() {
        double res = 180 + Math.toDegrees(Math.atan2(y, x));
        if (res < 0) {
            res += 360;
        }
        return res;
    }

    public static double angle(double x, double y) {
        double res = 180 + Math.toDegrees(Math.atan2(y, x));
        if (res < 0) {
            res += 360;
        }
        return res;
    }
}
