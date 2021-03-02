package com.th.cor;

import com.th.tank.GameObject;

/**
 * @author TanHaooo
 * @date 2021/1/16 15:35
 */
public interface Collider {
    boolean collide(GameObject o1,GameObject o2);
}
