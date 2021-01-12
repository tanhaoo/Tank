package com.th.tank.abstractfactory;

import com.th.tank.Dir;
import com.th.tank.Group;
import com.th.tank.TankFrame;

/**
 * @author TanHaooo
 * @date 2021/1/12 16:37
 */
public abstract class GameFactory {
    public abstract BaseExplode createExplode(int x, int y, TankFrame tf);

    public abstract BaseTank createTank(int x, int y, Dir dir, Group group, TankFrame tf);

    public abstract BaseBullet createBullet(int x, int y, Dir dir, Group group, TankFrame tf);
}
