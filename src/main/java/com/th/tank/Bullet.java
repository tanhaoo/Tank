package com.th.tank;

import com.th.net.BulletNewMsg;
import com.th.net.Client;

import java.awt.*;
import java.util.UUID;

/**
 * @author TanHaooo
 * @date 2020/12/29 2:07
 */
public class Bullet extends GameObject {
    private static final int SPEED = 10;
    public static final int WIDTH = ResourceMgr.bulletD.getWidth();
    public static final int HEIGHT = ResourceMgr.bulletD.getHeight();
    private boolean living = true;
    private Dir dir = Dir.DOWN;
    private Group group = Group.BAD;
    private Rectangle rect = new Rectangle();
    private UUID id;

    public Bullet(UUID id, int x, int y, Dir dir, Group group) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        initBullet();
    }

    public Bullet(BulletNewMsg msg) {
        this.id = msg.getId();
        this.x = msg.getX();
        this.y = msg.getY();
        this.dir = msg.getDir();
        this.group = msg.getGroup();
        initBullet();
    }

    public void initBullet() {
        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;
        GameModel.getInstance().add(this);
    }

    public void paint(Graphics g) {
        if (!isLiving())
            GameModel.getInstance().remove(this);
//        g.setColor(Color.RED);
//        g.fillOval(x, y, WIDTH, HEIGHT);
        switch (dir) {
            case DOWN:
                g.drawImage(ResourceMgr.bulletD, x, y, null);
                break;
            case UP:
                g.drawImage(ResourceMgr.bulletU, x, y, null);
                break;
            case LEFT:
                g.drawImage(ResourceMgr.bulletL, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.bulletR, x, y, null);
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
        //update rect
        rect.x = this.x;
        rect.y = this.y;

        if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT)
            living = false;
    }

//    public boolean collideWith(Tank tank) {
//        if (this.group == tank.getGroup()) return false;
//
//        if (rect.intersects(tank.getRect())) {
//            tank.die();
//            this.die();
//            int eX = tank.getX() + Tank.WIDTH / 2 - Explode.WIDTH / 2;
//            int eY = tank.getY() + Tank.HEIGHT / 2 - Explode.HEIGHT / 2;
//            gm.add(new Explode(eX, eY, gm));
//            return true;
//        }
//        return false;
//    }

    public void die() {
        this.living = false;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public boolean isLiving() {
        return living;
    }

    public void setLiving(boolean living) {
        this.living = living;
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

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }
}
