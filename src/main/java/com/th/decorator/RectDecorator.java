package com.th.decorator;

import com.th.tank.GameObject;

import java.awt.*;

/**
 * @author TanHaooo
 * @date 2021/1/21 0:26
 */
public class RectDecorator extends GODecorator {

    public RectDecorator(GameObject go) {
        super(go);
    }

    public void paint(Graphics g) {
        super.paint(g);
        Color c = g.getColor();
        g.setColor(Color.YELLOW);
        g.drawRect(go.x, go.y, getWidth(), getHeight());
        g.setColor(c);
    }

    @Override
    public int getWidth() {
        return go.getWidth();
    }

    @Override
    public int getHeight() {
        return go.getHeight();
    }
}
