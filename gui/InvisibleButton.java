package gui;
import java.awt.Graphics;

import javax.swing.*;

@SuppressWarnings("serial")
class InvisibleButton extends JButton {
    public InvisibleButton(){
        super();
        setOpaque(false);
        getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
    }
    @Override
    public void paint(Graphics g){
        // Do nothing here
    }
}