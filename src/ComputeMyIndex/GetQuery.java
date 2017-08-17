package ComputeMyIndex;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;

public class GetQuery {
	private String path;
	private String[] query = new String[500];// initial size
	private int numQuery = 0;// Query数

	public GetQuery(String path) {
		this.path = path;
	}

	public void process(String input) throws IOException {
		String rLine = null, s = null;
		File file = null;
		FileReader fileR = null;
		LineNumberReader lnReader = null;
		try {
			file = new File(input);
			fileR = new FileReader(file);
			lnReader = new LineNumberReader(fileR);
			while ((rLine = lnReader.readLine()) != null) {
				if (rLine.matches("<desc>.+")) {
					lnReader.readLine();
					s = "";
					while (!(rLine = lnReader.readLine()).matches("</top>")) {//确认</top> 无空格
						s += rLine + " ";
					}
					query[numQuery++] = s;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("读取数据错误！");
		} finally {
			lnReader.close();
		}
	}

	/**
	 * return query
	 * */
	public String[] getQ() {
		try {
			process(path);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("文件关闭错误..");
		}
		return query;
	}

	public static void main(String[] args) throws IOException {
		GetQuery gq = new GetQuery("./TREC4/topics.201-250");
		String[] que = gq.getQ();
		String[] Qlen = null;
		int num = 200;
		
		FileWriter fileWriter=null;
		fileWriter=new FileWriter("./TREC4/topics.new.201-250");
		
		for (int j = 0; j < que.length; j++) {
			if (que[j] != null) {
				System.out.println(que[j]);
				
				Qlen=que[j].trim().split("  | ");
				
				System.out.println(Qlen.length);
				num++;
				fileWriter.write(String.valueOf(num)+" "+String.valueOf(Qlen.length)+"\n");
				
			}
		}
		fileWriter.close();
		
		System.out.println("success..");
		System.out.println(num);
	}
}
