package Tools;

/**
 * @author 不识不知 wx:hbhdlhd96
 */

public class 常量池 {
    public static String 默认IP = "localhost";

    public static int 广播台端口号 = 9999;
    public static int 服务器通信端口 = 9998;

    public static int 绘画端口号 = 9997;
    public static int 音频端口号 = 9996;
    public static int 视频端口号 = 9995;

    public static int 默认画板宽 = 916;
    public static int 默认画板高 = 620;

    public static int 默认摄像头宽 = 176;
    public static int 默认摄像头高 = 144;

    public static int getDrawPort() {
        // TODO Auto-generated method stub
        return 绘画端口号;
    }

    public static int getVideoPort() {
        return 视频端口号;
    }

    public static int getAudioPort() {
        return 音频端口号;
    }

    public static int get时薪(String 等级) {
        int result = 0;
        switch (等级) {
            case "A1":
                result = 100;
                break;
            case "A2":
                result = 90;
                break;
            case "A3":
                result = 80;
                break;
            case "B1":
                result = 75;
                break;
            case "B2":
                result = 70;
                break;
            case "B3":
                result = 65;
                break;
            case "C1":
                result = 60;
                break;
            case "C2":
                result = 55;
                break;
            case "C3":
                result = 50;
                break;
            default:
                result = 0;
                break;
        }
        return result;
    }
}
