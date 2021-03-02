package com.th.tank;

import com.th.cor.TankTankCollider;
import com.th.net.BulletNewMsg;
import com.th.net.Client;
import com.th.net.TankDieMsg;
import com.th.net.TankStateMsg;
import com.th.observer.TankFireEvent;
import com.th.observer.TankFireHandler;
import com.th.observer.TankFireObserver;
import com.th.strategy.FireStrategy;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author TanHaooo
 * @date 2020/12/29 1:43
 */

public class Tank extends GameObject {
    public static final int WIDTH = ResourceMgr.goodTankU.getWidth();
    public static final int HEIGHT = ResourceMgr.goodTankU.getHeight();
    private int oldX, oldY;
    private Dir dir = Dir.DOWN;
    private final int SPEED = 5;
    private boolean moving = true;
    private TankFrame tf;
    private boolean living = true;
    private Random random = new Random();
    private Group group = Group.BAD;
    private UUID id = UUID.randomUUID();
    private Rectangle rect = new Rectangle();
    private FireStrategy fs;

    public Tank(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        initTank();
    }

    public Tank(TankStateMsg msg) {
        this.x = msg.getX();
        this.y = msg.getY();
        this.dir = msg.getDir();
        this.group = msg.getGroup();
        this.id = msg.getId();
        this.moving = false;
        initTank();
    }

    public void initTank() {
        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;
        try {
            fs = (group == Group.GOOD ? (FireStrategy) Class.forName((String) PropertyMgr.get("goodFS")).newInstance()
                    : (FireStrategy) Class.forName((String) PropertyMgr.get("badFS")).newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        GameModel.getInstance().addHashTank(this);
        GameModel.getInstance().add(this);
    }

    public void paint(Graphics g) {
//        g.setColor(Color.BLACK);
//        g.fillRect(x, y, WIDTH, HEIGHT);
        //加UUID标识
        Color c = g.getColor();
        g.setColor(Color.YELLOW);
        g.drawString(id.toString(), this.x, this.y - 10);
        g.setColor(c);

        if (!living) {
            GameModel.getInstance().remove(this);
            GameModel.getInstance().removeHashTank(id);
        }
        switch (dir) {
            case DOWN:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankD : ResourceMgr.badTankD, x, y, null);
                break;
            case UP:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankU : ResourceMgr.badTankU, x, y, null);
                break;
            case LEFT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankL : ResourceMgr.badTankL, x, y, null);
                break;
            case RIGHT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankR : ResourceMgr.badTankR, x, y, null);
                break;
        }
        move();
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    private void move() {
        oldX = x;
        oldY = y;
        if (!isMoving()) return;
        switch (dir) {
            case LEFT:
                x -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case UP:
                y -= SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
        }
//        if (this.group == Group.BAD && random.nextInt(100) > 95) {
//            randomDir();
//            this.fire();
//        }
        boundsCheck();
        //update rect
        rect.x = this.x;
        rect.y = this.y;
    }

    private void boundsCheck() {
        if (this.x < 2) x = 2;
        if (this.y < 28) y = 28;
        if (this.x > TankFrame.GAME_WIDTH - Tank.WIDTH) x = TankFrame.GAME_WIDTH - Tank.WIDTH;
        if (this.y > TankFrame.GAME_HEIGHT - Tank.HEIGHT) y = TankFrame.GAME_HEIGHT - Tank.HEIGHT;
    }

    public void back() {
        this.x = oldX;
        this.y = oldY;
    }

    private List<TankFireObserver> fireObservers = Arrays.asList(
            new TankFireHandler(),
            (o1) -> {
                Tank t = o1.getSource();
            });
    //这边其实只有一个TankFireHandler，下面这个lambda表达式是测试没有实际意义，为了知道这样一个一个添加观察者事件

    public void handleFireKey() {
        TankFireEvent event = new TankFireEvent(this);
        for (TankFireObserver o : fireObservers
        ) {
            o.actionOnFire(event);
        }
    }

    private void randomDir() {
        this.dir = Dir.values()[random.nextInt(4)];
    }

    public void fire() {
        fs.fire(this);
    }

    public void die() {
        this.living = false;
    }

    public boolean isLiving() {
        return living;
    }

    public void setLiving(boolean living) {
        this.living = living;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public int getSPEED() {
        return SPEED;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
