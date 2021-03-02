package com.th.strategy;

import com.th.net.BulletNewMsg;
import com.th.net.Client;
import com.th.tank.Bullet;
import com.th.tank.Tank;

import java.io.Serializable;

/**
 * @author TanHaooo
 * @date 2021/1/15 22:08
 */
public interface FireStrategy extends Serializable {
    void fire(Tank t);

    default void sendFireMsg(Bullet b){
        Client.INSTANCE.send(new BulletNewMsg(b));
    }
}
