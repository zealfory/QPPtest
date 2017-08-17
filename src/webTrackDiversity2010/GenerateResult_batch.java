package webTrackDiversity2010;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 批量产生结果
 *
 */
public class GenerateResult_batch {
	
	/**
	 * 
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException{
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		String runIdFile=null;
		String tempLine=null;
		String packageName=null;
		String runId=null;
		int n=0;
		
		runIdFile="./webTrackDiversity2010/runId.txt";
		fileReader=new FileReader(runIdFile);
		bufferedReader=new BufferedReader(fileReader);
		packageName="webTrackDiversity2010";
		
		while((tempLine=bufferedReader.readLine())!=null){
			runId=tempLine.split("\\.")[1];
			System.out.println("\n\n"+(++n)+"、track为"+runId);
			GenerateResult.getGeneratedResult(packageName, runId);
			Thread.sleep(10000);
		}
		bufferedReader.close();
		System.out.println("批量产生结果,已完成!");
	}

}
