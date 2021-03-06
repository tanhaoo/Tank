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
public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 8) return;

        byteBuf.markReaderIndex();
        //在一开始做一个标记   是为了如果丢包要重新读的话要从头开始读，和下面的resetReaderIndex匹配

        MsgType type = MsgType.values()[byteBuf.readInt()];
        int length = byteBuf.readInt();

        if (byteBuf.readableBytes() < length) {
            byteBuf.resetReaderIndex();
            return;//TCP 拆包粘包问题 因为一次数据必须是33个字节
        }
        //开始消息处理
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);//这边就是把除了一开始读出来的消息类型和消息长度以外的消息读到这个字节数组里

        String className = "com.th.net." + type.toString() + "Msg";
        //  System.out.println(className);
        Msg msg = (Msg) Class.forName(className).getConstructor().newInstance();
        //这里使用java Reflection来获得实例化对象，而不是通过new这样就不用switch case一个一个找了
//        switch (type) {
//            case TankState:
//                msg = new TankStateMsg();
//                break;
//            case TankStartMoving:
//                msg = new TankStateMovingMsg();
//                break;
//            case TankStop:
//                msg = new TankStopMsg();
//            default:
//                break;
//        }
        msg.parse(bytes);
        list.add(msg);
    }
    //写的时候用Encoder读的时候用Decoder
}
