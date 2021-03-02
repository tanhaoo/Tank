package com.th.cor;

import com.th.tank.Bullet;
import com.th.tank.GameObject;
import com.th.tank.Wall;

/**
 * @author TanHaooo
 * @date 2021/1/16 18:54
 */
public class BulletWallCollider implements Collider {
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Bullet && o2 instanceof Wall) {
            Bullet b = (Bullet) o1;
            Wall w = (Wall) o2;
            if (b.getRect().intersects(w.getRect())) {
                b.die();
                return true;
            }
        } else if (o1 instanceof Wall && o2 instanceof Bullet)
            return collide(o2, o1);
        return true;
    }
}
