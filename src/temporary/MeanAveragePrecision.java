package temporary;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Mean Average Precision
 *
 */
public class MeanAveragePrecision {
	
	/**
	 * �洢docId��Ӧ�������
	 *
	 */
	public static class DocRel{
		public String subTopic;//������
		public String docId;//�ĵ�Id
		public String relevancy;//�����
		
	}
	/**
	 * �洢input.runId�ļ�
	 */
	public static class InputRun{
		
	} 
	
	
	
	
	
	/**
	 * װ��completeqrels
	 * @param args
	 * @throws IOException 
	 */
	public static HashMap<String,ArrayList<DocRel>> loadCompleteQrel(String input) throws IOException{
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		DocRel docRel=null;
		ArrayList<DocRel> arrayDocRel=new ArrayList<DocRel>();
		HashMap<String,ArrayList<DocRel>> qrels=new HashMap<String,ArrayList<DocRel>>();
		String tempLine=null;
		String[] terms=null;
		String preQueryId=null;//��ǰ��queryId
		
		fileReader=new FileReader(input);
		bufferedReader=new BufferedReader(fileReader);
		while((tempLine=bufferedReader.readLine())!=null){
			terms=tempLine.split(" |\t");
			//��ʼʱpreQueryIdΪnull
			if(preQueryId==null) preQueryId=terms[0];
			
			//preQueryId��terms[0]��ͬʱ,�洢terms��Ϣ
			if(preQueryId.equalsIgnoreCase(terms[0])){
				docRel=new DocRel();
				docRel.subTopic=terms[1];
				docRel.docId=terms[2];
				docRel.relevancy=terms[3];
				//��docRel����arrayDocRel��
				arrayDocRel.add(docRel);
			}
			
			//preQueryId��terms[0]����ͬʱ,�洢preQueryId��Ӧ����Ϣ,����arrayDocRel����,�洢��ǰ��terms��Ϣ
			if(!preQueryId.equalsIgnoreCase(terms[0])){
				//�洢preQueryId��Ӧ����Ϣ
				qrels.put(preQueryId, arrayDocRel);
				//������ϢarrayDocRel
				arrayDocRel=new ArrayList<DocRel>();
				
				//�洢��ǰ��terms��Ϣ
				preQueryId=terms[0];
				docRel=new DocRel();
				docRel.subTopic=terms[1];
				docRel.docId=terms[2];
				docRel.relevancy=terms[3];
				//��docRel����arrayDocRel��
				arrayDocRel.add(docRel);
			}
			
		}
		//����preQueryId��Ӧ����Ϣû�д洢,�洢preQueryId��Ӧ����Ϣ
		qrels.put(preQueryId, arrayDocRel);
		//�ر�IO����
		bufferedReader.close();
		return qrels;
	}
	
	
	
	
	
	/**
	 * ����AP
	 */
	public static void computeAveragePrecision(){
		HashMap<String,Double> apMap=new HashMap<String,Double>();
		
		
		
		
	}
	
	

}
