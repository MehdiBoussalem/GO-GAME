package gui;
import javax.swing.*;
import java.awt.*;
//import java.awt.geom.Rectangle2D;

public class PlateauGui extends JComponent {
    private final double longueur=24;
    private final double largeur=22;
    private int nbcase;
    PlateauGui(int nbcase){
        this.nbcase=nbcase;
    }
    public void paint(Graphics g) {
        if (getNbcases() == 19) {
            Graphics2D g2 = (Graphics2D) g;
            int x = getWidth() / nbcase;
            int y = getHeight() / nbcase;
            for (int i = 0; i < (nbcase); i++) {
                g2.drawLine(x * i, 0, x * i, getHeight() - 44);
                //for(int j=0;j<nbcase;j++){
                g2.drawLine(0, y * i, getWidth() - 44, y * i);

                //}
            }
            //g2.setStroke(new BasicStroke(2.0f));
            //g2.draw(new Rectangle2D.Double(1,1,17.29*longueur,19.6*largeur));
            g2.dispose();
        }
        else if(getNbcases()==13){
            Graphics2D g2 = (Graphics2D) g;
            int x = getWidth() / nbcase;
            int y = getHeight() / nbcase;
            for (int i = 0; i < (nbcase); i++) {
                g2.drawLine(x * i, 0, x * i, getHeight() - 68);
                g2.drawLine(0, y * i, getWidth() - 68, y * i);


            }
            //g2.setStroke(new BasicStroke(2.0f));
            //g2.draw(new Rectangle2D.Double(1,1,17.29*longueur,19.6*largeur));
            g2.dispose();
        }
        else if(getNbcases()==9){
            Graphics2D g2 = (Graphics2D) g;
            int x = getWidth() / nbcase;
            int y = getHeight() / nbcase;
            for (int i = 0; i < (nbcase); i++) {
                g2.drawLine(x * i, 0, x * i, getHeight() - 96);
                g2.drawLine(0, y * i, getWidth() - 96, y * i);

            }
            //g2.setStroke(new BasicStroke(2.0f));
            //g2.draw(new Rectangle2D.Double(1,1,17.29*longueur,19.6*largeur));
            g2.dispose();
        }
    }
    public Graphics getGraphics(Graphics g){
        return g;
    }
    public int getNbcases(){return this.nbcase;}



}