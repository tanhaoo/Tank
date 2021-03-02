package com.th.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author TanHaooo
 * @date 2021/2/25 16:35
 */
public class TankMsgCodecTest {
    @Test
    public void testTankMsgEncoder() {
        TankMsg msg = new TankMsg(10, 10);
        EmbeddedChannel ch = new EmbeddedChannel(new TankMsgEncoder());
        ch.writeOutbound(msg);

        ByteBuf buf = ch.readOutbound();
        int x = buf.readInt();
        int y = buf.readInt();

        Assert.assertTrue(x == 10 && y == 10);
        buf.release();
    }

    @Test
    public void testTankMsgEncoder2() {
        ByteBuf buf = Unpooled.buffer();
        TankMsg msg = new TankMsg(10, 10);
        buf.writeInt(msg.x);
        buf.writeInt(msg.y);
        EmbeddedChannel ch = new EmbeddedChannel(new TankMsgDecoder());
        ch.writeInbound(buf.duplicate());
        TankMsg tm=ch.readInbound();
        Assert.assertTrue(tm.x == 10 && tm.y == 10);

    }
}
