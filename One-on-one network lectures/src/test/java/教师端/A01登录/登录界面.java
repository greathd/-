package 教师端.A01登录;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import 教师端.A02管理.管理系统;
import 数据库.DatabaseUtil;

/**
 *
 * @author 不识不知 wx:hbhdlhd96
 * @version 创建时间：2019年2月7日 下午6:33:27
 */
public class 登录界面 extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2805055538195929357L;
	private final JPanel 信息区 = new JPanel();
	private JTextField 联系电话;
	private JPasswordField 密码;
	private final JLabel label = new JLabel("老师 欢迎登录");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			登录界面 dialog = new 登录界面();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public 登录界面() {
		initGUI();
	}

	private void initGUI() {
		setResizable(false);
		setBounds(100, 100, 333, 244);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		信息区.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(信息区, BorderLayout.CENTER);
		信息区.setLayout(null);
		{
			JLabel lblNewLabel_1 = new JLabel("联系电话");
			lblNewLabel_1.setBounds(10, 61, 87, 15);
			信息区.add(lblNewLabel_1);
		}
		{
			联系电话 = new JTextField();
			联系电话.setBounds(121, 58, 132, 21);
			联系电话.setText("15634394249");
			信息区.add(联系电话);
			联系电话.setColumns(10);
		}
		{
			JLabel lblNewLabel = new JLabel("密码");
			lblNewLabel.setBounds(10, 112, 87, 15);
			信息区.add(lblNewLabel);
		}
		{
			密码 = new JPasswordField();
			密码.setBounds(121, 109, 132, 21);
			密码.setText("123456");
			信息区.add(密码);
		}
		label.setBounds(111, 26, 129, 15);

		信息区.add(label);
		{
			{
				JButton 登录 = new JButton("登录");
				登录.setBounds(140, 174, 87, 23);
				信息区.add(登录);
				登录.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						登录();
					}
				});
				登录.setActionCommand("OK");
				getRootPane().setDefaultButton(登录);
			}
			{
				JButton 退出 = new JButton("退出");
				退出.setBounds(229, 174, 80, 23);
				信息区.add(退出);
				退出.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				});
				退出.setActionCommand("Cancel");
			}
		}
	}

	private void 登录() {
		// TODO Auto-generated method stub
		boolean flag = false;
		int Id = 0;
		try (Connection con = DatabaseUtil.getConnection()) {
			String sql = "SELECT 密码,Id FROM 教师 WHERE 联系电话=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, 联系电话.getText());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				if (String.valueOf(密码.getPassword()).equals(rs.getString(1))) {
					flag = true;// 登录成功
					Id = rs.getInt(2);// 获取教师Id
				}
			}
			rs.close();
			pstmt.close();
			con.close();
			if (flag) {
				dispose();// 关闭登录界面
				new 管理系统(Id).setVisible(true);// 登录成功 进入管理系统
			} else {
				JOptionPane.showMessageDialog(null, "用户名或密码错误！", "登录失败", JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "网络连接失败！", "登录失败", JOptionPane.ERROR_MESSAGE);
		}
	}
}
