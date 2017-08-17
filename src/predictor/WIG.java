package predictor;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;

public class WIG {
	private int k;//kΪ�ض�ֵ
	private HashMap<String,String> queryMap=null;//queryMap����queryId��queryLen
	
	public int getK() {
		return k;
	}
	public void setK(int k) {
		this.k = k;
	}
	public HashMap<String, String> getQueryMap() {
		return queryMap;
	}
	public void setQueryMap(HashMap<String, String> queryMap) {
		this.queryMap = queryMap;
	}

	public WIG(){
    	this.k=5;
    }
	/**
	 * ��queryMapΪ��,qlen��Ϊ1
	 * @param score
	 * @param n
	 * @param queryId
	 * @return
	 */
	public double computeWIG(double[] score,int n,String queryId){
		double s=0;
		double ScoreD=mean(score,n);
		int qlen=0;
		
		if(queryMap!=null){
			qlen=Integer.parseInt(queryMap.get(queryId));
		}else{
			qlen=1;
		}
		
		for(int i=0;i<k;i++){
			s=s+score[i]-ScoreD;
		}
		s=s/Math.sqrt(qlen)/k;
		return s;
	}
	
	public double mean(double[] m, int n){
		double s=0;
		for(int i=0;i<n;i++){
			s+=m[i];
		}
		return s/n;
	}
	
	/**
	 * ��2016/06/02,�����޸�,������k_original������
	 * ����input.pircRB04t3����ÿ��query��WIGֵ, ����WIGScore�����ļ�
	 * 
	 * */
	public void getWIGScores(String input, String output) {
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
		double WIGScore = 0;// ��ʱ���һ��query��WIGֵ
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
				// queryId��ͬ,����preQueryId��WIGScore,д���ļ�,���arrayList��Ϣ,����terms��Ϣ
				if (!preQueryId.equalsIgnoreCase(terms[0])) {
					// ��arrayListת��Ϊdouble����
					scoreCount = arrayList.size();
					scores = new double[scoreCount];
					for (int i = 0; i < scoreCount; i++)
						scores[i] = arrayList.get(i);
					//���˲�ѯ�µ��ĵ���scoreCountС��k,��k��ΪscoreCount
					if(scoreCount<k) k=scoreCount;
					// ����computWIG()�����query��WIGֵ
					WIGScore = computeWIG(scores, scoreCount,preQueryId);
					// ��queryId��WIGScoreд���ļ�
					fileWriter.write("queryId:\t" + preQueryId + "\tWIG:\t"
							+ WIGScore + "\n");
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
			// ���queryId��Ӧ��scoresδ����,������WIGScore,��д���ļ�
			// ��arrayListת��Ϊdouble����
			scoreCount = arrayList.size();
			scores = new double[scoreCount];
			for (int i = 0; i < scoreCount; i++)
				scores[i] = arrayList.get(i);
			//���˲�ѯ�µ��ĵ���scoreCountС��k,��k��ΪscoreCount
			if(scoreCount<k) k=scoreCount;
			// ����computWIG()�����query��WIGֵ
			WIGScore = computeWIG(scores, scoreCount,preQueryId);
			// ��queryId��WIGScoreд���ļ�
			fileWriter.write("queryId:\t" + preQueryId + "\tWIG:\t" + WIGScore
					+ "\n");
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
	public static void main(String[] args) throws IOException {
		
	}

}
