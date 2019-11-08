package com.bsbz.cloudclass.Tools.CommunicationTool;

import com.bsbz.cloudclass.Tools.DrawTool.Draw;

import java.io.Serializable;


/**
 * @author 不识不知 wx:hbhdlhd96
 * @version 创建时间：2018年12月29日 下午11:48:42 类说明 消息包
 */
public class Message implements Serializable {

    private static final long serialVersionUID = 2053785351542599654L;
    private String 身份 = "";
    private String 描述 = "";
    private String 内容 = "";
    private String 附加 = "";
    private byte[] img;

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String get附加() {
        return 附加;
    }

    public void set附加(String 附加) {
        this.附加 = 附加;
    }

    private String drawtype = null;
    private Draw draw = null;

    public String getDrawtype() {
        return drawtype;
    }

    public void setDrawtype(String drawtype) {
        this.drawtype = drawtype;
    }

    public Draw getDraw() {
        return draw;
    }

    public void setDraw(Draw draw) {
        this.draw = draw;
    }

    public String get身份() {
        return 身份;
    }

    public void set身份(String 身份) {
        this.身份 = 身份;
    }

    public String get描述() {
        return 描述;
    }

    public void set描述(String 描述) {
        this.描述 = 描述;
    }

    public String get内容() {
        return 内容;
    }

    public void set内容(String 内容) {
        this.内容 = 内容;
    }
}
