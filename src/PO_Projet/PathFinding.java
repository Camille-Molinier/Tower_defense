//Source : https://fr.wikipedia.org/wiki/Algorithme_de_Dijkstra
//L'algorithme a été 100% adapté pour le projet par notre groupe

package PO_Projet;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class PathFinding {

    //-------------------------------Fonctions utiles-------------------------------
    /**
     * Fonction qui determine l'ensemble des voisins
     * @param Q Un ensemble de position
     * @param s La position d'origine
     * @param height La largeur d'une case
     * @param weight La heuteur d'une case
     * @return Toute les cases dans Q qui sont voisine de s (a coté)
     */
    private static Set<Position> Voisin(Set<Position> Q, Position s , double height, double weight){
        Set<Position> copie = new HashSet<Position>(Q);
        Iterator<Position> i = copie.iterator();
		while (i.hasNext()) {
            Position p = i.next();
            double dx = Math.abs(s.x - p.x);
            double dy = Math.abs(s.y - p.y);
            if (dx > weight * 1.01 || dy > height * 1.01) i.remove();
            else if (dx > weight/2 && dy > height/2 && dx <= weight * 1.01 && dy <= height * 1.01) i.remove();
        }
        return copie;
    }
    // -------------------------------------------------------------------------------
    /**
     * Fonction qui determine la position avec la distance la plus petite par rapport au départ
     * @param chemins Un ensemble de position
     * @param distance Une table de correspondance qui relie une position et un double
     * @return La position avec la distance la plus petite
     */
    private static Position Trouve_min(Set<Position> chemins, Map<Position, Double> distance){
        double mini = 999;
        Position sommet = null;
        for(Position p : chemins){
            if(distance.get(p) < mini){
                mini = distance.get(p);
                sommet = p;
            }
        }
        return sommet;
    }
    // -------------------------------------------------------------------------------
    /**
     * Fonction qui met à jour les distances par rapport au départ
     * @param s1 Une position
     * @param s2 Une position
     * @param distance Une table de correspondance qui relie une position et un double
     * @param prec Une table de correspondance qui relie une position et un position
     */
    private static void Maj_distance(Position s1, Position s2, Map<Position, Double> distance, Map<Position, Position> prec){
        if (distance.get(s2) > (distance.get(s1) + 1)){
            distance.put(s2, distance.get(s1) + 1);
            prec.put(s2, s1);
        }
    }
    // -------------------------------------------------------------------------------
    /**
     * Fonction qui renvoit la position ayant les meme coordonées que fin dans l'ensemble chemin
     * @param chemin Un ensemble de position
     * @param fin Une position
     * @return La position dans chemins avec les meme coordonées que fin
    */
    private static Position TrouveFin (Set<Position> chemin, Position fin){
        for(Position p : chemin){
            if (p.equals(fin)){
                return p;
            }
        }
        return null;
    }
    
    //---------------------------------Initialisation--------------------------------
    /**
     * Fonction d'initailisation de l'algorithme
     * @param chemin l'ensemble des chemins possibles
     * @param depart la position de depart
     * @return Une table de corespondance qui pour chanque position dans chemin et départ relie 0
     */
    private static Map<Position, Double> Initialisation(Set<Position> chemins, Position depart){
        Map<Position, Double> distance = new HashMap<Position, Double>();
        for(Position p : chemins) distance.put(p, 999.0);
        distance.put(depart, 0.0);
        return distance;
    }
    
    //-----------------------------Fonction Princiaple------------------------------
    /**
     * Fonction qui génere le chemin à prendre pour aller d'un position à une autre le plus rapidement possible
     * @param chemins Un ensemble de position qui est possible de traverser
     * @param depart La position de départ
     * @param fin La position finale (l'objectif)
     * @param height La hauteur d'un case
     * @param weight La Largeur d'une case
     * @return La liste des positions (dans l'ordre) à parcourir
     */
    public static List<Position> Algorithme_de_Dijkstra(Set<Position> chemins, Position depart,Position fin ,double height, double weight){
        Map<Position, Double> distance = Initialisation(chemins, depart);
        Map<Position, Position> prec = new HashMap<Position, Position>();
        Set<Position> Q = new HashSet<Position>(chemins);
        Q.add(depart);
        Position finQ = TrouveFin(chemins, fin);
        Position s1 = null;
        while(!Q.isEmpty()){
            s1 = Trouve_min(Q, distance);
            Q.remove(s1);
            for(Position s2 : Voisin(Q, s1, height, weight)){
                Maj_distance(s1, s2, distance, prec);
            }
        }
        List<Position> cheminFinal = new ArrayList<Position>();
        s1 = finQ;
        while(!s1.equals(depart)){
            cheminFinal.add(0, s1);
            s1 = prec.get(s1);
        }
        return cheminFinal;
    }
}