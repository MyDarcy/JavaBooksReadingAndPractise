package basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Author by darcy
 * Date on 17-9-8 上午9:40.
 * Description:
 * 1. 监听指定的端口是否有连接请求,
 * 2. 有连接请求, 那么接受请求, 返回代表一个通信接口的Socket.
 * 3. 收发消息.
 */
public class SocketServer {
  public static void main(String[] args) {
    BufferedReader reader = null;
    PrintWriter writer = null;
    try {
      ServerSocket serverSocket = new ServerSocket(20000);
      Socket clientSocket = serverSocket.accept();
      reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      writer = new PrintWriter(clientSocket.getOutputStream(), true);
      String s = reader.readLine();
      writer.println(s);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        reader.close();
        writer.close();

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
