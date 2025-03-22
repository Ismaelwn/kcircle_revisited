package model;
import model.Position;

import java.awt.*;
import java.util.*;

public class Parcours {

    public static final java.util.Random RANDOM = new java.util.Random();
    public static final int X_MIN = 10;
    public static final int X_MAX = 50;
    private Position pos;
    //arraylist de points
    private ArrayList<Point> points = new ArrayList<Point>();

    public Parcours(Position position){
        this.pos = position;
        this._init_();
    }

    public boolean generePoints(){
        Point tmp = this.points.get(this.points.size()-1);
        return tmp.x-this.pos.getAvancement() < this.pos.AFTER+this.pos.BEFORE*4;
    }



    public Point genererPointSuivant(Point point){
        int x = this.points.get(this.points.size()-1).x + RANDOM.nextInt(X_MAX - X_MIN) + X_MIN;
        int y = this.pos.HAUTEUR_MIN +15 + RANDOM.nextInt(this.pos.HAUTEUR_MAX - this.pos.HAUTEUR_MIN-45);
        return new Point(x, y);
    }
     public ArrayList<Point> getPoints(){
        ArrayList<Point> tmp = new ArrayList<Point>();
        if (inTheRange(this.points.get(1))) {
            this.points.remove(0);
        }
         if (this.generePoints()){
            Point old_ = this.points.get(this.points.size()-1);
            Point new_ = this.genererPointSuivant(old_);
            this.points.add(new_);
        }

        for (Point point : this.points){
            tmp.add(new Point(point.x - this.pos.getAvancement(), point.y));
        }
        return tmp;

    }


    public void _init_(){
        Point point = new Point(0, this.pos.getHeight());
        this.points.add(point);
        point = new Point(this.pos.AFTER, this.pos.getHeight());
        this.points.add(point);
        while (this.generePoints()){
            point = this.genererPointSuivant(point);
            this.points.add(point);
        }
    }


    public boolean inTheRange(Point point){
        return point.x < this.pos.BEFORE;
    }





}
