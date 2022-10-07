package gui;

import jeudego.Joueur;
import jeudego.Plateau;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Fenetre {
private CardLayout cardLayout = new CardLayout();
private JPanel mainPanel = new JPanel();

private MenuPrincipal menuPrincipal = new MenuPrincipal();
private Regle regles = new Regle();
private Propos propos = new Propos();
private ModeDeJeu modeDeJeu = new ModeDeJeu();
private Tutoriel tutoriel = new Tutoriel();
private Jeu jeu ;

    public Fenetre() {
        JFrame fenetre = new JFrame("Fenetre");
        fenetre.setExtendedState(JFrame.MAXIMIZED_BOTH);
        fenetre.setLocationRelativeTo(null);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setVisible(true);
        fenetre.setResizable(true);
        mainPanel.setLayout(cardLayout);
        fenetre.setContentPane(mainPanel);
        mainPanel.add(menuPrincipal, "MenuPrincipal");
        mainPanel.add(regles, "Regles");
        mainPanel.add(propos, "Propos");
        mainPanel.add(modeDeJeu, "ModeDeJeu");
        mainPanel.add(tutoriel, "Tutoriel");
        cardLayout.show(mainPanel, "MenuPrincipal");


        //listener

       menuPrincipal.getRegle().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardLayout.show(mainPanel, "Regles");
            }
        });
        menuPrincipal.getPropos().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardLayout.show(mainPanel, "Propos");

            }

        });
        menuPrincipal.getModeDeJeu().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardLayout.show(mainPanel, "ModeDeJeu");

            }

                                                       }
        );
        regles.getRetour().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardLayout.show(mainPanel, "MenuPrincipal");
            }
        });

        propos.getRetour().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardLayout.show(mainPanel, "MenuPrincipal");
            }
        });
        modeDeJeu.getDemarer().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (modeDeJeu.getTaille9().isSelected()) {
                    if (modeDeJeu.getTroisJoueurs().isSelected()) {
                        modeDeJeu.getJoueur1().setNom(modeDeJeu.getNomJoueur1());
                        modeDeJeu.getJoueur2().setNom(modeDeJeu.getNomJoueur2());
                        modeDeJeu.getJoueur3().setNom(modeDeJeu.getNomJoueur3());
                        Plateau plateau = new Plateau(9, modeDeJeu.getJoueur1(), modeDeJeu.getJoueur2(), modeDeJeu.getJoueur3());
                        jeu = new Jeu(9, 3, modeDeJeu.getJoueur1(), modeDeJeu.getJoueur2(), modeDeJeu.getJoueur3(), plateau);
                        mainPanel.add(jeu, "Jeu");
                        cardLayout.show(mainPanel, "Jeu");

                    }
                    if (modeDeJeu.getDeuxJoueurs().isSelected()) {
                        modeDeJeu.getJoueur1().setNom(modeDeJeu.getNomJoueur1());
                        modeDeJeu.getJoueur2().setNom(modeDeJeu.getNomJoueur2());
                        modeDeJeu.getJoueur3().setNom("IA");
                        Plateau plateau = new Plateau(9, modeDeJeu.getJoueur1(), modeDeJeu.getJoueur2(), modeDeJeu.getJoueur3());
                        jeu = new Jeu(9, 2, modeDeJeu.getJoueur1(), modeDeJeu.getJoueur2(), modeDeJeu.getJoueur3(), plateau);
                        mainPanel.add(jeu, "Jeu");
                        cardLayout.show(mainPanel, "Jeu");


                    }
                    if (modeDeJeu.getUnJoueur().isSelected()) {
                        modeDeJeu.getJoueur1().setNom(modeDeJeu.getNomJoueur1());
                        modeDeJeu.getJoueur2().setNom("IA");
                        modeDeJeu.getJoueur3().setNom("IA");
                        Plateau plateau = new Plateau(9, modeDeJeu.getJoueur1(), modeDeJeu.getJoueur2(), modeDeJeu.getJoueur3());
                        jeu = new Jeu(9, 1, modeDeJeu.getJoueur1(), modeDeJeu.getJoueur3(), modeDeJeu.getJoueur3(), plateau);
                        mainPanel.add(jeu, "Jeu");
                        cardLayout.show(mainPanel, "Jeu");

                    }
                }
                if (modeDeJeu.getTaille13().isSelected()) {
                    if(modeDeJeu.getTroisJoueurs().isSelected()){
                        modeDeJeu.getJoueur1().setNom(modeDeJeu.getNomJoueur1());
                        modeDeJeu.getJoueur2().setNom(modeDeJeu.getNomJoueur2());
                        modeDeJeu.getJoueur3().setNom(modeDeJeu.getNomJoueur3());
                        Plateau plateau = new Plateau(13, modeDeJeu.getJoueur1(), modeDeJeu.getJoueur2(), modeDeJeu.getJoueur3());
                        jeu = new Jeu(13,3,modeDeJeu.getJoueur1(),modeDeJeu.getJoueur2(),modeDeJeu.getJoueur3(),plateau);
                        mainPanel.add(jeu, "Jeu");
                        cardLayout.show(mainPanel, "Jeu");
                    }
                    else if(modeDeJeu.getDeuxJoueurs().isSelected()){
                        modeDeJeu.getJoueur1().setNom(modeDeJeu.getNomJoueur1());
                        modeDeJeu.getJoueur2().setNom(modeDeJeu.getNomJoueur2());
                        modeDeJeu.getJoueur3().setNom("IA");
                        Plateau plateau = new Plateau(13, modeDeJeu.getJoueur1(), modeDeJeu.getJoueur2(), modeDeJeu.getJoueur3());
                        jeu = new Jeu(13,2,modeDeJeu.getJoueur1(),modeDeJeu.getJoueur2(),modeDeJeu.getJoueur3(),plateau);
                        mainPanel.add(jeu, "Jeu");
                        cardLayout.show(mainPanel, "Jeu");

                    }
                    else if(modeDeJeu.getUnJoueur().isSelected()){
                        modeDeJeu.getJoueur1().setNom(modeDeJeu.getNomJoueur1());
                        modeDeJeu.getJoueur2().setNom("IA");
                        modeDeJeu.getJoueur3().setNom("IA");
                        Plateau plateau = new Plateau(13, modeDeJeu.getJoueur1(), modeDeJeu.getJoueur2(), modeDeJeu.getJoueur3());
                        jeu = new Jeu(13,1,modeDeJeu.getJoueur1(),modeDeJeu.getJoueur3(),modeDeJeu.getJoueur3(),plateau);
                        mainPanel.add(jeu, "Jeu");
                        cardLayout.show(mainPanel, "Jeu");
                    }
                }
                if (modeDeJeu.getTaille19().isSelected()) {
                    if(modeDeJeu.getTroisJoueurs().isSelected()) {
                        modeDeJeu.getJoueur1().setNom(modeDeJeu.getNomJoueur1());
                        modeDeJeu.getJoueur2().setNom(modeDeJeu.getNomJoueur2());
                        modeDeJeu.getJoueur3().setNom(modeDeJeu.getNomJoueur3());
                        Plateau plateau = new Plateau(19, modeDeJeu.getJoueur1(), modeDeJeu.getJoueur2(), modeDeJeu.getJoueur3());
                        jeu = new Jeu(19, 3, modeDeJeu.getJoueur1(), modeDeJeu.getJoueur2(), modeDeJeu.getJoueur3(), plateau);




                        mainPanel.add(jeu, "Jeu");
                        cardLayout.show(mainPanel, "Jeu");

                    }
                    if(modeDeJeu.getDeuxJoueurs().isSelected()){
                        modeDeJeu.getJoueur1().setNom(modeDeJeu.getNomJoueur1());
                        modeDeJeu.getJoueur2().setNom(modeDeJeu.getNomJoueur2());
                        modeDeJeu.getJoueur3().setNom("IA");
                        Plateau plateau = new Plateau(19, modeDeJeu.getJoueur1(), modeDeJeu.getJoueur2(), modeDeJeu.getJoueur3());

                        jeu = new Jeu(19,2,modeDeJeu.getJoueur1(),modeDeJeu.getJoueur2(),modeDeJeu.getJoueur3(),plateau);
                        mainPanel.add(jeu, "Jeu");
                        cardLayout.show(mainPanel, "Jeu");
                    }
                    if(modeDeJeu.getUnJoueur().isSelected()){
                        modeDeJeu.getJoueur1().setNom(modeDeJeu.getNomJoueur1());
                        modeDeJeu.getJoueur2().setNom("IA");
                        modeDeJeu.getJoueur3().setNom("IA");
                        Plateau plateau = new Plateau(19, modeDeJeu.getJoueur1(), modeDeJeu.getJoueur2(), modeDeJeu.getJoueur3());
                        jeu = new Jeu(19,1,modeDeJeu.getJoueur1(),modeDeJeu.getJoueur2(),modeDeJeu.getJoueur3(),plateau);
                        mainPanel.add(jeu, "Jeu");
                        cardLayout.show(mainPanel, "Jeu");
                    }
                }
            }

        });

        menuPrincipal.getTutoriel().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tutoriel = new Tutoriel();
                mainPanel.add(tutoriel, "Tutoriel");
                cardLayout.show(mainPanel, "Tutoriel");
            }
        });




    }


}
