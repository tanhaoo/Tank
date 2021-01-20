package com.th.strategy;

import com.th.decorator.RectDecorator;
import com.th.decorator.TailDecorator;
import com.th.tank.*;

/**
 * @author TanHaooo
 * @date 2021/1/15 22:09
 */
public class DefaultFireStrategy implements FireStrategy {
    @Override
    public void fire(Tank t) {
        int bX = t.getX() + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int bY = t.getY() + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;
        GameModel.getInstance().add(new RectDecorator(new TailDecorator(new Bullet(bX, bY, t.getDir(), t.getGroup()))));

        if (t.getGroup() == Group.GOOD) new Thread(() -> new Audio("audio/tank_fire.wav"));
    }
}
