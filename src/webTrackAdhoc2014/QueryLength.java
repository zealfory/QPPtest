package webTrackAdhoc2014;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
/**
 * ����./webTrackAdhoc2014/testQueries�ļ�,�õ�queryId,queryLength,�����HashMap��
 * 
 * */
public class QueryLength {
	
	/**
	 * ����./webTrackAdhoc2014/testQueries�ļ�,�õ�queryId,queryLength,�����HashMap��
	 * 
	 * */
	public static HashMap<String,String> getQueryLength() throws IOException{
		HashMap<String,String> queryMap=new HashMap<String,String>();
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		String tempLine=null;
		String[] terms=null;
		String queryId=null;//��ʱ���queryId
		String queryLen=null;//��ʱ���queryLen
		
		fileReader=new FileReader("./webTrackAdhoc2014/testQueries");
		bufferedReader=new BufferedReader(fileReader);
		while((tempLine=bufferedReader.readLine())!=null){
			queryId=tempLine.substring(0, 3);//��ΪqueryIdΪ3λ��
			terms=tempLine.substring(4).trim().split("  | \t| |\t");
			queryLen=String.valueOf(terms.length);
			//��queryId,queryLen����queryMap
			queryMap.put(queryId, queryLen);
		}
		bufferedReader.close();
		System.out.println("����./webTrackAdhoc2014/testQueries�ļ�,�õ�queryId,queryLength,�����HashMap,�����..");
		return queryMap;
	}
	/**
	 * ��ʾqueryMap��Ϣ
	 * @throws IOException 
	 * 
	 * */
	public static void showQueryMap() throws IOException{
		HashMap<String,String> queryMap=getQueryLength();
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
		showQueryMap();
	}

}
