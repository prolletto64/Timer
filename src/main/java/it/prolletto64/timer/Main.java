package it.prolletto64.timer;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

class Main {

    private static final Color FG = new Color(216, 222, 233);
    private static final Color BG = new Color(48, 54, 65);
    private static final MyFrame frame = new MyFrame();

    public static void main(String[] args) {
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
            frame.setLabel1text("ORA: " + etichetta1);
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
        private final MyLabel l1 = new MyLabel("");
        private final MyLabel l2 = new MyLabel("maye here'll go something");
        public MyFrame() {
            super("Ora");
            this.setAlwaysOnTop(true);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setResizable(false);
            this.setVisible(true);
            this.setIconImage(new ImageIcon("res/icon.png").getImage());
            this.setLayout(new GridLayout(2, 1));
            this.setBackground(BG);
            this.add(l1);
            this.add(l2);
            this.pack();
            this.setSize(300, this.getHeight());
            this.repaint();
        }

        public void setLabel1text(String text) {
            l1.setText(text);
        }

        public void setLabel2text(String text) {
            l2.setText(text);
        }
    }

    private static class MyLabel extends JLabel {
        private MyLabel(String text) {
            super(text);
            this.setFont(this.getFont().deriveFont(22.0f));
            this.setForeground(FG);
            this.setBackground(BG);
            this.setOpaque(true);
        }
    }
}