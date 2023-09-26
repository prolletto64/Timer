package it.prolletto64.timer;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Main {

    private static final Color FG = new Color(216, 222, 233);
    private static final Color BG = new Color(48, 54, 65);
    private static final MyFrame frame = new MyFrame();

    public static void main(String[] args) {
        new Thread(() -> {
            int tmpSec=-1;
            LocalDateTime now;
            do {
                now = LocalDateTime.now();
                if(now.getSecond()!=tmpSec){
                    frame.setLabel1text("ORA: "+(DateTimeFormatter.ofPattern("HH:mm:ss").format(now)));
                    frame.repaint();
                }
                System.gc();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }).start();
    }


    private static class MyFrame extends JFrame {
        private final MyLabel l1 = new MyLabel("");
        private final MyLabel l2 = new MyLabel("maye there'll go something");
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