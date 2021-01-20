package com.th.decorator;

import com.th.tank.GameObject;

import java.awt.*;

/**
 * @author TanHaooo
 * @date 2021/1/21 0:21
 */
public abstract class GODecorator extends GameObject {

    GameObject go;

    public GODecorator(GameObject go) {
        this.go = go;
    }

    public void paint(Graphics g) {
        this.x=go.x;
        this.y=go.y;
        go.paint(g);
    }
}
