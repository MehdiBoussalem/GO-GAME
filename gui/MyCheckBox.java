package gui;
import javax.swing.*;

public class MyCheckBox extends JCheckBox {
    public MyCheckBox(String iconPath,String iconSelectedPath) {
        setSelected(false);
        setContentAreaFilled(false);
        setIcon(new ImageIcon(iconPath));
        setSelectedIcon(new ImageIcon(iconSelectedPath));
    }
}
