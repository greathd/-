package Tools.DrawTool;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import Tools.CommunicationTool.Message;
import Tools.Other.PictureUtil;
import Clinet.TeachingPad;

/**
 * 画板类
 *
 * @author 不识不知 wx:hbhdlhd96
 */
public class 授课板 extends JPanel {
    /**
     * 控制变量
     */
    protected TeachingPad father;
    protected boolean 可用 = false;
    /**
     * 存储变量
     */
    protected int 总页码 = 1;
    protected int 当前页码 = 1;
    protected ArrayList<ArrayList<Draw>> 页面池 = new ArrayList<>();
    protected String 名字;
    protected ArrayList<BufferedImage> 背景 = new ArrayList<>();
    /**
     * 运行变量
     */
    private Draw draw = null;// 当前正在设置的draw
    private ArrayList<Draw> draws = new ArrayList<Draw>(); // 当前绘画池
    private final JPanel 底部区 = new JPanel();
    private final JButton 上一页按钮 = new JButton("上一页");
    private final JLabel 当前页码标签 = new JLabel("1");
    private final JLabel 斜杠线 = new JLabel("/");
    private final JLabel 总页码标签 = new JLabel("1");
    private final JButton 下一页按钮 = new JButton("下一页");
    private final JPanel 中心区 = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            // TODO Auto-generated method stub
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            if (页面池.size() != 0) {
                可用 = true;
            } else {
                可用 = false;
            }
            if (可用) {
                draws = 页面池.get(当前页码 - 1);
                // 画背景图
                if (背景.size() > 0) {
                    g2d.drawImage(背景.get(当前页码 - 1), 0, 0, this);
                }
                // 画操作
                int size = draws.size();
                for (int i = 0; i < size; i++) {
                    draws.get(i).draw(g2d);
                }
            } else {
                String text = "加载中，请稍后！";
                int width = this.getWidth();
                int height = this.getHeight();
                // 画图
                g2d.clearRect(0, 0, width, height);
                Font font = new Font("宋体", Font.PLAIN, 64);
                g2d.setFont(font);
                // 抗锯齿
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // 计算文字长度，计算居中的x点坐标
                FontMetrics fm = g2d.getFontMetrics(font);
                int textWidth = fm.stringWidth(text);
                int widthX = (width - textWidth) / 2;
                // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
                g2d.drawString(text, widthX, height / 2);
            }
        }
    };

    private void send(Message message) {
        message.set内容(名字);
        father.sendDrawMessage(message);
    }

    /**
     * @param 名字
     */
    public 授课板(String 名字, TeachingPad 调用者) {
        this.名字 = 名字;
        this.father = 调用者;
        initGUI();
    }

    /**
     * 设置调用者
     *
     * @param 调用者
     */
    public void 设置调用者(TeachingPad 调用者) {
        this.father = 调用者;
    }

    private void initGUI() {
        setLayout(new BorderLayout(0, 0));

        add(底部区, BorderLayout.SOUTH);
        上一页按钮.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lastPage();
            }
        });

        底部区.add(上一页按钮);

        底部区.add(当前页码标签);

        底部区.add(斜杠线);

        底部区.add(总页码标签);
        下一页按钮.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nextPage();
            }
        });

        底部区.add(下一页按钮);

        中心区.setBackground(Color.WHITE);
        中心区.setBorder(new LineBorder(Color.GRAY, 5, true));
        // 添加鼠标事件
        中心区.addMouseListener(new MouseA());
        中心区.addMouseMotionListener(new MouseB());
        add(中心区, BorderLayout.CENTER);
    }

    /**
     * 新建一个绘画单元
     *
     * @param mode
     * @return
     */
    private Draw newDraw(DrawingMode mode) {
        Draw newDraw = new Draw();
        newDraw.setColor(father.获取颜色());
        newDraw.setMode(mode);
        newDraw.setTime(father.读取计时器());
        newDraw.setWidth(father.获取画刷粗细());
        return newDraw;
    }

    /**
     * @param 新页面
     */
    public void 增加一页(BufferedImage 新页面) {
        addpage(新页面);
        Message message = new Message();
        message.setDrawtype("填充页面");
        message.set内容(名字);
        try {
            message.setImg(PictureUtil.Encoding(新页面));
            father.sendDrawMessage(message);
            // 通知服务器
            message.set身份(father.身份);
            message.set描述("draw");
            father.server.sendMsg(message);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void addpage(BufferedImage 新页面) {
        背景.add(新页面);
        页面池.add(new ArrayList<Draw>());
        总页码 = 页面池.size();
        刷新页码区();
    }

    /**
     *
     */
    public void 增加一页() {
        页面池.add(new ArrayList<Draw>());
        总页码 = 页面池.size();
        刷新页码区();
    }

    /**
     * 获取总页数
     *
     * @return
     */
    public int getTotalPageNumber() {
        return 总页码;
    }

    /**
     * 获取当前页码
     *
     * @return
     */
    public int getCurPageNumber() {
        return 当前页码;
    }

    /**
     * 上一页
     */
    public void 上一页() {
        if (可用) {
            if (当前页码 > 1)
                当前页码 -= 1;
            刷新页码区();
            repaint();
        }
    }

    private void lastPage() {
        上一页();
        Message message = new Message();
        message.setDrawtype("上一页");
        send(message);
    }

    /**
     * 下一页
     */
    public void 下一页() {
        if (可用) {
            if (当前页码 < 总页码) {
                当前页码 += 1;
            } else if (当前页码 == 总页码 && 背景.size() == 0) {
                当前页码 += 1;
                页面池.add(new ArrayList<Draw>());
                总页码 = 页面池.size();
            }
            刷新页码区();
            repaint();
        }
    }

    private void nextPage() {
        下一页();
        Message message = new Message();
        message.setDrawtype("下一页");
        send(message);
    }

    /**
     * 清空当前画布
     */
    public void 清空() {
        if (可用) {
            draws.clear();
            repaint();
        }
    }

    public void addDraw(Draw draw) {
        draws.add(draw);
        repaint();
    }

    private void sendDraw(Draw draw) {
        Message message = new Message();
        message.setDrawtype("draw");
        message.setDraw(draw);
        send(message);
    }

    /**
     * 鼠标事件MouseA类继承了MouseAdapter 用来完成鼠标的响应事件的操作（鼠标的按下、释放)
     *
     * @author 不识不知 wx:hbhdlhd96
     */
    protected class MouseA extends MouseAdapter {

        @Override
        /**
         * 鼠标按下
         */
        public void mousePressed(MouseEvent e) {
            Point p = new Point(e.getX(), e.getY());
            switch (father.获取绘画模式()) {
                case MarqueeDeletion:// 选框删除
                    draw = newDraw(DrawingMode.HollowRectangle);
                    draw.setColor(Color.GRAY);
                    break;
                case HollowRectangle:// 空心矩形
                case SolidRectangle:// 实心矩形
                case Circle:// 圆形
                case line:// 直线
                case Pencil:// 铅笔
                    draw = newDraw(father.获取绘画模式());// 新建
                    break;
            }
            draw.setP1(p);// 设置第一个点
            draw.setP2(p);// 设置第一个点
            draws.add(draw);
            repaint();
        }

        @Override
        /**
         * 鼠标松开
         */
        public void mouseReleased(MouseEvent me) {
            Point p = new Point(me.getX(), me.getY());
            switch (father.获取绘画模式()) {
                case MarqueeDeletion:
                    if (draw != null) {
                        draw.setP2(p);
                        draw.setMode(DrawingMode.MarqueeDeletion);
                        // 完成构建 组成操作
                        sendDraw(draw);
                        // 实施选框删除
                        draws.remove(draw);
                        选框删除(draw.getP1(), draw.getP2());
                    }
                    break;
                case Circle:
                case HollowRectangle:
                case SolidRectangle:
                case line:
                case Pencil:
                    if (draw != null) {
                        draw.setP2(p);
                        // 完成构建 组成操作
                        sendDraw(draw);
                    }
                    break;
                default:
                    break;
            }
            repaint();
        }
    }

    /**
     * 鼠标事件MouseB继承了MouseMotionAdapter 用来处理鼠标的滚动与拖动
     *
     * @author 不识不知 wx:hbhdlhd96
     */
    protected class MouseB extends MouseMotionAdapter {
        /**
         * 鼠标拖动
         */
        @Override
        public void mouseDragged(MouseEvent me) {
            Point p = new Point(me.getX(), me.getY());
            if (draw != null) {
                draw.setP2(p);
            }
            if (father.获取绘画模式() == DrawingMode.Pencil) {
                // 上一笔已完成
                sendDraw(draw);
                // 新建下一笔
                draw = newDraw(DrawingMode.Pencil);
                draw.setP1(p);
                draw.setP2(p);
                draws.add(draw);
                // 完成构建 组成操作
            }
            repaint();
        }
    }

    public void 刷新页码区() {
        // TODO Auto-generated method stub
        当前页码标签.setText(当前页码 + "");
        总页码标签.setText(总页码 + "");
        repaint();
    }

    public void 选框删除(Point p1, Point p2) {
        int x1, x3;
        int y1, y3;

        x1 = Math.min(p1.x, p2.x);
        y1 = Math.min(p1.y, p2.y);
        x3 = Math.max(p1.x, p2.x);
        y3 = Math.max(p1.y, p2.y);

        ArrayList<Draw> removeDraws = new ArrayList<Draw>();

        // 找
        for (Draw tempDraw : draws) {
            if (端点是否在矩形内(x1, y1, x3, y3, tempDraw.getP1(), tempDraw.getP2()))
                removeDraws.add(tempDraw);
        }
        // 删
        for (Draw tempDraw : removeDraws) {
            draws.remove(tempDraw);
        }
        repaint();
    }

    private boolean 端点是否在矩形内(int x1, int y1, int x3, int y3, Point p1, Point p2) {
        // TODO Auto-generated method stub
        boolean flag = false;
        // p1
        if (p1.getX() >= x1 && p1.getX() <= x3 && p1.getY() >= y1 && p1.getY() <= y3)
            flag = true;
        // p2
        if (p2.getX() >= x1 && p2.getX() <= x3 && p2.getY() >= y1 && p2.getY() <= y3)
            flag = true;
        return flag;
    }
}