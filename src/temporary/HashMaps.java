package temporary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Hashtable;
import java.util.TreeMap;

public class HashMaps {
	public static void main(String[] args) {
		String a="h  ell o a  ";
		String[] terms=null;
		terms=a.split("  | |\t");
		int length=0;
		length=terms.length;
		System.out.println("length:"+length);
		
		for(int i=0;i<length;i++)
			System.out.println(terms[i]);
		//
		a="<title> International Organized Crime ";
		a=a.substring(7);
		System.out.println(a);
		a=a.trim();
		System.out.println(a);
		
	}
}      

