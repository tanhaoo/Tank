package com.th.observer;

import java.io.Serializable;

/**
 * @author TanHaooo
 * @date 2021/1/23 18:06
 */
public interface TankFireObserver extends Serializable {
    void actionOnFire(TankFireEvent event);
}
