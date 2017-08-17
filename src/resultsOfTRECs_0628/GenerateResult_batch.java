package resultsOfTRECs_0628;

import java.io.IOException;


public class GenerateResult_batch {
	
	/**
	 * 
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	public static void getGeneratedResult() throws IOException, InterruptedException{
		String packageName=null;
		String runId=null;
		int round=0;
		String queries_only=null;
		
		//处理TREC4数据集的系统CnQst2
		packageName="trec";
		runId="CnQst2";
		round=49;
		queries_only=null;
		System.out.println("\n\n1、track为"+runId);
		GenerateResult.getGeneratedResult(packageName,runId,round,queries_only);
		Thread.sleep(10000);
		
		//处理TREC5数据集的系统ETHme1
		packageName="trec";
		runId="ETHme1";
		round=50;
		queries_only=null;
		System.out.println("\n\n2、track为"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only);
		Thread.sleep(10000);
		
		//处理robust2004数据集的系统pircRB04t3
		packageName="trec";
		runId="pircRB04t3";
		round=249;
		queries_only=null;
		System.out.println("\n\n3、track为"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only);
		Thread.sleep(10000);
		
		//处理webTrack2001数据集的系统fub01be2
		packageName="trec";
		runId="fub01be2";
		round=50;
		queries_only=null;
		System.out.println("\n\n4、track为"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only);
		Thread.sleep(10000);
		
		//处理webTrack2002数据集的系统thutd5
		packageName="trec";
		runId="thutd5";
		round=49;
		queries_only=null;
		System.out.println("\n\n5、track为"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only);
		Thread.sleep(10000);
		
		//处理terabyte2004数据集的系统uogTBQEL
		packageName="trec";
		runId="uogTBQEL";
		round=49;
		queries_only=null;
		System.out.println("\n\n6、track为"+runId);
		terabyteTrack2004_0628.GenerateResult.getGeneratedResult(packageName, runId,round,queries_only);
		Thread.sleep(10000);
		
		//处理webTrackAdhoc 2010数据集的系统uogTrB67
		packageName="trec";
		runId="uogTrB67";
		round=48;
		queries_only=null;
		System.out.println("\n\n7、track为"+runId);
		terabyteTrack2004_0628.GenerateResult.getGeneratedResult(packageName, runId,round,queries_only);
		Thread.sleep(10000);
		
		System.out.println("批量产生预测结果结果,已完成..");
	}
	/**
	 * 
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException{
		getGeneratedResult();
	}
}
