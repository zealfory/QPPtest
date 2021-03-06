package newPredictor;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

public class SMVWIG_2 {
	private int k1=0;//SMV方面的截断值
	private int k2=0;//WIG方面的截断值
	
	public int getK1() {
		return k1;
	}
	public void setK1(int k1) {
		this.k1 = k1;
	}
	public int getK2() {
		return k2;
	}
	public void setK2(int k2) {
		this.k2 = k2;
	}

	public double uMean(double[] scores){
		double sum=0;
		for(int i=0;i<k1;i++)
			sum=sum+scores[i];
		return sum/k1;
	}
	
	public double mean(double[] scores){
		double sum=0;
		int length=scores.length;
		for(int i=0;i<length;i++)
			sum=sum+scores[i];
		return sum/length;
	}
	
	public double computeSMVWIG_2(double[] scores){
		double u=uMean(scores);
		double scoreD=mean(scores);
		double sum1=0;
		double sum2=0;
		
		for(int i=0;i<k1;i++){
			sum1=sum1+Math.abs(Math.log(scores[i]/u));
		}
		sum1=sum1/k1;
		for(int i=0;i<k2;i++){
			sum2=sum2+scores[i]-scoreD;
		}
		sum2=sum2/k2;
		return sum1*sum2;
	}
	
	/**
	 * 根据input.runId计算每个query的SMVWIG_2值, 并将SMVWIG_2Score存入文件 
	 *  
	 * */
	public void getSMVWIG_2Scores(String input,String output){
		FileReader fileReader=null;
		LineNumberReader lineNumberReader=null;
		FileWriter fileWriter=null;
		String tempLine=null;
		String[] terms=null;//分析tempLine
		String preQueryId=null;
		ArrayList<Double> arrayList=new ArrayList<Double>();
		double score=0;//临时存放terms[4]的score
		double[] scores=null;//临时存放一个query对应的score数组
		int scoreCount=0;//临时存放score数组的长度
		double SMVWIG_2Score=0;//临时存放一个query的SMVWIG_2值
		
		try{
			fileReader=new FileReader(input);
			lineNumberReader=new LineNumberReader(fileReader);
			fileWriter=new FileWriter(output,false);	
			while((tempLine=lineNumberReader.readLine())!=null){
				terms=tempLine.split("\t| ");
				//起初preQueryId为null
				if(preQueryId==null) preQueryId=terms[0];
				//queryId相同,存入score
				if(preQueryId.equalsIgnoreCase(terms[0])){
					score=Double.parseDouble(terms[4]);
					arrayList.add(score);
				}
				//queryId不同,计算preQueryId的SMVWIG_2Score,写入文件,清空arrayList信息,处理terms信息
				if(!preQueryId.equalsIgnoreCase(terms[0])){
					//把arrayList转化为double数组
					scoreCount=arrayList.size();
					scores=new double[scoreCount];
					for(int i=0;i<scoreCount;i++)
						scores[i]=arrayList.get(i);
					//调用computSMVWIG_2()计算此query的SMVWIG_2值
					SMVWIG_2Score=computeSMVWIG_2(scores);
					//把queryId和SMVWIG_2Score写入文件
					fileWriter.write("queryId:\t"+preQueryId+"\tSMVWIG_2:\t"+SMVWIG_2Score+"\n");
					//清空arrayList
					arrayList.clear();
					//开始处理terms信息
					preQueryId=terms[0];
					score=Double.parseDouble(terms[4]);
					arrayList.add(score);
				}
			}
			//最后queryId对应的scores未处理,计算其SMVWIG_2Score,并写入文件
			//把arrayList转化为double数组
			scoreCount=arrayList.size();
			scores=new double[scoreCount];
			for(int i=0;i<scoreCount;i++)
				scores[i]=arrayList.get(i);
			//调用computSMVWIG_2()计算此query的SMVWIG_2值
			SMVWIG_2Score=computeSMVWIG_2(scores);
			//把queryId和SMVWIG_2Score写入文件
			fileWriter.write("queryId:\t"+preQueryId+"\tSMVWIG_2:\t"+SMVWIG_2Score+"\n");
		}catch(IOException e){
			System.err.println("处理数据出错!");
			e.printStackTrace();
		}finally{
			try {
				fileWriter.close();
				lineNumberReader.close();
			} catch (IOException e) {
				System.err.println("关闭IO连接错误!");
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args){
		
	}
	

}
