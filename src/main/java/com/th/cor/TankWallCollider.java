package com.th.cor;

import com.th.tank.GameObject;
import com.th.tank.Tank;
import com.th.tank.Wall;

/**
 * @author TanHaooo
 * @date 2021/1/16 19:09
 */
public class TankWallCollider implements Collider {
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Tank && o2 instanceof Wall) {
            Tank t = (Tank) o1;
            Wall w = (Wall) o2;
            if (t.getRect().intersects(w.getRect())) {
                t.back();
                return true;
            }
        } else if (o1 instanceof Wall && o2 instanceof Tank)
            return collide(o2, o1);
        return true;
    }
}
