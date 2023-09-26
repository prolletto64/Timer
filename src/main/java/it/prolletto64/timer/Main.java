package it.prolletto64.timer;

import javax.swing.*;
import java.awt.*;

class Main {
    public static void main(String[] args) {
        Color text = new Color(216, 222, 233);
        Color bg = new Color(48, 54, 65);
        JFrame frame = new JFrame("ORA");
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setIconImage(new ImageIcon("res/icon.png").getImage());
        JLabel label1 = new JLabel("ciao");
        JLabel label2 = new JLabel("ciao ciao");
        frame.setLayout(new GridLayout(2, 1));
        label1.setFont(label1.getFont().deriveFont(22.0f));
        label2.setFont(label1.getFont());
        frame.add(label1);
        frame.add(label2);
        frame.pack();
        frame.setSize(300, frame.getHeight());
        frame.setBackground(bg);
        label1.setOpaque(true);
        label2.setOpaque(true);
        label1.setBackground(bg);
        label2.setBackground(bg);
        label1.setForeground(text);
        label2.setForeground(text);
        frame.repaint();
    }
}