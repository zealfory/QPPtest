package newPredictor;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
/**
 * ��predictor.SMV��Ļ�����ɾȥ��ln�ľ���ֵ,1/score(D),1/k��
 * 
 * @author 1
 *
 */
public class SMV_2 {
	private int k=100;//kΪ�ضϲ���
	
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
	 * ȥ����Math.abs(),1/k,1/score(D)<br>
	 * ����score����ͽضϲ���k����SMV_2
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
	 * ����input.pircRB04t3����ÿ��query��SMV_2ֵ, ����SMV_2Score�����ļ�
	 * 
	 * */
	public void getSMV_2Scores(String input, String output) {
		FileReader fileReader = null;
		LineNumberReader lineNumberReader = null;
		FileWriter fileWriter = null;
		String tempLine = null;
		String[] terms = null;// ����tempLine
		String preQueryId = null;
		ArrayList<Double> arrayList = new ArrayList<Double>();
		double score = 0;// ��ʱ���terms[4]��score
		double[] scores = null;// ��ʱ���һ��query��Ӧ��score����
		int scoreCount = 0;// ��ʱ���score����ĳ���
		double SMV_2Score = 0;// ��ʱ���һ��query��SMV_2ֵ
		int k_original=k;//�洢�����kֵ
		
		try {
			fileReader = new FileReader(input);
			lineNumberReader = new LineNumberReader(fileReader);
			fileWriter = new FileWriter(output, false);
			while ((tempLine = lineNumberReader.readLine()) != null) {
				terms = tempLine.split("\t| ");
				// ���preQueryIdΪnull
				if (preQueryId == null)
					preQueryId = terms[0];
				// queryId��ͬ,����score
				if (preQueryId.equalsIgnoreCase(terms[0])) {
					score = Double.parseDouble(terms[4]);
					arrayList.add(score);
				}
				// queryId��ͬ,����preQueryId��SMV_2Score,д���ļ�,���arrayList��Ϣ,����terms��Ϣ
				if (!preQueryId.equalsIgnoreCase(terms[0])) {
					// ��arrayListת��Ϊdouble����
					scoreCount = arrayList.size();
					scores = new double[scoreCount];
					for (int i = 0; i < scoreCount; i++)
						scores[i] = arrayList.get(i);
					//���˲�ѯ�µ��ĵ���scoreCountС��k,��k��ΪscoreCount
					if(scoreCount<k) k=scoreCount;
					// ����computSMV_2()�����query��SMV_2ֵ
					SMV_2Score = computeSMV_2(scores);
					// ��queryId��SMV_2Scoreд���ļ�
					fileWriter.write("queryId:\t" + preQueryId + "\tSMV_2:\t" + SMV_2Score + "\n");
					//��k��Ϊ��ʼkֵ,��k��Ϊ��ʼֵk_original
					if(k!=k_original) k=k_original;
					// ���arrayList
					arrayList.clear();
					// ��ʼ����terms��Ϣ
					preQueryId = terms[0];
					score = Double.parseDouble(terms[4]);
					arrayList.add(score);
				}
			}
			// ���queryId��Ӧ��scoresδ����,������SMV_2Score,��д���ļ�
			// ��arrayListת��Ϊdouble����
			scoreCount = arrayList.size();
			scores = new double[scoreCount];
			for (int i = 0; i < scoreCount; i++)
				scores[i] = arrayList.get(i);
			//���˲�ѯ�µ��ĵ���scoreCountС��k,��k��ΪscoreCount
			if(scoreCount<k) k=scoreCount;
			// ����computSMV_2()�����query��SMV_2ֵ
			SMV_2Score = computeSMV_2(scores);
			// ��queryId��SMV_2Scoreд���ļ�
			fileWriter.write("queryId:\t" + preQueryId + "\tSMV_2:\t" + SMV_2Score + "\n");
		} catch (IOException e) {
			System.err.println("�������ݳ���!");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.close();
				lineNumberReader.close();
			} catch (IOException e) {
				System.err.println("�ر�IO���Ӵ���!");
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		
	}
}
