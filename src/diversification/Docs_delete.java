package diversification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Docs_delete {
	
	/**
	 * ɾ��input�ļ��е÷�Ϊ0���ĵ�
	 * @param input
	 * @throws IOException 
	 */
	public static void delete_docs(String input) throws IOException{
		FileReader fileReader=null;
		BufferedReader buffReader=null;
		FileWriter fileWriter=null;
		String tempLine=null;
		String[] terms=null;
		
		fileReader=new FileReader(input);
		buffReader=new BufferedReader(fileReader);
		fileWriter=new FileWriter(input+"_extracted");
		
		while((tempLine=buffReader.readLine())!=null){
			terms=tempLine.split(" |\t");
			if(!terms[4].equals("0.0")){
				fileWriter.write(tempLine+"\n");
			}
		}
		fileWriter.close();
		buffReader.close();
		//��input+"_extracted"�ļ��滻input�ļ�
		File file1=null;
		File file2=null;
		file1=new File(input);
		file2=new File(input+"_extracted");
		file1.delete();
		file2.renameTo(file1);
		System.out.println("ɾ��input�ļ��е÷�Ϊ0���ĵ�,�����..");
	}
	/**
	 * ɾ��2009-2012��input�ļ��е÷�Ϊ0.0���ĵ�
	 * @throws IOException 
	 */
	public static void getInputFile_extracted() throws IOException{
		String input=null;
		//ɾ��2009��input�ļ��е÷�Ϊ0.0���ĵ�
		input="./diversification/input.indri2009pm2_60addRank";
		delete_docs(input);
		
		//ɾ��2010��input�ļ��е÷�Ϊ0.0���ĵ�
		input="./diversification/input.indri2010pm2_60addRank";
		delete_docs(input);
		
		//ɾ��2011��input�ļ��е÷�Ϊ0.0���ĵ�
		input="./diversification/input.indri2011pm2_60addRank";
		delete_docs(input);
		
		//ɾ��2012��input�ļ��е÷�Ϊ0.0���ĵ�
		input="./diversification/input.indri2012pm2_60addRank";
		delete_docs(input);
	}
	/**
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException{
		/*//ɾ��2009-2012��input�ļ��е÷�Ϊ0.0���ĵ�
		getInputFile_extracted();
		*/
		wait_forDelete();
	}
	/**
	 * ����ɾ���������
	 * ����4 best pm2 runs,ʦ��ļ������
	 * @throws IOException 
	 */
	public static void wait_forDelete() throws IOException{
		String input=null;
		//ɾ��2009��input�ļ��е÷�Ϊ0.0���ĵ�
		input="./diversification/input.2009-0.5";
		delete_docs(input);
		
		//ɾ��2010��input�ļ��е÷�Ϊ0.0���ĵ�
		input="./diversification/input.2010-0.4";
		delete_docs(input);
		
		//ɾ��2011��input�ļ��е÷�Ϊ0.0���ĵ�
		input="./diversification/input.2011-0.6";
		delete_docs(input);
		
		//ɾ��2012��input�ļ��е÷�Ϊ0.0���ĵ�
		input="./diversification/input.2012-0.3";
		delete_docs(input);
	}

}
