package com.th.test;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author TanHaooo
 * @date 2021/2/25 16:49
 */
public class TankMsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 8) return;//TCP 拆包粘包问题

        int x = byteBuf.readInt();
        int y = byteBuf.readInt();
        list.add(new TankMsg(x, y));
    }
    //写的时候用Encoder读的时候用Decoder

}
