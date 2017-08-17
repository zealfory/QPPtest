package Preprocess;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;

public class Preprocess {
	private int countChar=0; // only used by method nextString()
	public String nextString(StringBuffer s, int i) {// the same as the one eFile
		int start=0, end=0;
		if (i == 1) countChar = 0;
		while((countChar<s.length()) && (s.charAt(countChar) == '\b' || s.charAt(countChar) == '\t'|| s.charAt(countChar) == ' '|| s.charAt(countChar) == '\n'|| s.charAt(countChar) == '\f'||s.charAt(countChar)=='\r'))
			countChar++;
		start = countChar;
		end = start;
		while ((end < s.length()) && (s.charAt(end) != '\b')&& (s.charAt(end) != '\t') && (s.charAt(end) != ' ')&& (s.charAt(end) != '\n') && (s.charAt(end) != '\f')&& (s.charAt(end) != '\r'))
			end++;
		countChar = end;
		return (s.substring(start, end).trim());
	}
	
	public void process(String input, String output) throws IOException {
		String rLine=null;
		File file1=null;
		FileReader fileR=null;
		LineNumberReader lnReader=null;
		FileWriter fileW=null;
		BufferedWriter bufWriter=null;
		StringBuffer line=null;
		try {
			rLine = "initial line";
			file1 = new File(input);
			fileR = new FileReader(file1);
			lnReader = new LineNumberReader(fileR);
			fileW = new FileWriter(output, false);
			bufWriter = new BufferedWriter(fileW);
			line = new StringBuffer();
			rLine = lnReader.readLine();
			while (rLine != null) {
				line.delete(0, line.length());
				line.append(rLine);
				for (int i = 1; i <= 6; i++) {
					bufWriter.write(nextString(line, i) + '\t');
				}
				bufWriter.write('\n');
				rLine = lnReader.readLine();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			lnReader.close();
			bufWriter.close();
		}
	}

	public static void main(String[] args) {
		try {
			new Preprocess().process("./TREC5/map.ETHme1", "./TREC5/map.ETHme1.Normalized");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.print("task is finished");
	}

}
