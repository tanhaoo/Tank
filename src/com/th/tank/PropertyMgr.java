package com.th.tank;

import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

/**
 * @author TanHaooo
 * @date 2021/1/10 23:33
 * 设计单例模式
 * 在内存中如果需要某个类的对象，在程序上保证，有且只有一个该类的对象
 */

public class PropertyMgr {
    private PropertyMgr() {
    }

    static Properties props = new Properties();

    static {
        try {
            props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static Object get(String key) {
        if (props == null) return null;
        return props.get(key);
    }


    @Test
    public void test() {
        System.out.println(get("initTankCount"));
    }
}
