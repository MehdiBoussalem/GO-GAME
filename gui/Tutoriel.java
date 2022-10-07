package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Tutoriel  extends JPanel {

    private PlateauGui plateau ;
    private JLabel pion = new JLabel(new ImageIcon("image/pion-noir.png"));
    private JLabel pion2 = new JLabel(new ImageIcon("image/pion-noir.png"));
    private JLabel pion3 = new JLabel(new ImageIcon("image/pion-noir.png"));
    private JLabel pion4 = new JLabel(new ImageIcon("image/pion-noir.png"));


    private JLabel texte = new JLabel();
    private JLabel pionBlanc = new JLabel(new ImageIcon("image/pion-blanc.png"));
    private Timer chrono;
    private int temps ;

    public Tutoriel(){
        setBackground(new Color(186, 138, 72));
        setLayout(null);
        chrono = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            temps++;

                if (temps==10){


                    pionBlanc.setBounds(835,390,36,36 );

                    texte.setText("<html> <p> Ici le pion posséde 3 libertés </p>"+"<p>car il y a un pion au dessus de lui  </p>"    +" </html>");

                }

                if (temps ==20){
                    pion2.setBounds(835,567,36,36 );
                    pionBlanc.setVisible(false);
                    texte.setText("<html> <p> Ici les pion noirs sont allignés  </p>"+"<p>donc il forme une chaine de 6 libértées  </p>"    +" </html>");


                }
                if (temps == 30){
                    pionBlanc.setVisible(true);
                    pion2.setBounds(835,320-17,36,36);
                    pion3.setBounds(763-16,408-17,36,36);
                    pion4.setBounds(938-16,408-17,36,36);
                    texte.setText("<html> <p> Ici le pion blanc est capturé  </p>"+"<p>car il ne possède plus de libértées   </p>"    +" </html>");
                }
                if (temps == 40){
                    pionBlanc.setVisible(false);
                    texte.setText("<html> <p> Ici les noirs ont un territoire  </p>"+"<p>car ils ont entourés une intersection vide   </p>"    +" </html>");

                    chrono.stop();
                }



            }
        });





        plateau = new PlateauGui(9);
        plateau.setBounds(499, 144, 800, 800);
        add(plateau);
        pion.setBounds(835,479,36,36);
        add(pion);
        add(pionBlanc);
        add(pion2);
        add(pion3);
        add(pion4);
        texte.setBounds(1300,100,600,400);
        texte.setFont(new Font("Arial",Font.BOLD,25));
        texte.setText("<html> <p> Ici le pion posséde 4 libertés </p>"+"<p>car il n'y a aucun pion autour de lui  </p>"    +" </html>");
        add(texte);
        chrono.start();




        addMouseListener(new MouseListener() {


            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                int x=e.getX();
                int y=e.getY();
                System.out.println(x+","+y);//these co-ords are relative to the component
            }
        });
    }


}
