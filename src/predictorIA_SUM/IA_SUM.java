package predictorIA_SUM;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * ��������ڴ洢resultList_final��Ϣ,
 * <br>��20170309,������score����,IASUM3Ҳʹ��Struct_sum��
 * @author 1
 *
 */
class Struct_sum{
	String queryId;
	String docId;
	int rank;
	double score;
	double sum_di;
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
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public double getSum_di() {
		return sum_di;
	}
	public void setSum_di(double sum_di) {
		this.sum_di = sum_di;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	
	public Struct_sum(String tempLine){
		String[] terms=tempLine.split(" |\t");
		queryId=terms[0];
		docId=terms[2];
		rank=Integer.parseInt(terms[3]);
		score=Double.parseDouble(terms[4]);
		sum_di=0;
	}
}

public class IA_SUM {
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
	 * �˷�������input_subquery����Ϣ����hashMap��,��ʽΪ(queryId=x\tdocId=x, rank=x)��
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
				mapValue="rank="+terms[3];
				mapResult_subquery.put(mapKey, mapValue);
			}
			//preQueryId��terms[0]����ͬʱ,����n_subqueryΪ1,����preQueryId,����terms��Ϣ
			if(!preQueryId.equalsIgnoreCase(terms[0])){
				//����n_subquery=1
				n_subquery=1;
				preQueryId=terms[0];
				//��tempLine��Ϣ����mapResult_subquery
				mapKey="queryId="+terms[0]+"\tdocId="+terms[2];
				mapValue="rank="+terms[3];
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
	 * �����IA-SUM���,����output_later�ļ�
	 * @throws IOException 
	 * 
	 */
	public static void generateIA_SUM(String output_middle,String output_later) throws IOException{
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
				score_queryId=score_queryId+struct_sum.getSum_di()/struct_sum.getRank();				
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
				
				score_queryId=score_queryId+struct_sum.getSum_di()/struct_sum.getRank();
				
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
			tempLine="queryId:\t"+struct_queryIdScore.getQueryId()+"\tIA_SUM:\t"+struct_queryIdScore.getScore()+"\n";
			buffWriter.write(tempLine);
		}
		buffWriter.close();
		System.out.println("�����IA-SUM���,����output_later�ļ�,�����..");
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
		String rank=null;
		double sum_di=0;

		mainQueryId=struct_sum.getQueryId();
		
		//����struct_sum��sum_diֵ
		for(int i=0;i<n_preQuery.length;i++){
			mapKey="queryId="+mainQueryId+"-"+(i+1)+"\tdocId="+struct_sum.getDocId();
			if(MapRes_subquery.containsKey(mapKey)){
				rank=MapRes_subquery.get(mapKey).split("=")[1];
				sum_di=sum_di+Math.pow(1-alpha, n_preQuery[i])/(60+Double.parseDouble(rank));
				n_preQuery[i]++;
			}
		}
		
		//����struct_sum��sum_di
		struct_sum.setSum_di(sum_di);
	}
	/**
	 * ��06/13,ʹ����y=kx+b�������
	 * ʹ��probability_subquery����1.0/(60+rank)
	 * @return
	 */
	public static double getProbability_subquery(double rank){
		double probability=0;//�洢����probability_subquery
		double part1=0;
		double part2=0;
		
		part1=1-0.2*Math.log(1+rank);
		if(part1<0) part1=0;
		part2=25.92/(60+rank);
		
		probability=0.5*(part1+part2);
		return probability;		
	}
	/**
	 * ����IA_SUMScores
	 * @throws IOException 
	 * 
	 */
	public static void getIA_SUMScores(String input_runId,String input_subquery,String output_middle,String output_later) throws IOException{
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
		generateIA_SUM(output_middle,output_later);
		System.out.println("����IA_SUMScore,�����..");
	}
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args){

	}

}
