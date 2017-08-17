package terabyteTrack2004_0628;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
/**
 * ��predictor.SummaryAnalysis������,��summary.runId�ļ���ʽ��ͬ��������predictor.SummaryAnalysis�಻ͬ��
 * ��terabyteTrack2004��ʹ��
 * ����summary�ļ�
 */
public class SummaryAnalysis {
	public static int round = 0;// summary�ļ���query��
	private static int countChar = 0; // only used by method nextString()

	public static void extractAveragePrecision(String input, String output) {
		FileReader fileReader = null;
		LineNumberReader lineNumberReader = null;
		FileWriter fileWriter = null;
		String tempLine = null;
		int times=0;//��¼ѭ���Ĵ���
		try {
			fileReader = new FileReader(input);
			lineNumberReader = new LineNumberReader(fileReader);
			fileWriter = new FileWriter(output, false);
			
			while((tempLine=lineNumberReader.readLine())!=null){
				if(times<round&&tempLine.startsWith("map")){
					fileWriter.write(tempLine+"\n");
					times++;
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
	 * �淶map.runId�ļ�,�γ�map.normalized.runId�ļ�
	 * */
	public static void normalizeAveragePrecision(String input, String output) {
		String tempLine = null;
		FileReader fileReader = null;
		LineNumberReader lineNumberReader = null;
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		StringBuffer line = new StringBuffer();
		String runId=null;
		runId=input.substring(input.lastIndexOf(".")+1);//��input�����runId
		try {
			fileReader = new FileReader(input);
			lineNumberReader = new LineNumberReader(fileReader);
			fileWriter = new FileWriter(output, false);
			bufferedWriter = new BufferedWriter(fileWriter);
			tempLine = lineNumberReader.readLine();
			while (tempLine != null) {
				line.delete(0, line.length());
				line.append(tempLine);
				//һ�еĴ�����Ϊ3
				nextString(line,1);
				bufferedWriter.write("Queryid\t(Num):\t"+nextString(line,2)+"\t");
				bufferedWriter.write(runId+"\t"+nextString(line,3)+"\n");
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
		System.out.println("average Precision data have been normalized..");
	}

	public static void main(String[] args) {
		/*SummaryAnalysis.round = 249;
		extractAveragePrecision("./robustTrack2004/summary.apl04rsTDNw5", "./robustTrack2004/map.apl04rsTDNw5");
		
		normalizeAveragePrecision("./robustTrack2004/map.apl04rsTDNw5", "./robustTrack2004/map.normalized.apl04rsTDNw5");
		*/
		SummaryAnalysis.round=50;
		extractAveragePrecision("./resultsOfTrecs/summary.uogTBQEL","./resultsOfTrecs/map.uogTBQEL");
		normalizeAveragePrecision("./resultsOfTrecs/map.uogTBQEL","./resultsOfTrecs/map.normalized.uogTBQEL");
		
	}

}
