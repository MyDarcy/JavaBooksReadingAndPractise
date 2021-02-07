package com.darcy.ch1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Author by darcy
 * Date on 17-5-30 下午7:26.
 * Description:
 * 这里只在主线程处理, 所以只能同时处理一个客户端。 并发处理多个客户端的话需要为每个新的clientSocket分派一个线程进行处理。
 * 问题:
 * 1. 任何时候都可能有大量的线程处于休眠状态，等待IO.
 * 2. 每个线程都需要分配栈空间
 * 3. JVM管理大量的线程上下文切换的开销不可忽视。
 */
public class BlockingIODemo {
    public void serve(int portNumber) throws IOException {
        //创建一个新的 ServerSocket，用以监听指定端口上的连接请求
        ServerSocket serverSocket = new ServerSocket(portNumber);
        //对accept()方法的调用将被阻塞，直到一个连接建立
        Socket clientSocket = serverSocket.accept();
        //这些流对象都派生于该套接字的流对象
        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);
        String request, response;
        //处理循环开始, in.readLine同样会阻塞,直到读取换行符。
        while ((request = in.readLine()) != null) {
            if ("Done".equals(request)) {
                break;
            }
            //请求被传递给服务器的处理方法
            response = processRequest(request);
            //服务器的响应被发送给了客户端
            out.println(response);
            //继续执行处理循环
        }
    }

    private String processRequest(String request){
        return "Processed";
    }
}
