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

    private UUID bulletId;
    private UUID playerId;

    public TankDieMsg() {
    }

    public TankDieMsg(UUID bulletId, UUID playerId) {
        this.bulletId = bulletId;
        this.playerId = playerId;
    }


    @Override
    public void handle() {
        Bullet b = TankFrame.INSTANCE.getGm().findBulletByUUID(bulletId);
        if (b != null) {
            b.die();
        }
        if (this.playerId.equals(TankFrame.INSTANCE.getMyTank().getId())) {
            TankFrame.INSTANCE.getMyTank().die();
        } else {
            Tank t = TankFrame.INSTANCE.getGm().findTankByUUID(playerId);
            if (t != null)
                t.die();
        }
        System.out.println(getMsgType().toString() + " " + this.toString() + "\n");
    }

    @Override
    public byte[] toBytes() {
        byte[] bytes = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            dos.writeLong(bulletId.getMostSignificantBits());
            dos.writeLong(bulletId.getLeastSignificantBits());
            dos.writeLong(playerId.getMostSignificantBits());
            dos.writeLong(playerId.getLeastSignificantBits());
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
            this.bulletId = new UUID(dis.readLong(), dis.readLong());
            this.playerId = new UUID(dis.readLong(), dis.readLong());
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
