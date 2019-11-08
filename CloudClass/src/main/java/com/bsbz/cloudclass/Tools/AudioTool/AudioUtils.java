package com.bsbz.cloudclass.Tools.AudioTool;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

/**
 * 音频捕捉以及发送的程序
 *
 * @author will
 */
public class AudioUtils {
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
    public static int BUFFER_SIZE = 1024;

    private static SourceDataLine sourceDataLine = null;
    private static TargetDataLine targetDataLine = null;

    public static SourceDataLine getSourceDataLine() {
        return sourceDataLine;
    }

    public static TargetDataLine getTargetDataLine() {
        return targetDataLine;
    }

    static {
        /**
         * 采集
         */
        AudioFormat af = new AudioFormat(ENCODING, RATE, SAMPLE_SIZE, CHANNELS, (SAMPLE_SIZE / 8) * CHANNELS,
                RATE, BIG_ENDIAN);
        try {
            targetDataLine = AudioSystem.getTargetDataLine(af);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        try {
            targetDataLine.open();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        targetDataLine.start();
        /**
         * 播放
         */
        Info info = new DataLine.Info(SourceDataLine.class, af);
        try {
            sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        try {
            sourceDataLine.open();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        sourceDataLine.start();
    }


    public static void Record() {
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