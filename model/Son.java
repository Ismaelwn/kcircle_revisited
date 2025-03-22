package model;

import javax.sound.sampled.*;
import java.io.File;



public class Son { // j'ai créé cette classe pour plus facilement utiliser mes sound effects
    private Clip clip; // attribut de ma classe Son
    public static Son music = new Son("Projet_IW/src/model/musics/music.wav");
    public static Son son = new Son("Projet_IW/src/model/musics/degats.wav");
    public static Son gameover = new Son("Projet_IW/src/model/musics/game_over.wav");
    public Son(String AudioRoad) { // on construit le futur sound effect a partir d'un fichier .wav
        try {
            File audPath = new File(AudioRoad);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audPath);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void jouer(boolean rejouer) { //
        if (clip != null && !clip.isRunning()) {  // le param rejouer sert a savoir si on continue d'executer le son apres que l'on ai completement joué le son
            clip.setFramePosition(0);
            clip.start();
        }
        if (rejouer) { // si le boolean est true le son s'arrete donc jamais
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void arreter() { // une methode pour arreter le son
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public static void main(String[] args) {
        Son x = new Son("model/musics/music.wav");
        x.jouer(true);
        while (true){
            System.out.println("test");
        }
    }
}