package com.th.tank;

import lombok.Data;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author TanHaooo
 * @date 2020/12/29 0:05
 */
@Data
public class TankFrame extends Frame {
    public static final TankFrame INSTANCE=new TankFrame();
    boolean bL = false;
    boolean bU = false;
    boolean bR = false;
    boolean bD = false;
    static final int GAME_WIDTH = 1080, GAME_HEIGHT = 960;
    GameModel gm = GameModel.getInstance();
    Tank myTank = gm.getMainTank();

    private TankFrame() {
        myTank.setMoving(false);
        setSize(GAME_WIDTH, GAME_HEIGHT);
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
                                   case KeyEvent.VK_SPACE:
                                       myTank.handleFireKey();
                                       break;
                                   case KeyEvent.VK_S:
                                       gm.save();
                                       break;
                                   case KeyEvent.VK_L:
                                       gm.load();
                                       myTank=gm.getMainTank();
                                       break;
                                   default:
                                       break;
                               }
                               setMainTankDir();
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
                                   default:
                                       break;
                               }
                               setMainTankDir();
                           }

                           private void setMainTankDir() {
                               if (!bL && !bU && !bR && !bD)
                                   myTank.setMoving(false);
                               else {
                                   myTank.setMoving(true);
                                   if (bL) myTank.setDir(Dir.LEFT);
                                   if (bU) myTank.setDir(Dir.UP);
                                   if (bR) myTank.setDir(Dir.RIGHT);
                                   if (bD) myTank.setDir(Dir.DOWN);
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

    //双缓冲解决闪烁问题
    /**
     * repaint->update
     * 截获update
     * 把图片画在内存
     * 把内存图片一次性画到屏幕上（内存内容复制到显存）
     **/
    Image offScreenImage = null;    //内存定义一张图片

    @Override
    public void update(Graphics g) {//update方法在paint方法前被调用
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);//有多大画多大
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);//在内存里构图完成
        g.drawImage(offScreenImage, 0, 0, null);//从内存画到屏幕上
    }

    @Override
    public void paint(Graphics g) {
        gm.paint(g);
    }
}

