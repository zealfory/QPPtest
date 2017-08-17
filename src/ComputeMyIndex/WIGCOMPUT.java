package ComputeMyIndex;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;

public class WIGCOMPUT {
	/**
	 * @param args
	 * numEach 表示每个查询查到的最大文档数.usually is 1000 or 10000
	 */
	private int countChar; // only used by method nextString()
	private final int numEach = 10000;
	private String[] query;
	private int queryId = 0;

	public WIGCOMPUT(String topicPath) {
		GetQuery gq = new GetQuery(topicPath);
		query = gq.getQ();// size=500 from GetQuery
	}

	public String nextString(StringBuffer s, int i) {// the same as..
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

			myIndex = new WIG().wig(m, i, query[queryId++]);
			fileW.write(id + "\t\t" + myIndex + "\t\t" + run + "\n");
		}
		fileW.close();
		lnReader.close();
	}
	
	public static void main(String[] args) throws IOException {
		WIGCOMPUT wigComput = new WIGCOMPUT("./TREC4/topics.201-250");
		//是否与./TREC4/input.pircs2配对
		wigComput.computeIndex("./TREC4/input.pircs2", "./TREC4/WIGQ.pircs2");
		System.out.println("wigComput is finished! " + wigComput.queryId);
	}

}
