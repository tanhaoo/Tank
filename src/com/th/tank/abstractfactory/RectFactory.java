package com.th.tank.abstractfactory;

import com.sun.org.apache.regexp.internal.RE;
import com.th.tank.Bullet;
import com.th.tank.Dir;
import com.th.tank.Group;
import com.th.tank.TankFrame;

/**
 * @author TanHaooo
 * @date 2021/1/12 16:52
 */
public class RectFactory extends GameFactory {
    @Override
    public BaseExplode createExplode(int x, int y, TankFrame tf) {
        return new RectExplode(x, y, tf);
    }

    @Override
    public BaseTank createTank(int x, int y, Dir dir, Group group, TankFrame tf) {
        return new RectTank(x, y, dir, group, tf);
    }

    @Override
    public BaseBullet createBullet(int x, int y, Dir dir, Group group, TankFrame tf) {
        return new RectBullet(x, y, dir, group, tf);
    }
}
