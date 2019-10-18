package me.darcy.network;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class NetWorkDemo {

  public static String getHostIpSimple() throws UnknownHostException {
    InetAddress addr = InetAddress.getLocalHost();
    System.out.println("Local HostAddress: " + addr.getHostAddress());
        String hostname = addr.getHostName();
    System.out.println("Local host name: "+hostname);
    return hostname;
  }

  /**
   * https://blog.csdn.net/u010295735/article/details/74645653
   * @return
   */
  public static String getHostIp(){
    try{
      Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
      while (allNetInterfaces.hasMoreElements()){
        NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
        Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
        while (addresses.hasMoreElements()){
          InetAddress ip = (InetAddress) addresses.nextElement();
          if (ip != null
              && ip instanceof Inet4Address
              && !ip.isLoopbackAddress() //loopback地址即本机地址，IPv4的loopback范围是127.0.0.0 ~ 127.255.255.255
              && ip.getHostAddress().indexOf(":")==-1){
            System.out.println("本机的IP = " + ip.getHostAddress());
            return ip.getHostAddress();
          }
        }
      }
    }catch(Exception e){
      e.printStackTrace();
    }
    return null;
  }

  public static void main(String[] args) throws UnknownHostException {
    getHostIpSimple();
    System.out.println();
    getHostIp();
  }

}
