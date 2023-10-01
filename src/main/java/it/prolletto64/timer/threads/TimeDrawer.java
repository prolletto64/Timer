package it.prolletto64.timer.threads;

import it.prolletto64.timer.graphics.MyFrame;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeDrawer extends Thread {
    @SuppressWarnings({"InfiniteLoopStatement", "BusyWait"})
    public TimeDrawer(MyFrame frame) {
        super(() -> {
            int tmpSec = -1;
            LocalDateTime now;
            while (true) {
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
            }
        });
    }
}
