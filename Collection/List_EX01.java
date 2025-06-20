package Collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class List_EX01 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		List list = new ArrayList();

		list.add(1);
		list.add("1");
		list.add('1');
		list.add(true);
		list.add(null);
		list.add(1.0);
		list.add(1f);

		System.out.println(list.size());
		System.out.println(list.get(4));
		
		/* 정수값이 아닌 것 add 불가
		List<Integer> list1 = new ArrayList();

		list1.add(1);
		list1.add("1");
		list1.add('1');
		list1.add(true);
		list1.add(null);
		list1.add(1.0);
		list1.add(1f);

		System.out.println(list1.size());
		System.out.println(list1.get(4));
		*/

		
		
		List<Integer> list2 = new ArrayList();

		list2.add(1);
		list2.add(2);
		list2.add(3);
		list2.add(4);
		
		// list2.remove(4); // 해당 요소 삭제
		// list.clear(); // 전체 요소 삭제

		/*
		System.out.println(list2.size());
		System.out.println(list2.get(3));
		
		Iterator<Integer> it = list2.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
		*/
		
		System.out.println(list2.contains(1));
	}

}
