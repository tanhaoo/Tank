package com.th.cor;

import com.th.tank.GameObject;
import com.th.tank.Tank;

/**
 * @author TanHaooo
 * @date 2021/1/16 16:34
 */
public class TankTankCollider implements Collider {
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Tank && o2 instanceof Tank) {
            if (((Tank) o1).getRect().intersects(((Tank) o2).getRect())) {
                ((Tank) o1).back();
                ((Tank) o2).back();
            }
        }
        return true;
    }
}
