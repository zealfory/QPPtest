package temporary;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;


/**
 * ����http://blog.csdn.net/wsywl/article/details/5889419,��kendall��������޸ġ�
 * @author 1
 *
 */
public class Kendall {
	private static double[] m;
	private static double[] n;
	private static int k;
	
	public static double cMinusD(){
		int C=0;
		int D=0;
		double ra=0;
		double rb=0;
		double cMinus=0;
		for(int i=0;i<k-1;i++){
			for(int j=i+1;j<k;j++){
				ra=m[i]-m[j];
				rb=n[i]-n[j];
				//��ra��rbһ��
				if(ra>0&&rb>0) C++;
				if(ra<0&&rb<0) C++;
				//��ra��rb��һ��
				if(ra>0&&rb<0) D++;
				if(ra<0&&rb>0) D++;
			}
		}
		cMinus=C-D;
		return cMinus;
	}
	
	public static double getDenom(){
		double n3=k*(k-1)/2.0;
		//��������m����ͬԪ�ص������
		double n1=0;
		double n3_n1=0;
		n1=getPairs_equal(m);
		n3_n1=n3-n1;
		//��������n����ͬԪ�ص������
		double n2=0;
		double n3_n2=0;
		n2=getPairs_equal(n);
		n3_n2=n3-n2;
		//����kendall�ȼ�ϵ����denominator
		double denom=0;
		denom=Math.sqrt(n3_n1*n3_n2);
		return denom;
	}
	
	public static double getCoefficientKendall(){
		double cMinus=0;
		double denom=0;
		double coeff=0;
		cMinus=cMinusD();
		denom=getDenom();
		coeff=cMinus/denom;
		return coeff;
	}
	
	public static String computeKendall(double[] score1,double[] score2){
		double kendallCoeff=0;
		String kendallResult=null;
		m=score1;
		n=score2;
		//kȡ���鳤�ȵĽϴ���
		k=score1.length>score2.length?score1.length:score2.length;
		kendallCoeff=getCoefficientKendall();
		kendallResult="kendallCoefficient= "+kendallCoeff;
		return kendallResult;
	}
	
	/**
	 * �������е���ͬԪ�طֱ���ϳ�С����,��ȥԪ�ظ���Ϊ1��С����,
	 * ����ÿ��С������Ԫ�ص������,�����
	 */
	public static double getPairs_equal(double[] x){
		HashMap<Double,Integer> map=new HashMap<Double,Integer>();
		double figure=0;
		int count=0;
		//��x�������map��
		for(int i=0;i<x.length;i++){
			figure=x[i];
			//��map��keys����figure,����(figure 1)��ֵ��
			if(!map.containsKey(figure)){
				map.put(figure, 1);
			}else{
				//��map��keys����figure,����figure��Ӧ�ļ�ֵ��
				count=map.get(figure);
				count++;
				map.put(figure, count);
			}
		}
		//ɾ��map��countΪ1�ļ�ֵ��
		figure=0;//figure��Ϊ��
		count=0;//count��Ϊ0
		Set<Entry<Double,Integer>> set=null;
		Iterator<Entry<Double,Integer>> it=null;
		Entry<Double,Integer> entry=null;
		ArrayList<Double> array_figure=new ArrayList<Double>();
		
		set=map.entrySet();
		it=set.iterator();
		while(it.hasNext()){
			entry=it.next();
			figure=entry.getKey();
			count=entry.getValue();
			//��countΪ1��figure����array_figure��
			if(count==1) array_figure.add(figure);
		}
		//����array_figure�е���Ϣɾ��map�е���Ϣ
		for(int i=0;i<array_figure.size();i++){
			figure=array_figure.get(i);
			map.remove(figure);
		}
		//����map��Ϣ,����ÿ��С������Ԫ�ص������,�����
		count=0;//����countΪ0
		double sum_pair=0;
		double pair=0;
		set=map.entrySet();
		it=set.iterator();
		while(it.hasNext()){
			entry=it.next();
			count=entry.getValue();
			pair=1.0/2*count*(count-1);
			sum_pair=sum_pair+pair;
		}
		return sum_pair;
	}
	/**
	 * �����input�ļ��ĸ�ʽ�Դ˺��������޸�
	 * �˴����ص��ļ�Ϊ: ./robustTrack2004/nQCScore.pircRB04t3  ./robustTrack2004/map.normalized.pircRB04t3
	 * �˴����ص��ļ�Ϊ: ./robustTrack2004/sDScore.pircRB04t3  ./robustTrack2004/map.normalized.pircRB04t3
	 * �˴����ص��ļ�Ϊ: ./robustTrack2004/wIGScore.pircRB04t3  ./robustTrack2004/map.normalized.pircRB04t3
	 * �˴����ص��ļ�Ϊ: ./robustTrack2004/sMVScore.pircRB04t3  ./robustTrack2004/map.normalized.pircRB04t3
	 * ����input1��input2��Kendallϵ��
	 * @throws IOException 
	 * */
	public static void loadScoreAndComputeKendall(String input1, String input2) throws IOException{
		FileReader fileReader=null;
		LineNumberReader lineNumberReader=null;
		double[] score1=null;//���input1����
		double[] score2=null;//���input2����
		ArrayList<Double> arrayList=new ArrayList<Double>();
		int scoreCount=0;//���score����
		double score=0;
		String tempLine=null;
		String[] terms=null;
		String kendallResult=null;//������ʾkendallResult
		
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
		//����kendall
		kendallResult=computeKendall(score1, score2);
		//����input1�ļ�,��ʾkendallResult
		if(input1.contains("nQCScore")) kendallResult="nQC "+kendallResult;
		if(input1.contains("sDScore")) kendallResult="sD "+kendallResult;
		if(input1.contains("wIGScore")) kendallResult="wIG "+kendallResult;
		if(input1.contains("sMVScore")) kendallResult="sMV "+kendallResult;
		
		if(input1.contains("sD2Score")) kendallResult="sD2 "+kendallResult;
		
		if(input1.contains("iA_SUMScore")) kendallResult="iA_SUM "+kendallResult;
		if(input1.contains("sD2MultiWIGScore")) kendallResult="sD2MultiWIG "+kendallResult;
		if(input1.contains("iASUM2Score")) kendallResult="iASUM2 "+kendallResult;
		if(input1.contains("iASUM3Score")) kendallResult="iASUM3 "+kendallResult;
		if(input1.contains("cFScore")) kendallResult="cF "+kendallResult;
		if(input1.contains("cF2Score")) kendallResult="cF2 "+kendallResult;
		if(input1.contains("AuPRScore"))kendallResult="AuPR"+kendallResult;
		
		System.out.println(kendallResult);
	}
	/**
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException{
		/*FileReader fileReader=null;
		BufferedReader buffReader=null;
		String tempLine=null;
		String[] terms=null;
		String input=null;
		double[] score1=new double[500];
		double[] score2=new double[500];
		int i=0;
		
		input="./diversification/probability_subquery.txt";
		fileReader=new FileReader(input);
		buffReader=new BufferedReader(fileReader);
		buffReader.readLine();
		while((tempLine=buffReader.readLine())!=null){
			terms=tempLine.split(" |\t");
			score1[i]=Double.parseDouble(terms[0]);
			score2[i]=Double.parseDouble(terms[1]);
			i++;
		}
		buffReader.close();
		//����pearsonϵ��
		String kendallResult=null;
		kendallResult=computeKendall(score1,score2);
		System.out.println(kendallResult);
		*/
	}

}
