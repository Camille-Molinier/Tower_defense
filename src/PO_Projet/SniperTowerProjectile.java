package PO_Projet;

public class SniperTowerProjectile extends Projectile {

    // ---------------------------------Constructeur---------------------------------
    public SniperTowerProjectile(Position position, Vecteur deplacement, Tower mother) {
        super(mother);
        setProjectileParameter(position, 60, 0.02, deplacement, "", false); //L'image importe peu car on Override le draw
    }

    // -------------------------------------------------------------------------------
    @Override
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.line(position.x + deplacement.x * 0.008, position.y + deplacement.y * 0.008,
                position.x - deplacement.x * 0.008, position.y - deplacement.y * 0.008);
    }
}