package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
/**
 * ����input�ļ�,�õ�queryId,queryLength,�����HashMap��
 * 
 * */
public class QueryLength {
	
	/**
	 * ����input�ļ�,�õ�queryId,queryLength,�����HashMap��
	 * 
	 * */
	public static HashMap<String,String> getQueryLength(String input) throws IOException{
		HashMap<String,String> queryMap=new HashMap<String,String>();
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		String tempLine=null;
		String[] terms=null;
		String queryId=null;//��ʱ���queryId
		String queryLen=null;//��ʱ���queryLen
		//"./webTrackDiversity2010/wt2010-topics.queries-only"
		fileReader=new FileReader(input);
		bufferedReader=new BufferedReader(fileReader);
		while((tempLine=bufferedReader.readLine())!=null){
			if(!tempLine.equalsIgnoreCase("")){
				terms=tempLine.trim().split(":");
				queryId=terms[0].trim();
				//��ȡqueryLen
				terms=terms[1].trim().split("  | \t| |\t");
				queryLen=String.valueOf(terms.length);
				//��queryId,queryLen����queryMap
				queryMap.put(queryId, queryLen);
			}
		}
		bufferedReader.close();
		System.out.println("����"+input+"�ļ�,�õ�queryId,queryLength,�����HashMap,�����..");
		return queryMap;
	}
	/**
	 * ��ʾqueryMap��Ϣ
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
		String input=null;
		showQueryMap(input);
	}

}
