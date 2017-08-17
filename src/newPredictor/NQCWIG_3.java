package newPredictor;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

public class NQCWIG_3 {
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
	
	public double computeNQCWIG_3(double[] scores){
		double u=uMean(scores);
		double scoreD=mean(scores);
		double sum=0;
		double numerator=0;
		
		for(int i=0;i<k;i++){
			sum=sum+(scores[i]-u)*(scores[i]-u)*(scores[i]-scoreD)*(scores[i]-scoreD);
		}
		numerator=Math.sqrt(sum/k);
		return numerator;
	}
	
	/**
	 * ����input.runId����ÿ��query��NQCWIG_3ֵ, ����NQCWIG_3Score�����ļ� 
	 *  
	 * */
	public void getNQCWIG_3Scores(String input,String output){
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
		double NQCWIG_3Score=0;//��ʱ���һ��query��NQCWIG_3ֵ
		
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
				//queryId��ͬ,����preQueryId��NQCWIG_3Score,д���ļ�,���arrayList��Ϣ,����terms��Ϣ
				if(!preQueryId.equalsIgnoreCase(terms[0])){
					//��arrayListת��Ϊdouble����
					scoreCount=arrayList.size();
					scores=new double[scoreCount];
					for(int i=0;i<scoreCount;i++)
						scores[i]=arrayList.get(i);
					//����computNQCWIG_3()�����query��NQCWIG_3ֵ
					NQCWIG_3Score=computeNQCWIG_3(scores);
					//��queryId��NQCWIG_3Scoreд���ļ�
					fileWriter.write("queryId:\t"+preQueryId+"\tNQCWIG_3:\t"+NQCWIG_3Score+"\n");
					//���arrayList
					arrayList.clear();
					//��ʼ����terms��Ϣ
					preQueryId=terms[0];
					score=Double.parseDouble(terms[4]);
					arrayList.add(score);
				}
			}
			//���queryId��Ӧ��scoresδ����,������NQCWIG_3Score,��д���ļ�
			//��arrayListת��Ϊdouble����
			scoreCount=arrayList.size();
			scores=new double[scoreCount];
			for(int i=0;i<scoreCount;i++)
				scores[i]=arrayList.get(i);
			//����computNQCWIG_3()�����query��NQCWIG_3ֵ
			NQCWIG_3Score=computeNQCWIG_3(scores);
			//��queryId��NQCWIG_3Scoreд���ļ�
			fileWriter.write("queryId:\t"+preQueryId+"\tNQCWIG_3:\t"+NQCWIG_3Score+"\n");
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
	
	public static void main(String[] args){
		
	}

}
