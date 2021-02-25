package com.th.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author TanHaooo
 * @date 2021/2/25 20:37
 */
public class TankStateMsgEncoder extends MessageToByteEncoder<TankStateMsg> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, TankStateMsg tankMsg, ByteBuf byteBuf) throws Exception {
        byteBuf.writeBytes(tankMsg.toBytes());
    }
}
