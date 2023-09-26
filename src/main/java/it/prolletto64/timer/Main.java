package it.prolletto64.timer;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

class Main {

    private static final Color FG = new Color(216, 222, 233);
    private static final Color BG = new Color(48, 54, 65);
    private static final JFrame frame = new MyFrame();

    public static void main(String[] args) {
        JLabel label1 = new JLabel("ciao");
        JLabel label2 = new JLabel("maybe will put something");
        label1.setFont(label1.getFont().deriveFont(22.0f));
        label2.setFont(label1.getFont());
        frame.add(label1);
        frame.add(label2);
        frame.pack();
        frame.setBackground(BG);
        label1.setOpaque(true);
        label2.setOpaque(true);
        label1.setBackground(BG);
        label2.setBackground(BG);
        label1.setForeground(FG);
        label2.setForeground(FG);
        frame.repaint();
        LocalDateTime now;
        LocalDateTime tmp = LocalDateTime.now(ZoneId.systemDefault());
        int tmpSeconds2 = 61;
        String etichetta1;
        while (true) {
            now = LocalDateTime.now(ZoneId.systemDefault());
            if (now.getHour() < 10) {
                etichetta1 = "0" + now.getHour() + ":";
            } else {
                etichetta1 = now.getHour() + ":";
            }
            if (now.getMinute() < 10) {
                etichetta1 += "0" + now.getMinute() + ":";
            } else {
                etichetta1 += now.getMinute() + ":";
            }
            if (now.getSecond() < 10) {
                etichetta1 += "0" + now.getSecond();
            } else {
                etichetta1 += Integer.toString(now.getSecond());
            }
            label1.setText("ORA: " + etichetta1);
            if (now.getSecond() != tmpSeconds2) {
                tmp = tmp.withSecond(0);
                frame.repaint();
                tmpSeconds2 = now.getSecond();
                System.gc();
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class MyFrame extends JFrame {
        public MyFrame() {
            super("Ora");
            this.setAlwaysOnTop(true);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setResizable(false);
            this.setVisible(true);
            this.setIconImage(new ImageIcon("res/icon.png").getImage());
            this.setLayout(new GridLayout(2, 1));
        }

        @Override
        public void pack() {
            super.pack();
            this.setSize(300, this.getHeight());
            this.repaint();
        }
    }
}