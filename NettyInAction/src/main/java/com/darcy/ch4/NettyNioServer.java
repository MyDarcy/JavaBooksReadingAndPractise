package com.darcy.ch4;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Author by darcy
 * Date on 17-6-1 下午4:04.
 * Description:
 */
public class NettyNioServer {
    public void server(int port) throws Exception {
        final ByteBuf buf =
                Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hi!\r\n",
                        Charset.forName("UTF-8")));
        //为非阻塞模式使用NioEventLoopGroup
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            //创建ServerBootstrap
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    //指定 ChannelInitializer，对于每个已接受的连接都调用它
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                                      @Override
                                      public void initChannel(SocketChannel ch)
                                              throws Exception {
                                          ch.pipeline().addLast(
                                                  //添加 ChannelInboundHandlerAdapter以接收和处理事件
                                                  new ChannelInboundHandlerAdapter() {
                                                      @Override
                                                      public void channelActive(
                                                              //将消息写到客户端，并添加ChannelFutureListener，
                                                              //以便消息一被写完就关闭连接
                                                              ChannelHandlerContext ctx) throws Exception {
                                                          ctx.writeAndFlush(buf.duplicate())
                                                                  .addListener(
                                                                          ChannelFutureListener.CLOSE);
                                                      }
                                                  });
                                      }
                                  }
                    );
            //绑定服务器以接受连接
            ChannelFuture f = b.bind().sync();
            f.channel().closeFuture().sync();
        } finally {
            //释放所有的资源
            group.shutdownGracefully().sync();
        }
    }
}

