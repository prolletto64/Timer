package it.prolletto64.timer.graphics;

import javax.swing.*;
import java.awt.*;

import static it.prolletto64.timer.MyConfig.BG;
import static it.prolletto64.timer.MyUtilities.getFileJarSafe;
import static it.prolletto64.timer.MyUtilities.windowClosingManager;

public class MyFrame extends JFrame {
    private final MyLabel l1 = new MyLabel("");
    private final MyLabel l2 = new MyLabel("maye there'll go something");

    public MyFrame() {
        super("Ora");
        this.setAlwaysOnTop(true);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(windowClosingManager);
        this.setResizable(false);
        this.setVisible(true);
        this.setIconImage(new ImageIcon(getFileJarSafe("res/icon.png").getPath()).getImage());
        this.setLayout(new GridLayout(2, 1));
        this.setBackground(BG);
        this.add(l1);
        this.add(l2);
        this.resetSize();
    }

    private void resetSize() {
        this.setLayout(new GridLayout(l2.isVisible() ? 2 : 1, 1));
        int padding = l2.isVisible() ? 0 : 30;
        this.setSize(300, this.getHeight() + padding);
        this.repaint();
    }

    public void setLabel1text(String text) {
        l1.setText(text);
    }

    public void setLabel2text(String text) {
        l2.setText("<html><p style=\"width:300px\">" + text + "</p></html>");
    }

    public void removeL2() {
        this.remove(l2);
        l2.setVisible(false);
        this.resetSize();
    }
}
