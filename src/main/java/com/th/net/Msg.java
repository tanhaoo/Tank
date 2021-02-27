package com.th.net;

/**
 * @author TanHaooo
 * @date 2021/2/25 16:14
 */
public abstract class Msg {
    public abstract void handle();

    public abstract byte[] toBytes();
}
