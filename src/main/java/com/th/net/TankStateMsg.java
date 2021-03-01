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
 * @date 2021/2/25 20:33
 */
@Data
public class TankStateMsg extends Msg {
    private int x, y;
    private Dir dir;
    private boolean moving;
    private Group group;
    private UUID id;

    public TankStateMsg(Tank t) {
        this.x = t.getX();
        this.y = t.getY();
        this.dir = t.getDir();
        this.moving = t.isMoving();
        this.group = t.getGroup();
        this.id = t.getId();
    }

    public TankStateMsg(int x, int y, Dir dir, boolean moving, Group group, UUID id) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.moving = moving;
        this.group = group;
        this.id = id;
    }

    public TankStateMsg() {
    }

    @Override
    public void handle() {
        if (this.id.equals(TankFrame.INSTANCE.getMyTank().getId())
                || TankFrame.INSTANCE.getGm().findTankByUUID(this.id) != null) return;
        System.out.println(getMsgType().toString() + " " + this.toString() + "\n");
        this.setGroup(Group.BAD);
        new Tank(this);
        if (TankFrame.INSTANCE.getMyTank().isLiving())
            Client.INSTANCE.send(new TankStateMsg(TankFrame.INSTANCE.getMyTank()));
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;
        baos = new ByteArrayOutputStream();
        dos = new DataOutputStream(baos);
        try {
            //在网络里传数据 传1个 123456 如果是 int 那是4个字节
            //可是如果把它变成String "123456"  如果是按照UTF-8编码 那就是6个字节 只要他不超过int最大值
            //那用int势必比String有优势，所以按照这个例子在网络中传输很少直接使用String 开销太大
            //int 4Byte Boolean 1Byte Long 8Byte   4*4+1+8*2=33Byte
            //如果是在不确定消息长度的自定义协议中，都要在消息头部写好  消息类型及长度
            //组成: 消息头(消息类型+长度) + 消息体 + 校验码(由消息体算出来，一旦中途数据被篡改，那校验码就不对)
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());//ordinal是指当前这个dir的下标值
            dos.writeBoolean(moving);//Boolean是1个字节
            dos.writeInt(group.ordinal());
            dos.writeLong(id.getMostSignificantBits());//UUID是128位  这边分高低位分别写  一个Long是8Byte 就是64bit
            dos.writeLong(id.getLeastSignificantBits());//两个long就是一个UUID
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
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = (Dir.values()[dis.readInt()]);
            this.moving = dis.readBoolean();
            this.group = (Group.values()[dis.readInt()]);
            this.id = (new UUID(dis.readLong(), dis.readLong()));
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
        return MsgType.TankState;
    }

    @Override
    public String toString() {
        return "TankStateMsg{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", moving=" + moving +
                ", group=" + group +
                ", id=" + id +
                '}';
    }
}
