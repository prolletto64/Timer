package it.prolletto64.timer;

import org.apache.logging.log4j.Level;

import static it.prolletto64.timer.MyConfig.logger;

public class MyUtilities {
    public static void logError(Throwable e) {
        logger.log(Level.ERROR, e.getMessage());
    }
}
