package predictor;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
/**
 * changed on 20170312,��ʱ���޸ġ���NQCԤ��ֵΪNaN,��Ϊ0,������޸ĵ���ʾ,
 * �ڷ���getNQCScores(String input, String output),�����޸�<br>
 * @author 1
 *
 */
public class NQC {
	private int k = 100; // kΪNQC�Ľض�ָ��
	
	public int getK() {
		return k;
	}
	public void setK(int k) {
		this.k = k;
	}

	public double mean(double[] m, int n) {
		double s = 0;
		for (int i = 0; i < n; i++) {
			s += m[i];
		}
		return s / n;
	}

	public double Umean(double[] m) {
		double s = 0;
		for (int i = 0; i < k; i++) {
			s += m[i];
		}
		return s / k;
	}

	// NQC�㷨
	public double computeNQC(double[] score, int n) {
		double u = Umean(score);
		double s = 0;
		double numerator = 0;
		double denom = 0;
		for (int i = 0; i < k; i++) {
			s += (score[i] - u) * (score[i] - u);
		}
		numerator = Math.sqrt(s / k);
		denom = Math.abs(mean(score, n));
		return numerator / denom;
	}
	/**
	 * <br>��nQCScoreΪNaN,��ʱ��Ϊ0<br>
	 * ��2016/06/02,�����޸�,������k_original������
	 * ����input.pircRB04t3����ÿ��query��NQCֵ, ����NQCScore�����ļ� 
	 * @ trackΪNLPR04OKapi
	 * */
	public void getNQCScores(String input,String output){
		FileReader fileReader=null;
		LineNumberReader lineNumberReader=null;
		FileWriter fileWriter=null;
		String tempLine=null;
		String[] terms=null;//����tempLine
		String preQueryId=null;
		ArrayList<Double> arrayList=new ArrayList<Double>();
		double score=0;//��ʱ���terms[4]��score
		double[] scores=null;//��ʱ���һ��query��Ӧ��score����
		int scoreCount=0;//��ʱ���score����ĳ���
		double NQCScore=0;//��ʱ���һ��query��NQCֵ
		int k_original=k;//�洢�����kֵ
		
		try{
			fileReader=new FileReader(input);
			lineNumberReader=new LineNumberReader(fileReader);
			fileWriter=new FileWriter(output,false);	
			while((tempLine=lineNumberReader.readLine())!=null){
				terms=tempLine.split("\t| ");
				//���preQueryIdΪnull
				if(preQueryId==null) preQueryId=terms[0];
				//queryId��ͬ,����score
				if(preQueryId.equalsIgnoreCase(terms[0])){
					score=Double.parseDouble(terms[4]);
					arrayList.add(score);
				}
				//queryId��ͬ,����preQueryId��NQCScore,д���ļ�,���arrayList��Ϣ,����terms��Ϣ
				if(!preQueryId.equalsIgnoreCase(terms[0])){
					//��arrayListת��Ϊdouble����
					scoreCount=arrayList.size();
					scores=new double[scoreCount];
					for(int i=0;i<scoreCount;i++)
						scores[i]=arrayList.get(i);
					//���˲�ѯ�µ��ĵ���scoreCountС��k,��k��ΪscoreCount
					if(scoreCount<k) k=scoreCount;
					//����computNQC()�����query��NQCֵ
					NQCScore=computeNQC(scores,scoreCount);
					
					//��queryId��NQCScoreд���ļ�
					tempLine="queryId:\t"+preQueryId+"\tNQC:\t"+NQCScore+"\n";
					//��tempLine��nQCScoreΪNaN,��ʱ��Ϊ0
					fileWriter.write(convertNaN(tempLine));
					
					//��k��Ϊ��ʼkֵ,��k��Ϊ��ʼֵk_original
					if(k!=k_original) k=k_original;
					//���arrayList
					arrayList.clear();
					//��ʼ����terms��Ϣ
					preQueryId=terms[0];
					score=Double.parseDouble(terms[4]);
					arrayList.add(score);
				}
			}
			//���queryId��Ӧ��scoresδ����,������NQCScore,��д���ļ�
			//��arrayListת��Ϊdouble����
			scoreCount=arrayList.size();
			scores=new double[scoreCount];
			for(int i=0;i<scoreCount;i++)
				scores[i]=arrayList.get(i);
			//���˲�ѯ�µ��ĵ���scoreCountС��k,��k��ΪscoreCount
			if(scoreCount<k) k=scoreCount;
			//����computNQC()�����query��NQCֵ
			NQCScore=computeNQC(scores,scoreCount);
			
			//��queryId��NQCScoreд���ļ�
			tempLine="queryId:\t"+preQueryId+"\tNQC:\t"+NQCScore+"\n";
			//��tempLine��nQCScoreΪNaN,��ʱ��Ϊ0
			fileWriter.write(convertNaN(tempLine));
			
		}catch(IOException e){
			System.err.println("�������ݳ���!");
			e.printStackTrace();
		}finally{
			try {
				fileWriter.close();
				lineNumberReader.close();
			} catch (IOException e) {
				System.err.println("�ر�IO���Ӵ���!");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ��nQCֵΪNaN,��Ϊ0
	 * @return
	 */
	public String convertNaN(String tempLine){
		String[] terms=null;
		String newTempLine=null;
		//��ȥ��\n,ʹ��trim()����
		terms=tempLine.trim().split(" |\t");
		//��nQCΪNaN,��Ϊ0
		if(terms[3].equals("NaN")){
			terms[3]="0";
			newTempLine=terms[0]+"\t"+terms[1]+"\t"+terms[2]+"\t"+terms[3]+"\n";
			System.out.println("\n\n������¼"+tempLine+" �д���NaNֵ,�ѱ���Ϊ0,������nQCScore.runId�ļ��С�");
			return newTempLine;
		}else{
			return tempLine;
		}
	}
	
	public static void main(String[] args){
		NQC nQC=new NQC();
		nQC.k=100;
		nQC.getNQCScores("./robustTrack2004/input.apl04rsTDNw5.inversed", "./robustTrack2004/nQCScore.apl04rsTDNw5");
		System.out.println("����input.pircRB04t3����ÿ��query��NQCֵ,����NQCScore�����ļ�,�����..");
	}
	/*
	//������queryId=327��NQCScore
	public static void main(String[] args){
		String fileName=null;
		FileReader fileReader=null;
		LineNumberReader lineNumberReader=null;
		String tempLine=null;
		
		double[] scores=null;//��NQCʹ��
		int scoreCount=0;//score������
		double NQCScore=0;//NQCֵ
		
		ArrayList<Double> arrayList=null;
		String[] terms=null;
		double score=0;
		arrayList=new ArrayList<Double>();
		
		try{
			fileName="./robustTrack2004/input.pircRB04t3";
			fileReader=new FileReader(fileName);
			lineNumberReader=new LineNumberReader(fileReader);
			
			while((tempLine=lineNumberReader.readLine())!=null){
				terms=tempLine.split("\t| ");
				if(terms[0].equalsIgnoreCase("327")){
					score=Double.parseDouble(terms[4]);
					System.out.println("score="+score);
					arrayList.add(score);
				}
			}
			
		}catch(IOException e){
			System.err.println("���ִ���!");
			e.printStackTrace();
		}finally{
			try {
				lineNumberReader.close();
			} catch (IOException e) {
				System.err.println("lineNumberReader.close()����..");
				e.printStackTrace();
			}
		}
		//��arrayListת��Ϊdouble[]
		scoreCount=arrayList.size();
		//
		scores=new double[scoreCount];
		System.out.println(scoreCount);
		for(int i=0;i<scoreCount;i++){
			System.out.println("i="+i+" "+arrayList.get(i));
			scores[i]=(double)arrayList.get(i);
			System.out.println("i="+i+" "+arrayList.get(i));
		}
			//scores[i]=arrayList.get(i);
		
		System.out.println("scoreCount="+scoreCount);
		
		NQC nQC=new NQC();
		nQC.k=100;
		NQCScore=nQC.computeNQC(scores, scoreCount);
		System.out.println("NQCֵΪ "+NQCScore);
		//NQC(324,pircRB04t3)=0.2020690488478744
		//NQC(327,pircRB04t3)=0.20966116075331787
		
	}*/

}
