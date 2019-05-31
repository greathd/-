package server;


/**
 *
 * @author 不识不知 wx:hbhdlhd96
 * @version 创建时间：2019年5月15日 下午3:26:32
 */
public class User {
	private String name;
	private String psw;

	public User(String name, String psw) {
		super();
		this.name = name;
		this.psw = psw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPsw() {
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	public boolean Check(User b) {
		if (b.name.equals(name) && b.psw.equals(psw))
			return true;
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj == null)
			return false;
		if (((User) obj).name.equals(name))
			return true;
		return false;
	}
}
