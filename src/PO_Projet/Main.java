/*************************************************************************************/
/* Projet PO : Tower Defense														 */
/*																					 */
/* Binome : Léo-paul Huar / Camille Molinier										 */
/*																					 */
/*																					 */
/* Version : 1.13																	 */
/*																					 */
/* Statut : OK																 */
/*************************************************************************************/
package PO_Projet;

public class Main {

	public static void main(String[] args){
		// ---------------------------------Parametrage---------------------------------
		int width = 1200;
		int height = 800;
		int nbSquareX = 11;
		int nbSquareY = 11;

		// ------------------------------Création du monde------------------------------
		World w = new World(width, height, nbSquareX, nbSquareY);

		// -------------------Lancement de la boucle principale du jeu------------------
		w.menu();
	}
}