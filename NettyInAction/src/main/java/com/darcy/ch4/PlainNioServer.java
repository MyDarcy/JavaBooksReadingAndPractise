package com.darcy.ch4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

/**
 * Author by darcy
 * Date on 17-5-31 下午1:02.
 * Description:
 */
public class PlainNioServer {
    public void serve(int port) throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        ServerSocket ss = serverChannel.socket();
        InetSocketAddress address = new InetSocketAddress(port);
        //将服务器绑定到选定的端口
        ss.bind(address);
        //打开Selector来处理 Channel
        Selector selector = Selector.open();
        //将ServerSocket注册到Selector以接受连接
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        final ByteBuffer msg = ByteBuffer.wrap("Hi!\r\n".getBytes());
        for (;;){
            try {
                //等待需要处理的新事件；阻塞将一直持续到下一个传入事件
                selector.select();
            } catch (IOException ex) {
                ex.printStackTrace();
                //handle exception
                break;
            }
            //获取所有接收事件的SelectionKey实例
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                try {
                    //检查事件是否是一个新的已经就绪可以被接受的连接
                    if (key.isAcceptable()) {
                        ServerSocketChannel server =
                                (ServerSocketChannel) key.channel();
                        SocketChannel client = server.accept();
                        client.configureBlocking(false);
                        //接受客户端，并将它注册到选择器
                        client.register(selector, SelectionKey.OP_WRITE |
                                SelectionKey.OP_READ, msg.duplicate());
                        System.out.println(
                                "Accepted connection from " + client);
                    }
                    //检查套接字是否已经准备好写数据
                    if (key.isWritable()) {
                        SocketChannel client =
                                (SocketChannel) key.channel();
                        ByteBuffer buffer =
                                (ByteBuffer) key.attachment();
                        while (buffer.hasRemaining()) {
                            //将数据写到已连接的客户端
                            if (client.write(buffer) == 0) {
                                break;
                            }
                        }
                        /*//关闭连接
                        client.close();*/
                    }

                    if (key.isReadable()) {
                        SocketChannel client =
                                (SocketChannel) key.channel();
                        /*ByteBuffer buffer =
                                (ByteBuffer) key.attachment();
                        while (buffer.hasRemaining()) {
                            //将数据写到已连接的客户端
                            if (client.write(buffer) == 0) {
                                break;
                            }
                        }
                        //关闭连接
                        client.close();*/
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        client.read(byteBuffer);
                        byteBuffer.flip();
                        System.out.println("Received:" + new String(byteBuffer.array()));
                    }
                } catch (IOException ex) {
                    key.cancel();
                    try {
                        key.channel().close();
                    } catch (IOException cex) {
                        // ignore on close
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new PlainNioServer().serve(8001);

    }
}