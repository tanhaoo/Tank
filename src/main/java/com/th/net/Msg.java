package com.th.net;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author TanHaooo
 * @date 2021/2/25 16:14
 */
public abstract class Msg {
    public abstract void handle();

    public abstract byte[] toBytes();

    public abstract void parse(byte[] bytes);

    public abstract MsgType getMsgType();

    public void outPutStreamClose(ByteArrayOutputStream baos, DataOutputStream dos) {
        try {
            if (baos != null) {
                baos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (dos != null) {
                dos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
