package com.th.observer;

import com.th.tank.Tank;

/**
 * @author TanHaooo
 * @date 2021/1/23 18:05
 */
public class TankFireHandler implements TankFireObserver {

    @Override
    public void actionOnFire(TankFireEvent event) {
        Tank t = event.getSource();
        t.fire();
    }
}
