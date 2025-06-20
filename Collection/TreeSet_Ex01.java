package Collection;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class TreeSet_Ex01 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Set treeSet = new TreeSet();
		Set hashSet = new HashSet();
		
		Object[] data = {8, 4, 3, 100, 2, 1, 6, 5, 7};
		
		for(int i = 0; i < data.length; i++) {
			treeSet.add(data[i]);
			hashSet.add(data[i]);
		}
		
		System.out.println("treeSet : " + treeSet);
		System.out.println("HashSet : " + hashSet);

	}

}
