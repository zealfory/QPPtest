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
 * CFָcoverage frequency
 * @author 1
 *
 */
public class CF {
	//�洢resultList_final��Ϣ
	public static ArrayList<QueryDoc> array_queryDoc=null;
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
	 * ��ȡÿ��queryId��Ӧ��k_mainQueryƪ�ĵ�
	 */
	public static int k_mainQuery=20;
	
	/**
	 * ����input.runId�ļ�,
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
			//��tempLineΪ���ַ���,������
			if(tempLine.equals("")) continue;
			terms=tempLine.split("\t| ");
			//��ʼʱ,preQueryIdΪ��
			if(preQueryId==null){
				preQueryId=terms[0];
			}
			//preQueryId��terms[0]��ͬʱ,����countֵ
			if(preQueryId.equalsIgnoreCase(terms[0])){
				//count>=100ʱ,������
				if(count>=k_mainQuery) continue;
				//count<100ʱ
				//��tempLine��Ϣ����array_queryDoc��
				queryDoc=new QueryDoc(tempLine);
				array_queryDoc.add(queryDoc);
				count++;
			}
			//preQueryId��terms[0]��ͬʱ,��countֵ��λ,����preQueryIdֵ,������terms��Ϣ
			if(!preQueryId.equalsIgnoreCase(terms[0])){
				count=0;//countֵ��λ
				preQueryId=terms[0];
				//��tempLine��Ϣ����array_queryDoc��
				queryDoc=new QueryDoc(tempLine);
				array_queryDoc.add(queryDoc);
				count++;
			}
		}
		buffReader.close();
	}
	/**
	 * ����array_queryDoc��ĳqueryId�µ�n1��n2ƪ�ĵ��������ͼ���Ƕ�
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
		//����array_temp�е�n1��n2ƪ�ĵ�����subQuery����
		for(int i=n1;i<=n2;i++){
			queryDoc=array_temp.get(i);
			updateSubQuery(queryDoc,subQuery);
		}
		//������ͼ���Ƕ�
		int count=0;
		for(int i=0;i<subQuery.length;i++){
			if(subQuery[i]==1) count++;
		}
		//�ṩ(���)��ȷ�ĳ�������
		covRatio=Arith.div(count, subQuery.length);
		return covRatio;	
	}
	/**
	 * ����queryDoc��Ϣ����subQuery����
	 * @param subQuery
	 */
	public static void updateSubQuery(QueryDoc queryDoc,int[] subQuery){
		String mainQueryId=queryDoc.getQueryId();
		String docId=queryDoc.getDocId();
		String mapKey=null;
		
		for(int i=0;i<subQuery.length;i++){
			mapKey="queryId="+mainQueryId+"-"+(i+1)+"\tdocId="+docId;
			//��MapRes_subquery����mapKey,subQuery[i]��Ϊ1
			if(MapRes_subquery.containsKey(mapKey)){
				subQuery[i]=1;
			}
		}
	}
	
	/**
	 * ����input_subquery�ļ�,
	 * �˷�������input_subquery����Ϣ����hashMap��,��ʽΪ(queryId=x\tdocId=x, rank=x)��
	 * k_subquery: �Ӳ�ѯsubquery�Ľضϲ���, n_subquery: subquery��Ӧ���Ѷ��ĵ���
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
		int n_subquery=0;//subquery��Ӧ���Ѷ��ĵ���
		
		fileReader=new FileReader(input);
		buffReader=new BufferedReader(fileReader);

		while((tempLine=buffReader.readLine())!=null){
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
		buffReader.close();
		return mapResult_subquery;
	}
	/**
	 * ����input_subquery,�õ�queryId subquery_count��ֵ��,����queryMap��
	 * @throws IOException 
	 */
	public static HashMap<String,String> generateQueryMap(String input) throws IOException{
		FileReader fileReader=null;
		BufferedReader buffReader=null;
		HashMap<String,String> queryMap=new HashMap<String,String>();
		String tempLine=null;
		String[] terms=null;
		String preQueryId=null;//�洢������queryId
		String mainQueryId=null;//�洢mainQueryId
		int subQuery_count=0;//��ʱ�洢mainQueryId�²�ͬsubQueryId�ĸ���
		
		fileReader=new FileReader(input);
		buffReader=new BufferedReader(fileReader);

		while((tempLine=buffReader.readLine())!=null){
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
		buffReader.close();
		return queryMap;
	}
	/**
	 * ����array_queryDoc��ĳqueryId�µĵ�0��rƪ�ĵ���������������ͼ�Ĵ���,
	 * <br>������(r+1)
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
	 * ����coverageFre��ֵ
	 */
	public static double coverageFre(ArrayList<QueryDoc> array_temp){
		double covFre=0;
		
		for(int i=0;i<array_temp.size();i++){
			covFre=covFre+coverageTime(array_temp,i);
		}
		return covFre;
	}
	/**
	 * ����ÿ��queryId��Ӧ��coverageFrequencyֵ,������output�ļ�
	 * @throws IOException 
	 */
	public static void generateCF(String output) throws IOException{
		FileWriter fileWriter=null;
		BufferedWriter buffWriter=null;
		QueryDoc queryDoc=null;
		ArrayList<QueryDoc> array_temp=null;//�洢ĳqueryId�µ��ĵ�
		//�洢ÿ��queryId��Ӧ��array_temp
		ArrayList<ArrayList<QueryDoc>> arrays=new ArrayList<ArrayList<QueryDoc>>();
		String preQueryId=null;
		
		//��array_queryDoc�е��ĵ���queryId�������arrays��
		for(int i=0;i<array_queryDoc.size();i++){
			queryDoc=array_queryDoc.get(i);
			//��preQueryIdΪ��,��preQueryId����ֵ,����array_temp
			if(preQueryId==null){
				preQueryId=queryDoc.getQueryId();
				array_temp=new ArrayList<QueryDoc>();
			}
			//��preQueryId��queryDoc��queryId��ͬ,��queryDoc����array_temp��
			if(preQueryId.equals(queryDoc.getQueryId())){
				array_temp.add(queryDoc);
			}
			//��preQueryId��queryDoc��queryId����ͬ,��preQueryId��Ӧ��array_temp����arrays,����queryDoc
			if(!preQueryId.equals(queryDoc.getQueryId())){
				arrays.add(array_temp);
				//����preQueryId,����array_temp,����queryDoc����array_temp��
				preQueryId=queryDoc.getQueryId();
				array_temp=new ArrayList<QueryDoc>();
				array_temp.add(queryDoc);
			}
		}
		//����array_temp��û�д���arrays��
		arrays.add(array_temp);
		
		//����ÿ��queryId��Ӧ��CFֵ,������array_queryIdScore��
		array_temp=null;//����array_tempΪ��
		String queryId=null;
		double covFre=0;
		ArrayList<Struct_queryIdScore> array_queryIdScore=new ArrayList<Struct_queryIdScore>();
		Struct_queryIdScore struct_queryIdScore=null;
		for(int i=0;i<arrays.size();i++){
			array_temp=arrays.get(i);
			queryId=array_temp.get(0).getQueryId();
			covFre=coverageFre(array_temp);
			//��queryId��covFre��Ϣ����array_queryIdScore
			struct_queryIdScore=new Struct_queryIdScore();
			struct_queryIdScore.setQueryId(Integer.parseInt(queryId));
			struct_queryIdScore.setScore(covFre);
			array_queryIdScore.add(struct_queryIdScore);
		}
		
		//��array_queryIdScore��Ϣ����output�ļ���
		fileWriter=new FileWriter(output);
		buffWriter=new BufferedWriter(fileWriter);
		String tempLine=null;
		for(int i=0;i<array_queryIdScore.size();i++){
			struct_queryIdScore=array_queryIdScore.get(i);
			tempLine="queryId:\t"+struct_queryIdScore.getQueryId()+"\tCF:\t"+struct_queryIdScore.getScore()+"\n";
			buffWriter.write(tempLine);
		}
		buffWriter.close();
		System.out.println("�����CF���,����output�ļ�,�����..");
	}
	/**
	 * ���coverage frequency��Ԥ��ֵ
	 * @throws IOException 
	 * 
	 */
	public static void getCFScores(String input_runId,String input_subquery,String output) throws IOException{
		//����input.runId�ļ�
		processInput(input_runId);
		//����input_subquery�ļ�
		MapRes_subquery=processResult_subquery(input_subquery);
		//����input_subquery,�õ�queryId subquery_count��ֵ��,����queryMap��
		MapQ=generateQueryMap(input_subquery);
		//����ÿ��queryId��Ӧ��coverageFrequencyֵ,������output�ļ�
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
 * �洢resultList_final��Ϣ
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
