package com.bsbz.cloudclass.Clinet;

import com.bsbz.cloudclass.Tools.AudioTool.Audio发送器;
import com.bsbz.cloudclass.Tools.AudioTool.Audio接收器;
import com.bsbz.cloudclass.Tools.CommunicationTool.Message;
import com.bsbz.cloudclass.Tools.CommunicationTool.SocketProcessor;
import com.bsbz.cloudclass.Tools.CommunicationTool.SocketUser;
import com.bsbz.cloudclass.Tools.DrawTool.Draw;
import com.bsbz.cloudclass.Tools.DrawTool.DrawingMode;
import com.bsbz.cloudclass.Tools.DrawTool.总界面_画板接口;
import com.bsbz.cloudclass.Tools.DrawTool.授课板;
import com.bsbz.cloudclass.Tools.Other.FileFilter;
import com.bsbz.cloudclass.Tools.Other.PictureUtil;
import com.bsbz.cloudclass.Tools.Other.doc2BufferedImageUtil;
import com.bsbz.cloudclass.Tools.VideoTool.VideoInterface;
import com.bsbz.cloudclass.Tools.VideoTool.Video发送线程;
import com.bsbz.cloudclass.Tools.VideoTool.Video接收线程;
import com.bsbz.cloudclass.Tools.常量池;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


import java.awt.Font;
import java.awt.FlowLayout;

/**
 * @author 不识不知 wx:hbhdlhd96
 * @version 创建时间：2019年1月3日 下午3:42:01
 */
public class TeachingPad implements 总界面_画板接口, VideoInterface, SocketUser {
    /**
     * GUI 变量
     */
    public JFrame frame;
    private final JPanel 顶部 = new JPanel();
    private final JPanel 左部 = new JPanel();
    private final JPanel 右部 = new JPanel();
    private final JLabel 时钟标签 = new JLabel("00:00:00");
    private final JTabbedPane 课件组 = new JTabbedPane(JTabbedPane.TOP);
    private final 授课板 黑板 = new 授课板("黑板", this);
    private final JLabel 我 = new JLabel("我的信息");
    private final JLabel 我的照片 = new JLabel("");
    private final JLabel 对方 = new JLabel("对方信息");
    private final JLabel 对方照片 = new JLabel("");
    private final JButton 添加课件按钮 = new JButton("添加课件");
    private final JButton 铅笔工具按钮 = new JButton("铅笔工具");
    private final JButton 实心矩形按钮 = new JButton("实心矩形");
    private final JButton 空心矩形按钮 = new JButton("空心矩形");
    private final JButton 直线工具按钮 = new JButton("直线工具");
    private final JButton 圆形工具按钮 = new JButton("圆形工具");
    private final JButton 清空墨迹按钮 = new JButton("清空墨迹");
    private final JButton 红色按钮 = new JButton("");
    private final JButton 黄色按钮 = new JButton("");
    private final JButton 橙色按钮 = new JButton("");
    private final JButton 绿色按钮 = new JButton("");
    private final JButton 蓝色按钮 = new JButton("");
    private final JButton 黑色按钮 = new JButton("");
    private final JButton 白色按钮 = new JButton("");
    private final JButton 紫色按钮 = new JButton("");
    private final JButton 粉色按钮 = new JButton("");
    private final JButton 暂时离开 = new JButton("暂时离开");
    private final JScrollPane 消息显示区 = new JScrollPane();
    private final JList<String> 消息列表 = new JList<String>();
    private final DefaultListModel<String> 消息组 = new DefaultListModel<>();
    private final JPanel 输入面版 = new JPanel();
    private final JTextField 消息输入框 = new JTextField();
    private final JButton 消息发送按钮 = new JButton("发送");

    /**
     * 绘画状态变量
     */
    public String 身份;
    private String name;
    private DrawingMode drawMode = DrawingMode.Pencil;
    private Color color = Color.RED;
    private long time = 0;
    private float brushThickness = 5;
    SocketProcessor drawSocketProcessor = null;
    /**
     * 课件状态变量
     */
    private HashMap<String, 授课板> 课件池Map = new HashMap<String, 授课板>(); // 课件池
    private String docname = "黑板"; // 当前被选中的课件名
    /**
     * 运行状态变量
     */
    public SocketProcessor server;
    String begintime;
    Clinet clinet;
    String 对方IP;

    Timer timer = new Timer();
    private final JButton 结束课堂 = new JButton("结束课堂");
    private final JButton 选框删除按钮 = new JButton("选框删除");

    public TeachingPad(SocketProcessor server, String 身份, String name, String cur_begintime, Clinet clinet) {
        System.out.println("我是 " + 身份);
        this.server = server;
        server.setSocketUser(this);
        this.身份 = 身份;
        this.name = name;
        this.begintime = cur_begintime;
        this.clinet = clinet;
        initialize();
        黑板.设置调用者(this);
        黑板.增加一页();
        课件组.addTab("黑板", null, 黑板, null);
        课件池Map.put("黑板", 黑板);
        try {
            Message msg = new Message();
            msg.set身份(身份);
            msg.set描述("ready");
            msg.set内容(name);
            server.sendMsg(msg);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        消息输入框.setColumns(10);
        frame = new JFrame();
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(顶部, BorderLayout.NORTH);
        顶部.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        时钟标签.setFont(new Font("等线", Font.BOLD, 20));

        顶部.add(时钟标签);
        结束课堂.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                结束课堂();
            }
        });
        结束课堂.setForeground(Color.BLACK);
        结束课堂.setFont(new Font("楷体", Font.BOLD, 16));

        顶部.add(结束课堂);

        frame.getContentPane().add(左部, BorderLayout.WEST);
        GridBagLayout gbl_左部 = new GridBagLayout();
        gbl_左部.columnWidths = new int[]{0, 0, 0, 0};
        gbl_左部.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_左部.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_左部.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                Double.MIN_VALUE};
        左部.setLayout(gbl_左部);

        GridBagConstraints gbc_添加课件按钮 = new GridBagConstraints();
        gbc_添加课件按钮.gridwidth = 3;
        gbc_添加课件按钮.insets = new Insets(0, 0, 5, 0);
        gbc_添加课件按钮.gridx = 0;
        gbc_添加课件按钮.gridy = 0;
        添加课件按钮.setFont(new Font("楷体", Font.BOLD, 16));
        添加课件按钮.setForeground(new Color(0, 0, 0));
        添加课件按钮.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                添加课件();
            }
        });
        左部.add(添加课件按钮, gbc_添加课件按钮);

        GridBagConstraints gbc_铅笔工具按钮 = new GridBagConstraints();
        gbc_铅笔工具按钮.gridwidth = 3;
        gbc_铅笔工具按钮.insets = new Insets(0, 0, 5, 0);
        gbc_铅笔工具按钮.gridx = 0;
        gbc_铅笔工具按钮.gridy = 1;
        铅笔工具按钮.setFont(new Font("楷体", Font.BOLD, 16));
        铅笔工具按钮.setForeground(new Color(0, 0, 0));
        铅笔工具按钮.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawMode = DrawingMode.Pencil;
            }
        });
        左部.add(铅笔工具按钮, gbc_铅笔工具按钮);

        GridBagConstraints gbc_实心矩形按钮 = new GridBagConstraints();
        gbc_实心矩形按钮.gridwidth = 3;
        gbc_实心矩形按钮.insets = new Insets(0, 0, 5, 0);
        gbc_实心矩形按钮.gridx = 0;
        gbc_实心矩形按钮.gridy = 2;
        实心矩形按钮.setFont(new Font("楷体", Font.BOLD, 16));
        实心矩形按钮.setForeground(new Color(0, 0, 0));
        实心矩形按钮.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawMode = DrawingMode.SolidRectangle;
            }
        });
        左部.add(实心矩形按钮, gbc_实心矩形按钮);

        GridBagConstraints gbc_空心矩形按钮 = new GridBagConstraints();
        gbc_空心矩形按钮.gridwidth = 3;
        gbc_空心矩形按钮.insets = new Insets(0, 0, 5, 0);
        gbc_空心矩形按钮.gridx = 0;
        gbc_空心矩形按钮.gridy = 3;
        空心矩形按钮.setFont(new Font("楷体", Font.BOLD, 16));
        空心矩形按钮.setForeground(new Color(0, 0, 0));
        空心矩形按钮.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawMode = DrawingMode.HollowRectangle;
            }
        });
        左部.add(空心矩形按钮, gbc_空心矩形按钮);

        GridBagConstraints gbc_直线工具按钮 = new GridBagConstraints();
        gbc_直线工具按钮.gridwidth = 3;
        gbc_直线工具按钮.insets = new Insets(0, 0, 5, 0);
        gbc_直线工具按钮.gridx = 0;
        gbc_直线工具按钮.gridy = 4;
        直线工具按钮.setFont(new Font("楷体", Font.BOLD, 16));
        直线工具按钮.setForeground(new Color(0, 0, 0));
        直线工具按钮.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawMode = DrawingMode.line;
            }
        });
        左部.add(直线工具按钮, gbc_直线工具按钮);

        GridBagConstraints gbc_圆形工具按钮 = new GridBagConstraints();
        gbc_圆形工具按钮.gridwidth = 3;
        gbc_圆形工具按钮.insets = new Insets(0, 0, 5, 0);
        gbc_圆形工具按钮.gridx = 0;
        gbc_圆形工具按钮.gridy = 5;
        圆形工具按钮.setFont(new Font("楷体", Font.BOLD, 16));
        圆形工具按钮.setForeground(new Color(0, 0, 0));
        圆形工具按钮.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawMode = DrawingMode.Circle;
            }
        });
        左部.add(圆形工具按钮, gbc_圆形工具按钮);

        GridBagConstraints gbc_清空墨迹按钮 = new GridBagConstraints();
        gbc_清空墨迹按钮.gridwidth = 3;
        gbc_清空墨迹按钮.insets = new Insets(0, 0, 5, 0);
        gbc_清空墨迹按钮.gridx = 0;
        gbc_清空墨迹按钮.gridy = 7;
        清空墨迹按钮.setFont(new Font("楷体", Font.BOLD, 16));
        清空墨迹按钮.setForeground(new Color(0, 0, 0));
        清空墨迹按钮.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                全部清空();
            }
        });

        GridBagConstraints gbc_选框删除按钮 = new GridBagConstraints();
        gbc_选框删除按钮.gridwidth = 3;
        gbc_选框删除按钮.insets = new Insets(0, 0, 5, 0);
        gbc_选框删除按钮.gridx = 0;
        gbc_选框删除按钮.gridy = 6;
        选框删除按钮.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawMode = DrawingMode.MarqueeDeletion;
            }
        });
        选框删除按钮.setFont(new Font("楷体", Font.BOLD, 16));
        左部.add(选框删除按钮, gbc_选框删除按钮);
        左部.add(清空墨迹按钮, gbc_清空墨迹按钮);

        GridBagConstraints gbc_红色按钮 = new GridBagConstraints();
        gbc_红色按钮.insets = new Insets(0, 0, 5, 5);
        gbc_红色按钮.gridx = 0;
        gbc_红色按钮.gridy = 9;
        红色按钮.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                color = 红色按钮.getBackground();
            }
        });
        GridBagConstraints gbc_暂时离开 = new GridBagConstraints();
        gbc_暂时离开.gridwidth = 3;
        gbc_暂时离开.insets = new Insets(0, 0, 5, 0);
        gbc_暂时离开.gridx = 0;
        gbc_暂时离开.gridy = 8;
        暂时离开.setForeground(new Color(0, 0, 0));
        左部.add(暂时离开, gbc_暂时离开);
        暂时离开.setFont(new Font("楷体", Font.BOLD, 16));
        暂时离开.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                暂离();
            }
        });
        红色按钮.setPreferredSize(new Dimension(30, 20));
        红色按钮.setBackground(Color.RED);
        红色按钮.setForeground(Color.BLACK);
        左部.add(红色按钮, gbc_红色按钮);

        GridBagConstraints gbc_黄色按钮 = new GridBagConstraints();
        gbc_黄色按钮.insets = new Insets(0, 0, 5, 5);
        gbc_黄色按钮.gridx = 1;
        gbc_黄色按钮.gridy = 9;
        黄色按钮.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                color = 黄色按钮.getBackground();
            }
        });
        黄色按钮.setPreferredSize(new Dimension(30, 20));
        黄色按钮.setBackground(Color.YELLOW);
        黄色按钮.setForeground(Color.BLACK);
        左部.add(黄色按钮, gbc_黄色按钮);

        GridBagConstraints gbc_橙色按钮 = new GridBagConstraints();
        gbc_橙色按钮.insets = new Insets(0, 0, 5, 0);
        gbc_橙色按钮.gridx = 2;
        gbc_橙色按钮.gridy = 9;
        橙色按钮.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                color = 橙色按钮.getBackground();
            }
        });
        橙色按钮.setPreferredSize(new Dimension(30, 20));
        橙色按钮.setForeground(Color.BLACK);
        橙色按钮.setBackground(Color.ORANGE);
        左部.add(橙色按钮, gbc_橙色按钮);

        GridBagConstraints gbc_绿色按钮 = new GridBagConstraints();
        gbc_绿色按钮.insets = new Insets(0, 0, 5, 5);
        gbc_绿色按钮.gridx = 0;
        gbc_绿色按钮.gridy = 10;
        绿色按钮.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                color = 绿色按钮.getBackground();
            }
        });
        绿色按钮.setPreferredSize(new Dimension(30, 20));
        绿色按钮.setBackground(Color.GREEN);
        绿色按钮.setForeground(Color.BLACK);
        左部.add(绿色按钮, gbc_绿色按钮);

        GridBagConstraints gbc_蓝色按钮 = new GridBagConstraints();
        gbc_蓝色按钮.insets = new Insets(0, 0, 5, 5);
        gbc_蓝色按钮.gridx = 1;
        gbc_蓝色按钮.gridy = 10;
        蓝色按钮.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                color = 蓝色按钮.getBackground();
            }
        });
        蓝色按钮.setPreferredSize(new Dimension(30, 20));
        蓝色按钮.setBackground(Color.BLUE);
        蓝色按钮.setForeground(Color.BLACK);
        左部.add(蓝色按钮, gbc_蓝色按钮);

        GridBagConstraints gbc_黑色按钮 = new GridBagConstraints();
        gbc_黑色按钮.insets = new Insets(0, 0, 5, 0);
        gbc_黑色按钮.gridx = 2;
        gbc_黑色按钮.gridy = 10;
        黑色按钮.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                color = 黑色按钮.getBackground();
            }
        });
        黑色按钮.setPreferredSize(new Dimension(30, 20));
        黑色按钮.setBackground(Color.BLACK);
        黑色按钮.setForeground(Color.BLACK);
        左部.add(黑色按钮, gbc_黑色按钮);

        GridBagConstraints gbc_白色按钮 = new GridBagConstraints();
        gbc_白色按钮.insets = new Insets(0, 0, 5, 5);
        gbc_白色按钮.gridx = 0;
        gbc_白色按钮.gridy = 11;
        白色按钮.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                color = 白色按钮.getBackground();
            }
        });
        白色按钮.setPreferredSize(new Dimension(30, 20));
        白色按钮.setBackground(Color.WHITE);
        白色按钮.setForeground(Color.BLACK);
        左部.add(白色按钮, gbc_白色按钮);

        GridBagConstraints gbc_紫色按钮 = new GridBagConstraints();
        gbc_紫色按钮.insets = new Insets(0, 0, 5, 5);
        gbc_紫色按钮.gridx = 1;
        gbc_紫色按钮.gridy = 11;
        紫色按钮.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                color = 紫色按钮.getBackground();
            }
        });
        紫色按钮.setPreferredSize(new Dimension(30, 20));
        紫色按钮.setBackground(Color.MAGENTA);
        紫色按钮.setForeground(Color.BLACK);
        左部.add(紫色按钮, gbc_紫色按钮);

        GridBagConstraints gbc_粉色按钮 = new GridBagConstraints();
        gbc_粉色按钮.insets = new Insets(0, 0, 5, 0);
        gbc_粉色按钮.gridx = 2;
        gbc_粉色按钮.gridy = 11;
        粉色按钮.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                color = 粉色按钮.getBackground();
            }
        });
        粉色按钮.setPreferredSize(new Dimension(30, 20));
        粉色按钮.setBackground(Color.PINK);
        粉色按钮.setForeground(Color.BLACK);
        左部.add(粉色按钮, gbc_粉色按钮);

        frame.getContentPane().add(右部, BorderLayout.EAST);
        GridBagLayout gbl_右部 = new GridBagLayout();
        gbl_右部.columnWidths = new int[]{66, 0};
        gbl_右部.rowHeights = new int[]{15, 0, 0, 0, 0, 0, 0};
        gbl_右部.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_右部.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        右部.setLayout(gbl_右部);

        GridBagConstraints gbc_我 = new GridBagConstraints();
        gbc_我.insets = new Insets(0, 0, 5, 0);
        gbc_我.gridx = 0;
        gbc_我.gridy = 0;
        右部.add(我, gbc_我);

        GridBagConstraints gbc_我的照片 = new GridBagConstraints();
        gbc_我的照片.insets = new Insets(0, 0, 5, 0);
        gbc_我的照片.gridx = 0;
        gbc_我的照片.gridy = 1;
//        我的照片.setIcon(new ImageIcon(this.getClass().getResource("/").getPath().substring(1) + "picture/关闭.png"));
        右部.add(我的照片, gbc_我的照片);

        GridBagConstraints gbc_对方 = new GridBagConstraints();
        gbc_对方.insets = new Insets(0, 0, 5, 0);
        gbc_对方.gridx = 0;
        gbc_对方.gridy = 2;
        右部.add(对方, gbc_对方);

        GridBagConstraints gbc_对方照片 = new GridBagConstraints();
        gbc_对方照片.insets = new Insets(0, 0, 5, 0);
        gbc_对方照片.gridx = 0;
        gbc_对方照片.gridy = 3;
//		对方照片.setIcon(new ImageIcon(TeachingPad.class.getResource("/图片/关闭.png")));
        右部.add(对方照片, gbc_对方照片);

        // TODO xiaoxi
        消息列表.setFont(new java.awt.Font("黑体", 1, 18));
        消息列表.setModel(消息组);
        消息显示区.setViewportView(消息列表);

        GridBagConstraints gbc_消息记录显示区 = new GridBagConstraints();
        gbc_消息记录显示区.insets = new Insets(0, 0, 5, 0);
        gbc_消息记录显示区.gridx = 0;
        gbc_消息记录显示区.gridy = 4;
        右部.add(消息显示区, gbc_消息记录显示区);

        GridBagConstraints gbc_输入面版 = new GridBagConstraints();
        gbc_输入面版.fill = GridBagConstraints.BOTH;
        gbc_输入面版.gridx = 0;
        gbc_输入面版.gridy = 5;
        右部.add(输入面版, gbc_输入面版);
        输入面版.setLayout(new BorderLayout(0, 0));

        输入面版.add(消息输入框);
        消息发送按钮.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                新增消息(消息输入框.getText());
                消息输入框.setText("");
            }
        });

        输入面版.add(消息发送按钮, BorderLayout.EAST);
        课件组.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                docname = 课件组.getTitleAt(课件组.getSelectedIndex());
                Message message = new Message();
                message.setDrawtype("切换课件");
                message.set内容(docname);
                sendDrawMessage(message);
            }
        });

        frame.getContentPane().add(课件组, BorderLayout.CENTER);

        timer.schedule(new TimerTask() {
            //			long midTime = 0;
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            @Override
            public void run() {
//				midTime++;
//				int hh = (int) (midTime / 60 / 60 % 60);
//				int mm = (int) (midTime / 60 % 60);
//				int ss = (int) (midTime % 60);
//				System.out.println("计时器：" + hh + "小时" + mm + "分钟" + ss + "秒");
                时钟标签.setText(ft.format(new Date()));
            }
        }, 0, 1000);
    }

    protected void 新增消息(String 消息) {
        // BUG 自动换行、界面尺寸问题
        try {
            Message message = new Message();
            message.set身份(身份);
            message.set描述("消息");
            message.set内容(消息);
            server.sendMsg(message);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void 退出() {
        // TODO 退出课堂
        if (drawSocketProcessor != null)
            drawSocketProcessor.close();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.exit(0);
    }

    protected void 结束课堂() {
        // TODO Auto-generated method stub
        if (身份.equals("teacher")) {
            Message message = new Message();
            message.set身份(身份);
            message.set描述("下课");
            try {
                server.sendMsg(message);
                drawSocketProcessor.sendMsg(message);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            退出();
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "只有教师才可以结束课堂！");
        }
    }

    protected void 暂离() {
        Message message = new Message();
        message.set身份(身份);
        message.set描述("退出");
        try {
            server.sendMsg(message);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        退出();
    }

    protected void 全部清空() {
        // TODO 全部清空
        Message message = new Message();
        message.setDrawtype("清空");
        message.set内容(docname);
        sendDrawMessage(message);
        课件池Map.get(docname).清空();
    }

    protected void 添加课件() {
        // TODO 添加课件
        FileFilter filter = new FileFilter();
        filter.addExtension("pdf");
        filter.addExtension("ppt");
        filter.addExtension("pptx");
        filter.setDescription("*.pdf;*.ppt;*.pptx文件");
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(filter);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            new Thread(() -> {
                // 文件转图片
                File file = chooser.getSelectedFile();// 获取文件
                String 文件名 = file.getName();// 文件名
                String type = 文件名.substring(文件名.lastIndexOf('.') + 1);// 文件类型
                System.out.println("正在处理 " + 文件名);
                // 新建课件
                授课板 课件 = new 授课板(文件名, this);
                // 加入课件索引
                课件池Map.put(文件名, 课件);
                // 加入授课区
                课件组.addTab(文件名, null, 课件, null);

                Message message = new Message();
                message.set内容(文件名);
                message.setDrawtype("新增课件");
                sendDrawMessage(message);

                message.set身份(身份);
                message.set描述("draw");
                // 通知服务器
                try {
                    server.sendMsg(message);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // 填充课件内容
                switch (type) {
                    case "pdf":
                        doc2BufferedImageUtil.pdf2BufferedImages(file, 课件);
                        break;
                    case "ppt":
                        doc2BufferedImageUtil.ppt2BufferedImages(file, 课件);
                        break;
                    case "pptx":
                        doc2BufferedImageUtil.pptx2BufferedImages(file, 课件);
                    default:
                        break;
                }
            }, "课件转码").start();
        }
    }

    @Override
    public Color 获取颜色() {
        return color;
    }

    @Override
    public long 读取计时器() {
        return time;
    }

    @Override
    public float 获取画刷粗细() {
        return brushThickness;
    }

    @Override
    public DrawingMode 获取绘画模式() {
        return drawMode;
    }

    @Override
    public void 我方(BufferedImage image) {
        // TODO Auto-generated method stub
        image = PictureUtil.zoomImage(image, 常量池.默认摄像头宽, 常量池.默认摄像头高);
        我的照片.setIcon(new ImageIcon(image));
        对方照片.setIcon(new ImageIcon(image));
    }

    @Override
    public void 对方(BufferedImage image) {
        // TODO Auto-generated method stub
        image = PictureUtil.zoomImage(image, 常量池.默认摄像头宽, 常量池.默认摄像头高);
        我的照片.setIcon(new ImageIcon(image));
        对方照片.setIcon(new ImageIcon(image));
    }

    @Override
    public void acceptit(SocketProcessor socketProcessor, Message msg) {
        // TODO Auto-generated method stub
        String 身份1 = msg.get身份();
        String 内容1 = msg.get内容();
        String 描述1 = msg.get描述();
        switch (描述1) {
            case "下课":
                退出();
                break;
            case "ready":
                对方IP = 内容1;
                switch (身份) {
                    case "teacher":
                        我.setText("teacher " + name);
                        对方.setText("student " + msg.get附加());
                        break;
                    case "student":
                        我.setText("student " + name);
                        对方.setText("teacher " + msg.get附加());
                        break;
                    default:
                        break;
                }
                建立连接();
                break;
            case "消息":
                SwingUtilities.invokeLater(() -> {
                    消息组.add(0, 身份1 + ":" + 内容1);
                });
                break;
            case "draw":
                授课板 temp = 课件池Map.get(内容1);
                String drawtype = msg.getDrawtype();
                switch (drawtype) {
                    case "切换课件":
                        课件组.setSelectedIndex(课件组.indexOfTab(内容1));
                        break;
                    case "上一页":
                        temp.上一页();
                        break;
                    case "下一页":
                        temp.下一页();
                        break;
                    case "清空":
                        temp.清空();
                        break;
                    case "draw":
                        Draw draw = msg.getDraw();
                        if (draw.getMode() == DrawingMode.MarqueeDeletion)
                            temp.选框删除(draw.getP1(), draw.getP2());
                        else {
                            temp.addDraw(draw);
                        }

                        break;
                    case "新增课件":
                        新增课件(内容1);
                        break;
                    case "填充页面":
                        try {
                            temp.addpage(PictureUtil.Decoding(msg.getImg()));
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    private void 新增课件(String 内容1) {
        // TODO Auto-generated method stub
        // 新建课件
        授课板 课件 = new 授课板(内容1, this);
        // 加入课件索引
        课件池Map.put(内容1, 课件);
        // 加入授课区
        课件组.addTab(内容1, null, 课件, null);
    }

    public void sendDrawMessage(Message msg) {
        if (drawSocketProcessor != null) {
            try {
                msg.set身份(身份);
                msg.set描述("draw");
                drawSocketProcessor.sendMsg(msg);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void 建立连接() {
        // TODO Auto-generated method stub
        /**
         * 启动视频组件
         */
        switch (身份) {
            case "teacher":
                new Video接收线程(常量池.getVideoPort(), this).启动();
                break;
            case "student":
                try {
                    new Video发送线程(对方IP, 常量池.getVideoPort(), this).启动();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        /**
         * 启动音频组件
         */
        switch (身份) {
            case "teacher":
                Audio发送器 sender = new Audio发送器(对方IP, 常量池.getAudioPort());
                break;
            case "student":
                Audio接收器 receiver = new Audio接收器(常量池.getAudioPort());
                break;
        }
        /**
         * 开启绘画连接
         */
        try {
            switch (身份) {
                case "teacher":// 接收方
                    ServerSocket serverSocket;
                    serverSocket = new ServerSocket(常量池.getDrawPort());
                    drawSocketProcessor = new SocketProcessor(serverSocket.accept(), this);
                    break;
                case "student":// 连接方
                    drawSocketProcessor = new SocketProcessor(对方IP, 常量池.getDrawPort(), this);
                default:
                    break;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 申请恢复现场
        Message msg = new Message();
        msg.set身份(身份);
        msg.set描述("恢复现场");
        try {
            server.sendMsg(msg);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}