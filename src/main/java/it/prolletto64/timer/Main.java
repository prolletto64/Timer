package it.prolletto64.timer;

import it.prolletto64.timer.graphics.MyFrame;
import it.prolletto64.timer.threads.QuoteDrawer;
import it.prolletto64.timer.threads.TimeDrawer;

import java.util.Objects;

import static it.prolletto64.timer.MyConfig.config;

public class Main {

    private static final MyFrame frame = new MyFrame();

    public static void main(String[] args) {

        new TimeDrawer(frame).start();
        if (config.get("quotes", "enabled") == null || Objects.equals(config.get("quotes", "enabled"), "true")) {
            new QuoteDrawer(frame).start();
        } else {
            frame.removeL2();
        }
    }


}