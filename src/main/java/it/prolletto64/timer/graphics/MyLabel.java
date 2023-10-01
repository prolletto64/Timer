package it.prolletto64.timer.graphics;

import javax.swing.*;

import static it.prolletto64.timer.MyConfig.BG;
import static it.prolletto64.timer.MyConfig.FG;

public class MyLabel extends JLabel {
    public MyLabel(String text) {
        super(text);
        this.setFont(this.getFont().deriveFont(22.0f));
        this.setForeground(FG);
        this.setBackground(BG);
        this.setOpaque(true);
    }
}