package 公共组件池.探测器与接收器;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import pool.常量池;

public class 接收器 implements Runnable {
	int listenPort = 常量池.广播台端口号;
	byte[] buf = new byte[1024];// 缓冲池
	DatagramPacket packet = new DatagramPacket(buf, buf.length);// 包格式
	DatagramSocket responseSocket = null;
	public 接收器(){
		new Thread(this,"广播台进程").start();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			responseSocket = new DatagramSocket(listenPort);
			System.out.println("广播台Server started, Listen port: " + listenPort);
			while (true) {
				responseSocket.receive(packet);
				String hi = new String(packet.getData(), 0, packet.getLength());
//				System.out.println("Received " + hi + " from address: " + packet.getSocketAddress());
				if (hi.equals("search bsbz server")) {
					// Send a response packet to sender
					String backData = "bsbz server is here";
					byte[] data = backData.getBytes();
//					System.out.println("Send " + backData + " to " + packet.getSocketAddress());
					DatagramPacket backPacket = new DatagramPacket(data, 0, data.length, packet.getSocketAddress());
					responseSocket.send(backPacket);
				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}