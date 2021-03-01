package com.th.net;

import com.th.tank.*;
import lombok.Data;

import java.io.*;
import java.util.UUID;

/**
 * @author TanHaooo
 * @date 2021/3/1 18:11
 */
@Data
public class TankDieMsg extends Msg {

    private UUID id;
    private boolean living;

    public TankDieMsg() {
    }

    public TankDieMsg(UUID id, boolean living) {
        this.id = id;
        this.living = living;
    }

    public TankDieMsg(Tank t) {
        this.id = t.getId();
        this.living = t.isLiving();
    }

    @Override
    public void handle() {
        if (TankFrame.INSTANCE.getMyTank().getId() != id) {
            TankFrame.INSTANCE.getGm().findTankByUUID(id).setLiving(false);
            System.out.println(getMsgType().toString() + " " + this.toString() + "\n");
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
            dos.writeBoolean(living);
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
            this.living = dis.readBoolean();
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
        return MsgType.TankDie;
    }
}
