package temporary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Test1 {
	/**
	 * ����robustTrack2004/testSet,��ȡqueryId��query��
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
			if(tempLine.startsWith("<num> Number:")){//tempLine��<num>��ͷ
				fileWriter.write(tempLine+"\n");
				
				tempLine=bufferedReader.readLine();
				if(tempLine.equalsIgnoreCase("")||tempLine.equalsIgnoreCase(" "))//������û������,��ȡ��һ��
					tempLine=bufferedReader.readLine();
				if(tempLine.startsWith("<title>"))//��������<title>��ͷ,�Ѵ���д���ļ�
					fileWriter.write(tempLine);
				
				tempLine=bufferedReader.readLine();
				if(tempLine.equalsIgnoreCase("")||tempLine.equalsIgnoreCase(" "))//������û������,�����ļ�����һ��,��һ��
					fileWriter.write("\n\n");
				if(!(tempLine.equalsIgnoreCase("")||tempLine.equalsIgnoreCase(" ")))//������Ϊquery,�����ļ�����һ��д��,��������,��һ�С�
					fileWriter.write(" "+tempLine+"\n\n");
			}
		}
		fileWriter.close();
		bufferedReader.close();
		System.out.println("����robustTrack2004/testSet,��ȡqueryId��query,�����..");
	}
	public static void main(String[] args) throws IOException{
		analyzeTestSet();
	}

}
