package com.th.tankCodec;

import com.th.net.*;
import com.th.tank.Bullet;
import com.th.tank.Dir;
import com.th.tank.Group;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * @author TanHaooo
 * @date 2021/2/25 22:29
 */
public class TankStateMsgCodecTest {

    @Test
    public void testTankDieEncoder() {
        EmbeddedChannel ch = new EmbeddedChannel();
        UUID playerId = UUID.randomUUID();
        UUID bulletId = UUID.randomUUID();
        TankDieMsg msg = new TankDieMsg(bulletId, playerId);
        ch.pipeline().addLast(new MsgEncoder());
        ch.writeOutbound(msg);
        ByteBuf buf = ch.readOutbound();

        MsgType type = MsgType.values()[buf.readInt()];
        int length = buf.readInt();
        assertEquals(MsgType.TankDie, type);
        assertEquals(32, length);
        assertEquals(bulletId, new UUID(buf.readLong(), buf.readLong()));
        assertEquals(playerId, new UUID(buf.readLong(), buf.readLong()));
    }

    @Test
    public void testTankDieDecoder() {
        EmbeddedChannel ch = new EmbeddedChannel();
        ch.pipeline().addLast(new MsgDecoder());

        UUID playerId = UUID.randomUUID();
        UUID bulletId = UUID.randomUUID();
        TankDieMsg msg = new TankDieMsg(playerId, bulletId);

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(MsgType.TankDie.ordinal());
        byte[] bytes = msg.toBytes();
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);

        ch.writeInbound(buf.duplicate());
        TankDieMsg result = ch.readInbound();

        assertEquals(result.getMsgType(), MsgType.TankDie);
        assertEquals(result.getPlayerId(), bulletId);
        assertEquals(result.getBulletId(), playerId);
    }

    @Test
    public void testBulletEncoder() {
        EmbeddedChannel ch = new EmbeddedChannel();
        UUID id = UUID.randomUUID();
        UUID playerId = UUID.randomUUID();
        BulletNewMsg msg = new BulletNewMsg(playerId, id, 10, 20, Dir.DOWN, Group.BAD);
        ch.pipeline().addLast(new MsgEncoder());
        ch.writeOutbound(msg);
        ByteBuf buf = ch.readOutbound();

        MsgType type = MsgType.values()[buf.readInt()];
        int length = buf.readInt();
        assertEquals(type, MsgType.BulletNew);
        assertEquals(length, 48);
        assertEquals(id, new UUID(buf.readLong(), buf.readLong()));
        assertEquals(playerId, new UUID(buf.readLong(), buf.readLong()));
        assertEquals(10, buf.readInt());
        assertEquals(20, buf.readInt());
        assertEquals(Dir.DOWN, Dir.values()[buf.readInt()]);
        assertEquals(Group.BAD, Group.values()[buf.readInt()]);
    }

    @Test
    public void testBulletDecoder() {
        EmbeddedChannel ch = new EmbeddedChannel();
        ch.pipeline().addLast(new MsgDecoder());

        UUID id = UUID.randomUUID();
        UUID playerId = UUID.randomUUID();
        BulletNewMsg msg = new BulletNewMsg(playerId, id, 10, 20, Dir.DOWN, Group.BAD);

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(MsgType.BulletNew.ordinal());
        byte[] bytes = msg.toBytes();
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);

        ch.writeInbound(buf.duplicate());
        BulletNewMsg result = ch.readInbound();

        assertEquals(MsgType.BulletNew, result.getMsgType());
        assertEquals(10, result.getX());
        assertEquals(20, result.getY());
        assertEquals(id, result.getBulletId());
        assertEquals(playerId, result.getPlayerID());
        assertEquals(Dir.DOWN, result.getDir());
        assertEquals(Group.BAD, result.getGroup());
    }

    @Test
    public void testStopEncoder() {
        EmbeddedChannel ch = new EmbeddedChannel();
        UUID id = UUID.randomUUID();
        TankStopMsg msg = new TankStopMsg(id, true);
        ch.pipeline().addLast(new MsgEncoder());
        ch.writeOutbound(msg);
        ByteBuf buf = ch.readOutbound();

        MsgType type = MsgType.values()[buf.readInt()];
        int length = buf.readInt();
        boolean moving = buf.readBoolean();
        assertEquals(type, MsgType.TankStop);
        assertEquals(length, 17);
        assertEquals(moving, true);
    }

    @Test
    public void testStopDecoder() {
        EmbeddedChannel ch = new EmbeddedChannel();
        ch.pipeline().addLast(new MsgDecoder());

        UUID id = UUID.randomUUID();
        TankStopMsg msg = new TankStopMsg(id, true);

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(MsgType.TankStop.ordinal());
        byte[] bytes = msg.toBytes();
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);

        ch.writeInbound(buf.duplicate());
        TankStopMsg result = ch.readInbound();

        assertEquals(MsgType.TankStop, result.getMsgType());
        assertEquals(true, result.isMoving());
    }

    @Test
    public void testEncoder() {
        EmbeddedChannel ch = new EmbeddedChannel();
        UUID id = UUID.randomUUID();
        TankStateMsg msg = new TankStateMsg(5, 10, Dir.DOWN, true, Group.BAD, id);
        ch.pipeline().addLast(new MsgEncoder());
        ch.writeOutbound(msg);
        ByteBuf buf = ch.readOutbound();

        MsgType type = MsgType.values()[buf.readInt()];
        assertEquals(MsgType.TankState, type);

        int length = buf.readInt();
        assertEquals(length, 33);

        int x = buf.readInt();
        int y = buf.readInt();
        Dir dir = Dir.values()[buf.readInt()];
        boolean moving = buf.readBoolean();
        Group group = Group.values()[buf.readInt()];
        UUID uuid = new UUID(buf.readLong(), buf.readLong());

        assertEquals(5, x);
        assertEquals(10, y);
        assertEquals(Dir.DOWN, dir);
        assertEquals(true, moving);
        assertEquals(Group.BAD, group);
        assertEquals(id, uuid);
    }

    @Test
    public void testDecoder() {
        EmbeddedChannel ch = new EmbeddedChannel();
        ch.pipeline().addLast(new MsgDecoder());

        UUID id = UUID.randomUUID();
        TankStateMsg msg = new TankStateMsg(5, 10, Dir.DOWN, true, Group.BAD, id);

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(MsgType.TankState.ordinal());
        byte[] bytes = msg.toBytes();
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);

        ch.writeInbound(buf.duplicate());
        TankStateMsg result = ch.readInbound();

        assertEquals(MsgType.TankState, result.getMsgType());
        assertEquals(5, result.getX());
        assertEquals(10, result.getY());
        assertEquals(Dir.DOWN, result.getDir());
        assertEquals(true, result.isMoving());
        assertEquals(Group.BAD, result.getGroup());
        assertEquals(id, result.getId());
    }
}
