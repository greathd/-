package Video;

import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by 不识不知 on 2019.5.24.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(640, 480));//配置分辨率
        webcam.open();
        long startTime = System.currentTimeMillis();    //获取开始时间
        int i = 5;
        while (i > 0) {
            BufferedImage img = webcam.getImage();
            ImageIO.write(img,"png",new File(i+".png"));
            i--;
        }
        long endTime = System.currentTimeMillis();    //获取结束时间
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时间
    }
}
