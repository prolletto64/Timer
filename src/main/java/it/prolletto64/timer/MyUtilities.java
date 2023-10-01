package it.prolletto64.timer;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Level;

import java.io.*;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static it.prolletto64.timer.MyConfig.config;
import static it.prolletto64.timer.MyConfig.logger;

public class MyUtilities {
    public static void logError(Throwable e) {
        logger.log(Level.ERROR, e.getMessage());
    }

    public static File getLocaleQuote() {
        File file = getFileJarSafe("res/quotes/" + Locale.getDefault());
        if (!config.get("quotes", "locale").isEmpty() && config.get("quotes", "locale") != null){
            file = getFileJarSafe("res/quotes/" + config.get("quotes", "locale"));
        }
        if (!file.exists() || !file.isFile()) {
            file = getFileJarSafe("res/quotes/en_US");
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

    public static File getFileJarSafe(String path) {
        String protocol = Main.class.getResource("Main.class").getProtocol();
        if (Objects.equals(protocol, "jar")) {
            try {
                URL jar = Main.class.getProtectionDomain().getCodeSource().getLocation();
                String s = jar.toString().substring("file:".length());
                Path jarFile;
                if (s.contains("C:")) {
                    jarFile = Paths.get(s.substring("/C:".length()));
                } else {
                    jarFile = Paths.get(s);
                }
                FileSystem fs = FileSystems.newFileSystem(jarFile, (ClassLoader) null);
                InputStream is = Files.newInputStream(fs.getPath(path));
                Files.createDirectories(Path.of(path).getParent());
                FileOutputStream os = new FileOutputStream(path);
                IOUtils.copy(is, os);
                is.close();
                os.flush();
                os.close();
                System.gc();
            } catch (IOException e) {
                logError(e);
            }
        }
        return new File(path);
    }
}
