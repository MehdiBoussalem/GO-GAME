package gui;
import javax.swing.*;
import java.awt.*;

public class MyButton extends JButton {


    public MyButton(String ico) {
        setIcon(new ImageIcon(ico));
        setContentAreaFilled(false);
        setBorder(BorderFactory.createLineBorder(Color.BLACK,5));

    }
}
