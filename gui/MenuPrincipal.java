package gui;

import javax.swing.*;

public class MenuPrincipal extends JPanel {
    private MyButton modeDeJeu = new MyButton("image/modeDeJeu.png");
    private MyButton charger = new MyButton("image/charger.png");
    private MyButton regle = new MyButton("image/regle.png");
    private MyButton tutoriel = new MyButton("image/tutoriel.png");
    private MyButton propos = new MyButton("image/propos.png");
    private JLabel background = new JLabel(new ImageIcon("image/Background.jpg"), JLabel.CENTER);

    public MenuPrincipal() {
        setLayout(null);
        modeDeJeu.setBounds(710, 100, 500, 100);
        charger.setBounds(710, 250, 500, 100);
        regle.setBounds(710, 400, 500, 100);
        tutoriel.setBounds(710, 550, 500, 100);
        propos.setBounds(710, 700, 500, 100);
        background.setBounds(0, 0, 1920, 1080);
        background.setSize(1920, 1080);
        charger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JFileChooser chooser = new JFileChooser();
                if (chooser.showDialog(null,"Ouvrir") == JFileChooser.APPROVE_OPTION){

                    String path=chooser.getSelectedFile().getAbsolutePath();
                    JOptionPane.showMessageDialog(charger, "le fichier choisi est "+ path);
                }
            }
        });
        add(modeDeJeu);

        add(regle);
        add(tutoriel);
        add(propos);
        add(background);
        System.out.println("test");
    }

    public JButton getModeDeJeu() {
        return modeDeJeu;
    }

    public JButton getCharger() {
        return charger;
    }

    public JButton getRegle() {
        return regle;
    }

    public JButton getTutoriel() {
        return tutoriel;
    }

    public JButton getPropos() {
        return propos;
    }


    }
