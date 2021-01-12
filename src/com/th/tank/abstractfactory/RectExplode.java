package com.th.tank.abstractfactory;

import com.th.tank.ResourceMgr;
import com.th.tank.TankFrame;

import java.awt.*;

/**
 * @author TanHaooo
 * @date 2021/1/12 16:51
 */
public class RectExplode extends BaseExplode {
    public static final int WIDTH = ResourceMgr.explodes[0].getWidth();
    public static final int HEIGHT = ResourceMgr.explodes[0].getHeight();
    private int x, y;
    private TankFrame tf;
    private int step = 0;

    public RectExplode(int x, int y, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.tf = tf;

    }

    @Override
    public void paint(Graphics g) {
        //g.drawImage(ResourceMgr.explodes[step++], x, y, null);
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillRect(x, y, 10 * step, 10 * step);
        step++;
        if (step >= 15) tf.explodes.remove(this);
        g.setColor(c);
    }
}
