package predictorIA_SUM;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 对IASUM类进行了修改,使用p(di|q),p(di|qj),代替1/i,1/(60.0+rankij),
 * <br>使用了IASUM类的Struct_sum类
 * @author 1
 *
 */
public class IASUM3 {
	//存储resultList_final信息
	public static ArrayList<Struct_sum> ArraySum=null;
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
	 */
	public static int k_mainQuery=100;
	
	/**
	 * 分析input.runId文件,
	 * @throws IOException 
	 */
	public static ArrayList<Struct_sum> processInput(String input) throws IOException{
		ArrayList<Struct_sum> array_structSum=new ArrayList<Struct_sum>();
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		String tempLine=null;
		String[] terms=null;
		String preQueryId=null;
		int count=0;
		Struct_sum struct_sum=null;

		fileReader=new FileReader(input);
		bufferedReader=new BufferedReader(fileReader);

		while((tempLine=bufferedReader.readLine())!=null){
			//若tempLine为空字符串,不处理
			if(tempLine.equals("")) continue;
			terms=tempLine.split("\t| ");
			//文件中出现名次为0的文档时,提示用户,并退出程序
			if(terms[3].equalsIgnoreCase("0")){
				System.out.println(input+"文件中出现名次为0的文档\n"+tempLine+"程序已退出..");
				System.exit(0);
			}
			//开始时,preQueryId为空
			if(preQueryId==null){
				preQueryId=terms[0];
			}
			//preQueryId和terms[0]相同时,分析count值
			if(preQueryId.equalsIgnoreCase(terms[0])){
				//count>=100时,不处理
				if(count>=k_mainQuery) continue;
				//count<100时
				//把tempLine信息存入array_structSum中
				struct_sum=new Struct_sum(tempLine);
				array_structSum.add(struct_sum);
				count++;
			}
			//preQueryId和terms[0]不同时,把count值复位,更新preQueryId值,并处理terms信息
			if(!preQueryId.equalsIgnoreCase(terms[0])){
				count=0;//count值复位
				preQueryId=terms[0];
				//把tempLine信息存入array_structSum中
				struct_sum=new Struct_sum(tempLine);
				array_structSum.add(struct_sum);
				count++;
			}
		}
		bufferedReader.close();
		return array_structSum;
	}
	/**
	 * 分析input_subquery文件,
	 * 此方法将把input_subquery的信息存入hashMap中,格式为(queryId=x\tdocId=x, rank=x\tscore=x)。
	 * k_subquery: 子查询subquery的截断参数, n_subquery: subquery对应的已读文档数
	 * @throws IOException 
	 */
	public static HashMap<String,String> processResult_subquery(String input) throws IOException{
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		String tempLine=null;
		String[] terms=null;
		String preQueryId=null;
		HashMap<String,String> mapResult_subquery=new HashMap<String,String>();
		String mapKey=null;
		String mapValue=null;
		int n_subquery=0;//subquery对应的已读文档数

		fileReader=new FileReader(input);
		bufferedReader=new BufferedReader(fileReader);

		while((tempLine=bufferedReader.readLine())!=null){
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
				mapValue="rank="+terms[3]+"\tscore="+terms[4];
				mapResult_subquery.put(mapKey, mapValue);
			}
			//preQueryId和terms[0]不相同时,重置n_subquery为1,更新preQueryId,分析terms信息
			if(!preQueryId.equalsIgnoreCase(terms[0])){
				//重置n_subquery=1
				n_subquery=1;
				preQueryId=terms[0];
				//把tempLine信息存入mapResult_subquery
				mapKey="queryId="+terms[0]+"\tdocId="+terms[2];
				mapValue="rank="+terms[3]+"\tscore="+terms[4];
				mapResult_subquery.put(mapKey, mapValue);
			}
		}
		bufferedReader.close();
		return mapResult_subquery;
	}
	/**
	 * 分析input_subquery,得到queryId subquery_count键值对,存入queryMap中
	 * @throws IOException 
	 */
	public static HashMap<String,String> generateQueryMap(String input) throws IOException{
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		HashMap<String,String> queryMap=new HashMap<String,String>();
		String tempLine=null;
		String[] terms=null;
		String preQueryId=null;//存储完整的queryId
		String mainQueryId=null;//存储mainQueryId
		int subQuery_count=0;//临时存储mainQueryId下不同subQueryId的个数

		fileReader=new FileReader(input);
		bufferedReader=new BufferedReader(fileReader);

		while((tempLine=bufferedReader.readLine())!=null){
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
		bufferedReader.close();
		return queryMap;
	}

	/**
	 * 计算出IASUM3结果,存入output_later文件
	 * @throws IOException 
	 * 
	 */
	public static void generateIASUM3(String output_middle,String output_later) throws IOException{
		//遍历ArraySum,给struct_sum的sum_di赋值
		Struct_sum struct_sum=null;
		//
		//在06/12,把n_preQuery数组更新为double数组
		double[] n_preQuery=null;//子查询数组,存储已检索文档与某子查询相关的次数
		String queryId=null;
		String preQueryId=null;
		int subQuery_count=0;//主查询下的子查询数

		for(int i=0;i<ArraySum.size();i++){
			struct_sum=ArraySum.get(i);
			queryId=struct_sum.getQueryId();
			//若preQueryId为null时,给preQueryId,subQuery_count赋值,初始化n_preQuery
			if(preQueryId==null) {
				preQueryId=queryId;
				subQuery_count=Integer.parseInt(MapQ.get(preQueryId));
				n_preQuery=new double[subQuery_count];
			}
			//若preQueryId和queryId相同时,
			if(preQueryId.equals(queryId)){
				update_sumdi(n_preQuery,struct_sum);
			}
			//若preQueryId和queryId不相同时,更新preQueryId,subQuery_count,n_preQuery,并分析struct_sum
			if(!preQueryId.equals(queryId)){
				preQueryId=queryId;
				subQuery_count=Integer.parseInt(MapQ.get(preQueryId));
				n_preQuery=new double[subQuery_count];
				update_sumdi(n_preQuery,struct_sum);
			}
		}

		//把ArraySum信息输出到output_middle文件,供用户分析IA_SUM算法的运行状况
		FileWriter fileWriter=null;
		BufferedWriter buffWriter=null;
		String tempLine=null;

		fileWriter=new FileWriter(output_middle);
		buffWriter=new BufferedWriter(fileWriter);
		for(int i=0;i<ArraySum.size();i++){
			struct_sum=ArraySum.get(i);
			tempLine="queryId="+struct_sum.getQueryId()+"\tdocId="+struct_sum.getDocId()+"\trank="+struct_sum.getRank()+"\tsum_di="+struct_sum.getSum_di()+"\n";
			buffWriter.write(tempLine);
		}
		buffWriter.close();

		//计算ArraySum中queryId对应的IA_SUM值
		double score_queryId=0;
		ArrayList<Struct_queryIdScore> arrayList_queryIdScore=new ArrayList<Struct_queryIdScore>();
		Struct_queryIdScore struct_queryIdScore=null;
		//重用preQueryId变量,重置preQueryId为空
		preQueryId=null;

		for(int i=0;i<ArraySum.size();i++){
			struct_sum=ArraySum.get(i);
			queryId=struct_sum.getQueryId();

			//preQueryId为空时,给preQueryId赋值
			if(preQueryId==null) preQueryId=queryId;
			//若preQueryId和queryId相同时,
			if(preQueryId.equals(queryId)){
				//使用*struct_sum.getScore()代替1/rank
				score_queryId=score_queryId+struct_sum.getSum_di()*struct_sum.getScore();
			}
			//若preQueryId和queryId不相同时,计算preQueryId的IASUM值,存入arrayList_queryIdScore,
			//更新preQueryId,重置score_queryId为0,并分析struct_sum信息
			if(!preQueryId.equals(queryId)){
				score_queryId=score_queryId/Integer.parseInt(MapQ.get(preQueryId));
				
				struct_queryIdScore=new Struct_queryIdScore();
				struct_queryIdScore.setQueryId(Integer.parseInt(preQueryId));
				struct_queryIdScore.setScore(score_queryId);
				arrayList_queryIdScore.add(struct_queryIdScore);
				//更新preQueryId,重置score_queryId为0,并分析struct_sum信息
				preQueryId=queryId;
				score_queryId=0;
				//*struct_sum.getScore()
				score_queryId=score_queryId+struct_sum.getSum_di()*struct_sum.getScore();
				
			}
		}
		//最后的preQueryId的IA_SUM值还没有存入arrayList_queryIdScore
		
		score_queryId=score_queryId/Integer.parseInt(MapQ.get(preQueryId));
		
		struct_queryIdScore=new Struct_queryIdScore();
		struct_queryIdScore.setQueryId(Integer.parseInt(preQueryId));
		struct_queryIdScore.setScore(score_queryId);
		arrayList_queryIdScore.add(struct_queryIdScore);

		//把arrayList_queryIdScore信息存入output_later文件中
		fileWriter=new FileWriter(output_later);
		buffWriter=new BufferedWriter(fileWriter);

		for(int i=0;i<arrayList_queryIdScore.size();i++){
			struct_queryIdScore=arrayList_queryIdScore.get(i);
			tempLine="queryId:\t"+struct_queryIdScore.getQueryId()+"\tIASUM3:\t"+struct_queryIdScore.getScore()+"\n";
			buffWriter.write(tempLine);
		}
		buffWriter.close();
		System.out.println("计算出IASUM3结果,存入output_later文件,已完成..");
	}
	/**
	 * 在06/12,把n_preQuery int型数组改为double型数组
	 * 给struct_sum的sum_di赋值
	 * @param struct_sum
	 */
	public static void update_sumdi(double[] n_preQuery,Struct_sum struct_sum){
		//alpha值
		//
		double alpha=0.1;
		//
		//
		String mapKey=null;
		String mainQueryId=null;
		double sum_di=0;
		//临时存储MapRes_subquery中mapKey对应的score信息
		String score=null;

		mainQueryId=struct_sum.getQueryId();
		
		//计算struct_sum的sum_di值
		for(int i=0;i<n_preQuery.length;i++){
			mapKey="queryId="+mainQueryId+"-"+(i+1)+"\tdocId="+struct_sum.getDocId();
			if(MapRes_subquery.containsKey(mapKey)){
				score=MapRes_subquery.get(mapKey).split("\t")[1].split("=")[1];
					
				sum_di=sum_di+Math.pow(1-alpha, n_preQuery[i])*Double.parseDouble(score);
				n_preQuery[i]++;
			}
		}
		
		//更新struct_sum的sum_di
		struct_sum.setSum_di(sum_di);
	}
	
	/**
	 * 计算IASUM3Scores
	 * @throws IOException 
	 * 
	 */
	public static void getIASUM3Scores(String input_runId,String input_subquery,String output_middle,String output_later) throws IOException{
		//分析input.runId文件
		ArraySum=processInput(input_runId);
		System.out.println("分析input.runId文件,已完成..");

		//分析input_subquery文件,此方法将把input_subquery的信息存入hashMap中,格式为(queryId=x\tdocId=x, rank=x)。
		MapRes_subquery=processResult_subquery(input_subquery);
		System.out.println("分析input_subquery文件,已完成..");

		//分析input_subquery,得到queryId subquery_count键值对,存入queryMap中
		MapQ=generateQueryMap(input_subquery);
		System.out.println("分析input_subquery文件,得到queryId subquery_count键值对,存入queryMap中,已完成..");

		//计算出IA-SUM结果,存入文件
		generateIASUM3(output_middle,output_later);
		System.out.println("计算IASUM3Score,已完成..");
	}
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args){

	}

}
