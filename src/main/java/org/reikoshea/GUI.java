package org.reikoshea;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.InputEvent;

public class GUI extends Thread {
    public int msIntervalVal = 1000;
    public volatile boolean shouldRun = true;
    public Robot bot = new Robot();
    private Thread worker;
    public GUI() throws AWTException {
        JFrame frame = new JFrame();

        JButton startButton = new JButton("Start");
        startButton.setSize(300,100);
        JButton stopButton = new JButton("Stop");
        stopButton.setSize(300,100);

        JTextField msInterval = new JTextField("1000");

        JPanel msPanel = new JPanel();
        JLabel msLabel = new JLabel("Interval in ms");
        msLabel.setBounds(0, 0, 250, 30);
        msPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        msPanel.setLayout(new GridLayout(1, 1));
        msPanel.add(msLabel);
        msPanel.add(msInterval);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        buttonPanel.setBounds(100,0,300,200);
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);

        frame.setLayout(new GridLayout(2,1));
        frame.add(msPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Zelda's Autoclicker");
        frame.pack();
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);

        startButton.addActionListener(e -> {
            if (Thread.activeCount() <= 2) {
                System.out.println("Number of threads " + Thread.activeCount());
                System.out.println("Attempting to start new thread");
                ClickThread ct = new ClickThread(msIntervalVal);
                worker = new Thread(ct);
                worker.start();
                System.out.println("Number of threads " + Thread.activeCount());
            }
        });
        stopButton.addActionListener(e -> stopAction());
        msInterval.getDocument().addDocumentListener(
                new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        if (isInteger(msInterval.getText()) == true) {
                            setMsInterval(Integer.parseInt(msInterval.getText()));
                        }
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        if (isInteger(msInterval.getText()) == true) {
                            setMsInterval(Integer.parseInt(msInterval.getText()));
                        }
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        if (isInteger(msInterval.getText()) == true) {
                            setMsInterval(Integer.parseInt(msInterval.getText()));
                        }
                    }
                }
        );

    }

    public void stopAction() {
        worker.interrupt();
    }

    public void setMsInterval(int msInterval) {
        this.msIntervalVal = msInterval;
    }

    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) throws AWTException {
        new GUI();
    }

}
