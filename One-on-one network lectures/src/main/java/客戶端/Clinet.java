package 客戶端;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import pool.DateChooserJButton;
import pool.常量池;
import pool.服务器探测器;
import 公共组件池.传输组件.Message;
import 公共组件池.传输组件.SocketProcessor;
import 公共组件池.传输组件.SocketUser;

/**
 *
 * @author 不识不知 wx:hbhdlhd96
 * @version 创建时间：2019年5月6日 下午12:45:07
 */
public class Clinet extends JFrame implements SocketUser {
	private Clinet clinet;
	private SocketProcessor server = null;
	private String name = "";
	private String psw = "";
	private String cur_roomid;
	private String cur_roompsw;
	private String cur_begintime;

	private JPanel contentPane;
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenuItem mntmNewMenuItem_1 = new JMenuItem("退出");
	private final JMenuItem mntmNewMenuItem_2 = new JMenuItem("切换用户");
	private final JMenu mnNewMenu = new JMenu("系统");
	private final JMenuItem mntmNewMenuItem = new JMenuItem("帮助");
	private final JMenu mnNewMenu_1 = new JMenu("关于");
	private final JMenuItem menuItem = new JMenuItem("检查更新");
	private final JPanel 主界面 = new JPanel();
	private final JPanel panel = new JPanel();
	private final JLabel label = new JLabel("用户名");
	private final JLabel namel = new JLabel("请先登录!");
	private final JTextField newroomid = new JTextField();
	private final JLabel label_1 = new JLabel("房间号");
	private final JLabel label_2 = new JLabel("密码");
	private final JTextField newroompsw = new JTextField();
	private final JButton 时间 = new DateChooserJButton("选择日期");
	private final JLabel label_3 = new JLabel("时间");
	private final JButton 开课 = new JButton("开课");
	private final JButton 检查 = new JButton("检查");
	private final JPanel 开课通道 = new JPanel();
	private final JPanel 上课通道 = new JPanel();
	private final JLabel label_4 = new JLabel("房间号");
	private final JTextField roomid = new JTextField();
	private final JLabel label_5 = new JLabel("密码");
	private final JTextField roompsw = new JTextField();
	private final JButton 查找 = new JButton("查找");
	private final JButton 进入课堂 = new JButton("进入课堂");
	private final JLabel 课堂信息 = new JLabel("当前暂无匹配课堂");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Clinet frame = new Clinet();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void go() throws UnknownHostException, IOException {
		// 初始化并登记
		初始化();
	}

	private void 初始化() {
		// TODO Auto-generated method stub
		clinet = this;
		String input = JOptionPane.showInputDialog("请输入用户名和密码，并用空格分隔，未注册会直接为您注册！");
		while (input.indexOf(" ") < 0) {
			input = JOptionPane.showInputDialog("请输入用户名和密码，并用空格分隔，未注册会直接为您注册！");
		}
		String[] b = input.split(" ");
		name = b[0];
		psw = b[1];
		连接服务器();
	}

	private void 连接服务器() {
		// TODO Auto-generated method stub
		new Thread(() -> {
			服务器探测器 a;
			try {
				a = new 服务器探测器();
				while (!a.找到()) {
					a.探测();
				}
				if (server != null)
					server.close();
				System.out.println("探测到服务器 " + a.getIp());
//				server = new SocketProcessor("localhost", 常量池.服务器通信端口, this);
				server = new SocketProcessor(a.getIp(), 常量池.服务器通信端口, this);
				Message msg = new Message();
				msg.set描述("登记");
				msg.set内容(name + " " + psw);
				server.sendMsg(msg);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
	}

	/**
	 * Create the frame.
	 * 
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public Clinet() throws UnknownHostException, IOException {
		roompsw.setBounds(74, 80, 122, 21);
		roompsw.setColumns(10);
		roomid.setBounds(74, 36, 122, 21);
		roomid.setColumns(10);
		newroompsw.setBounds(66, 64, 159, 21);
		开课通道.add(newroompsw);
		newroompsw.setColumns(10);
		newroomid.setBounds(66, 33, 159, 21);
		开课通道.add(newroomid);
		newroomid.setColumns(10);
		initGUI();
		go();
	}

	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 517, 300);
		setJMenuBar(menuBar);

		menuBar.add(mnNewMenu);
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				初始化();
			}
		});
		mnNewMenu.add(mntmNewMenuItem_2);
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);

		menuBar.add(mnNewMenu_1);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(new JFrame(), "您的软件已是最新版本！");
			}
		});

		mnNewMenu_1.add(menuItem);
		mnNewMenu_1.add(mntmNewMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		contentPane.add(主界面);
		主界面.setLayout(null);
		开课通道.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"\u5F00\u8BFE\u901A\u9053", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		开课通道.setBounds(10, 10, 261, 172);

		主界面.add(开课通道);
		开课通道.setLayout(null);
		label_1.setBounds(10, 36, 54, 15);
		开课通道.add(label_1);
		label_2.setBounds(10, 65, 54, 15);
		开课通道.add(label_2);
		时间.setBounds(66, 95, 159, 23);
		开课通道.add(时间);
		label_3.setBounds(10, 99, 54, 15);
		开课通道.add(label_3);
		开课.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				开课();
			}
		});
		开课.setEnabled(false);
		开课.setBounds(127, 128, 98, 23);
		开课通道.add(开课);
		检查.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				检查();
			}
		});
		检查.setBounds(10, 128, 107, 23);
		开课通道.add(检查);
		上课通道.setBorder(
				new TitledBorder(null, "\u4E0A\u8BFE\u901A\u9053", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		上课通道.setBounds(281, 10, 213, 172);

		主界面.add(上课通道);
		上课通道.setLayout(null);
		label_4.setBounds(10, 39, 54, 15);

		上课通道.add(label_4);

		上课通道.add(roomid);
		label_5.setBounds(10, 83, 54, 15);

		上课通道.add(label_5);

		上课通道.add(roompsw);
		查找.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				查找();
			}
		});
		查找.setBounds(10, 128, 83, 23);

		上课通道.add(查找);
		进入课堂.setEnabled(false);
		进入课堂.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				进入课堂();
			}
		});
		进入课堂.setBounds(103, 128, 93, 23);

		上课通道.add(进入课堂);
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);

		contentPane.add(panel, BorderLayout.NORTH);
		label.setForeground(Color.BLUE);
		label.setFont(new Font("微软雅黑", Font.PLAIN, 15));

		panel.add(label);
		namel.setFont(new Font("微软雅黑", Font.PLAIN, 15));

		panel.add(namel);
		课堂信息.setForeground(Color.RED);
		课堂信息.setFont(new Font("微软雅黑", Font.PLAIN, 13));

		panel.add(课堂信息);

		// ---------居中显示----------
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int x = (int) (toolkit.getScreenSize().getWidth() - getWidth()) / 2;
		int y = (int) (toolkit.getScreenSize().getHeight() - getHeight()) / 2;
		setLocation(x, y);
		// ---------居中显示----------
	}

	protected void 进入课堂() {
		// TODO Auto-generated method stub
		try {
			Message msg = new Message();
			msg.set描述("进入课堂");
			msg.set内容(cur_roomid + " " + cur_roompsw + " " + name);
			server.sendMsg(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			连接服务器();
		}
	}

	protected void 查找() {
		// TODO Auto-generated method stub
		try {
			Message msg = new Message();
			msg.set描述("查找");
			cur_roomid = roomid.getText();
			cur_roompsw = roompsw.getText();
			msg.set内容(cur_roomid + " " + cur_roompsw);
			server.sendMsg(msg);
			roomid.setText("");
			roompsw.setText("");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			连接服务器();
		}
	}

	protected void 开课() {
		// TODO Auto-generated method stub
		try {
			Message msg = new Message();
			msg.set描述("开课");
			msg.set内容(newroomid.getText() + "*" + newroompsw.getText() + "*" + 时间.getText());
			server.sendMsg(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			连接服务器();
		}
	}

	protected void 检查() {
		// TODO Auto-generated method stub
		try {
			Message msg = new Message();
			msg.set描述("检查");
			msg.set内容(newroomid.getText());
			server.sendMsg(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			连接服务器();
		}
	}

	@Override
	public void acceptit(SocketProcessor socketProcessor, Message msg) {
		// TODO Auto-generated method stub
		String 描述 = msg.get描述();
		String 内容 = msg.get内容();
		switch (描述) {
		case "登记":
			if (内容.equals("true"))
				namel.setText(name);
			else {
				namel.setText("登录未成功！");
				初始化();
			}
			break;
		case "检查":
			if (内容.equals("true")) {
				JOptionPane.showMessageDialog(this, "检查通过，该房间号可以使用！");
				开课.setEnabled(true);
			} else {
				JOptionPane.showMessageDialog(this, "检查不通过，该房间号已存在！");
				开课.setEnabled(false);
			}
			break;
		case "开课":
			if (内容.equals("true")) {
				JOptionPane.showMessageDialog(null, "开课成功！");
				newroomid.setText("");
				newroompsw.setText("");
				开课.setEnabled(false);
			} else {
				JOptionPane.showMessageDialog(this, "开课失败，请检查网络后重试！");
			}
			break;
		case "查找":
			if (内容.equals("false")) {
				JOptionPane.showMessageDialog(null, "请检查房间号和密码！");
				课堂信息.setText("当前暂无匹配课堂");
				进入课堂.setEnabled(false);
			} else {
				JOptionPane.showMessageDialog(this, "课堂匹配成功，上课时间为" + 内容);
				cur_begintime = 内容;
				进入课堂.setEnabled(true);
				课堂信息.setText("课堂匹配成功！房间号 " + cur_roomid + " 时间 " + cur_begintime);
			}
			break;
		case "进入课堂":
			// 隐藏管理系统
			dispose();
			// 进入授课界面
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						授课界面 a = new 授课界面(server, 内容, name, cur_begintime, clinet);
						a.frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			break;
		default:
			break;
		}
	}
}
