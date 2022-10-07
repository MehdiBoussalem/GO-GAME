package gui;
import jeudego.Intersection;
import jeudego.Joueur;
import jeudego.Minimax;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class Jeu extends JPanel {
    private PlateauGui plateau;
    private InvisibleButton[] buttons19 = new InvisibleButton[361];
    private InvisibleButton[] buttons13 = new InvisibleButton[169];
    private InvisibleButton[] buttons9 = new InvisibleButton[81];
    private InvisibleButton[][] plateu13 = new InvisibleButton[13][13];
    private InvisibleButton[][] plateau19= new InvisibleButton[19][19];
    private InvisibleButton[][] plateau9 = new InvisibleButton[9][9];
    private JLabel[] pionNoir = new JLabel[361];
    private JLabel[] pionBlanc = new JLabel[361];
    private JLabel[] pionOrange = new JLabel[361];
    private JLabel infoJ1 = new JLabel(); //= new JLabel("<html><p>JOUEUR 1</p><p>Nom: " + Fenetre.getJoueur1().getNom() + "</p> </html>");
    private JLabel infoJ2 = new JLabel(); //= new JLabel("<html><p>JOUEUR 2</p><p>Nom: " + Fenetre.getJoueur2().getNom() + "</p> </html>");
    private JLabel infoJ3 = new JLabel();// = new JLabel("<html><p>JOUEUR 3</p><p>Nom: " + Fenetre.getJoueur3().getNom() + "</p> </html>");
    private JLabel infoIa = new JLabel();
    private JLabel infoIa2 = new JLabel();
    private JLabel infoFinDePartie = new JLabel();
    private Minimax ia1 = new Minimax();

    private JLabel  megapionOrange = new JLabel(new ImageIcon("image/mega-pion-orange.png"), JLabel.CENTER);
    private JLabel  megapionNoir = new JLabel(new ImageIcon("image/mega-pion-noir.png"), JLabel.CENTER);
    private JLabel  megapionBlanc = new JLabel(new ImageIcon("image/mega-pion-blanc.png"), JLabel.CENTER);

    private MyButton passerTour = new MyButton("image/passer.png");
    private MyButton abandonner = new MyButton("image/abandonner.png");
    private MyButton sauvegarder = new MyButton("image/Sauvegarder.png");

    private MyButton megapion = new MyButton("image/megapion.png");

    private JLabel tempsJoueur = new JLabel();
    private Timer chrono;
    private Timer chronoPartie;
    private int timerJoueur = 30;
    private int seconde = 0;
    private int minute = 0;
    private int heure = 0;
    private JLabel tempsPartie = new JLabel();
    private ArrayList<Joueur> joueursList = new ArrayList<>();

    public Jeu(int taille, int nbJoueur, Joueur joueur1, Joueur joueur2, Joueur joueur3,jeudego.Plateau plateauTraitement) {
        setBackground(new Color(186, 138, 72));
        setLayout(null);
        joueursList.add(joueur1);
        joueursList.add(joueur2);
        joueursList.add(joueur3);
        if (taille ==9) {
            plateau = new PlateauGui(9);
            plateau.setBounds(499, 144, 800, 800);
            add(plateau);
            if (nbJoueur == 3) {
                infoJ1.setBounds(500, 0, 200, 150);
                infoJ2.setBounds(750, 0, 200, 150);
                infoJ3.setBounds(1000, 0, 200, 150);
                infoJ1.setText("<html><p>JOUEUR 1</p><p>Nom: " + joueur1.getNom() + "</p> </html>");
                infoJ2.setText("<html><p>JOUEUR 2</p><p>Nom: " + joueur2.getNom() + "</p> </html>");
                infoJ3.setText("<html><p>JOUEUR 3</p><p>Nom: " + joueur3.getNom() + "</p> </html>");
            } else if (nbJoueur == 2) {
                infoJ1.setBounds(500, 0, 200, 150);
                infoJ2.setBounds(750, 0, 200, 150);
                infoJ3.setBounds(1000, 0, 200, 150);
                infoJ1.setText("<html><p>JOUEUR 1</p><p>Nom: " + joueur1.getNom() + "</p> </html>");
                infoJ2.setText("<html><p>JOUEUR 2</p><p>Nom: " + joueur2.getNom() + "</p> </html>");
                infoJ3.setText("<html><p>ORDINATEUR</p><p>Nom: " + "IA1" + "</p> </html>");


            } else {
                infoJ1.setBounds(500, 0, 200, 150);
                infoJ2.setBounds(750, 0, 200, 150);
                infoJ3.setBounds(1000, 0, 200, 150);
                infoJ1.setText("<html><p>JOUEUR 1</p><p>Nom: " + joueur1.getNom() + "</p> </html>");
                infoJ2.setText("<html><p>ORDINATEUR</p><p>Nom: " + "IA1" + "</p> </html>");
                infoJ3.setText("<html><p>ORDINATEUR</p><p>Nom: " + "IA2" + "</p> </html>");
            }
            for (int i = 0; i < 81; i++) {
                buttons9[i] = new InvisibleButton();

                pionOrange[i] = new JLabel(new ImageIcon("image/pion-orange.png"), JLabel.CENTER);
                pionBlanc[i] = new JLabel(new ImageIcon("image/pion-blanc.png"), JLabel.CENTER);
                pionNoir[i] = new JLabel(new ImageIcon("image/pion-noir.png"), JLabel.CENTER);

                add(pionOrange[i]);
                add(buttons9[i]);
                add(pionBlanc[i]);
                add(pionNoir[i]);


            }
            int i = 0;

            for (int k = (int) (1080 / 7.7); k <= 905; k = k + 88) {
                for (int j = 495; j <= 1240; j = j + 88) {
                    if (i >= 81) {
                        System.out.println("done" + i);

                        break;
                    }
                    buttons9[i].setBounds(j, k, 10, 10);
                    add(buttons9[i]);
                    i++;
                }

            }

            int compteur=0;
            for (int u = 0; u < 9; u++) {
                for (int j = 0; j < 9; j++) {
                    plateau9[u][j] = buttons9[compteur];
                    compteur++;
                    int pos1=u;
                    int pos2=j;
                    int numeroButton=compteur;
                    plateau9[u][j].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int x = plateau9[pos1][pos2].getX();
                            int y = plateau9[pos1][pos1].getY();
                            for (int i = 0; i<joueursList.size();i++){
                                if (joueursList.get(i).getPeutJouer()){
                                    joueursList.get(i).setPasser(false);
                                }
                            }

                            if (nbJoueur == 3) {
                                for (int i = 0; i < joueursList.size(); i++) {
                                    if (joueursList.get(i).getPeutJouer()) {
                                        if (joueursList.get(i).getJoueMegaPion()&& plateauTraitement.placerMegaPion(pos1,pos2,joueursList.get(i))){
                                            if (joueursList.get(i).getCouleur().equals("blanc")) {
                                                megapionBlanc.setBounds(x - 34, y - 25, 80, 60);
                                                plateau9[pos1][pos2].setEnabled(false);
                                                joueursList.get(i).setJoueMegapion(false);

                                            }
                                            if (joueursList.get(i).getCouleur().equals("noir")) {
                                                megapionNoir.setBounds(x - 34, y - 25, 80, 60);

                                                plateau9[pos1][pos2].setEnabled(false);
                                                joueursList.get(i).setJoueMegapion(false);
                                            }
                                            if (joueursList.get(i).getCouleur().equals("orange")) {
                                                megapionOrange.setBounds(x - 34, y - 25, 80, 60);

                                                plateau9[pos1][pos2].setEnabled(false);
                                                joueursList.get(i).setJoueMegapion(false);
                                            }
                                            chrono.stop();
                                            timerJoueur = 30;
                                            chrono.start();

                                            for (int x1 =0;x1<9;x1++){
                                                for (int y1=0; y1<9;y1++){
                                                    if (plateauTraitement.getTab()[x1][y1].getPion()==null && !getPlateau9(x1, y1).isEnabled() ){
                                                        getPlateau9(x1, y1).setEnabled(true);
                                                        pionBlanc[x1*9+y1].setBounds(6000,6000,36,36);
                                                        pionNoir[x1*9+y1].setBounds(6000,6000,36,36);
                                                        pionOrange[x1*9+y1].setBounds(6000,6000,36,36);

                                                    }
                                                }
                                            }


                                            joueursList.get(i).setPeutJouer(false);
                                            if (i == joueursList.size() - 1) {
                                                joueursList.get(0).setPeutJouer(true);
                                            } else {
                                                joueursList.get(i + 1).setPeutJouer(true);
                                            }
                                            System.out.println(plateauTraitement.toString());
                                            break;
                                        }
                                        else if (plateauTraitement.placer(pos1, pos2, joueursList.get(i))) {
                                            if (joueursList.get(i).getCouleur().equals("blanc")) {
                                                pionBlanc[numeroButton - 1].setBounds(x - 34, y - 25, 80, 60);
                                                plateau9[pos1][pos2].setEnabled(false);

                                            }
                                            if (joueursList.get(i).getCouleur().equals("noir")) {
                                                pionNoir[numeroButton - 1].setBounds(x - 34, y - 25, 80, 60);

                                                plateau9[pos1][pos2].setEnabled(false);
                                            }
                                            if (joueursList.get(i).getCouleur().equals("orange")) {
                                                pionOrange[numeroButton - 1].setBounds(x - 34, y - 25, 80, 60);

                                                plateau9[pos1][pos2].setEnabled(false);
                                            }
                                            chrono.stop();
                                            timerJoueur = 30;
                                            chrono.start();
                                            System.out.println(plateauTraitement.toString());
                                            for (int x1 =0;x1<9;x1++){
                                                for (int y1=0; y1<9;y1++){
                                                    if (plateauTraitement.getTab()[x1][y1].getPion()==null && !getPlateau9(x1, y1).isEnabled() ){
                                                        getPlateau9(x1, y1).setEnabled(true);
                                                        pionBlanc[x1*9+y1].setBounds(6000,6000,36,36);
                                                        pionNoir[x1*9+y1].setBounds(6000,6000,36,36);
                                                        pionOrange[x1*9+y1].setBounds(6000,6000,36,36);

                                                    }
                                                }
                                            }


                                            joueursList.get(i).setPeutJouer(false);
                                            if (i == joueursList.size() - 1) {
                                                joueursList.get(0).setPeutJouer(true);
                                            } else {
                                                joueursList.get(i + 1).setPeutJouer(true);
                                            }
                                            break;
                                        }

                                    }


                                }
                            }
                            else if (nbJoueur == 2) {
                                for (int i = 0; i < joueursList.size(); i++) {
                                    if (joueursList.get(i).getPeutJouer() && !joueursList.get(i).getNom().equals("IA")) {
                                        if (joueursList.get(i).getJoueMegaPion()&& plateauTraitement.placerMegaPion(pos1, pos2, joueursList.get(i))) {
                                            if (joueursList.get(i).getCouleur().equals("blanc")) {
                                                megapionBlanc.setBounds(x - 34, y - 25, 80, 60);
                                                plateau9[pos1][pos2].setEnabled(false);
                                                joueursList.get(i).setJoueMegapion(false);


                                            }
                                            if (joueursList.get(i).getCouleur().equals("noir")) {
                                                megapionNoir.setBounds(x - 34, y - 25, 80, 60);
                                                plateau9[pos1][pos2].setEnabled(false);
                                                joueursList.get(i).setJoueMegapion(false);

                                            }
                                            if (i==0) {
                                                if (joueursList.get(i + 1).getNom().equals("IA")) {

                                                    joueursList.get(i).setPeutJouer(false);
                                                    joueursList.get(i + 1).setPeutJouer(true);
                                                    ia1.bestMove(plateauTraitement,3);
                                                    //System.out.println("IA");
                                                    x = getPlateau9(ia1.getMove(), ia1.getMove2()).getX();
                                                    y = getPlateau9(ia1.getMove(), ia1.getMove2()).getY();
                                                    pionOrange[numeroButton-1].setBounds(x - 34, y - 25, 80, 60);
                                                    getPlateau9(ia1.getMove2(), ia1.getMove()).setEnabled(false);
                                                    joueursList.get(i + 1).setPeutJouer(false);
                                                    joueursList.get(0).setPeutJouer(true);

                                                    chrono.stop();
                                                    timerJoueur=30;
                                                    chrono.start();
                                                    System.out.println(plateauTraitement.toString());
                                                    break;
                                                }
                                                for (int x2 =0;x2<9;x2++){
                                                    for (int y2=0; y2<9;y2++){
                                                        if (plateauTraitement.getTab()[x2][y2].getPion()==null && !getPlateau9(x2, y2).isEnabled() ){
                                                            System.out.println(x2+" "+y2);
                                                            getPlateau9(x2, y2).setEnabled(true);

                                                            pionBlanc[x2*9+y2].setBounds(6000,6000,36,36);
                                                            pionNoir[x2*9+y2].setBounds(6000,6000,36,36);
                                                            pionOrange[x2*9+y2].setBounds(10000,10000,36,36);
                                                        }
                                                    }
                                                }
                                                joueursList.get(i).setPeutJouer(false);
                                                joueursList.get(i+1).setPeutJouer(true);
                                                chrono.stop();
                                                timerJoueur=30;
                                                chrono.start();
                                                System.out.println(plateauTraitement.toString());

                                                break;
                                            }
                                            if (i==1){
                                                if (i<joueursList.size()-1){
                                                    if (joueursList.get(i + 1).getNom().equals("IA")) {

                                                        joueursList.get(i).setPeutJouer(false);
                                                        joueursList.get(i + 1).setPeutJouer(true);
                                                        ia1.bestMove(plateauTraitement,3);
                                                        System.out.println("IA");
                                                        x = getPlateau9(ia1.getMove(), ia1.getMove2()).getX();
                                                        y = getPlateau9(ia1.getMove(), ia1.getMove2()).getY();
                                                        pionOrange[numeroButton-1].setBounds(x - 34, y - 25, 80, 60);
                                                        getPlateau9(ia1.getMove(), ia1.getMove2()).setEnabled(false);
                                                        joueursList.get(i + 1).setPeutJouer(false);
                                                        joueursList.get(0).setPeutJouer(true);
                                                        chrono.stop();
                                                        timerJoueur = 30;
                                                        chrono.start();

                                                        System.out.println(plateauTraitement.toString());
                                                        for (int x2 =0;x2<9;x2++){
                                                            for (int y2=0; y2<9;y2++){
                                                                if (plateauTraitement.getTab()[x2][y2].getPion()==null && !getPlateau9(x2, y2).isEnabled() ){
                                                                    System.out.println(x2+" "+y2);
                                                                    getPlateau9(x2, y2).setEnabled(true);

                                                                    pionBlanc[x2*9+y2].setBounds(6000,6000,36,36);
                                                                    pionNoir[x2*9+y2].setBounds(6000,6000,36,36);
                                                                    pionOrange[x2*9+y2].setBounds(10000,10000,36,36);
                                                                }
                                                            }
                                                        }
                                                        break;
                                                    }

                                                }
                                            }


                                            for (int x1 =0;x1<9;x1++){
                                                for (int y1=0; y1<9;y1++){
                                                    if (plateauTraitement.getTab()[x1][y1].getPion()==null && !getPlateau9(x1, y1).isEnabled() ){
                                                        getPlateau9(x1, y1).setEnabled(true);
                                                        pionBlanc[x1*9+y1].setBounds(6000,6000,36,36);
                                                        pionNoir[x1*9+y1].setBounds(6000,6000,36,36);
                                                        pionOrange[x1*9+y1].setBounds(6000,6000,36,36);

                                                    }
                                                }
                                            }
                                            System.out.println(plateauTraitement.toString());
                                            break;

                                        }
                                        else if ( plateauTraitement.placer(pos1,pos2,joueursList.get(i))) {
                                            if (joueursList.get(i).getCouleur().equals("blanc")) {
                                                pionBlanc[numeroButton - 1].setBounds(x - 34, y - 25, 80, 60);
                                                plateau9[pos1][pos2].setEnabled(false);

                                            }
                                            if (joueursList.get(i).getCouleur().equals("noir")) {
                                                pionNoir[numeroButton - 1].setBounds(x - 34, y - 25, 80, 60);
                                                plateau9[pos1][pos2].setEnabled(false);
                                            }
                                            if (i==0) {
                                                if (joueursList.get(i + 1).getNom().equals("IA")) {

                                                    joueursList.get(i).setPeutJouer(false);
                                                    joueursList.get(i + 1).setPeutJouer(true);
                                                    ia1.bestMove(plateauTraitement,3);
                                                    //System.out.println("IA");
                                                    x = getPlateau9(ia1.getMove(), ia1.getMove2()).getX();
                                                    y = getPlateau9(ia1.getMove(), ia1.getMove2()).getY();
                                                    pionOrange[numeroButton-1].setBounds(x - 34, y - 25, 80, 60);
                                                    getPlateau9(ia1.getMove(), ia1.getMove2()).setEnabled(false);
                                                    joueursList.get(i + 1).setPeutJouer(false);
                                                    joueursList.get(0).setPeutJouer(true);
                                                    System.out.println(plateauTraitement.toString());
                                                    for (int x2 =0;x2<9;x2++){
                                                        for (int y2=0; y2<9;y2++){
                                                            if (plateauTraitement.getTab()[x2][y2].getPion()==null && !getPlateau9(x2, y2).isEnabled() ){
                                                                System.out.println(x2+" "+y2);
                                                                getPlateau9(x2, y2).setEnabled(true);

                                                                pionBlanc[x2*9+y2].setBounds(6000,6000,36,36);
                                                                pionNoir[x2*9+y2].setBounds(6000,6000,36,36);
                                                                pionOrange[x2*9+y2].setBounds(10000,10000,36,36);
                                                            }
                                                        }
                                                    }
                                                    break;
                                                }
                                                joueursList.get(i).setPeutJouer(false);
                                                joueursList.get(i+1).setPeutJouer(true);
                                                System.out.println(plateauTraitement.toString());
                                                for (int x1 =0;x1<9;x1++){
                                                    for (int y1=0; y1<9;y1++){
                                                        if (plateauTraitement.getTab()[x1][y1].getPion()==null && !getPlateau9(x1, y1).isEnabled() ){
                                                            getPlateau9(x1, y1).setEnabled(true);
                                                            pionBlanc[x1*9+y1].setBounds(6000,6000,36,36);
                                                            pionNoir[x1*9+y1].setBounds(6000,6000,36,36);
                                                            pionOrange[x1*9+y1].setBounds(6000,6000,36,36);

                                                        }
                                                    }
                                                }
                                                break;
                                            }
                                            if (i==1){
                                                if (i<joueursList.size()-1){
                                                    if (joueursList.get(i + 1).getNom().equals("IA")) {

                                                        joueursList.get(i).setPeutJouer(false);
                                                        joueursList.get(i + 1).setPeutJouer(true);
                                                        ia1.bestMove(plateauTraitement,3);
                                                        System.out.println("IA");
                                                        x = getPlateau9(ia1.getMove(), ia1.getMove2()).getX();
                                                        y = getPlateau9(ia1.getMove(), ia1.getMove2()).getY();
                                                        pionOrange[numeroButton-1].setBounds(x - 34, y - 25, 80, 60);
                                                        getPlateau9(ia1.getMove(), ia1.getMove2()).setEnabled(false);
                                                        joueursList.get(i + 1).setPeutJouer(false);
                                                        joueursList.get(0).setPeutJouer(true);
                                                        chrono.stop();
                                                        timerJoueur = 30;
                                                        chrono.start();

                                                        System.out.println(plateauTraitement.toString());
                                                        for (int x2 =0;x2<9;x2++){
                                                            for (int y2=0; y2<9;y2++){
                                                                if (plateauTraitement.getTab()[x2][y2].getPion()==null && !getPlateau9(x2, y2).isEnabled() ){
                                                                    System.out.println(x2+" "+y2);
                                                                    getPlateau9(x2, y2).setEnabled(true);

                                                                    pionBlanc[x2*9+y2].setBounds(6000,6000,36,36);
                                                                    pionNoir[x2*9+y2].setBounds(6000,6000,36,36);
                                                                    pionOrange[x2*9+y2].setBounds(10000,10000,36,36);
                                                                }
                                                            }
                                                        }
                                                        break;

                                                    }

                                                }
                                            }
                                            System.out.println(plateauTraitement.toString());
                                            for (int x2 =0;x2<9;x2++){
                                                for (int y2=0; y2<9;y2++){
                                                    if (plateauTraitement.getTab()[x2][y2].getPion()==null && !getPlateau9(x2, y2).isEnabled() ){
                                                        System.out.println(x2+" "+y2);
                                                        getPlateau9(x2, y2).setEnabled(true);

                                                        pionBlanc[x2*9+y2].setBounds(6000,6000,36,36);
                                                        pionNoir[x2*9+y2].setBounds(6000,6000,36,36);
                                                        pionOrange[x2*9+y2].setBounds(10000,10000,36,36);
                                                    }
                                                }
                                            }
                                            break;
                                        }


                                    }


                                }
                            }
                            else{
                                if (joueur1.getJoueMegaPion()) {
                                    x = getPlateau9(pos1, pos2).getX();
                                    y = getPlateau9(pos1, pos2).getY();
                                    megapionBlanc.setBounds(x - 34, y - 25, 80, 60);
                                    plateauTraitement.placerMegaPion(pos1, pos2, joueur1);
                                    getPlateau9(pos1, pos2).setEnabled(false);
                                    joueur1.setJoueMegapion(false);


                                }
                                else {
                                    x = getPlateau9(pos1, pos2).getX();
                                    y = getPlateau9(pos1, pos2).getY();
                                    pionBlanc[numeroButton - 1].setBounds(x - 34, y - 25, 80, 60);
                                    plateauTraitement.placer(pos1, pos2, joueur1);
                                    getPlateau9(pos1, pos2).setEnabled(false);
                                    for (int x1 =0;x1<9;x1++){
                                        for (int y1=0; y1<9;y1++){
                                            if (plateauTraitement.getTab()[x1][y1].getPion()==null && !getPlateau9(x1, y1).isEnabled() ){
                                                getPlateau9(x1, y1).setEnabled(true);
                                                pionBlanc[x1*9+y1].setBounds(6000,6000,36,36);
                                                pionNoir[x1*9+y1].setBounds(6000,6000,36,36);
                                                pionOrange[x1*9+y1].setBounds(6000,6000,36,36);

                                            }
                                        }
                                    }
                                }
                                ia1.bestMove(plateauTraitement,2);
                                x = getPlateau9(ia1.getMove(), ia1.getMove2()).getX();
                                y = getPlateau9(ia1.getMove(), ia1.getMove2()).getY();
                                pionNoir[numeroButton-1].setBounds(x - 34, y - 25, 80, 60);
                                getPlateau9(ia1.getMove(), ia1.getMove2()).setEnabled(false);
                                ia1.bestMove(plateauTraitement,3);
                                x = getPlateau9(ia1.getMove(), ia1.getMove2()).getX();
                                y = getPlateau9(ia1.getMove(), ia1.getMove2()).getY();
                                pionOrange[numeroButton-1].setBounds(x - 34, y - 25, 80, 60);
                                getPlateau9(ia1.getMove(), ia1.getMove2()).setEnabled(false);




                                chrono.restart();
                                timerJoueur=30;


                            }


                        }
                    });
                }
            }
        }

        if (taille == 13) {
            plateau = new PlateauGui(13);
            plateau.setBounds(499, 144, 800, 800);
            add(plateau);
            if (nbJoueur == 3) {
                infoJ1.setBounds(500, 0, 200, 150);
                infoJ2.setBounds(750, 0, 200, 150);
                infoJ3.setBounds(1000, 0, 200, 150);
                infoJ1.setText("<html><p>JOUEUR 1</p><p>Nom: " + joueur1.getNom() + "</p> </html>");
                infoJ2.setText("<html><p>JOUEUR 2</p><p>Nom: " + joueur2.getNom() + "</p> </html>");
                infoJ3.setText("<html><p>JOUEUR 3</p><p>Nom: " + joueur3.getNom() + "</p> </html>");
            }
            else if (nbJoueur == 2) {
                infoJ1.setBounds(500, 0, 200, 150);
                infoJ2.setBounds(750, 0, 200, 150);
                infoJ3.setBounds(1000, 0, 200, 150);
                infoJ1.setText("<html><p>JOUEUR 1</p><p>Nom: " + joueur1.getNom() + "</p> </html>");
                infoJ2.setText("<html><p>JOUEUR 2</p><p>Nom: " + joueur2.getNom() + "</p> </html>");
                infoJ3.setText("<html><p>JOUEUR 3</p><p>Nom: " + "Ordinateur" + "</p> </html>");



            } else {
                infoJ1.setBounds(500, 0, 200, 150);
                infoJ2.setBounds(750, 0, 200, 150);
                infoJ3.setBounds(1000, 0, 200, 150);
                infoJ1.setText("<html><p>JOUEUR 1</p><p>Nom: " + joueur1.getNom() + "</p> </html>");
                infoJ2.setText("<html><p>JOUEUR 3</p><p>Nom: " + "Ordinateur1" + "</p> </html>");
                infoJ3.setText("<html><p>JOUEUR 3</p><p>Nom: " + "Ordinateur2" + "</p> </html>");
            }
            for (int i = 0; i < 169; i++) {
                buttons13[i] = new InvisibleButton();

                pionOrange[i] = new JLabel(new ImageIcon("image/pion-orange.png"), JLabel.CENTER);
                pionBlanc[i] = new JLabel(new ImageIcon("image/pion-blanc.png"), JLabel.CENTER);
                pionNoir[i] = new JLabel(new ImageIcon("image/pion-noir.png"), JLabel.CENTER);
                add(pionOrange[i]);
                add(buttons13[i]);
                add(pionBlanc[i]);
                add(pionNoir[i]);



            }

            int i = 0;

            for (int k = (int) (1080 / 7.7); k <= 905; k = k + 61) {
                for (int j = 495; j <= 1240; j = j + 61) {
                    if (i >= 169) {
                        break;
                    }
                    buttons13[i].setBounds(j, k, 10, 10);
                    add(buttons13[i]);
                    i++;
                }


            }
            int compteur=0;
            for (int u = 0; u < 13; u++) {
                for (int j = 0; j < 13; j++) {
                    plateu13[u][j] = buttons13[compteur];
                    compteur++;
                    int pos1=u;
                    int pos2=j;
                    int numeroButton=compteur;
                    plateu13[u][j].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int x = plateu13[pos1][pos2].getX();
                            int y = plateu13[pos1][pos1].getY();

                            if (nbJoueur == 3) {
                                for (int i = 0; i < joueursList.size(); i++) {
                                    if (joueursList.get(i).getPeutJouer()) {
                                        if (joueursList.get(i).getPeutJouer()) {
                                            if (joueursList.get(i).getJoueMegaPion()&& plateauTraitement.placerMegaPion(pos1, pos2, joueursList.get(i))) {
                                                if (joueursList.get(i).getCouleur().equals("blanc")) {
                                                    megapionBlanc.setBounds(x - 34, y - 25, 80, 60);
                                                    plateu13[pos1][pos2].setEnabled(false);
                                                    joueursList.get(i).setJoueMegapion(false);

                                                }
                                                if (joueursList.get(i).getCouleur().equals("noir")) {
                                                    megapionNoir.setBounds(x - 34, y - 25, 80, 60);
                                                    plateu13[pos1][pos2].setEnabled(false);
                                                    joueursList.get(i).setJoueMegapion(false);
                                                }
                                                if (joueursList.get(i).getCouleur().equals("orange")) {
                                                    megapionOrange.setBounds(x - 34, y - 25, 80, 60);
                                                    plateu13[pos1][pos2].setEnabled(false);
                                                    joueursList.get(i).setJoueMegapion(false);
                                                }
                                                chrono.stop();
                                                timerJoueur = 30;
                                                chrono.start();


                                                joueursList.get(i).setPeutJouer(false);
                                                if (i == joueursList.size() - 1) {
                                                    joueursList.get(0).setPeutJouer(true);
                                                } else {
                                                    joueursList.get(i + 1).setPeutJouer(true);
                                                }
                                                for (int x1 =0;x1<13;x1++){
                                                    for (int y1=0; y1<13;y1++){
                                                        if (plateauTraitement.getTab()[x1][y1].getPion()==null && !getPlateu13(x1, y1).isEnabled() ){
                                                            getPlateu13(x1, y1).setEnabled(true);
                                                            pionBlanc[x1*13+y1].setBounds(6000,6000,36,36);
                                                            pionNoir[x1*13+y1].setBounds(6000,6000,36,36);
                                                            pionOrange[x1*13+y1].setBounds(6000,6000,36,36);

                                                        }
                                                    }
                                                }
                                                break;
                                            } else if (plateauTraitement.placer(pos1, pos2, joueursList.get(i))) {
                                                if (joueursList.get(i).getCouleur().equals("blanc")) {
                                                    pionBlanc[numeroButton - 1].setBounds(x - 34, y - 25, 80, 60);

                                                    plateu13[pos1][pos2].setEnabled(false);

                                                }
                                                if (joueursList.get(i).getCouleur().equals("noir")) {
                                                    pionNoir[numeroButton - 1].setBounds(x - 34, y - 25, 80, 60);
                                                    plateu13[pos1][pos2].setEnabled(false);
                                                }
                                                if (joueursList.get(i).getCouleur().equals("orange")) {
                                                    pionOrange[numeroButton - 1].setBounds(x - 34, y - 25, 80, 60);
                                                    plateu13[pos1][pos2].setEnabled(false);
                                                }
                                                chrono.stop();
                                                timerJoueur = 30;
                                                chrono.start();


                                                joueursList.get(i).setPeutJouer(false);
                                                if (i == joueursList.size() - 1) {
                                                    joueursList.get(0).setPeutJouer(true);
                                                } else {
                                                    joueursList.get(i + 1).setPeutJouer(true);
                                                }
                                                for (int x1 =0;x1<13;x1++){
                                                    for (int y1=0; y1<13;y1++){
                                                        if (plateauTraitement.getTab()[x1][y1].getPion()==null && !getPlateu13(x1, y1).isEnabled() ){
                                                            getPlateu13(x1, y1).setEnabled(true);
                                                            pionBlanc[x1*13+y1].setBounds(6000,6000,36,36);
                                                            pionNoir[x1*13+y1].setBounds(6000,6000,36,36);
                                                            pionOrange[x1*13+y1].setBounds(6000,6000,36,36);

                                                        }
                                                    }
                                                }
                                                break;
                                            }

                                        }


                                    }
                                }
                            }
                            else if (nbJoueur == 2) {
                                for (int i = 0; i < joueursList.size(); i++) {
                                    if (joueursList.get(i).getPeutJouer() && !joueursList.get(i).getNom().equals("IA")) {
                                        if (joueursList.get(i).getJoueMegaPion() && plateauTraitement.placerMegaPion(pos1, pos2, joueursList.get(i))) {
                                            if (joueursList.get(i).getCouleur().equals("blanc")) {
                                                megapionBlanc.setBounds(x - 34, y - 25, 80, 60);

                                                plateu13[pos1][pos2].setEnabled(false);
                                                joueursList.get(i).setJoueMegapion(false);

                                            }
                                            if (joueursList.get(i).getCouleur().equals("noir")) {
                                                megapionNoir.setBounds(x - 34, y - 25, 80, 60);

                                                plateu13[pos1][pos2].setEnabled(false);
                                                joueursList.get(i).setJoueMegapion(false);

                                            }

                                            if (i==0) {
                                                if (joueursList.get(i + 1).getNom().equals("IA")) {

                                                    joueursList.get(i).setPeutJouer(false);
                                                    joueursList.get(i + 1).setPeutJouer(true);
                                                    ia1.bestMove(plateauTraitement,3);
                                                    //System.out.println("IA");
                                                    x = getPlateu13(ia1.getMove(), ia1.getMove2()).getX();
                                                    y = getPlateu13(ia1.getMove(), ia1.getMove2()).getY();
                                                    pionOrange[numeroButton-1].setBounds(x - 34, y - 25, 80, 60);
                                                    getPlateu13(ia1.getMove(), ia1.getMove2()).setEnabled(false);
                                                    joueursList.get(i + 1).setPeutJouer(false);
                                                    joueursList.get(0).setPeutJouer(true);
                                                    for (int x1 =0;x1<13;x1++){
                                                        for (int y1=0; y1<13;y1++){
                                                            if (plateauTraitement.getTab()[x1][y1].getPion()==null && !getPlateu13(x1, y1).isEnabled() ){
                                                                getPlateu13(x1, y1).setEnabled(true);
                                                                pionBlanc[x1*13+y1].setBounds(6000,6000,36,36);
                                                                pionNoir[x1*13+y1].setBounds(6000,6000,36,36);
                                                                pionOrange[x1*13+y1].setBounds(6000,6000,36,36);

                                                            }
                                                        }
                                                    }

                                                    break;
                                                }
                                                joueursList.get(i).setPeutJouer(false);
                                                joueursList.get(i+1).setPeutJouer(true);
                                                for (int x1 =0;x1<13;x1++){
                                                    for (int y1=0; y1<13;y1++){
                                                        if (plateauTraitement.getTab()[x1][y1].getPion()==null && !getPlateu13(x1, y1).isEnabled() ){
                                                            getPlateu13(x1, y1).setEnabled(true);
                                                            pionBlanc[x1*13+y1].setBounds(6000,6000,36,36);
                                                            pionNoir[x1*13+y1].setBounds(6000,6000,36,36);
                                                            pionOrange[x1*13+y1].setBounds(6000,6000,36,36);

                                                        }
                                                    }
                                                }
                                                break;
                                            }
                                            if (i==1){
                                                if (i<joueursList.size()-1){
                                                    if (joueursList.get(i + 1).getNom().equals("IA")) {

                                                        joueursList.get(i).setPeutJouer(false);
                                                        joueursList.get(i + 1).setPeutJouer(true);
                                                        ia1.bestMove(plateauTraitement,3);
                                                        System.out.println("IA");
                                                        x = getPlateu13(ia1.getMove(), ia1.getMove2()).getX();
                                                        y = getPlateu13(ia1.getMove(), ia1.getMove2()).getY();
                                                        pionOrange[numeroButton-1].setBounds(x - 34, y - 25, 80, 60);
                                                        getPlateu13(ia1.getMove(), ia1.getMove2()).setEnabled(false);
                                                        joueursList.get(i + 1).setPeutJouer(false);
                                                        joueursList.get(0).setPeutJouer(true);
                                                        chrono.stop();
                                                        timerJoueur = 30;
                                                        chrono.start();

                                                        for (int x1 =0;x1<13;x1++){
                                                            for (int y1=0; y1<13;y1++){
                                                                if (plateauTraitement.getTab()[x1][y1].getPion()==null && !getPlateu13(x1, y1).isEnabled() ){
                                                                    getPlateu13(x1, y1).setEnabled(true);
                                                                    pionBlanc[x1*13+y1].setBounds(6000,6000,36,36);
                                                                    pionNoir[x1*13+y1].setBounds(6000,6000,36,36);
                                                                    pionOrange[x1*13+y1].setBounds(6000,6000,36,36);

                                                                }
                                                            }
                                                        }
                                                        break;
                                                    }

                                                }
                                            }

                                        }
                                        else if (plateauTraitement.placer(pos1, pos2, joueursList.get(i))) {
                                            if (joueursList.get(i).getCouleur().equals("blanc")) {
                                                pionBlanc[numeroButton - 1].setBounds(x - 34, y - 25, 80, 60);


                                                plateu13[pos1][pos2].setEnabled(false);

                                            }
                                            if (joueursList.get(i).getCouleur().equals("noir")) {
                                                pionNoir[numeroButton - 1].setBounds(x - 34, y - 25, 80, 60);


                                                plateu13[pos1][pos2].setEnabled(false);

                                            }

                                            if (i==0) {
                                                if (joueursList.get(i + 1).getNom().equals("IA")) {

                                                    joueursList.get(i).setPeutJouer(false);
                                                    joueursList.get(i + 1).setPeutJouer(true);
                                                    ia1.bestMove(plateauTraitement,3);
                                                    //System.out.println("IA");
                                                    x = getPlateu13(ia1.getMove(), ia1.getMove2()).getX();
                                                    y = getPlateu13(ia1.getMove(), ia1.getMove2()).getY();
                                                    pionOrange[numeroButton-1].setBounds(x - 34, y - 25, 80, 60);
                                                    getPlateu13(ia1.getMove(), ia1.getMove2()).setEnabled(false);
                                                    joueursList.get(i + 1).setPeutJouer(false);
                                                    joueursList.get(0).setPeutJouer(true);

                                                    for (int x1 =0;x1<13;x1++){
                                                        for (int y1=0; y1<13;y1++){
                                                            if (plateauTraitement.getTab()[x1][y1].getPion()==null && !getPlateu13(x1, y1).isEnabled() ){
                                                                getPlateu13(x1, y1).setEnabled(true);
                                                                pionBlanc[x1*13+y1].setBounds(6000,6000,36,36);
                                                                pionNoir[x1*13+y1].setBounds(6000,6000,36,36);
                                                                pionOrange[x1*13+y1].setBounds(6000,6000,36,36);

                                                            }
                                                        }
                                                    }
                                                    break;
                                                }
                                                joueursList.get(i).setPeutJouer(false);
                                                joueursList.get(i+1).setPeutJouer(true);
                                                for (int x1 =0;x1<13;x1++){
                                                    for (int y1=0; y1<13;y1++){
                                                        if (plateauTraitement.getTab()[x1][y1].getPion()==null && !getPlateu13(x1, y1).isEnabled() ){
                                                            getPlateu13(x1, y1).setEnabled(true);
                                                            pionBlanc[x1*13+y1].setBounds(6000,6000,36,36);
                                                            pionNoir[x1*13+y1].setBounds(6000,6000,36,36);
                                                            pionOrange[x1*13+y1].setBounds(6000,6000,36,36);

                                                        }
                                                    }
                                                }
                                                break;
                                            }
                                            if (i==1){
                                                if (i<joueursList.size()-1){
                                                    if (joueursList.get(i + 1).getNom().equals("IA")) {

                                                        joueursList.get(i).setPeutJouer(false);
                                                        joueursList.get(i + 1).setPeutJouer(true);
                                                        ia1.bestMove(plateauTraitement,3);
                                                        System.out.println("IA");
                                                        x = getPlateu13(ia1.getMove(), ia1.getMove2()).getX();
                                                        y = getPlateu13(ia1.getMove(), ia1.getMove2()).getY();
                                                        pionOrange[numeroButton-1].setBounds(x - 34, y - 25, 80, 60);
                                                        getPlateu13(ia1.getMove(), ia1.getMove2()).setEnabled(false);
                                                        joueursList.get(i + 1).setPeutJouer(false);
                                                        joueursList.get(0).setPeutJouer(true);
                                                        chrono.stop();
                                                        timerJoueur = 30;
                                                        chrono.start();
                                                        for (int x1 =0;x1<13;x1++){
                                                            for (int y1=0; y1<13;y1++){
                                                                if (plateauTraitement.getTab()[x1][y1].getPion()==null && !getPlateu13(x1, y1).isEnabled() ){
                                                                    getPlateu13(x1, y1).setEnabled(true);
                                                                    pionBlanc[x1*13+y1].setBounds(6000,6000,36,36);
                                                                    pionNoir[x1*13+y1].setBounds(6000,6000,36,36);
                                                                    pionOrange[x1*13+y1].setBounds(6000,6000,36,36);

                                                                }
                                                            }
                                                        }

                                                        break;
                                                    }

                                                }
                                            }
                                        }





                                   ;
                                    }


                                }
                            }
                            else{
                                if (joueur1.getJoueMegaPion() && plateauTraitement.placerMegaPion(pos1, pos2, joueur1)) {
                                    x = getPlateu13(pos1, pos2).getX();
                                    y = getPlateu13(pos1, pos2).getY();
                                    megapionBlanc.setBounds(x - 34, y - 25, 80, 60);
                                    getPlateu13(pos1, pos2).setEnabled(false);
                                    joueur1.setJoueMegapion(false);

                                    ia1.bestMove(plateauTraitement,2);
                                    x = getPlateu13(ia1.getMove(), ia1.getMove2()).getX();
                                    y = getPlateu13(ia1.getMove(), ia1.getMove2()).getY();
                                    pionNoir[numeroButton-1].setBounds(x - 34, y - 25, 80, 60);
                                    getPlateu13(ia1.getMove(), ia1.getMove2()).setEnabled(false);
                                    ia1.bestMove(plateauTraitement,3);
                                    x = getPlateu13(ia1.getMove(), ia1.getMove2()).getX();
                                    y = getPlateu13(ia1.getMove(), ia1.getMove2()).getY();
                                    pionOrange[numeroButton-1].setBounds(x - 34, y - 25, 80, 60);
                                    getPlateu13(ia1.getMove(), ia1.getMove2()).setEnabled(false);




                                    chrono.restart();
                                    timerJoueur=30;
                                    for (int x1 =0;x1<13;x1++){
                                        for (int y1=0; y1<13;y1++){
                                            if (plateauTraitement.getTab()[x1][y1].getPion()==null && !getPlateu13(x1, y1).isEnabled() ){
                                                getPlateu13(x1, y1).setEnabled(true);
                                                pionBlanc[x1*13+y1].setBounds(6000,6000,36,36);
                                                pionNoir[x1*13+y1].setBounds(6000,6000,36,36);
                                                pionOrange[x1*13+y1].setBounds(6000,6000,36,36);

                                            }
                                        }
                                    }


                                }
                                else if (plateauTraitement.placer(pos1, pos2, joueur1)){
                                    x = getPlateu13(pos1, pos2).getX();
                                    y = getPlateu13(pos1, pos2).getY();
                                    pionBlanc[numeroButton - 1].setBounds(x - 34, y - 25, 80, 60);
                                    getPlateu13(pos1, pos2).setEnabled(false);


                                    ia1.bestMove(plateauTraitement,2);
                                    x = getPlateu13(ia1.getMove(), ia1.getMove2()).getX();
                                    y = getPlateu13(ia1.getMove(), ia1.getMove2()).getY();
                                    pionNoir[numeroButton-1].setBounds(x - 34, y - 25, 80, 60);
                                    getPlateu13(ia1.getMove(), ia1.getMove2()).setEnabled(false);
                                    ia1.bestMove(plateauTraitement,3);
                                    x = getPlateu13(ia1.getMove(), ia1.getMove2()).getX();
                                    y = getPlateu13(ia1.getMove(), ia1.getMove2()).getY();
                                    pionOrange[numeroButton-1].setBounds(x - 34, y - 25, 80, 60);
                                    getPlateu13(ia1.getMove(), ia1.getMove2()).setEnabled(false);




                                    chrono.restart();
                                    timerJoueur=30;
                                    for (int x1 =0;x1<13;x1++){
                                        for (int y1=0; y1<13;y1++){
                                            if (plateauTraitement.getTab()[x1][y1].getPion()==null && !getPlateu13(x1, y1).isEnabled() ){
                                                getPlateu13(x1, y1).setEnabled(true);
                                                pionBlanc[x1*13+y1].setBounds(6000,6000,36,36);
                                                pionNoir[x1*13+y1].setBounds(6000,6000,36,36);
                                                pionOrange[x1*13+y1].setBounds(6000,6000,36,36);

                                            }
                                        }
                                    }

                                }








                            }


                        }
                    });
                }
            }



        }


        if (taille == 19) {
            plateau = new PlateauGui(19);
            plateau.setBounds(499, 144, 800, 800);
            add(plateau);
            if (nbJoueur == 3) {
                infoJ1.setBounds(500, 0, 200, 150);
                infoJ2.setBounds(750, 0, 200, 150);
                infoJ3.setBounds(1000, 0, 200, 150);
                infoJ1.setText("<html><p>JOUEUR 1</p><p>Nom: " + joueur1.getNom() + "</p> </html>");
                infoJ2.setText("<html><p>JOUEUR 2</p><p>Nom: " + joueur2.getNom() + "</p> </html>");
                infoJ3.setText("<html><p>JOUEUR 3</p><p>Nom: " + joueur3.getNom() + "</p> </html>");
            } else if (nbJoueur == 2) {
                infoJ1.setBounds(500, 0, 200, 150);
                infoJ2.setBounds(750, 0, 200, 150);
                infoJ3.setBounds(1000, 0, 200, 150);
                infoJ1.setText("<html><p>JOUEUR 1</p><p>Nom: " + joueur1.getNom() + "</p> </html>");
                infoJ2.setText("<html><p>JOUEUR 2</p><p>Nom: " + joueur2.getNom() + "</p> </html>");
                infoJ3.setText("<html><p>ORDINATEUR</p><p>Nom: " + "IA1" + "</p> </html>");


            } else {
                infoJ1.setBounds(500, 0, 200, 150);
                infoJ2.setBounds(750, 0, 200, 150);
                infoJ3.setBounds(1000, 0, 200, 150);
                infoJ1.setText("<html><p>JOUEUR 1</p><p>Nom: " + joueur1.getNom() + "</p> </html>");
                infoJ2.setText("<html><p>ORDINATEUR</p><p>Nom: " + "IA1" + "</p> </html>");
                infoJ3.setText("<html><p>ORDINATEUR</p><p>Nom: " + "IA2" + "</p> </html>");
            }

            for (int i = 0; i < 361; i++) {
                buttons19[i] = new InvisibleButton();
                add(buttons19[i]);
                pionOrange[i] = new JLabel(new ImageIcon("image/pion-orange.png"), JLabel.CENTER);
                pionNoir[i] = new JLabel(new ImageIcon("image/pion-noir.png"), JLabel.CENTER);
                pionBlanc[i] = new JLabel(new ImageIcon("image/pion-blanc.png"), JLabel.CENTER);
                add(pionOrange[i]);
                add(pionNoir[i]);
                add(pionBlanc[i]);
                buttons19[i].setVisible(true);


            }
            int i = 0;

            for (int k = (int) (1080 / 7.7); k <= 900; k = k + 42) {
                for (int j = 495; j <= 1275; j = j + 42) {
                    if (i >= 361) {
                        break;
                    }
                    buttons19[i].setBounds(j, k, 10, 10);

                    i++;
                }
            }
            int compteur=0;
            for (int u = 0; u < 19; u++) {
                for (int j = 0; j < 19; j++) {
                    plateau19[u][j] = buttons19[compteur];
                    compteur++;
                    int pos1=u;
                    int pos2=j;
                    int numeroButton=compteur;
                    plateau19[u][j].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int x = plateau19[pos1][pos2].getX();
                            int y = plateau19[pos1][pos1].getY();

                            if (nbJoueur == 3) {
                                for (int i = 0; i < joueursList.size(); i++) {
                                    if (joueursList.get(i).getPeutJouer()) {
                                        if (joueursList.get(i).getJoueMegaPion() && plateauTraitement.placerMegaPion(pos1, pos2, joueursList.get(i))) {
                                            if (joueursList.get(i).getCouleur().equals("blanc")) {
                                                megapionBlanc.setBounds(x - 34, y - 25, 80, 60);

                                                System.out.println("megapion blanc" + " " + pos1 + " " + pos2);
                                                plateau19[pos1][pos2].setEnabled(false);
                                                joueursList.get(i).setJoueMegapion(false);

                                            }
                                            if (joueursList.get(i).getCouleur().equals("noir")) {
                                                megapionNoir.setBounds(x - 34, y - 25, 80, 60);

                                                plateau19[pos1][pos2].setEnabled(false);
                                                joueursList.get(i).setJoueMegapion(false);

                                            }
                                            if (joueursList.get(i).getCouleur().equals("orange")) {
                                                megapionOrange.setBounds(x - 34, y - 25, 80, 60);

                                                plateau19[pos1][pos2].setEnabled(false);
                                                joueursList.get(i).setJoueMegapion(false);
                                                System.out.println(plateauTraitement.toString());
                                            }
                                            chrono.stop();
                                            timerJoueur = 30;
                                            chrono.start();


                                            joueursList.get(i).setPeutJouer(false);
                                            if (i == joueursList.size() - 1) {
                                                joueursList.get(0).setPeutJouer(true);
                                            } else {
                                                joueursList.get(i + 1).setPeutJouer(true);

                                            }
                                            for (int x1 = 0; x1 < 19; x1++) {
                                                for (int y1 = 0; y1 < 19; y1++) {
                                                    if (plateauTraitement.getTab()[x1][y1].getPion() == null && !getPlateu19(x1, y1).isEnabled()) {
                                                        getPlateu19(x1, y1).setEnabled(true);
                                                        pionBlanc[x1 * 19 + y1].setBounds(6000, 6000, 36, 36);
                                                        pionNoir[x1 * 19 + y1].setBounds(6000, 6000, 36, 36);
                                                        pionOrange[x1 * 19 + y1].setBounds(6000, 6000, 36, 36);

                                                    }
                                                }
                                            }
                                            break;
                                        } else if (plateauTraitement.placer(pos1, pos2, joueursList.get(i))) {
                                            if (joueursList.get(i).getCouleur().equals("blanc")) {
                                                pionBlanc[numeroButton - 1].setBounds(x - 34, y - 25, 80, 60);
                                                plateau19[pos1][pos2].setEnabled(false);


                                            }
                                            if (joueursList.get(i).getCouleur().equals("noir")) {
                                                pionNoir[numeroButton - 1].setBounds(x - 34, y - 25, 80, 60);
                                                plateau19[pos1][pos2].setEnabled(false);
                                            }
                                            if (joueursList.get(i).getCouleur().equals("orange")) {
                                                pionOrange[numeroButton - 1].setBounds(x - 34, y - 25, 80, 60);
                                                plateau19[pos1][pos2].setEnabled(false);
                                                System.out.println(plateauTraitement.toString());

                                            }
                                            chrono.stop();
                                            timerJoueur = 30;
                                            chrono.start();


                                            joueursList.get(i).setPeutJouer(false);
                                            if (i == joueursList.size() - 1) {
                                                joueursList.get(0).setPeutJouer(true);
                                            } else {
                                                joueursList.get(i + 1).setPeutJouer(true);
                                            }
                                            for (int x1 = 0; x1 < 19; x1++) {
                                                for (int y1 = 0; y1 < 19; y1++) {
                                                    if (plateauTraitement.getTab()[x1][y1].getPion() == null && !getPlateu19(x1, y1).isEnabled()) {
                                                        getPlateu19(x1, y1).setEnabled(true);
                                                        pionBlanc[x1 * 19 + y1].setBounds(6000, 6000, 36, 36);
                                                        pionNoir[x1 * 19 + y1].setBounds(6000, 6000, 36, 36);
                                                        pionOrange[x1 * 19 + y1].setBounds(6000, 6000, 36, 36);

                                                    }
                                                }
                                            }
                                            break;
                                        }

                                    }


                                }
                            }

                            else if (nbJoueur == 2) {
                                for (int i = 0; i < joueursList.size(); i++) {
                                    if (joueursList.get(i).getPeutJouer() && !joueursList.get(i).getNom().equals("IA")) {
                                        if (joueursList.get(i).getJoueMegaPion() && plateauTraitement.placerMegaPion(pos1, pos2, joueursList.get(i))) {
                                            if (joueursList.get(i).getCouleur().equals("blanc")) {
                                                megapionBlanc.setBounds(x - 34, y - 25, 80, 60);
                                                plateau19[pos1][pos2].setEnabled(false);
                                                joueursList.get(i).setJoueMegapion(false);

                                            }
                                            if (joueursList.get(i).getCouleur().equals("noir")) {
                                                megapionNoir.setBounds(x - 34, y - 25, 80, 60);
                                                plateauTraitement.placerMegaPion(pos1, pos2, joueursList.get(i));
                                                plateau19[pos1][pos2].setEnabled(false);
                                                joueursList.get(i).setJoueMegapion(false);

                                            }
                                            if (i == 0) {
                                                if (joueursList.get(i + 1).getNom().equals("IA")) {

                                                    joueursList.get(i).setPeutJouer(false);
                                                    joueursList.get(i + 1).setPeutJouer(true);
                                                    ia1.bestMove(plateauTraitement, 3);
                                                    //System.out.println("IA");

                                                    x = getPlateu19(ia1.getMove(), ia1.getMove2()).getX();
                                                    y = getPlateu19(ia1.getMove(), ia1.getMove2()).getY();
                                                    pionOrange[numeroButton - 1].setBounds(x - 34, y - 25, 80, 60);
                                                    getPlateu19(ia1.getMove(), ia1.getMove2()).setEnabled(false);
                                                    joueursList.get(i + 1).setPeutJouer(false);
                                                    joueursList.get(0).setPeutJouer(true);

                                                    break;
                                                }
                                                joueursList.get(i).setPeutJouer(false);
                                                joueursList.get(i + 1).setPeutJouer(true);

                                                break;
                                            }
                                            if (i == 1) {
                                                if (i < joueursList.size() - 1) {
                                                    if (joueursList.get(i + 1).getNom().equals("IA")) {

                                                        joueursList.get(i).setPeutJouer(false);
                                                        joueursList.get(i + 1).setPeutJouer(true);
                                                        ia1.bestMove(plateauTraitement, 3);
                                                        System.out.println("IA");
                                                        x = getPlateu19(ia1.getMove(), ia1.getMove2()).getX();
                                                        y = getPlateu19(ia1.getMove(), ia1.getMove2()).getY();
                                                        pionOrange[numeroButton].setBounds(x - 34, y - 25, 80, 60);
                                                        getPlateu19(ia1.getMove(), ia1.getMove2()).setEnabled(false);
                                                        joueursList.get(i + 1).setPeutJouer(false);
                                                        joueursList.get(0).setPeutJouer(true);
                                                        chrono.stop();
                                                        timerJoueur = 30;
                                                        chrono.start();


                                                        break;
                                                    }

                                                }
                                            }


                                        }

                                        break;

                                    } else if (plateauTraitement.placer(pos1, pos2, joueursList.get(i))) {
                                        if (joueursList.get(i).getCouleur().equals("blanc")) {
                                            pionBlanc[numeroButton - 1].setBounds(x - 34, y - 25, 80, 60);
                                            System.out.println("blanc");
                                            plateau19[pos1][pos2].setEnabled(false);

                                        }
                                        if (joueursList.get(i).getCouleur().equals("noir")) {
                                            pionNoir[numeroButton - 1].setBounds(x - 34, y - 25, 80, 60);
                                            System.out.println("noir");
                                            plateau19[pos1][pos2].setEnabled(false);

                                        }
                                        if (i == 0) {
                                            if (joueursList.get(i + 1).getNom().equals("IA")) {

                                                joueursList.get(i).setPeutJouer(false);
                                                joueursList.get(i + 1).setPeutJouer(true);
                                                ia1.bestMove(plateauTraitement, 3);
                                                //System.out.println("IA");

                                                x = getPlateu19(ia1.getMove(), ia1.getMove2()).getX();
                                                y = getPlateu19(ia1.getMove(), ia1.getMove2()).getY();
                                                pionOrange[numeroButton - 1].setBounds(x - 34, y - 25, 80, 60);
                                                getPlateu19(ia1.getMove(), ia1.getMove2()).setEnabled(false);
                                                joueursList.get(i + 1).setPeutJouer(false);
                                                joueursList.get(0).setPeutJouer(true);

                                                break;
                                            }
                                            joueursList.get(i).setPeutJouer(false);
                                            joueursList.get(i + 1).setPeutJouer(true);
                                            break;
                                        }
                                        if (i == 1) {
                                            if (i < joueursList.size() - 1) {
                                                if (joueursList.get(i + 1).getNom().equals("IA")) {

                                                    joueursList.get(i).setPeutJouer(false);
                                                    joueursList.get(i + 1).setPeutJouer(true);
                                                    ia1.bestMove(plateauTraitement, 3);
                                                    System.out.println("IA");
                                                    x = getPlateu19(ia1.getMove(), ia1.getMove2()).getX();
                                                    y = getPlateu19(ia1.getMove(), ia1.getMove2()).getY();
                                                    pionOrange[numeroButton].setBounds(x - 34, y - 25, 80, 60);
                                                    getPlateu19(ia1.getMove(), ia1.getMove2()).setEnabled(false);
                                                    joueursList.get(i + 1).setPeutJouer(false);
                                                    joueursList.get(0).setPeutJouer(true);
                                                    chrono.stop();
                                                    timerJoueur = 30;
                                                    chrono.start();

                                                    break;
                                                }

                                            }
                                        }


                                        break;
                                    }


                                }


                            }
                            else{
                                if (joueur1.getJoueMegaPion() && plateauTraitement.placerMegaPion(pos1, pos2, joueur1) ) {
                                    x = getPlateu19(pos1, pos2).getX();
                                    y = getPlateu19(pos1, pos2).getY();
                                    megapionBlanc.setBounds(x - 34, y - 25, 80, 60);
                                    getPlateu19(pos1, pos2).setEnabled(false);
                                    joueur1.setJoueMegapion(false);
                                    ia1.bestMove(plateauTraitement,2);
                                    x = getPlateu19(ia1.getMove(), ia1.getMove2()).getX();
                                    y = getPlateu19(ia1.getMove(), ia1.getMove2()).getY();
                                    pionNoir[numeroButton-1].setBounds(x - 34, y - 25, 80, 60);
                                    getPlateu19(ia1.getMove(), ia1.getMove2()).setEnabled(false);
                                    ia1.bestMove(plateauTraitement,3);
                                    x = getPlateu19(ia1.getMove(), ia1.getMove2()).getX();
                                    y = getPlateu19(ia1.getMove(), ia1.getMove2()).getY();
                                    pionOrange[numeroButton-1].setBounds(x - 34, y - 25, 80, 60);
                                    getPlateu19(ia1.getMove(), ia1.getMove2()).setEnabled(false);




                                    chrono.restart();
                                    timerJoueur=30;
                                    for (int x1 =0;x1<19;x1++){
                                        for (int y1=0; y1<19;y1++){
                                            if (plateauTraitement.getTab()[x1][y1].getPion()==null && !getPlateu19(x1, y1).isEnabled() ){
                                                getPlateu19(x1, y1).setEnabled(true);
                                                pionBlanc[x1*19+y1].setBounds(6000,6000,36,36);
                                                pionNoir[x1*19+y1].setBounds(6000,6000,36,36);
                                                pionOrange[x1*19+y1].setBounds(6000,6000,36,36);

                                            }
                                        }
                                    }

                                }
                                else if (plateauTraitement.placer(pos1, pos2, joueur1)) {
                                    x = getPlateu19(pos1, pos2).getX();
                                    y = getPlateu19(pos1, pos2).getY();
                                    pionBlanc[numeroButton - 1].setBounds(x - 34, y - 25, 80, 60);
                                    getPlateu19(pos1, pos2).setEnabled(false);
                                    ia1.bestMove(plateauTraitement,2);
                                    x = getPlateu19(ia1.getMove(), ia1.getMove2()).getX();
                                    y = getPlateu19(ia1.getMove(), ia1.getMove2()).getY();
                                    pionNoir[numeroButton-1].setBounds(x - 34, y - 25, 80, 60);
                                    getPlateu19(ia1.getMove(), ia1.getMove2()).setEnabled(false);
                                    ia1.bestMove(plateauTraitement,3);
                                    x = getPlateu19(ia1.getMove(), ia1.getMove2()).getX();
                                    y = getPlateu19(ia1.getMove(), ia1.getMove2()).getY();
                                    pionOrange[numeroButton-1].setBounds(x - 34, y - 25, 80, 60);
                                    getPlateu19(ia1.getMove(), ia1.getMove2()).setEnabled(false);




                                    chrono.restart();
                                    timerJoueur=30;
                                    for (int x1 =0;x1<19;x1++){
                                        for (int y1=0; y1<19;y1++){
                                            if (plateauTraitement.getTab()[x1][y1].getPion()==null && !getPlateu19(x1, y1).isEnabled() ){
                                                getPlateu19(x1, y1).setEnabled(true);
                                                pionBlanc[x1*19+y1].setBounds(6000,6000,36,36);
                                                pionNoir[x1*19+y1].setBounds(6000,6000,36,36);
                                                pionOrange[x1*19+y1].setBounds(6000,6000,36,36);

                                            }
                                        }
                                    }
                                }


                            }

                        }
                    });
                }
            }
        }


        tempsJoueur.setBounds(100, 350, 500, 150);
        tempsJoueur.setFont(new Font("Arial", Font.BOLD, 30));
        chrono = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tempsJoueur.setText("<html>Temps restant pour jouer : <br/>" + "<center>" + timerJoueur + "s" + "</center></html>");
                timerJoueur--;
                if (timerJoueur == -1) {

                    chrono.restart();
                    timerJoueur = 30;
                    passerTour.doClick();

                }
            }
        });
        chrono.start();


        tempsPartie.setBounds(100, 200, 500, 150);
        tempsPartie.setFont(new Font("Arial", Font.BOLD, 30));
        chronoPartie = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tempsPartie.setText("<html>Temps de la partie : <br/>" + "<center>" + heure + "h:" + minute + "m:" + seconde + "s" + "</center></html>");
                seconde++;
                if (seconde == 60) {
                    seconde = 0;
                    minute++;
                }
                if (minute == 60) {
                    minute = 0;
                    heure++;
                }


            }
        });
        chronoPartie.start();

        passerTour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean finPartie = true;
                for (int i = 0; i < joueursList.size(); i++) {
                    if (joueursList.get(i).getPeutJouer() && !joueursList.get(i).getNom().equals("IA")) {
                        joueursList.get(i).setPasser(true);
                        System.out.println(joueursList.get(i).getCouleur()+" a passé son tour ");
                    }
                }
                for (int i = 0; i < joueursList.size(); i++) {
                    if (!joueursList.get(i).isPasser() && !joueursList.get(i).getNom().equals("IA")) {
                        finPartie = false;
                        break;
                    }
                }
                if (finPartie) {
                    System.out.println("FIN DE LA PARTIE ");
                    chrono.stop();
                    chronoPartie.stop();
                    removeAll();
                    infoFinDePartie.setBounds(200,0,1500,800);
                    infoFinDePartie.setFont(new Font("Arial", Font.BOLD, 80));
                    plateauTraitement.verifTerritoires();
                    if (joueur1.getScore()> joueur2.getScore()&&joueur1.getScore()>joueur3.getScore()  ){
                        infoFinDePartie.setText("<html><p>"+joueur1.getNom()+" a gagné </p>"+"<p>avec un score de "+joueur1.getScore() +" points </p> </html>");
                    }
                    else if  (joueur2.getScore()> joueur1.getScore() && joueur2.getScore()> joueur3.getScore()){
                        System.out.println("J2 Gagne");
                        infoFinDePartie.setText("<html><p>"+joueur2.getNom()+" a gagné </p>"+"<p>avec un score de "+joueur2.getScore() +" points </p> </html>");

                    }
                    else {
                        System.out.println("J3 Gagne");
                        infoFinDePartie.setText("<html><p>"+joueur3.getNom()+" a gagné </p>"+"<p>avec un score de "+joueur3.getScore() +" points </p> </html>");
                    }
                    add(infoFinDePartie);
                    repaint();

                }
                else {
                    if (nbJoueur == 1) {
                        if (taille == 9) {
                            ia1.bestMove(plateauTraitement, 2);
                            int x = getPlateau9(ia1.getMove(), ia1.getMove2()).getX();
                            int y = getPlateau9(ia1.getMove(), ia1.getMove2()).getY();
                            pionNoir[ia1.getMove() * 9 + ia1.getMove2()].setBounds(x - 34, y - 25, 80, 60);
                            getPlateau9(ia1.getMove(), ia1.getMove2()).setEnabled(false);

                            ia1.bestMove(plateauTraitement, 3);
                            x = getPlateau9(ia1.getMove(), ia1.getMove2()).getX();
                            y = getPlateau9(ia1.getMove(), ia1.getMove2()).getY();
                            pionOrange[ia1.getMove() * 9 + ia1.getMove2()].setBounds(x - 34, y - 25, 80, 60);
                            getPlateau9(ia1.getMove(), ia1.getMove2()).setEnabled(false);
                        }
                        if (taille == 13) {


                            ia1.bestMove(plateauTraitement, 2);
                            int x = getPlateu13(ia1.getMove(), ia1.getMove2()).getX();
                            int y = getPlateu13(ia1.getMove(), ia1.getMove2()).getY();
                            pionNoir[ia1.getMove() * 13 + ia1.getMove2()].setBounds(x - 34, y - 25, 80, 60);
                            getPlateu13(ia1.getMove(), ia1.getMove2()).setEnabled(false);

                            ia1.bestMove(plateauTraitement, 3);
                            x = getPlateu13(ia1.getMove(), ia1.getMove2()).getX();
                            y = getPlateu13(ia1.getMove(), ia1.getMove2()).getY();
                            pionOrange[ia1.getMove() * 13 + ia1.getMove2()].setBounds(x - 34, y - 25, 80, 60);
                            getPlateu13(ia1.getMove(), ia1.getMove2()).setEnabled(false);


                        }
                        if (taille == 19) {
                            ia1.bestMove(plateauTraitement, 2);
                            int x = getPlateu19(ia1.getMove(), ia1.getMove2()).getX();
                            int y = getPlateu19(ia1.getMove(), ia1.getMove2()).getY();
                            pionNoir[ia1.getMove() * 19 + ia1.getMove2()].setBounds(x - 34, y - 25, 80, 60);
                            getPlateu19(ia1.getMove(), ia1.getMove2()).setEnabled(false);

                            ia1.bestMove(plateauTraitement, 3);
                            x = getPlateu19(ia1.getMove(), ia1.getMove2()).getX();
                            y = getPlateu19(ia1.getMove(), ia1.getMove2()).getY();
                            pionOrange[ia1.getMove() * 19 + ia1.getMove2()].setBounds(x - 34, y - 25, 80, 60);
                            getPlateu19(ia1.getMove(), ia1.getMove2()).setEnabled(false);

                        }
                    } else {
                        for (int i = 0; i < joueursList.size(); i++) {


                            if (joueursList.get(i).getPeutJouer() && !joueursList.get(i).getNom().equals("IA")) {
                                joueursList.get(i).setPeutJouer(false);


                                chrono.restart();
                                timerJoueur = 30;
                                if (i == 0) {

                                    if (joueursList.get(i + 1).getNom().equals("IA")) {

                                        ia1.bestMove(plateauTraitement, 3);
                                        int pos1 = ia1.getMove();
                                        int pos2 = ia1.getMove2();
                                        getPlateu13(pos1, pos2).setEnabled(false);
                                        int x = getPlateu13(pos1, pos2).getX();
                                        int y = getPlateu13(pos1, pos2).getY();

                                        pionOrange[(pos1 * 12 + pos2)].setBounds(x - 34, y - 25, 80, 60);
                                        if (i + 1 == joueursList.size() - 1) {
                                            joueursList.get(0).setPeutJouer(true);
                                            break;
                                        } else {
                                            joueursList.get(i + 1).setPeutJouer(true);
                                            break;
                                        }

                                    } else {
                                        joueursList.get(i + 1).setPeutJouer(true);
                                        break;

                                    }

                                }
                                if (i == 1) {

                                    if (i < joueursList.size() - 1) {
                                        if (joueursList.get(i + 1).getNom().equals("IA")) {

                                            ia1.bestMove(plateauTraitement, 3);
                                            int pos1 = ia1.getMove();
                                            int pos2 = ia1.getMove2();
                                            if (taille == 9) {
                                                getPlateau9(pos1, pos2).setEnabled(false);

                                                int x = getPlateau9(pos1, pos2).getX();
                                                int y = getPlateau9(pos1, pos2).getY();
                                                pionOrange[pos1 * 9 + pos2].setBounds(x - 34, y - 25, 80, 60);
                                            }
                                            if (taille == 13) {
                                                getPlateu13(pos1, pos2).setEnabled(false);

                                                int x = getPlateu13(pos1, pos2).getX();
                                                int y = getPlateu13(pos1, pos2).getY();
                                                pionOrange[pos1 * 13 + pos2].setBounds(x - 34, y - 25, 80, 60);
                                            } if (taille ==19) {
                                                getPlateu19(pos1, pos2).setEnabled(false);

                                                int x = getPlateu19(pos1, pos2).getX();
                                                int y = getPlateu19(pos1, pos2).getY();
                                                pionOrange[pos1 * 19 + pos2].setBounds(x - 34, y - 25, 80, 60);
                                            }
                                        }


                                        if (i == joueursList.size() - 1) {
                                            joueursList.get(0).setPeutJouer(true);

                                            break;
                                        } else {
                                            joueursList.get(i + 1).setPeutJouer(true);
                                            break;
                                        }


                                    }


                                    if (i == joueursList.size() - 1) {
                                        if (joueursList.get(0).getNom().equals("IA")) {
                                            ia1.bestMove(plateauTraitement, 3);
                                            int x = ia1.getMove();
                                            int y = ia1.getMove2();
                                            getPlateu13(x, y).setEnabled(false);
                                            int numeroPion = x * 13 + y;
                                            pionOrange[numeroPion].setBounds(x - 34, y - 25, 80, 60);
                                            add(pionOrange[numeroPion]);
                                            if (i + 1 == joueursList.size() - 1) {
                                                joueursList.get(0).setPeutJouer(true);
                                                break;
                                            } else {
                                                joueursList.get(i + 1).setPeutJouer(true);
                                                break;
                                            }
                                        } else {
                                            joueursList.get(0).setPeutJouer(true);
                                            break;
                                        }
                                    }


                                    if (i == joueursList.size() - 1) {
                                        joueursList.get(0).setPeutJouer(true);
                                        break;
                                    }
                                }
                                if (i == 2) {
                                    joueursList.get(0).setPeutJouer(true);
                                }
                            }


                        }
                    }
                }
            }

        });

        abandonner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nbJoueur !=1) {
                    for (int i = 0; i < joueursList.size(); i++) {
                        if (joueursList.get(i).getPeutJouer()) {
                            if (i == 0) {
                                if (!joueursList.get(i + 1).getNom().equals("IA")) {
                                    joueursList.get(i + 1).setPeutJouer(true);
                                    joueursList.remove(i);
                                    chrono.restart();
                                    timerJoueur = 30;
                                    if (joueursList.size() == 1) {
                                        System.out.println("FIN DE PARTIE");
                                        chrono.stop();
                                        chronoPartie.stop();
                                        removeAll();
                                        infoFinDePartie.setBounds(200,0,1500,800);
                                        infoFinDePartie.setFont(new Font("Arial", Font.BOLD, 80));
                                        plateauTraitement.verifTerritoires();
                                        if (joueur1.getScore()> joueur2.getScore()&&joueur1.getScore()>joueur3.getScore()  ){
                                            infoFinDePartie.setText("<html><p>"+joueur1.getNom()+" a gagné </p>"+"<p>avec un score de "+joueur1.getScore() +" points </p> </html>");
                                        }
                                        else if  (joueur2.getScore()> joueur1.getScore() && joueur2.getScore()> joueur3.getScore()){
                                            System.out.println("J2 Gagne");
                                            infoFinDePartie.setText("<html><p>"+joueur2.getNom()+" a gagné </p>"+"<p>avec un score de "+joueur2.getScore() +" points </p> </html>");

                                        }
                                        else {
                                            System.out.println("J3 Gagne");
                                            infoFinDePartie.setText("<html><p>"+joueur3.getNom()+" a gagné </p>"+"<p>avec un score de "+joueur3.getScore() +" points </p> </html>");
                                        }
                                        add(infoFinDePartie);
                                        repaint();
                                    }
                                    break;
                                } else {
                                    ia1.bestMove(plateauTraitement, 3);
                                    System.out.println("L IA A JOUE");
                                    int pos1 = ia1.getMove();
                                    int pos2 = ia1.getMove2();
                                    if (taille == 13) {
                                        getPlateu13(pos1, pos2).setEnabled(false);
                                         int x = getPlateu13(pos1, pos2).getX();
                                        int y = getPlateu13(pos1, pos2).getY();
                                        pionOrange[pos1 * 13 + pos2].setBounds(x - 34, y - 25, 80, 60);
                                    } else if (taille == 19){
                                        getPlateu19(pos1, pos2).setEnabled(false);

                                        int x = getPlateu19(pos1, pos2).getX();
                                        int y = getPlateu19(pos1, pos2).getY();
                                        pionOrange[pos1 * 19 + pos2].setBounds(x - 34, y - 25, 80, 60);
                                    }
                                    else {

                                        int x = getPlateau9(pos1, pos2).getX();
                                        int y = getPlateau9(pos1, pos2).getY();
                                        pionOrange[pos1 * 9 + pos2].setBounds(x - 34, y - 25, 80, 60);

                                    }
                                    joueursList.get(0).setPeutJouer(true);
                                    joueursList.remove(1);
                                    chrono.restart();
                                    timerJoueur = 30;
                                    if (joueursList.size() == 1) {
                                        System.out.println("FIN DE PARTIE");
                                        chrono.stop();
                                        chronoPartie.stop();
                                        removeAll();
                                        infoFinDePartie.setBounds(200,0,1500,800);
                                        infoFinDePartie.setFont(new Font("Arial", Font.BOLD, 80));
                                        plateauTraitement.verifTerritoires();
                                        if (joueur1.getScore()> joueur2.getScore()&&joueur1.getScore()>joueur3.getScore()  ){
                                            infoFinDePartie.setText("<html><p>"+joueur1.getNom()+" a gagné </p>"+"<p>avec un score de "+joueur1.getScore() +" points </p> </html>");
                                        }
                                        else if  (joueur2.getScore()> joueur1.getScore() && joueur2.getScore()> joueur3.getScore()){
                                            System.out.println("J2 Gagne");
                                            infoFinDePartie.setText("<html><p>"+joueur2.getNom()+" a gagné </p>"+"<p>avec un score de "+joueur2.getScore() +" points </p> </html>");

                                        }
                                        else {
                                            System.out.println("J3 Gagne");
                                            infoFinDePartie.setText("<html><p>"+joueur3.getNom()+" a gagné </p>"+"<p>avec un score de "+joueur3.getScore() +" points </p> </html>");
                                        }
                                        add(infoFinDePartie);
                                        repaint();
                                    }
                                    break;
                                }
                            }
                            if (i == 1) {
                                if (joueursList.size()-1==i)
                                {
                                    System.out.println("FIN DE PARTIE");
                                    chrono.stop();
                                    chronoPartie.stop();
                                    removeAll();
                                    infoFinDePartie.setBounds(200,0,1500,800);
                                    infoFinDePartie.setFont(new Font("Arial", Font.BOLD, 80));
                                    plateauTraitement.verifTerritoires();
                                    if (joueur1.getScore()> joueur2.getScore()&&joueur1.getScore()>joueur3.getScore()  ){
                                        infoFinDePartie.setText("<html><p>"+joueur1.getNom()+" a gagné </p>"+"<p>avec un score de "+joueur1.getScore() +" points </p> </html>");
                                    }
                                    else if  (joueur2.getScore()> joueur1.getScore() && joueur2.getScore()> joueur3.getScore()){
                                        System.out.println("J2 Gagne");
                                        infoFinDePartie.setText("<html><p>"+joueur2.getNom()+" a gagné </p>"+"<p>avec un score de "+joueur2.getScore() +" points </p> </html>");

                                    }
                                    else {
                                        System.out.println("J3 Gagne");
                                        infoFinDePartie.setText("<html><p>"+joueur3.getNom()+" a gagné </p>"+"<p>avec un score de "+joueur3.getScore() +" points </p> </html>");
                                    }
                                    add(infoFinDePartie);
                                    repaint();
                                }
                                else if (!joueursList.get(i + 1).getNom().equals("IA")) {
                                    joueursList.get(i + 1).setPeutJouer(true);
                                    joueursList.remove(i);
                                    chrono.restart();
                                    timerJoueur = 30;
                                    if (joueursList.size() == 1) {
                                        System.out.println("FIN DE PARTIE");
                                        chrono.stop();
                                        chronoPartie.stop();
                                        removeAll();
                                        infoFinDePartie.setBounds(200,0,1500,800);
                                        infoFinDePartie.setFont(new Font("Arial", Font.BOLD, 80));
                                        plateauTraitement.verifTerritoires();
                                        if (joueur1.getScore()> joueur2.getScore()&&joueur1.getScore()>joueur3.getScore()  ){
                                            infoFinDePartie.setText("<html><p>"+joueur1.getNom()+" a gagné </p>"+"<p>avec un score de "+joueur1.getScore() +" points </p> </html>");
                                        }
                                        else if  (joueur2.getScore()> joueur1.getScore() && joueur2.getScore()> joueur3.getScore()){
                                            System.out.println("J2 Gagne");
                                            infoFinDePartie.setText("<html><p>"+joueur2.getNom()+" a gagné </p>"+"<p>avec un score de "+joueur2.getScore() +" points </p> </html>");

                                        }
                                        else {
                                            System.out.println("J3 Gagne");
                                            infoFinDePartie.setText("<html><p>"+joueur3.getNom()+" a gagné </p>"+"<p>avec un score de "+joueur3.getScore() +" points </p> </html>");
                                        }
                                        add(infoFinDePartie);
                                        repaint();
                                    }
                                    break;
                                }
                                else {
                                    ia1.bestMove(plateauTraitement, 3);

                                    int pos1 = ia1.getMove();
                                    int pos2 = ia1.getMove2();
                                    if (taille == 13) {
                                        getPlateu13(pos1, pos2).setEnabled(false);

                                        int x = getPlateu13(pos1, pos2).getX();
                                        int y = getPlateu13(pos1, pos2).getY();
                                        pionOrange[pos1 * 13 + pos2].setBounds(x - 34, y - 25, 80, 60);
                                    } else if (taille == 19) {
                                        getPlateu19(pos1, pos2).setEnabled(false);

                                        int x = getPlateu19(pos1, pos2).getX();
                                        int y = getPlateu19(pos1, pos2).getY();
                                        pionOrange[pos1 * 19 + pos2].setBounds(x - 34, y - 25, 80, 60);
                                    }
                                    else {
                                        getPlateau9(pos1, pos2).setEnabled(false);

                                        int x = getPlateau9(pos1, pos2).getX();
                                        int y = getPlateau9(pos1, pos2).getY();
                                        pionOrange[pos1 * 9 + pos2].setBounds(x - 34, y - 25, 80, 60);
                                    }
                                    joueursList.get(0).setPeutJouer(true);
                                    joueursList.remove(1);
                                    chrono.restart();
                                    timerJoueur = 30;
                                    if (joueursList.size() == 1) {
                                        System.out.println("FIN DE PARTIE");
                                        chrono.stop();
                                        chronoPartie.stop();
                                        removeAll();
                                        infoFinDePartie.setBounds(200,0,1500,800);
                                        infoFinDePartie.setFont(new Font("Arial", Font.BOLD, 80));
                                        plateauTraitement.verifTerritoires();
                                        if (joueur1.getScore()> joueur2.getScore()&&joueur1.getScore()>joueur3.getScore()  ){
                                            infoFinDePartie.setText("<html><p>"+joueur1.getNom()+" a gagné </p>"+"<p>avec un score de "+joueur1.getScore() +" points </p> </html>");
                                        }
                                        else if  (joueur2.getScore()> joueur1.getScore() && joueur2.getScore()> joueur3.getScore()){
                                            System.out.println("J2 Gagne");
                                            infoFinDePartie.setText("<html><p>"+joueur2.getNom()+" a gagné </p>"+"<p>avec un score de "+joueur2.getScore() +" points </p> </html>");

                                        }
                                        else {
                                            System.out.println("J3 Gagne");
                                            infoFinDePartie.setText("<html><p>"+joueur3.getNom()+" a gagné </p>"+"<p>avec un score de "+joueur3.getScore() +" points </p> </html>");
                                        }
                                        add(infoFinDePartie);
                                        repaint();
                                    }
                                    break;

                                }


                            }
                            if (joueursList.size() == 1) {
                                System.out.println("FIN DE PARTIE ");
                                chrono.stop();
                                chronoPartie.stop();
                                removeAll();
                                infoFinDePartie.setBounds(200,0,1500,800);
                                infoFinDePartie.setFont(new Font("Arial", Font.BOLD, 80));
                                plateauTraitement.verifTerritoires();
                                if (joueur1.getScore()> joueur2.getScore()&&joueur1.getScore()>joueur3.getScore()  ){
                                    infoFinDePartie.setText("<html><p>"+joueur1.getNom()+" a gagné </p>"+"<p>avec un score de "+joueur1.getScore() +" points </p> </html>");
                                }
                                else if  (joueur2.getScore()> joueur1.getScore() && joueur2.getScore()> joueur3.getScore()){
                                    System.out.println("J2 Gagne");
                                    infoFinDePartie.setText("<html><p>"+joueur2.getNom()+" a gagné </p>"+"<p>avec un score de "+joueur2.getScore() +" points </p> </html>");

                                }
                                else {
                                    System.out.println("J3 Gagne");
                                    infoFinDePartie.setText("<html><p>"+joueur3.getNom()+" a gagné </p>"+"<p>avec un score de "+joueur3.getScore() +" points </p> </html>");
                                }
                                add(infoFinDePartie);
                                repaint();
                                break;
                            }

                        }


                    }
                }
                else {
                    System.out.println("FIN DE PARTIE");
                    chrono.stop();
                    chronoPartie.stop();
                    removeAll();
                    infoFinDePartie.setBounds(200,0,1500,800);
                    infoFinDePartie.setFont(new Font("Arial", Font.BOLD, 80));
                    plateauTraitement.verifTerritoires();
                    if (joueur1.getScore()> joueur2.getScore()&&joueur1.getScore()>joueur3.getScore()  ){
                        infoFinDePartie.setText("<html><p>"+joueur1.getNom()+" a gagné </p>"+"<p>avec un score de "+joueur1.getScore() +" points </p> </html>");
                    }
                    else if  (joueur2.getScore()> joueur1.getScore() && joueur2.getScore()> joueur3.getScore()){
                        System.out.println("J2 Gagne");
                        infoFinDePartie.setText("<html><p>"+joueur2.getNom()+" a gagné </p>"+"<p>avec un score de "+joueur2.getScore() +" points </p> </html>");

                    }
                    else {
                        System.out.println("J3 Gagne");
                        infoFinDePartie.setText("<html><p>"+joueur3.getNom()+" a gagné </p>"+"<p>avec un score de "+joueur3.getScore() +" points </p> </html>");
                    }
                    add(infoFinDePartie);
                    repaint();
                }
            }
        });
        megapion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < joueursList.size(); i++) {
                    if (joueursList.get(i).getPeutJouer() && joueursList.get(i).getNbMegaPions()>0){
                        joueursList.get(i).setJoueMegapion(true);

                    }


                }
            }
        });


        passerTour.setBounds(1400, 200, 200, 100);
        abandonner.setBounds(1400, 400, 200, 100);
        sauvegarder.setBounds(1400, 800, 200, 100);
        megapion.setBounds(80, 700, 400, 83);
        add(passerTour);
        add(abandonner);

        add(infoJ1);
        add(infoJ2);
        add(infoJ3);
        add(tempsJoueur);
        add(tempsPartie);
        add(megapion);
        add(megapionBlanc);
        add(megapionNoir);
        add(megapionOrange);


    }

    private InvisibleButton getPlateu19(int pos1, int pos2) {
        return plateau19[pos1][pos2];
    }

    public InvisibleButton getPlateu13(int pos1, int pos2){
        return plateu13[pos1][pos2];

}
    public InvisibleButton getPlateau9(int pos1, int pos2){
        return plateau9[pos1][pos2];
    }
}