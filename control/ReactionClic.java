// ReactionClic.java
package control;

import model.Position;
import model.Redessine;
import model.Descendre;
import model.Avancement;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ReactionClic extends MouseAdapter {
    private Position pos;
    private Redessine redraw;
    private Descendre drop_down;
    private Avancement avancée;
    private boolean threads_on = false;

    // Constructeur prenant un objet Position et les threads en paramètre
    public ReactionClic(Position position_, Redessine redessine_, Descendre descendre_, Avancement avancement_) {
        this.pos = position_;
        this.redraw = redessine_;
        this.drop_down = descendre_;
        this.avancée = avancement_;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        pos.jump(); // change la vitesse pour permettre un saut
        if (!threads_on) {
            redraw.start();
            drop_down.start();
            avancée.start();
            threads_on = true;
        }
    }
}