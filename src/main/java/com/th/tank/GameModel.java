package com.th.tank;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author TanHaooo
 * @date 2021/1/15 21:14
 */
public class GameModel {

    List<Bullet> bullets = new ArrayList<>();
    List<Tank> tanks = new ArrayList<>();
    List<Explode> explodes = new ArrayList<>();
    private Tank myTank = new Tank(TankFrame.GAME_WIDTH / 2 - Tank.WIDTH / 2, TankFrame.GAME_HEIGHT / 2 - Tank.HEIGHT / 2, Dir.UP, Group.GOOD, this);


    public GameModel() {
        int initTankCount = Integer.parseInt((String) PropertyMgr.get("initTankCount"));
        //初始化敌方坦克
        for (int i = 0; i < initTankCount; i++) {
            tanks.add(new Tank(50 + i * 100, 200, Dir.DOWN, Group.BAD, this));
        }
    }

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

    public Tank getMainTank() {
        return myTank;
    }
}
