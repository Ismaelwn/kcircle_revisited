package view;


import java.awt.*;
import view.Affichage;

public class Animation implements Runnable {
    private Affichage affichage;
    private volatile boolean iSrunning = true;

    public Animation(Affichage affichage) {
        this.affichage = affichage;
    }

    public void stop() {
        iSrunning = false;
    }

    @Override
    public void run() {
        try {
            Color[] tmp = {Color.orange, Color.RED};
            while (iSrunning) {
                for (Color color : tmp) {
                    if (!iSrunning) break;
                    affichage.setBackground(color);
                    Thread.sleep(25);
                }
            }
            affichage.setBackground(Color.orange);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void gameover() {
        affichage.setBackground(Color.BLACK);
        iSrunning = false;
        model.Son.gameover.jouer(false);
    }

}