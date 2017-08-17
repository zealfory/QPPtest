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
		
		//����TREC4���ݼ���ϵͳCnQst2
		packageName="trec";
		runId="CnQst2";
		round=49;
		queries_only=null;
		System.out.println("\n\n1��trackΪ"+runId);
		GenerateResult.getGeneratedResult(packageName,runId,round,queries_only);
		Thread.sleep(10000);
		
		//����TREC5���ݼ���ϵͳETHme1
		packageName="trec";
		runId="ETHme1";
		round=50;
		queries_only=null;
		System.out.println("\n\n2��trackΪ"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only);
		Thread.sleep(10000);
		
		//����robust2004���ݼ���ϵͳpircRB04t3
		packageName="trec";
		runId="pircRB04t3";
		round=249;
		queries_only=null;
		System.out.println("\n\n3��trackΪ"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only);
		Thread.sleep(10000);
		
		//����webTrack2001���ݼ���ϵͳfub01be2
		packageName="trec";
		runId="fub01be2";
		round=50;
		queries_only=null;
		System.out.println("\n\n4��trackΪ"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only);
		Thread.sleep(10000);
		
		//����webTrack2002���ݼ���ϵͳthutd5
		packageName="trec";
		runId="thutd5";
		round=49;
		queries_only=null;
		System.out.println("\n\n5��trackΪ"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only);
		Thread.sleep(10000);
		
		//����terabyte2004���ݼ���ϵͳuogTBQEL
		packageName="trec";
		runId="uogTBQEL";
		round=49;
		queries_only=null;
		System.out.println("\n\n6��trackΪ"+runId);
		terabyteTrack2004_0628.GenerateResult.getGeneratedResult(packageName, runId,round,queries_only);
		Thread.sleep(10000);
		
		//����webTrackAdhoc 2010���ݼ���ϵͳuogTrB67
		packageName="trec";
		runId="uogTrB67";
		round=48;
		queries_only=null;
		System.out.println("\n\n7��trackΪ"+runId);
		terabyteTrack2004_0628.GenerateResult.getGeneratedResult(packageName, runId,round,queries_only);
		Thread.sleep(10000);
		
		System.out.println("��������Ԥ�������,�����..");
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
