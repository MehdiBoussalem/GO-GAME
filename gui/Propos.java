package gui;
import javax.swing.*;
import java.awt.*;

public class Propos extends JPanel {
    JLabel propos= new JLabel();
    MyButton retour = new MyButton("image/retour.png");
    public Propos (){
        setLayout(null);
        String texte="<html> <p> <font size = '7'>Programme réalisé dans le cadre du Projet de GLP à l'université CYU par Mehdi Boussalem,Adrian Sciortino et Florent Courtin </font> </p> </html>";
        propos.setText(texte);
        propos.setBounds(50, 0, 1800, 500);
        add(propos);
        retour.setBounds(1400,800,500,100);
        add(retour);
        setBackground(Color.GRAY);
    }
    MyButton getRetour(){
        return retour;
    }
}
