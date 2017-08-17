package CorrelationCoefficient;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

public class PearsonAnalysis {
	/**
	 * �����input�ļ��ĸ�ʽ�Դ˺��������޸�
	 * �˴����ص��ļ�Ϊ: ./robustTrack2004/nQCScore.pircRB04t3  ./robustTrack2004/map.normalized.pircRB04t3
	 * �˴����ص��ļ�Ϊ: ./robustTrack2004/sDScore.pircRB04t3  ./robustTrack2004/map.normalized.pircRB04t3
	 * �˴����ص��ļ�Ϊ: ./robustTrack2004/wIGScore.pircRB04t3  ./robustTrack2004/map.normalized.pircRB04t3
	 * �˴����ص��ļ�Ϊ: ./robustTrack2004/sMVScore.pircRB04t3  ./robustTrack2004/map.normalized.pircRB04t3
	 * ����input1��input2��Pearsonϵ��
	 * @throws IOException 
	 * */
	public static void loadScoreAndComputePearson(String input1, String input2) throws IOException{
		FileReader fileReader=null;
		LineNumberReader lineNumberReader=null;
		double[] score1=null;//���input1����
		double[] score2=null;//���input2����
		ArrayList<Double> arrayList=new ArrayList<Double>();
		int scoreCount=0;//���score����
		double score=0;
		String tempLine=null;
		String[] terms=null;
		String pearsonResult=null;//������ʾpearsonResult
		
		//��ȡinput1�е�score
		fileReader=new FileReader(input1);
		lineNumberReader=new LineNumberReader(fileReader);
		while((tempLine=lineNumberReader.readLine())!=null){
			terms=tempLine.split("\t");
			score=Double.parseDouble(terms[3]);
			arrayList.add(score);
		}
		//��arrayListת��Ϊdouble����
		scoreCount=arrayList.size();
		score1=new double[scoreCount];
		for(int i=0;i<scoreCount;i++)
			score1[i]=arrayList.get(i);
		//�ر�IO�ļ�
		lineNumberReader.close();
		
		//��ȡinput2�е�score,���arrayList,�޸�terms[x]��x
		fileReader=new FileReader(input2);
		lineNumberReader=new LineNumberReader(fileReader);
		arrayList.clear();//���arrayList
		while((tempLine=lineNumberReader.readLine())!=null){
			terms=tempLine.split("\t");
			score=Double.parseDouble(terms[4]);
			arrayList.add(score);
		}
		//��arrayListת��Ϊdouble����
		scoreCount=arrayList.size();
		score2=new double[scoreCount];
		for(int i=0;i<scoreCount;i++)
			score2[i]=arrayList.get(i);
		//�ر�IO�ļ�
		lineNumberReader.close();
		//����pearson
		pearsonResult=PearsonWithDataFromFile.computePearson(score1, score2);
		//����input1�ļ�,��ʾpearsonResult
		if(input1.contains("nQCScore")) pearsonResult="nQC "+pearsonResult;
		if(input1.contains("sDScore")) pearsonResult="sD "+pearsonResult;
		if(input1.contains("wIGScore")) pearsonResult="wIG "+pearsonResult;
		if(input1.contains("sMVScore")) pearsonResult="sMV "+pearsonResult;
		
		if(input1.contains("sD2Score")) pearsonResult="sD2 "+pearsonResult;
		
		if(input1.contains("iA_SUMScore")) pearsonResult="iA_SUM "+pearsonResult;
		if(input1.contains("sD2MultiWIGScore")) pearsonResult="sD2MultiWIG "+pearsonResult;
		if(input1.contains("iASUM2Score")) pearsonResult="iASUM2 "+pearsonResult;
		if(input1.contains("iASUM3Score")) pearsonResult="iASUM3 "+pearsonResult;
		if(input1.contains("cFScore")) pearsonResult="cF "+pearsonResult;
		if(input1.contains("cF2Score")) pearsonResult="cF2 "+pearsonResult;
		if(input1.contains("AuPRScore"))pearsonResult="AuPR"+pearsonResult;
		System.out.println(pearsonResult);
	}
	public static void main(String[] args){
		try {
			//nQC��Ӧ��pearson
			loadScoreAndComputePearson("./robustTrack2004/nQCScore.apl04rsTDNw5","./robustTrack2004/map.normalized.apl04rsTDNw5");
			//sD��Ӧ��pearson
			loadScoreAndComputePearson("./robustTrack2004/sDScore.apl04rsTDNw5","./robustTrack2004/map.normalized.apl04rsTDNw5");
			//wIG��Ӧ��pearson
			loadScoreAndComputePearson("./robustTrack2004/wIGScore.apl04rsTDNw5","./robustTrack2004/map.normalized.apl04rsTDNw5");
			//sMV��Ӧ��pearson
			loadScoreAndComputePearson("./robustTrack2004/sMVScore.apl04rsTDNw5","./robustTrack2004/map.normalized.apl04rsTDNw5");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
// ./robustTrack2004/nQCScore.pircRB04t3 ��Ӧ��pearsonCoefficient=0.5971430864664645
	// sDScore pearsonCoefficient=0.6458754968527928
	//wIGScore pearsonCoefficient=0.5752996313858094
	//sMVScore pearsonCoefficient=0.5910095602130441
	
}
