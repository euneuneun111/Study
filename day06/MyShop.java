package day06;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MyShop implements IShop {

	User[] users = new User[2];
	Product[] products = new Product[4];

	Scanner scan = new Scanner(System.in);

	ArrayList<Product> cart = new ArrayList<Product>();

	int selUser;
	String title = "MyShop"; // title 초기화

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void genUser() {
		users[0] = new User("홍길동", PayType.CARD);
		users[1] = new User("박홍시", PayType.CASH);
	}

	@Override
	public void genProduct() {
		products[0] = new CellPhone("갤럭시 노트", 1000000, "SKT");
		products[1] = new CellPhone("아이폰", 1200000, "애플");
		products[2] = new SmartTV("삼성 스마트 TV", 2000000, "삼성");
		products[3] = new SmartTV("LG 스마트 TV", 1800000, "LG");
	}

	@Override
	public void start() {
		System.out.println(title + " : 메인화면 - 계정 선택");
		System.out.println("========================");
		int i = 0;

		for (User u : users) {
			System.out.printf("[%d] %s (%s)\n", i++, u.getName(), u.getPayType());
		}

		System.out.println("[x] 종료");
		System.out.print("선택: ");
		String sel = scan.next();
		System.out.println("## " + sel + " 선택 ##");

		switch (sel) {
		case "x":
			System.exit(0);
			break;
		default:
			try {
				selUser = Integer.parseInt(sel);
				if (selUser >= 0 && selUser < users.length) {
					productList();
				} else {
					System.out.println("유효하지 않은 선택입니다.");
					start();
				}
			} catch (NumberFormatException e) {
				System.out.println("숫자를 입력해야 합니다.");
				start();
			}
		}
	}

	public void checkOut() {
		System.out.println(title + " : 체크아웃");
		System.out.println("================== ");
		int total = 0;
		int i = 0;

		for (Product p : cart) { // for-each 루프 구문 수정
			System.out.printf("[%d] %s (%d)\n", i++, p.getPname(), p.getPrice());
			total += p.getPrice();
		}

		System.out.println("================== ");
		System.out.println("합계: " + total + "원");
		System.out.println("[p]이전, [q]결제완료");
		String sel = scan.next();

		if (sel.equals("q")) {
			System.out.println("결제가 완료되었습니다.");
			System.exit(0);
		} else {
			productList();
		}
	}

	public void productList() {
		System.out.println(title + " : 상품 목록 - 상품 선택");
		System.out.println("=========================");
		int i = 0;

		for (Product p : products) {
			System.out.print("[" + i + "] ");
			p.printDetail();
			i++;
		}

		System.out.println("[h] 메인화면");
		System.out.println("[c] 체크아웃");
		System.out.print("선택: ");
		String sel = scan.next();
		System.out.println("## " + sel + " 선택 ##");

		switch (sel) {
		case "h":
			start();
			break;
		case "c":
			checkOut();
			break;
		default:
			try {
				int psel = Integer.parseInt(sel);
				if (psel >= 0 && psel < products.length) {
					cart.add(products[psel]);
					productList();
				} else {
					System.out.println("유효하지 않은 선택입니다.");
					productList();
				}
			} catch (NumberFormatException e) {
				System.out.println("숫자를 입력해야 합니다.");
				productList();
			}
		}
	}
}
