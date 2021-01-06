package com.th.tank;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {


    public static void main(String[] args) {
        // write your code here
        TankFrame tf = new TankFrame();
        //初始化敌方坦克
        for (int i = 0; i < 5; i++) {
            tf.tanks.add(new Tank(50 + i * 100, 200, Dir.DOWN, Group.BAD,tf));
        }
        while (true) {
            try {
                Thread.sleep(50);
                tf.repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
