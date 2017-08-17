package predictorIA_SUM;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 把webTrackDiversity2010_chenjiawei文件的subQuery标点符号去掉
 * @author 1
 *
 */
public class Format_div2010 {
	
	
	/**
	 * 把webTrackDiversity2010_chenjiawei文件的subQuery标点符号去掉
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
			//把文件中subQuery的标点符号去掉
			if(!tempLine.startsWith("<")){
				tempLine=tempLine.replaceAll(",|\\.|\\?|!|\"|'|\\(|\\)|:", "");
				tempLine=tempLine.replaceAll("/", " ");
			}
			fileWriter.write(tempLine+"\n");
		}
		fileWriter.close();
		bufferedReader.close();
		System.out.println("文件中的标点符号已去掉了..");
	}
	
	/**
	 * 转换input_subquery文件的queryId格式,转换后的格式为mainQueryId-subQueryId
	 * 这里认为原来的格式为mainQueryId*100+subQueryId
	 * @throws Exception 
	 */
	public static void queryIdConvertion(String input) throws Exception{
		FileReader fileReader=null;
		BufferedReader buffReader=null;
		String tempLine=null;
		String[] terms=null;
		FileWriter fileWriter=null;
		String queryId=null;
		
		//若input文件中queryId的格式已被转换,抛出异常
		fileReader=new FileReader(input);
		buffReader=new BufferedReader(fileReader);
		tempLine=buffReader.readLine();
		buffReader.close();
		terms=tempLine.split(" |\t");
		if(terms[0].contains("-")) throw new Exception("input文件中queryId的格式已被转换!");
		
		fileReader=new FileReader(input);
		buffReader=new BufferedReader(fileReader);
		fileWriter=new FileWriter(input+"_withQueryIdConverted");
		
		while((tempLine=buffReader.readLine())!=null){
			terms=tempLine.split("\t| ");
			queryId=terms[0];
			//对queryId的格式进行转换
			if(queryId.charAt(queryId.length()-2)=='0'){
				queryId=queryId.substring(0, queryId.length()-2)+"-"+queryId.substring(queryId.length()-1, queryId.length());
			}else{
				//此时认为最后两位是subQueryId
				queryId=queryId.substring(0,queryId.length()-2)+"-"+queryId.substring(queryId.length()-2,queryId.length());
			}
			//把queryId和terms信息存入文件
			tempLine=queryId+"\t"+terms[1]+"\t"+terms[2]+"\t"+terms[3]+"\t"+terms[4]+"\t"+terms[5]+"\n";
			fileWriter.write(tempLine);
		}
		fileWriter.close();
		buffReader.close();
		
		//使用input+"_withQueryIdConverted"文件替换input文件
		File file1=null;
		File file2=null;
		file1=new File(input);
		file1.delete();
		file2=new File(input+"_withQueryIdConverted");
		file2.renameTo(file1);
		System.out.println("转换input_subquery文件的queryId格式,已完成..");
	}
	/**
	 * 更新input文件中的rank,使rank为rank+1
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
		//使用input+"_withRankConverted"文件替换input文件
		File file1=null;
		File file2=null;
		file1=new File(input);
		file1.delete();
		file2=new File(input+"_withRankConverted");
		file2.renameTo(file1);
		System.out.println(input+"文件中的rank改为rank+1,已完成..");
	}
	/**
	 * 若input文件中的rank一列都为零,便生成rank信息,rank值为1,2,3,...,n。
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
			//preQueryId为null时
			if(preQueryId==null) preQueryId=terms[0];
			//preQueryId和terms[0]相同时,产生rank信息并写入新文件,rank++
			if(preQueryId.equalsIgnoreCase(terms[0])){
				terms[3]=String.valueOf(rank);
				tempLine=terms[0]+"\t"+terms[1]+"\t"+terms[2]+"\t"+terms[3]+"\t"+terms[4]+"\t"+terms[5]+"\n";
				fileWriter.write(tempLine);
				rank++;
			}
			//preQueryId和terms[0]不相同时,重置rank值,更新preQueryId,并分析terms信息
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
		//使用input+"_withRankGenerated"文件替换input文件
		File file1=null;
		File file2=null;
		file1=new File(input);
		file1.delete();
		file2=new File(input+"_withRankGenerated");
		file2.renameTo(file1);
		System.out.println("产生input文件的rank信息,已完成..");
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
		String root="C:/Users/1/Desktop/新建文件夹"+"/";
		queryIdConvertion(root+"input.indri2010subquery");
		queryIdConvertion(root+"input.indri2011subquery");
		
		
		
	}

}
