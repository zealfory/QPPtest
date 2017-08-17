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
 * �˴�ʹ��CombSUM��Ϊ�����������㷨��CombSUM: s(d,q)=(1-lanta)*P(d|q)+lanta*SUM(P(qi|q)*p(d|qi))
 * @author 1
 *
 */
public class CombSUM {
	
	//�洢mainQuery��resultList��Ϣ
	public HashMap<String,ArrayList<Result>> MapRes_mainQuery=null;
	//�洢subquery��resultList_subquery��Ϣ
	public HashMap<String,String> MapRes_subquery=null;
	//�洢����ѯ�µ��Ӳ�ѯ��,<mainQuery,subquery_count>
	public HashMap<String,String> MapQ=null;
	//CombSUM�㷨��ϵ��
	public double lanta=0;
	
	/**
	 * ��input_mainQuery�ļ��е���Ϣ����MapRes_mainQuery,
	 * ���ʽΪ["queryId="+preQueryId, array_result]��ֵ��
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
		//ʵ����MapRes_mainQuery
		MapRes_mainQuery=new HashMap<String,ArrayList<Result>>();
		
		while((tempLine=buffReader.readLine())!=null){
			//��tempLineΪ���ַ���,ִ����һѭ��
			if(tempLine.equals("")) continue;
			terms=tempLine.split(" |\t");
			//��preQueryId==null
			if(preQueryId==null){
				preQueryId=terms[0];
				array_result=new ArrayList<Result>();
			}
			//��preQueryId��terms[0]��ͬ
			if(preQueryId.equals(terms[0])){
				result=new Result(tempLine);
				array_result.add(result);
			}
			//��preQueryId��terms[0]����ͬ,��array_result����MapRes_mainQuery��,����preQueryId,��array_result����ֵ,����tempLine��Ϣ
			if(!preQueryId.equals(terms[0])){
				//��array_result����MapRes_mainQuery��
				MapRes_mainQuery.put("queryId="+preQueryId, array_result);
				//����preQueryId,����array_result����,����tempLine��Ϣ
				preQueryId=terms[0];
				array_result=new ArrayList<Result>();
				result=new Result(tempLine);
				array_result.add(result);
			}
		}
		buffReader.close();
		//����array_result��û�д���MapRes_mainQuery��
		MapRes_mainQuery.put("queryId="+preQueryId, array_result);
		System.out.println("��input_mainQuery�ļ��е���Ϣ����MapRes_mainQuery,�����..");
	}
	/**
	 * ��input_subquery�ļ�����Ϣ����MapRes_subquery��,
	 * ��ʽΪ["queryId="+queryId+"\tdocId="+docId, "score="+score]
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
			//��mapKey,mapValue����MapRes_subquery��
			MapRes_subquery.put(mapKey, mapValue);
		}
		buffReader.close();
		System.out.println("��input_subquery�ļ�����Ϣ����MapRes_subquery��,�����..");
	}
	
	/**
	 * ����input_subquery,�õ�queryId subquery_count��ֵ��,����MapQ��
	 * @throws IOException 
	 */
	public void generateMapQ(String input_subquery) throws IOException{
		
		BufferedReader buffReader=null;
		String tempLine=null;
		String[] terms=null;
		String preQueryId=null;//�洢������queryId
		String mainQueryId=null;//�洢mainQueryId
		int subquery_count=0;//��ʱ�洢mainQueryId�²�ͬsubqueryId�ĸ���
		
		buffReader=new BufferedReader(new FileReader(input_subquery));
		//����MapQ����
		MapQ=new HashMap<String,String>();
		
		while((tempLine=buffReader.readLine())!=null){
			terms=tempLine.split("\t| ");
			//preQueryIdΪ��ʱ,��preQueryId,mainQueryId����ֵ,����subquery_count
			if(preQueryId==null){
				preQueryId=terms[0];
				//terms[0]�Ĳ�ѯ��ʽΪmainQueryId*100+No. of subquery
				mainQueryId=terms[0].substring(0, terms[0].length()-2);
				subquery_count++;
			}
			//preQueryId��terms[0]���ʱ
			if(preQueryId.equalsIgnoreCase(terms[0])){
				continue;
			}
			//��preQueryId��terms[0]�����ʱ
			if(!preQueryId.equalsIgnoreCase(terms[0])){
				//��mainQueryId��terms[0].substring(0, terms[0].length()-2))���
				if(mainQueryId.equals(terms[0].substring(0, terms[0].length()-2))){
					preQueryId=terms[0];
					subquery_count++;
				}
				//��mainQueryId��terms[0].substring(0, terms[0].length()-2)�����,��mainQueryId��Ϣ����MapQ,��λsubquery_count,����terms��Ϣ
				if(!mainQueryId.equals(terms[0].substring(0, terms[0].length()-2))){
					MapQ.put(mainQueryId, String.valueOf(subquery_count));
					subquery_count=0;//subquery_count��λ
					preQueryId=terms[0];
					mainQueryId=terms[0].substring(0, terms[0].length()-2);
					subquery_count++;
				}
			}
		}
		//����mainQueryId��subquery_count��û����MapQ
		MapQ.put(mainQueryId, String.valueOf(subquery_count));
		buffReader.close();
	}
	
	/**
	 * ��mainQueryId��Ӧ�ļ���������ж���������,��ʹ��buffWriter���
	 * <br>�����p(qi|q)=1/subquery_count
	 * @param mainQueryId
	 * @param buffWriter
	 * @throws IOException 
	 */
	public void diversifyRes(int mainQueryId,double lanta,BufferedWriter buffWriter) throws IOException{
		ArrayList<Result> array_result=null;
		//��mainQueryId��Ӧ�Ľ������array_result��
		array_result=MapRes_mainQuery.get("queryId="+mainQueryId);
		//��ȡmainQueryId��Ӧ���Ӳ�ѯ��
		int subquery_count=Integer.parseInt(MapQ.get(String.valueOf(mainQueryId)));
		
		//����array_result�еĶ���,�������Ķ������÷�score_div����result�����score��
		double score_div=0;
		double rel_score=0;//�洢�ĵ�d������Ե÷�
		double div_score=0;//�洢�ĵ�d�Ķ������÷�
		Result result=null;
		String mapKey=null;
		String mapValue=null;
		int queryId=0;//�洢�Ӳ�ѯ�ı��(�������)
		//�洢p(qi|q),����p(qi|q)������ͳһֵ
		double p_qi_q=1.0/Double.parseDouble(String.valueOf(subquery_count));
		
		//��array_result==null,������,����
		if(array_result==null) return;
		
		for(int i=0;i<array_result.size();i++){
			//��rel_score,div_score�ÿ�
			rel_score=0;
			div_score=0;
			
			//��ȡ�ĵ�d����Ϣ
			result=array_result.get(i);
			//�����ĵ�d������Ե÷�
			rel_score=(1-lanta)*result.getScore();
			//�����ĵ�d�Ķ������÷�,��ÿ���Ӳ�ѯ�Ľ����Ѱ���ĵ�,�����д��ĵ�,����÷�
			for(int j=0;j<subquery_count;j++){
				//�����Ӳ�ѯ���,��ʽΪmainQueryId*100+(j+1)
				queryId=mainQueryId*100+(j+1);
				mapKey="queryId="+queryId+"\tdocId="+result.getDocno();
				//��MapRes_subquery����mapKey
				if(MapRes_subquery.containsKey(mapKey)){
					mapValue=MapRes_subquery.get(mapKey);
					//mapValue��Ϣ�ĸ�ʽΪscore=x
					div_score=div_score+p_qi_q*Double.parseDouble(mapValue.split("=")[1]);
				}
			}
			//����ϵ��lanta
			div_score=div_score*lanta;
			//����score_div,������result��score������
			score_div=rel_score+div_score;
			result.setScore(score_div);
		}
		
		
		//��array_result��Ϣ��������
		Collections.sort(array_result, new Result_compare());
		//����array_result�ж���result��rank��Ϣ,rankֵΪ1,2,3,...,n
		int rank=1;
		int preTopic=0;
		for(int i=0;i<array_result.size();i++){
			result=array_result.get(i);
			//��preTopic==0,��preTopic��ֵ
			if(preTopic==0) preTopic=result.getTopic();
			//��preTopic��result.getTopic()��ͬ,����result��rank��Ϣ,Ȼ��rank++
			if(preTopic==result.getTopic()){
				result.setRank(rank);
				rank++;
			}
			//��preTopic��result.getTopic()����ͬ,����rank,preTopic,����result��rank��Ϣ,rank++
			if(preTopic!=result.getTopic()){
				rank=1;
				preTopic=result.getTopic();
				result.setRank(rank);
				rank++;
			}
		}
		//���array_result��Ϣ,�˴����ر�buffWriter
		for(int i=0;i<array_result.size();i++){
			result=array_result.get(i);
			//����result��runIdΪCombSUM
			result.setRunId("CombSUM");
			buffWriter.write(result.getTempLine());
		}
	}
	/**
	 * �������������Ž��
	 * @param output
	 * @param qid_first
	 * @throws IOException 
	 */
	public void process_diversification(String output,int qid_first) throws IOException{
		BufferedWriter buffWriter=null;
		double lanta=0;
		//�ı�lantaֵ,������Ӧ���ļ�,���ı�times��
		int time_total=11;
		
		for(int time=0;time<time_total;time++){
			//����buffWriter����,����output+lanta�ļ�
			buffWriter=new BufferedWriter(new FileWriter(output+lanta));
			//��input_main����������ж���������
			for(int qid=qid_first;qid<=(qid_first+49);qid++){
				//��qidΪ95,100,��ִ��
				if (qid == 95 || qid == 100) continue;
				//��qid�ļ���������ж���������,��ʹ��buffWriter������
				diversifyRes(qid,lanta,buffWriter);
			}
			buffWriter.close();
			System.out.println(output+lanta+"�ļ��Ѳ���..");
			//lanta����0.1
			lanta=utils.Arith.add(lanta, 0.1);
		}
		System.out.println("��input_main����������ж���������,�����..");
	}
	
	/**
	 * ��input_main�ļ���������ж���������,�����������ļ�
	 * @param path_main
	 * @param path_sub
	 * @param output
	 * @param qid_first
	 * @throws Exception
	 */
	public static void generate_combSUMFile(String input_mainQuery,String input_subquery,String output,int qid_first) throws Exception{
		//����combSUM����
		CombSUM combSUM=new CombSUM();
		//��input_mainQuery�ļ��е���Ϣ����MapRes_mainQuery
		combSUM.processResult_mainQuery(input_mainQuery);
		//��input_subquery�ļ�����Ϣ����MapRes_subquery��
		combSUM.processResult_subquery(input_subquery);
		//����input_subquery,�õ�queryId subquery_count��ֵ��,����MapQ��
		combSUM.generateMapQ(input_subquery);
		//�������������Ž��
		combSUM.process_diversification(output,qid_first);
		
		System.out.println("����combSUM���������,�����..");
	}
	
	/**
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException{
		
	}

}
