package com.th.net;

import com.th.tank.TankFrame;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author TanHaooo
 * @date 2021/2/24 21:20
 */
public class Client {

    public static final Client INSTANCE = new Client();
    public Channel channel = null;

    private Client() {
    }

    public void connect() {
        //线程池
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        try {
            ChannelFuture f = b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientChannelInitializer())
                    .connect("localhost", 8888)
                    .addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture future) {
                            if (!future.isSuccess())
                                System.out.println("not connected");
                            else {
                                System.out.println("connected");
                                channel = future.channel();
                            }
                        }
                    })
                    .sync();//因为Netty里都是异步的，所以sync在这里的作用是等它结束，不让他继续往下执行，必须等他结束
            System.out.println("...");
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public void send(Msg msg) {
        channel.writeAndFlush(msg);
    }

//    public void closeConnect() {
//        this.send("_bye_");
//    }
}

class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        System.out.println(ch);
        ch.pipeline()
                .addLast(new MsgEncoder())
                .addLast(new MsgDecoder())
                .addLast(new ClientHandler());//pipeline是channel上的责任链一个一个链条
    }
}

class ClientHandler extends SimpleChannelInboundHandler<Msg> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //Channel第一次连上可用，写出一个字符串 Direct Memory  直接访问系统内存，不通过JVM虚拟机，带来了垃圾回收问题
        ctx.writeAndFlush(new TankStateMsg(TankFrame.INSTANCE.getMyTank()));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
        msg.handle();
    }

}

