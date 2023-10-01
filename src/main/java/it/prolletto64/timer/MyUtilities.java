package it.prolletto64.timer;

import org.apache.logging.log4j.Level;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static it.prolletto64.timer.MyConfig.config;
import static it.prolletto64.timer.MyConfig.logger;

public class MyUtilities {
    public static void logError(Throwable e) {
        logger.log(Level.ERROR, e.getMessage());
    }

    public static File getLocaleQuote() {
        File file = new File("res/quotes/" + Locale.getDefault());
        if (!file.exists() || !file.isFile()) {
            if (config.get("quotes", "locale") != null) {
                file = new File("res/quotes/" + config.get("quotes", "locale"));
                if (!file.exists() || !file.isFile()) {
                    file = new File("res/quotes/en_US");
                }
            }
        }
        return file;
    }

    public static List<String> getQuotes() {
        List<String> quotes = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(getLocaleQuote().getPath()));
            String line;
            while (true) {
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                quotes.add(line);
            }
        } catch (IOException e) {
            logError(e);
        }
        return quotes;
    }
}
