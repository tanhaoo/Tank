package com.th.tank;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {


    public static void main(String[] args) {
        // write your code here
        TankFrame tf = new TankFrame();
        while (true){
            try {
                Thread.sleep(50);
                tf.repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
