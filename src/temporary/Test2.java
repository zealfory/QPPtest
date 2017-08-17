package temporary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Test2 {
	
	/**
	 * ��input.CiirWikiRm�ķ���תΪ����,�ҳ�һ���ѯ����С�ķ���low_s,Ȼ��Score(d)=Score(d)-low_s,<hr>
	 * 
	 * decimalFormat=new DecimalFormat("###.0000000000");
	 * �Ѹ�ΪbigDecimal.setScale(10, BigDecimal.ROUND_HALF_UP);
	 * @throws IOException 
	 */
	public static void makeInputPositive(String input,String output) throws IOException{
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		FileWriter fileWriter=null;
		double low_s=0;//һ���ѯ����С�ķ���
		double score=0;//��ʱ���input�еķ���
		String tempLine=null;
		String[] terms=null;
		BigDecimal bigDecimal=null;//���ڹ淶score,����scoreС�����xλ
		
		fileReader=new FileReader(input);
		bufferedReader=new BufferedReader(fileReader);
		//��ȡlow_sֵ
		while((tempLine=bufferedReader.readLine())!=null){
			terms=tempLine.split("\t| ");
			score=Double.parseDouble(terms[4]);
			if(low_s>score) low_s=score;
		}
		//�ر�IO����
		bufferedReader.close();
		//���´�input�ļ�,��ÿһ����Ϣ,���ķ���,score(d)=score(d)-low_s,��д��output�ļ�
		fileReader=new FileReader(input);
		bufferedReader=new BufferedReader(fileReader);
		fileWriter=new FileWriter(output);
		while((tempLine=bufferedReader.readLine())!=null){
			terms=tempLine.split("\t| ");
			score=Double.parseDouble(terms[4]);
			score=score-low_s;
			//����tempLine,����score����С�����xλ,��������������
			bigDecimal=BigDecimal.valueOf(score);
			bigDecimal=bigDecimal.setScale(10, BigDecimal.ROUND_HALF_UP);
			tempLine=terms[0]+"\t"+terms[1]+"\t"+terms[2]+"\t"+terms[3]+"\t"+bigDecimal.toString()+"\t"+terms[5]+"\n";
			fileWriter.write(tempLine);
		}
		//�ر�IO����
		fileWriter.close();
		bufferedReader.close();
	}
	/**
	 * ����input�ļ�,�ҳ�һ���ѯ���ĵ����������ֵscore_max,���Ÿ��������ĵ���score,score_new=score/score_max��
	 * 
	 * @param input
	 * @throws IOException 
	 */
	public static void makeInput_lowerThanOne(String input) throws IOException{
		FileReader fileReader=null;
		BufferedReader buffReader=null;
		FileWriter fileWriter=null;
		BufferedWriter buffWriter=null;
		String tempLine=null;
		String[] terms=null;
		
		fileReader=new FileReader(input);
		buffReader=new BufferedReader(fileReader);
		fileWriter=new FileWriter(input+"_lowerThanOne");
		buffWriter=new BufferedWriter(fileWriter);
		//��input�ļ�����Ϣ����array_result��
		ArrayList<Result> array_result=new ArrayList<Result>();
		Result result=null;
		while((tempLine=buffReader.readLine())!=null){
			result=new Result(tempLine);
			array_result.add(result);
		}
		buffReader.close();
		//����array_result,�õ�score_max
		double score_max=0;
		for(int i=0;i<array_result.size();i++){
			result=array_result.get(i);
			if(score_max<result.getScore()) score_max=result.getScore();
		}
		//ʹ��score_max����array_result���ĵ��ķ���,��������ļ�
		double score_new=0;
		for(int i=0;i<array_result.size();i++){
			result=array_result.get(i);
			score_new=result.getScore()/score_max;
			result.setScore(score_new);
			buffWriter.write(result.getTempLine());
		}
		buffWriter.close();
		System.out.println("���������ĵ���score,score_new=score/score_max,�����..");
	}
	/**
	 * ��ȡinput�ļ�����Ϣ,ÿ��queryId����ȡ1000ƪ�ĵ�,�γ��µ�input_extracted�ļ�,���滻ԭinput�ļ�
	 * @param input
	 * @throws IOException 
	 */
	public static void extractInput(String input) throws IOException{
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		FileWriter fileWriter=null;
		String tempLine=null;
		String[] terms=null;
		String preQueryId=null;
		int n_preQueryId=0;//��ʱ��¼preQueryId����ѡ����ĵ���
		
		fileReader=new FileReader(input);
		bufferedReader=new BufferedReader(fileReader);
		fileWriter=new FileWriter(input+"_extracted");
		
		while((tempLine=bufferedReader.readLine())!=null){
			terms=tempLine.split(" |\t");
			//��preQueryIdΪ��ʱ
			if(preQueryId==null) preQueryId=terms[0];
			//��preQueryId��terms[0]��ͬʱ,����n_preQueryIdֵ
			if(preQueryId.equalsIgnoreCase(terms[0])){
				//��n_preQueryId>=1000,ֱ�ӽ�����һ��ѭ��
				if(n_preQueryId>=1000) continue;
				//��n_preQueryId<1000ʱ
				fileWriter.write(tempLine+"\n");
				n_preQueryId++;
			}
			//��preQueryId��terms[0]����ͬʱ,����n_preQueryId,����preQueryId,����tempLine��Ϣ
			if(!preQueryId.equalsIgnoreCase(terms[0])){
				n_preQueryId=0;
				preQueryId=terms[0];
				fileWriter.write(tempLine+"\n");
				n_preQueryId++;
			}
		}
		fileWriter.close();
		bufferedReader.close();
		//ʹ��input+"_extracted"�ļ��滻input�ļ�
		File file1=null;
		File file2=null;
		file1=new File(input);
		file1.delete();
		file2=new File(input+"_extracted");
		file2.renameTo(file1);
		System.out.println("ɾ��input�ļ���һ������Ϣ,�����..");
	}
	/**
	 * summary.ndeval.runId�ļ���ĳЩqueryId��Ӧ����ϢΪ��,
	 * ����ֱ��ɾ��input.runId�ļ�����֮��ӦqueryId�µ������ĵ�,
	 * ����ʡ�����ڼ���pearson,kendallǰɾ��predictor.runId�ļ��ж������Ϣ��һ����
	 * @param input ����ʹ����runId.txt�ļ�,��������һ�θ��¶��input.runId�ļ�
	 * @throws IOException 
	 */
	public static void updateInput_byDeletingTopic(String runId_txt) throws IOException{
		String parentFilePath="";//runId.txt�����ļ��е�·��
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		String tempLine=null;
		String input=null;
		//��д��ɾ����topic
		String topic_deleted="95\t100";//ɾ��topicΪ95,100�µ��ĵ�
		String[] terms_topic=null;
		//���涨�崦��input�ļ��ı���
		FileReader fileReader_2=null;
		BufferedReader buffReader_2=null;
		FileWriter fileWriter=null;
		String topic=null;
		File file1=null;
		File file2=null;
		boolean isMatched=false;//����tempLine��topic�Ƿ�Ϊ��ɾ����topic
		
		parentFilePath=runId_txt.replaceFirst("runId\\.txt", "");
		terms_topic=topic_deleted.split(" |\t");
		fileReader=new FileReader(runId_txt);
		bufferedReader=new BufferedReader(fileReader);
		//����runId.txt��runId
		while((tempLine=bufferedReader.readLine())!=null){
			//����runId��Ӧ��input�ļ�
			input=parentFilePath+tempLine;
			fileReader_2=new FileReader(input);
			buffReader_2=new BufferedReader(fileReader_2);
			fileWriter=new FileWriter(input+"_topicDeleted");
			while((tempLine=buffReader_2.readLine())!=null){
				topic=tempLine.split(" |\t")[0];
				for(int i=0;i<terms_topic.length;i++){
					if(topic.equals(terms_topic[i])) isMatched=true;
				}
				if(isMatched==false)
					fileWriter.write(tempLine+"\n");
				isMatched=false;//����isMatchedΪfalse
			}
			fileWriter.close();
			buffReader_2.close();
			//ʹ��input+"_topicDeleted"�ļ��滻input�ļ�
			file1=new File(input);
			file1.delete();
			file2=new File(input+"_topicDeleted");
			file2.renameTo(file1);
		}
		bufferedReader.close();
		System.out.println("����runId.txt�ļ�,ɾ��input�ļ���һЩtopic�µ��ĵ�,�����..");
	}
	
	/**
	 * 1����input�ļ��е��ĵ���������: sort by topic, then by score, and
	 * then by docno, which is the traditional sort order for TREC runs
	 * 2������rank��Ϣ,rankֵΪ1,2,3,...,n��
	 * @param input
	 * @throws IOException 
	 */
	public static void rebuildInput(String input) throws IOException{
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		FileWriter fileWriter=null;
		String tempLine=null;
		ArrayList<Result> array_result=new ArrayList<Result>();
		Result result=null;
		
		fileReader=new FileReader(input);
		bufferedReader=new BufferedReader(fileReader);
		//��tempLine����Ϣ����array_result��
		while((tempLine=bufferedReader.readLine())!=null){
			result=new Result(tempLine);
			array_result.add(result);
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
		//���array_result��Ϣ
		fileWriter=new FileWriter(input+"_rebuilt");
		for(int i=0;i<array_result.size();i++){
			result=array_result.get(i);
			fileWriter.write(result.getTempLine());
		}
		fileWriter.close();
		bufferedReader.close();
		System.out.println("��input�ļ��е��ĵ��������򲢸���rank��Ϣ,�����..");
	}
	
	public static void main(String[] args) throws IOException{
		String filePath=null;
		//filePath="./webTrackDiversity2010/input.cmuComb10";
		//makeInputPositive("./webTrackAdhoc2014/input.CiirSub1","./webTrackAdhoc2014/input.CiirSub1.inversed");
		//makeInputPositive(filePath,filePath+".inversed");
		//System.out.println("��input.runId�ķ���תΪ����,Score(d)=Score(d)-low_s,�����ļ�,�����..");
		//extractInput("./webTrack2011/input.CWIIAt5b5");
		extractInput("C:/Users/1/Desktop/web2011diversity_20170306_ѡ����ϵͳ/input.CWIcIAt5b1");
		//rebuildInput("./webTrackDiversity2010/input.UAMSD10ancPR");
		//updateInput_byDeletingTopic("./webTrackDiversity2010/runId.txt");
		/*rebuildInput("./resultsOfTRECs_0628/input.CnQst2");
		rebuildInput("./resultsOfTRECs_0628/input.ETHme1");
		rebuildInput("./resultsOfTRECs_0628/input.fub01be2");
		rebuildInput("./resultsOfTRECs_0628/input.pircRB04t3");
		rebuildInput("./resultsOfTRECs_0628/input.thutd5");
		rebuildInput("./resultsOfTRECs_0628/input.uogTBQEL");
		*/
		//wait_forDelete();
	}
	/**
	 * ����ɾ���������
	 * 
	 * ����input�ļ�,�ҳ�һ���ѯ���ĵ����������ֵscore_max,
	 * ���Ÿ��������ĵ���score,score_new=score/score_max
	 * @throws IOException 
	 */
	public static void wait_forDelete() throws IOException{
		String input=null;
		input="./resultsOfTRECs_0628/input.CnQst2";
		makeInput_lowerThanOne(input);
		
		input="./resultsOfTRECs_0628/input.ETHme1";
		makeInput_lowerThanOne(input);
		
		input="./resultsOfTRECs_0628/input.fub01be2";
		makeInput_lowerThanOne(input);
		
		input="./resultsOfTRECs_0628/input.pircRB04t3";
		makeInput_lowerThanOne(input);
		
		input="./resultsOfTRECs_0628/input.thutd5";
		makeInput_lowerThanOne(input);
		
		input="./resultsOfTRECs_0628/input.uogTBQEL";
		makeInput_lowerThanOne(input);
	}

}
class Result{
	public int topic;
	public String docno;
	public int rank;
	public double score;
	public String runId;
	
	public int getTopic() {
		return topic;
	}
	public void setTopic(int topic) {
		this.topic = topic;
	}
	public String getDocno() {
		return docno;
	}
	public void setDocno(String docno) {
		this.docno = docno;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getRunId() {
		return runId;
	}
	public void setRunId(String runId) {
		this.runId = runId;
	}
	public Result(String tempLine){
		String[] terms=null;
		terms=tempLine.split(" |\t");
		topic=Integer.parseInt(terms[0]);
		docno=terms[2];
		rank=Integer.parseInt(terms[3]);
		score=Double.parseDouble(terms[4]);
		runId=terms[5];
	}
	public String getTempLine(){
		String tempLine="";
		tempLine=topic+"\tQ0\t"+docno+"\t"+rank+"\t"+score+"\t"+runId+"\n";
		return tempLine;
	}
}
/**
 * ��Result��Ķ����������: sort by topic, then by score, and
 * then by docno, which is the traditional sort order for TREC runs
 * @author 1
 *
 */
class Result_compare implements Comparator<Result>{
	
	@Override
	public int compare(Result arg0, Result arg1) {
		if(arg0.getTopic()<arg1.getTopic())
			return -1;
		if(arg0.getTopic()>arg1.getTopic())
			return 1;
		if(arg0.getScore()<arg1.getScore())
			return 1;
		if(arg0.getScore()>arg1.getScore())
			return -1;
		return arg0.getDocno().compareTo(arg1.getDocno());
	}

}
