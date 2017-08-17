package newPredictor;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
/**
 * 在predictor.SMV类的基础上删去了ln的绝对值,1/score(D),1/k。
 * 
 * @author 1
 *
 */
public class SMV_2 {
	private int k=100;//k为截断参数
	
	public int getK() {
		return k;
	}
	public void setK(int k) {
		this.k = k;
	}
	public double mean(double[] score){
		int length=score.length;
		double sum=0;
		for(int i=0;i<length;i++)
			sum=sum+score[i];
		return sum/length;
	}
	public double uMean(double[] score){
		double uMean=0;
		double sum=0;
		for(int i=0;i<k;i++){
			sum=sum+score[i];
		}
		uMean=sum/k;
		return uMean;
	}
	/**
	 * 去掉了Math.abs(),1/k,1/score(D)<br>
	 * 根据score数组和截断参数k计算SMV_2
	 * */
	public double computeSMV_2(double[] score){
		double u=uMean(score);
		double sum=0;
		for(int i=0;i<k;i++){
			sum=sum+score[i]*Math.log(score[i]/u);
		}
		return sum;
	}
	/**
	 * 根据input.pircRB04t3计算每个query的SMV_2值, 并将SMV_2Score存入文件
	 * 
	 * */
	public void getSMV_2Scores(String input, String output) {
		FileReader fileReader = null;
		LineNumberReader lineNumberReader = null;
		FileWriter fileWriter = null;
		String tempLine = null;
		String[] terms = null;// 分析tempLine
		String preQueryId = null;
		ArrayList<Double> arrayList = new ArrayList<Double>();
		double score = 0;// 临时存放terms[4]的score
		double[] scores = null;// 临时存放一个query对应的score数组
		int scoreCount = 0;// 临时存放score数组的长度
		double SMV_2Score = 0;// 临时存放一个query的SMV_2值
		int k_original=k;//存储起初的k值
		
		try {
			fileReader = new FileReader(input);
			lineNumberReader = new LineNumberReader(fileReader);
			fileWriter = new FileWriter(output, false);
			while ((tempLine = lineNumberReader.readLine()) != null) {
				terms = tempLine.split("\t| ");
				// 起初preQueryId为null
				if (preQueryId == null)
					preQueryId = terms[0];
				// queryId相同,存入score
				if (preQueryId.equalsIgnoreCase(terms[0])) {
					score = Double.parseDouble(terms[4]);
					arrayList.add(score);
				}
				// queryId不同,计算preQueryId的SMV_2Score,写入文件,清空arrayList信息,处理terms信息
				if (!preQueryId.equalsIgnoreCase(terms[0])) {
					// 把arrayList转化为double数组
					scoreCount = arrayList.size();
					scores = new double[scoreCount];
					for (int i = 0; i < scoreCount; i++)
						scores[i] = arrayList.get(i);
					//若此查询下的文档数scoreCount小于k,把k设为scoreCount
					if(scoreCount<k) k=scoreCount;
					// 调用computSMV_2()计算此query的SMV_2值
					SMV_2Score = computeSMV_2(scores);
					// 把queryId和SMV_2Score写入文件
					fileWriter.write("queryId:\t" + preQueryId + "\tSMV_2:\t" + SMV_2Score + "\n");
					//若k不为初始k值,把k设为初始值k_original
					if(k!=k_original) k=k_original;
					// 清空arrayList
					arrayList.clear();
					// 开始处理terms信息
					preQueryId = terms[0];
					score = Double.parseDouble(terms[4]);
					arrayList.add(score);
				}
			}
			// 最后queryId对应的scores未处理,计算其SMV_2Score,并写入文件
			// 把arrayList转化为double数组
			scoreCount = arrayList.size();
			scores = new double[scoreCount];
			for (int i = 0; i < scoreCount; i++)
				scores[i] = arrayList.get(i);
			//若此查询下的文档数scoreCount小于k,把k设为scoreCount
			if(scoreCount<k) k=scoreCount;
			// 调用computSMV_2()计算此query的SMV_2值
			SMV_2Score = computeSMV_2(scores);
			// 把queryId和SMV_2Score写入文件
			fileWriter.write("queryId:\t" + preQueryId + "\tSMV_2:\t" + SMV_2Score + "\n");
		} catch (IOException e) {
			System.err.println("处理数据出错!");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.close();
				lineNumberReader.close();
			} catch (IOException e) {
				System.err.println("关闭IO连接错误!");
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		
	}
}
