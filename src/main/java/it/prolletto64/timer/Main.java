package it.prolletto64.timer;

import org.ini4j.Wini;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

class Main {

    private static final Color FG = new Color(216, 222, 233);
    private static final Color BG = new Color(48, 54, 65);
    private static final MyFrame frame = new MyFrame();
    private static Wini config = null;

    public static void main(String[] args) {
        File f = new File("config.ini");
        if (!f.exists() || !f.isFile()) {
            try {
                Wini ini = new Wini(f);
                ini.put("settings", "quotes", true);
                ini.put("settings", "locale", "en_US");
                ini.store();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        new Thread(() -> {
            int tmpSec = -1;
            LocalDateTime now;
            do {
                now = LocalDateTime.now();
                if (now.getSecond() != tmpSec) {
                    frame.setLabel1text("ORA: " + (DateTimeFormatter.ofPattern("HH:mm:ss").format(now)));
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
        try {
            config = new Wini(new File("config.ini"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (config.get("settings", "quotes") == null || Objects.equals(config.get("settings", "quotes"), "true")) {
            new Thread(() -> {
                File file = new File("res/quotes/" + Locale.getDefault());
                if (config.get("settings", "locale") != null) {
                    file = new File("res/quotes/" + config.get("settings", "locale"));
                }
                if (!file.exists() || !file.isFile()) {
                    file = new File("res/quotes/en_US");
                    if (!file.exists() || !file.isFile()) {
                        return;
                    }
                }
                List<String> quotes = new ArrayList<>();
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file.getPath()));
                    String line;
                    do {
                        line = reader.readLine();
                        if (line == null) {
                            break;
                        }
                        quotes.add(line);
                    } while (true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (quotes.isEmpty()) {
                    frame.removeL2();
                    return;
                }
                while (true) {
                    frame.setLabel2text(quotes.get((int) (Math.random() * (quotes.size() + 1))));
                    frame.pack();
                    try {
                        Thread.sleep((int) (30000 + (Math.random() * ((300000 - 30000) + 1))));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            frame.removeL2();
        }
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
            this.resetSize();
        }

        @Override
        public void pack() {
            super.pack();
            this.repaint();
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