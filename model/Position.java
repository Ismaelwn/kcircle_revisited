package model;


public class Position {
    private int hauteur = 0;

    public static final int saut = 3;
    public static final int gravite = 2;
    private int avancee = 0;
    public static final int HAUTEUR_OVALE = 19;
    public static final int HAUTEUR_MAX = 50, HAUTEUR_MIN = -20;
    public static final int BEFORE = 50, AFTER = 200;
    private int vtss = 1;

    //mes ajouts : Attributs
    private int hp = 125; // Moved life variable here
    private int facteur_degats = 12;


    public void jump() {
        vtss = saut;
    }

    public int getHeight() {
        return hauteur;
    }

    public void move() {
        if (hauteur > HAUTEUR_MIN || vtss > 0) {
            hauteur += vtss;
            vtss -= gravite;
            if (hauteur < HAUTEUR_MIN + HAUTEUR_OVALE / 2) {
                hauteur = HAUTEUR_MIN + HAUTEUR_OVALE / 2;
                vtss = 0;
            }
            if (hauteur > HAUTEUR_MAX - HAUTEUR_OVALE / 2) {
                hauteur = HAUTEUR_MAX - HAUTEUR_OVALE / 2;
                vtss = 0;
            }
        }
    }

    public int getAvancement() {
        return avancee;
    }

    public void setAvancement(int avancement) {
        this.avancee = avancement;
    }

    public int getHp() {
        return hp;
    }
    public boolean isOver() {
        return hp <= 0;
    }

    public void setHp(int life) {
        this.hp = life;
    }
    public void degatsCollision() {
        setHp(getHp() - facteur_degats);
    }

}