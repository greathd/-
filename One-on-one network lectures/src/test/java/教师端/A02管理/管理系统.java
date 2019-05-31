package 教师端.A02管理;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/**
 *
 * @author 不识不知 wx:hbhdlhd96
 * @version 创建时间：2019年3月19日 下午6:41:22
 */
public class 管理系统 extends JFrame {

	/**
	 * 
	 */
	private int id = 0;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final JTabbedPane 显示面板 = new JTabbedPane(JTabbedPane.TOP);
	private final 首页 首页 = new 首页();
	private final 课程表 课程表 = new 课程表(this);
	private final 我的学生 我的学生 = new 我的学生(this);
	private final 薪资结算 薪资结算 = new 薪资结算(this);
	private final 个人信息 个人信息 = new 个人信息(this);

	/**
	 * Create the frame.
	 * 
	 * @param id
	 */
	public 管理系统(int id) {
		this.id = id;
		initGUI();
	}

	/**
	 * 获取教师Id
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	private void initGUI() {
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		显示面板.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
//				JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
				String Tagname = 显示面板.getTitleAt(显示面板.getSelectedIndex());
				System.out.println("当前选择:" + Tagname);
				switch (Tagname) {
				case "首页":
					首页.刷新();
					break;
				case "课程表":
					课程表.刷新();
					break;
				case "我的学生":
					我的学生.刷新();
					break;
				case "薪资结算":
					薪资结算.刷新();
					break;
				case "个人信息":
					个人信息.刷新();
					break;
				default:
					break;
				}
			}
		});

		contentPane.add(显示面板, BorderLayout.CENTER);

		显示面板.addTab("首页", null, 首页, null);

		显示面板.addTab("课程表", null, 课程表, null);

		显示面板.addTab("我的学生", null, 我的学生, null);

		显示面板.addTab("薪资结算", null, 薪资结算, null);

		显示面板.addTab("个人信息", null, 个人信息, null);
	}
}
