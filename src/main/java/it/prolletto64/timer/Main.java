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

import static it.prolletto64.timer.MyConfig.config;

public class Main {

    private static final MyFrame frame = new MyFrame();

    public static void main(String[] args) {

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
        if (config.get("quotes", "enabled") == null || Objects.equals(config.get("quotes", "enabled"), "true")) {
            new Thread(() -> {
                File file = new File("res/quotes/" + Locale.getDefault());
                if (config.get("quotes", "locale") != null) {
                    file = new File("res/quotes/" + config.get("quotes", "locale"));
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