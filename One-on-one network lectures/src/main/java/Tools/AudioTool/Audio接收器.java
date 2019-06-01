package Tools.AudioTool;

import Tools.常量池;

import javax.sound.sampled.SourceDataLine;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static Tools.AudioTool.AudioUtils.BUFFER_SIZE;

/**
 * 音频通话中的听筒
 *
 * @author 不识不知 wx:hbhdlhd96
 */
public class Audio接收器 implements Runnable {
    /**
     * 网络参数
     */
    private int port = 常量池.音频端口号;
    /**
     * 变量
     */
    private InputStream in = null;
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private boolean run = true;
    /**
     * 音频参数
     */
    //播放器
    private static SourceDataLine sourceDataLine = AudioUtils.getSourceDataLine();

    public Audio接收器(int port) {
        this.port = port;
        new Thread(this).start();
    }

    @Override
    public void run() {
        run = true;
        try {
            serverSocket = new ServerSocket(port);
            socket = serverSocket.accept();// 此处会阻塞等待
            in = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            int len = 0;
            byte[] data = new byte[BUFFER_SIZE];
            while (run) {
                len = in.read(data);
                sourceDataLine.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
            run = false;
            try {
                in.close();
            } catch (IOException e1) {
                e.printStackTrace();
            }
            try {
                socket.close();
            } catch (IOException e1) {
                e.printStackTrace();
            }
        }

    }

    public void 关闭() {
        run = false;
    }
}