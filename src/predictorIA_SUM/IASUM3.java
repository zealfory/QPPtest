package predictorIA_SUM;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * ��IASUM��������޸�,ʹ��p(di|q),p(di|qj),����1/i,1/(60.0+rankij),
 * <br>ʹ����IASUM���Struct_sum��
 * @author 1
 *
 */
public class IASUM3 {
	//�洢resultList_final��Ϣ
	public static ArrayList<Struct_sum> ArraySum=null;
	//�洢resultList_subquery��Ϣ
	public static HashMap<String,String> MapRes_subquery=null;
	//�洢mainQueryId,subQuery_count��Ϣ
	public static HashMap<String,String> MapQ=null;
	/**
	 * �Ӳ�ѯsubquery�Ľضϲ���
	 */
	public static int k_subquery=1000;
	/**
	 * ����ѯmainQuery�Ľضϲ���
	 */
	public static int k_mainQuery=100;
	
	/**
	 * ����input.runId�ļ�,
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
			//��tempLineΪ���ַ���,������
			if(tempLine.equals("")) continue;
			terms=tempLine.split("\t| ");
			//�ļ��г�������Ϊ0���ĵ�ʱ,��ʾ�û�,���˳�����
			if(terms[3].equalsIgnoreCase("0")){
				System.out.println(input+"�ļ��г�������Ϊ0���ĵ�\n"+tempLine+"�������˳�..");
				System.exit(0);
			}
			//��ʼʱ,preQueryIdΪ��
			if(preQueryId==null){
				preQueryId=terms[0];
			}
			//preQueryId��terms[0]��ͬʱ,����countֵ
			if(preQueryId.equalsIgnoreCase(terms[0])){
				//count>=100ʱ,������
				if(count>=k_mainQuery) continue;
				//count<100ʱ
				//��tempLine��Ϣ����array_structSum��
				struct_sum=new Struct_sum(tempLine);
				array_structSum.add(struct_sum);
				count++;
			}
			//preQueryId��terms[0]��ͬʱ,��countֵ��λ,����preQueryIdֵ,������terms��Ϣ
			if(!preQueryId.equalsIgnoreCase(terms[0])){
				count=0;//countֵ��λ
				preQueryId=terms[0];
				//��tempLine��Ϣ����array_structSum��
				struct_sum=new Struct_sum(tempLine);
				array_structSum.add(struct_sum);
				count++;
			}
		}
		bufferedReader.close();
		return array_structSum;
	}
	/**
	 * ����input_subquery�ļ�,
	 * �˷�������input_subquery����Ϣ����hashMap��,��ʽΪ(queryId=x\tdocId=x, rank=x\tscore=x)��
	 * k_subquery: �Ӳ�ѯsubquery�Ľضϲ���, n_subquery: subquery��Ӧ���Ѷ��ĵ���
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
		int n_subquery=0;//subquery��Ӧ���Ѷ��ĵ���

		fileReader=new FileReader(input);
		bufferedReader=new BufferedReader(fileReader);

		while((tempLine=bufferedReader.readLine())!=null){
			terms=tempLine.split("\t| ");
			//preQueryIdΪ��ʱ,
			if(preQueryId==null) preQueryId=terms[0];
			//preQueryId��terms[0]��ͬʱ
			if(preQueryId.equalsIgnoreCase(terms[0])){
				//��¼subquery�µ��Ѷ�����
				n_subquery++;
				//n_subquery>k_subqueryʱ,������terms��Ϣ
				if(n_subquery>k_subquery) continue;
				//n_subquery<=k_subqueryʱ,��tempLine��Ϣ����mapResult_subquery
				mapKey="queryId="+terms[0]+"\tdocId="+terms[2];
				mapValue="rank="+terms[3]+"\tscore="+terms[4];
				mapResult_subquery.put(mapKey, mapValue);
			}
			//preQueryId��terms[0]����ͬʱ,����n_subqueryΪ1,����preQueryId,����terms��Ϣ
			if(!preQueryId.equalsIgnoreCase(terms[0])){
				//����n_subquery=1
				n_subquery=1;
				preQueryId=terms[0];
				//��tempLine��Ϣ����mapResult_subquery
				mapKey="queryId="+terms[0]+"\tdocId="+terms[2];
				mapValue="rank="+terms[3]+"\tscore="+terms[4];
				mapResult_subquery.put(mapKey, mapValue);
			}
		}
		bufferedReader.close();
		return mapResult_subquery;
	}
	/**
	 * ����input_subquery,�õ�queryId subquery_count��ֵ��,����queryMap��
	 * @throws IOException 
	 */
	public static HashMap<String,String> generateQueryMap(String input) throws IOException{
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		HashMap<String,String> queryMap=new HashMap<String,String>();
		String tempLine=null;
		String[] terms=null;
		String preQueryId=null;//�洢������queryId
		String mainQueryId=null;//�洢mainQueryId
		int subQuery_count=0;//��ʱ�洢mainQueryId�²�ͬsubQueryId�ĸ���

		fileReader=new FileReader(input);
		bufferedReader=new BufferedReader(fileReader);

		while((tempLine=bufferedReader.readLine())!=null){
			terms=tempLine.split("\t| ");
			//preQueryIdΪ��ʱ,��preQueryId,mainQueryId,subQueryId����ֵ,����subQuery_count
			if(preQueryId==null){
				preQueryId=terms[0];
				mainQueryId=terms[0].split("-")[0];
				subQuery_count++;
			}
			//preQueryId��terms[0]���ʱ
			if(preQueryId.equalsIgnoreCase(terms[0])){
				continue;
			}
			//��preQueryId��terms[0]�����ʱ
			if(!preQueryId.equalsIgnoreCase(terms[0])){
				//��mainQueryId��terms[0].split("-")[0]���
				if(mainQueryId.equalsIgnoreCase(terms[0].split("-")[0])){
					preQueryId=terms[0];
					subQuery_count++;
				}
				//��mainQueryId��terms[0].split("-")[0]�����,��mainQueryId��Ϣ����queryMap,��λsubQuery_count,����terms��Ϣ
				if(!mainQueryId.equalsIgnoreCase(terms[0].split("-")[0])){
					queryMap.put(mainQueryId, String.valueOf(subQuery_count));
					subQuery_count=0;//subQuery_count��λ
					preQueryId=terms[0];
					mainQueryId=terms[0].split("-")[0];
					subQuery_count++;
				}
			}
		}
		//����mainQueryId��subQuery_count��û����queryMap
		queryMap.put(mainQueryId, String.valueOf(subQuery_count));
		bufferedReader.close();
		return queryMap;
	}

	/**
	 * �����IASUM3���,����output_later�ļ�
	 * @throws IOException 
	 * 
	 */
	public static void generateIASUM3(String output_middle,String output_later) throws IOException{
		//����ArraySum,��struct_sum��sum_di��ֵ
		Struct_sum struct_sum=null;
		//
		//��06/12,��n_preQuery�������Ϊdouble����
		double[] n_preQuery=null;//�Ӳ�ѯ����,�洢�Ѽ����ĵ���ĳ�Ӳ�ѯ��صĴ���
		String queryId=null;
		String preQueryId=null;
		int subQuery_count=0;//����ѯ�µ��Ӳ�ѯ��

		for(int i=0;i<ArraySum.size();i++){
			struct_sum=ArraySum.get(i);
			queryId=struct_sum.getQueryId();
			//��preQueryIdΪnullʱ,��preQueryId,subQuery_count��ֵ,��ʼ��n_preQuery
			if(preQueryId==null) {
				preQueryId=queryId;
				subQuery_count=Integer.parseInt(MapQ.get(preQueryId));
				n_preQuery=new double[subQuery_count];
			}
			//��preQueryId��queryId��ͬʱ,
			if(preQueryId.equals(queryId)){
				update_sumdi(n_preQuery,struct_sum);
			}
			//��preQueryId��queryId����ͬʱ,����preQueryId,subQuery_count,n_preQuery,������struct_sum
			if(!preQueryId.equals(queryId)){
				preQueryId=queryId;
				subQuery_count=Integer.parseInt(MapQ.get(preQueryId));
				n_preQuery=new double[subQuery_count];
				update_sumdi(n_preQuery,struct_sum);
			}
		}

		//��ArraySum��Ϣ�����output_middle�ļ�,���û�����IA_SUM�㷨������״��
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

		//����ArraySum��queryId��Ӧ��IA_SUMֵ
		double score_queryId=0;
		ArrayList<Struct_queryIdScore> arrayList_queryIdScore=new ArrayList<Struct_queryIdScore>();
		Struct_queryIdScore struct_queryIdScore=null;
		//����preQueryId����,����preQueryIdΪ��
		preQueryId=null;

		for(int i=0;i<ArraySum.size();i++){
			struct_sum=ArraySum.get(i);
			queryId=struct_sum.getQueryId();

			//preQueryIdΪ��ʱ,��preQueryId��ֵ
			if(preQueryId==null) preQueryId=queryId;
			//��preQueryId��queryId��ͬʱ,
			if(preQueryId.equals(queryId)){
				//ʹ��*struct_sum.getScore()����1/rank
				score_queryId=score_queryId+struct_sum.getSum_di()*struct_sum.getScore();
			}
			//��preQueryId��queryId����ͬʱ,����preQueryId��IASUMֵ,����arrayList_queryIdScore,
			//����preQueryId,����score_queryIdΪ0,������struct_sum��Ϣ
			if(!preQueryId.equals(queryId)){
				score_queryId=score_queryId/Integer.parseInt(MapQ.get(preQueryId));
				
				struct_queryIdScore=new Struct_queryIdScore();
				struct_queryIdScore.setQueryId(Integer.parseInt(preQueryId));
				struct_queryIdScore.setScore(score_queryId);
				arrayList_queryIdScore.add(struct_queryIdScore);
				//����preQueryId,����score_queryIdΪ0,������struct_sum��Ϣ
				preQueryId=queryId;
				score_queryId=0;
				//*struct_sum.getScore()
				score_queryId=score_queryId+struct_sum.getSum_di()*struct_sum.getScore();
				
			}
		}
		//����preQueryId��IA_SUMֵ��û�д���arrayList_queryIdScore
		
		score_queryId=score_queryId/Integer.parseInt(MapQ.get(preQueryId));
		
		struct_queryIdScore=new Struct_queryIdScore();
		struct_queryIdScore.setQueryId(Integer.parseInt(preQueryId));
		struct_queryIdScore.setScore(score_queryId);
		arrayList_queryIdScore.add(struct_queryIdScore);

		//��arrayList_queryIdScore��Ϣ����output_later�ļ���
		fileWriter=new FileWriter(output_later);
		buffWriter=new BufferedWriter(fileWriter);

		for(int i=0;i<arrayList_queryIdScore.size();i++){
			struct_queryIdScore=arrayList_queryIdScore.get(i);
			tempLine="queryId:\t"+struct_queryIdScore.getQueryId()+"\tIASUM3:\t"+struct_queryIdScore.getScore()+"\n";
			buffWriter.write(tempLine);
		}
		buffWriter.close();
		System.out.println("�����IASUM3���,����output_later�ļ�,�����..");
	}
	/**
	 * ��06/12,��n_preQuery int�������Ϊdouble������
	 * ��struct_sum��sum_di��ֵ
	 * @param struct_sum
	 */
	public static void update_sumdi(double[] n_preQuery,Struct_sum struct_sum){
		//alphaֵ
		//
		double alpha=0.1;
		//
		//
		String mapKey=null;
		String mainQueryId=null;
		double sum_di=0;
		//��ʱ�洢MapRes_subquery��mapKey��Ӧ��score��Ϣ
		String score=null;

		mainQueryId=struct_sum.getQueryId();
		
		//����struct_sum��sum_diֵ
		for(int i=0;i<n_preQuery.length;i++){
			mapKey="queryId="+mainQueryId+"-"+(i+1)+"\tdocId="+struct_sum.getDocId();
			if(MapRes_subquery.containsKey(mapKey)){
				score=MapRes_subquery.get(mapKey).split("\t")[1].split("=")[1];
					
				sum_di=sum_di+Math.pow(1-alpha, n_preQuery[i])*Double.parseDouble(score);
				n_preQuery[i]++;
			}
		}
		
		//����struct_sum��sum_di
		struct_sum.setSum_di(sum_di);
	}
	
	/**
	 * ����IASUM3Scores
	 * @throws IOException 
	 * 
	 */
	public static void getIASUM3Scores(String input_runId,String input_subquery,String output_middle,String output_later) throws IOException{
		//����input.runId�ļ�
		ArraySum=processInput(input_runId);
		System.out.println("����input.runId�ļ�,�����..");

		//����input_subquery�ļ�,�˷�������input_subquery����Ϣ����hashMap��,��ʽΪ(queryId=x\tdocId=x, rank=x)��
		MapRes_subquery=processResult_subquery(input_subquery);
		System.out.println("����input_subquery�ļ�,�����..");

		//����input_subquery,�õ�queryId subquery_count��ֵ��,����queryMap��
		MapQ=generateQueryMap(input_subquery);
		System.out.println("����input_subquery�ļ�,�õ�queryId subquery_count��ֵ��,����queryMap��,�����..");

		//�����IA-SUM���,�����ļ�
		generateIASUM3(output_middle,output_later);
		System.out.println("����IASUM3Score,�����..");
	}
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args){

	}

}
