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
	 * ��input_subquery�ļ�����Map_rel��
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
		
		//��input_subquery�ļ�����Map_rel��
		String preQueryId=null;
		fileReader=new FileReader(input);
		buffReader=new BufferedReader(fileReader);
		while((tempLine=buffReader.readLine())!=null){
			terms=tempLine.split(" |\t");
			//��preQueryIdΪ��,��preQueryId��ֵ,����array_rel
			if(preQueryId==null){
				preQueryId=terms[0];
				array_rel=new ArrayList<Rel>();
			}
			//��preQueryId��terms[0]��ͬ,���tempLine��Ϣ����array_rel��
			if(preQueryId.equals(terms[0])){
				rel=new Rel(tempLine);
				array_rel.add(rel);
			}
			//��preQueryId��terms[0]����ͬʱ,��preQueryId��Ӧ��array_rel����Map_rel��,
			if(!preQueryId.equals(terms[0])){
				Map_rel.put(preQueryId, array_rel);
				//����preQueryId,����array_rel,������tempLine��Ϣ
				preQueryId=terms[0];
				array_rel=new ArrayList<Rel>();
				rel=new Rel(tempLine);
				array_rel.add(rel);
			}
		}
		//����preQueryId��Ӧ��array_rel��û�д���Map_rel��
		Map_rel.put(preQueryId, array_rel);
		buffReader.close();
	}
	/**
	 * ��2009-2012���input_subquery�ļ�����Map_rel��
	 * @throws IOException 
	 */
	public static void process_inputSubquery() throws IOException{
		String input=null;
		//��2009���input_subquery�ļ�����map_rel��
		input="./diversification/input.indri2009subquery_60addRank";
		store_inputSubquery(input);
		
		//��2010���input_subquery�ļ�����map_rel��
		input="./diversification/input.indri2010subquery_60addRank";
		store_inputSubquery(input);
		
		//��2011���input_subquery�ļ�����map_rel��
		input="./diversification/input.indri2011subquery_60addRank";
		store_inputSubquery(input);
		
		//��2012���input_subquery�ļ�����map_rel��
		input="./diversification/input.indri2012subquery_60addRank";
		store_inputSubquery(input);
	}
	/**
	 * ��qrels�ļ�����MapQ��,MapQ�м�ֵ�Եĸ�ʽΪ(queryId=\tdocId= relevant=)��
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
			//��relevant<=0ʱ,������һ����Ϣ
			if(Integer.parseInt(terms[3])<=0) continue;
			
			//queryId�Ĺ�����ʽΪterms[0]*100+terms[1]
			int queryId=Integer.parseInt(terms[0])*100+Integer.parseInt(terms[1]);
			int relevant=Integer.parseInt(terms[3]);
			//��relevant<0,��relevant��Ϊ0,��relevant>0,���relevant��Ϊ1
			if(relevant<0) relevant=0;
			if(relevant>0) relevant=1;
			mapKey="queryId="+String.valueOf(queryId)+"\tdocId="+terms[2];
			mapValue="relevant="+String.valueOf(relevant);
			//mapKey,mapValue����MapQ��
			MapQ.put(mapKey, mapValue);
		}
		buffReader.close();
	}
	/**
	 * ��2009-2012���qrels�ļ�����MapQ��
	 * @throws IOException 
	 */
	public static void process_qrel() throws IOException{
		String input=null;
		//��2009���qrels�ļ�����MapQ��
		input="./diversification/qrels.2009";
		store_qrel(input);

		//��2010���qrels�ļ�����MapQ��
		input="./diversification/qrels.2010";
		store_qrel(input);
		
		//��2011���qrels�ļ�����MapQ��
		input="./diversification/qrels.2011";
		store_qrel(input);
		
		//��2012���qrels�ļ�����MapQ��
		input="./diversification/qrels.2012";
		store_qrel(input);
	}
	/**
	 * ����MapQ��Map_rel��rel�����relevant��ֵ
	 */
	public static void completeMap_rel(){
		Set<Entry<String,ArrayList<Rel>>> set=null;
		Iterator<Entry<String,ArrayList<Rel>>> it=null;
		Entry<String,ArrayList<Rel>> entry=null;
		ArrayList<Rel> array_rel=null;
		String queryId=null;
		
		//����Map_rel,����MapQ��Map_rel��rel�����relevant��ֵ
		String mapKey=null;
		String mapValue=null;
		Rel rel=null;
		set=Map_rel.entrySet();
		it=set.iterator();
		while(it.hasNext()){
			entry=it.next();
			queryId=entry.getKey();
			array_rel=entry.getValue();
			//����array_rel
			for(int i=0;i<array_rel.size();i++){
				rel=array_rel.get(i);
				mapKey="queryId="+queryId+"\tdocId="+rel.getDocId();
				mapValue=MapQ.get(mapKey);
				if(mapValue!=null){
					rel.setRelevant(Integer.parseInt(mapValue.split("=")[1]));
				}
			}
		}
		System.out.println("����MapQ��Map_rel��rel�����relevant��ֵ,�����..");
	}
	/**
	 * ����Map_rel�е���Ϣ��ĳ��ѯ��Ӧ�ļ�������б�,
	 * ����λ��i���ĵ�,������ĵ�Ϊ����ĵ��ĸ��ʡ�
	 * @throws IOException 
	 */
	public static void generate_probability(String input) throws IOException{
		Set<Entry<String,ArrayList<Rel>>> set=null;
		Iterator<Entry<String,ArrayList<Rel>>> it=null;
		Entry<String,ArrayList<Rel>> entry=null;
		ArrayList<Rel> array_rel=null;
		
		int k_analy=500;//�������������ǰ500ƪ�ĵ�
		double[] prob=new double[k_analy];//����ĳλ�ô��ĵ�Ϊ��صĸ���
		int sum_rel=0;//��ʱ�洢ĳλ�ô����ĵ�Ϊ����ĵ�����Ŀ
		int sum_doc=0;//��ʱ�洢ĳλ�ô��ĵ�������
		//
		for(int i=0;i<k_analy;i++){
			set=Map_rel.entrySet();
			it=set.iterator();
			while(it.hasNext()){
				entry=it.next();
				array_rel=entry.getValue();
				//��array_rel������i��
				if(array_rel.size()>i){
					sum_rel=sum_rel+array_rel.get(i).getRelevant();
					sum_doc++;
				}
			}
			//��sum_doc>0
			if(sum_doc>0) prob[i]=Double.valueOf(sum_rel)/sum_doc;
			//��sum_rel,sum_doc��Ϊ0
			sum_rel=0;
			sum_doc=0;
		}
		//��prob����������ļ���
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
		System.out.println("�������б��iƪ�ĵ�Ϊ����ĵ��ĸ���,�����..");
	}
	/**
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException{
		//��2009-2012���input_subquery�ļ�����Map_rel��
		process_inputSubquery();
		
		//��2009-2012���qrels�ļ�����MapQ��
		process_qrel();
		
		//����MapQ��Map_rel��rel�����relevant��ֵ
		completeMap_rel();
		
		//�������б��iƪ�ĵ�Ϊ����ĵ��ĸ���
		String input=null;
		input="./diversification/probability_subquery.txt";
		generate_probability(input);
		System.out.println("���ʼ���,�����!");
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
