package com.th.cor;

import com.th.tank.Bullet;
import com.th.tank.Explode;
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
            Bullet b = (Bullet) o1;
            Tank t = (Tank) o2;
            if (b.getGroup() == t.getGroup()) return true;

            if (b.getRect().intersects(t.getRect())) {
                t.die();
                b.die();
                int eX = t.getX() + Tank.WIDTH / 2 - Explode.WIDTH / 2;
                int eY = t.getY() + Tank.HEIGHT / 2 - Explode.HEIGHT / 2;
                new Explode(eX, eY);
                return false;
            }
        } else if (o1 instanceof Tank && o2 instanceof Bullet)
            return collide(o2, o1);
        return true;
    }
}
