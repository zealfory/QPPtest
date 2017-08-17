package webTrackAdhoc2014;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

import CorrelationCoefficient.PearsonWithDataFromFile;

public class PearsonAnalysis {
	/**
	 * ��ͬ��CorrelationCoefficient.PearsonAnalysis 
	 * ����input�ļ��ĸ�ʽ��ͬ,�˳�����Щ�޸�
	 * 
	 * �����input�ļ��ĸ�ʽ�Դ˺��������޸�
	 * ����summary.std-gd.CiirWikiRm��ȡ���е�ndcg@20����
	 * 
	 * �˴����ص��ļ�Ϊ: ./webTrackAdhoc2014/nQCScore.CiirWikiRm  ./webTrackAdhoc2014/summary.std-gd.CiirWikiRm
	 * �˴����ص��ļ�Ϊ: ./webTrackAdhoc2014/sDScore.CiirWikiRm  ./webTrackAdhoc2014/summary.std-gd.CiirWikiRm
	 * �˴����ص��ļ�Ϊ: ./webTrackAdhoc2014/wIGScore.CiirWikiRm  ./webTrackAdhoc2014/summary.std-gd.CiirWikiRm
	 * �˴����ص��ļ�Ϊ: ./webTrackAdhoc2014/sMVScore.CiirWikiRm  ./webTrackAdhoc2014/summary.std-gd.CiirWikiRm
	 * ����input1��input2��Pearsonϵ��
	 * 
	 * @trackΪCiirSdm,���ض�Ӧ���ļ�
	 * @trackΪCiirSub1
	 * 
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
			//��tempLineΪ�ļ��ĵ�һ������,��ȡ��һ��
			if(tempLine.equalsIgnoreCase("runid,topic,ndcg@20,err@20"))
				tempLine=lineNumberReader.readLine();
			//��tempLine����amean,Ϊ�ܽ���,�������ȡ��Ϣ
			if(tempLine.contains("amean"))
				break;
			
			terms=tempLine.split(",");
			score=Double.parseDouble(terms[3]);//terms[2]Ϊndcg@20,terms[3]Ϊerr@20
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
		PearsonWithDataFromFile.computePearson(score1, score2);
	}
	
	public static void main(String[] args){
		try {
			//nQC��Ӧ��pearson
			loadScoreAndComputePearson("./webTrackAdhoc2014/nQCScore.CiirSub1","./webTrackAdhoc2014/summary.std-gd.CiirSub1");
			//sD��Ӧ��pearson
			loadScoreAndComputePearson("./webTrackAdhoc2014/sDScore.CiirSub1","./webTrackAdhoc2014/summary.std-gd.CiirSub1");
			//wIG��Ӧ��pearson
			loadScoreAndComputePearson("./webTrackAdhoc2014/wIGScore.CiirSub1","./webTrackAdhoc2014/summary.std-gd.CiirSub1");
			//sMV��Ӧ��pearson
			loadScoreAndComputePearson("./webTrackAdhoc2014/sMVScore.CiirSub1","./webTrackAdhoc2014/summary.std-gd.CiirSub1");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//��input.CiirSdm.inversedΪ��Ϣ:
	//ndcg@20��Ϊ��׼
	//nQC 
	//sD 
	//wIG 
	//sMV 
	//err@20��Ϊ��׼
	//nQC 
	//sD 
	//wIG 
	//sMV 
	
	
	
}
