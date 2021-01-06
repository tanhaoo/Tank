package com.th.tank;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author TanHaooo
 * @date 2020/12/29 0:05
 */
public class TankFrame extends Frame {
    boolean bL = false;
    boolean bU = false;
    boolean bR = false;
    boolean bD = false;
    static final int GAME_WIDTH = 1080, GAME_HEIGHT = 960;
    List<Bullet> bullets = new ArrayList<Bullet>();
    List<Tank> tanks = new ArrayList<>();
    List<Explode> explodes = new ArrayList<>();
    Tank myTank = new Tank(GAME_WIDTH / 2 - Tank.WIDTH / 2, GAME_HEIGHT / 2 - Tank.HEIGHT / 2, Dir.UP, Group.GOOD, this);

    public TankFrame() {
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
                                       myTank.fire();
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
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹数量：" + bullets.size(), 10, 60);
        g.drawString("坦克数量：" + tanks.size(), 10, 80);
        g.drawString("爆炸数量：" + explodes.size(), 10, 100);
        g.setColor(c);
        myTank.paint(g);
        for (int i = 0; i < bullets.size(); i++) {
            //foreach迭代时不允许外部remove元素，只允许自身删除，所以用简单遍历就没事
            bullets.get(i).paint(g);
        }
        for (int i = 0; i < tanks.size(); i++) {
            tanks.get(i).paint(g);
        }
        for (int i = 0; i < explodes.size(); i++) {
            explodes.get(i).paint(g);
        }
        //Collision Detect
        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < tanks.size(); j++) {
                bullets.get(i).collideWith(tanks.get(j));
            }
        }

    }
}

