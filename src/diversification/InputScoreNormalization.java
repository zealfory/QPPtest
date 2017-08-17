package diversification;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import utils.Result;

public class InputScoreNormalization {
	
	/**
	 * MinMax normalization
	 * @param input
	 * @throws IOException 
	 */
	public static void normalization_minMax(String input) throws IOException{
		FileReader fileReader=null;
		BufferedReader buffReader=null;
		FileWriter fileWriter=null;
		BufferedWriter buffWriter=null;
		String tempLine=null;
		double minScore=0;
		double maxScore=0;
		double score=0;
		ArrayList<Result> array_result=new ArrayList<Result>();
		Result result=null;
		
		//��input�ļ���Ϣ����array_result��
		fileReader=new FileReader(input);
		buffReader=new BufferedReader(fileReader);
		while((tempLine=buffReader.readLine())!=null){
			result=new Result(tempLine);
			array_result.add(result);
		}
		buffReader.close();
		//��ȡÿ��topic��minScore,maxScore
		int preTopic=0;
		ArrayList<MinMax> array_minMax=new ArrayList<MinMax>();
		MinMax minMax=null;
		for(int i=0;i<array_result.size();i++){
			result=array_result.get(i);
			//preTopicΪ0ʱ,��preTopic,minScore,maxScore����ֵ
			if(preTopic==0){
				preTopic=result.getTopic();
				minScore=result.getScore();
				maxScore=result.getScore();
			}
			//preTopic��result.getTopic()��ͬʱ,����minScore,maxScore
			if(preTopic==result.getTopic()){
				if(minScore>result.getScore())
					minScore=result.getScore();
				if(maxScore<result.getScore())
					maxScore=result.getScore();
			}
			//preTopic��result.getTopic()����ͬʱ,����preTopic��Ӧ��minScore,maxScore,����result
			if(preTopic!=result.getTopic()){
				minMax=new MinMax(minScore,maxScore);
				array_minMax.add(minMax);
				//��preTopic,minScore,maxScore��ֵ
				preTopic=result.getTopic();
				minScore=result.getScore();
				maxScore=result.getScore();
			}
		}
		//����minScore,maxScore��δ����array_minMax,��������minScore,maxScore
		minMax=new MinMax(minScore,maxScore);
		array_minMax.add(minMax);
		//ʹ��MinMax�㷨�淶��array_result�ж����score
		preTopic=0;//����preTopic
		int count_topic=0;//array_minMax������
		for(int i=0;i<array_result.size();i++){
			result=array_result.get(i);
			//preTopicΪ0ʱ,��preTopic����ֵ
			if(preTopic==0) preTopic=result.getTopic();
			//preTopic��result.getTopic()��ͬʱ,����result��score
			if(preTopic==result.getTopic()){
				minMax=array_minMax.get(count_topic);
				score=(result.getScore()-minMax.getMinScore())/minMax.getDifference();
				result.setScore(score);
			}
			//preTopic��result.getTopic()����ͬʱ,����count_topic,preTopic,result��score
			if(preTopic!=result.getTopic()){
				count_topic++;
				preTopic=result.getTopic();
				minMax=array_minMax.get(count_topic);
				score=(result.getScore()-minMax.getMinScore())/minMax.getDifference();
				result.setScore(score);
			}
		}
		//��array_result�����ļ�
		fileWriter=new FileWriter(input+"_minMaxNormalized");
		buffWriter=new BufferedWriter(fileWriter);
		for(int i=0;i<array_result.size();i++){
			result=array_result.get(i);
			buffWriter.write(result.getTempLine());
		}
		buffWriter.close();
		System.out.println("minMax normalization, �����..");
	}
	/**
	 * 1.0/(60+rank) normalization
	 * @param input
	 * @throws IOException 
	 */
	public static void normalization_60addRank(String input) throws IOException{
		FileReader fileReader=null;
		BufferedReader buffReader=null;
		FileWriter fileWriter=null;
		BufferedWriter buffWriter=null;
		String tempLine=null;
		String[] terms=null;
		double score=0;
		
		fileReader=new FileReader(input);
		buffReader=new BufferedReader(fileReader);
		fileWriter=new FileWriter(input+"_60addRank");
		buffWriter=new BufferedWriter(fileWriter);
		while((tempLine=buffReader.readLine())!=null){
			terms=tempLine.split("  | \t| |\t");
			score=1.0/(60+Integer.parseInt(terms[3]));
			tempLine=terms[0]+"\t"+terms[1]+"\t"+terms[2]+"\t"+terms[3]+"\t"+String.valueOf(score)+"\t"+terms[5]+"\n";
			buffWriter.write(tempLine);
		}
		buffWriter.close();
		buffReader.close();
		System.out.println("score=1.0/(60+rank) �����淶�����..");
	}
	/**
	 * ����input�ļ��еķ���,newScore=0.58*score+0.02
	 * @throws IOException 
	 */
	public static void normalizeScores(String input) throws IOException{
		FileReader fileReader=null;
		BufferedReader buffReader=null;
		FileWriter fileWriter=null;
		BufferedWriter buffWriter=null;
		String tempLine=null;
		String[] terms=null;
		double score=0;
		
		fileReader=new FileReader(input);
		buffReader=new BufferedReader(fileReader);
		fileWriter=new FileWriter(input+"_normalized0.02to0.6");
		buffWriter=new BufferedWriter(fileWriter);
		
		while((tempLine=buffReader.readLine())!=null){
			terms=tempLine.split(" |\t");
			score=0.58*Double.parseDouble(terms[4])+0.02;
			terms[4]=String.valueOf(score);
			tempLine=terms[0]+"\t"+terms[1]+"\t"+terms[2]+"\t"+terms[3]+"\t"+terms[4]+"\t"+terms[5]+"\n";
			buffWriter.write(tempLine);
		}
		buffWriter.close();
		buffReader.close();
		System.out.println("����input�ļ��еķ���,newScore=0.58*score+0.02,�����..");
	}
	/**
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		//String input=null;
		/*input="./webTrack2011/input.indri2011";
		normalization_minMax(input);
		input="./webTrack2011/input.indri2011subquery";
		normalization_minMax(input);
		*/
		/*input="./resultsOfTRECs_0628/input.CnQst2";
		normalization_minMax(input);
		input="./resultsOfTRECs_0628/input.ETHme1";
		normalization_minMax(input);
		input="./resultsOfTRECs_0628/input.fub01be2";
		normalization_minMax(input);
		input="./resultsOfTRECs_0628/input.pircRB04t3";
		normalization_minMax(input);
		input="./resultsOfTRECs_0628/input.thutd5";
		normalization_minMax(input);
		input="./resultsOfTRECs_0628/input.uogTBQEL";
		normalization_minMax(input);
		*/
		/*
		input="./resultsOfTRECs_0628/input.CnQst2";
		normalizeScores(input);
		input="./resultsOfTRECs_0628/input.ETHme1";
		normalizeScores(input);
		input="./resultsOfTRECs_0628/input.fub01be2";
		normalizeScores(input);
		input="./resultsOfTRECs_0628/input.pircRB04t3";
		normalizeScores(input);
		input="./resultsOfTRECs_0628/input.thutd5";
		normalizeScores(input);
		input="./resultsOfTRECs_0628/input.uogTBQEL";
		normalizeScores(input);
		*/
		//ʹ��1.0/(60+rank)ʽ�ӽ��й淶��
		String root="C:/Users/1/Desktop/2009-2012my_20170313_60addRank��Ľ��/";
		
		normalization_60addRank(root+"input.indri2009mainquery");
		normalization_60addRank(root+"input.indri2009subquery");
		normalization_60addRank(root+"input.indri2010mainquery");
		normalization_60addRank(root+"input.indri2010subquery");
		normalization_60addRank(root+"input.indri2011mainquery");
		normalization_60addRank(root+"input.indri2011subquery");
		normalization_60addRank(root+"input.indri2012mainquery");
		normalization_60addRank(root+"input.indri2012subquery");
		
		
		
	}

}

class MinMax{
	double minScore;
	double maxScore;
	double difference;
	
	public double getDifference() {
		return difference;
	}
	public void setDifference(double difference) {
		this.difference = difference;
	}
	public double getMinScore() {
		return minScore;
	}
	public void setMinScore(double minScore) {
		this.minScore = minScore;
	}
	public double getMaxScore() {
		return maxScore;
	}
	public void setMaxScore(double maxScore) {
		this.maxScore = maxScore;
	}

	public MinMax(double minScore,double maxScore){
		this.minScore=minScore;
		this.maxScore=maxScore;
		difference=maxScore-minScore;
	}
}
