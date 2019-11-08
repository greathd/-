package com.bsbz.cloudclass.Tools.CommunicationTool;


import com.bsbz.cloudclass.Tools.常量池;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class 探测器 implements Runnable {
    DatagramSocket detectSocket = null;
    byte[] buf = new byte[1024];
    int packetPort = 常量池.广播台端口号;
    boolean find = false;
    String ip = "not found";
    Thread thread = null;

    public 探测器() throws SocketException {
        detectSocket = new DatagramSocket();
        thread = new Thread(this);
        thread.setName("探测器");
        thread.start();
    }

    public void 探测() {
        // 发送探测包
        try {
            // 255.255.255.255是广播地址.
            InetAddress hostAddress = InetAddress.getByName("255.255.255.255");
            buf = "search bsbz Server".getBytes();
            DatagramPacket out = new DatagramPacket(buf, buf.length, hostAddress, packetPort);
            detectSocket.send(out);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean 找到() {
        return find;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (true) {
            //刷新率
            try {
                thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            byte[] buf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                detectSocket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String data = new String(packet.getData(), 0, packet.getLength());
            if (data.equals("bsbz Server is here")) {
                ip = packet.getSocketAddress().toString().substring(1).split(":")[0];
                find = true;
                break;
            }
        }
    }

    public String getIp() {
        // TODO Auto-generated method stub
        return ip;
    }
}