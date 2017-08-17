package temporary;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Mean Average Precision
 *
 */
public class MeanAveragePrecision {
	
	/**
	 * 存储docId对应的相关性
	 *
	 */
	public static class DocRel{
		public String subTopic;//子主题
		public String docId;//文档Id
		public String relevancy;//相关性
		
	}
	/**
	 * 存储input.runId文件
	 */
	public static class InputRun{
		
	} 
	
	
	
	
	
	/**
	 * 装载completeqrels
	 * @param args
	 * @throws IOException 
	 */
	public static HashMap<String,ArrayList<DocRel>> loadCompleteQrel(String input) throws IOException{
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		DocRel docRel=null;
		ArrayList<DocRel> arrayDocRel=new ArrayList<DocRel>();
		HashMap<String,ArrayList<DocRel>> qrels=new HashMap<String,ArrayList<DocRel>>();
		String tempLine=null;
		String[] terms=null;
		String preQueryId=null;//先前的queryId
		
		fileReader=new FileReader(input);
		bufferedReader=new BufferedReader(fileReader);
		while((tempLine=bufferedReader.readLine())!=null){
			terms=tempLine.split(" |\t");
			//开始时preQueryId为null
			if(preQueryId==null) preQueryId=terms[0];
			
			//preQueryId和terms[0]相同时,存储terms信息
			if(preQueryId.equalsIgnoreCase(terms[0])){
				docRel=new DocRel();
				docRel.subTopic=terms[1];
				docRel.docId=terms[2];
				docRel.relevancy=terms[3];
				//把docRel放入arrayDocRel中
				arrayDocRel.add(docRel);
			}
			
			//preQueryId和terms[0]不相同时,存储preQueryId对应的信息,创建arrayDocRel对象,存储当前的terms信息
			if(!preQueryId.equalsIgnoreCase(terms[0])){
				//存储preQueryId对应的信息
				qrels.put(preQueryId, arrayDocRel);
				//创建信息arrayDocRel
				arrayDocRel=new ArrayList<DocRel>();
				
				//存储当前的terms信息
				preQueryId=terms[0];
				docRel=new DocRel();
				docRel.subTopic=terms[1];
				docRel.docId=terms[2];
				docRel.relevancy=terms[3];
				//把docRel放入arrayDocRel中
				arrayDocRel.add(docRel);
			}
			
		}
		//最后的preQueryId对应的信息没有存储,存储preQueryId对应的信息
		qrels.put(preQueryId, arrayDocRel);
		//关闭IO连接
		bufferedReader.close();
		return qrels;
	}
	
	
	
	
	
	/**
	 * 计算AP
	 */
	public static void computeAveragePrecision(){
		HashMap<String,Double> apMap=new HashMap<String,Double>();
		
		
		
		
	}
	
	

}
