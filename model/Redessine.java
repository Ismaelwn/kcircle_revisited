package model;
import view.Affichage;
import main.Main;



public class Redessine extends Thread {
    private Affichage monaffichage;
    public static final int delay = 50; //délai constant en ms

    public Redessine(Affichage affichage) {
        this.monaffichage = affichage;
    }

    @Override
    public void run() {
        while (true) {
            if (!Main.estEnPause()) {
                monaffichage.revalidate();
                monaffichage.repaint();
            }
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}