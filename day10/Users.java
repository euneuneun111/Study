package day10;

public class Users {

	private String id;
	private String password;

	public Users(String id, String password) {
		this.id = id;
		this.password = password;

	}

	public Users(String id) {
		this.id = id;

	}

	public String getid() {
		return id;
	}

	public String getpassword() {
		return password;
	}

	public void setid(String id) {
		this.id = id;
	}

	public void setpassword(String password) {
		this.password = password;
	}

}
