package model;
import model.Position;
import main.Main;


public class Descendre extends Thread {
    private Position position;
    public static final int DELAY = 100;

    public Descendre(Position position) {
        this.position = position;
    }

    @Override
    public void run() {
        while (true) {
            if (!Main.estEnPause()) {
                position.move();
            }
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}