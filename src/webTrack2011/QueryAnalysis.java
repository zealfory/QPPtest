package webTrack2011;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class QueryAnalysis {
	/**
	 * ���ڷ��� runId.queries�ļ�,�������ΪqueryId,queryLen,�γ�runId.new.queries�ļ�
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		FileWriter fileWriter=null;
		String tempLine=null;
		String preQueryId=null;
		int queryLen=0;
		String[] terms=null;
		String runId=null;
		runId="pircs1";
		
		fileReader=new FileReader("./TREC5/"+runId+".queries");
		bufferedReader=new BufferedReader(fileReader);
		fileWriter=new FileWriter("./TREC5/"+runId+".new.queries");
		while((tempLine=bufferedReader.readLine())!=null){
			//terms=tempLine.split(" |\t");
			//terms=tempLine.split(":");
			terms=tempLine.split(" |\t");
			if(preQueryId==null) preQueryId=terms[0];
			
			if(preQueryId.equalsIgnoreCase(terms[0])) queryLen++;
			
			if(!preQueryId.equalsIgnoreCase(terms[0])){
				fileWriter.write(preQueryId+" "+String.valueOf(queryLen)+"\n");
				preQueryId=terms[0];
				queryLen=1;
			}
		}
		//����preQueryId,queryLenΪ�����ļ�
		fileWriter.write(preQueryId+" "+String.valueOf(queryLen)+"\n");
		
		fileWriter.close();
		bufferedReader.close();
		System.out.println("QueryAnalysis has finished..");
	}

}
