package com.th.net;

import com.th.tank.Bullet;
import com.th.tank.Dir;
import com.th.tank.Group;
import com.th.tank.TankFrame;
import lombok.Data;

import java.io.*;
import java.util.UUID;

/**
 * @author TanHaooo
 * @date 2021/3/1 16:09
 */
@Data
public class BulletNewMsg extends Msg {

    private UUID bulletId;
    private UUID playerID;
    private int x, y;
    private Dir dir;
    private Group group;

    public BulletNewMsg() {
    }

    public BulletNewMsg(UUID playerID, UUID id, int x, int y, Dir dir, Group group) {
        this.playerID = playerID;
        this.bulletId = bulletId;
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
    }

    public BulletNewMsg(Bullet bullet) {
        this.playerID = bullet.getPlayerId();
        this.bulletId = bullet.getId();
        this.x = bullet.getX();
        this.y = bullet.getY();
        this.dir = bullet.getDir();
        this.group = Group.BAD;
    }

    @Override
    public void handle() {
        if (this.playerID.equals(TankFrame.INSTANCE.getMyTank().getId())) return;
        new Bullet(this);
    }

    @Override
    public byte[] toBytes() {
        byte[] bytes = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            dos.writeLong(bulletId.getMostSignificantBits());
            dos.writeLong(bulletId.getLeastSignificantBits());
            dos.writeLong(playerID.getMostSignificantBits());
            dos.writeLong(playerID.getLeastSignificantBits());
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.writeInt(group.ordinal());
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
            this.playerID = new UUID(dis.readLong(), dis.readLong());
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = (Dir.values()[dis.readInt()]);
            this.group = (Group.values()[dis.readInt()]);
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
        return MsgType.BulletNew;
    }
}
