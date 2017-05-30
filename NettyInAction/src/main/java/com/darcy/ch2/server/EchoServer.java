package com.darcy.ch2.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Author by darcy
 * Date on 17-5-30 下午9:38.
 * Description:
 */
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        // 示例代码中的port配置在了pom文件中。
        int port = 8000;
        new EchoServer(port).start();
    }

    private void start() throws InterruptedException {
        EchoServerHandler handler = new EchoServerHandler();
        // 指定NioEventLoopGroup来接收和处理新的连接.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group)
                    // channel的类型指定为NioServerSocketChannel.
                    .channel(NioServerSocketChannel.class)
                    // 设置本地地在, 服务器将绑定到这个地址监听新的连接请求.
                    .localAddress(new InetSocketAddress(port))
                    // 当新的连接到来时, 一个新的子Channel将会被创建, 而ChannelInitializer将会把EchoServerhandler
                    // 添加到该子handler的ChannelPipeline中。
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(handler);

                        }
                    });
            // 绑定服务器并等待绑定完成。
            ChannelFuture future = bootstrap.bind().sync();
            // 阻塞直到服务器的Channel关闭。
            future.channel().closeFuture().sync();
        } finally {
            // 关闭EventLoopGroup,并且释放所有的资源，包括被创建的线程。
            group.shutdownGracefully().sync();
        }
    }
}
