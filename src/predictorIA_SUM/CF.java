package predictorIA_SUM;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import utils.Arith;

/**
 * CF指coverage frequency
 * @author 1
 *
 */
public class CF {
	//存储resultList_final信息
	public static ArrayList<QueryDoc> array_queryDoc=null;
	//存储resultList_subquery信息
	public static HashMap<String,String> MapRes_subquery=null;
	//存储mainQueryId,subQuery_count信息
	public static HashMap<String,String> MapQ=null;
	/**
	 * 子查询subquery的截断参数
	 */
	public static int k_subquery=1000;
	/**
	 * 主查询mainQuery的截断参数
	 * 获取每个queryId对应的k_mainQuery篇文档
	 */
	public static int k_mainQuery=20;
	
	/**
	 * 分析input.runId文件,
	 * @throws IOException 
	 */
	public static void processInput(String input) throws IOException{
		array_queryDoc=new ArrayList<QueryDoc>();
		FileReader fileReader=null;
		BufferedReader buffReader=null;
		String tempLine=null;
		String[] terms=null;
		String preQueryId=null;
		int count=0;
		QueryDoc queryDoc=null;

		fileReader=new FileReader(input);
		buffReader=new BufferedReader(fileReader);

		while((tempLine=buffReader.readLine())!=null){
			//若tempLine为空字符串,不处理
			if(tempLine.equals("")) continue;
			terms=tempLine.split("\t| ");
			//开始时,preQueryId为空
			if(preQueryId==null){
				preQueryId=terms[0];
			}
			//preQueryId和terms[0]相同时,分析count值
			if(preQueryId.equalsIgnoreCase(terms[0])){
				//count>=100时,不处理
				if(count>=k_mainQuery) continue;
				//count<100时
				//把tempLine信息存入array_queryDoc中
				queryDoc=new QueryDoc(tempLine);
				array_queryDoc.add(queryDoc);
				count++;
			}
			//preQueryId和terms[0]不同时,把count值复位,更新preQueryId值,并处理terms信息
			if(!preQueryId.equalsIgnoreCase(terms[0])){
				count=0;//count值复位
				preQueryId=terms[0];
				//把tempLine信息存入array_queryDoc中
				queryDoc=new QueryDoc(tempLine);
				array_queryDoc.add(queryDoc);
				count++;
			}
		}
		buffReader.close();
	}
	/**
	 * 计算array_queryDoc中某queryId下第n1到n2篇文档整体的意图覆盖度
	 * @param n1
	 * @param n2
	 */
	public static double cov(ArrayList<QueryDoc> array_temp,int n1,int n2){
		int[] subQuery=null;
		String queryId=null;
		int subQuery_count=0;
		QueryDoc queryDoc=null;
		double covRatio=0;
		
		queryDoc=array_temp.get(n1);
		queryId=queryDoc.getQueryId();
		subQuery_count=Integer.parseInt(MapQ.get(queryId));
		subQuery=new int[subQuery_count];
		//根据array_temp中第n1到n2篇文档更新subQuery数组
		for(int i=n1;i<=n2;i++){
			queryDoc=array_temp.get(i);
			updateSubQuery(queryDoc,subQuery);
		}
		//计算意图覆盖度
		int count=0;
		for(int i=0;i<subQuery.length;i++){
			if(subQuery[i]==1) count++;
		}
		//提供(相对)精确的除法运算
		covRatio=Arith.div(count, subQuery.length);
		return covRatio;	
	}
	/**
	 * 根据queryDoc信息更新subQuery数组
	 * @param subQuery
	 */
	public static void updateSubQuery(QueryDoc queryDoc,int[] subQuery){
		String mainQueryId=queryDoc.getQueryId();
		String docId=queryDoc.getDocId();
		String mapKey=null;
		
		for(int i=0;i<subQuery.length;i++){
			mapKey="queryId="+mainQueryId+"-"+(i+1)+"\tdocId="+docId;
			//若MapRes_subquery含有mapKey,subQuery[i]置为1
			if(MapRes_subquery.containsKey(mapKey)){
				subQuery[i]=1;
			}
		}
	}
	
	/**
	 * 分析input_subquery文件,
	 * 此方法将把input_subquery的信息存入hashMap中,格式为(queryId=x\tdocId=x, rank=x)。
	 * k_subquery: 子查询subquery的截断参数, n_subquery: subquery对应的已读文档数
	 * @throws IOException 
	 */
	public static HashMap<String,String> processResult_subquery(String input) throws IOException{
		FileReader fileReader=null;
		BufferedReader buffReader=null;
		String tempLine=null;
		String[] terms=null;
		String preQueryId=null;
		HashMap<String,String> mapResult_subquery=new HashMap<String,String>();
		String mapKey=null;
		String mapValue=null;
		int n_subquery=0;//subquery对应的已读文档数
		
		fileReader=new FileReader(input);
		buffReader=new BufferedReader(fileReader);

		while((tempLine=buffReader.readLine())!=null){
			terms=tempLine.split("\t| ");
			//preQueryId为空时,
			if(preQueryId==null) preQueryId=terms[0];
			//preQueryId和terms[0]相同时
			if(preQueryId.equalsIgnoreCase(terms[0])){
				//记录subquery下的已读行数
				n_subquery++;
				//n_subquery>k_subquery时,不分析terms信息
				if(n_subquery>k_subquery) continue;
				//n_subquery<=k_subquery时,把tempLine信息存入mapResult_subquery
				mapKey="queryId="+terms[0]+"\tdocId="+terms[2];
				mapValue="rank="+terms[3];
				mapResult_subquery.put(mapKey, mapValue);
			}
			//preQueryId和terms[0]不相同时,重置n_subquery为1,更新preQueryId,分析terms信息
			if(!preQueryId.equalsIgnoreCase(terms[0])){
				//重置n_subquery=1
				n_subquery=1;
				preQueryId=terms[0];
				//把tempLine信息存入mapResult_subquery
				mapKey="queryId="+terms[0]+"\tdocId="+terms[2];
				mapValue="rank="+terms[3];
				mapResult_subquery.put(mapKey, mapValue);
			}
		}
		buffReader.close();
		return mapResult_subquery;
	}
	/**
	 * 分析input_subquery,得到queryId subquery_count键值对,存入queryMap中
	 * @throws IOException 
	 */
	public static HashMap<String,String> generateQueryMap(String input) throws IOException{
		FileReader fileReader=null;
		BufferedReader buffReader=null;
		HashMap<String,String> queryMap=new HashMap<String,String>();
		String tempLine=null;
		String[] terms=null;
		String preQueryId=null;//存储完整的queryId
		String mainQueryId=null;//存储mainQueryId
		int subQuery_count=0;//临时存储mainQueryId下不同subQueryId的个数
		
		fileReader=new FileReader(input);
		buffReader=new BufferedReader(fileReader);

		while((tempLine=buffReader.readLine())!=null){
			terms=tempLine.split("\t| ");
			//preQueryId为空时,给preQueryId,mainQueryId,subQueryId赋初值,更新subQuery_count
			if(preQueryId==null){
				preQueryId=terms[0];
				mainQueryId=terms[0].split("-")[0];
				subQuery_count++;
			}
			//preQueryId和terms[0]相等时
			if(preQueryId.equalsIgnoreCase(terms[0])){
				continue;
			}
			//若preQueryId和terms[0]不相等时
			if(!preQueryId.equalsIgnoreCase(terms[0])){
				//若mainQueryId和terms[0].split("-")[0]相等
				if(mainQueryId.equalsIgnoreCase(terms[0].split("-")[0])){
					preQueryId=terms[0];
					subQuery_count++;
				}
				//若mainQueryId和terms[0].split("-")[0]不相等,把mainQueryId信息存入queryMap,复位subQuery_count,分析terms信息
				if(!mainQueryId.equalsIgnoreCase(terms[0].split("-")[0])){
					queryMap.put(mainQueryId, String.valueOf(subQuery_count));
					subQuery_count=0;//subQuery_count复位
					preQueryId=terms[0];
					mainQueryId=terms[0].split("-")[0];
					subQuery_count++;
				}
			}
		}
		//最后的mainQueryId和subQuery_count还没存入queryMap
		queryMap.put(mainQueryId, String.valueOf(subQuery_count));
		buffReader.close();
		return queryMap;
	}
	/**
	 * 计算array_queryDoc中某queryId下的第0到r篇文档共覆盖所有子意图的次数,
	 * <br>并除以(r+1)
	 * @param r
	 * @param k
	 */
	public static double coverageTime(ArrayList<QueryDoc> array_temp,int r){
		double count_ct=0;
		double covRatio=0;
		int p=0;
		for(int i=0;i<=r;i++){
			covRatio=cov(array_temp,p,i);
			if(covRatio==1.0){
				count_ct++;
				p=i+1;
			}else if(i==r){
				count_ct=count_ct+covRatio;
			}
		}
		count_ct=count_ct/(r+1);
		return count_ct;
	}
	/**
	 * 计算coverageFre的值
	 */
	public static double coverageFre(ArrayList<QueryDoc> array_temp){
		double covFre=0;
		
		for(int i=0;i<array_temp.size();i++){
			covFre=covFre+coverageTime(array_temp,i);
		}
		return covFre;
	}
	/**
	 * 产生每个queryId对应的coverageFrequency值,并存入output文件
	 * @throws IOException 
	 */
	public static void generateCF(String output) throws IOException{
		FileWriter fileWriter=null;
		BufferedWriter buffWriter=null;
		QueryDoc queryDoc=null;
		ArrayList<QueryDoc> array_temp=null;//存储某queryId下的文档
		//存储每个queryId对应的array_temp
		ArrayList<ArrayList<QueryDoc>> arrays=new ArrayList<ArrayList<QueryDoc>>();
		String preQueryId=null;
		
		//把array_queryDoc中的文档按queryId分组存入arrays中
		for(int i=0;i<array_queryDoc.size();i++){
			queryDoc=array_queryDoc.get(i);
			//若preQueryId为空,给preQueryId赋初值,建立array_temp
			if(preQueryId==null){
				preQueryId=queryDoc.getQueryId();
				array_temp=new ArrayList<QueryDoc>();
			}
			//若preQueryId和queryDoc的queryId相同,把queryDoc加入array_temp中
			if(preQueryId.equals(queryDoc.getQueryId())){
				array_temp.add(queryDoc);
			}
			//若preQueryId和queryDoc的queryId不相同,把preQueryId对应的array_temp存入arrays,分析queryDoc
			if(!preQueryId.equals(queryDoc.getQueryId())){
				arrays.add(array_temp);
				//更新preQueryId,建立array_temp,并把queryDoc加入array_temp中
				preQueryId=queryDoc.getQueryId();
				array_temp=new ArrayList<QueryDoc>();
				array_temp.add(queryDoc);
			}
		}
		//最后的array_temp还没有存入arrays中
		arrays.add(array_temp);
		
		//计算每个queryId对应的CF值,并存入array_queryIdScore中
		array_temp=null;//重置array_temp为空
		String queryId=null;
		double covFre=0;
		ArrayList<Struct_queryIdScore> array_queryIdScore=new ArrayList<Struct_queryIdScore>();
		Struct_queryIdScore struct_queryIdScore=null;
		for(int i=0;i<arrays.size();i++){
			array_temp=arrays.get(i);
			queryId=array_temp.get(0).getQueryId();
			covFre=coverageFre(array_temp);
			//把queryId和covFre信息存入array_queryIdScore
			struct_queryIdScore=new Struct_queryIdScore();
			struct_queryIdScore.setQueryId(Integer.parseInt(queryId));
			struct_queryIdScore.setScore(covFre);
			array_queryIdScore.add(struct_queryIdScore);
		}
		
		//把array_queryIdScore信息存入output文件中
		fileWriter=new FileWriter(output);
		buffWriter=new BufferedWriter(fileWriter);
		String tempLine=null;
		for(int i=0;i<array_queryIdScore.size();i++){
			struct_queryIdScore=array_queryIdScore.get(i);
			tempLine="queryId:\t"+struct_queryIdScore.getQueryId()+"\tCF:\t"+struct_queryIdScore.getScore()+"\n";
			buffWriter.write(tempLine);
		}
		buffWriter.close();
		System.out.println("计算出CF结果,存入output文件,已完成..");
	}
	/**
	 * 获得coverage frequency的预测值
	 * @throws IOException 
	 * 
	 */
	public static void getCFScores(String input_runId,String input_subquery,String output) throws IOException{
		//分析input.runId文件
		processInput(input_runId);
		//分析input_subquery文件
		MapRes_subquery=processResult_subquery(input_subquery);
		//分析input_subquery,得到queryId subquery_count键值对,存入queryMap中
		MapQ=generateQueryMap(input_subquery);
		//产生每个queryId对应的coverageFrequency值,并存入output文件
		generateCF(output);
	}
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		
	}

}
/**
 * 存储resultList_final信息
 * @author 1
 *
 */
class QueryDoc{
	String queryId;
	String docId;
	public String getQueryId() {
		return queryId;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public QueryDoc(String tempLine){
		String[] terms=null;
		terms=tempLine.split(" |\t");
		queryId=terms[0];
		docId=terms[2];
	}
}
