package com.th.tank;

import java.awt.*;
import java.awt.event.*;

/**
 * @author TanHaooo
 * @date 2020/12/29 0:05
 */
public class TankFrame extends Frame {
    int x = 200, y = 200;
    boolean bL = false;
    boolean bU = false;
    boolean bR = false;
    boolean bD = false;


    public TankFrame() {
        setSize(800, 600);
        setResizable(false);
        setTitle("Tank War");
        setVisible(true);
        addKeyListener(new KeyAdapter() {
                           @Override
                           public void keyPressed(KeyEvent e) {
                               //按下去
                               int key = e.getKeyCode();
                               switch (key) {
                                   case KeyEvent.VK_LEFT:
                                       bL = true;
                                       break;
                                   case KeyEvent.VK_RIGHT:
                                       bR = true;
                                       break;
                                   case KeyEvent.VK_UP:
                                       bU = true;
                                       break;
                                   case KeyEvent.VK_DOWN:
                                       bD = true;
                                       break;
                               }
                           }

                           @Override
                           public void keyReleased(KeyEvent e) {
                               //抬起来
                               int key = e.getKeyCode();
                               switch (key) {
                                   case KeyEvent.VK_LEFT:
                                       bL = false;
                                       break;
                                   case KeyEvent.VK_RIGHT:
                                       bR = false;
                                       break;
                                   case KeyEvent.VK_UP:
                                       bU = false;
                                       break;
                                   case KeyEvent.VK_DOWN:
                                       bD = false;
                                       break;
                               }
                           }
                       }
        );

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }


    @Override
    public void paint(Graphics g) {
        g.fillRect(x, y, 50, 50);

    }
}

