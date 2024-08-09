package day04;
import java.util.ArrayList;
import java.util.Scanner;

public class Problem1 {
/*
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner scan = new Scanner(System.in);
		
		boolean run = true;
		int select;
		ArrayList<Account> arrList = new ArrayList<>();
		
		while(true) {
		System.out.println("---------------------------------------------------------");
		System.out.println("1. 계좌 생성 | 2. 계좌 목록 | 3. 예금 | 4. 출금 | 5. 종료");
		System.out.println("---------------------------------------------------------");

		System.out.print("선택 > ");
		
		select = scan.nextInt();
		
		if (select == 1) {
			Acc.inputPerson()
		} else if (select == 2) {
			Account.Showinfo();
		} else if (select == 3) {
			
		} else if (select == 4) {
			
		} else if (select == 5) {
			System.out.println("프로그램 종료");
			System.exit(0);
		} else  {
			System.out.println("유효하지 않은 값입니다.");
		}
		}
		
		
		

	}

}

class Account {
	
	String AccountNumber;
	String ClientName;
	int Money;
	
	public Account(String AccountNumber, String ClientName, int Money) {
		
		   this.AccountNumber = AccountNumber;
	        this.ClientName = ClientName;
	        this.Money = Money;
		
	}
	
	void Showinfo() {
       System.out.println("계좌 번호 : " + AccountNumber + "\n" + "계좌 주: " + ClientName + "\n" + "초기 입금액: " + Money); 
      
    }
	
	 public void inputPerson() {
		Scanner scan = new Scanner(System.in);
		Problem1 a1 = new Problem1();
		
		System.out.print("계좌 번호 : ");
		String Anm = scan.next();
		a1.AccountNumber = Anm;
		 
		System.out.print("\n" + "계좌 주 : ");
		String Cnm = scan.next();
		a1.ClientName = Cnm;
		
		System.out.print("\n" + "초기 입금액 : ");
		int Am = scan.nextInt();
		a1.Money = Am;
		
		System.out.print("\n" + "계좌가 생성되었습니다.");


		
		
		
	 }
	 */
	
	  public static void main(String[] args) {
	        Scanner scan = new Scanner(System.in);
	        ArrayList<Account> arrList = new ArrayList<>();

	        while (true) {
	            System.out.println("---------------------------------------------------------");
	            System.out.println("1. 계좌 생성 | 2. 계좌 목록 | 3. 예금 | 4. 출금 | 5. 종료");
	            System.out.println("---------------------------------------------------------");

	            System.out.print("선택 > ");
	            int select = scan.nextInt();

	            if (select == 1) {
	                Account newAccount = Account.inputPerson(); // 계좌 생성
	                if (newAccount != null) {
	                    arrList.add(newAccount);
	                    System.out.println("계좌가 생성되었습니다.");
	                }
	            } else if (select == 2) {
	                for (Account account : arrList) {
	                    account.Showinfo();
	                    System.out.println("---------------------------------------------------------");
	                }
	            } else if (select == 3) {
	                System.out.print("계좌 번호 입력: ");
	                String accNum = scan.next();
	                Account acc = findAccount(arrList, accNum);
	                if (acc != null) {
	                    System.out.print("예금액 입력: ");
	                    int amount = scan.nextInt();
	                    acc.deposit(amount);
	                    System.out.println("예금이 완료되었습니다.");
	                } else {
	                    System.out.println("해당 계좌를 찾을 수 없습니다.");
	                }
	            } else if (select == 4) {
	                System.out.print("계좌 번호 입력: ");
	                String accNum = scan.next();
	                Account acc = findAccount(arrList, accNum);
	                if (acc != null) {
	                    System.out.print("출금액 입력: ");
	                    int amount = scan.nextInt();
	                    if (acc.withdraw(amount)) {
	                        System.out.println("출금이 완료되었습니다.");
	                    } else {
	                        System.out.println("출금 실패: 잔고 부족.");
	                    }
	                } else {
	                    System.out.println("해당 계좌를 찾을 수 없습니다.");
	                }
	            } else if (select == 5) {
	                System.out.println("프로그램 종료");
	                scan.close();
	                System.exit(0);
	            } else {
	                System.out.println("유효하지 않은 값입니다.");
	            }
	        }
	    }

	    private static Account findAccount(ArrayList<Account> arrList, String accountNumber) {
	        for (Account account : arrList) {
	            if (account.getAccountNumber().equals(accountNumber)) {
	                return account;
	            }
	        }
	        return null;
	    }
	}

	class Account {
	    private String accountNumber;
	    private String clientName;
	    private int money;

	    public Account(String accountNumber, String clientName, int money) {
	        this.accountNumber = accountNumber;
	        this.clientName = clientName;
	        this.money = money;
	    }

	    public void Showinfo() {
	        System.out.println("계좌 번호 : " + accountNumber);
	        System.out.println("계좌 주 : " + clientName);
	        System.out.println("잔고 : " + money);
	    }

	    public static Account inputPerson() {
	        Scanner scan = new Scanner(System.in);

	        System.out.print("계좌 번호 : ");
	        String accountNumber = scan.next();

	        System.out.print("계좌 주 : ");
	        String clientName = scan.next();

	        System.out.print("초기 입금액 : ");
	        int money = scan.nextInt();

	        if (money < 0) {
	            System.out.println("초기 입금액은 음수가 될 수 없습니다.");
	            return null;
	        }

	        return new Account(accountNumber, clientName, money);
	    }

	    public void deposit(int amount) {
	        if (amount > 0) {
	            money += amount;
	        } else {
	            System.out.println("예금액은 양수여야 합니다.");
	        }
	    }

	    public boolean withdraw(int amount) {
	        if (amount > 0 && amount <= money) {
	            money -= amount;
	            return true;
	        } else {
	            return false;
	        }
	    }

	    public String getAccountNumber() {
	        return accountNumber;
	    }
}
