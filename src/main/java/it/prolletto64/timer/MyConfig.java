package it.prolletto64.timer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ini4j.Wini;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static it.prolletto64.timer.MyUtilities.logError;

public class MyConfig {
    public static final Color FG = new Color(216, 222, 233);
    public static final Color BG = new Color(48, 54, 65);
    public static final Logger logger = LogManager.getLogger(Main.class);
    public static final Wini config = getConfig("config.ini");

    private static Wini getConfig(@SuppressWarnings("SameParameterValue") String path) {
        Wini ini = null;
        File f = new File(path);
        if (!f.exists() || !f.isFile()) {
            ini = createDefaultConfig(f);
        } else {
            try {
                ini = new Wini(f);
            } catch (IOException e) {
                logError(e);
            }
        }
        assert ini != null;
        return ini;
    }

    private static Wini createDefaultConfig(File f) {
        Wini ini = null;
        try {
            ini = new Wini(f);
            ini.put("quotes", "enabled", true);
            ini.put("quotes", "locale", "en_US");
            ini.store();
        } catch (IOException e) {
            logError(e);
        }
        assert ini != null;
        return ini;
    }


}
