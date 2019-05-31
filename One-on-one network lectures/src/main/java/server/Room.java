package server;
/**
*
* @author 不识不知 wx:hbhdlhd96
* @version 创建时间：2019年5月15日 下午4:01:03
*/

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import 公共组件池.传输组件.Message;
import 公共组件池.传输组件.SocketProcessor;
import 公共组件池.传输组件.SocketUser;

public class Room implements SocketUser {
	class 课件 {
		String name;
		int num;
		ArrayList<byte[]> pages = new ArrayList<byte[]>();

		public 课件(String name) {
			// TODO Auto-generated constructor stub
			this.name = name;
			num = 0;
		}

		public void addpage(byte[] page) {
			pages.add(page);
			num++;
		}

		public byte[] getpage(int i) {
			return pages.get(i);
		}
	}

	boolean classover = false;

	ArrayList<Room.课件> docs = new ArrayList<Room.课件>();
	private HashMap<String, Room.课件> 课件池Map = new HashMap<>(); // 课件池

	String roomid = "";
	String roompsw = "";
	String teacher = "";
	String begintime = "";

	SocketProcessor teacherProcessor = null;
	SocketProcessor studentProcessor = null;

	String teacher状态 = "";
	String student状态 = "";

	String teacher名字 = "";
	String student名字 = "";

	String teacherip = "";
	String studentip = "";

	Server server = null;

	public Room(String roomid, String roompsw, String teacher, String begintime, Server server) {
		super();
		this.roomid = roomid;
		this.roompsw = roompsw;
		this.teacher = teacher;
		this.begintime = begintime;
		this.server = server;
	}

	public void setTeacherProcessor(SocketProcessor teacherProcessor) {
		this.teacherProcessor = teacherProcessor;
	}

	public void setStudentProcessor(SocketProcessor studentProcessor) {
		this.studentProcessor = studentProcessor;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		Room a = (Room) obj;
		if (a.roomid.equals(roomid))
			return true;
		return false;
	}

	public boolean Check(Room room) {
		// TODO Auto-generated method stub
		if (room.roomid.equals(roomid) && room.roompsw.equals(roompsw))
			return true;
		return false;
	}

	@Override
	public void acceptit(SocketProcessor socketProcessor, Message msg) {
		// TODO Auto-generated method stub
		String 身份 = msg.get身份();
		String 描述 = msg.get描述();
		String 内容 = msg.get内容();
		switch (描述) {
		case "下课":
			if (身份.equals("teacher")) {
				teacher状态 = "false";
				student状态 = "false";
				classover = true;
				teacherProcessor.close();
				studentProcessor.close();
				server.close(this.roomid);
			}
		case "退出":
			if (身份.equals("teacher"))
				teacher状态 = "false";
			else {
				student状态 = "false";
			}
		case "恢复现场":
			// 恢复现场
			try {
				System.out.println("服务器端共存有" + docs.size() + "个课件");
				for (int i = 0; i < docs.size(); i++) {
					课件 temp = docs.get(i);
					System.out.println(temp.name + " " + temp.num);
					Message message = new Message();
					message.set身份("room");
					message.set描述("draw");
					message.set内容(temp.name);
					message.setDrawtype("新增课件");
					socketProcessor.sendMsg(message);
					for (int j = 0; j < temp.num; j++) {
						message = new Message();
						message.set身份("room");
						message.set描述("draw");
						message.set内容(temp.name);
						message.setDrawtype("填充页面");
						message.setImg(temp.getpage(j));
						socketProcessor.sendMsg(message);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "ready":
			// 登记状态
			if (身份.equals("teacher")) {
				teacher状态 = "ready";
				teacher名字 = 内容;
				teacherip = teacherProcessor.getIp();
				System.out.println("teacher ready " + teacherip);
			} else {
				student状态 = "ready";
				student名字 = 内容;
				studentip = studentProcessor.getIp();
				System.out.println("student ready " + studentip);
			}
			// 交换IP
			if (teacher状态.equals("ready") && student状态.equals("ready")) {
				teacher状态 = "false";
				student状态 = "false";
				try {
					msg = new Message();
					msg.set描述("ready");
					msg.set内容(studentip);
					msg.set附加(student名字);
					teacherProcessor.sendMsg(msg);
					Message message = new Message();
					message.set描述("ready");
					msg.set内容(teacherip);
					msg.set附加(teacher名字);
					studentProcessor.sendMsg(msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case "消息":
			try {
				teacherProcessor.sendMsg(msg);
				studentProcessor.sendMsg(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "draw":
			课件 doc = null;
			switch (msg.getDrawtype()) {
			case "新增课件":
				doc = new 课件(内容);
				课件池Map.put(内容, doc);
				docs.add(doc);
				break;
			case "填充页面":
				doc = 课件池Map.get(内容);
				doc.addpage(msg.getImg());
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}

	}

}
