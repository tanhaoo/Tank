package com.th.tank.abstractfactory;

import com.th.tank.Tank;

import java.awt.*;

/**
 * @author TanHaooo
 * @date 2021/1/12 16:38
 */
public abstract class BaseBullet {
    public abstract void paint(Graphics g);

    public abstract void collideWith(BaseTank tank);
}
