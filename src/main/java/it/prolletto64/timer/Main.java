package it.prolletto64.timer;

import it.prolletto64.timer.graphics.MyFrame;
import it.prolletto64.timer.threads.QuoteDrawer;
import it.prolletto64.timer.threads.TimeDrawer;

import static it.prolletto64.timer.MyConfig.areQuotesEnabled;

public class Main {

    private static final MyFrame frame = new MyFrame();

    public static void main(String[] args) {

        new TimeDrawer(frame).start();
        if (areQuotesEnabled()) {
            new QuoteDrawer(frame).start();
        } else {
            frame.removeL2();
        }
    }


}