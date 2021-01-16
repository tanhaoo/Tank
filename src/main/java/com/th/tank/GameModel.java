package com.th.tank;

import com.th.cor.BulletTankCollider;
import com.th.cor.Collider;
import com.th.cor.ColliderChain;
import com.th.cor.TankTankCollider;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author TanHaooo
 * @date 2021/1/15 21:14
 */
public class GameModel {

    private List<GameObject> objects = new ArrayList<>();
    //    List<Bullet> bullets = new ArrayList<>();
//    List<Tank> tanks = new ArrayList<>();
//    List<Explode> explodes = new ArrayList<>();
    ColliderChain chain = new ColliderChain();
    private Tank myTank = new Tank(TankFrame.GAME_WIDTH / 2 - Tank.WIDTH / 2, TankFrame.GAME_HEIGHT / 2 - Tank.HEIGHT / 2, Dir.UP, Group.GOOD, this);

    public GameModel() {
        int initTankCount = Integer.parseInt((String) PropertyMgr.get("initTankCount"));
        //初始化敌方坦克
        for (int i = 0; i < initTankCount; i++) {
            add(new Tank(50 + i * 100, 200, Dir.DOWN, Group.BAD, this));
        }
        //add(myTank);
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
//        g.drawString("子弹数量：" + bullets.size(), 10, 60);
//        g.drawString("坦克数量：" + tanks.size(), 10, 80);
//        g.drawString("爆炸数量：" + explodes.size(), 10, 100);
        g.setColor(c);
        myTank.paint(g);
        for (int i = 0; i < objects.size(); i++) {
            //foreach迭代时不允许外部remove元素，只允许自身删除，所以用简单遍历就没事
            objects.get(i).paint(g);
        }

//        //Collision Detect
//        for (int i = 0; i < bullets.size(); i++) {
//            for (int j = 0; j < tanks.size(); j++) {
//                bullets.get(i).collideWith(tanks.get(j));
//            }
//        }
        for (int i = 0; i < objects.size(); i++) {
            for (int j = i + 1; j < objects.size(); j++) {
                chain.collide(objects.get(i), objects.get(j));
            }
        }
    }

    public Tank getMainTank() {
        return myTank;
    }

    public void add(GameObject go) {
        this.objects.add(go);
    }

    public void remove(GameObject go) {
        this.objects.remove(go);
    }

}
