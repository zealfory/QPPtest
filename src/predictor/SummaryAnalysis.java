package predictor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;

/**
 * ����summary�ļ�,�õ�mapֵ
 * */
public class SummaryAnalysis {
	public static int round = 0;// summary�ļ���query��
	private static int countChar = 0; // only used by method nextString()
	private static int termSize = 0;// һ�еĴ�����, ��normalizeAveragePrecision()ʹ��
	
	public static int getTermSize() {
		return termSize;
	}
	public static void setTermSize(int termSize) {
		SummaryAnalysis.termSize = termSize;
	}

	public static void extractAveragePrecision(String input, String output) {
		FileReader fileReader = null;
		LineNumberReader lineNumberReader = null;
		FileWriter fileWriter = null;
		String tempLine = null;
		try {
			fileReader = new FileReader(input);
			lineNumberReader = new LineNumberReader(fileReader);
			fileWriter = new FileWriter(output, false);
			for (int i = 0; i < round; i++) {
				lineNumberReader.readLine();//��һ���ǿյ�,by zzm
				tempLine = lineNumberReader.readLine();
				fileWriter.write(tempLine + '\t');//д��id runId,by zzm
				for (int j = 1; j <= 18; j++) {
					tempLine = lineNumberReader.readLine();
				}
				fileWriter.write(tempLine + "\n");//��map����д�룬by zzm
				for (int j = 1; j <= 12; j++) {
					lineNumberReader.readLine();
				}
			}
			System.out.println("average Precision data have been extracted..");
		} catch (IOException e) {
			System.err.println("��ȡ���ݳ���!");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.close();
				fileReader.close();
			} catch (IOException e) {
				System.err.println("�ر�IO�ļ�������!");
				e.printStackTrace();
			}
		}
	}

	public static void showResult(String input) throws IOException {
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		String info = null;
		fileReader = new FileReader(input);
		bufferedReader = new BufferedReader(fileReader);
		while ((info = bufferedReader.readLine()) != null) {
			System.out.println(info);
		}
		bufferedReader.close();
	}

	public static String nextString(StringBuffer s, int i) {// the same as
		int start = 0, end = 0;
		if (i == 1)
			countChar = 0;
		while ((countChar < s.length())
				&& (s.charAt(countChar) == '\b' || s.charAt(countChar) == '\t'
				|| s.charAt(countChar) == ' '
				|| s.charAt(countChar) == '\n'
				|| s.charAt(countChar) == '\f' || s.charAt(countChar) == '\r'))
			countChar++;
		start = countChar;
		end = start;
		while ((end < s.length()) && (s.charAt(end) != '\b')
				&& (s.charAt(end) != '\t') && (s.charAt(end) != ' ')
				&& (s.charAt(end) != '\n') && (s.charAt(end) != '\f')
				&& (s.charAt(end) != '\r'))
			end++;
		countChar = end;
		return (s.substring(start, end).trim());
	}

	/**
	 * ȥ��map�ļ��еĿո�
	 * */
	public static void normalizeAveragePrecision(String input, String output) {
		String tempLine = null;
		FileReader fileReader = null;
		LineNumberReader lineNumberReader = null;
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		StringBuffer line = new StringBuffer();
		try {
			fileReader = new FileReader(input);
			lineNumberReader = new LineNumberReader(fileReader);
			fileWriter = new FileWriter(output, false);
			bufferedWriter = new BufferedWriter(fileWriter);
			tempLine = lineNumberReader.readLine();
			while (tempLine != null) {
				line.delete(0, line.length());
				line.append(tempLine);
				for (int i = 1; i <= termSize; i++) {
					bufferedWriter.write(nextString(line, i) + "\t");
				}
				bufferedWriter.write("\n");
				tempLine = lineNumberReader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedWriter.close();
				lineNumberReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("average Precision data have been normalized..");//�Ҿ�����formated��by zoey
	}

	public static void main(String[] args) {
		/*SummaryAnalysis.round = 249;
		extractAveragePrecision("./robustTrack2004/summary.apl04rsTDNw5", "./robustTrack2004/map.apl04rsTDNw5");
		termSize = 5;
		normalizeAveragePrecision("./robustTrack2004/map.apl04rsTDNw5", "./robustTrack2004/map.normalized.apl04rsTDNw5");
		*/
		/*SummaryAnalysis.round=50;
		extractAveragePrecision("./resultsOfTrecs/summary.ETHme1","./resultsOfTrecs/map.ETHme1");
		termSize=5;
		normalizeAveragePrecision("./resultsOfTRECs/map.ETHme1","./resultsOfTRECs/map.normalized.ETHme1");
		*/
		termSize=6;
		normalizeAveragePrecision("./resultsOfTRECs/input.ETHme1","./resultsOfTRECs/input.new.ETHme1");
		
		
	
	}

}
