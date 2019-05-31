package 公共组件池.其他组件;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author 不识不知 wx:hbhdlhd96
 *
 */
public class 时间Util {

	Date rtn;
	SimpleDateFormat df= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	GregorianCalendar cal= new GregorianCalendar();

	/**
	 * 创建一个当前时间
	 */
	public 时间Util() {
		this.rtn = new Date();
		cal.setTime(rtn);
	}

	public GregorianCalendar getCalendar() {
		return cal;
	}

	public static boolean 是否A早于B(String A, String B) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date bt, et;
		try {
			bt = sdf.parse(A);
			et = sdf.parse(B);
			if (bt.before(et)) {
				return true;
			} else {
				return false;
			}
		} catch (ParseException ex) {
			Logger.getLogger(时间Util.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	public 时间Util(String 时间) {
		try {
			this.rtn = df.parse(时间);
			cal.setTime(rtn);
		} catch (ParseException ex) {
			Logger.getLogger(时间Util.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public String 得到当前时间() {
		return df.format(cal.getTime());
	}

	public String 加年(int i) {
		cal.add(1, i);
		return df.format(cal.getTime());
	}

	public String 加月(int i) {
		cal.add(2, i);
		return df.format(cal.getTime());
	}

	public String 加周(int i) {
		cal.add(3, i);
		return df.format(cal.getTime());
	}

	public String 加日(int i) {
		cal.add(5, i);
		return df.format(cal.getTime());
	}

	public String 加小时(int i) {
		cal.add(10, i);
		return df.format(cal.getTime());
	}

	public String 加分钟(int i) {
		cal.add(12, i);
		return df.format(cal.getTime());
	}

	public String 加秒(int i) {
		cal.add(13, i);
		return df.format(cal.getTime());
	}
	public String 得到年月() {
		return df.format(cal.getTime()).substring(0,7);
	}
}
