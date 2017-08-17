package newPredictor;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

public class NQCWIG {
	private int k=0;
	
	public int getK() {
		return k;
	}
	public void setK(int k) {
		this.k = k;
	}

	public double uMean(double[] scores){
		double sum=0;
		for(int i=0;i<k;i++)
			sum=sum+scores[i];
		return sum/k;
	}
	
	public double mean(double[] scores){
		double sum=0;
		int length=scores.length;
		for(int i=0;i<length;i++)
			sum=sum+scores[i];
		return sum/length;
	}
	
	public double computeNQCWIG(double[] scores){
		double u=uMean(scores);
		double scoreD=mean(scores);
		double sum=0;
		double numerator=0;
		
		for(int i=0;i<k;i++){
			sum=sum+(scores[i]-u)*(scores[i]-u)*(scores[i]-scoreD)*(scores[i]-scoreD);
		}
		numerator=Math.sqrt(sum/k);
		return numerator/Math.abs(scoreD);
	}
	
	/**
	 * 根据input.runId计算每个query的NQCWIG值, 并将NQCWIGScore存入文件 
	 *  
	 * */
	public void getNQCWIGScores(String input,String output){
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
		double NQCWIGScore=0;//临时存放一个query的NQCWIG值
		
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
				//queryId不同,计算preQueryId的NQCWIGScore,写入文件,清空arrayList信息,处理terms信息
				if(!preQueryId.equalsIgnoreCase(terms[0])){
					//把arrayList转化为double数组
					scoreCount=arrayList.size();
					scores=new double[scoreCount];
					for(int i=0;i<scoreCount;i++)
						scores[i]=arrayList.get(i);
					//调用computNQCWIG()计算此query的NQCWIG值
					NQCWIGScore=computeNQCWIG(scores);
					//把queryId和NQCWIGScore写入文件
					fileWriter.write("queryId:\t"+preQueryId+"\tNQCWIG:\t"+NQCWIGScore+"\n");
					//清空arrayList
					arrayList.clear();
					//开始处理terms信息
					preQueryId=terms[0];
					score=Double.parseDouble(terms[4]);
					arrayList.add(score);
				}
			}
			//最后queryId对应的scores未处理,计算其NQCWIGScore,并写入文件
			//把arrayList转化为double数组
			scoreCount=arrayList.size();
			scores=new double[scoreCount];
			for(int i=0;i<scoreCount;i++)
				scores[i]=arrayList.get(i);
			//调用computNQCWIG()计算此query的NQCWIG值
			NQCWIGScore=computeNQCWIG(scores);
			//把queryId和NQCWIGScore写入文件
			fileWriter.write("queryId:\t"+preQueryId+"\tNQCWIG:\t"+NQCWIGScore+"\n");
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
