package clueweb09spam;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import utils.Result;
import utils.Result_compare;

public class Spamming {
	
	/**
	 * ɾ��input�ļ���percentile<70���ĵ���<br>
	 * <br>��20170309,ʹ����threshold����,����70,ɾ��input�ļ���percentile < threshold���ĵ���<br>
	 * ��input�ļ�����hashMap��,�ٷ���clueweb09spam.Fusion�ļ���Ϣ,
	 * ��percentile<70,ɾ��hashMap�ж�Ӧ����Ϣ��
	 * ��map_docId��Ϣ����array_result��,��array_result��Ϣ��������,
	 * ����rank��Ϣ,rankֵΪ1,2,3,...,n,�����array_result��Ϣ��
	 * @throws IOException 
	 * 
	 */
	public static void remove_spam(String input,double threshold) throws IOException{
		FileReader fileReader=null;
		BufferedReader buffReader=null;
		FileWriter fileWriter=null;
		String tempLine=null;
		String[] terms=null;
		//hashMap��ֵ�Եĸ�ʽΪ(docId array_line)
		HashMap<String,ArrayList<String>> map_docId=new HashMap<String,ArrayList<String>>();
		ArrayList<String> array_line=null;
		//��input�ļ�����hashMap��
		fileReader=new FileReader(input);
		buffReader=new BufferedReader(fileReader);
		while((tempLine=buffReader.readLine())!=null){
			terms=tempLine.split(" |\t");
			//��map_docId����terms[2],����array_line,����tempLine
			if(!map_docId.containsKey(terms[2])){
				array_line=new ArrayList<String>();
				array_line.add(tempLine);
				map_docId.put(terms[2], array_line);
			}else{
				//��map_docId����terms[2],ֱ�Ӵ���tempLine
				array_line=map_docId.get(terms[2]);
				array_line.add(tempLine);
			}
		}
		buffReader.close();
		//��ȡclueweb09spam.Fusion�ļ�,��map_docId��ĳ�ĵ���percentile<70,ɾ��map_docId�ж�Ӧ�ļ�ֵ��
		double percentile=0;
		fileReader=new FileReader("./clueweb09spam/clueweb09spam.Fusion");
		buffReader=new BufferedReader(fileReader);
		
		while((tempLine=buffReader.readLine())!=null){
			//tempLine����ϢΪpercentile docId
			terms=tempLine.split(" |\t");
			//��map_docId���м�ֵterms[1],�����ĵ���percentileֵ
			if(map_docId.containsKey(terms[1])){
				percentile=Double.parseDouble(terms[0]);
				if(percentile<threshold) map_docId.remove(terms[1]);
			}
		}
		buffReader.close();
		//��map_docId��Ϣ����array_result��
		array_line=null;//����array_line����,array_line��Ϊ��
		ArrayList<Result> array_result=new ArrayList<Result>();
		Result result=null;
		Set<Entry<String,ArrayList<String>>> set=null;
		Iterator<Entry<String,ArrayList<String>>> it=null;
		Entry<String,ArrayList<String>> entry=null;
		set=map_docId.entrySet();
		it=set.iterator();
		while(it.hasNext()){
			entry=it.next();
			array_line=entry.getValue();
			for(int i=0;i<array_line.size();i++){
				tempLine=array_line.get(i);
				result=new Result(tempLine);
				array_result.add(result);
			}
		}
		/**
		 * 1����array_result��������: sort by topic, then by score, and
		 * then by docno, which is the traditional sort order for TREC runs
		 * 2������rank��Ϣ,rankֵΪ1,2,3,...,n��
		 * 
		 */
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
		//���array_result��Ϣ
		fileWriter=new FileWriter(input+"_spammed");
		for(int i=0;i<array_result.size();i++){
			result=array_result.get(i);
			fileWriter.write(result.getTempLine());
		}
		fileWriter.close();
		System.out.println("ɾ��input�ļ���percentile<threshold���ĵ�,�����..");
	}
	
	/**
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException{
		
	}

}
