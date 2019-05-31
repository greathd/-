package 教师端.A02管理;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import pool.DateChooserJButton;
import 数据库.DatabaseUtil;

/**
 *
 * @author 不识不知 wx:hbhdlhd96
 * @version 创建时间：2019年4月10日 下午12:43:44
 */
public class 我的学生 extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private 管理系统 father = null;
	private final JPanel 学生选择区 = new JPanel();
	private final JPanel 工作区 = new JPanel();
	private final JScrollPane 学生显示区 = new JScrollPane();
	private final JList<String> 学生列表 = new JList<>();
	private final DefaultListModel<String> 我的学生 = new DefaultListModel<>();
	private final JLabel label = new JLabel("姓名");
	private final JLabel label_1 = new JLabel("性别");
	private final JLabel label_2 = new JLabel("省份");
	private final JLabel label_3 = new JLabel("年级");
	private final JLabel label_5 = new JLabel("成绩");
	private final JLabel 姓名 = new JLabel("张烨");
	private final JLabel 性别 = new JLabel("女");
	private final JLabel 省份 = new JLabel("山东");
	private final JLabel 入学年份 = new JLabel("高二");
	private final JLabel 成绩 = new JLabel("80");
	private final JLabel lblNewLabel = new JLabel("总分");
	private final JLabel label_4 = new JLabel("150");
	private final JScrollPane scrollPane = new JScrollPane();
	private final JTable 上课历史 = new JTable();
	private final JLabel label_6 = new JLabel("历史课堂");
	private final JButton btnNewButton = new DateChooserJButton("选择开课时间");
	private final JComboBox comboBox = new JComboBox();
	private final JLabel label_7 = new JLabel("时长");
	private final JButton button = new JButton("开课");
	private final JPanel panel = new JPanel();
	private final JPanel panel_1 = new JPanel();
	private final JPanel panel_2 = new JPanel();

	public 我的学生(管理系统 father) {
		this.father = father;
		initGUI();
		刷新();
	}

	private void initGUI() {
		setLayout(new BorderLayout(0, 0));

		add(学生显示区, BorderLayout.WEST);
		学生列表.setModel(我的学生);
		学生显示区.setViewportView(学生列表);

		add(工作区, BorderLayout.CENTER);
		工作区.setLayout(new BorderLayout(0, 0));

		工作区.add(panel, BorderLayout.SOUTH);
		panel.add(btnNewButton);
		panel.add(label_7);
		panel.add(comboBox);
		panel.add(button);

		工作区.add(panel_1, BorderLayout.NORTH);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 24, 24, 24, 24, 15, 4, 24, 24, 12, 24, 12, 0 };
		gbl_panel_1.rowHeights = new int[] { 15, 15, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);
		GridBagConstraints gbc_省份 = new GridBagConstraints();
		gbc_省份.anchor = GridBagConstraints.NORTHWEST;
		gbc_省份.insets = new Insets(0, 0, 5, 5);
		gbc_省份.gridx = 0;
		gbc_省份.gridy = 0;
		panel_1.add(省份, gbc_省份);
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.anchor = GridBagConstraints.NORTHWEST;
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 1;
		gbc_label_2.gridy = 0;
		panel_1.add(label_2, gbc_label_2);
		GridBagConstraints gbc_姓名 = new GridBagConstraints();
		gbc_姓名.anchor = GridBagConstraints.NORTHWEST;
		gbc_姓名.insets = new Insets(0, 0, 5, 5);
		gbc_姓名.gridx = 2;
		gbc_姓名.gridy = 0;
		panel_1.add(姓名, gbc_姓名);
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.NORTHWEST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 3;
		gbc_label.gridy = 0;
		panel_1.add(label, gbc_label);
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.anchor = GridBagConstraints.NORTHWEST;
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridwidth = 2;
		gbc_label_3.gridx = 4;
		gbc_label_3.gridy = 0;
		panel_1.add(label_3, gbc_label_3);
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.NORTHWEST;
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 6;
		gbc_label_1.gridy = 0;
		panel_1.add(label_1, gbc_label_1);
		GridBagConstraints gbc_入学年份 = new GridBagConstraints();
		gbc_入学年份.anchor = GridBagConstraints.NORTHWEST;
		gbc_入学年份.insets = new Insets(0, 0, 5, 5);
		gbc_入学年份.gridx = 7;
		gbc_入学年份.gridy = 0;
		panel_1.add(入学年份, gbc_入学年份);
		GridBagConstraints gbc_性别 = new GridBagConstraints();
		gbc_性别.anchor = GridBagConstraints.NORTHWEST;
		gbc_性别.insets = new Insets(0, 0, 5, 5);
		gbc_性别.gridx = 8;
		gbc_性别.gridy = 0;
		panel_1.add(性别, gbc_性别);
		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		gbc_label_5.anchor = GridBagConstraints.NORTHWEST;
		gbc_label_5.insets = new Insets(0, 0, 5, 5);
		gbc_label_5.gridx = 9;
		gbc_label_5.gridy = 0;
		panel_1.add(label_5, gbc_label_5);
		GridBagConstraints gbc_成绩 = new GridBagConstraints();
		gbc_成绩.anchor = GridBagConstraints.NORTHWEST;
		gbc_成绩.insets = new Insets(0, 0, 5, 0);
		gbc_成绩.gridx = 10;
		gbc_成绩.gridy = 0;
		panel_1.add(成绩, gbc_成绩);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.gridx = 3;
		gbc_lblNewLabel.gridy = 1;
		panel_1.add(lblNewLabel, gbc_lblNewLabel);
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.anchor = GridBagConstraints.NORTHWEST;
		gbc_label_4.insets = new Insets(0, 0, 0, 5);
		gbc_label_4.gridwidth = 2;
		gbc_label_4.gridx = 5;
		gbc_label_4.gridy = 1;
		panel_1.add(label_4, gbc_label_4);

		工作区.add(panel_2, BorderLayout.CENTER);
		panel_2.add(scrollPane);

		scrollPane.setViewportView(上课历史);
		panel_2.add(label_6);
	}

	public void 刷新() {
		// TODO Auto-generated method stub
		我的学生.clear();
		try (Connection con = DatabaseUtil.getConnection()) {
			String sql = "SELECT 学生.姓名,学生.Id " + "FROM 学生,师生表 " + "WHERE 师生表.学生号=学生.Id AND 师生表.教师号=? ";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, father.getId());
			// 显示最终执行的SQL语句
			System.out.println(pstmt.toString());
			ResultSet rs = pstmt.executeQuery();
			// 填充表
			while (rs.next()) {
				我的学生.addElement(rs.getInt(2) + " " + rs.getString(1));
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
