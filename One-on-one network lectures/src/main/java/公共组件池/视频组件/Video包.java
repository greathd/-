package 公共组件池.视频组件;

import java.io.Serializable;

/**
*
* @author 不识不知 wx:hbhdlhd96
* @version 创建时间：2019年4月14日 下午4:54:47
*/
public class Video包 implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6128380503626629191L;
	private byte image[] = null;
	
	public Video包() {
		super();
	}
	public void setImage(byte[] image){
		this.image = image;
	}
	public byte[] getImage() {
		return image;
	}
}