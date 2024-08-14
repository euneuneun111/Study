package day06;

public abstract class Product {
	
	private String pname;
	private int price;
	
	public void printDetail() {
		System.out.print("상품명 : "+ pname+" , ");
		System.out.print("가격 : "+ price+" , ");

	}

	public abstract void printExtra();
	
	
}
