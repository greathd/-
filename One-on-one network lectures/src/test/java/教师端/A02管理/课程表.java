package 教师端.A02管理;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import 公共组件池.其他组件.JTableUtil;
import 公共组件池.其他组件.时间Util;
import 客戶端.授课界面;
import 数据库.DatabaseUtil;

/**
 *
 * @author 不识不知 wx:hbhdlhd96
 * @version 创建时间：2019年4月10日 下午12:43:37
 */
public class 课程表 extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2986783410593223151L;
	private 管理系统 father = null;
	private final JScrollPane scrollPane = new JScrollPane();
	private final JTable 课程表 = new JTable();
	private final JPanel panel = new JPanel();
	private final JLabel 第二节课 = new JLabel("请准时上课：");
	private final JButton 刷新 = new JButton("刷新");
	private final JLabel 内容2 = new JLabel("内容");
	private final JLabel 第一节课 = new JLabel("请进入课堂：");
	private final JLabel 内容1 = new JLabel("内容");
	private final JButton 开始上课 = new JButton("开始上课");
	private final DefaultTableModel 课程表model = new DefaultTableModel(new Object[][] {},
			new String[] { "\u8BFE\u5802\u7C7B\u578B", "\u79D1\u76EE", "\u5B66\u751F\u59D3\u540D",
					"\u4E0A\u8BFE\u65F6\u95F4", "\u65F6\u957F" });

	public 课程表(管理系统 father) {
		// TODO Auto-generated constructor stub
		this.father = father;
		initGUI();
	}

	private void initGUI() {
		setLayout(new BorderLayout(0, 0));

		add(scrollPane);
		课程表.setModel(课程表model);
		JTableUtil.A格式(课程表);
		scrollPane.setViewportView(课程表);

		add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		GridBagConstraints gbc_第一节课 = new GridBagConstraints();
		gbc_第一节课.insets = new Insets(0, 0, 5, 5);
		gbc_第一节课.gridx = 0;
		gbc_第一节课.gridy = 0;
		panel.add(第一节课, gbc_第一节课);

		GridBagConstraints gbc_内容1 = new GridBagConstraints();
		gbc_内容1.insets = new Insets(0, 0, 5, 5);
		gbc_内容1.gridx = 1;
		gbc_内容1.gridy = 0;
		panel.add(内容1, gbc_内容1);

		GridBagConstraints gbc_开始上课 = new GridBagConstraints();
		gbc_开始上课.anchor = GridBagConstraints.EAST;
		gbc_开始上课.insets = new Insets(0, 0, 5, 0);
		gbc_开始上课.gridx = 2;
		gbc_开始上课.gridy = 0;
		开始上课.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				开始上课();
			}
		});
		panel.add(开始上课, gbc_开始上课);

		GridBagConstraints gbc_第二节课 = new GridBagConstraints();
		gbc_第二节课.insets = new Insets(0, 0, 0, 5);
		gbc_第二节课.gridx = 0;
		gbc_第二节课.gridy = 1;
		panel.add(第二节课, gbc_第二节课);

		GridBagConstraints gbc_内容2 = new GridBagConstraints();
		gbc_内容2.insets = new Insets(0, 0, 0, 5);
		gbc_内容2.gridx = 1;
		gbc_内容2.gridy = 1;
		panel.add(内容2, gbc_内容2);

		GridBagConstraints gbc_刷新 = new GridBagConstraints();
		gbc_刷新.anchor = GridBagConstraints.EAST;
		gbc_刷新.gridx = 2;
		gbc_刷新.gridy = 1;
		刷新.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				刷新();
			}
		});
		panel.add(刷新, gbc_刷新);
	}

	protected void 开始上课() {
		// TODO Auto-generated method stub
		String[] temp = 内容1.getText().split(" ");
		String time = temp[1] + " " + temp[2];
		int 课堂号 = 0;
		try (Connection con = DatabaseUtil.getConnection()) {
			String sql = "SELECT 课堂号 FROM 上课记录 WHERE 教师号 = ? AND 上课时间 = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, father.getId());
			pstmt.setString(2, time);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				课堂号 = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (课堂号 != 0) {
//			教师端授课界面.go(课堂号, father.getId());
		}
	}

	public void 刷新() {
		// TODO Auto-generated method stub
		// 清空表
		while (课程表model.getRowCount() > 0) {
			课程表model.removeRow(课程表model.getRowCount() - 1);
		}
		// 填充表
		try (Connection con = DatabaseUtil.getConnection()) {
			String sql = "SELECT 上课记录.课堂类型,上课记录.科目,学生.姓名,上课记录.上课时间,上课记录.时长,上课记录.课堂号 " + "FROM 学生,上课记录 "
					+ "WHERE 上课记录.学生号=学生.Id " + "AND 上课记录.教师号 = ? "
					+ "AND date_add(上课记录.上课时间,interval +(60*上课记录.时长) minute) > curtime() " + "ORDER BY 上课记录.上课时间 ASC";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, father.getId());
//			 显示最终执行的SQL语句
//			System.out.println(pstmt.toString());
			ResultSet rs = pstmt.executeQuery();
			// 填充表
			while (rs.next()) {
				Vector<String> dataVector = new Vector<String>();
				for (int i = 1; i <= 课程表.getColumnCount(); i++) {
					dataVector.addElement(rs.getString(i));
				}
				课程表model.addRow(dataVector);// 添加一行
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 遍历表
		开始上课.setVisible(false);
		String time = (课程表.getValueAt(0, 3) + "");
		int lenth = (int) (Double.valueOf((课程表.getValueAt(0, 4) + "")) * 60);
//		System.out.println(time + " " + lenth);
		if (时间Util.是否A早于B(time, new 时间Util().得到当前时间())
				&& 时间Util.是否A早于B(new 时间Util().得到当前时间(), new 时间Util(time).加分钟(lenth))) {
			开始上课.setVisible(true);
		}
		if (课程表.getRowCount() == 0) {
			// 没有要上的课
			第一节课.setText("您暂时没有要上的课哦！");
			内容1.setText("");
			第二节课.setText("");
			内容2.setText("");
		} else if (课程表.getRowCount() == 1) {
			// 只有一节课
			第一节课.setText("请您准时上课");
			内容1.setText("" + 课程表.getValueAt(0, 2) + " " + 课程表.getValueAt(0, 3));
			第二节课.setText("");
			内容2.setText("");
		} else {
			第一节课.setText("请您准时上课");
			内容1.setText("" + 课程表.getValueAt(0, 2) + " " + 课程表.getValueAt(0, 3));
			第二节课.setText("请您准时上课");
			内容2.setText("" + 课程表.getValueAt(1, 2) + " " + 课程表.getValueAt(1, 3));
		}
	}

}
