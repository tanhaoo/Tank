package com.th.cor;

import com.th.tank.Bullet;
import com.th.tank.GameObject;
import com.th.tank.Tank;

/**
 * @author TanHaooo
 * @date 2021/1/16 15:37
 */
public class BulletTankCollider implements Collider {

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Bullet && o2 instanceof Tank) {
            if (((Bullet) o1).collideWith((Tank) o2)) return false;
        } else if (o1 instanceof Tank && o2 instanceof Bullet)
            return collide(o2, o1);
        return true;
    }
}
