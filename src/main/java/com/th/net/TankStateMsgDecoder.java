package com.th.net;

import com.th.tank.Dir;
import com.th.tank.Group;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
import java.util.UUID;

/**
 * @author TanHaooo
 * @date 2021/2/25 20:37
 */
public class TankStateMsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 33) return;//TCP 拆包粘包问题 因为一次数据必须是33个字节

        TankStateMsg msg = new TankStateMsg();

        msg.setX(byteBuf.readInt());
        msg.setY(byteBuf.readInt());
        msg.setDir(Dir.values()[byteBuf.readInt()]);
        msg.setMoving(byteBuf.readBoolean());
        msg.setGroup(Group.values()[byteBuf.readInt()]);
        msg.setId(new UUID(byteBuf.readLong(), byteBuf.readLong()));
        list.add(msg);
    }
    //写的时候用Encoder读的时候用Decoder
}
