package Tools.DrawTool;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;

/**
 * @author 不识不知 wx:hbhdlhd96
 */
public class Draw implements Serializable {
    protected static final long serialVersionUID = 1L;

    private Point p1, p2;// 两个定位点
    private Color color = Color.BLACK;// 颜色
    private float width = 5;// 线条粗细
    private long time = 0;// 时间

    private DrawingMode mode = DrawingMode.Pencil;// 模式

    public Draw() {
        super();
    }

    public Draw(DrawingMode mode) {
        super();
        this.mode = mode;
    }

    public Point getP1() {
        return p1;
    }

    public void setP1(Point p1) {
        this.p1 = p1;
    }

    public Point getP2() {
        return p2;
    }

    public void setP2(Point p2) {
        this.p2 = p2;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public DrawingMode getMode() {
        return mode;
    }

    public void setMode(DrawingMode mode) {
        this.mode = mode;
    }

    public void draw(Graphics2D g2d) {
        switch (mode) {
            case HollowRectangle:
                g2d.setPaint(color);
                g2d.setStroke(new BasicStroke(width));
                g2d.drawRect(Math.min(p1.x, p2.x), Math.min(p1.y, p2.y), Math.abs(p1.x - p2.x), Math.abs(p1.y - p2.y));
                break;
            case Pencil:
                g2d.setPaint(color);
                g2d.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
                g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
                break;
            case SolidRectangle:
                g2d.setPaint(color);
                g2d.setStroke(new BasicStroke(width));
                g2d.fillRect(Math.min(p1.x, p2.x), Math.min(p1.y, p2.y), Math.abs(p1.x - p2.x), Math.abs(p1.y - p2.y));
                break;
            case Circle:
                g2d.setPaint(color);
                g2d.setStroke(new BasicStroke(width));
                g2d.drawOval(Math.min(p1.x, p2.x), Math.min(p1.y, p2.y), Math.abs(p1.x - p2.x), Math.abs(p1.y - p2.y));
                break;
            case line:
                g2d.setPaint(color);
                g2d.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
                g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
                break;
            default:// The default brush is pencil
                g2d.setPaint(color);
                g2d.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
                g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
                break;
        }
    }
}
