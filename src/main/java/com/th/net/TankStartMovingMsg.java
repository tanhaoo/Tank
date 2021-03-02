package com.th.net;

import com.th.tank.Dir;
import com.th.tank.Group;
import com.th.tank.Tank;
import com.th.tank.TankFrame;
import lombok.Data;

import java.io.*;
import java.util.UUID;

/**
 * @author TanHaooo
 * @date 2021/2/28 18:51
 */
@Data
public class TankStartMovingMsg extends Msg {

    private UUID id;
    private int x, y;
    private Dir dir;

    public TankStartMovingMsg() {
    }

    public TankStartMovingMsg(UUID uuid, int x, int y, Dir dir) {
        this.id = uuid;
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public TankStartMovingMsg(Tank tank) {
        this.id = tank.getId();
        this.x = tank.getX();
        this.y = tank.getY();
        this.dir = tank.getDir();
    }

    @Override
    public void handle() {
        if (this.id.equals(TankFrame.INSTANCE.getMyTank().getId())) return;
        System.out.println(getMsgType().toString() + " "+ this.toString() + "\n");
        Tank t = TankFrame.INSTANCE.getGm().findTankByUUID(id);
        if (t != null) {
            t.setMoving(true);
            t.setX(x);
            t.setY(y);
            t.setDir(dir);
        }
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;
        baos = new ByteArrayOutputStream();
        dos = new DataOutputStream(baos);
        try {
            dos.writeLong(id.getMostSignificantBits());//UUID是128位  这边分高低位分别写  一个Long是8Byte 就是64bit
            dos.writeLong(id.getLeastSignificantBits());//两个long就是一个UUID
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());//ordinal是指当前这个dir的下标值
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
            this.id = (new UUID(dis.readLong(), dis.readLong()));
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = (Dir.values()[dis.readInt()]);
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
        return MsgType.TankStartMoving;
    }
}
