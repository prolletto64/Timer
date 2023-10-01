package it.prolletto64.timer.threads;

import it.prolletto64.timer.graphics.MyFrame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static it.prolletto64.timer.MyConfig.config;

public class QuoteDrawer extends Thread {

    public QuoteDrawer(MyFrame frame) {
        super(() -> {
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
        });
    }
}
