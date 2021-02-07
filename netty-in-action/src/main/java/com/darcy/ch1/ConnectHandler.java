package com.darcy.ch1;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Author by darcy
 * Date on 17-5-30 下午7:55.
 * Description:
 */
public class ConnectHandler extends ChannelInboundHandlerAdapter {
    // 新连接建立的时候，本方法会被回调。
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("client:"  + ctx.channel().remoteAddress() + " connected...");
    }
}
