package com.th.observer;

import com.th.tank.Tank;

/**
 * @author TanHaooo
 * @date 2021/1/23 18:04
 */
public class TankFireEvent {
    private Tank tank;

    public TankFireEvent(Tank tank) {
        this.tank = tank;
    }

    public Tank getSource() {
        return tank;
    }
}
