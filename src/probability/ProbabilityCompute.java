package probability;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class ProbabilityCompute {
	public static HashMap<String,ArrayList<Rel>> Map_rel=new HashMap<String,ArrayList<Rel>>();
	public static HashMap<String,String> MapQ=new HashMap<String,String>();
	/**
	 * 把input_subquery文件存入Map_rel中
	 * @throws IOException 
	 * 
	 */
	public static void store_inputSubquery(String input) throws IOException{
		ArrayList<Rel> array_rel=null;
		Rel rel=null;
		FileReader fileReader=null;
		BufferedReader buffReader=null;
		String tempLine=null;
		String[] terms=null;
		
		//把input_subquery文件存入Map_rel中
		String preQueryId=null;
		fileReader=new FileReader(input);
		buffReader=new BufferedReader(fileReader);
		while((tempLine=buffReader.readLine())!=null){
			terms=tempLine.split(" |\t");
			//若preQueryId为空,给preQueryId赋值,建立array_rel
			if(preQueryId==null){
				preQueryId=terms[0];
				array_rel=new ArrayList<Rel>();
			}
			//若preQueryId和terms[0]相同,则把tempLine信息存入array_rel中
			if(preQueryId.equals(terms[0])){
				rel=new Rel(tempLine);
				array_rel.add(rel);
			}
			//若preQueryId和terms[0]不相同时,把preQueryId对应的array_rel存入Map_rel中,
			if(!preQueryId.equals(terms[0])){
				Map_rel.put(preQueryId, array_rel);
				//更新preQueryId,建立array_rel,并分析tempLine信息
				preQueryId=terms[0];
				array_rel=new ArrayList<Rel>();
				rel=new Rel(tempLine);
				array_rel.add(rel);
			}
		}
		//最后的preQueryId对应的array_rel还没有存入Map_rel中
		Map_rel.put(preQueryId, array_rel);
		buffReader.close();
	}
	/**
	 * 把2009-2012年的input_subquery文件存入Map_rel中
	 * @throws IOException 
	 */
	public static void process_inputSubquery() throws IOException{
		String input=null;
		//把2009年的input_subquery文件存入map_rel中
		input="./diversification/input.indri2009subquery_60addRank";
		store_inputSubquery(input);
		
		//把2010年的input_subquery文件存入map_rel中
		input="./diversification/input.indri2010subquery_60addRank";
		store_inputSubquery(input);
		
		//把2011年的input_subquery文件存入map_rel中
		input="./diversification/input.indri2011subquery_60addRank";
		store_inputSubquery(input);
		
		//把2012年的input_subquery文件存入map_rel中
		input="./diversification/input.indri2012subquery_60addRank";
		store_inputSubquery(input);
	}
	/**
	 * 把qrels文件存入MapQ中,MapQ中键值对的格式为(queryId=\tdocId= relevant=)。
	 * @param input
	 * @throws IOException 
	 */
	public static void store_qrel(String input) throws IOException{
		FileReader fileReader=null;
		BufferedReader buffReader=null;
		String tempLine=null;
		String[] terms=null;
		String mapKey=null;
		String mapValue=null;
		
		fileReader=new FileReader(input);
		buffReader=new BufferedReader(fileReader);
		while((tempLine=buffReader.readLine())!=null){
			terms=tempLine.split(" |\t");
			//若relevant<=0时,忽略这一行信息
			if(Integer.parseInt(terms[3])<=0) continue;
			
			//queryId的构成形式为terms[0]*100+terms[1]
			int queryId=Integer.parseInt(terms[0])*100+Integer.parseInt(terms[1]);
			int relevant=Integer.parseInt(terms[3]);
			//若relevant<0,把relevant设为0,若relevant>0,则把relevant设为1
			if(relevant<0) relevant=0;
			if(relevant>0) relevant=1;
			mapKey="queryId="+String.valueOf(queryId)+"\tdocId="+terms[2];
			mapValue="relevant="+String.valueOf(relevant);
			//mapKey,mapValue存入MapQ中
			MapQ.put(mapKey, mapValue);
		}
		buffReader.close();
	}
	/**
	 * 把2009-2012年的qrels文件存入MapQ中
	 * @throws IOException 
	 */
	public static void process_qrel() throws IOException{
		String input=null;
		//把2009年的qrels文件存入MapQ中
		input="./diversification/qrels.2009";
		store_qrel(input);

		//把2010年的qrels文件存入MapQ中
		input="./diversification/qrels.2010";
		store_qrel(input);
		
		//把2011年的qrels文件存入MapQ中
		input="./diversification/qrels.2011";
		store_qrel(input);
		
		//把2012年的qrels文件存入MapQ中
		input="./diversification/qrels.2012";
		store_qrel(input);
	}
	/**
	 * 根据MapQ给Map_rel中rel对象的relevant赋值
	 */
	public static void completeMap_rel(){
		Set<Entry<String,ArrayList<Rel>>> set=null;
		Iterator<Entry<String,ArrayList<Rel>>> it=null;
		Entry<String,ArrayList<Rel>> entry=null;
		ArrayList<Rel> array_rel=null;
		String queryId=null;
		
		//遍历Map_rel,根据MapQ给Map_rel中rel对象的relevant赋值
		String mapKey=null;
		String mapValue=null;
		Rel rel=null;
		set=Map_rel.entrySet();
		it=set.iterator();
		while(it.hasNext()){
			entry=it.next();
			queryId=entry.getKey();
			array_rel=entry.getValue();
			//遍历array_rel
			for(int i=0;i<array_rel.size();i++){
				rel=array_rel.get(i);
				mapKey="queryId="+queryId+"\tdocId="+rel.getDocId();
				mapValue=MapQ.get(mapKey);
				if(mapValue!=null){
					rel.setRelevant(Integer.parseInt(mapValue.split("=")[1]));
				}
			}
		}
		System.out.println("根据MapQ给Map_rel中rel对象的relevant赋值,已完成..");
	}
	/**
	 * 分析Map_rel中的信息。某查询对应的检索结果列表,
	 * 对于位置i处文档,计算此文档为相关文档的概率。
	 * @throws IOException 
	 */
	public static void generate_probability(String input) throws IOException{
		Set<Entry<String,ArrayList<Rel>>> set=null;
		Iterator<Entry<String,ArrayList<Rel>>> it=null;
		Entry<String,ArrayList<Rel>> entry=null;
		ArrayList<Rel> array_rel=null;
		
		int k_analy=500;//分析检索结果的前500篇文档
		double[] prob=new double[k_analy];//计算某位置处文档为相关的概率
		int sum_rel=0;//临时存储某位置处的文档为相关文档的数目
		int sum_doc=0;//临时存储某位置处文档的总数
		//
		for(int i=0;i<k_analy;i++){
			set=Map_rel.entrySet();
			it=set.iterator();
			while(it.hasNext()){
				entry=it.next();
				array_rel=entry.getValue();
				//若array_rel包含第i项
				if(array_rel.size()>i){
					sum_rel=sum_rel+array_rel.get(i).getRelevant();
					sum_doc++;
				}
			}
			//若sum_doc>0
			if(sum_doc>0) prob[i]=Double.valueOf(sum_rel)/sum_doc;
			//把sum_rel,sum_doc置为0
			sum_rel=0;
			sum_doc=0;
		}
		//把prob数组输出到文件中
		FileWriter fileWriter=null;
		BufferedWriter buffWriter=null;
		String tempLine=null;
		
		fileWriter=new FileWriter(input);
		buffWriter=new BufferedWriter(fileWriter);
		tempLine="rel_prob\t1.0/(60+rank)\n";
		buffWriter.write(tempLine);
		for(int i=0;i<prob.length;i++){
			tempLine=String.format("%.6f", prob[i])+"\t"+String.format("%.6f", 1.0/(60+i+1))+"\n";
			buffWriter.write(tempLine);
		}
		buffWriter.close();
		System.out.println("计算结果列表第i篇文档为相关文档的概率,已完成..");
	}
	/**
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException{
		//把2009-2012年的input_subquery文件存入Map_rel中
		process_inputSubquery();
		
		//把2009-2012年的qrels文件存入MapQ中
		process_qrel();
		
		//根据MapQ给Map_rel中rel对象的relevant赋值
		completeMap_rel();
		
		//计算结果列表第i篇文档为相关文档的概率
		String input=null;
		input="./diversification/probability_subquery.txt";
		generate_probability(input);
		System.out.println("概率计算,已完成!");
	}
	

}
class Rel{
	String docId;
	int relevant;
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public int getRelevant() {
		return relevant;
	}
	public void setRelevant(int relevant) {
		this.relevant = relevant;
	}
	public Rel(String tempLine){
		String[] terms=null;
		terms=tempLine.split(" |\t");
		docId=terms[2];
		relevant=0;
	}

}
