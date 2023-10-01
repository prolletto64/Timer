package it.prolletto64.timer;

import it.prolletto64.timer.graphics.MyFrame;
import org.ini4j.Wini;

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

public class Main {

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


}