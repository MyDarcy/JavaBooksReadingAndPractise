package com.darcy.ch4;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Author by darcy
 * Date on 17-5-31 下午12:55.
 * Description:
 */
public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("127.0.0.1", 8001));
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("Hello\r\n".getBytes());

            byte[] bytes = new byte[1024];
            inputStream.read(bytes);
            System.out.println("Client Received:" + new String(bytes));
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
