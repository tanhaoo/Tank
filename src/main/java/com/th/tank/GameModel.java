package com.th.tank;

import com.th.cor.ColliderChain;
import lombok.Data;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * @author TanHaooo
 * @date 2021/1/15 21:14
 */
@Data
public class GameModel {

    private static final GameModel INSTANCE = new GameModel();
    private List<GameObject> objects = new ArrayList<>();
    private HashMap<UUID, Tank> tanks = new HashMap<>();
    ColliderChain chain = new ColliderChain();
    private Tank myTank;
    private int initTankCount = Integer.parseInt((String) PropertyMgr.get("initTankCount"));
    Random random = new Random();

    private GameModel() {

    }

    static {
        INSTANCE.init();
    }

    public static GameModel getInstance() {
        return INSTANCE;
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
//        g.drawString("子弹数量：" + bullets.size(), 10, 60);
//        g.drawString("坦克数量：" + tanks.size(), 10, 80);
//        g.drawString("爆炸数量：" + explodes.size(), 10, 100);
        g.setColor(c);
        //myTank.paint(g);
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

    private void init() {
        //初始化主坦克
        myTank = new Tank(random.nextInt(TankFrame.GAME_WIDTH - Tank.WIDTH),
                random.nextInt(TankFrame.GAME_HEIGHT - Tank.HEIGHT),
                Dir.UP, Group.GOOD);
        //初始化敌方坦克
//        for (int i = 0; i < initTankCount; i++) {
//            new Tank(50 + i * 100, 200, Dir.DOWN, Group.BAD);
//        }

        //初始化墙
        new Wall(150, 150, 220, 50);
        new Wall(550, 150, 220, 50);
        new Wall(300, 300, 50, 200);
        new Wall(550, 300, 50, 200);
        //add(myTank);
    }

    public void save() {
        File file = new File("D:\\Project\\IDEA\\Tank\\src\\main\\resources\\tank.data");
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(file));
//            oos.writeObject(myTank);
//            oos.writeObject(objects);
            Tank tank = null;
            for (GameObject object : objects
            ) {
                if (object instanceof Tank)
                    if (((Tank) object).getGroup() == Group.GOOD)
                        tank = (Tank) object;
            }
            oos.writeObject(tank);
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            if (oos != null)
                try {
                    oos.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
        }
    }

    public void load() {
        File file = new File("D:\\Project\\IDEA\\Tank\\src\\main\\resources\\tank.data");
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
//            myTank = (Tank) ois.readObject();
//            objects = (List) ois.readObject();
            Tank tank = (Tank) ois.readObject();
            myTank = tank;
            for (int i = 0; i < objects.size(); i++) {
                if (objects.get(i) instanceof Tank)
                    if (((Tank) objects.get(i)).getGroup() == Group.GOOD) {
                        objects.set(i, tank);
                    }
            }
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            if (ois != null)
                try {
                    ois.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
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

    public void addHashTank(Tank tank) {
        this.tanks.put(tank.getId(), tank);
    }

    public void removeHashTank(UUID id) {
        this.tanks.remove(id);
    }

    public Bullet findBulletByUUID(UUID id) {
        for (GameObject obj : objects
        ) {
            if (obj instanceof Bullet) {
                if (((Bullet) obj).getId().equals(id))
                    return (Bullet) obj;
            }
        }
        return null;
    }

    public Tank findTankByUUID(UUID id) {
        return tanks.get(id);
    }

}
