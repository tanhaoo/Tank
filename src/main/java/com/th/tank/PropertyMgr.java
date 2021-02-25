package com.th.tank;

import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

/**
 * @author TanHaooo
 * @date 2021/1/10 23:33
 */
public class PropertyMgr {
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
