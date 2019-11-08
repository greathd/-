package com.bsbz.cloudclass.server;

import com.bsbz.cloudclass.Tools.CommunicationTool.Message;
import com.bsbz.cloudclass.Tools.CommunicationTool.SocketProcessor;
import com.bsbz.cloudclass.Tools.CommunicationTool.SocketUser;
import com.bsbz.cloudclass.Tools.CommunicationTool.接收器;
import com.bsbz.cloudclass.Tools.常量池;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


/**
 * @author 不识不知 wx:hbhdlhd96
 * @version 创建时间：2019年5月6日 下午12:08:34
 */
public class Server implements Runnable, SocketUser {
    ServerSocket server = null;
    int listenport = 常量池.服务器通信端口;
    Socket socket = null;
    ArrayList<SocketProcessor> sockets = new ArrayList<>();
    ArrayList<User> users = new ArrayList<>();
    ArrayList<Room> rooms = new ArrayList<>();

    public Server() {
        // TODO Auto-generated constructor stub
        new 接收器();
        new Thread(this, "服务器进程").start();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            server = new ServerSocket(listenport);
            while (true) {
                System.out.println("正在监听" + listenport);
                socket = server.accept();
                sockets.add(new SocketProcessor(socket, this));
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void acceptit(SocketProcessor socketProcessor, Message msg) {
        // TODO Auto-generated method stub
        String 描述 = msg.get描述();
        String 内容 = msg.get内容();
        switch (描述) {
            case "登记":
                try {
                    // flag
                    boolean flag = false;
                    // 提取
                    String[] 用户名和密码 = 内容.split(" ");
                    String name = 用户名和密码[0];
                    String psw = 用户名和密码[1];
                    // 查询
                    User user = new User(name, psw);
                    int index = users.indexOf(user);
                    if (index == -1) {
                        flag = true;
                        users.add(user);
                    } else {
                        if (users.get(index).Check(user)) {
                            flag = true;
                        } else {
                            flag = false;
                        }
                    }
                    if (flag) {
                        socketProcessor.setName(name);
                        sockets.add(socketProcessor);
                        msg.set内容("true");
                        socketProcessor.sendMsg(msg);
                        System.out.println("新用户：" + name + " " + psw);
                    } else {
                        msg.set内容("false");
                        socketProcessor.sendMsg(msg);
                        socketProcessor.close();
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case "检查":
                try {
                    boolean flag = false;
                    String roomid = 内容;
                    int index = rooms.indexOf(new Room(roomid, "", "", "", this));
                    if (index == -1) {
                        flag = true;
                    } else {
                        flag = false;
                    }
                    if (flag) {
                        msg.set内容("true");
                        socketProcessor.sendMsg(msg);
                    } else {
                        msg.set内容("false");
                        socketProcessor.sendMsg(msg);
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case "开课":
                try {
                    String[] temp = 内容.split("\\*");
                    String roomid = temp[0];
                    String roompsw = temp[1];
                    String begintime = temp[2];
                    rooms.add(new Room(roomid, roompsw, socketProcessor.getName(), begintime, this));
                    System.out.println("开课成功！");
                    msg.set内容("true");
                    socketProcessor.sendMsg(msg);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case "查找":
                try {
                    boolean flag = false;
                    String[] temp = 内容.split(" ");
                    String roomid = temp[0];
                    String roompsw = temp[1];
                    Room room = new Room(roomid, roompsw, "", "", this);
                    String begintime = "false";
                    int index = rooms.indexOf(room);
                    if (index == -1) {
                        flag = false;
                    } else {
                        if (rooms.get(index).Check(room)) {
                            begintime = rooms.get(index).begintime;
                            flag = true;
                        } else {
                            flag = false;
                        }
                    }
                    if (flag) {
                        msg.set内容(begintime);
                        socketProcessor.sendMsg(msg);
                    } else {
                        msg.set内容("false");
                        socketProcessor.sendMsg(msg);
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case "进入课堂":
                try {
                    String[] temp = 内容.split(" ");
                    String roomid = temp[0];
                    String roompsw = temp[1];
                    String name = temp[2];
                    Room room = rooms.get(rooms.indexOf(new Room(roomid, roompsw, "", "", this)));
                    socketProcessor.setSocketUser(room);
                    if (room.teacher.equals(name)) {
                        msg.set内容("teacher");
                        room.setTeacherProcessor(socketProcessor);
                    } else {
                        msg.set内容("student");
                        room.setStudentProcessor(socketProcessor);
                    }
                    socketProcessor.sendMsg(msg);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            default:
                break;

        }
    }

    public void close(String roomid) {
        // TODO Auto-generated method stub
        int index = -1;
        for (int i = 0; i < rooms.size(); i++) {
            if (roomid.equals(rooms.get(i).roomid))
                index = i;
        }
        if (index != -1)
            rooms.remove(index);
    }

    public static void main(String[] args) {
        new Server();
    }
}
