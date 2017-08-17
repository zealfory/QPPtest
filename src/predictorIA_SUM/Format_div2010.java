package predictorIA_SUM;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * ��webTrackDiversity2010_chenjiawei�ļ���subQuery������ȥ��
 * @author 1
 *
 */
public class Format_div2010 {
	
	
	/**
	 * ��webTrackDiversity2010_chenjiawei�ļ���subQuery������ȥ��
	 */
	public static void removePunctuation() throws IOException{
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		FileWriter fileWriter=null;
		String tempLine=null;
		
		fileReader=new FileReader("./webTrackDiversity2010/webTrackDiversity2010_chenjiawei.xml");
		bufferedReader=new BufferedReader(fileReader);
		fileWriter=new FileWriter("./webTrackDiversity2010/webTrackDiversity2010_chenjiawei_new.xml");
		
		while((tempLine=bufferedReader.readLine())!=null){
			//���ļ���subQuery�ı�����ȥ��
			if(!tempLine.startsWith("<")){
				tempLine=tempLine.replaceAll(",|\\.|\\?|!|\"|'|\\(|\\)|:", "");
				tempLine=tempLine.replaceAll("/", " ");
			}
			fileWriter.write(tempLine+"\n");
		}
		fileWriter.close();
		bufferedReader.close();
		System.out.println("�ļ��еı�������ȥ����..");
	}
	
	/**
	 * ת��input_subquery�ļ���queryId��ʽ,ת����ĸ�ʽΪmainQueryId-subQueryId
	 * ������Ϊԭ���ĸ�ʽΪmainQueryId*100+subQueryId
	 * @throws Exception 
	 */
	public static void queryIdConvertion(String input) throws Exception{
		FileReader fileReader=null;
		BufferedReader buffReader=null;
		String tempLine=null;
		String[] terms=null;
		FileWriter fileWriter=null;
		String queryId=null;
		
		//��input�ļ���queryId�ĸ�ʽ�ѱ�ת��,�׳��쳣
		fileReader=new FileReader(input);
		buffReader=new BufferedReader(fileReader);
		tempLine=buffReader.readLine();
		buffReader.close();
		terms=tempLine.split(" |\t");
		if(terms[0].contains("-")) throw new Exception("input�ļ���queryId�ĸ�ʽ�ѱ�ת��!");
		
		fileReader=new FileReader(input);
		buffReader=new BufferedReader(fileReader);
		fileWriter=new FileWriter(input+"_withQueryIdConverted");
		
		while((tempLine=buffReader.readLine())!=null){
			terms=tempLine.split("\t| ");
			queryId=terms[0];
			//��queryId�ĸ�ʽ����ת��
			if(queryId.charAt(queryId.length()-2)=='0'){
				queryId=queryId.substring(0, queryId.length()-2)+"-"+queryId.substring(queryId.length()-1, queryId.length());
			}else{
				//��ʱ��Ϊ�����λ��subQueryId
				queryId=queryId.substring(0,queryId.length()-2)+"-"+queryId.substring(queryId.length()-2,queryId.length());
			}
			//��queryId��terms��Ϣ�����ļ�
			tempLine=queryId+"\t"+terms[1]+"\t"+terms[2]+"\t"+terms[3]+"\t"+terms[4]+"\t"+terms[5]+"\n";
			fileWriter.write(tempLine);
		}
		fileWriter.close();
		buffReader.close();
		
		//ʹ��input+"_withQueryIdConverted"�ļ��滻input�ļ�
		File file1=null;
		File file2=null;
		file1=new File(input);
		file1.delete();
		file2=new File(input+"_withQueryIdConverted");
		file2.renameTo(file1);
		System.out.println("ת��input_subquery�ļ���queryId��ʽ,�����..");
	}
	/**
	 * ����input�ļ��е�rank,ʹrankΪrank+1
	 * @param input
	 * @throws IOException 
	 */
	public static void convertRank(String input) throws IOException{
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		String tempLine=null;
		String[] terms=null;
		FileWriter fileWriter=null;
		
		fileReader=new FileReader(input);
		bufferedReader=new BufferedReader(fileReader);
		fileWriter=new FileWriter(input+"_withRankConverted");
		while((tempLine=bufferedReader.readLine())!=null){
			terms=tempLine.trim().split(" |\t");
			terms[3]=String.valueOf(Integer.parseInt(terms[3])+1);
			tempLine=terms[0]+"\t"+terms[1]+"\t"+terms[2]+"\t"+terms[3]+"\t"+terms[4]+"\t"+terms[5]+"\n";
			fileWriter.write(tempLine);
		}
		fileWriter.close();
		bufferedReader.close();
		//ʹ��input+"_withRankConverted"�ļ��滻input�ļ�
		File file1=null;
		File file2=null;
		file1=new File(input);
		file1.delete();
		file2=new File(input+"_withRankConverted");
		file2.renameTo(file1);
		System.out.println(input+"�ļ��е�rank��Ϊrank+1,�����..");
	}
	/**
	 * ��input�ļ��е�rankһ�ж�Ϊ��,������rank��Ϣ,rankֵΪ1,2,3,...,n��
	 * @throws IOException 
	 */
	public static void generateRank(String input) throws IOException{
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		FileWriter fileWriter=null;
		String tempLine=null;
		String[] terms=null;
		String preQueryId=null;
		int rank=1;
		
		fileReader=new FileReader(input);
		bufferedReader=new BufferedReader(fileReader);
		fileWriter=new FileWriter(input+"_withRankGenerated");
		while((tempLine=bufferedReader.readLine())!=null){
			terms=tempLine.split(" |\t");
			//preQueryIdΪnullʱ
			if(preQueryId==null) preQueryId=terms[0];
			//preQueryId��terms[0]��ͬʱ,����rank��Ϣ��д�����ļ�,rank++
			if(preQueryId.equalsIgnoreCase(terms[0])){
				terms[3]=String.valueOf(rank);
				tempLine=terms[0]+"\t"+terms[1]+"\t"+terms[2]+"\t"+terms[3]+"\t"+terms[4]+"\t"+terms[5]+"\n";
				fileWriter.write(tempLine);
				rank++;
			}
			//preQueryId��terms[0]����ͬʱ,����rankֵ,����preQueryId,������terms��Ϣ
			if(!preQueryId.equalsIgnoreCase(terms[0])){
				rank=1;
				preQueryId=terms[0];
				terms[3]=String.valueOf(rank);
				tempLine=terms[0]+"\t"+terms[1]+"\t"+terms[2]+"\t"+terms[3]+"\t"+terms[4]+"\t"+terms[5]+"\n";
				fileWriter.write(tempLine);
				rank++;
			}
		}
		fileWriter.close();
		bufferedReader.close();
		//ʹ��input+"_withRankGenerated"�ļ��滻input�ļ�
		File file1=null;
		File file2=null;
		file1=new File(input);
		file1.delete();
		file2=new File(input+"_withRankGenerated");
		file2.renameTo(file1);
		System.out.println("����input�ļ���rank��Ϣ,�����..");
	}
	/**
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception{
		
		/*
		queryIdConvertion("webTrackDiversity2010/input.indri2010subquery_60addRank");
		queryIdConvertion("webTrack2011/input.indri2011subquery_60addRank");
		String input=null;
		*/
		//input="./webTrack2011/input.uogTrA45Vmx";
		//convertRank(input);
		//input="./webTrack2011/input.bpacad1";
		//generateRank(input);
		String root="C:/Users/1/Desktop/�½��ļ���"+"/";
		queryIdConvertion(root+"input.indri2010subquery");
		queryIdConvertion(root+"input.indri2011subquery");
		
		
		
	}

}
