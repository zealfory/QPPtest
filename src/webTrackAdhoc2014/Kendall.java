package webTrackAdhoc2014;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
/**
 * ��CorrelationCoefficient.Kendall���ơ�
 * ����input�ļ��ĸ�ʽ��ͬ,�˳�����Щ�޸�
 */
public class Kendall {
	private double[] m;
	private double[] n;
	private int k;//���m,n���鳤�ȵĽ�Сֵ
	
	//By ChenJiawei strict coherence
	public int getCoherence(double[] a,double[] b){
		int count=0;
		double ra=0;
		double rb=0;
		for(int i=0;i<k-1;i++){
			for(int j=i+1;j<k;j++){
				ra=a[i]-a[j];
				rb=b[i]-b[j];
				if(ra>0 && rb>0) count++;
				if(ra<0 && rb<0) count++;
				if(ra==0 && rb==0) count++;
			}
		}
		return count;
	}
	//By ChenJiawei
	public double getCoefficientKendall(){
		double total=k*(k-1)/2;
		double coherence=getCoherence(m,n);
		double coefficientKen=(2*coherence-total)/total;
		return coefficientKen;
	}
	/**
	 * ����kendallϵ��
	 */
	public void computeKendall(double[] score1,double[] score2){
		double kendallCoefficient=0;
		m=score1;
		n=score2;
		k=score1.length>score2.length?score2.length:score1.length;
		kendallCoefficient=getCoefficientKendall();
		System.out.println("kendallCoefficient= "+kendallCoefficient);
	}
	/**
	 * ��CorrelationCoefficient.Kendall���� 
	 * ����input�ļ��ĸ�ʽ��ͬ,�˳�����Щ�޸�
	 * 
	 * �����input�ļ��ĸ�ʽ�Դ˺��������޸�
	 * ����summary.std-gd.CiirWikiRm��ȡ���е�ndcg@20����
	 * 
	 * �˴����ص��ļ�Ϊ: ./webTrackAdhoc2014/nQCScore.CiirWikiRm  ./webTrackAdhoc2014/summary.std-gd.CiirWikiRm
	 * �˴����ص��ļ�Ϊ: ./webTrackAdhoc2014/sDScore.CiirWikiRm  ./webTrackAdhoc2014/summary.std-gd.CiirWikiRm
	 * �˴����ص��ļ�Ϊ: ./webTrackAdhoc2014/wIGScore.CiirWikiRm  ./webTrackAdhoc2014/summary.std-gd.CiirWikiRm
	 * �˴����ص��ļ�Ϊ: ./webTrackAdhoc2014/sMVScore.CiirWikiRm  ./webTrackAdhoc2014/summary.std-gd.CiirWikiRm
	 * ����input1��input2��Kendallϵ��
	 * 
	 * @trackΪCiirSdm
	 * @trackΪCiirSub1
	 * @throws IOException 
	 * */
	public void loadScoreAndComputeKendall(String input1,String input2) throws IOException{
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
		//����kendall
		computeKendall(score1,score2);
	}
	
	public static void main(String[] args){
		Kendall kendall=new Kendall();
		try {
			//nQC��Ӧ��kendall
			kendall.loadScoreAndComputeKendall("./webTrackAdhoc2014/nQCScore.CiirSub1","./webTrackAdhoc2014/summary.std-gd.CiirSub1");
			//sD��Ӧ��kendall
			kendall.loadScoreAndComputeKendall("./webTrackAdhoc2014/sDScore.CiirSub1","./webTrackAdhoc2014/summary.std-gd.CiirSub1");
			//wIG��Ӧ��kendall
			kendall.loadScoreAndComputeKendall("./webTrackAdhoc2014/wIGScore.CiirSub1","./webTrackAdhoc2014/summary.std-gd.CiirSub1");
			//sMV��Ӧ��kendall
			kendall.loadScoreAndComputeKendall("./webTrackAdhoc2014/sMVScore.CiirSub1","./webTrackAdhoc2014/summary.std-gd.CiirSub1");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//����input.CiirWikiRm.inversed
	//ndcg@20

}
