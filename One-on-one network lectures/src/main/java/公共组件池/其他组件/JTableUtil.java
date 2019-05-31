package 公共组件池.其他组件;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * 表的管理工具
 * @author 不识不知 wx:hbhdlhd96
 * @version 创建时间：2019年4月10日 下午12:34:47
 */
public class JTableUtil {
	/**
	 * 奇偶颜色不同 表头不可拖动 表头大小不可更改 内容不可编辑 内容居中
	 * 
	 * @param table
	 */
	public static void A格式(JTable table) {
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				if (row % 2 == 0)
					setBackground(Color.white); // 设置奇数行底色
				else if (row % 2 == 1)
					setBackground(new Color(206, 231, 255)); // 设置偶数行底色
				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			}
		};
		tcr.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, tcr);

		// 设置表头不可拖动
		table.getTableHeader().setReorderingAllowed(false);
		// 设置表头不可更改大小
		table.getTableHeader().setResizingAllowed(false);
		// 让表只能看,不能做其他操作
		table.setEnabled(false);
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
		}
	}
}
