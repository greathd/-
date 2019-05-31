package Audio;


import javax.sound.sampled.*;
import javax.sound.sampled.DataLine.Info;

/**
 * Created by 不识不知 on 2019.5.24.
 */
public class Test {
    //采样率
    private static float RATE = 44100f;
    //编码格式PCM
    private static AudioFormat.Encoding ENCODING = AudioFormat.Encoding.PCM_SIGNED;
    //帧大小 16
    private static int SAMPLE_SIZE = 16;
    //是否大端
    private static boolean BIG_ENDIAN = true;
    //通道数
    private static int CHANNELS = 2;
    //缓存区大小
    private static int BUFFER_SIZE = 1024 * 10;

    public static void main(String[] args) throws Exception {
        Record();
    }

    public static void Record() throws Exception {
        /**
         * 采集
         */
        AudioFormat af1 = new AudioFormat(ENCODING, RATE, SAMPLE_SIZE, CHANNELS, (SAMPLE_SIZE / 8) * CHANNELS,
                RATE, BIG_ENDIAN);
        TargetDataLine targetDataLine = AudioSystem.getTargetDataLine(af1);
        targetDataLine.open();
        targetDataLine.start();
        /**
         * 播放
         */
        AudioFormat af2 = new AudioFormat(ENCODING, RATE, SAMPLE_SIZE, CHANNELS, (SAMPLE_SIZE / 8) * CHANNELS,
                RATE, BIG_ENDIAN);
        Info info = new DataLine.Info(SourceDataLine.class, af2);
        SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
        sourceDataLine.open();
        sourceDataLine.start();

        /**
         * 运行
         */
        int len = 0;
        byte[] data = new byte[BUFFER_SIZE];
        while ((len = targetDataLine.read(data, 0, data.length)) > 0) {//从声卡中采集数据
            sourceDataLine.write(data, 0, len);
        }
    }
}
