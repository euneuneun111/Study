package day06;

class Account {
	int balance;

	// 입금 메서드
	public void deposit(int money) {
		balance += money;
	}

	// 출금 메서드
	public void withdraw(int money) throws InsufficientException {
		if (balance < money) {
			throw new InsufficientException("잔고 부족: " + (money - balance) + " 모자람");
		}
		balance -= money;
	}

	// 잔고 반환 메서드
	public int getBalance() {
		return balance;
	}
}

// 사용자 정의 예외 클래스
class InsufficientException extends Exception {
	public InsufficientException(String message) {
		super(message);
	}
}

public class AccountExample {
	public static void main(String[] args) {
		Account account = new Account();

		account.deposit(10000); // 입금
		System.out.println("예금액: " + account.getBalance());

		try {
			account.withdraw(30000); // 출금 시도
		} catch (InsufficientException e) {
			String message = e.getMessage();
			System.out.println(message);
		}
	}
}
