package basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Author by darcy
 * Date on 17-9-8 上午9:43.
 * Description:
 */
public class SocketClient {

  /**
   * server监听端口, client发起connect, server accept建立连接.
   *
   * 阻塞式IO
   * @param args
   */
  public static void main(String[] args) {
    BufferedReader reader = null;
    PrintWriter writer = null;
    try {
      Socket socket = new Socket("localhost", 20000);
      reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      writer = new PrintWriter(socket.getOutputStream(), true);
      writer.println("你好.");
      String line = null;
      while (true) {
        line  = reader.readLine();
        if (line != null) {
          break;
        }
      }
      System.out.println("client received:" + line);

    } catch (UnknownHostException e) {
      e.printStackTrace();
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
