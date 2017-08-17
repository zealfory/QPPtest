package predictor;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

public class SD {
	private int k=100;//kΪSD�Ľضϲ���
	
	public int getK() {
		return k;
	}
	public void setK(int k) {
		this.k = k;
	}

	// standardDev�㷨
	public double computeSD(double[] m) {//��ǰȷ��k<=m.length
		double midd = mean(m, k);
		double s = 0;
		for (int i = 0; i < k; i++) {
			s += (m[i] - midd) * (m[i] - midd);
		}
		double iner = s / (k-1);
		return Math.sqrt(iner);
	}

	public double mean(double[] m, int n) {
		double s = 0;
		for (int i = 0; i < n; i++) {
			s += m[i];
		}
		return s / n;
	}

	/**
	 * ��2016/06/02,�����޸�,������k_original������
	 * ����input.pircRB04t3����ÿ��query��SDֵ, ����SDScore�����ļ�
	 * @trackΪNLPR04OKapi
	 * */
	public void getSDScores(String input, String output) {
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
		double SDScore = 0;// ��ʱ���һ��query��SDֵ
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
				// queryId��ͬ,����preQueryId��SDScore,д���ļ�,���arrayList��Ϣ,����terms��Ϣ
				if (!preQueryId.equalsIgnoreCase(terms[0])) {
					// ��arrayListת��Ϊdouble����
					scoreCount = arrayList.size();
					scores = new double[scoreCount];
					for (int i = 0; i < scoreCount; i++)
						scores[i] = arrayList.get(i);
					//���˲�ѯ�µ��ĵ���scoreCountС��k,��k��ΪscoreCount
					if(scoreCount<k) k=scoreCount;
					// ����computSD()�����query��SDֵ
					SDScore = computeSD(scores);
					// ��queryId��SDScoreд���ļ�
					fileWriter.write("queryId:\t" + preQueryId + "\tSD:\t"
							+ SDScore + "\n");
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
			// ���queryId��Ӧ��scoresδ����,������SDScore,��д���ļ�
			// ��arrayListת��Ϊdouble����
			scoreCount = arrayList.size();
			scores = new double[scoreCount];
			for (int i = 0; i < scoreCount; i++)
				scores[i] = arrayList.get(i);
			//���˲�ѯ�µ��ĵ���scoreCountС��k,��k��ΪscoreCount
			if(scoreCount<k) k=scoreCount;
			// ����computSD()�����query��SDֵ
			SDScore = computeSD(scores);
			// ��queryId��SDScoreд���ļ�
			fileWriter.write("queryId:\t" + preQueryId + "\tSD:\t" + SDScore
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

	public static void main(String[] args) {
		SD sD = new SD();
		sD.k=100;
		sD.getSDScores("./robustTrack2004/input.apl04rsTDNw5.inversed",
				"./robustTrack2004/sDScore.apl04rsTDNw5");
		System.out
		.println("����input.pircRB04t3����ÿ��query��SDֵ,����SDScore�����ļ�,�����..");
	}

}
