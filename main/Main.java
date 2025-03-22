package main;

import model.Descendre;
import model.Redessine;
import control.ReactionClic;
import model.Position;
import model.Parcours;
import model.Avancement;
import view.Affichage;
import model.Son;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Main {
    private static volatile boolean jeuEnPause = false;
    private static Timer minuteur;
    private static int compteARebours = 3;
    private static Thread threadRedessine;
    private static Thread threadDescente;
    private static Thread threadAvancement;
    private static JFrame fenetrePrincipale;
    private static JButton boutonPause;
    private static JButton boutonJouer;
    private static JButton boutonRedemarrer;

    public static void main(String[] args) {
        Position positionActuelle = new Position();
        Parcours parcoursActuel = new Parcours(positionActuelle);

        fenetrePrincipale = new JFrame("kcircle");
        fenetrePrincipale.setIconImage((new ImageIcon(new ImageIcon("model/images/kcirclelogo.png").getImage().getScaledInstance(40,40,Image.SCALE_DEFAULT))).getImage());
        fenetrePrincipale.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        model.Son.music.jouer(true);
        Affichage affichageJeu = new Affichage(positionActuelle, parcoursActuel);

        Redessine tacheRedessine = new Redessine(affichageJeu);
        Descendre tacheDescente = new Descendre(positionActuelle);
        Avancement tacheAvancement = new Avancement(positionActuelle);

        ReactionClic gestionClics = new ReactionClic(positionActuelle, tacheRedessine, tacheDescente, tacheAvancement);
        affichageJeu.addMouseListener(gestionClics);

        fenetrePrincipale.add(affichageJeu, BorderLayout.CENTER);

        JPanel panneauControle = new JPanel();

        boutonPause = new JButton("Pause");
        boutonJouer = new JButton("Jouer");
        boutonRedemarrer = new JButton("Relancer");

        boutonPause = new JButton("Pause");
        boutonJouer = new JButton("Play");
        boutonRedemarrer = new JButton("Restart");

        boutonPause.setBorderPainted(false);
        boutonPause.setContentAreaFilled(false);
        boutonPause.setFocusPainted(false);

        boutonJouer.setBorderPainted(false);
        boutonJouer.setContentAreaFilled(false);
        boutonJouer.setFocusPainted(false);

        boutonRedemarrer.setBorderPainted(false);
        boutonRedemarrer.setContentAreaFilled(false);
        boutonRedemarrer.setFocusPainted(false);

        boutonPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jeuEnPause = true;
                mettreAJourVisibiliteBoutons();
                model.Son.music.arreter();
            }
        });

        boutonJouer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                compteARebours = 3;
                minuteur.start();
                mettreAJourVisibiliteBoutons();
                model.Son.music.jouer(true);
            }
        });

        boutonRedemarrer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fenetrePrincipale.dispose();
                Main.main(new String[]{});
                model.Son.music.jouer(true);
            }
        });

        panneauControle.add(boutonPause);
        panneauControle.add(boutonJouer);
        panneauControle.add(boutonRedemarrer);

        fenetrePrincipale.add(panneauControle, BorderLayout.SOUTH);

        minuteur = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (compteARebours > 0) {
                    System.out.println(compteARebours);
                    compteARebours--;
                } else {
                    minuteur.stop();
                    jeuEnPause = false;
                    mettreAJourVisibiliteBoutons();
                }
            }
        });

        fenetrePrincipale.pack();
        fenetrePrincipale.setVisible(true);

        threadRedessine = new Thread(tacheRedessine);
        threadDescente = new Thread(tacheDescente);
        threadAvancement = new Thread(tacheAvancement);

        mettreAJourVisibiliteBoutons();
    }

    public static boolean estEnPause() {
        return jeuEnPause;
    }

    public static void mettreEnPause() {
        jeuEnPause = true;
        mettreAJourVisibiliteBoutons();
    }

    public static void afficherBoutonRedemarrer() {
        mettreEnPause();
        boutonRedemarrer.setVisible(true);
        boutonJouer.setVisible(false);
        boutonPause.setVisible(false);
    }

    private static void mettreAJourVisibiliteBoutons() {
        if (jeuEnPause) {
            boutonJouer.setVisible(true);
            boutonPause.setVisible(false);
            boutonRedemarrer.setVisible(true);
        } else {
            boutonJouer.setVisible(false);
            boutonPause.setVisible(true);
            boutonRedemarrer.setVisible(false);
        }
    }
}
