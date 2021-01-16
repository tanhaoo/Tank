package com.th.cor;

import com.th.strategy.FireStrategy;
import com.th.tank.GameObject;
import com.th.tank.Group;
import com.th.tank.PropertyMgr;

import java.util.LinkedList;
import java.util.List;

/**
 * @author TanHaooo
 * @date 2021/1/16 17:16
 */
public class ColliderChain implements Collider {
    private List<Collider> colliders = new LinkedList<>();
    //用LinkedList好处，不用像数组一样动态开辟空间，有一个加一个，最关键的是不需要下标数随机访问，从头访问到尾就完事了

    public ColliderChain() {
        String allCollider = (String) PropertyMgr.get("colliders");
        try {
            for (String collide : allCollider.split(",")
            ) {
                add((Collider) Class.forName(collide).newInstance());
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ColliderChain add(Collider c) {
        colliders.add(c);
        return this;
    }

    public boolean collide(GameObject o1, GameObject o2) {
        for (int i = 0; i < colliders.size(); i++) {
            if (!colliders.get(i).collide(o1, o2)) return false;
        }
        return true;
    }

}
