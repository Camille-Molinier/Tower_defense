package PO_Projet;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import java.util.HashSet;
import java.awt.Font;

public class World {
    // -----------------------------------Attributs----------------------------------
    // l'ensemble des monstres, pour gerer (notamment) l'affichage
    private List<Monster> monsters = new ArrayList<Monster>();
    // l'ensemble des monstres attendant leur spawn
    private List<Monster> waitingMonster = new ArrayList<Monster>();
    // l'ensemble des projectiles sur le plateau de jeu
    private List<Projectile> projectiles = new ArrayList<Projectile>();
    // l'ensemble des case représentant les chemins
    private Set<Position> path;
    // Ensemble des tours construite sur le terrain
    private Set<Tower> tower;
    // Ensemble des tours construite sur le terrain
    private Set<PassiveTower> passiveTower;
    //Liste des musique de jeu
    List<String> musicPath = new ArrayList<String>();
    // Position par laquelle les monstres vont venir
    private Position spawn;
    // Position de la base
    private Position base;

    // Information sur la taille du plateau de jeu
    private int nbSquareX;
    private int nbSquareY;
    private double squareWidth;
    private double squareHeight;

    // Nombre de points de vie du joueur
    private int life = 20;
    // Nombre de gold du joueur
    public int gold = 250;
    // Commande sur laquelle le joueur appuie (sur le clavier)
    private char key;
    // Condition pour terminer la partie
    private boolean end = false;
    // Nombre de tick du monde
    private int tick;
    // Varaible pour faire tourner le radar
    private int angleRadar = 0;
    // Partie démarée ou non
    private boolean started;
    // Partie en mode tutoriel
    //private boolean tutoriel;
    // Numero de la vague d emonstre actuelle
    private int wave;
    // Cheat Activé
    private boolean cheat;
    private boolean quitteMenu;
    private boolean quitteOptions;
    private boolean quitteAide;
    private int texturePack = 1;
    //Musiques
    Music menu = new Music("src\\musiques\\under construction.wav");
    Music game = new Music();
    Music tuto = new Music("src\\musiques\\tuto.wav");
    Music roll = new Music("src\\musiques\\nya.wav");
    

    // ---------------------------------Constructeur---------------------------------
    /**
     * Initialisation du monde en fonction de la largeur, la hauteur et le nombre de
     * cases données
     * 
     * @param width
     * @param height
     * @param nbSquareX
     * @param nbSquareY
     * @param startSquareX
     * @param startSquareY
     */
    public World(int width, int height, int nbSquareX, int nbSquareY) {
        this.nbSquareX = nbSquareX;
        this.nbSquareY = nbSquareY;
        squareWidth = (double) 1 / nbSquareX;
        squareHeight = (double) 1 / nbSquareY;
        tower = new HashSet<Tower>();
        passiveTower = new HashSet<PassiveTower>();
        tick = 0;
        started = false;
        wave = 1;
        StdDraw.setCanvasSize(width, height);
        StdDraw.enableDoubleBuffering();
        Font font = new Font("Arial", Font.BOLD, 17);
        StdDraw.setFont(font);
        cheat = false;
        musicPath.add("src\\musiques\\Attack on Arrakis.wav"); musicPath.add("src\\musiques\\fight for power.wav");
        musicPath.add("src\\musiques\\fremen.wav"); musicPath.add("src\\musiques\\harkonnen battle.wav");
        musicPath.add("src\\musiques\\Rise of Harkonnen.wav"); musicPath.add("src\\musiques\\score.wav");
        musicPath.add("src\\musiques\\under construction.wav"); musicPath.add("src\\musiques\\plotting.wav");
    }
    // -------------------------------Getters/Setters--------------------------------
    /**
     * Renvoie la longueure normalisée
     */
    public double getSquareWidth() {
        return squareWidth;
    }
    // ------------------------------------------------------------------------------
    /**
     * Renvoie la largeure normalisée
     */
    public double getSquareHeight() {
        return squareHeight;
    }
    // ------------------------------------------------------------------------------
    /**
     * Ajoute un monstre dans la liste d'attente
     * @param m un monstre
     */
    public void addWaitingMonster(Monster m) {
        this.waitingMonster.add(0, m);
    }
    // ------------------------------------------------------------------------------
    /**
     * Renvoie le chemin
     */
    public Set<Position> getPath() {
        return path;
    }
    // ------------------------------------------------------------------------------
    /**
     * Renvois si un monstre appartien à la liste des monstres en jeu
     * @param m un monstre
     * @return true si il est dans la liste false sinon
     */
    public boolean monstersContains(Monster m) {
        return monsters.contains(m);
    }
    // ------------------------------------------------------------------------------
    /**
     * Renvois si un monstre appartien à la liste des monstres en attente
     * @param m un monstre
     * @return true si il est dans la liste false sinon
     */
    public boolean waitingMonstersContains(Monster m) {
        return waitingMonster.contains(m);
    }
    // ------------------------------------------------------------------------------
    /**
     * Revoie la liste des monstres
     */
    public List<Monster> getMonsters() {
        return monsters;
    }
    // -----------------------------Fonctions_de_dessin------------------------------
    /**
     * Définit le décors du plateau de jeu.
     * @param menu true si on est sur le menu false sinon
     */
    public void drawBackground(boolean menu) {
    	//On charge les textures
    	String background = "";
    	if(texturePack==1) {
    		background = "src\\images\\background.jpg";
    		StdDraw.setPenColor(51,120,13);
    	}
    	else {
    		StdDraw.setPenColor(232, 196, 126);
    		background = "src\\images\\background2.png";
    	}

        //On remplis le fond pour eviter contrer un probleme d'affichage avec la texture du sol
        
        for (int i = 0; i < nbSquareX; i++)
            for (int j = 0; j < nbSquareY; j++)
                StdDraw.filledRectangle(i * squareWidth + squareWidth / 2, j * squareHeight + squareHeight / 2,
                        squareWidth, squareHeight);

        //On applique la texture en fonction de sis c'est une case normale ou si c'est la case "info" (en haut a droite)
        // et que l'on est pas sur le menu
        for (int i = 0; i < nbSquareX; i++) {
            for (int j = 0; j < nbSquareY; j++) {
            	StdDraw.picture(i * squareWidth + squareWidth / 2, j * squareHeight + squareHeight / 2, background,
            			squareWidth, squareHeight);  
            }
        }
        
        //Si on est sur le menu, on affiche le logo
        if(menu) {
        	String logo = "src\\images\\LOGO.png";
        	StdDraw.picture(0.5,0.9, logo,	0.75, 0.75); 
        }
    }

    // ------------------------------------------------------------------------------
    /**
     * Initialise le chemin sur la position du point de départ des monstres. Cette
     * fonction permet d'afficher une route qui sera différente du décors.
     */
    public void drawPath() {
        //On place la spawn et la base
        Position p = new Position(spawn);
        Position b = new Position(base);
        
        //On charge les textures
        String spawn; String pathImage; String base; 
        String radar = "src\\images\\baseRadar.png";
        
        if(texturePack==1) {
        	spawn = "src\\images\\spawn.png";
            pathImage = "src\\images\\path.png";
            base = "src\\images\\base.png";
            StdDraw.setPenColor(110,110,109);
        }
        else {
        	spawn = "src\\images\\spawn2.png";
            pathImage = "src\\images\\path2.png";
            base = "src\\images\\base2.png";
            StdDraw.setPenColor(186,85,0);
        }

        //Puis on dessine les elements
        StdDraw.picture(p.x, p.y, spawn, squareWidth, squareHeight);

        for (Position i : path) {
        	StdDraw.filledRectangle(i.x, i.y, squareWidth/2, squareHeight/2);
            StdDraw.picture(i.x, i.y, pathImage, squareWidth, squareHeight);
        }
        StdDraw.picture(b.x, b.y, base, squareWidth, squareHeight);
        if(texturePack==2) {
        	StdDraw.picture(b.x+0.02, b.y, radar, squareWidth, squareHeight, angleRadar);
        	angleRadar +=5;
        	if(angleRadar>360) {
        		angleRadar = 0;
        	}
        }
    }

    // ------------------------------------------------------------------------------
    /**
     * Affiche certaines informations sur l'écran telles que les points de vie du
     * joueur ou son or
     */
    public void drawInfos() {
    	 String info = "src\\images\\info2.png";
    	 StdDraw.picture(10 * squareWidth + squareWidth / 2, 10 * squareHeight + squareHeight / 2, info,
                 squareWidth, squareHeight);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(0.95, 0.983, life + "");
        StdDraw.text(0.95, 0.953, gold + "");
        StdDraw.text(0.95, 0.923, wave + "");
        
        StdDraw.text(0.075, 0.983, "A : Tour de Sniper");
        StdDraw.text(0.049, 0.963, "Q : Mortier");
        StdDraw.text(0.200, 0.983, "Z : Canon");
        StdDraw.text(0.1925, 0.963, "S : DCA");
        StdDraw.text(0.300, 0.983, "X : Mines");
        StdDraw.text(0.299, 0.963, "C : Piège");
        StdDraw.text(0.400, 0.983, "W : Barbelés");
        StdDraw.text(0.396, 0.963, "R : Quitter");
        StdDraw.text(0.525, 0.983, "E : Ameliorations");
        StdDraw.text(0.521, 0.963, "D : Suppression");
        if(!started)
        	StdDraw.text(0.635, 0.983, "T : Demarer");
    }
    // ------------------------------------------------------------------------------
    /**
     * Fonction qui récupère le positionnement de la souris et permet d'afficher une
     * image de tour en temps réél lorsque le joueur appuie sur une des touches
     * permettant la construction d'une tour.
     */
    public void drawMouse() {
        //On crée des valeures normaliser pour avoir les images de la bonne taille dans leurs cases respectives
        double normalizedX = (int) (StdDraw.mouseX() / squareWidth) * squareWidth + squareWidth / 2;
        double normalizedY = (int) (StdDraw.mouseY() / squareHeight) * squareHeight + squareHeight / 2;

        //On crée une image pour l'instant vide
        String image = null;

        //Puis on regarde la valeur de key
        switch (key) {
            //Si c'est "a", l'image devient celle de la tour de sniper
            case 'a':
                image = "src\\images\\SniperTower.png";
                break;
            //Si c'est "z", l'image devient celle du canon
            case 'z':
                image = "src\\images\\Canon.png";
                break;
            //Si c'est "w", l'image devient celle des barbelés
            case 'w':
                image = "src\\images\\barbeles2.png";
                break;
            //Si c'est "s", l'image devient celle de la DCA
            case 's':
                image = "src\\images\\Dca.png";
                break;
            //Si c'est "c", l'image devient celle du piege explosif
            case 'c':
                image = "src\\images\\piegeExp.png";
                break;
            //Si c'est "x", l'image devient celle de la mine
            case 'x':
                image = "src\\images\\mine.png";
                break;
            //Si c'est "q", l'image devient celle du moriter
            case 'q':
                image = "src\\images\\Mortar.png";
                break;
        }
        //Si l'image n'est pas nul et que l'on est pas sur une case qui contient déja une tour, on dessine la tour
        if (image != null && !onTower(new Position(normalizedX, normalizedY)))
            StdDraw.picture(normalizedX, normalizedY, image, squareWidth, squareHeight);

        //Si on est sur une tour, on dessine son champ d'action avec un cercle
        else{
            Position p = new Position(normalizedX, normalizedY);
            StdDraw.setPenColor(StdDraw.BLACK);
            for(Tower t : tower){
                if (t.position.equals(p)) StdDraw.circle(t.position.x, t.position.y, t.getRange());
            }
        }
    }

    // -----------------------------Fonctions_d'update-------------------------------
    /**
     * Pour chaque monstre de la liste de monstres de la vague, utilise la fonction
     * update() qui appelle les fonctions run() et draw() de Monster. Modifie la
     * position du monstre au cours du temps à l'aide du paramètre nextP.
     */
    //TODO commentaires
    public void updateMonsters() {

        Iterator<Monster> i = monsters.iterator();
        while (i.hasNext()) {
            Monster m = i.next();
            m.update();
            double normalizedX = (int) (m.p.x / squareWidth) * squareWidth + squareWidth / 2;
            double normalizedY = (int) (m.p.y / squareHeight) * squareHeight + squareHeight / 2;
            boolean effected = false;
            boolean killed = false;
            Iterator<PassiveTower> j = passiveTower.iterator();
            while (j.hasNext()) {
                PassiveTower t = j.next();
                double dx = Math.abs(t.position.x - normalizedX);
                double dy = Math.abs(t.position.y - normalizedY);
                if (dx < squareWidth / 2 && dy < squareHeight / 2) {
                    t.effect(m);
                    effected = true;
                    if (t.isDestroyed())
                        j.remove();
                }
            }

            if (!effected && m.getEffect() != null) {
                m.getEffect().uneffect(m);
            }

            if (m.p.x > base.x - (squareWidth / 4) && m.p.y > base.y - (squareHeight / 4)
                    && m.p.x < base.x + (squareWidth / 4) && m.p.y < base.y + (squareHeight / 4)) { // Le monstre est
                                                                                                    // arrive a
                                                                                                    // la base
                i.remove();
                killed = true;
                life--;
            }

            if (m.isBreach() && m.p.dist(spawn) > 2 * squareWidth) {
                boolean creuse = false;
                if (onChemin(new Position(normalizedX + 2 * squareWidth, normalizedY))
                        && !onChemin(new Position(normalizedX + squareWidth, normalizedY))
                        && !onTower(new Position(normalizedX + squareWidth, normalizedY))) {
                    creuse = true;
                    path.add(new Position(normalizedX + squareWidth, normalizedY));
                } else if (onChemin(new Position(normalizedX - 2 * squareWidth, normalizedY))
                        && !onChemin(new Position(normalizedX - squareWidth, normalizedY))
                        && !onTower(new Position(normalizedX - squareWidth, normalizedY))) {
                    creuse = true;
                    path.add(new Position(normalizedX - squareWidth, normalizedY));
                } else if (onChemin(new Position(normalizedX, normalizedY + 2 * squareHeight))
                        && !onChemin(new Position(normalizedX, normalizedY + squareHeight))
                        && !onTower(new Position(normalizedX, normalizedY + squareHeight))) {
                    creuse = true;
                    path.add(new Position(normalizedX, normalizedY + squareHeight));
                } else if (onChemin(new Position(normalizedX, normalizedY - 2 * squareHeight))
                        && !onChemin(new Position(normalizedX, normalizedY - squareHeight))
                        && !onTower(new Position(normalizedX, normalizedY - squareHeight))) {
                    creuse = true;
                    path.add(new Position(normalizedX, normalizedY - squareHeight));
                }
                if (creuse) {
                    for (Monster mon : monsters) {
                        mon.recalculateObjectif(path, base);
                    }
                    for (Monster mon : waitingMonster) {
                        mon.recalculateObjectif(path, base);
                    }
                    m.killed();
                    i.remove();
                    killed = true;
                }
            }
            if (!killed && m.hp <= 0) {
                m.killed();
                i.remove();
            }

        }
        if (started && monsters.isEmpty() && waitingMonster.isEmpty() && !end) {
            vague(++wave);
        }
    }

    // ------------------------------------------------------------------------------
    /**
     * Pour chaque projectile du monde, utilise la fonction update() qui appelle les
     * fonctions move() et draw() de Porjectile.
     */
    //TODO : commentaires
    public void updateProjectiles() {

        Iterator<Projectile> i = projectiles.iterator();
        while (i.hasNext()) {
            Projectile proj = i.next(); // must be called before you can call i.remove()
            boolean touched = false;
            proj.update();
            for (Monster m : monsters) {

                if (Math.sqrt(Math.pow(proj.position.x - m.p.x, 2) + Math.pow(proj.position.y - m.p.y, 2)) <= m
                        .getHitbox()
                        && (m.isAerial() && proj.isAerialLock() || !m.isAerial() && proj.isTerrestLock())) {
                    i.remove();
                    touched = true;
                    if (proj.isAreaDamage())
                        AOEDamage(m, proj.getDammage());
                    m.hp -= proj.getDammage();
                    if (m.hp <= 0) {
                        monsters.remove(m);
                        m.killed();
                        gold += m.getReward();
                    }
                    break;
                }
            }
            if (proj.motherDist() > proj.getMother().getRange() && !touched)
                i.remove();
        }

    }

    // ------------------------------------------------------------------------------
    /**
     * Pour chaque tour du monde, utilise la fonction update() qui appelle les
     * fonctions shot() et draw() de Tower.
     */
    public void updateTower() {
    	for (PassiveTower t : passiveTower) {
            t.draw();
        }
        for (Tower t : tower) {
            Projectile proj = t.update();
            if (proj != null)
                projectiles.add(proj);
        }
        

    }

    // ------------------------------------------------------------------------------
    /**
     * Fonction d'update du menu
     */
    public void updateMenu() {
        //On dessine le background menu
        drawBackground(true);
        StdDraw.setPenColor(StdDraw.RED);

        // On charge les textures
        String jouer = "src\\images\\boutonJouer.png";
        String exit = "src\\images\\boutonExit.png";
        String tuto = "src\\images\\boutonTuto.png";
        String options = "src\\images\\boutonOptions.png";
        
        //Puis on dessine les bouton grace aux images
        StdDraw.picture(0.5, 0.7, jouer, 0.4, 0.1);
        StdDraw.picture(0.5, 0.5, tuto, 0.4, 0.1);
        StdDraw.picture(0.5, 0.3, exit, 0.4, 0.1);
        StdDraw.picture(0.8, 0.1, options, 0.3, 0.075);
            //StdDraw.text(0.5, 0.9, "CHEAT ON");
    }

    // ------------------------------------------------------------------------------
    /**
     * Fonction d'update du tutoriel
     */
    public void updateTuto(int etape) {
        drawBackground(false);
        drawPath();
        updateTower();
        updateMonsters();
        updateProjectiles();
        spawningMonster();
        texteTuto(etape);
    }
 // ------------------------------------------------------------------------------
    /**
     * Fonction d'update du menu des options
     */
    public void updateOption() {
    	drawBackground(false);
    	// Chargement des textures
    	String cheatOn = "src\\images\\boutonCheatOn.png";
    	String cheatOff = "src\\images\\boutonCheatOff.png";
    	String texturePackPlaine = "src\\images\\boutonTexturePackPlaine.png";
    	String texturePackDesert = "src\\images\\boutonTexturePackDesert.png";
    	String aide = "src\\images\\boutonAide.png";
    	String retour = "src\\images\\boutonRetour.png";
    	
    	//Affichage des boutons
    	if(cheat)
    		StdDraw.picture(0.5, 0.8, cheatOn, 0.4, 0.1);
    	else
    		StdDraw.picture(0.5, 0.8, cheatOff, 0.4, 0.1);
    	if(texturePack==1)
    		StdDraw.picture(0.5, 0.6, texturePackPlaine, 0.4, 0.1);
    	else
    		StdDraw.picture(0.5, 0.6, texturePackDesert, 0.4, 0.1);
    	
        StdDraw.picture(0.5, 0.4, aide, 0.4, 0.1);
        StdDraw.picture(0.5, 0.2, retour, 0.4, 0.1);
    }
    // ------------------------------------------------------------------------------
    /**
     * Fonction d'update de l'aide
     */
    public void updateAide(int niv) {
    	drawBackground(false);
    	StdDraw.setPenColor(StdDraw.BLACK);
    	
    	//Chargement des texutres
    	String oui = "src\\images\\boutonOui.png";
    	String non = "src\\images\\boutonNon.png";
    	Font font = new Font("Arial", Font.BOLD, 30);
    	StdDraw.setFont(font);
    	
    	if(niv==1) {
    		
    		StdDraw.text(0.5, 0.8, "Vous voulez de l'aide ?");
    		StdDraw.picture(0.5, 0.65, oui, 0.4, 0.1);
    		StdDraw.picture(0.5, 0.45, non, 0.4, 0.1);
    	}
    	else {
    		StdDraw.text(0.5, 0.8, "Êtes vous sûr ?");
    		StdDraw.picture(0.5, 0.55, oui, 0.4, 0.1);
    		StdDraw.picture(0.5, 0.35, non, 0.4, 0.1);
    	}
    	
    }

    // ------------------------------------------------------------------------------
    /**
     * Met à jour toutes les informations du plateau de jeu ainsi que les
     * déplacements des monstres et les attaques des tours.
     * 
     * @return les points de vie restants du joueur
     */
    public int update() {
        drawBackground(false);
        drawPath();
        drawInfos();
        updateTower();
        updateMonsters();
        updateProjectiles();
        drawMouse();
        spawningMonster();
        return life;
    }

    // -------------------------------Fonctions_utiles--------------------------------
    /**
     * Récupère la touche appuyée par l'utilisateur et affiche les informations pour
     * la touche séléctionnée
     * 
     * @param key la touche utilisée par le joueur
     */
    public void keyPress(char key) {
        //On recupere la valeure entrée au clavier
        key = Character.toLowerCase(key);
        //On change la valeur de la variable globale
        this.key = key;

        //Puis on regarde la valeur de key
        switch (key) {
            case 't':
                if (!started) {
                    vague(1);
                    started = true;
                }
                break;
            // Si c'est un "r" on reviens au menu
            case 'r':
            	game.stop();
                menu();
        }
    }

    // ------------------------------------------------------------------------------
    /**
     * Vérifie lorsque l'utilisateur clique sur sa souris qu'il peut: - Ajouter une
     * tour à la position indiquée par la souris. - Améliorer une tour existante.
     * Puis l'ajouter à la liste des tours
     * 
     * @param x
     * @param y
     */
    public void mouseClick(double x, double y) {
        //On crée des valeures normaliser pour avoir les images de la bonne taille dans leurs cases respectives
        double normalizedX = (int) (x / squareWidth) * squareWidth + squareWidth / 2;
        double normalizedY = (int) (y / squareHeight) * squareHeight + squareHeight / 2;
        //On crée une nouvelle position qui est celle où l'on a cliqué
        Position p = new Position(normalizedX, normalizedY);

        if (normalizedY < 1 - squareHeight) {
            //On regarde la valeur de key
            switch (key) {
                //Si c'est un "a" : on place une tour de sniper
                case 'a':
                    if (!onTower(p) && !onChemin(p) && gold >= 50) {
                        Tower t = new SniperTower(p, this);
                        tower.add(t);
                        gold -= 50;
                        StdDraw.pause(100);
                    }
                    break;
                //Si c'est un "z" : on place un canon
                case 'z':
                    if (!onTower(p) && !onChemin(p) && gold >= 60) {
                        Tower t = new Canon(p, this);
                        tower.add(t);
                        gold -= 60;
                        StdDraw.pause(100);
                    }
                    break;
                //Si c'est un "q" : on place un mortier
                case 'q':
                    if (!onTower(p) && !onChemin(p) && gold >= 100) {
                        Tower t = new Mortier(p, this);
                        tower.add(t);
                        gold -= 100;
                        StdDraw.pause(100);
                    }
                    break;
                //Si c'est un "s" : on place une DCA
                case 's':
                if (!onTower(p) && !onChemin(p) && gold >= 60) {
                    Tower t = new Dca(p, this);
                    tower.add(t);
                    gold -= 60;
                    StdDraw.pause(100);
                }
                    break;
                //Si c'est un "w" : on place des barbelés
                case 'w':
                    if (onChemin(p) && gold >= 40) {
                        PassiveTower t = new Barbele(p, this);
                        passiveTower.add(t);
                        gold -= 40;
                        StdDraw.pause(100);
                    }
                    break;
                //Si c'est un "c" : on place un piège explosif
                case 'c':
                    if (onChemin(p) && gold >= 40) {
                        PassiveTower t = new PiegeExp(p, this);
                        passiveTower.add(t);
                        gold -= 40;
                        StdDraw.pause(100);
                    }
                    break;
                //Si c'est un "x" : on place une mine
                case 'x':
                    if (onChemin(p) && gold >= 40) {
                        PassiveTower t = new Mine(p, this);
                        passiveTower.add(t);
                        gold -= 40;
                        StdDraw.pause(100);
                    }
                    break;
                //Si c'est un "e" : on ameliore la tour si il y en a une et que le joueur a la quantité d'or suffisant
                case 'e':
                    if (onTower(p) && gold >= 100) {
                        for (Tower t : tower) {
                            if (t.position.equals(p)) {
                                t.upgrade();
                                StdDraw.pause(100);
                            }
                        }

                    }
                    break;
                  //Si c'est un "d" : on supprime la tour si il y en a une et on donne 25g au joueur
                case 'd':
                    
                    	Iterator<Tower> t = tower.iterator();
                    	while(t.hasNext()) {
                    		Tower tmp = t.next();
                            if (tmp.position.equals(p)) {
                                t.remove();
                                gold += 25;
                            }
                        }
                    
                    break;
            }
        }
    }

    // ------------------------------------------------------------------------------
    /**
     * lorsque l'utilisateur clique sur sa souris, si il clique sur un bouton, ecxecute le choix fait
     * 
     * @param x
     * @param y
     */
    public void mouseClickMenu(double x, double y) {

    	if(!quitteMenu) {
        if ((x > 0.3 && x < 0.7) && (y > 0.65 && y < 0.75)) {
            menu.stop();
            run();
        } else if ((x > 0.3 && x < 0.7) && (y > 0.45 && y < 0.55)) {
            menu.stop();
            tuto();
        } else if ((x > 0.3 && x < 0.7) && (y > 0.25 && y < 0.35)) {
                System.exit(0); 
        }
        else if ((x > 0.65 && x < 0.95) && (y > 0.075 && y < 0.125)) {
        	options();
        }
    	}	
        		
    }
    
 // ------------------------------------------------------------------------------
    /**
     * lorsque l'utilisateur clique sur sa souris, si il clique sur un bouton, ecxecute le choix fait
     * 
     * @param x
     * @param y
     */
    public void mouseClickOption(double x, double y) {
        if((x > 0.3 && x < 0.7) && (y > 0.75 && y < 0.85)) {
        	if(cheat) cheat = false;
        	else cheat = true;
        }
        else if((x > 0.3 && x < 0.7) && (y > 0.55 && y < 0.65)) {
        	if(texturePack==1) texturePack=2;
        	else texturePack=1;
        }
        else if((x > 0.3 && x < 0.7) && (y > 0.35 && y < 0.45)) {
        	quitteOptions=true;
        	aide(1);
        }
        else if((x > 0.3 && x < 0.7) && (y > 0.15 && y < 0.25)) {
        	quitteOptions=true;
        	menu();
        }     		
    }
    
 // ------------------------------------------------------------------------------
    /**
     * lorsque l'utilisateur clique sur sa souris, si il clique sur un bouton, ecxecute le choix fait
     * 
     * @param x
     * @param y
     */
    public void mouseClickAide(double x, double y, int niv) {
       if(niv==1) {
    	   if((x > 0.3 && x < 0.7) && (y > 0.60 && y < 0.70)) {
    		   niv=2;
    		   aide(niv);
    	   }
    	   else if((x > 0.3 && x < 0.7) && (y > 0.40 && y < 0.5)) {
    		   quitteAide=true;
    		   roll.stop();
    		   options();
    	   }
       }else {
    	   if((x > 0.3 && x < 0.7) && (y > 0.50 && y < 0.60)) {
    		   if(!roll.isPLay()) {
    			   menu.stop();
    		   roll.play();
    		   }
    		      		   
    	   }
    	   else if((x > 0.3 && x < 0.7) && (y > 0.30 && y < 0.4)) {
    		   roll.stop();
    		   menu.play();
    		   quitteAide=true;
    		   options();
    	   }
       }
        	
        		
    }

    // ------------------------------------------------------------------------------
    /**
     * Fonction d'intialisation du chemin
     * 
     * @return la liste des cases appartenant au chemin
     */
    //TODO commentaires
    public Set<Position> setPath() {
        Set<Position> chemin = new HashSet<Position>();
        int[][] pathTable = new int[nbSquareX][nbSquareY];
        int operation = 0;
        do {
            for (int i = 0; i < pathTable.length; i++) {
                for (int j = 0; j < pathTable[i].length; j++) {
                    pathTable[i][j] = 0;
                }
            }
            int spawnX = (int) (Math.random() * (nbSquareX - 2)) + 2;
            int pathLengthMin = 3 * (nbSquareY + nbSquareX) / 4;
            boolean end = false;
            int courantX = spawnX;
            int courantY = 0;
            pathTable[courantX][courantY] = 2;
            operation = 0;
            for (int i = 0; (i < pathLengthMin || !end) && operation < 500; i++) {
                operation++;
                int dir = (int) (Math.random() * 5); // 0,4 pour Est; 1 pour Nord; 2 pour Sud; 3 pour Ouest
                switch (dir) {
                    case 0:
                    case 4:
                        if (courantY + 2 < nbSquareY && pathTable[courantX][courantY + 1] == 0
                                && pathTable[courantX][courantY + 2] == 0) {
                            pathTable[courantX][courantY + 1] = 1;
                            courantY++;
                            pathTable[courantX][courantY + 1] = 1;
                            courantY++;
                            if (courantY > 3 * nbSquareY / 4)
                                end = true;
                            else
                                end = false;
                        } else {
                            end = false;
                            i--;
                        }
                        break;
                    case 1:
                        if (courantX + 2 < nbSquareX - 2 && pathTable[courantX + 1][courantY] == 0
                                && pathTable[courantX + 2][courantY] == 0) {
                            pathTable[courantX + 1][courantY] = 1;
                            courantX++;
                            pathTable[courantX + 1][courantY] = 1;
                            courantX++;
                            if (courantY > 3 * nbSquareY / 4)
                                end = true;
                            else
                                end = false;
                        } else {
                            end = false;
                            i--;
                        }
                        break;
                    case 2:
                        if (courantX - 2 >= 2 && pathTable[courantX - 1][courantY] == 0
                                && pathTable[courantX - 2][courantY] == 0) {
                            pathTable[courantX - 1][courantY] = 1;
                            courantX--;
                            pathTable[courantX - 1][courantY] = 1;
                            courantX--;
                            if (courantY > 3 * nbSquareY / 4)
                                end = true;
                            else
                                end = false;
                        } else {
                            end = false;
                            i--;
                        }
                        break;
                    case 3:
                        if (courantY - 2 >= 0 && pathTable[courantX][courantY - 1] == 0
                                && pathTable[courantX][courantY - 2] == 0) {
                            pathTable[courantX][courantY - 1] = 1;
                            courantY--;
                            pathTable[courantX][courantY - 1] = 1;
                            courantY--;
                            if (courantY > (3 * nbSquareY) / 4)
                                end = true;
                            else
                                end = false;
                        } else {
                            end = false;
                            i--;
                        }
                        break;
                }
            }
            pathTable[courantX][courantY] = 3;
        } while (operation >= 500);
        for (int i = 0; i < pathTable.length; i++) {
            for (int j = 0; j < pathTable[i].length; j++) {
                if (pathTable[i][j] == 1)
                    chemin.add(new Position(j * squareWidth + squareWidth / 2,
                            (nbSquareY - i - 1) * squareHeight + squareHeight / 2));
                if (pathTable[i][j] == 2)
                    spawn = new Position(j * squareWidth + squareWidth / 2,
                            (nbSquareY - i - 1) * squareHeight + squareHeight / 2);
                if (pathTable[i][j] == 3) {
                    base = new Position(j * squareWidth + squareWidth / 2,
                            (nbSquareY - i - 1) * squareHeight + squareHeight / 2);
                    chemin.add(new Position(j * squareWidth + squareWidth / 2,
                            (nbSquareY - i - 1) * squareHeight + squareHeight / 2));
                }
            }
        }
        return chemin;
    }

    // ------------------------------------------------------------------------------
    /**
     * Crée le chemin en fonction de l'étape du tutoriel
     * 
     * @param etape
     * @return la liste des cases appartenant au chemin
     */
    //TODO commentaires
    public Set<Position> setPathTuto(int etape) {
        Set<Position> chemin = new HashSet<Position>();
        int[][] pathTable = new int[nbSquareY][nbSquareX];
        if (etape >= 1) { // Ligne Droite
            for (int i = 0; i < nbSquareX; i++) {
                if (i == 0)
                    pathTable[nbSquareY / 2][i] = 2;
                else
                    pathTable[nbSquareY / 2][i] = 1;
            }
            pathTable[nbSquareY / 2][nbSquareX - 1] = 3;
        }
        if (etape == 3 || etape == 6) { // Ligne Droite
            for (int i = 0; i < nbSquareX; i++) {
                if (i == 0)
                    pathTable[nbSquareY / 2][i] = 2;
                else
                    pathTable[nbSquareY / 2][i] = 1;
            }
            pathTable[nbSquareY / 2 + 2][nbSquareX / 2] = 1;
            for (int i = nbSquareY / 2; i < nbSquareY / 2 + 3; i++) {
                pathTable[i][nbSquareX / 2 - 1] = 1;
                pathTable[i][nbSquareX / 2 + 1] = 1;
            }
            pathTable[nbSquareY / 2][nbSquareX / 2] = 0;
            pathTable[nbSquareY / 2][nbSquareX - 1] = 3;
        }

        for (int i = 0; i < pathTable.length; i++) {
            for (int j = 0; j < pathTable[i].length; j++) {
                if (pathTable[i][j] == 1)
                    chemin.add(new Position(j * squareWidth + squareWidth / 2,
                            (nbSquareY - i - 1) * squareHeight + squareHeight / 2));
                if (pathTable[i][j] == 2)
                    spawn = new Position(j * squareWidth + squareWidth / 2,
                            (nbSquareY - i - 1) * squareHeight + squareHeight / 2);
                if (pathTable[i][j] == 3) {
                    base = new Position(j * squareWidth + squareWidth / 2,
                            (nbSquareY - i - 1) * squareHeight + squareHeight / 2);
                    chemin.add(new Position(j * squareWidth + squareWidth / 2,
                            (nbSquareY - i - 1) * squareHeight + squareHeight / 2));
                }
            }
        }
        return chemin;
    }

    // ------------------------------------------------------------------------------
    /**
     * Place les tours en fonction de l'étape du tutoriel
     * 
     * @param etape
     */
    public void towerTuto(int etape) {
        //On enleve toute les tours deja présentes
        tower.clear();
        passiveTower.clear();

        //On crée deux position, une où on va placer les tours et l'autres les monstres "statiques"
        Position p = new Position(nbSquareX / 2 * squareWidth + squareWidth / 2,
                (nbSquareY - nbSquareY / 2 + 1 - 1) * squareHeight + squareHeight / 2);
        Position p2 = new Position(nbSquareX / 2 * squareWidth + squareWidth / 2,
                nbSquareY / 2 * squareHeight + squareHeight / 2);

        //Si on est à la quatrième etape on place un canon
        if (etape == 4) {
            Tower t = new Canon(p, this);
            for (int i = 0; i < 2; i++)
                t.upgrade();
            tower.add(t);
        }
        //Si on est à la quatrième etape on place un canon
        if (etape == 5) {
            Tower t = new Dca(p, this);
            for (int i = 0; i < 2; i++)
                t.upgrade();
            tower.add(t);
        }
        //Si on est à la quatrième etape on place un canon
        if (etape == 8) {
            Tower t = new SniperTower(p, this);
            for (int i = 0; i < 2; i++)
                t.upgrade();
            tower.add(t);
        }
        //Si on est à la quatrième etape on place un canon
        if (etape == 9) {
            Tower t = new Canon(p, this);
            for (int i = 0; i < 2; i++)
                t.upgrade();
            tower.add(t);
        }
        //Si on est à la quatrième etape on place un canon
        if (etape == 10) {
            Tower t = new Dca(p, this);
            for (int i = 0; i < 2; i++)
                t.upgrade();
            tower.add(t);
        }
        //Si on est à la quatrième etape on place un canon
        if (etape == 11) {
            Tower t = new Mortier(p, this);
            for (int i = 0; i < 2; i++)
                t.upgrade();
            tower.add(t);
        }
        //Si on est à la quatrième etape on place un canon
        if (etape == 12)
            passiveTower.add(new Barbele(p2, this));
        //Si on est à la quatrième etape on place un canon
        if (etape == 13)
            passiveTower.add(new PiegeExp(p2, this));
        //Si on est à la quatrième etape on place un canon
        if (etape == 14)
            passiveTower.add(new Mine(p2, this));
    }

    // ------------------------------------------------------------------------------
    /**
     * Affiche les textes du tutoriel selon l'étape
     * 
     * @param etape
     */
    public void texteTuto(int etape) {
        StdDraw.setPenColor(StdDraw.BLACK);
        Position p = new Position(nbSquareX / 2 * squareWidth + squareWidth / 2,
                (nbSquareY - nbSquareY / 6 - 1) * squareHeight + squareHeight / 2);
        Position p2 = new Position(nbSquareX / 2 * squareWidth + squareWidth / 2,
                (nbSquareY / 4) * squareHeight + squareHeight / 2);

        if (etape == 1) {
            StdDraw.text(p.x, p.y - squareHeight, "Le terroriste est l'ennemi de base");
            StdDraw.text(p.x, p.y - 2 * squareHeight, "Peu de vie, petite vitesse et terreste");
            Monster m = new Terrorist(p, null, null, this);
            m.draw();
        }
        if (etape == 2) {
            StdDraw.text(p.x, p.y - squareHeight, "Le drone d'attaque est le premier ennemi aérien");
            StdDraw.text(p.x, p.y - 2 * squareHeight, "Peu de vie mais possède une vitesse acrue");
            Monster m = new AttackDrone(p, null, null, this);
            m.draw();
        }
        if (etape == 3) {
            StdDraw.text(p.x, p.y - squareHeight, "Le scout à la capacité de créer un passage pour ses aliés");
            StdDraw.text(p.x, p.y - 2 * squareHeight, "Il meurt apres la création mais reste très dangereux");
            Monster m = new Scout(p, null, null, this);
            m.draw();
        }
        if (etape == 4) {
            StdDraw.text(p.x, p.y - squareHeight, "Le camion est le premier véhicule terrestre");
            StdDraw.text(p.x, p.y - 2 * squareHeight, "A sa mort il libere les 3 terroristes à son bord");
            Monster m = new Truck(p, null, null, this);
            m.draw();
        }
        if (etape == 5) {
            StdDraw.text(p.x, p.y - squareHeight, "Le Bombardier est la version aérienne du camion");
            StdDraw.text(p.x, p.y - 2 * squareHeight, "A sa mort 3 drones d'attaque sont largués");
            Monster m = new Bomber(p, null, null, this);
            m.draw();
        }
        if (etape == 6) {
            StdDraw.text(p.x, p.y - squareHeight, "Le SuperCopter est un nouvel ennemi aérien");
            StdDraw.text(p.x, p.y - 2 * squareHeight, "Grace à son altitude il peut ignorer les chemins");
            Monster m = new SuperCopter(p, null, null, this);
            m.draw();
        }
        if (etape == 7) {
            StdDraw.text(p.x, p.y - squareHeight, "Le CRS est un ennemi extremement résistant");
            StdDraw.text(p.x, p.y - 2 * squareHeight,
                    "En plus de ses PV, il génere un bouclier devant lui qui absorbe les dégats");
            Monster m = new Crs(p, null, null, this);
            m.draw();
        }
        if (etape == 8) {
            StdDraw.text(p.x, p.y - squareHeight, "La tour de sniper est la tourelle de base");
            StdDraw.text(p.x, p.y - 2 * squareHeight,
                    "Peu cher, elle touche a la fois les ennemis terrestes et aériens");
            StdDraw.text(p2.x, p2.y - squareHeight, "Touche A /// 50 Golds");
            Tower t = new SniperTower(p, this);
            t.draw();
        }
        if (etape == 9) {
            StdDraw.text(p.x, p.y - squareHeight, "Le canon possède un grande puissance de feu");
            StdDraw.text(p.x, p.y - 2 * squareHeight,
                    "Par contre il vise uniquement les ennemis terrestes et est lent a recharger");
            StdDraw.text(p2.x, p2.y - squareHeight, "Touche Z /// 60 Golds");
            Tower t = new Canon(p, this);
            t.draw();
        }
        if (etape == 10) {
            StdDraw.text(p.x, p.y - squareHeight, "La DCA est la version anti-aérienne du canon");
            StdDraw.text(p.x, p.y - 2 * squareHeight, "Elle vise uniquement les ennemis aériens");
            StdDraw.text(p2.x, p2.y - squareHeight, "Touche S /// 60 Golds");
            Tower t = new Dca(p, this);
            t.draw();
        }
        if (etape == 11) {
            StdDraw.text(p.x, p.y - squareHeight, "La mortier est la seule tourelle a délivrer des dégats de zone");
            StdDraw.text(p.x, p.y - 2 * squareHeight, "Ses projectiles visent uniquement les ennemis terrestes");
            StdDraw.text(p2.x, p2.y - squareHeight, "Touche Q /// 100 Golds");
            Tower t = new Mortier(p, this);
            t.draw();
        }
        if (etape == 12) {
            StdDraw.text(p.x, p.y - squareHeight, "Le barbelé est la premiere tour passive, elle ralentit les ennemis");
            StdDraw.text(p.x, p.y - 2 * squareHeight,
                    "Comparé aux tourelles, les tours passives se pose sur le chemin");
            StdDraw.text(p2.x, p2.y - squareHeight, "Touche W /// 40 Golds");
            PassiveTower t = new Barbele(p, this);
            t.draw();
        }
        if (etape == 13) {
            StdDraw.text(p.x, p.y - squareHeight, "Le piege explosif inflige des dégats aux ennemis terrestes");
            StdDraw.text(p.x, p.y - 2 * squareHeight,
                    "Les ennemis volants ne le déclenche pas et les véhicules recoivent moins de dégats de ce piege");
            StdDraw.text(p2.x, p2.y - squareHeight, "Touche C /// 40 Golds");
            PassiveTower t = new PiegeExp(p, this);
            t.draw();
        }
        if (etape == 14) {
            StdDraw.text(p.x, p.y - squareHeight, "La mine est la version anti-véhicule du piege explosif");
            StdDraw.text(p2.x, p2.y - squareHeight, "Touche X /// 40 Golds");
            PassiveTower t = new Mine(p, this);
            t.draw();
        }
        if (etape == 15) {

            StdDraw.text(p.x, p.y - 2 * squareHeight,
                    "Vous pouvez améliorer toute les tourelles non passives avec la touche E pour 100 Golds");
            StdDraw.text(p2.x, p2.y - squareHeight, "Vous pouvez supprimer toute les tourelles avec la touche D et vous récupererez 25 golds");
        }
    }

    // ------------------------------------------------------------------------------
    /**
     * fonction de generation des vagues de monstre
     * 
     * @param numero le numero de la vague
     */
    public void vague(int numero) {
        if (wave > 10) {
            end = true;
            wave = 0;
        } else {
            // Vague 1
            if (numero == 1) {
                // 10 ennemis -> 6 Terrorists 4 AttackDrones
                for (int i = 0; i < 5; i++) {
                    waitingMonster.add(new Terrorist(new Position(spawn.x, spawn.y), path, new Position(base), this));
                }
                waitingMonster.add(new Terrorist(new Position(spawn.x, spawn.y), path, new Position(base), this));
                waitingMonster.add(new AttackDrone(new Position(spawn.x, spawn.y), path, new Position(base), this));
                for (int i = 0; i < 3; i++) {
                    waitingMonster.add(new AttackDrone(new Position(spawn.x, spawn.y), path, new Position(base), this));
                }
            }
            // Vague 2
            if (numero == 2) {
                // 20 ennemis -> 12 Terrorists 8 AttackDrones
                for (int i = 0; i < 5; i++) {
                    waitingMonster.add(new Terrorist(new Position(spawn.x, spawn.y), path, new Position(base), this));
                }
                for (int i = 0; i < 7; i++) {
                    waitingMonster.add(new Terrorist(new Position(spawn.x, spawn.y), path, new Position(base), this));
                    waitingMonster.add(new AttackDrone(new Position(spawn.x, spawn.y), path, new Position(base), this));
                }
                waitingMonster.add(new AttackDrone(new Position(spawn.x, spawn.y), path, new Position(base), this));
            }
            // Vague 3
            if (numero == 3) {
                // 30 ennemis -> 18 Terrorists 12 AttackDrones
                for (int i = 0; i < 6; i++) {
                    Monster m = new Terrorist(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    waitingMonster.add(m);
                }

                for (int i = 0; i < 12; i++) {
                    Monster m = new Terrorist(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    waitingMonster.add(m);
                    Monster n = new AttackDrone(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    waitingMonster.add(n);
                }
            }
            // Vague 4
            if (numero == 4) {
                // 40 ennemis -> 25 Terrosists 14 AttackDrones 1 Scout
                for (int i = 0; i < 5; i++) {
                    Monster m = new Terrorist(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    m.hp = 2 * m.hp;
                    waitingMonster.add(m);
                }
                for (int i = 0; i < 7; i++) {
                    Monster m = new Terrorist(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    m.hp = 2 * m.hp;
                    waitingMonster.add(m);
                    Monster n = new AttackDrone(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    n.hp = 2 * n.hp;
                    waitingMonster.add(n);
                }
                for (int i = 0; i < 1; i++) {
                    waitingMonster.add(new Scout(new Position(spawn.x, spawn.y), path, new Position(base), this));
                }
                for (int i = 0; i < 7; i++) {
                    Monster m = new Terrorist(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    m.hp = 2 * m.hp;
                    waitingMonster.add(m);
                    Monster n = new AttackDrone(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    n.hp = 2 * n.hp;
                    waitingMonster.add(n);
                }
                for (int i = 0; i < 6; i++) {
                    Monster m = new Terrorist(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    m.hp = 2 * m.hp;
                    waitingMonster.add(m);
                }
            }
            // Vague 5
            if (numero == 5) {
                // 50 ennemis -> 33 terrorists 18 attackDrones
                for (int i = 0; i < 10; i++) {
                    Monster m = new Terrorist(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    m.hp = 2 * m.hp;
                    waitingMonster.add(m);
                }
                for (int i = 0; i < 15; i++) {
                    Monster m = new Terrorist(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    m.hp = 2 * m.hp;
                    waitingMonster.add(m);
                    Monster n = new AttackDrone(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    n.hp = 2 * n.hp;
                    waitingMonster.add(n);
                }
                for (int i = 0; i < 8; i++) {
                    Monster m = new Terrorist(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    m.hp = 2 * m.hp;
                    waitingMonster.add(m);
                }
                for (int i = 0; i < 3; i++) {
                    Monster m = new AttackDrone(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    m.hp = 2 * m.hp;
                    waitingMonster.add(m);
                }
            }
            // Vague 6
            if (numero == 6) {
                // 60 ennemis -> 39 terrorists 21 attackDrones
                for (int i = 0; i < 5; i++) {
                    Monster m = new Terrorist(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    m.hp = 3 * m.hp;
                    waitingMonster.add(m);
                }
                for (int i = 0; i < 17; i++) {
                    Monster m = new Terrorist(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    m.hp = 3 * m.hp;
                    waitingMonster.add(m);
                    Monster n = new AttackDrone(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    n.hp = 3 * n.hp;
                    waitingMonster.add(n);
                }
                for (int i = 0; i < 4; i++) {
                    Monster m = new AttackDrone(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    m.hp = 3 * m.hp;
                    waitingMonster.add(m);
                }
                for (int i = 0; i < 7; i++) {
                    Monster m = new Terrorist(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    m.hp = 3 * m.hp;
                    waitingMonster.add(m);
                }
            }
            // Vague 7
            if (numero == 7) {
                // 70 ennemis : 44 terrorists 25 attackDrones 1 Truck
                for (int i = 0; i < 10; i++) {
                    Monster m = new Terrorist(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    m.hp = 3 * m.hp;
                    waitingMonster.add(m);
                }
                for (int i = 0; i < 10; i++) {
                    Monster m = new Terrorist(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    m.hp = 3 * m.hp;
                    waitingMonster.add(m);
                    Monster n = new AttackDrone(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    n.hp = 3 * n.hp;
                    waitingMonster.add(n);
                }
                for (int i = 0; i < 1; i++) {
                    waitingMonster.add(new Truck(new Position(spawn.x, spawn.y), path, new Position(base), this));
                }
                for (int i = 0; i < 10; i++) {
                    Monster m = new Terrorist(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    m.hp = 3 * m.hp;
                    waitingMonster.add(m);
                    Monster n = new AttackDrone(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    n.hp = 3 * n.hp;
                    waitingMonster.add(n);
                }
                for (int i = 0; i < 10; i++) {
                    Monster m = new Terrorist(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    m.hp = 3 * m.hp;
                    waitingMonster.add(m);
                }
                for (int i = 0; i < 5; i++) {
                    Monster m = new AttackDrone(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    m.hp = 3 * m.hp;
                    waitingMonster.add(m);
                }
                for (int i = 0; i < 4; i++) {
                    Monster m = new Terrorist(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    m.hp = 3 * m.hp;
                    waitingMonster.add(m);
                }

            }
            // Vague 8
            if (numero == 8) {
                // 80 ennemis -> 51 terrorists 30 attackDrones 1 Bomber
                for (int i = 0; i < 11; i++) {
                    Monster m = new Terrorist(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    m.hp = 4 * m.hp;
                    waitingMonster.add(m);
                }
                for (int i = 0; i < 10; i++) {
                    Monster m = new Terrorist(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    m.hp = 4 * m.hp;
                    waitingMonster.add(m);
                    Monster n = new AttackDrone(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    n.hp = 4 * n.hp;
                    waitingMonster.add(n);
                }
                for (int i = 0; i < 1; i++) {
                    waitingMonster.add(new Bomber(new Position(spawn.x, spawn.y), path, new Position(base), this));
                }
                for (int i = 0; i < 10; i++) {
                    Monster m = new Terrorist(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    m.hp = 4 * m.hp;
                    waitingMonster.add(m);
                    Monster n = new AttackDrone(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    n.hp = 4 * n.hp;
                    waitingMonster.add(n);
                }
                for (int i = 0; i < 10; i++) {
                    Monster m = new Terrorist(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    m.hp = 4 * m.hp;
                    waitingMonster.add(m);
                }
                for (int i = 0; i < 10; i++) {
                    Monster m = new AttackDrone(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    m.hp = 4 * m.hp;
                    waitingMonster.add(m);
                }
                for (int i = 0; i < 10; i++) {
                    Monster m = new Terrorist(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    m.hp = 4 * m.hp;
                    waitingMonster.add(m);
                }

            }
            // Vague 9
            if (numero == 9) {
                // 90 ennemis -> 53 terrorists 30 attackDrones 2 Scouts 2 Trucks 2 Bomber 1 SupetCopter
            	for (int i = 0; i < 13; i++) {
                    Monster m = new Terrorist(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    m.hp = 6 * m.hp;
                    waitingMonster.add(m);
                }
                for (int i = 0; i < 13; i++) {
                    Monster m = new Terrorist(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    m.hp = 6 * m.hp;
                    waitingMonster.add(m);
                    Monster n = new AttackDrone(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    n.hp = 6 * n.hp;
                    waitingMonster.add(n);
                }
                
                Monster scout = new Scout(new Position(spawn.x, spawn.y), path, new Position(base), this);
                scout.hp = 5 * scout.hp;
                waitingMonster.add(scout);
                
                waitingMonster.add(new SuperCopter(new Position(spawn.x, spawn.y), path, new Position(base), this));
                
                Monster scoutt = new Scout(new Position(spawn.x, spawn.y), path, new Position(base), this);
                scoutt.hp = 4 * scoutt.hp;
                waitingMonster.add(scout);
                
                for (int i = 0; i < 17; i++) {
                    Monster m = new Terrorist(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    m.hp = 6 * m.hp;
                    waitingMonster.add(m);
                    Monster n = new AttackDrone(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    n.hp = 5 * n.hp;
                    waitingMonster.add(n);
                }
              
                for (int i = 0; i < 7; i++) {
                    Monster m = new AttackDrone(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    m.hp = 6 * m.hp;
                    waitingMonster.add(m);
                }
            }
            // Vague 10
            if (numero == 10) {
                // 100 ennemis -> 58 terrorists 34 attackDrones 1 Scout 2 Trucks 2 Bombers 2 SuperCopter 1 CRS
                for (int i = 0; i < 24; i++) {
                    Monster m = new Terrorist(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    m.hp = 4 * m.hp;
                    waitingMonster.add(m);
                }
                for (int i = 0; i < 17; i++) {
                    Monster m = new Terrorist(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    m.hp = 4 * m.hp;
                    waitingMonster.add(m);
                    Monster n = new AttackDrone(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    n.hp = 4 * n.hp;
                    waitingMonster.add(n);
                }
                for (int i = 0; i < 2; i++) {
                	Monster bomber = new Bomber(new Position(spawn.x, spawn.y), path, new Position(base), this);
                	bomber.hp = bomber.hp * 2;
                    waitingMonster.add(bomber);
                }
                for (int i = 0; i < 1; i++) {
                    waitingMonster.add(new Crs(new Position(spawn.x, spawn.y), path, new Position(base), this));
                }
                Monster scout = new Scout(new Position(spawn.x, spawn.y), path, new Position(base), this);
                scout.hp = 4 * scout.hp;
                waitingMonster.add(scout);
                for (int i = 0; i < 2; i++) {
                	Monster truck = new Truck(new Position(spawn.x, spawn.y), path, new Position(base), this);
                	truck.hp = truck.hp * 2;
                    waitingMonster.add(truck);
                }
                for (int i = 0; i < 17; i++) {
                    Monster m = new Terrorist(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    m.hp = 4 * m.hp;
                    waitingMonster.add(m);
                    Monster n = new AttackDrone(new Position(spawn.x, spawn.y), path, new Position(base), this);
                    n.hp = 4 * n.hp;
                    waitingMonster.add(n);
                }
            }
        }
    }

    // ------------------------------------------------------------------------------
    /**
     * generation des vagues de monstre en fonction de l'étape du tutoriel
     * 
     * @param numero le numero de la vague
     */
    public void vagueTuto(int etape) {

        if (etape == 1)
            waitingMonster.add(new Terrorist(spawn, path, base, this));
        if (etape == 2)
            waitingMonster.add(new AttackDrone(spawn, path, base, this));
        if (etape == 3) {
            waitingMonster.add(new Terrorist(spawn, path, base, this));
            waitingMonster.add(new Scout(spawn, path, base, this));
            waitingMonster.add(new Terrorist(spawn, path, base, this));
        }
        if (etape == 4)
            waitingMonster.add(new Truck(spawn, path, base, this));
        if (etape == 5)
            waitingMonster.add(new Bomber(spawn, path, base, this));
        if (etape == 6)
            waitingMonster.add(new SuperCopter(spawn, path, base, this));
        if (etape == 7)
            waitingMonster.add(new Crs(spawn, path, base, this));
        if (etape > 7) {
            for (int i = 0; i < 3; i++)
                waitingMonster.add(new AttackDrone(spawn, path, base, this));
            for (int i = 0; i < 3; i++)
                waitingMonster.add(new Terrorist(spawn, path, base, this));

        }

    }

    // ------------------------------------------------------------------------------
    /**
     * Fonction de spawn des monstres
     */
    public void spawningMonster() {
        if (tick == 0 && !waitingMonster.isEmpty()) {
            Monster spawning = waitingMonster.get(0);
            monsters.add(spawning);
            waitingMonster.remove(spawning);
        }
    }

    // ------------------------------------------------------------------------------
    /**
     * Fonction pour savoir si une position appartient au chemin
     * 
     * @param p la position à tester
     * @return true si la positon appartient au chemin, false sinon
     */
    public boolean onChemin(Position p) {
        boolean res = false;
        for (Position chemin : path) {
            double dx = Math.abs(chemin.x - p.x);
            double dy = Math.abs(chemin.y - p.y);
            if (dx < squareWidth / 2 && dy < squareHeight / 2)
                res = true;
        }
        if (!res && p.equals(spawn))
            res = true;
        return res;
    }

    // ------------------------------------------------------------------------------
    /**
     * Fonction pour savoir si une position est la même que celle d'une tour
     * 
     * @param p la position à tester
     * @return true si la positon est sur une tour, false sinon
     */
    public boolean onTower(Position p) {
        boolean res = false;
        for (Tower t : tower) {
            double dx = Math.abs(t.position.x - p.x);
            double dy = Math.abs(t.position.y - p.y);
            if (dx < squareWidth / 2 && dy < squareHeight / 2)
                res = true;
        }
        return res;
    }

    // ------------------------------------------------------------------------------
    /**
     * Fonction pour gerer les dégats de zone
     * 
     * @param m      le monstre ciblé par le projectile AOE
     * @param damage les degats faits par le projectile
     */
    public void AOEDamage(Monster m, int damage) {
        for (Monster monster : monsters) {
        	//2 * squareHeight
            if (Math.sqrt(Math.pow(monster.p.x - m.p.x, 2) + Math.pow(monster.p.y - m.p.y, 2)) < 0.1
                    && !monster.isAerial()) {
                monster.hp -= damage;
                gold += monster.getReward();
            }
        }
    }

    // ------------------------------------------------------------------------------
    /**
     * Récupère la touche entrée au clavier ainsi que la position de la souris et
     * met à jour le plateau en fonction de ces interractions
     */
    public void run() {
    	Font font = new Font("Arial", Font.BOLD, 17);
        StdDraw.setFont(font);
        StdDraw.pause(1000);
    	Random random = new Random();
    	int index = random.nextInt(musicPath.size());
    	game.setPath(musicPath.get(index));
    	game.play();
        monsters.clear();
        //tutoriel = false;
        path = setPath();
        tower = new HashSet<Tower>();
        passiveTower = new HashSet<PassiveTower>();
        waitingMonster.clear();
        projectiles.clear();
        started = false;
        life = cheat ? 1000000000 : 20;
        end = false;
        gold = cheat ? 1000000000 : 200;
        while (!end) {

            tick++;
            if (tick > 10)
                tick = 0;
            StdDraw.clear();
            if (StdDraw.hasNextKeyTyped()) {
                keyPress(StdDraw.nextKeyTyped());
            }

            if (StdDraw.isMousePressed()) {
                mouseClick(StdDraw.mouseX(), StdDraw.mouseY());
            }

            if (update() <= 0) {
                end = true;
                StdDraw.picture(0.5, 0.5, "src\\images\\gameOver.png", 1, 1);
                StdDraw.show();
                StdDraw.pause(3000);
                game.stop();
                menu.play();
            }
            StdDraw.show();
            StdDraw.pause(20);
        }
        if(life!=0) {
        	StdDraw.picture(0.5, 0.5, "src\\images\\you win.png", 1, 1);
        	StdDraw.show();
        	StdDraw.pause(3000);
        	game.stop();
        	menu.play();
        }
    }

    // -----------------------------------MENU---------------------------------------
    /**
     * Fonction qui gere le menu
     */
    public void menu() {
        StdDraw.pause(1000);
    	menu.play();
        monsters.clear();
        //tutoriel = false;
        path = setPath();
        tower = new HashSet<Tower>();
        passiveTower = new HashSet<PassiveTower>();
        waitingMonster.clear();
        projectiles.clear();
        started = false;
        quitteMenu = false;
        while (!quitteMenu) {
            StdDraw.clear();
            if (StdDraw.hasNextKeyTyped()) {
                if (StdDraw.nextKeyTyped() == 'p') {
                    cheat = !cheat;
                }
            }
            if (StdDraw.isMousePressed()) {
                mouseClickMenu(StdDraw.mouseX(), StdDraw.mouseY());
            }
            updateMenu();
            StdDraw.show();
            StdDraw.pause(20);
        }
    }
    // ---------------------------------TUTORIEL-------------------------------------
    /**
     * Fonction principale du tutoriel
     */
    public void tuto() {
        StdDraw.pause(1000);
    	tuto.play();
        int etapeTuto = 1;
        tower = new HashSet<Tower>();
        passiveTower = new HashSet<PassiveTower>();
        path = setPathTuto(etapeTuto);
        waitingMonster.clear();
        vagueTuto(etapeTuto);
        end = false;
        while (!end) {

            tick++;
            if (tick > 10)
                tick = 0;
            StdDraw.clear();
            updateTuto(etapeTuto);
            if (waitingMonster.isEmpty() && monsters.isEmpty()) {
                updateTuto(etapeTuto);
                StdDraw.pause(300);
                etapeTuto++;
                path = setPathTuto(etapeTuto);
                vagueTuto(etapeTuto);
                towerTuto(etapeTuto);
                if (etapeTuto > 15)
                    end = true;
            }
            StdDraw.show();
            StdDraw.pause(20);
        }
        tuto.stop();
        menu.play();
    }    
    // ---------------------------------OPTIONS--------------------------------------
    public void options() {
        StdDraw.pause(1000);
    	quitteMenu=true;
    	quitteOptions=false;
    	while(!quitteOptions) {
    		StdDraw.clear();
    		if (StdDraw.isMousePressed()) {
                mouseClickOption(StdDraw.mouseX(), StdDraw.mouseY());
            }
    		updateOption();
    		StdDraw.show();
            StdDraw.pause(20);
    	}
    }
    // -----------------------------------AIDE----------------------------------------
    public void aide(int niv) {
    	quitteAide=false;
    	while(!quitteAide) {
    		StdDraw.clear();
    		if (StdDraw.isMousePressed()) {
                mouseClickAide(StdDraw.mouseX(), StdDraw.mouseY(), niv);
            }
    		updateAide(niv);
    		StdDraw.show();
            StdDraw.pause(20);
    	}
    }
    	

}
