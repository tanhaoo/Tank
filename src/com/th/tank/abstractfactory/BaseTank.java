package com.th.tank.abstractfactory;

import com.th.tank.Group;

import java.awt.*;

/**
 * @author TanHaooo
 * @date 2021/1/12 16:39
 */
public abstract class BaseTank {
    public Group group = Group.BAD;
    public Rectangle rect = new Rectangle();

    public abstract void paint(Graphics g);

    public Group getGroup() {
        return group;
    }

    public Rectangle getRect() {
        return rect;
    }

    public abstract void die();


    public abstract int getX();

    public abstract int getY();
}
