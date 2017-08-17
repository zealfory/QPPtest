package predictor;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
/**
 * changed on 20170313,�޸�,��score=0,��score(d)*|ln(score(d)/umean)|=0,
 * ��computeSMV(double[] score)�����������޸�<br>
 * changed on 20170312,��ʱ���޸ġ���SMVԤ��ֵΪNaN,��Ϊ0,������޸ĵ���ʾ,
 * �ڷ���getSMVScores(String input, String output),�����޸�<br>
 * @author 1
 *
 */
public class SMV {
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
	/**
	 * ����ǰk���ƽ��ֵ
	 * */
	public double Umean(double[] score){
		double sum=0;
		for(int i=0;i<k;i++)
			sum=sum+score[i];
		return sum/k;
	}
	/**
	 * ��score=0,��score(d)*|ln(score(d)/umean)|=0��<br>
	 * ����score����ͽضϲ���k����SMV
	 * */
	public double computeSMV(double[] score){
		double u=Umean(score);
		double sum=0;
		double numerator=0;
		double denom=0;
		
		for(int i=0;i<k;i++){
			//score[i]Ϊ0ʱ,sum=sum+0,
			if(score[i]==0) continue;
			sum=sum+score[i]*Math.abs(Math.log(score[i]/u));
		}
		numerator=sum/k;
		denom=mean(score);
		return numerator/denom;
	}
	/**
	 * <br>��sD2ScoreΪNaN,��ʱ��Ϊ0
	 * <br>��2016/06/02,�����޸�,������k_original������
	 * ����input.pircRB04t3����ÿ��query��SMVֵ, ����SMVScore�����ļ�
	 * 
	 * */
	public void getSMVScores(String input, String output) {
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
		double SMVScore = 0;// ��ʱ���һ��query��SMVֵ
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
				// queryId��ͬ,����preQueryId��SMVScore,д���ļ�,���arrayList��Ϣ,����terms��Ϣ
				if (!preQueryId.equalsIgnoreCase(terms[0])) {
					// ��arrayListת��Ϊdouble����
					scoreCount = arrayList.size();
					scores = new double[scoreCount];
					for (int i = 0; i < scoreCount; i++)
						scores[i] = arrayList.get(i);
					//���˲�ѯ�µ��ĵ���scoreCountС��k,��k��ΪscoreCount
					if(scoreCount<k) k=scoreCount;
					// ����computSMV()�����query��SMVֵ
					SMVScore = computeSMV(scores);
					
					// ��queryId��SMVScoreд���ļ�
					tempLine="queryId:\t" + preQueryId + "\tSMV:\t"+ SMVScore + "\n";
					//��tempLine��sMVScoreΪNaN,��ʱ��Ϊ0
					fileWriter.write(convertNaN(tempLine));
					
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
			// ���queryId��Ӧ��scoresδ����,������SMVScore,��д���ļ�
			// ��arrayListת��Ϊdouble����
			scoreCount = arrayList.size();
			scores = new double[scoreCount];
			for (int i = 0; i < scoreCount; i++)
				scores[i] = arrayList.get(i);
			//���˲�ѯ�µ��ĵ���scoreCountС��k,��k��ΪscoreCount
			if(scoreCount<k) k=scoreCount;
			// ����computSMV()�����query��SMVֵ
			SMVScore = computeSMV(scores);
			
			// ��queryId��SMVScoreд���ļ�
			tempLine="queryId:\t" + preQueryId + "\tSMV:\t" + SMVScore+ "\n";
			//��tempLine��sMVScoreΪNaN,��ʱ��Ϊ0
			fileWriter.write(convertNaN(tempLine));
			
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
	
	/**
	 * ��sMVֵΪNaN,��Ϊ0
	 * @return
	 */
	public String convertNaN(String tempLine){
		String[] terms=null;
		String newTempLine=null;
		//��ȥ��\n,ʹ��trim()����
		terms=tempLine.trim().split(" |\t");
		//��sMVΪNaN,��Ϊ0
		if(terms[3].equals("NaN")){
			terms[3]="0";
			newTempLine=terms[0]+"\t"+terms[1]+"\t"+terms[2]+"\t"+terms[3]+"\n";
			System.out.println("\n\n������¼"+tempLine+" �д���NaNֵ,�ѱ���Ϊ0,������sMVScore.runId�ļ��С�");
			return newTempLine;
		}else{
			return tempLine;
		}
	}

	
	public static void main(String[] args) {
		SMV sMV = new SMV();
		sMV.k=100;
		sMV.getSMVScores("./robustTrack2004/input.apl04rsTDNw5.inversed",
				"./robustTrack2004/sMVScore.apl04rsTDNw5");
		System.out
		.println("����input.pircRB04t3����ÿ��query��SMVֵ,����SMVScore�����ļ�,�����..");
	}

}
