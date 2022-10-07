package gui;
import jeudego.Joueur;
import jeudego.Minimax;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

public class ModeDeJeu extends JPanel {
    private JLabel background = new JLabel(new ImageIcon("image/Background.jpg"), JLabel.CENTER);
    private MyCheckBox troisJoueurs = new MyCheckBox("image/3Joueurs.png","image/3JoueursSelected.png");
    private MyCheckBox deuxJoueurs = new MyCheckBox("image/2Joueurs.png","image/2JoueursSelected.png");
    private MyCheckBox unJoueur = new MyCheckBox("image/1Joueur.png","image/1JoueurSelected.png");

    private MyCheckBox taille9 = new MyCheckBox("image/taille9.png","image/taille9Selected.png");
    private MyCheckBox taille13 = new MyCheckBox("image/taille13.png","image/taille13Selected.png");
    private MyCheckBox taille19 = new MyCheckBox("image/taille19.png","image/taille19Selected.png");

    private JTextField nomJoueur1 = new JTextField(" Nom joueur 1");
    private JTextField nomJoueur2 = new JTextField(" Nom joueur 2");
    private JTextField nomJoueur3 = new JTextField(" Nom joueur 3");
    private MyButton demarer = new MyButton("image/demarer.png");


    private   Joueur joueur1 = new Joueur("J1","noir",true);
    private  Joueur joueur2 = new Joueur("J2","blanc",false);
    private  Joueur joueur3 = new Joueur("J3","orange",false);
    private Minimax ia1=new Minimax();
    private Minimax ia2=new Minimax();



    public ModeDeJeu() {
        setLayout(null);
        troisJoueurs.setBounds(200,100,300,50);
        deuxJoueurs.setBounds(200,150,300,50);
        unJoueur.setBounds(200,200,300,50);
        taille9.setBounds(1200,100,200,50);
        taille13.setBounds(1200,150,200,50);
        taille19.setBounds(1200,200,200,50);
        nomJoueur1.setBounds(800,100,200,50);
        nomJoueur2.setBounds(800,150,200,50);
        nomJoueur3.setBounds(800,200,200,50);
        demarer.setBounds(600, 600, 500, 100);
        background.setBounds(0, 0, 1920, 1080);




       troisJoueurs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (troisJoueurs.isSelected()) {
                    deuxJoueurs.setSelected(false);
                    unJoueur.setSelected(false);
            }
            }
        });
       deuxJoueurs.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               if (deuxJoueurs.isSelected()) {
                   troisJoueurs.setSelected(false);
                   unJoueur.setSelected(false);
               }

           }
       });
       unJoueur.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               if (unJoueur.isSelected()) {
                   troisJoueurs.setSelected(false);
                   deuxJoueurs.setSelected(false);
               }
           }

       });
       taille9.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               if (taille9.isSelected()) {
                   taille13.setSelected(false);
                   taille19.setSelected(false);
               }
           }
       });
       taille13.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               if (taille13.isSelected()) {
                     taille9.setSelected(false);
                   taille19.setSelected(false);
               }
           }
                                  });
       taille19.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               if (taille19.isSelected()) {
                   taille13.setSelected(false);
                   taille9.setSelected(false);
               }
           }
                                  });







        add(troisJoueurs);
        add(deuxJoueurs);
        add(unJoueur);
        add(taille9);
        add(taille13);
        add(taille19);
        add(nomJoueur1);
        add(nomJoueur2);
        add(nomJoueur3);
        add(demarer);
        add(background);

    }

    public JCheckBox getTroisJoueurs() {
        return troisJoueurs;
    }

    public JCheckBox getDeuxJoueurs() {
        return deuxJoueurs;
    }

    public JCheckBox getUnJoueur() {
        return unJoueur;
    }

    public JCheckBox getTaille13() {
        return taille13;
    }

    public JCheckBox getTaille19() {
        return taille19;
    }

    public String getNomJoueur1() {
        return nomJoueur1.getText();
    }

    public String getNomJoueur2() {
        return nomJoueur2.getText();
    }

    public String getNomJoueur3() {
        return nomJoueur3.getText();
    }

    public MyButton getDemarer() {
        return demarer;
    }
    public Joueur getJoueur1() {
        return joueur1;
    }

    public Joueur getJoueur2() {
        return joueur2;
    }

    public Joueur getJoueur3() {
        return joueur3;
    }

    public Minimax getIa1() {
        return ia1;
    }
    public Minimax getIa2() {
        return ia2;
    }
    public JCheckBox getTaille9() {
        return taille9;
    }
}





