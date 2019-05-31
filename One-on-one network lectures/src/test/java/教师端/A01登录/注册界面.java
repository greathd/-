package 教师端.A01登录;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author 不识不知 wx:hbhdlhd96
 * @version 创建时间：2019年3月19日 下午6:02:13
 */
public class 注册界面 extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private final JTextField textField = new JTextField();
	private final JLabel label_1 = new JLabel("性别");
	private final JComboBox comboBox = new JComboBox();
	private final JLabel label_2 = new JLabel("生日");
	private final JTextField textField_1 = new JTextField();
	private final JLabel label_3 = new JLabel("本科学校");
	private final JTextField textField_2 = new JTextField();
	private final JLabel label_4 = new JLabel("科目");
	private final JComboBox comboBox_1 = new JComboBox();
	private final JLabel label_5 = new JLabel("手机号");
	private final JTextField textField_3 = new JTextField();
	private final JLabel label_6 = new JLabel("高考分数");
	private final JTextField textField_4 = new JTextField();
	private final JLabel label_7 = new JLabel("满分");
	private final JTextField textField_5 = new JTextField();
	private final JLabel label_8 = new JLabel("本科专业");
	private final JTextField textField_6 = new JTextField();
	private final JLabel label_9 = new JLabel("教学经验");
	private final JTextField textField_7 = new JTextField();
	private final JTextField textField_8 = new JTextField();
	private final JLabel label_10 = new JLabel("兴趣爱好");

	/**
	 * Create the dialog.
	 */
	public 注册界面() {
		textField_8.setText("篮球");
		textField_8.setBounds(257, 80, 66, 21);
		textField_8.setColumns(10);
		textField_7.setText("2年");
		textField_7.setBounds(257, 42, 66, 21);
		textField_7.setColumns(10);
		textField_6.setText("计算机科学与技术");
		textField_6.setBounds(257, 117, 107, 21);
		textField_6.setColumns(10);
		textField_5.setText("150");
		textField_5.setBounds(327, 152, 34, 21);
		textField_5.setColumns(10);
		textField_4.setText("136");
		textField_4.setBounds(257, 152, 34, 21);
		textField_4.setColumns(10);
		textField_3.setText("15634394249");
		textField_3.setBounds(257, 7, 107, 21);
		textField_3.setColumns(10);
		textField_2.setText("哈工大");
		textField_2.setBounds(73, 117, 66, 21);
		textField_2.setColumns(10);
		textField_1.setText("19961020");
		textField_1.setBounds(73, 80, 66, 21);
		textField_1.setColumns(10);
		textField.setText("张三");
		textField.setBounds(73, 7, 66, 21);
		textField.setColumns(10);
		initGUI();
	}

	private void initGUI() {
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		contentPanel.setBounds(416, 0, 20, 230);
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 230, 436, 33);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		{
			JLabel label = new JLabel("姓名");
			label.setBounds(10, 10, 54, 15);
			getContentPane().add(label);
		}

		getContentPane().add(textField);
		label_1.setBounds(10, 45, 54, 15);

		getContentPane().add(label_1);
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "男", "女" }));
		comboBox.setToolTipText("");
		comboBox.setBounds(73, 41, 66, 23);

		getContentPane().add(comboBox);
		{
			label_2.setBounds(10, 83, 54, 15);
			getContentPane().add(label_2);
		}
		{
			getContentPane().add(textField_1);
		}
		{
			label_3.setBounds(10, 120, 54, 15);
			getContentPane().add(label_3);
		}
		{
			getContentPane().add(textField_2);
		}
		{
			label_4.setBounds(10, 155, 54, 15);
			getContentPane().add(label_4);
		}
		{
			comboBox_1.setModel(new DefaultComboBoxModel(new String[] { "数学", "英语", "物理", "化学", "生物" }));
			comboBox_1.setToolTipText("");
			comboBox_1.setBounds(73, 151, 66, 23);
			getContentPane().add(comboBox_1);
		}
		{
			label_5.setBounds(198, 10, 54, 15);
			getContentPane().add(label_5);
		}
		{
			getContentPane().add(textField_3);
		}
		{
			label_6.setBounds(198, 155, 54, 15);
			getContentPane().add(label_6);
		}
		{
			getContentPane().add(textField_4);
		}
		{
			label_7.setBounds(297, 155, 34, 15);
			getContentPane().add(label_7);
		}
		{
			getContentPane().add(textField_5);
		}
		{
			label_8.setBounds(198, 120, 54, 15);
			getContentPane().add(label_8);
		}
		{
			getContentPane().add(textField_6);
		}
		{
			label_9.setBounds(198, 45, 54, 15);
			getContentPane().add(label_9);
		}
		{
			getContentPane().add(textField_7);
		}
		{
			getContentPane().add(textField_8);
		}
		{
			label_10.setBounds(198, 83, 54, 15);
			getContentPane().add(label_10);
		}
	}
}
