// Avancement.java
package model;
import model.Position;
import main.Main;


public class Avancement extends Thread {
    private Position pos;
    private static final int DELAY = 101, AVANCEMENT = 3;

    public Avancement(Position pos) {
        this.pos = pos;
    }

    @Override
    public void run() {

        while (true) {
            if (!Main.estEnPause()) {
                this.pos.setAvancement(this.pos.getAvancement() + AVANCEMENT);
            }
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}