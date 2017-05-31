package com.darcy.ch4;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Author by darcy
 * Date on 17-5-31 上午11:36.
 * Description:
 */
public class PlainOioServer {
    public void serve(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        try {
            for (; ; ) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket.getInetAddress());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            InputStream inputStream = clientSocket.getInputStream();
                            byte[] bytes = new byte[1024];
                            inputStream.read(bytes);
                            System.out.println("PlainOioServer Received:" + new String(bytes));
                            final OutputStream output = clientSocket.getOutputStream();
                            output.write("May you happy.\r\n".getBytes(Charset.forName("UTF-8")));
                            output.flush();

                            serverSocket.close();
                            clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        } catch (Exception e) {}
    }

    public static void main(String[] args) throws IOException {
        new PlainOioServer().serve(8000);
    }
}
