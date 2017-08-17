package CorrelationCoefficient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * ����http://blog.csdn.net/wsywl/article/details/5859751�Ľ���д�ɡ�
 * Spearman's rank correlation coefficient��
 * @author 1
 *
 */
public class Spearman {
	
	private int k=0;//score�������󳤶�
	private double[] m;
	private double[] n;
	private double[] mRank;
	private double[] nRank;
	/**
	 * 
	 * @return
	 */
	public double getSpearmanCoeff(){
		mRank=new double[k];
		nRank=new double[k];
		//����m�����mRank���鸳ֵ
		int cont1=0;//��¼�����ض�Ԫ�ص�Ԫ�ظ���
		int cont2=0;//��¼���ض�Ԫ����ͬ��Ԫ�ظ���
		double sum_cont2=0;
		for(int i=0;i<k;i++){
		    cont1 = 1;
		    cont2 = -1;
			for(int j=0;j<k;j++){
				if(m[i]<m[j]){
					cont1++;
				}else if(m[i]==m[j]){
					cont2++;
				}
			}
			//����0��sum_cont2��ƽ��ֵ
			sum_cont2=0;//sum_cont2��Ϊ0
			for(int t=0;t<=cont2;t++){
				sum_cont2=sum_cont2+t;
			}
			sum_cont2=sum_cont2/(cont2+1);
			mRank[i]=cont1+sum_cont2;
		}
		//����n�����nRank���鸳ֵ
		for(int i=0;i<k;i++){
			cont1 = 1;
			cont2 = -1;
			for(int j=0;j<k;j++){
				if(n[i]<n[j]){
					cont1++;
				}else if(n[i]==n[j]){
					cont2++;
				}
			}
			//����0��sum_cont2��ƽ��ֵ
			sum_cont2=0;//sum_cont2��Ϊ0
			for(int t=0;t<=cont2;t++){
				sum_cont2=sum_cont2+t;
			}
			sum_cont2=sum_cont2/(cont2+1);
			nRank[i]=cont1+sum_cont2;
		}
		//����mRank��nRank���pearsonϵ��
		return getPearsonCoeff();
	}
	/**
	 * ����mRank��nRank���pearsonϵ��
	 * @return
	 */
	public double getPearsonCoeff(){
		String pearsonResult=null;
		double pearsonCoeff=0;
		pearsonResult=PearsonWithDataFromFile.computePearson(mRank, nRank);
		pearsonCoeff=Double.parseDouble(pearsonResult.split("=")[1].trim());
		return pearsonCoeff;
	}
	/**
	 * ����Spearmanϵ��
	 * @param score1
	 * @param score2
	 */
	public String computeSpearman(double[] score1,double[] score2){
		double spearmanCoeff=0;
		String spearmanResult=null;
		m=score1;
		n=score2;
		//kȡ���鳤�ȵĽϴ���
		k=score1.length>score2.length?score1.length:score2.length;
		spearmanCoeff=getSpearmanCoeff();
		spearmanResult="spearmanCoefficient="+spearmanCoeff;
		return spearmanResult;
	}
	
	/**
	 * �����input�ļ��ĸ�ʽ�Դ˺��������޸�
	 * �˴����ص��ļ�Ϊ: ./robustTrack2004/nQCScore.runId  ./robustTrack2004/map.normalized.runId
	 * ����input1��input2��Spearmanϵ��
	 * @throws IOException 
	 * */
	public void loadScoreAndComputeSpearman(String input1, String input2) throws IOException{
		FileReader fileReader=null;
		BufferedReader buffReader=null;
		double[] score1=null;//���input1����
		double[] score2=null;//���input2����
		ArrayList<Double> arrayList=new ArrayList<Double>();
		int scoreCount=0;//���score����
		double score=0;
		String tempLine=null;
		String[] terms=null;
		String result=null;//������ʾspearman�Ľ��
		
		//��ȡinput1�е�score
		fileReader=new FileReader(input1);
		buffReader=new BufferedReader(fileReader);
		while((tempLine=buffReader.readLine())!=null){
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
		buffReader.close();
		
		//��ȡinput2�е�score,���arrayList,�޸�terms[x]��x
		fileReader=new FileReader(input2);
		buffReader=new BufferedReader(fileReader);
		arrayList.clear();//���arrayList
		while((tempLine=buffReader.readLine())!=null){
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
		buffReader.close();
		//����spearman
		result=computeSpearman(score1, score2);
		//����input1�ļ�,��ʾresult
		if(input1.contains("nQCScore")) result="nQC "+result;
		if(input1.contains("sDScore")) result="sD "+result;
		if(input1.contains("wIGScore")) result="wIG "+result;
		if(input1.contains("sMVScore")) result="sMV "+result;
		
		if(input1.contains("sD2Score")) result="sD2 "+result;
		if(input1.contains("cScore")) result="c "+result;
		if(input1.contains("c2Score")) result="c2 "+result;
		if(input1.contains("c3Score")) result="c3 "+result;
		if(input1.contains("c4Score")) result="c4 "+result;
		
		if(input1.contains("sD_WIGScore")) result="sD_WIG "+result;
		if(input1.contains("wIG_NQCScore")) result="wIG_NQC "+result;
		if(input1.contains("sD_Multi_WIGScore")) result="sD_Multi_WIG "+result;
		if(input1.contains("wIG_Multi_NQCScore")) result="wIG_Multi_NQC "+result;
		
		if(input1.contains("iA_SUMScore")) result="iA_SUM "+result;
		if(input1.contains("sD2MultiWIGScore")) result="sD2MultiWIG "+result;
		if(input1.contains("iASUM2Score")) result="iASUM2 "+result;
		if(input1.contains("iASUM3Score")) result="iASUM3 "+result;
		if(input1.contains("cFScore")) result="cF "+result;
		if(input1.contains("cF2Score")) result="cF2 "+result;
		
		System.out.println(result);
	}
	
	public static void main(String[] args){
		/*
		double[] a={0.586,0.89,0.357,0.41,0.41};
		double[] b={0.926,0.995,0.322,0.282,0.282};
		for(int i=0;i<a.length;i++){
			System.out.print(a[i]+" ");
		}
		Spearman spearman=new Spearman();
		String result=spearman.computeSpearman(a, b);
		System.out.println("\n"+result);
		*/
	}

}
