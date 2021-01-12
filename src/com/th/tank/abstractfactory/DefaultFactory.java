package com.th.tank.abstractfactory;

import com.th.tank.*;

/**
 * @author TanHaooo
 * @date 2021/1/12 16:41
 */
public class DefaultFactory extends GameFactory {
    @Override
    public BaseExplode createExplode(int x, int y, TankFrame tf) {
        return new Explode(x, y, tf);
    }

    @Override
    public BaseTank createTank(int x, int y, Dir dir, Group group, TankFrame tf) {
        return null;
    }

    @Override
    public BaseBullet createBullet(int x, int y, Dir dir, Group group, TankFrame tf) {
        return new Bullet(x, y, dir, group, tf);
    }
}
