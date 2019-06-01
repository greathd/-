package Tools.DrawTool;

import java.awt.Color;

/**
 * @author 不识不知 wx:hbhdlhd96
 * @version 创建时间：2018年12月30日 下午9:41:44 类说明
 */
public interface 总界面_画板接口 {

    DrawingMode 获取绘画模式();

    Color 获取颜色();

    long 读取计时器();

    float 获取画刷粗细();
}
