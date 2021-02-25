package com.th.tankCodec;

import com.th.net.TankStateMsg;
import com.th.net.TankStateMsgDecoder;
import com.th.net.TankStateMsgEncoder;
import com.th.tank.Dir;
import com.th.tank.Group;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author TanHaooo
 * @date 2021/2/25 22:29
 */
public class TankStateMsgCodecTest {
    @Test
    public void testEncoder() {
        EmbeddedChannel ch = new EmbeddedChannel();
        UUID id = UUID.randomUUID();
        TankStateMsg msg = new TankStateMsg(5, 10, Dir.DOWN, true, Group.BAD, id);
        ch.pipeline().addLast(new TankStateMsgEncoder());
        ch.writeOutbound(msg);
        ByteBuf buf = ch.readOutbound();
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
        ch.pipeline().addLast(new TankStateMsgDecoder());
        UUID id = UUID.randomUUID();
        TankStateMsg msg = new TankStateMsg(5, 10, Dir.DOWN, true, Group.BAD, id);
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(msg.toBytes());
        ch.writeInbound(buf.duplicate());
        TankStateMsg result = ch.readInbound();
        assertEquals(5, result.getX());
        assertEquals(10, result.getY());
        assertEquals(Dir.DOWN, result.getDir());
        assertEquals(true, result.isMoving());
        assertEquals(Group.BAD, result.getGroup());
        assertEquals(id, result.getId());
    }
}
