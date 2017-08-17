package newPredictor;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

public class NQCSMV_2 {
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
	
	public double computeNQCSMV_2(double[] scores){
		double u=uMean(scores);
		double scoreD=mean(scores);
		double sum1=0;
		double sum2=0;
		
		for(int i=0;i<k;i++){
			sum1=sum1+(scores[i]-u)*(scores[i]-u);
		}
		sum1=Math.sqrt(sum1/k);
		for(int i=0;i<k;i++){
			sum2=sum2+scores[i];
		}
		sum2=sum2/k/scoreD;
		return sum1*sum2;
	}
	
	/**
	 * 根据input.runId计算每个query的NQCSMV_2值, 并将NQCSMV_2Score存入文件 
	 *  
	 * */
	public void getNQCSMV_2Scores(String input,String output){
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
		double NQCSMV_2Score=0;//临时存放一个query的NQCSMV_2值
		
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
				//queryId不同,计算preQueryId的NQCSMV_2Score,写入文件,清空arrayList信息,处理terms信息
				if(!preQueryId.equalsIgnoreCase(terms[0])){
					//把arrayList转化为double数组
					scoreCount=arrayList.size();
					scores=new double[scoreCount];
					for(int i=0;i<scoreCount;i++)
						scores[i]=arrayList.get(i);
					//调用computNQCSMV_2()计算此query的NQCSMV_2值
					NQCSMV_2Score=computeNQCSMV_2(scores);
					//把queryId和NQCSMV_2Score写入文件
					fileWriter.write("queryId:\t"+preQueryId+"\tNQCSMV_2:\t"+NQCSMV_2Score+"\n");
					//清空arrayList
					arrayList.clear();
					//开始处理terms信息
					preQueryId=terms[0];
					score=Double.parseDouble(terms[4]);
					arrayList.add(score);
				}
			}
			//最后queryId对应的scores未处理,计算其NQCSMV_2Score,并写入文件
			//把arrayList转化为double数组
			scoreCount=arrayList.size();
			scores=new double[scoreCount];
			for(int i=0;i<scoreCount;i++)
				scores[i]=arrayList.get(i);
			//调用computNQCSMV_2()计算此query的NQCSMV_2值
			NQCSMV_2Score=computeNQCSMV_2(scores);
			//把queryId和NQCSMV_2Score写入文件
			fileWriter.write("queryId:\t"+preQueryId+"\tNQCSMV_2:\t"+NQCSMV_2Score+"\n");
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
