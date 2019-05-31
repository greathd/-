package 公共组件池.绘画组件;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import pool.常量池;
import 公共组件池.其他组件.PictureUtil;

/**
 * utility 形容词,有多种用途的,各种工作都会做的. Util为其简写
 *
 * @author 不识不知 wx:hbhdlhd96
 * @version 创建时间：2019年1月1日 下午8:01:20
 */
public class doc2BufferedImageUtil {

    public static ArrayList<BufferedImage> pdf2BufferedImages(File pdfFile, 授课板 page) {
        //将作为结果返回
        ArrayList<BufferedImage> images = new ArrayList<>();
        //定义在外面是为了方便在finally语句块中关闭
        PDDocument document = null;
        try {
            long start = System.currentTimeMillis();
            document = PDDocument.load(pdfFile);
            if (document == null) {
                return null;
            }
            int size = document.getNumberOfPages();
            for (int i = 0; i < size; i++) {
                BufferedImage img = new PDFRenderer(document).renderImageWithDPI(i, 130, ImageType.RGB);
                images.add(img);
                page.增加一页(PictureUtil.zoomImage(img, 常量池.默认画板宽, 常量池.默认画板高));
            }
            long end = System.currentTimeMillis();
            System.out.println("解析成功耗时：" + (end - start) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (document != null) {
                    document.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return images;
    }

    /**
     * *.ppt的文件转BufferdImage
     *
     * @param file
     * @return
     */
    public static BufferedImage[] ppt2BufferedImages(File file, 授课板 page) {

        try {
            FileInputStream in = new FileInputStream(file);
            HSLFSlideShow ppt = new HSLFSlideShow(in);
            Dimension pageSize = ppt.getPageSize();
            List<HSLFSlide> pages = ppt.getSlides();
            BufferedImage[] images = new BufferedImage[pages.size()];
            for (int i = 0; i < ppt.getSlides().size(); i++) {
                images[i] = new BufferedImage(pageSize.width, pageSize.height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D graphics = images[i].createGraphics();
                graphics.setPaint(Color.WHITE);
                graphics.fill(new Rectangle2D.Float(0, 0, pageSize.width, pageSize.height));
                pages.get(i).draw(graphics);
                graphics.dispose();
                page.增加一页(images[i]);
//                page.增加一页(PictureUtil.zoomImage(images[i], 常量池.默认画板宽, 常量池.默认画板高));
            }
            ppt.close();
            in.close();
            return images;
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * *.pptx的文件转BufferedImage
     *
     * @param file
     * @return
     */
    public static BufferedImage[] pptx2BufferedImages(File file, 授课板 page) {
        try {
            FileInputStream in = new FileInputStream(file);
            XMLSlideShow pptx = new XMLSlideShow(in);
            Dimension pageSize = pptx.getPageSize();
            List<XSLFSlide> pages = pptx.getSlides();
            BufferedImage[] images = new BufferedImage[pages.size()];
            for (int i = 0; i < pages.size(); i++) {
                images[i] = new BufferedImage(pageSize.width, pageSize.height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D graphics = images[i].createGraphics();
                graphics.setPaint(Color.WHITE);
                graphics.fill(new Rectangle2D.Float(0, 0, pageSize.width, pageSize.height));
                pages.get(i).draw(graphics);
                graphics.dispose();
//                page.增加一页(PictureUtil.zoomImage(images[i], 常量池.默认画板宽, 常量池.默认画板高));
                page.增加一页(images[i]);
            }

            pptx.close();
            in.close();
            return images;
        } catch (IOException e) {
            return null;
        }
    }

}
