package temporary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Test1 {
	/**
	 * 根据robustTrack2004/testSet,提取queryId、query。
	 * @throws IOException 
	 * */
	public static void analyzeTestSet() throws IOException{
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		FileWriter fileWriter=null;
		String tempLine=null;
		
		fileReader=new FileReader("./robustTrack2004/testSet");
		bufferedReader=new BufferedReader(fileReader);
		fileWriter=new FileWriter("./robustTrack2004/testSet.extracted");
		while((tempLine=bufferedReader.readLine())!=null){
			if(tempLine.startsWith("<num> Number:")){//tempLine以<num>开头
				fileWriter.write(tempLine+"\n");
				
				tempLine=bufferedReader.readLine();
				if(tempLine.equalsIgnoreCase("")||tempLine.equalsIgnoreCase(" "))//若此行没有数据,读取下一行
					tempLine=bufferedReader.readLine();
				if(tempLine.startsWith("<title>"))//若此行以<title>开头,把此行写入文件
					fileWriter.write(tempLine);
				
				tempLine=bufferedReader.readLine();
				if(tempLine.equalsIgnoreCase("")||tempLine.equalsIgnoreCase(" "))//若此行没有内容,结束文件的上一行,空一行
					fileWriter.write("\n\n");
				if(!(tempLine.equalsIgnoreCase("")||tempLine.equalsIgnoreCase(" ")))//若此行为query,接着文件的上一行写入,结束此行,空一行。
					fileWriter.write(" "+tempLine+"\n\n");
			}
		}
		fileWriter.close();
		bufferedReader.close();
		System.out.println("根据robustTrack2004/testSet,提取queryId、query,已完成..");
	}
	public static void main(String[] args) throws IOException{
		analyzeTestSet();
	}

}
