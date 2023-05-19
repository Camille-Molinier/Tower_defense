package PO_Projet;

import java.io.File;
import javax.sound.sampled.*;

public class Music {
	// -----------------------------------Attributs----------------------------------
	// Fichier contenant la musique
	private File file;
	// AudioInputStream pour aller en parametre du Clip
	private AudioInputStream audio;
	// Clip avec lequel on va pouvoir manipuler le fichier
	public Clip clip;
	// Indique si la musique est en cours de lecture
	private boolean play;

	// ---------------------------------Constructeur---------------------------------
	/**
	 * Classe permetant de jouer des musiques
	 */
	public Music(String path) {
		play = false;
		// Try/Catch super basique : On tente la fonction et si ca ne marche pas on
		// retourne l'erreur pour l'afficher dans la console
		try {
			this.file = new File(path);
			this.audio = AudioSystem.getAudioInputStream(file);
			this.clip = AudioSystem.getClip();
			clip.open(audio);
		} catch (Exception e) {
		}

	}

	// ------------------------------------------------------------------------------
	/**
	 * Constructeur vide utile pour les musiques aléatoires
	 */
	public Music() {
		/* ... */}

	// -------------------------------Getters/Setters--------------------------------
	/**
	 * Modifie le chemin d'acsès au ficher (modifie la musique)
	 * 
	 * @param path
	 */
	public void setPath(String path) {
		try {
			this.file = new File(path);
			this.audio = AudioSystem.getAudioInputStream(file);
			this.clip = AudioSystem.getClip();
			clip.open(audio);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// ---------------------------------Fonctions------------------------------------
	/**
	 * renvoie si la musique est en cour de lecture
	 * 
	 * @return
	 */
	public boolean isPLay() {
		return play;
	}

	// ------------------------------------------------------------------------------
	/**
	 * Fonction pour lancer la lecture en boucle de la musique
	 */
	public void play() {
		try {
			if (file != null) {
				play = true;
				clip.start();
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
		} catch (Exception e) {
		}

	}

	// ------------------------------------------------------------------------------
	/**
	 * Fonction pour stopper la lecture de la musique
	 * 
	 * @Note : Remet la musique au début
	 */
	public void stop() {
		try {
			if (file != null) {
				play = false;
				clip.stop();
				clip.setFramePosition(0);
			}
		} catch (Exception e) {
		}

	}

}
