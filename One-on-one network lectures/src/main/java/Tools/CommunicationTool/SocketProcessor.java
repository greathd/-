package Tools.CommunicationTool;


/**
 * @author 不识不知 wx:hbhdlhd96
 * @version 创建时间：2018年12月29日 下午11:22:55
 * 类说明
 */

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketProcessor implements Runnable {
    private String name = "";

    /**
     * Get the socket name.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Set the socket name.
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }


    private Socket socket = null;
    private String ip = "";

    public String getIp() {
        return ip;
    }

    private int port = 0;
    // Output stream
    private ObjectOutputStream out = null;
    // Input stream
    private ObjectInputStream in = null;
    // Whether the socket survives
    private boolean alive = true;
    // The socket user
    private SocketUser socketUser = null;
    private int resetnum = 0;


    /**
     * Set the user of the socket
     *
     * @param socketUser
     */
    public void setSocketUser(SocketUser socketUser) {
        this.socketUser = socketUser;
    }

    /**
     * Get socket status.
     *
     * @return
     */
    public boolean isAlive() {
        aliveRefresh();
        return alive;
    }

    /**
     * Update alive property.
     */
    private void aliveRefresh() {
        alive = true;
        try {
            socket.sendUrgentData(0xFF);
        } catch (Exception e) {
            alive = false;
            close();
        }
    }


    /**
     * Starting a new socket by ip and port.
     *
     * @param ip
     * @param port
     * @param socketUser
     * @throws IOException          获取输入输出流失败
     * @throws UnknownHostException Socket连接失败
     */
    public SocketProcessor(String ip, int port, SocketUser socketUser) throws UnknownHostException, IOException {
        this(new Socket(ip, port), socketUser);
    }

    /**
     * Used to manage existing sockets.
     *
     * @param socket
     * @param socketUser
     * @throws IOException 获取输入输出流失败
     */
    public SocketProcessor(Socket socket, SocketUser socketUser) throws IOException {
        super();
        this.socket = socket;
        this.socketUser = socketUser;
        new Thread(this, "SocketProcesser进程").start();
    }

    /**
     * Message receive thread.
     */
    public void run() {
        ip = socket.getInetAddress().toString().substring(1);
        port = socket.getPort();
        try {
            in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            while (true) {
                Message msg = (Message) in.readObject();
                socketUser.acceptit(this, msg);
            }
        } catch (ClassNotFoundException | IOException e) {
            // TODO Auto-generated catch block
            close();
        }
    }

    /**
     * Message send method.
     *
     * @param msg
     * @throws IOException 信息发送出错
     */
    public void sendMsg(Message msg) throws IOException {
        if (out == null)
            out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        if (resetnum == 50) {
            resetnum = 0;
            out.reset();
        }
        out.writeObject(msg);
        out.flush();
    }


    /**
     * Close this socket.
     */
    public void close() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException ex) {
                System.out.println(name + "-->socket关闭失败");
            } finally {
                socket = null;
            }
        }
    }

    /**
     * 打印IP和端口信息
     */
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "" + socket.getInetAddress() + " " + socket.getPort();
    }
}
