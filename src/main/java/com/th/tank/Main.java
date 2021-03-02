package com.th.tank;

import com.th.net.Client;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {

    public static void main(String[] args) {
        TankFrame tf = TankFrame.INSTANCE;
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(50);
                    tf.repaint();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Client.INSTANCE.connect();
    }
}
