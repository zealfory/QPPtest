package trec4_0831;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
/**
 * 根据./TREC4文件,得到queryId,queryLength,后存入HashMap。
 * 
 * */
public class QueryLength {
	
	/**
	 * input文件的格式为queryId queryLen
	 * 根据./TREC4文件,得到queryId,queryLength,后存入HashMap。
	 * 
	 * */
	public static HashMap<String,String> getQueryLength(String input) throws IOException{
		HashMap<String,String> queryMap=new HashMap<String,String>();
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		String tempLine=null;
		String[] terms=null;
		String queryId=null;//临时存放queryId
		String queryLen=null;//临时存放queryLen
		
		fileReader=new FileReader(input);
		bufferedReader=new BufferedReader(fileReader);
		while((tempLine=bufferedReader.readLine())!=null){
			terms=tempLine.trim().split(" |\t");
			queryId=terms[0];
			queryLen=terms[1];
			//把queryId,queryLen存入queryMap
			queryMap.put(queryId, queryLen);
		}
		bufferedReader.close();
		System.out.println("根据"+input+"文件,得到queryId,queryLength,后存入HashMap,已完成..");
		return queryMap;
	}
	/**
	 * 显示queryMap信息
	 * @throws IOException 
	 * 
	 * */
	public static void showQueryMap(String input) throws IOException{
		HashMap<String,String> queryMap=getQueryLength(input);
		Set<Entry<String,String>> set=queryMap.entrySet();
		Iterator<Entry<String,String>> it=set.iterator();
		int i=0;
		Entry<String,String> queryEntry=null;
		while(it.hasNext()){
			queryEntry=it.next();
			System.out.println((++i)+" "+queryEntry.getKey()+" "+queryEntry.getValue());
		}
	}
	public static void main(String[] args) throws IOException{
		showQueryMap(null);
	}

}
