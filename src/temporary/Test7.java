package temporary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;


public class Test7 {
	
	public static void main(String[] args) throws Exception{
		
	}
	public static void wait_for_delete() throws Exception{
		
		String yourInput=null;
		Scanner scanner=new Scanner(System.in);
		
		//��ȡsize
		int size=0;
		System.out.println("����������size=");
		yourInput=scanner.nextLine();
		size=Integer.parseInt(yourInput);
		String[] num_str=new String[size];
		//�����ݴ���num_str��
		System.out.println("������ERR-IA@20 score and rank:(�м��Կո����)");
		int i=0;
		while(i<size){
			yourInput=scanner.nextLine();
			num_str[i]=yourInput;
			i++;
		}
		//
		i=0;
		double num1=0;
		double num2=0;
		double num3=0;
		String[] terms=null;
		while(i<size){
			terms=num_str[i].split(" |\t");
			num1=Double.parseDouble(terms[0]);
			num2=Double.parseDouble(terms[1]);
			num3=num1-num2;
			System.out.println(num3);
			i++;
		}
		System.out.println("ok");
	}

}

