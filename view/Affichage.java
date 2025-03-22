package view;


import main.Main;
import model.Position;
import model.Parcours;
import view.Animation;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Iterator;

public class Affichage extends JPanel {
    public static final int LARGEUR = 50;
    private int RATIO_X = 3;
    private int RATIO_Y = 5;
    private Position pos;
    private int pos_x, pos_y;
    private Parcours parcours_;


    private boolean collisionFinded = false;
    private int collision_c = 0;
    private int score = -2; // je ne sais pas pourquoi mais le thread de compteur de debuts de partie compte une collision en plus mais pas mon compteur de collision
    private Thread cThread;
    private view.Animation animation;
    private model.Collisions collisions;
    private int coef_score = 100;
    public static Color animbarre;
    private int vie_depart;
    private int malus = 40;

    public Affichage(Position position, Parcours parcours) {
        this.animbarre = Color.white;
        this.pos = position;
        this.parcours_ = parcours;
        this.collisions = new model.Collisions(position, parcours, RATIO_X, RATIO_Y);
        int largeur = (this.pos.BEFORE + this.pos.AFTER);
        int hauteur = (this.pos.HAUTEUR_MAX - this.pos.HAUTEUR_MIN);
        this.vie_depart = position.getHp();
        nouvellePositionOvale();
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                RATIO_Y = getHeight() / hauteur;
                RATIO_X = getWidth() / largeur;
                nouvellePositionOvale();
            }
        });
        setBackground(Color.orange);
        setPreferredSize(new Dimension(largeur * RATIO_X, hauteur * RATIO_Y));
    }



    public void dessineParcours(Graphics g) {
        Graphics2D tmp = (Graphics2D) g;
        ArrayList<Point> LigneBrisee = this.parcours_.getPoints();
        Iterator<Point> iterator = LigneBrisee.iterator(); //copilote
        if (iterator.hasNext()) {
            Point pModele = iterator.next();
            Point pVue = ModeleToVuePoint(pModele);
            int x = pVue.x, y = pVue.y;
            while (iterator.hasNext()) {
                pModele = iterator.next();
                pVue = ModeleToVuePoint(pModele);
                int x2 = pVue.x, y2 = pVue.y;
                tmp.drawLine(x, y, x2, y2);
                x = x2;
                y = y2;
            }
        }
    }

    public void nouvellePositionOvale() {
        Point POVmodele = new Point(this.pos.BEFORE, this.pos.getHeight() + this.pos.HAUTEUR_OVALE / 2);
        Point POVvue = ModeleToVuePoint(POVmodele);
        this.pos_x = POVvue.x - LARGEUR / 2;
        this.pos_y = POVvue.y;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        nouvellePositionOvale();
        Graphics2D tmp = (Graphics2D) g;
        float lineThickness = 2.0f;
        tmp.setStroke(new BasicStroke(lineThickness));
        tmp.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        tmp.setColor(Color.BLACK);
        tmp.drawArc(this.pos_x, this.pos_y, LARGEUR, this.pos.HAUTEUR_OVALE * RATIO_Y, 90, 180);
        tmp.setColor(Color.RED);
        dessineParcours(g);
        score += 2 + collision_c * ( this.vie_depart - pos.getHp() ) / coef_score;
        tmp.setColor(Color.BLACK);
        tmp.drawArc(this.pos_x, this.pos_y, LARGEUR, this.pos.HAUTEUR_OVALE * RATIO_Y, 270, 180);
        if (collisions.isCollision()) {
            if (!collisionFinded) {
                collisionFinded = true;
                collision_c++;
                score-=malus;
                pos.degatsCollision();
                animation = new Animation(this);
                cThread = new Thread(animation);
                cThread.start();
            }
        } else {
            if (collisionFinded) {
                collisionFinded = false;
                if (animation != null) {
                    animation.stop();
                }
            }
        }

        tmp.setColor(Color.BLACK);
        tmp.drawString("Collisions: " + collision_c, 10, getHeight()-20);
        tmp.drawString("Score : " + score, 10, getHeight()-5);

        tmp.setColor(animbarre);
        tmp.fillRect(10, 10, vie_depart, 18);

        tmp.setColor(Color.RED);
        tmp.fillRect(10, 10, pos.getHp(), 18);



        tmp.setColor(Color.BLACK);
        tmp.drawRect(10, 10, vie_depart, 18);

        if (pos.isOver()) {
            tmp.setColor(Color.RED);
            tmp.setFont(new Font("Serif", Font.BOLD, 50));
            tmp.drawString("FIN DU JEU", getWidth() / 2 - 150, getHeight() / 2);
            tmp.setColor(Color.RED);
            tmp.setFont(new Font("Serif", Font.BOLD, 25));
            tmp.drawString("Score : " + score, getWidth()/2-70, getHeight()/2+40);
            animation.gameover();

            Main.afficherBoutonRedemarrer();
        }
    }

    public Point ModeleToVuePoint(Point pointModele) {
        int x = pointModele.x * RATIO_X;
        int y = (Position.HAUTEUR_MAX - pointModele.y) * RATIO_Y;
        return new Point(x, y);
    }
}