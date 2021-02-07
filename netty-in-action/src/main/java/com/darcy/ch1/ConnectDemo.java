package com.darcy.ch1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Author by darcy
 * Date on 17-5-30 下午8:01.
 * Description:
 * JDK中的Future需要手动检查对应的操作是否完成, 或者一直阻塞直到它完成。
 * 而Netty中提供的ChannelFuture可以注册ChannelFutureListener的实例，监听器的回调方法operationComplete将在
 * 对应的操作完成的时候被调用，针对成功和失败进行相应的处理。所以，监听器机制就消除了手动检查操作是否完成的必要。
 */
public class ConnectDemo {

    public static void connect() {
        Channel channel = null; //reference form somewhere
        // Does not block
        //异步地连接到远程节点， 每个Netty的出站操作都返回Future。
        ChannelFuture future = channel.connect(
                new InetSocketAddress("192.168.0.1", 25));
        //注册一个 ChannelFutureListener，以便在操作完成时获得通知
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                //检查操作的状态
                if (future.isSuccess()) {
                    //如果操作是成功的，则创建一个 ByteBuf 以持有数据
                    ByteBuf buffer = Unpooled.copiedBuffer(
                            "Hello", Charset.defaultCharset());
                    //将数据异步地发送到远程节点。返回一个 ChannelFuture
                    ChannelFuture wf = future.channel()
                            .writeAndFlush(buffer);
                    // ...
                } else {
                    //如果发生错误，则访问描述原因的 Throwable
                    Throwable cause = future.cause();
                    cause.printStackTrace();
                }
            }
        });
    }
}
