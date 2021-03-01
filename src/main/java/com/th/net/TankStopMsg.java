package com.th.net;

import com.th.tank.Tank;
import com.th.tank.TankFrame;
import lombok.Data;

import java.io.*;
import java.util.UUID;

/**
 * @author TanHaooo
 * @date 2021/3/1 14:12
 */
@Data
public class TankStopMsg extends Msg {

    private UUID id;
    private boolean moving;

    public TankStopMsg() {
    }

    public TankStopMsg(UUID id, boolean moving) {
        this.id = id;
        this.moving = moving;
    }

    public TankStopMsg(Tank t) {
        this.id = t.getId();
        this.moving = t.isMoving();
    }

    @Override
    public void handle() {
        if (this.id.equals(TankFrame.INSTANCE.getMyTank().getId())) return;
        System.out.println(getMsgType().toString() + " " + this.toString() + "\n");
        Tank t = TankFrame.INSTANCE.getGm().findByUUID(id);
        if (t != null) {
            t.setMoving(moving);
        }
    }

    @Override
    public byte[] toBytes() {
        byte[] bytes = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.writeBoolean(moving);
            dos.flush();
            bytes = baos.toByteArray();
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            outPutStreamClose(baos, dos);
        }
        return bytes;
    }

    @Override
    public void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            this.id = new UUID(dis.readLong(), dis.readLong());
            this.moving = dis.readBoolean();
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            try {
                dis.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankStop;
    }

}
