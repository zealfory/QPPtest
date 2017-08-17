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
	 * 把input.CiirWikiRm的分数转为正数,找出一组查询中最小的分数low_s,然后Score(d)=Score(d)-low_s,<hr>
	 * 
	 * decimalFormat=new DecimalFormat("###.0000000000");
	 * 已改为bigDecimal.setScale(10, BigDecimal.ROUND_HALF_UP);
	 * @throws IOException 
	 */
	public static void makeInputPositive(String input,String output) throws IOException{
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		FileWriter fileWriter=null;
		double low_s=0;//一组查询中最小的分数
		double score=0;//临时存放input中的分数
		String tempLine=null;
		String[] terms=null;
		BigDecimal bigDecimal=null;//用于规范score,保留score小数点后x位
		
		fileReader=new FileReader(input);
		bufferedReader=new BufferedReader(fileReader);
		//获取low_s值
		while((tempLine=bufferedReader.readLine())!=null){
			terms=tempLine.split("\t| ");
			score=Double.parseDouble(terms[4]);
			if(low_s>score) low_s=score;
		}
		//关闭IO连接
		bufferedReader.close();
		//重新打开input文件,读每一行信息,更改分数,score(d)=score(d)-low_s,后写入output文件
		fileReader=new FileReader(input);
		bufferedReader=new BufferedReader(fileReader);
		fileWriter=new FileWriter(output);
		while((tempLine=bufferedReader.readLine())!=null){
			terms=tempLine.split("\t| ");
			score=Double.parseDouble(terms[4]);
			score=score-low_s;
			//重组tempLine,其中score保留小数点后x位,进行了四舍五入
			bigDecimal=BigDecimal.valueOf(score);
			bigDecimal=bigDecimal.setScale(10, BigDecimal.ROUND_HALF_UP);
			tempLine=terms[0]+"\t"+terms[1]+"\t"+terms[2]+"\t"+terms[3]+"\t"+bigDecimal.toString()+"\t"+terms[5]+"\n";
			fileWriter.write(tempLine);
		}
		//关闭IO连接
		fileWriter.close();
		bufferedReader.close();
	}
	/**
	 * 根据input文件,找出一组查询中文档分数的最大值score_max,接着更新所有文档的score,score_new=score/score_max。
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
		//把input文件的信息存入array_result中
		ArrayList<Result> array_result=new ArrayList<Result>();
		Result result=null;
		while((tempLine=buffReader.readLine())!=null){
			result=new Result(tempLine);
			array_result.add(result);
		}
		buffReader.close();
		//根据array_result,得到score_max
		double score_max=0;
		for(int i=0;i<array_result.size();i++){
			result=array_result.get(i);
			if(score_max<result.getScore()) score_max=result.getScore();
		}
		//使用score_max更新array_result中文档的分数,并输出至文件
		double score_new=0;
		for(int i=0;i<array_result.size();i++){
			result=array_result.get(i);
			score_new=result.getScore()/score_max;
			result.setScore(score_new);
			buffWriter.write(result.getTempLine());
		}
		buffWriter.close();
		System.out.println("更新所有文档的score,score_new=score/score_max,已完成..");
	}
	/**
	 * 提取input文件的信息,每个queryId下提取1000篇文档,形成新的input_extracted文件,并替换原input文件
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
		int n_preQueryId=0;//临时记录preQueryId下已选择的文档数
		
		fileReader=new FileReader(input);
		bufferedReader=new BufferedReader(fileReader);
		fileWriter=new FileWriter(input+"_extracted");
		
		while((tempLine=bufferedReader.readLine())!=null){
			terms=tempLine.split(" |\t");
			//若preQueryId为空时
			if(preQueryId==null) preQueryId=terms[0];
			//当preQueryId和terms[0]相同时,分析n_preQueryId值
			if(preQueryId.equalsIgnoreCase(terms[0])){
				//若n_preQueryId>=1000,直接进入下一次循环
				if(n_preQueryId>=1000) continue;
				//若n_preQueryId<1000时
				fileWriter.write(tempLine+"\n");
				n_preQueryId++;
			}
			//当preQueryId和terms[0]不相同时,重置n_preQueryId,更新preQueryId,分析tempLine信息
			if(!preQueryId.equalsIgnoreCase(terms[0])){
				n_preQueryId=0;
				preQueryId=terms[0];
				fileWriter.write(tempLine+"\n");
				n_preQueryId++;
			}
		}
		fileWriter.close();
		bufferedReader.close();
		//使用input+"_extracted"文件替换input文件
		File file1=null;
		File file2=null;
		file1=new File(input);
		file1.delete();
		file2=new File(input+"_extracted");
		file2.renameTo(file1);
		System.out.println("删除input文件的一部分信息,已完成..");
	}
	/**
	 * summary.ndeval.runId文件中某些queryId对应的信息为空,
	 * 这里直接删除input.runId文件中与之对应queryId下的所有文档,
	 * 这样省略了在计算pearson,kendall前删除predictor.runId文件中多出的信息这一步骤
	 * @param input 这里使用了runId.txt文件,这样可以一次更新多个input.runId文件
	 * @throws IOException 
	 */
	public static void updateInput_byDeletingTopic(String runId_txt) throws IOException{
		String parentFilePath="";//runId.txt所在文件夹的路径
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		String tempLine=null;
		String input=null;
		//填写需删除的topic
		String topic_deleted="95\t100";//删除topic为95,100下的文档
		String[] terms_topic=null;
		//下面定义处理input文件的变量
		FileReader fileReader_2=null;
		BufferedReader buffReader_2=null;
		FileWriter fileWriter=null;
		String topic=null;
		File file1=null;
		File file2=null;
		boolean isMatched=false;//分析tempLine的topic是否为需删除的topic
		
		parentFilePath=runId_txt.replaceFirst("runId\\.txt", "");
		terms_topic=topic_deleted.split(" |\t");
		fileReader=new FileReader(runId_txt);
		bufferedReader=new BufferedReader(fileReader);
		//遍历runId.txt中runId
		while((tempLine=bufferedReader.readLine())!=null){
			//处理runId对应的input文件
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
				isMatched=false;//重置isMatched为false
			}
			fileWriter.close();
			buffReader_2.close();
			//使用input+"_topicDeleted"文件替换input文件
			file1=new File(input);
			file1.delete();
			file2=new File(input+"_topicDeleted");
			file2.renameTo(file1);
		}
		bufferedReader.close();
		System.out.println("根据runId.txt文件,删除input文件中一些topic下的文档,已完成..");
	}
	
	/**
	 * 1、对input文件中的文档进行排序: sort by topic, then by score, and
	 * then by docno, which is the traditional sort order for TREC runs
	 * 2、生成rank信息,rank值为1,2,3,...,n。
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
		//把tempLine的信息存入array_result中
		while((tempLine=bufferedReader.readLine())!=null){
			result=new Result(tempLine);
			array_result.add(result);
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
		//输出array_result信息
		fileWriter=new FileWriter(input+"_rebuilt");
		for(int i=0;i<array_result.size();i++){
			result=array_result.get(i);
			fileWriter.write(result.getTempLine());
		}
		fileWriter.close();
		bufferedReader.close();
		System.out.println("对input文件中的文档进行排序并更新rank信息,已完成..");
	}
	
	public static void main(String[] args) throws IOException{
		String filePath=null;
		//filePath="./webTrackDiversity2010/input.cmuComb10";
		//makeInputPositive("./webTrackAdhoc2014/input.CiirSub1","./webTrackAdhoc2014/input.CiirSub1.inversed");
		//makeInputPositive(filePath,filePath+".inversed");
		//System.out.println("把input.runId的分数转为正数,Score(d)=Score(d)-low_s,存入文件,已完成..");
		//extractInput("./webTrack2011/input.CWIIAt5b5");
		extractInput("C:/Users/1/Desktop/web2011diversity_20170306_选择新系统/input.CWIcIAt5b1");
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
	 * 可以删除这个方法
	 * 
	 * 根据input文件,找出一组查询中文档分数的最大值score_max,
	 * 接着更新所有文档的score,score_new=score/score_max
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
 * 对Result类的对象进行排序: sort by topic, then by score, and
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
