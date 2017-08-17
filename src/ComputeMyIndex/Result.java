package ComputeMyIndex;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;

public class Result {
	/**
	 * @param args
	 * numEach 表示每个查询查到的最大文档数.usually is 1000 or 10000
	 */
	private int countChar; // only used by method nextString()
	private final int numEach = 10000;

	public String nextString(StringBuffer s, int i) {// the same as the one
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

	public void computeIndex(String input, String output) throws IOException {
		String rLine = "initial line";
		double[] m = new double[numEach];
		File file = new File(input);
		FileReader fileR = new FileReader(file);
		LineNumberReader lnReader = new LineNumberReader(fileR);
		FileWriter fileW = new FileWriter(output, false);
		StringBuffer line = new StringBuffer(
				"new empty string for initialization");

		String id = "";
		String score = "";
		String run = "";
		String idNext = "";
		int i = 0;
		double myIndex = 0;
		rLine = lnReader.readLine();
		while (rLine != null) {
			i = 0;
			line.delete(0, line.length());
			line.append(rLine);
			do {
				id = nextString(line, 1);
				nextString(line, 2);
				nextString(line, 3);
				nextString(line, 4);
				score = nextString(line, 5);
				run = nextString(line, 6);
				m[i] = Double.parseDouble(score);
				i++;

				rLine = lnReader.readLine();
				line.delete(0, line.length());
				line.append(rLine);
				idNext = nextString(line, 1);
			} while (idNext.equals(id));
			//KDS的k为1000,m[]的初始长度为1000,计算KDS时需分析具体的input.result文件
			myIndex = new MyIndex().KDS(m, i);
			fileW.write(id + "\t\t" + myIndex + "\t\t" + run + "\n");
		}
		lnReader.close();
		fileW.close();
	}

	public static void main(String[] args) throws IOException {
		Result result = new Result();
		result.computeIndex("./Lucene/result", "./Lucene/kds_oo");
		System.out.println("result.computeIndex KDS is finished!");
	}

}
