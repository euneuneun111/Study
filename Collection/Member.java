package Collection;

public class Member {

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private String id;
	private String pwd;
	private String name;
	
	public Member(String id, String pwd, String name) {
		this.id = id;
		this.pwd = pwd;
		this.name = name;
	}

	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

	@Override
	public boolean equals(Object obj) { // 전달받은 obj가 실제로 Member 타입인지 검사
		if(obj instanceof Member) {
			Member other = (Member)obj; // 위에서 타입이 맞다고 확인했으니, 안전하게 형변환
			return other.getId().equals(this.id); // 두 객체의 id 값이 같은지를 비교
		}
		return false;
	}
	
	@Override 
	public String toString() {
		return this.id + "("+this.pwd+")";
	}
	

	
}
