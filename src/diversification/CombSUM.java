package diversification;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import utils.Result;
import utils.Result_compare;

/**
 * 此处使用CombSUM作为多样化重排算法，CombSUM: s(d,q)=(1-lanta)*P(d|q)+lanta*SUM(P(qi|q)*p(d|qi))
 * @author 1
 *
 */
public class CombSUM {
	
	//存储mainQuery的resultList信息
	public HashMap<String,ArrayList<Result>> MapRes_mainQuery=null;
	//存储subquery的resultList_subquery信息
	public HashMap<String,String> MapRes_subquery=null;
	//存储主查询下的子查询数,<mainQuery,subquery_count>
	public HashMap<String,String> MapQ=null;
	//CombSUM算法的系数
	public double lanta=0;
	
	/**
	 * 把input_mainQuery文件中的信息存入MapRes_mainQuery,
	 * 其格式为["queryId="+preQueryId, array_result]键值对
	 * @param input_mainQuery
	 * @throws IOException 
	 */
	public void processResult_mainQuery(String input_mainQuery) throws IOException{
		BufferedReader buffReader=null;
		String tempLine=null;
		String[] terms=null;
		String preQueryId=null;
		ArrayList<Result> array_result=null;
		Result result=null;
		
		buffReader=new BufferedReader(new FileReader(input_mainQuery));
		//实例化MapRes_mainQuery
		MapRes_mainQuery=new HashMap<String,ArrayList<Result>>();
		
		while((tempLine=buffReader.readLine())!=null){
			//若tempLine为空字符串,执行下一循环
			if(tempLine.equals("")) continue;
			terms=tempLine.split(" |\t");
			//若preQueryId==null
			if(preQueryId==null){
				preQueryId=terms[0];
				array_result=new ArrayList<Result>();
			}
			//若preQueryId和terms[0]相同
			if(preQueryId.equals(terms[0])){
				result=new Result(tempLine);
				array_result.add(result);
			}
			//若preQueryId和terms[0]不相同,把array_result存入MapRes_mainQuery中,更新preQueryId,给array_result赋新值,处理tempLine信息
			if(!preQueryId.equals(terms[0])){
				//把array_result存入MapRes_mainQuery中
				MapRes_mainQuery.put("queryId="+preQueryId, array_result);
				//更新preQueryId,建立array_result对象,处理tempLine信息
				preQueryId=terms[0];
				array_result=new ArrayList<Result>();
				result=new Result(tempLine);
				array_result.add(result);
			}
		}
		buffReader.close();
		//最后的array_result还没有存入MapRes_mainQuery中
		MapRes_mainQuery.put("queryId="+preQueryId, array_result);
		System.out.println("把input_mainQuery文件中的信息存入MapRes_mainQuery,已完成..");
	}
	/**
	 * 把input_subquery文件的信息存入MapRes_subquery中,
	 * 格式为["queryId="+queryId+"\tdocId="+docId, "score="+score]
	 * @param input_subquery
	 * @throws IOException 
	 */
	public void processResult_subquery(String input_subquery) throws IOException{
		BufferedReader buffReader=null;
		String tempLine=null;
		String[] terms=null;
		String mapKey=null;
		String mapValue=null;
		
		MapRes_subquery=new HashMap<String,String>();
		buffReader=new BufferedReader(new FileReader(input_subquery));
		
		while((tempLine=buffReader.readLine())!=null){
			terms=tempLine.split(" |\t");
			mapKey="queryId="+terms[0]+"\tdocId="+terms[2];
			mapValue="score="+terms[4];
			//把mapKey,mapValue存入MapRes_subquery中
			MapRes_subquery.put(mapKey, mapValue);
		}
		buffReader.close();
		System.out.println("把input_subquery文件的信息存入MapRes_subquery中,已完成..");
	}
	
	/**
	 * 分析input_subquery,得到queryId subquery_count键值对,存入MapQ中
	 * @throws IOException 
	 */
	public void generateMapQ(String input_subquery) throws IOException{
		
		BufferedReader buffReader=null;
		String tempLine=null;
		String[] terms=null;
		String preQueryId=null;//存储完整的queryId
		String mainQueryId=null;//存储mainQueryId
		int subquery_count=0;//临时存储mainQueryId下不同subqueryId的个数
		
		buffReader=new BufferedReader(new FileReader(input_subquery));
		//建立MapQ对象
		MapQ=new HashMap<String,String>();
		
		while((tempLine=buffReader.readLine())!=null){
			terms=tempLine.split("\t| ");
			//preQueryId为空时,给preQueryId,mainQueryId赋初值,更新subquery_count
			if(preQueryId==null){
				preQueryId=terms[0];
				//terms[0]的查询格式为mainQueryId*100+No. of subquery
				mainQueryId=terms[0].substring(0, terms[0].length()-2);
				subquery_count++;
			}
			//preQueryId和terms[0]相等时
			if(preQueryId.equalsIgnoreCase(terms[0])){
				continue;
			}
			//若preQueryId和terms[0]不相等时
			if(!preQueryId.equalsIgnoreCase(terms[0])){
				//若mainQueryId和terms[0].substring(0, terms[0].length()-2))相等
				if(mainQueryId.equals(terms[0].substring(0, terms[0].length()-2))){
					preQueryId=terms[0];
					subquery_count++;
				}
				//若mainQueryId和terms[0].substring(0, terms[0].length()-2)不相等,把mainQueryId信息存入MapQ,复位subquery_count,分析terms信息
				if(!mainQueryId.equals(terms[0].substring(0, terms[0].length()-2))){
					MapQ.put(mainQueryId, String.valueOf(subquery_count));
					subquery_count=0;//subquery_count复位
					preQueryId=terms[0];
					mainQueryId=terms[0].substring(0, terms[0].length()-2);
					subquery_count++;
				}
			}
		}
		//最后的mainQueryId和subquery_count还没存入MapQ
		MapQ.put(mainQueryId, String.valueOf(subquery_count));
		buffReader.close();
	}
	
	/**
	 * 对mainQueryId对应的检索结果进行多样化重排,并使用buffWriter输出
	 * <br>这里的p(qi|q)=1/subquery_count
	 * @param mainQueryId
	 * @param buffWriter
	 * @throws IOException 
	 */
	public void diversifyRes(int mainQueryId,double lanta,BufferedWriter buffWriter) throws IOException{
		ArrayList<Result> array_result=null;
		//把mainQueryId对应的结果存入array_result中
		array_result=MapRes_mainQuery.get("queryId="+mainQueryId);
		//获取mainQueryId对应的子查询数
		int subquery_count=Integer.parseInt(MapQ.get(String.valueOf(mainQueryId)));
		
		//遍历array_result中的对象,并把最后的多样化得分score_div存入result对象的score中
		double score_div=0;
		double rel_score=0;//存储文档d的相关性得分
		double div_score=0;//存储文档d的多样化得分
		Result result=null;
		String mapKey=null;
		String mapValue=null;
		int queryId=0;//存储子查询的编号(完整编号)
		//存储p(qi|q),这里p(qi|q)采用了统一值
		double p_qi_q=1.0/Double.parseDouble(String.valueOf(subquery_count));
		
		//若array_result==null,不处理,返回
		if(array_result==null) return;
		
		for(int i=0;i<array_result.size();i++){
			//给rel_score,div_score置空
			rel_score=0;
			div_score=0;
			
			//获取文档d的信息
			result=array_result.get(i);
			//计算文档d的相关性得分
			rel_score=(1-lanta)*result.getScore();
			//计算文档d的多样化得分,在每个子查询的结果中寻找文档,若含有此文档,计算得分
			for(int j=0;j<subquery_count;j++){
				//建立子查询编号,格式为mainQueryId*100+(j+1)
				queryId=mainQueryId*100+(j+1);
				mapKey="queryId="+queryId+"\tdocId="+result.getDocno();
				//若MapRes_subquery含有mapKey
				if(MapRes_subquery.containsKey(mapKey)){
					mapValue=MapRes_subquery.get(mapKey);
					//mapValue信息的格式为score=x
					div_score=div_score+p_qi_q*Double.parseDouble(mapValue.split("=")[1]);
				}
			}
			//乘上系数lanta
			div_score=div_score*lanta;
			//计算score_div,并存入result的score变量中
			score_div=rel_score+div_score;
			result.setScore(score_div);
		}
		
		
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
		//输出array_result信息,此处不关闭buffWriter
		for(int i=0;i<array_result.size();i++){
			result=array_result.get(i);
			//设置result的runId为CombSUM
			result.setRunId("CombSUM");
			buffWriter.write(result.getTempLine());
		}
	}
	/**
	 * 产生多样化重排结果
	 * @param output
	 * @param qid_first
	 * @throws IOException 
	 */
	public void process_diversification(String output,int qid_first) throws IOException{
		BufferedWriter buffWriter=null;
		double lanta=0;
		//改变lanta值,产生对应的文件,共改变times次
		int time_total=11;
		
		for(int time=0;time<time_total;time++){
			//建立buffWriter对象,产生output+lanta文件
			buffWriter=new BufferedWriter(new FileWriter(output+lanta));
			//对input_main检索结果进行多样化重排
			for(int qid=qid_first;qid<=(qid_first+49);qid++){
				//若qid为95,100,不执行
				if (qid == 95 || qid == 100) continue;
				//对qid的检索结果进行多样化重排,并使用buffWriter输出结果
				diversifyRes(qid,lanta,buffWriter);
			}
			buffWriter.close();
			System.out.println(output+lanta+"文件已产生..");
			//lanta增加0.1
			lanta=utils.Arith.add(lanta, 0.1);
		}
		System.out.println("对input_main检索结果进行多样化重排,已完成..");
	}
	
	/**
	 * 对input_main的检索结果进行多样化重排,产生多样化文件
	 * @param path_main
	 * @param path_sub
	 * @param output
	 * @param qid_first
	 * @throws Exception
	 */
	public static void generate_combSUMFile(String input_mainQuery,String input_subquery,String output,int qid_first) throws Exception{
		//建立combSUM对象
		CombSUM combSUM=new CombSUM();
		//把input_mainQuery文件中的信息存入MapRes_mainQuery
		combSUM.processResult_mainQuery(input_mainQuery);
		//把input_subquery文件的信息存入MapRes_subquery中
		combSUM.processResult_subquery(input_subquery);
		//分析input_subquery,得到queryId subquery_count键值对,存入MapQ中
		combSUM.generateMapQ(input_subquery);
		//产生多样化重排结果
		combSUM.process_diversification(output,qid_first);
		
		System.out.println("产生combSUM多样化结果,已完成..");
	}
	
	/**
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException{
		
	}

}
