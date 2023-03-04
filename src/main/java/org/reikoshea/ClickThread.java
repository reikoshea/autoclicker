package org.reikoshea;

import java.awt.*;
import java.awt.event.InputEvent;

public class ClickThread implements Runnable {
    int msInterval;

    public ClickThread(int msIntervalVal) {
        this.msInterval = msIntervalVal;
    }

    @Override
    public void run() {
        Robot bot = null;
        try {
            bot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
        System.out.printf("Current Interval: %d\n", msInterval);
        try {
            System.out.println("Sleeping for 5000ms");
            Thread.sleep(5000);
            while(true) {
                System.out.println("clicking...");
                bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                System.out.printf("Done. Sleeping for %d\n", msInterval);
                Thread.sleep(msInterval);
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
