package 公共组件池.视频组件;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import pool.常量池;
import 公共组件池.其他组件.图片Util;

/**
 * @author 不识不知 wx:hbhdlhd96
 * @version 创建时间：2019年4月14日 下午4:54:24
 */
public class Video接收线程 implements Runnable {
    /**
     * 网络参数
     */
    private int port = 常量池.视频端口号;
    /**
     * 变量
     */
    private ObjectInputStream in = null;// Input stream
    private Socket socket = null;
    private ServerSocket server = null;
    private boolean run = true;
    /**
     * 使用者接口
     */
    private VideoInterface user;

    public Video接收线程(int port, VideoInterface user) {
        super();
        this.user = user;
        this.port = port;
    }

    public void 启动() {
        new Thread(this, "接收者线程").start();
    }

    @Override
    public void run() {
        // TODO 线程体
        try {
            server = new ServerSocket(port);
            socket = server.accept();
            in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            while (run) {
                user.对方(图片Util.Decoding(((Video包) in.readObject()).getImage()));
            }
        } catch (ClassNotFoundException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
                if (socket != null)
                    socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void exit() {
        // TODO Auto-generated method stub
        run = false;
    }
}