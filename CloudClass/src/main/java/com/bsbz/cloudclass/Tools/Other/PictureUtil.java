package com.bsbz.cloudclass.Tools.Other;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 * 网络传输中 图片的编码与解码函數
 *
 * @author 不识不知 wx:hbhdlhd96
 * @version 创建时间：2018年12月30日 下午11:48:49 类说明
 */
public class PictureUtil {

    /**
     * 图片缩放
     *
     * @param src
     * @param dis_width
     * @param dis_height
     * @return
     */


    public static BufferedImage zoomImage(BufferedImage src, int dis_width, int dis_height) {
        double scale = Math.min(1.0 * dis_width / src.getWidth(), 1.0 * dis_height / src.getHeight());
        int width = (int) (src.getWidth() * scale);
        int height = (int) (src.getHeight() * scale);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(src.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
        return bufferedImage;
    }

    /**
     * 图片裁剪
     *
     * @param Img
     * @param p1  点1
     * @param p2  点2
     * @return 裁剪后的图片
     */
    public static BufferedImage subImage(BufferedImage Img, Point p1, Point p2) {
        int x = Math.min(p1.x, p2.x);
        int y = Math.min(p1.y, p2.y);
        int w = Math.abs(p2.x - p1.x);
        int h = Math.abs(p2.y - p1.y);
        System.out.println(x + " " + y + " " + w + " " + h);
        return Img.getSubimage(x, y, w, h);
    }

    /**
     * 获取截屏
     *
     * @return 截屏
     */
    public static BufferedImage snapShot() {
        try {
            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
            BufferedImage screenshot = (new Robot())
                    .createScreenCapture(new Rectangle(0, 0, (int) d.getWidth(), (int) d.getHeight()));
            return screenshot;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 图片编码
     *
     * @param bufferedImage 原图片
     * @return byteImg 可用于序列化的字节数组
     * @throws IOException
     */
    public static byte[] Encoding(BufferedImage bufferedImage) throws IOException {
        if (bufferedImage == null)
            return null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
//		图片格式（从大到小）
//		BMP＞PSD＞HDCPG＞＞PCX＞JPEG＞PNG＞GIF
//		PNG每张图片大约520KB
//		GIF每张图片大约78KB
//		JPG每张图片大约23KB
        ImageIO.write(bufferedImage, "JPG", out);
        byte[] datas = out.toByteArray();
        out.close();
        return datas;
    }

    /**
     * 图片解码
     *
     * @param byteImg 图片的字节数组
     * @return bufferedImage 原图片
     * @throws IOException
     */
    public static BufferedImage Decoding(byte[] byteImg) throws IOException {
        if (byteImg == null)
            return null;
        ByteArrayInputStream bin = new ByteArrayInputStream(byteImg);
        BufferedImage image = ImageIO.read(bin);
        bin.close();
        return image;
    }
}
