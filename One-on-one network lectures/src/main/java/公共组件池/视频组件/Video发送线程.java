package 公共组件池.视频组件;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.github.sarxos.webcam.Webcam;
import pool.常量池;
import 公共组件池.其他组件.图片Util;


/**
 * @author 不识不知 wx:hbhdlhd96
 * @version 创建时间：2019年4月14日 下午4:53:50
 */
public class Video发送线程 implements Runnable {
    /**
     * 网络参数
     */
    String ip = 常量池.默认IP;
    int port = 常量池.视频端口号;
    /**
     * 变量
     */
    Socket socket = null;
    ObjectOutputStream out = null;// 输出流
    boolean run = true;// 运行状态
    /**
     * 使用者接口
     */
    VideoInterface user = null;

    public Video发送线程(String ip, int port, VideoInterface user) {
        super();
        this.ip = ip;
        this.port = port;
        this.user = user;
    }

    public void 启动() throws UnknownHostException, IOException {
        // 提出访问请求
        socket = new Socket(ip, port);
        // 建立输出流
        out = new ObjectOutputStream(socket.getOutputStream());
        // 启动线程
        new Thread(this, "视频发送线程").start();
    }

    @Override
    public void run() {
        // 设置运行态
        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(常量池.默认摄像头宽, 常量池.默认摄像头高));
        webcam.open();
        run = true;
        int resetnum = 0;
        try {
            while (run) {
                // 休息
                Thread.sleep(30);
                //获取图片
                BufferedImage image = webcam.getImage();
                // 发送方显示图片
                user.我方(image);
                // 转码装包
                Video包 pack = new Video包();
                pack.setImage(图片Util.Encoding(image));
                // 发送
                if (resetnum == 50) {
                    out.reset();
                    resetnum = 0;
                }
                resetnum++;
                out.writeObject(pack);
                out.flush();
            }
        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            // 关闭摄像头
            webcam.close();
            try {
//				if (out != null)
//					out.close();
                if (socket != null)
                    socket.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    public void exit() {
        // TODO Auto-generated method stub
        run = false;
    }
}