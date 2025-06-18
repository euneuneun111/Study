package quiz;

import java.util.Scanner;

public class Quiz_UpDown {
	public static void main(String[] args) {
		int input = 0;
		int comNum = (int) (Math.random() * 100 + 1);

		int MAX = 100;
		int MIN = 1;
		int count = 0;

		final String INTRO = "��ǻ�ʹ� ���ڸ� �����߽��ϴ�.\n�����ϰڽ��ϴ�.\n";

		Scanner scan = new Scanner(System.in);

		System.out.println("******** ���ٿ� ���� ********\n\n");
		System.out.println(INTRO);

		boolean flagMenu1 = true;
		while (flagMenu1) {
			System.out.println(MIN + "���� " + MAX + "������ ���ڸ� �Է��ϼ���.\n");

			input = Integer.parseInt(scan.nextLine().trim());
			count++;
			if (input != comNum) {
				if (input >= MIN && input < comNum) {
					System.out.println("��!!!");
					MIN = input + 1;
				} else if (input <= MAX && input > comNum) {
					System.out.println("�ٿ�!!!");
					MAX = input - 1;
				} else {
					System.out.println("�Է��� �ùٸ��� �ʽ��ϴ�.");
				}
				continue;
			}

			System.out.println("�����մϴ�. �����Դϴ�.");
			System.out.println(count + "��° ������ϴ�.");

			input = 0;
			comNum = (int) (Math.random() * 100 + 1);
			MAX = 100;
			MIN = 1;
			count = 0;

			boolean flagMenu2 = true;
			while (flagMenu2) {
				System.out.print("\n\n������ �����Ͻðڽ��ϱ�? (y/n)\n");
				switch (scan.nextLine().charAt(0)) {
				case 'Y':
				case 'y':
					System.out.println("������ �����մϴ�.");
					flagMenu1 = false;
					flagMenu2 = false;
					
					break;
				case 'N':
				case 'n':
					System.out.println("������ ��� �����մϴ�.\n\n");
					flagMenu1 = true;
					flagMenu2 = false;
					
					System.out.println(INTRO);
					break;
				default:
					System.out.println("�޴������� �߸� �Ǿ����ϴ�");
				}
			}
		}
	}
}