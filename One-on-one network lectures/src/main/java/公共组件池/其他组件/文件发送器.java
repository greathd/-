package 公共组件池.其他组件;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 文件传输Client端<br>
 * 功能说明：
 *
 * @author 大智若愚的小懂
 * @Date 2016年09月01日
 * @version 1.0
 */
public class 文件发送器 {

    private FileInputStream fis;
    private DataOutputStream dos;

    /**
     * 向对方发送文件
     *
     * @param file 文件
     * @param IP IP地址
     * @param PORT 端口号
     * @return 文件名
     * @throws java.io.IOException 异常
     */
    public static String 发送文件(File file, String IP, int PORT) throws IOException {
        Socket client = new Socket(IP, PORT);
        String 文件名 = null;
        DataOutputStream dos = null;
        FileInputStream fis = null;
        dos = new DataOutputStream(client.getOutputStream());
        fis = new FileInputStream(file);
        文件名 = file.getName();
        dos.writeUTF(文件名);
        dos.flush();
        //长度
        dos.writeLong(file.length());
        dos.flush();
        // 开始传输文件
        System.out.println("======== 开始传输文件 ========");
        byte[] bytes = new byte[1024];
        int length = 0;
        long progress = 0;
        while ((length = fis.read(bytes, 0, bytes.length)) != -1) {
            dos.write(bytes, 0, length);
            dos.flush();
            progress += length;
            System.out.print("| " + (100 * progress / file.length()) + "% |");
        }
        System.out.println();
        System.out.println("======== 文件传输成功 ========");
        if (client != null) {
            try {
                client.close();
            } catch (IOException ex) {
                Logger.getLogger(文件发送器.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(文件发送器.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (dos != null) {
            try {
                dos.close();
            } catch (IOException ex) {
                Logger.getLogger(文件发送器.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 文件名;
    }
}
