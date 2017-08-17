package clueweb09spam;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import utils.Result;
import utils.Result_compare;

public class Spamming {
	
	/**
	 * 删除input文件中percentile<70的文档。<br>
	 * <br>在20170309,使用了threshold变量,代替70,删除input文件中percentile < threshold的文档。<br>
	 * 把input文件存入hashMap中,再分析clueweb09spam.Fusion文件信息,
	 * 若percentile<70,删除hashMap中对应的信息。
	 * 把map_docId信息存入array_result中,对array_result信息进行排序,
	 * 生成rank信息,rank值为1,2,3,...,n,并输出array_result信息。
	 * @throws IOException 
	 * 
	 */
	public static void remove_spam(String input,double threshold) throws IOException{
		FileReader fileReader=null;
		BufferedReader buffReader=null;
		FileWriter fileWriter=null;
		String tempLine=null;
		String[] terms=null;
		//hashMap键值对的格式为(docId array_line)
		HashMap<String,ArrayList<String>> map_docId=new HashMap<String,ArrayList<String>>();
		ArrayList<String> array_line=null;
		//把input文件存入hashMap中
		fileReader=new FileReader(input);
		buffReader=new BufferedReader(fileReader);
		while((tempLine=buffReader.readLine())!=null){
			terms=tempLine.split(" |\t");
			//若map_docId不含terms[2],建立array_line,分析tempLine
			if(!map_docId.containsKey(terms[2])){
				array_line=new ArrayList<String>();
				array_line.add(tempLine);
				map_docId.put(terms[2], array_line);
			}else{
				//若map_docId含有terms[2],直接存入tempLine
				array_line=map_docId.get(terms[2]);
				array_line.add(tempLine);
			}
		}
		buffReader.close();
		//读取clueweb09spam.Fusion文件,若map_docId中某文档的percentile<70,删除map_docId中对应的键值对
		double percentile=0;
		fileReader=new FileReader("./clueweb09spam/clueweb09spam.Fusion");
		buffReader=new BufferedReader(fileReader);
		
		while((tempLine=buffReader.readLine())!=null){
			//tempLine的信息为percentile docId
			terms=tempLine.split(" |\t");
			//若map_docId含有键值terms[1],分析文档的percentile值
			if(map_docId.containsKey(terms[1])){
				percentile=Double.parseDouble(terms[0]);
				if(percentile<threshold) map_docId.remove(terms[1]);
			}
		}
		buffReader.close();
		//把map_docId信息存入array_result中
		array_line=null;//重用array_line变量,array_line置为空
		ArrayList<Result> array_result=new ArrayList<Result>();
		Result result=null;
		Set<Entry<String,ArrayList<String>>> set=null;
		Iterator<Entry<String,ArrayList<String>>> it=null;
		Entry<String,ArrayList<String>> entry=null;
		set=map_docId.entrySet();
		it=set.iterator();
		while(it.hasNext()){
			entry=it.next();
			array_line=entry.getValue();
			for(int i=0;i<array_line.size();i++){
				tempLine=array_line.get(i);
				result=new Result(tempLine);
				array_result.add(result);
			}
		}
		/**
		 * 1、对array_result进行排序: sort by topic, then by score, and
		 * then by docno, which is the traditional sort order for TREC runs
		 * 2、生成rank信息,rank值为1,2,3,...,n。
		 * 
		 */
		//对array_result信息进行排序
		Collections.sort(array_result, new Result_compare());
		//更新array_result中对象result的rank信息,rank值为1,2,3,...,n
		int rank=1;
		int preTopic=0;
		for(int i=0;i<array_result.size();i++){
			result=array_result.get(i);
			//若preTopic==0,对preTopic赋值
			if(preTopic==0) preTopic=result.getTopic();
			//若preTopic和result.getTopic()相同,更新result的rank信息,然后rank++
			if(preTopic==result.getTopic()){
				result.setRank(rank);
				rank++;
			}
			//若preTopic和result.getTopic()不相同,重置rank,preTopic,更新result的rank信息,rank++
			if(preTopic!=result.getTopic()){
				rank=1;
				preTopic=result.getTopic();
				result.setRank(rank);
				rank++;
			}
		}
		//输出array_result信息
		fileWriter=new FileWriter(input+"_spammed");
		for(int i=0;i<array_result.size();i++){
			result=array_result.get(i);
			fileWriter.write(result.getTempLine());
		}
		fileWriter.close();
		System.out.println("删除input文件中percentile<threshold的文档,已完成..");
	}
	
	/**
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException{
		
	}

}
