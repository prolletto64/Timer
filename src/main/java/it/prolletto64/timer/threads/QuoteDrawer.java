package it.prolletto64.timer.threads;

import it.prolletto64.timer.graphics.MyFrame;

import java.util.List;

import static it.prolletto64.timer.MyUtilities.getQuotes;
import static it.prolletto64.timer.MyUtilities.logError;

public class QuoteDrawer extends Thread {

    @SuppressWarnings({"InfiniteLoopStatement", "BusyWait"})
    public QuoteDrawer(MyFrame frame) {
        super(() -> {
            List<String> quotes = getQuotes();
            if (quotes.isEmpty()) {
                frame.removeL2();
                return;
            }
            while (true) {
                frame.setLabel2text(quotes.get((int) (Math.random() * (quotes.size()))));
                frame.pack();
                try {
                    Thread.sleep((int) (30000 + (Math.random() * ((300000 - 30000) + 1))));
                } catch (InterruptedException e) {
                    logError(e);
                }
            }
        });
    }
}
