package com.th.tank;

import java.awt.*;
import java.io.Serializable;

/**
 * @author TanHaooo
 * @date 2021/1/15 22:41
 */
public abstract class GameObject implements Serializable {
                                            //说明找个对象可以被序列化
    public int x, y;

    public abstract void paint(Graphics g);
    public abstract  int getWidth();
    public abstract  int getHeight();
}
