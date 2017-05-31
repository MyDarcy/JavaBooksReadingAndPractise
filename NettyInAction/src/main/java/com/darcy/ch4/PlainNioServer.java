package com.darcy.ch4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Author by darcy
 * Date on 17-5-31 下午1:02.
 * Description:
 */
public class PlainNioServer {
    public void serve() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        InetSocketAddress address = new InetSocketAddress(8001);
        serverSocket.bind(address);
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        ByteBuffer msg = ByteBuffer.wrap("Received. nice".getBytes());
        for (;; ) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = channel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,
                            SelectionKey.OP_READ | SelectionKey.OP_WRITE,
                            msg.duplicate());
                    System.out.println("Acceptd connection from" + socketChannel.getRemoteAddress());
                }
                if (key.isWritable()) {
                    SocketChannel client = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
                    while (byteBuffer.hasRemaining()) {
                        if (client.write(byteBuffer) == 0) {
                            break;
                        }
                    }
                    client.close();
                }
                if (key.isReadable()) {
                    SocketChannel client = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    client.read(byteBuffer);
                    System.out.println("PlainNioServer Received:" + byteBuffer.toString());;
                    client.close();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new PlainOioServer().serve(8000);

    }
}
