package Tools.AudioTool;

import javax.sound.sampled.TargetDataLine;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import static Tools.AudioTool.AudioUtils.BUFFER_SIZE;

/**
 * 音频通话中麦克风
 *
 * @author 不识不知 wx:hbhdlhd96
 */
public class Audio发送器 implements Runnable {
    /**
     * 网络参数
     */
    private String ip;
    private int port;
    private OutputStream out = null;
    /**
     * 变量
     */
    private Socket socket = null;
    private boolean run = true;
    /**
     * 音频参数
     */
    //采集器
    private static TargetDataLine targetDataLine = AudioUtils.getTargetDataLine();


    public Audio发送器(String ip, int port) {
        super();
        this.ip = ip;
        this.port = port;
        new Thread(this).start();
    }

    @Override
    public void run() {
        run = true;
        // 建立链接
        try {
            socket = new Socket(ip, port);
            // 获取输出流
            out = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * 运行
         */
        try {
            int len = 0;
            byte[] data = new byte[BUFFER_SIZE];
            while (run) {
                len = targetDataLine.read(data, 0, data.length);
                out.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
            run = false;
            try {
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void 关闭() {
        run = false;
    }
}