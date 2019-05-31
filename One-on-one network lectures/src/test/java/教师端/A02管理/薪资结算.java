package 教师端.A02管理;

import java.awt.Font;
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import 公共组件池.其他组件.JTableUtil;
import 公共组件池.其他组件.时间Util;
import 数据库.DatabaseUtil;

/**
 *
 * @author 不识不知 wx:hbhdlhd96
 * @version 创建时间：2019年4月10日 上午10:26:32
 */
public class 薪资结算 extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1298858092550880761L;
	private 管理系统 father = null;
	private final JLabel 年月 = new JLabel(new 时间Util().得到年月());
	private final JButton button = new JButton("");
	private final JButton button_1 = new JButton("");
	private final JLabel label = new JLabel("老师薪资通过银行卡发放，请老师到个人信息里填写");
	private final JLabel lblNewLabel = new JLabel("测评课总课时（小时）");
	private final JLabel label_1 = new JLabel("正式课总课时（小时）");
	private final JLabel label_2 = new JLabel("实发薪资（元）");
	private final JLabel label_4 = new JLabel("总薪资（元）");
	private final JLabel label_5 = new JLabel("奖惩补贴（元）");
	private final JLabel 测评课总课时 = new JLabel("0");
	private final JLabel 正式课总课时 = new JLabel("0");
	private final JLabel 总薪资 = new JLabel("0");
	private final JLabel 奖惩补贴 = new JLabel("0");
	private final JLabel 实发薪资 = new JLabel("0");
	private final JScrollPane scrollPane = new JScrollPane();
	private final JTable 薪资结算表 = new JTable();
	private final DefaultTableModel 薪资结算表model = new DefaultTableModel(new Object[][] {},
			new String[] { "\u5B66\u751F\u59D3\u540D", "\u8BFE\u5802\u7C7B\u578B", "\u79D1\u76EE",
					"\u4E0A\u8BFE\u65F6\u95F4", "\u65F6\u957F", "\u85AA\u8D44", "\u8BFE\u5802\u60C5\u51B5" });
	private final JScrollPane scrollPane_1 = new JScrollPane();
	private final JTable 奖惩补贴表 = new JTable();
	private final DefaultTableModel 奖惩补贴表model = new DefaultTableModel(new Object[][] { { null, null, null }, },
			new String[] { "\u8BF4\u660E", "\u65F6\u95F4", "\u8865\u8D34\u989D" });

	/**
	 * Create the panel.
	 * 
	 * @param 管理系统
	 */
	public 薪资结算(管理系统 father) {
		this.father = father;
		initGUI();
	}

	private void initGUI() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 0;
		gbc_button.gridy = 0;
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				更新年月();
				更新表();
			}

			private void 更新年月() {
				// TODO Auto-generated method stub
				int month = Integer.parseInt(年月.getText().substring(5, 7));
				int year = Integer.parseInt(年月.getText().substring(0, 4));
				if (month == 1) {
					month = 12;
					year = year - 1;
				} else {
					month -= 1;
				}
				if (month < 10)
					年月.setText(year + "-0" + month);
				else {
					年月.setText(year + "-" + month);
				}
			}
		});
		button.setIcon(new ImageIcon(薪资结算.class.getResource("/图片/上一个.png")));
		add(button, gbc_button);

		GridBagConstraints gbc_年月 = new GridBagConstraints();
		gbc_年月.gridwidth = 3;
		gbc_年月.insets = new Insets(0, 0, 5, 5);
		gbc_年月.gridx = 1;
		gbc_年月.gridy = 0;
		年月.setFont(new Font("微软雅黑", Font.BOLD, 20));
		add(年月, gbc_年月);

		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.insets = new Insets(0, 0, 5, 0);
		gbc_button_1.gridx = 4;
		gbc_button_1.gridy = 0;
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				更新年月();
				更新表();
			}

			private void 更新年月() {
				// TODO Auto-generated method stub
				int month = Integer.parseInt(年月.getText().substring(5, 7));
				int year = Integer.parseInt(年月.getText().substring(0, 4));
				if (month == 12) {
					month = 1;
					year = year + 1;
				} else {
					month += 1;
				}
				if (month < 10)
					年月.setText(year + "-0" + month);
				else {
					年月.setText(year + "-" + month);
				}
			}
		});
		button_1.setIcon(new ImageIcon(薪资结算.class.getResource("/图片/下一个.png")));
		add(button_1, gbc_button_1);

		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.gridwidth = 5;
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridx = 0;
		gbc_label.gridy = 1;
		label.setFont(new Font("新宋体", Font.PLAIN, 20));
		add(label, gbc_label);

		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 2;
		add(lblNewLabel, gbc_lblNewLabel);

		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 2;
		add(label_1, gbc_label_1);

		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.insets = new Insets(0, 0, 5, 5);
		gbc_label_4.gridx = 2;
		gbc_label_4.gridy = 2;
		add(label_4, gbc_label_4);

		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		gbc_label_5.insets = new Insets(0, 0, 5, 5);
		gbc_label_5.gridx = 3;
		gbc_label_5.gridy = 2;
		add(label_5, gbc_label_5);

		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 0);
		gbc_label_2.gridx = 4;
		gbc_label_2.gridy = 2;
		add(label_2, gbc_label_2);

		GridBagConstraints gbc_测评课总课时 = new GridBagConstraints();
		gbc_测评课总课时.insets = new Insets(0, 0, 5, 5);
		gbc_测评课总课时.gridx = 0;
		gbc_测评课总课时.gridy = 3;
		add(测评课总课时, gbc_测评课总课时);

		GridBagConstraints gbc_正式课总课时 = new GridBagConstraints();
		gbc_正式课总课时.insets = new Insets(0, 0, 5, 5);
		gbc_正式课总课时.gridx = 1;
		gbc_正式课总课时.gridy = 3;
		add(正式课总课时, gbc_正式课总课时);

		GridBagConstraints gbc_总薪资 = new GridBagConstraints();
		gbc_总薪资.insets = new Insets(0, 0, 5, 5);
		gbc_总薪资.gridx = 2;
		gbc_总薪资.gridy = 3;
		add(总薪资, gbc_总薪资);

		GridBagConstraints gbc_奖惩补贴 = new GridBagConstraints();
		gbc_奖惩补贴.insets = new Insets(0, 0, 5, 5);
		gbc_奖惩补贴.gridx = 3;
		gbc_奖惩补贴.gridy = 3;
		add(奖惩补贴, gbc_奖惩补贴);

		GridBagConstraints gbc_实发薪资 = new GridBagConstraints();
		gbc_实发薪资.insets = new Insets(0, 0, 5, 0);
		gbc_实发薪资.gridx = 4;
		gbc_实发薪资.gridy = 3;
		add(实发薪资, gbc_实发薪资);

		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridwidth = 5;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 4;
		add(scrollPane, gbc_scrollPane);
		薪资结算表.setModel(薪资结算表model);
		JTableUtil.A格式(薪资结算表);
		scrollPane.setViewportView(薪资结算表);
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridwidth = 5;
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 5;
		add(scrollPane_1, gbc_scrollPane_1);
		奖惩补贴表.setModel(奖惩补贴表model);
		JTableUtil.A格式(奖惩补贴表);
		scrollPane_1.setViewportView(奖惩补贴表);
	}

	protected void 更新表() {
		// TODO Auto-generated method stub
		// 清空两表
		while (薪资结算表model.getRowCount() > 0) {
			薪资结算表model.removeRow(薪资结算表model.getRowCount() - 1);
		}
		while (奖惩补贴表model.getRowCount() > 0) {
			奖惩补贴表model.removeRow(奖惩补贴表model.getRowCount() - 1);
		}
		// 设置时间格式
		String 下月, 本月 = 年月.getText();
		int month = Integer.parseInt(本月.substring(5, 7));
		int year = Integer.parseInt(本月.substring(0, 4));
		if (month == 12) {
			month = 1;
			year = year + 1;
		} else {
			month += 1;
		}
		if (month < 10)
			下月 = year + "-0" + month;
		else {
			下月 = year + "-" + month;
		}
		// 填充薪资结算表
		try (Connection con = DatabaseUtil.getConnection()) {
			String sql = "SELECT 学生.姓名,上课记录.课堂类型,上课记录.科目,上课记录.上课时间,上课记录.时长,上课记录.薪资,上课记录.课堂情况 " + "FROM 学生,上课记录 "
					+ "WHERE 上课记录.学生号=学生.Id " + "AND 上课记录.教师号 = ? " + "AND 上课记录.上课时间 BETWEEN ? AND ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, father.getId());
			pstmt.setString(2, 本月 + "-01 00:00:00");
			pstmt.setString(3, 下月 + "-01 00:00:00");
//			// 显示最终执行的SQL语句
//			System.out.println(pstmt.toString());
			ResultSet rs = pstmt.executeQuery();
			// 填充表
			while (rs.next()) {
				Vector<String> dataVector = new Vector<String>();
				for (int i = 1; i <= 薪资结算表.getColumnCount(); i++) {
					dataVector.addElement(rs.getString(i));
				}
				薪资结算表model.addRow(dataVector);// 添加一行
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 填充奖惩补贴表
		try (Connection con = DatabaseUtil.getConnection()) {
			String sql = "SELECT 说明,时间,补贴额 " + "FROM 补贴记录 " + "WHERE 教师号=? AND 时间 BETWEEN ? AND ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, father.getId());
			pstmt.setString(2, 本月 + "-01 00:00:00");
			pstmt.setString(3, 下月 + "-01 00:00:00");
//			// 显示最终执行的SQL语句
//			System.out.println(pstmt.toString());
			ResultSet rs = pstmt.executeQuery();
			// 填充表
			while (rs.next()) {
				Vector<String> dataVector = new Vector<String>();
				for (int i = 1; i <= 奖惩补贴表.getColumnCount(); i++) {
					dataVector.addElement(rs.getString(i));
				}
				奖惩补贴表model.addRow(dataVector);// 增加一行
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 遍历两表
		double 测评课 = 0;
		double 正式课 = 0;
		double money = 0;
		double bonus = 0;
		for (int i = 0; i < 奖惩补贴表.getRowCount(); i++) {
			bonus += Double.valueOf(奖惩补贴表.getValueAt(i, 2) + "");
		}
		for (int i = 0; i < 薪资结算表.getRowCount(); i++) {
			if (薪资结算表.getValueAt(i, 1).equals("测评课"))
				测评课 += Double.valueOf(薪资结算表.getValueAt(i, 4) + "");
			else {
				正式课 += Double.valueOf(薪资结算表.getValueAt(i, 4) + "");
			}
			if (薪资结算表.getValueAt(i, 6).equals("未开始")) {
				money += 0;
			} else {
				money += Double.valueOf(薪资结算表.getValueAt(i, 5) + "");
			}
		}
		测评课总课时.setText(测评课 + "");
		正式课总课时.setText(正式课 + "");
		总薪资.setText(money + "");
		奖惩补贴.setText(bonus + "");
		实发薪资.setText(Double.valueOf(总薪资.getText() + "") + Double.valueOf(奖惩补贴.getText() + "") + "");
	}

	public void 刷新() {
		// TODO Auto-generated method stub
		更新表();
	}
}