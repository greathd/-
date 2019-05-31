package 教师端.A02管理;

import java.awt.Color;
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

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import pool.常量池;
import 数据库.DatabaseUtil;

/**
 *
 * @author 不识不知 wx:hbhdlhd96
 * @version 创建时间：2019年4月9日 上午10:39:42
 */
public class 个人信息 extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8342380017837403554L;
	private 管理系统 father = null;
	private final JLabel lblNewLabel = new JLabel("姓名");
	private final JTextField 姓名 = new JTextField();
	private final JLabel label = new JLabel("性别");
	private final JTextField 性别 = new JTextField();
	private final JLabel label_1 = new JLabel("联系电话");
	private final JTextField 联系电话 = new JTextField();
	private final JLabel label_2 = new JLabel("邮箱");
	private final JTextField 邮箱 = new JTextField();
	private final JLabel label_3 = new JLabel("微信号");
	private final JLabel lblQq = new JLabel("QQ号");
	private final JTextField 微信号 = new JTextField();
	private final JTextField QQ号 = new JTextField();
	private final JButton 基本信息编辑保存 = new JButton("编辑");
	private final JLabel label_4 = new JLabel("基本信息");
	private final JLabel label_5 = new JLabel("教育背景");
	private final JLabel label_6 = new JLabel("本科学校");
	private final JLabel label_7 = new JLabel("专业");
	private final JLabel label_8 = new JLabel("高考省份");
	private final JLabel 年级 = new JLabel("教学年级");
	private final JLabel label_9 = new JLabel("教学科目");
	private final JLabel 高考成绩 = new JLabel("学科高考成绩");
	private final JButton 教育背景编辑保存 = new JButton("编辑");
	private final JTextField 本科学校 = new JTextField();
	private final JTextField 专业 = new JTextField();
	private final JTextField 学科高考成绩 = new JTextField();
	private final JComboBox<String> 教学科目 = new JComboBox<>();
	private final JComboBox<String> 高考省份 = new JComboBox<>();
	private final JComboBox<String> 教学年级 = new JComboBox<>();
	private final JLabel label_10 = new JLabel("个人介绍");
	private final JLabel label_11 = new JLabel("兴趣爱好");
	private final JTextField 兴趣爱好 = new JTextField();
	private final JLabel label_12 = new JLabel("教学经验");
	private final JLabel label_13 = new JLabel("获奖经历");
	private final JLabel lblNewLabel_1 = new JLabel("教学心得");
	private final JLabel label_14 = new JLabel("教学风格");
	private final JTextField 教学经验 = new JTextField();
	private final JTextField 获奖经历 = new JTextField();
	private final JTextField 教学心得 = new JTextField();
	private final JTextField 教学风格 = new JTextField();
	private final JButton 个人介绍编辑保存 = new JButton("编辑");
	private final JLabel label_15 = new JLabel("薪资支付");
	private final JLabel label_16 = new JLabel("证件类型");
	private final JTextField 证件类型 = new JTextField();
	private final JLabel label_17 = new JLabel("证件号");
	private final JTextField 证件号 = new JTextField();
	private final JButton 薪资支付编辑保存 = new JButton("编辑");
	private final JLabel label_18 = new JLabel("支付宝");
	private final JTextField 支付宝 = new JTextField();
	private final JLabel label_19 = new JLabel("等级");
	private final JLabel 等级 = new JLabel("");
	private final JLabel label_20 = new JLabel("时薪");
	private final JLabel 时薪 = new JLabel("");

	/**
	 * Create the panel.
	 * 
	 * @param 管理系统
	 */
	public 个人信息(管理系统 father) {
		this.father = father;
		initGUI();
		刷新();
	}

	public void 刷新() {
		// TODO 页面刷新函数
		try (Connection con = DatabaseUtil.getConnection()) {
			String sql = "SELECT * FROM 教师 WHERE Id = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, father.getId());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				姓名.setText(rs.getString("姓名"));
				性别.setText(rs.getString("性别"));
				联系电话.setText(rs.getString("联系电话"));
				邮箱.setText(rs.getString("邮箱"));
				微信号.setText(rs.getString("微信号"));
				QQ号.setText(rs.getString("QQ号"));
				本科学校.setText(rs.getString("本科学校"));
				专业.setText(rs.getString("专业"));
				高考省份.setSelectedItem(rs.getString("高考省份"));
				学科高考成绩.setText(rs.getString("学科高考成绩"));
				教学科目.setSelectedItem(rs.getString("教学科目"));
				教学年级.setSelectedItem(rs.getString("教学年级"));
				兴趣爱好.setText(rs.getString("兴趣爱好"));
				教学经验.setText(rs.getString("教学经验"));
				获奖经历.setText(rs.getString("获奖经历"));
				教学心得.setText(rs.getString("教学心得"));
				教学风格.setText(rs.getString("教学风格"));
				等级.setText(rs.getString("等级"));
				时薪.setText(常量池.get时薪(等级.getText()) + "");
				证件类型.setText(rs.getString("证件类型"));
				证件号.setText(rs.getString("证件号"));
				支付宝.setText(rs.getString("支付宝"));
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException ex) {
			System.out.println("server.module.search()");
		}
	}

	private void initGUI() {
		支付宝.setFont(new Font("新宋体", Font.PLAIN, 14));
		支付宝.setEditable(false);
		支付宝.setColumns(30);
		证件号.setFont(new Font("新宋体", Font.PLAIN, 14));
		证件号.setEditable(false);
		证件号.setColumns(30);
		证件类型.setFont(new Font("新宋体", Font.PLAIN, 14));
		证件类型.setEditable(false);
		证件类型.setColumns(30);
		兴趣爱好.setFont(new Font("新宋体", Font.PLAIN, 14));
		兴趣爱好.setEditable(false);
		兴趣爱好.setColumns(100);
		学科高考成绩.setFont(new Font("新宋体", Font.PLAIN, 14));
		学科高考成绩.setEditable(false);
		学科高考成绩.setColumns(30);
		专业.setFont(new Font("新宋体", Font.PLAIN, 14));
		专业.setEditable(false);
		专业.setColumns(30);
		本科学校.setFont(new Font("新宋体", Font.PLAIN, 14));
		本科学校.setEditable(false);
		本科学校.setColumns(30);
		QQ号.setFont(new Font("新宋体", Font.PLAIN, 14));
		QQ号.setEditable(false);
		QQ号.setColumns(30);
		微信号.setFont(new Font("新宋体", Font.PLAIN, 14));
		微信号.setEditable(false);
		微信号.setColumns(30);
		邮箱.setFont(new Font("新宋体", Font.PLAIN, 14));
		邮箱.setEditable(false);
		邮箱.setColumns(30);
		联系电话.setFont(new Font("新宋体", Font.PLAIN, 14));
		联系电话.setEditable(false);
		联系电话.setColumns(30);
		性别.setFont(new Font("新宋体", Font.PLAIN, 14));
		性别.setEditable(false);
		性别.setColumns(30);
		姓名.setFont(new Font("新宋体", Font.PLAIN, 14));
		姓名.setEditable(false);
		姓名.setColumns(30);
		setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"\u4E2A\u4EBA\u4FE1\u606F", TitledBorder.CENTER, TitledBorder.TOP, null, Color.MAGENTA));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 48, 66, 24, 66, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 21, 21, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
				1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.anchor = GridBagConstraints.WEST;
		gbc_label_4.insets = new Insets(0, 0, 5, 5);
		gbc_label_4.gridx = 0;
		gbc_label_4.gridy = 0;
		label_4.setFont(new Font("新宋体", Font.PLAIN, 14));
		add(label_4, gbc_label_4);

		GridBagConstraints gbc_基本信息编辑保存 = new GridBagConstraints();
		gbc_基本信息编辑保存.anchor = GridBagConstraints.WEST;
		gbc_基本信息编辑保存.insets = new Insets(0, 0, 5, 0);
		gbc_基本信息编辑保存.gridx = 4;
		gbc_基本信息编辑保存.gridy = 0;
		基本信息编辑保存.setFont(new Font("新宋体", Font.PLAIN, 14));
		基本信息编辑保存.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (基本信息编辑保存.getText().equals("编辑")) {
					基本信息编辑保存.setText("保存");
					联系电话.setEditable(true);
					邮箱.setEditable(true);
					微信号.setEditable(true);
					QQ号.setEditable(true);
				} else {
					基本信息编辑保存.setText("编辑");
					联系电话.setEditable(false);
					邮箱.setEditable(false);
					微信号.setEditable(false);
					QQ号.setEditable(false);
					个人信息更新();
					刷新();
				}
			}

			private void 个人信息更新() {
				// TODO 基本信息更新
				try (Connection con = DatabaseUtil.getConnection()) {
					String sql = "UPDATE 教师 SET 联系电话 = ?,邮箱 = ?,微信号 = ?,QQ号 = ? WHERE Id = ?";
					PreparedStatement pstmt = con.prepareStatement(sql);
					pstmt.setString(1, 联系电话.getText());
					pstmt.setString(2, 邮箱.getText());
					pstmt.setString(3, 微信号.getText());
					pstmt.setString(4, QQ号.getText());
					pstmt.setInt(5, father.getId());
					pstmt.executeUpdate();
					pstmt.close();
					con.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});
		add(基本信息编辑保存, gbc_基本信息编辑保存);

		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		lblNewLabel.setFont(new Font("新宋体", Font.PLAIN, 14));
		add(lblNewLabel, gbc_lblNewLabel);

		GridBagConstraints gbc_姓名 = new GridBagConstraints();
		gbc_姓名.anchor = GridBagConstraints.WEST;
		gbc_姓名.insets = new Insets(0, 0, 5, 5);
		gbc_姓名.gridx = 1;
		gbc_姓名.gridy = 1;
		add(姓名, gbc_姓名);

		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 2;
		gbc_label.gridy = 1;
		label.setFont(new Font("新宋体", Font.PLAIN, 14));
		add(label, gbc_label);

		GridBagConstraints gbc_性别 = new GridBagConstraints();
		gbc_性别.anchor = GridBagConstraints.WEST;
		gbc_性别.insets = new Insets(0, 0, 5, 5);
		gbc_性别.gridx = 3;
		gbc_性别.gridy = 1;
		add(性别, gbc_性别);

		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.WEST;
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 2;
		label_1.setFont(new Font("新宋体", Font.PLAIN, 14));
		add(label_1, gbc_label_1);

		GridBagConstraints gbc_联系电话 = new GridBagConstraints();
		gbc_联系电话.anchor = GridBagConstraints.WEST;
		gbc_联系电话.insets = new Insets(0, 0, 5, 5);
		gbc_联系电话.gridx = 1;
		gbc_联系电话.gridy = 2;
		add(联系电话, gbc_联系电话);

		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.anchor = GridBagConstraints.WEST;
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 2;
		gbc_label_2.gridy = 2;
		label_2.setFont(new Font("新宋体", Font.PLAIN, 14));
		add(label_2, gbc_label_2);

		GridBagConstraints gbc_邮箱 = new GridBagConstraints();
		gbc_邮箱.anchor = GridBagConstraints.WEST;
		gbc_邮箱.insets = new Insets(0, 0, 5, 5);
		gbc_邮箱.gridx = 3;
		gbc_邮箱.gridy = 2;
		add(邮箱, gbc_邮箱);

		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.anchor = GridBagConstraints.WEST;
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 0;
		gbc_label_3.gridy = 3;
		label_3.setFont(new Font("新宋体", Font.PLAIN, 14));
		add(label_3, gbc_label_3);

		GridBagConstraints gbc_微信号 = new GridBagConstraints();
		gbc_微信号.anchor = GridBagConstraints.WEST;
		gbc_微信号.insets = new Insets(0, 0, 5, 5);
		gbc_微信号.gridx = 1;
		gbc_微信号.gridy = 3;
		add(微信号, gbc_微信号);

		GridBagConstraints gbc_lblQq = new GridBagConstraints();
		gbc_lblQq.anchor = GridBagConstraints.WEST;
		gbc_lblQq.insets = new Insets(0, 0, 5, 5);
		gbc_lblQq.gridx = 2;
		gbc_lblQq.gridy = 3;
		lblQq.setFont(new Font("新宋体", Font.PLAIN, 14));
		add(lblQq, gbc_lblQq);

		GridBagConstraints gbc_QQ号 = new GridBagConstraints();
		gbc_QQ号.anchor = GridBagConstraints.WEST;
		gbc_QQ号.insets = new Insets(0, 0, 5, 5);
		gbc_QQ号.gridx = 3;
		gbc_QQ号.gridy = 3;
		add(QQ号, gbc_QQ号);

		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		gbc_label_5.anchor = GridBagConstraints.WEST;
		gbc_label_5.insets = new Insets(0, 0, 5, 5);
		gbc_label_5.gridx = 0;
		gbc_label_5.gridy = 4;
		label_5.setFont(new Font("新宋体", Font.PLAIN, 14));
		add(label_5, gbc_label_5);

		GridBagConstraints gbc_教育背景编辑保存 = new GridBagConstraints();
		gbc_教育背景编辑保存.anchor = GridBagConstraints.WEST;
		gbc_教育背景编辑保存.insets = new Insets(0, 0, 5, 0);
		gbc_教育背景编辑保存.gridx = 4;
		gbc_教育背景编辑保存.gridy = 4;
		教育背景编辑保存.setFont(new Font("新宋体", Font.PLAIN, 14));
		教育背景编辑保存.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (教育背景编辑保存.getText().equals("编辑")) {
					教育背景编辑保存.setText("保存");
					本科学校.setEditable(true);
					专业.setEditable(true);
					学科高考成绩.setEditable(true);
					高考省份.setEnabled(true);
					教学科目.setEnabled(true);
					教学年级.setEnabled(true);

				} else {
					教育背景编辑保存.setText("编辑");
					本科学校.setEditable(false);
					专业.setEditable(false);
					学科高考成绩.setEditable(false);
					高考省份.setEnabled(false);
					教学科目.setEnabled(false);
					教学年级.setEnabled(false);
					个人信息更新();
					刷新();
				}
			}

			private void 个人信息更新() {
				// TODO Auto-generated method stub
				try (Connection con = DatabaseUtil.getConnection()) {
					String sql = "UPDATE 教师 SET 本科学校 = ?,专业 = ?,学科高考成绩 = ?,高考省份 = ?,教学科目 = ?,教学年级=? WHERE Id = ?";
					PreparedStatement pstmt = con.prepareStatement(sql);
					pstmt.setString(1, 本科学校.getText());
					pstmt.setString(2, 专业.getText());
					pstmt.setString(3, 学科高考成绩.getText());
					pstmt.setString(4, 高考省份.getSelectedItem().toString());
					pstmt.setString(5, 教学科目.getSelectedItem().toString());
					pstmt.setString(6, 教学年级.getSelectedItem().toString());
					pstmt.setInt(7, father.getId());
					pstmt.executeUpdate();
					pstmt.close();
					con.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});
		add(教育背景编辑保存, gbc_教育背景编辑保存);

		GridBagConstraints gbc_label_6 = new GridBagConstraints();
		gbc_label_6.anchor = GridBagConstraints.WEST;
		gbc_label_6.insets = new Insets(0, 0, 5, 5);
		gbc_label_6.gridx = 0;
		gbc_label_6.gridy = 5;
		label_6.setFont(new Font("新宋体", Font.PLAIN, 14));
		add(label_6, gbc_label_6);

		GridBagConstraints gbc_本科学校 = new GridBagConstraints();
		gbc_本科学校.anchor = GridBagConstraints.WEST;
		gbc_本科学校.insets = new Insets(0, 0, 5, 5);
		gbc_本科学校.gridx = 1;
		gbc_本科学校.gridy = 5;
		add(本科学校, gbc_本科学校);

		GridBagConstraints gbc_label_7 = new GridBagConstraints();
		gbc_label_7.anchor = GridBagConstraints.WEST;
		gbc_label_7.insets = new Insets(0, 0, 5, 5);
		gbc_label_7.gridx = 2;
		gbc_label_7.gridy = 5;
		label_7.setFont(new Font("新宋体", Font.PLAIN, 14));
		add(label_7, gbc_label_7);

		GridBagConstraints gbc_专业 = new GridBagConstraints();
		gbc_专业.anchor = GridBagConstraints.WEST;
		gbc_专业.insets = new Insets(0, 0, 5, 5);
		gbc_专业.gridx = 3;
		gbc_专业.gridy = 5;
		add(专业, gbc_专业);

		GridBagConstraints gbc_label_8 = new GridBagConstraints();
		gbc_label_8.anchor = GridBagConstraints.WEST;
		gbc_label_8.insets = new Insets(0, 0, 5, 5);
		gbc_label_8.gridx = 0;
		gbc_label_8.gridy = 6;
		label_8.setFont(new Font("新宋体", Font.PLAIN, 14));
		add(label_8, gbc_label_8);

		GridBagConstraints gbc_高考省份 = new GridBagConstraints();
		gbc_高考省份.anchor = GridBagConstraints.WEST;
		gbc_高考省份.insets = new Insets(0, 0, 5, 5);
		gbc_高考省份.gridx = 1;
		gbc_高考省份.gridy = 6;
		高考省份.setFont(new Font("新宋体", Font.PLAIN, 14));
		高考省份.setEnabled(false);
		高考省份.setModel(new DefaultComboBoxModel<String>(
				new String[] { "北京市", "天津市", "上海市", "重庆市", "河北省", "山西省", "辽宁省", "吉林省", "黑龙江省", "江苏省", "浙江省", "安徽省",
						"福建省", "江西省", "山东省", "河南省", "湖北省", "湖南省", "广东省", "海南省", "四川省", "贵州省", "云南省", "陕西省", "甘肃省",
						"青海省", "台湾省", "内蒙古自治区", "广西壮族自治区", "西藏自治区", "宁夏回族自治区", "新疆维吾尔自治区", "香港特别行政区", "澳门特别行政区" }));
		add(高考省份, gbc_高考省份);

		GridBagConstraints gbc_高考成绩 = new GridBagConstraints();
		gbc_高考成绩.anchor = GridBagConstraints.WEST;
		gbc_高考成绩.insets = new Insets(0, 0, 5, 5);
		gbc_高考成绩.gridx = 2;
		gbc_高考成绩.gridy = 6;
		高考成绩.setFont(new Font("新宋体", Font.PLAIN, 14));
		add(高考成绩, gbc_高考成绩);

		GridBagConstraints gbc_学科高考成绩 = new GridBagConstraints();
		gbc_学科高考成绩.anchor = GridBagConstraints.WEST;
		gbc_学科高考成绩.insets = new Insets(0, 0, 5, 5);
		gbc_学科高考成绩.gridx = 3;
		gbc_学科高考成绩.gridy = 6;
		add(学科高考成绩, gbc_学科高考成绩);

		GridBagConstraints gbc_label_9 = new GridBagConstraints();
		gbc_label_9.anchor = GridBagConstraints.WEST;
		gbc_label_9.insets = new Insets(0, 0, 5, 5);
		gbc_label_9.gridx = 0;
		gbc_label_9.gridy = 7;
		label_9.setFont(new Font("新宋体", Font.PLAIN, 14));
		add(label_9, gbc_label_9);

		GridBagConstraints gbc_教学科目 = new GridBagConstraints();
		gbc_教学科目.anchor = GridBagConstraints.WEST;
		gbc_教学科目.insets = new Insets(0, 0, 5, 5);
		gbc_教学科目.gridx = 1;
		gbc_教学科目.gridy = 7;
		教学科目.setFont(new Font("新宋体", Font.PLAIN, 14));
		教学科目.setEnabled(false);
		教学科目.setModel(new DefaultComboBoxModel<String>(new String[] { "数学", "英语", "语文", "物理", "化学", "生物", "地理" }));
		add(教学科目, gbc_教学科目);

		GridBagConstraints gbc_年级 = new GridBagConstraints();
		gbc_年级.anchor = GridBagConstraints.WEST;
		gbc_年级.insets = new Insets(0, 0, 5, 5);
		gbc_年级.gridx = 2;
		gbc_年级.gridy = 7;
		年级.setFont(new Font("新宋体", Font.PLAIN, 14));
		add(年级, gbc_年级);

		GridBagConstraints gbc_教学年级 = new GridBagConstraints();
		gbc_教学年级.anchor = GridBagConstraints.WEST;
		gbc_教学年级.insets = new Insets(0, 0, 5, 5);
		gbc_教学年级.gridx = 3;
		gbc_教学年级.gridy = 7;
		教学年级.setFont(new Font("新宋体", Font.PLAIN, 14));
		教学年级.setEnabled(false);
		教学年级.setModel(new DefaultComboBoxModel<String>(new String[] { "小学", "初中", "高中" }));
		add(教学年级, gbc_教学年级);

		GridBagConstraints gbc_label_10 = new GridBagConstraints();
		gbc_label_10.anchor = GridBagConstraints.WEST;
		gbc_label_10.insets = new Insets(0, 0, 5, 5);
		gbc_label_10.gridx = 0;
		gbc_label_10.gridy = 8;
		label_10.setFont(new Font("新宋体", Font.PLAIN, 14));
		add(label_10, gbc_label_10);

		GridBagConstraints gbc_个人介绍编辑保存 = new GridBagConstraints();
		gbc_个人介绍编辑保存.anchor = GridBagConstraints.WEST;
		gbc_个人介绍编辑保存.insets = new Insets(0, 0, 5, 0);
		gbc_个人介绍编辑保存.gridx = 4;
		gbc_个人介绍编辑保存.gridy = 8;
		个人介绍编辑保存.setFont(new Font("新宋体", Font.PLAIN, 14));
		个人介绍编辑保存.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (个人介绍编辑保存.getText().equals("编辑")) {
					个人介绍编辑保存.setText("保存");
					兴趣爱好.setEditable(true);
					教学经验.setEditable(true);
					教学心得.setEditable(true);
					获奖经历.setEditable(true);
					教学风格.setEditable(true);

				} else {
					个人介绍编辑保存.setText("编辑");
					兴趣爱好.setEditable(false);
					教学经验.setEditable(false);
					教学心得.setEditable(false);
					获奖经历.setEditable(false);
					教学风格.setEditable(false);
					个人信息更新();
					刷新();
				}
			}

			private void 个人信息更新() {
				// TODO Auto-generated method stub
				try (Connection con = DatabaseUtil.getConnection()) {
					String sql = "UPDATE 教师 SET 兴趣爱好 = ?,教学经验 = ?,教学心得 = ?,获奖经历 = ?,教学风格 = ? WHERE Id = ?";
					PreparedStatement pstmt = con.prepareStatement(sql);
					pstmt.setString(1, 兴趣爱好.getText());
					pstmt.setString(2, 教学经验.getText());
					pstmt.setString(3, 教学心得.getText());
					pstmt.setString(4, 获奖经历.getText());
					pstmt.setString(5, 教学风格.getText());
					pstmt.setInt(6, father.getId());
					pstmt.executeUpdate();
					pstmt.close();
					con.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});
		add(个人介绍编辑保存, gbc_个人介绍编辑保存);

		GridBagConstraints gbc_label_11 = new GridBagConstraints();
		gbc_label_11.anchor = GridBagConstraints.WEST;
		gbc_label_11.insets = new Insets(0, 0, 5, 5);
		gbc_label_11.gridx = 0;
		gbc_label_11.gridy = 9;
		label_11.setFont(new Font("新宋体", Font.PLAIN, 14));
		add(label_11, gbc_label_11);

		GridBagConstraints gbc_兴趣爱好 = new GridBagConstraints();
		gbc_兴趣爱好.anchor = GridBagConstraints.WEST;
		gbc_兴趣爱好.gridwidth = 3;
		gbc_兴趣爱好.insets = new Insets(0, 0, 5, 5);
		gbc_兴趣爱好.gridx = 1;
		gbc_兴趣爱好.gridy = 9;
		add(兴趣爱好, gbc_兴趣爱好);

		GridBagConstraints gbc_label_12 = new GridBagConstraints();
		gbc_label_12.anchor = GridBagConstraints.WEST;
		gbc_label_12.insets = new Insets(0, 0, 5, 5);
		gbc_label_12.gridx = 0;
		gbc_label_12.gridy = 10;
		label_12.setFont(new Font("新宋体", Font.PLAIN, 14));
		add(label_12, gbc_label_12);

		GridBagConstraints gbc_教学经验 = new GridBagConstraints();
		gbc_教学经验.anchor = GridBagConstraints.WEST;
		gbc_教学经验.gridwidth = 3;
		gbc_教学经验.insets = new Insets(0, 0, 5, 5);
		gbc_教学经验.gridx = 1;
		gbc_教学经验.gridy = 10;
		教学经验.setFont(new Font("新宋体", Font.PLAIN, 14));
		教学经验.setEditable(false);
		教学经验.setColumns(100);
		add(教学经验, gbc_教学经验);

		GridBagConstraints gbc_label_13 = new GridBagConstraints();
		gbc_label_13.anchor = GridBagConstraints.WEST;
		gbc_label_13.insets = new Insets(0, 0, 5, 5);
		gbc_label_13.gridx = 0;
		gbc_label_13.gridy = 11;
		label_13.setFont(new Font("新宋体", Font.PLAIN, 14));
		add(label_13, gbc_label_13);

		GridBagConstraints gbc_获奖经历 = new GridBagConstraints();
		gbc_获奖经历.anchor = GridBagConstraints.WEST;
		gbc_获奖经历.gridwidth = 3;
		gbc_获奖经历.insets = new Insets(0, 0, 5, 5);
		gbc_获奖经历.gridx = 1;
		gbc_获奖经历.gridy = 11;
		获奖经历.setFont(new Font("新宋体", Font.PLAIN, 14));
		获奖经历.setEditable(false);
		获奖经历.setColumns(100);
		add(获奖经历, gbc_获奖经历);

		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 12;
		lblNewLabel_1.setFont(new Font("新宋体", Font.PLAIN, 14));
		add(lblNewLabel_1, gbc_lblNewLabel_1);

		GridBagConstraints gbc_教学心得 = new GridBagConstraints();
		gbc_教学心得.anchor = GridBagConstraints.WEST;
		gbc_教学心得.gridwidth = 3;
		gbc_教学心得.insets = new Insets(0, 0, 5, 5);
		gbc_教学心得.gridx = 1;
		gbc_教学心得.gridy = 12;
		教学心得.setFont(new Font("新宋体", Font.PLAIN, 14));
		教学心得.setEditable(false);
		教学心得.setColumns(100);
		add(教学心得, gbc_教学心得);

		GridBagConstraints gbc_label_14 = new GridBagConstraints();
		gbc_label_14.anchor = GridBagConstraints.WEST;
		gbc_label_14.insets = new Insets(0, 0, 5, 5);
		gbc_label_14.gridx = 0;
		gbc_label_14.gridy = 13;
		label_14.setFont(new Font("新宋体", Font.PLAIN, 14));
		add(label_14, gbc_label_14);

		GridBagConstraints gbc_教学风格 = new GridBagConstraints();
		gbc_教学风格.anchor = GridBagConstraints.WEST;
		gbc_教学风格.gridwidth = 3;
		gbc_教学风格.insets = new Insets(0, 0, 5, 5);
		gbc_教学风格.gridx = 1;
		gbc_教学风格.gridy = 13;
		教学风格.setFont(new Font("新宋体", Font.PLAIN, 14));
		教学风格.setEditable(false);
		教学风格.setColumns(100);
		add(教学风格, gbc_教学风格);

		GridBagConstraints gbc_label_15 = new GridBagConstraints();
		gbc_label_15.anchor = GridBagConstraints.WEST;
		gbc_label_15.insets = new Insets(0, 0, 5, 5);
		gbc_label_15.gridx = 0;
		gbc_label_15.gridy = 14;
		label_15.setFont(new Font("新宋体", Font.PLAIN, 14));
		add(label_15, gbc_label_15);

		GridBagConstraints gbc_薪资支付编辑保存 = new GridBagConstraints();
		gbc_薪资支付编辑保存.anchor = GridBagConstraints.WEST;
		gbc_薪资支付编辑保存.insets = new Insets(0, 0, 5, 0);
		gbc_薪资支付编辑保存.gridx = 4;
		gbc_薪资支付编辑保存.gridy = 14;
		薪资支付编辑保存.setFont(new Font("新宋体", Font.PLAIN, 14));
		薪资支付编辑保存.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (薪资支付编辑保存.getText().equals("编辑")) {
					薪资支付编辑保存.setText("保存");
					证件类型.setEditable(true);
					证件号.setEditable(true);
					支付宝.setEditable(true);

				} else {
					薪资支付编辑保存.setText("编辑");
					证件类型.setEditable(false);
					证件号.setEditable(false);
					支付宝.setEditable(false);
					个人信息更新();
					刷新();
				}
			}

			private void 个人信息更新() {
				// TODO Auto-generated method stub
				try (Connection con = DatabaseUtil.getConnection()) {
					String sql = "UPDATE 教师 SET 证件类型 = ?,证件号 = ?,支付宝= ? WHERE Id = ?";
					PreparedStatement pstmt = con.prepareStatement(sql);
					pstmt.setString(1, 证件类型.getText());
					pstmt.setString(2, 证件号.getText());
					pstmt.setString(3, 支付宝.getText());
					pstmt.setInt(4, father.getId());
					pstmt.executeUpdate();
					pstmt.close();
					con.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});
		add(薪资支付编辑保存, gbc_薪资支付编辑保存);

		GridBagConstraints gbc_label_19 = new GridBagConstraints();
		gbc_label_19.anchor = GridBagConstraints.WEST;
		gbc_label_19.insets = new Insets(0, 0, 5, 5);
		gbc_label_19.gridx = 0;
		gbc_label_19.gridy = 15;
		label_19.setFont(new Font("新宋体", Font.PLAIN, 14));
		add(label_19, gbc_label_19);

		GridBagConstraints gbc_等级 = new GridBagConstraints();
		gbc_等级.anchor = GridBagConstraints.WEST;
		gbc_等级.insets = new Insets(0, 0, 5, 5);
		gbc_等级.gridx = 1;
		gbc_等级.gridy = 15;
		等级.setFont(new Font("新宋体", Font.PLAIN, 14));
		add(等级, gbc_等级);

		GridBagConstraints gbc_label_20 = new GridBagConstraints();
		gbc_label_20.anchor = GridBagConstraints.WEST;
		gbc_label_20.insets = new Insets(0, 0, 5, 5);
		gbc_label_20.gridx = 2;
		gbc_label_20.gridy = 15;
		label_20.setFont(new Font("新宋体", Font.PLAIN, 14));
		add(label_20, gbc_label_20);

		GridBagConstraints gbc_时薪 = new GridBagConstraints();
		gbc_时薪.anchor = GridBagConstraints.WEST;
		gbc_时薪.insets = new Insets(0, 0, 5, 5);
		gbc_时薪.gridx = 3;
		gbc_时薪.gridy = 15;
		时薪.setFont(new Font("新宋体", Font.PLAIN, 14));
		add(时薪, gbc_时薪);

		GridBagConstraints gbc_label_16 = new GridBagConstraints();
		gbc_label_16.anchor = GridBagConstraints.WEST;
		gbc_label_16.insets = new Insets(0, 0, 5, 5);
		gbc_label_16.gridx = 0;
		gbc_label_16.gridy = 16;
		label_16.setFont(new Font("新宋体", Font.PLAIN, 14));
		add(label_16, gbc_label_16);

		GridBagConstraints gbc_证件类型 = new GridBagConstraints();
		gbc_证件类型.anchor = GridBagConstraints.WEST;
		gbc_证件类型.insets = new Insets(0, 0, 5, 5);
		gbc_证件类型.gridx = 1;
		gbc_证件类型.gridy = 16;
		add(证件类型, gbc_证件类型);

		GridBagConstraints gbc_label_17 = new GridBagConstraints();
		gbc_label_17.anchor = GridBagConstraints.WEST;
		gbc_label_17.insets = new Insets(0, 0, 5, 5);
		gbc_label_17.gridx = 2;
		gbc_label_17.gridy = 16;
		label_17.setFont(new Font("新宋体", Font.PLAIN, 14));
		add(label_17, gbc_label_17);

		GridBagConstraints gbc_证件号 = new GridBagConstraints();
		gbc_证件号.anchor = GridBagConstraints.WEST;
		gbc_证件号.insets = new Insets(0, 0, 5, 5);
		gbc_证件号.gridx = 3;
		gbc_证件号.gridy = 16;
		add(证件号, gbc_证件号);

		GridBagConstraints gbc_label_18 = new GridBagConstraints();
		gbc_label_18.anchor = GridBagConstraints.WEST;
		gbc_label_18.insets = new Insets(0, 0, 0, 5);
		gbc_label_18.gridx = 0;
		gbc_label_18.gridy = 17;
		label_18.setFont(new Font("新宋体", Font.PLAIN, 14));
		add(label_18, gbc_label_18);

		GridBagConstraints gbc_支付宝 = new GridBagConstraints();
		gbc_支付宝.anchor = GridBagConstraints.WEST;
		gbc_支付宝.insets = new Insets(0, 0, 0, 5);
		gbc_支付宝.gridx = 1;
		gbc_支付宝.gridy = 17;
		add(支付宝, gbc_支付宝);
	}
}
