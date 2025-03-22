package model;

import java.awt.*;
import java.util.ArrayList;
import model.Collisions;
import model.Position;
import model.Parcours;


public class Collisions {
    private Position pos;
    private Parcours prcrs;
    private int RATIO_X;
    private int RATIO_Y;
    private ArrayList<Point> points_;

    public Collisions(Position position, Parcours parcours, int RATIO_X, int RATIO_Y) {
        this.pos = position;
        this.prcrs = parcours;
        this.RATIO_X = RATIO_X;
        this.RATIO_Y = RATIO_Y;
    }

    public void MAJ(){
            this.points_ = prcrs.getPoints();
    }


    public Point getRencontreLigneOvale() {
        int ovalX = 50;
        for (int i = 0; i < this.points_.size(); i++) {
            Point p1 = this.points_.get(i);
            Point p2 = this.points_.get(i + 1);
            if (p1.x <= ovalX && p2.x >= ovalX) {
                int x = ovalX;
                int y = p1.y + (ovalX - p1.x) * (p2.y - p1.y) / (p2.x - p1.x);
                return new Point(x, y);
            }
        }
        return null;
    }

    public boolean isCollision() {
        MAJ();
        Point tmp = getRencontreLigneOvale();
        Point top = new Point(pos.getAvancement(), pos.getHeight() + Position.HAUTEUR_OVALE / 2);
        Point bottom = new Point(pos.getAvancement(), pos.getHeight() - Position.HAUTEUR_OVALE / 2);
        return (tmp != null && (tmp.y <= bottom.y || tmp.y >= top.y));
    }

}
