package trec4_0831;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GenerateResult_batch {
	
	/**
	 * 
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	public static void printGeneratedResult() throws IOException, InterruptedException{
		
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		String runIdFile=null;
		String tempLine=null;
		String packageName=null;
		String runId=null;
		int n=0;
		int round=0;
		String queries_only=null;
		
		//����TREC4���ݼ���ϵͳ
		runIdFile="./trec4_0831/runId.txt";
		fileReader=new FileReader(runIdFile);
		bufferedReader=new BufferedReader(fileReader);
		packageName="trec4_0831";
		round=49;
		queries_only=null;
		
		while((tempLine=bufferedReader.readLine())!=null){
			runId=tempLine.split("\\.")[1];
			System.out.println("\n\n"+(++n)+"��trackΪ"+runId);
			GenerateResult.getGeneratedResult(packageName, runId,round,queries_only);
			Thread.sleep(10000);
		}
		bufferedReader.close();
		System.out.println("�����������,�����!");
		
	}
	/**
	 * 
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException{
		printGeneratedResult();
	}
}
