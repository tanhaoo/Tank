package com.th.net;

import com.th.tank.Dir;
import com.th.tank.Group;
import lombok.Data;

import java.util.UUID;

/**
 * @author TanHaooo
 * @date 2021/3/1 16:09
 */
@Data
public class BulletNewMsg extends Msg {

    private UUID id;
    private int x, y;
    private Dir dir;
    private Group group;

    public BulletNewMsg() {
    }

    @Override
    public void handle() {

    }

    @Override
    public byte[] toBytes() {
        return new byte[0];
    }

    @Override
    public void parse(byte[] bytes) {

    }

    @Override
    public MsgType getMsgType() {
        return MsgType.BulletNew;
    }
}
