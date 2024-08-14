package day06;

public class User {

	private String name;
	private PayType payType;
	
	public User(String name, PayType payType) {
		
		this.name = name;
		this.payType = payType;
		
	}
	
	public void setname(String name) {
        this.name = name;
    }

    public String getname() {
        return name;
    }

    
    public void setpayType(PayType payType) {
        this.payType = payType;
    }

    public PayType getpayType() {
        return payType;
    }
	
}
