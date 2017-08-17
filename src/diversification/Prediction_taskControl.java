package diversification;

import java.io.IOException;

public class Prediction_taskControl {
	
	/**
	 * 产生2009-2012年的预测结果
	 * @throws IOException 
	 * @throws InterruptedException 
	 * 
	 */
	public static void getGeneratedResult() throws IOException, InterruptedException{
		
		
		String packageName=null;
		String runId=null;
		int round=0;
		String queries_only=null;
		String input_subquery=null;
		
		//产生2009年的预测结果
		packageName="diversification";
		runId="indri2009xQuAD_60addRank";
		round=50;
		queries_only="2009queries_only";
		input_subquery="input.indri2009subquery_60addRank";
		System.out.println("\n\n1、track为"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		
		//产生2010年的预测结果
		packageName="diversification";
		runId="indri2010xQuAD_60addRank";
		round=47;
		queries_only="2010queries_only";
		input_subquery="input.indri2010subquery_60addRank";
		System.out.println("\n\n2、track为"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		
		//产生2011年的预测结果
		packageName="diversification";
		runId="indri2011xQuAD_60addRank";
		round=50;
		queries_only="2011queries_only";
		input_subquery="input.indri2011subquery_60addRank";
		System.out.println("\n\n3、track为"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		
		//产生2012年的预测结果
		packageName="diversification";
		runId="indri2012xQuAD_60addRank";
		round=48;
		queries_only="2012queries_only";
		input_subquery="input.indri2012subquery_60addRank";
		System.out.println("\n\n4、track为"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		System.out.println("产生2009-2012年的预测结果,已完成..");
		
	}
	/**
	 * 转换input_subquery文件的queryId格式,转换后的格式为mainQueryId-subQueryId
	 * @throws Exception 
	 */
	public static void convert_queryId() throws Exception{
		
		String input=null;
		//转换2009年的input_subquery文件的格式
		input="./diversification/input.indri2009subquery_60addRank";
		predictorIA_SUM.Format_div2010.queryIdConvertion(input);
		
		//转换2010年的input_subquery文件的格式
		input="./diversification/input.indri2010subquery_60addRank";
		predictorIA_SUM.Format_div2010.queryIdConvertion(input);
		
		//转换2011年的input_subquery文件的格式
		input="./diversification/input.indri2011subquery_60addRank";
		predictorIA_SUM.Format_div2010.queryIdConvertion(input);
		
		//转换2012年的input_subquery文件的格式
		input="./diversification/input.indri2012subquery_60addRank";
		predictorIA_SUM.Format_div2010.queryIdConvertion(input);
		
	}
	/**
	 * @throws Exception 
	 * 
	 */
	public static void processTask() throws Exception{
		//转换2009-2012年input_subquery文件的queryId格式
		convert_queryId();
		
		//产生2009-2012年的预测结果
		getGeneratedResult();
		System.out.println("批量产生结果,已完成!");
	}
	/**
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception{
		
		//wait_forDelete();
		//wait_forDelete2();
		wait_forDelete_pm2();
		//wait_forDelete4();
		//wait_forDelete_combSUM();
		
	}
	/**
	 * 8个xQuAD系统,师姐的检索结果
	 * 可以删除这个方法
	 * @throws Exception 
	 */
	public static void wait_forDelete() throws Exception{
		//convert_queryId();

		String packageName=null;
		String runId=null;
		int round=0;
		String queries_only=null;
		String input_subquery=null;

		//产生2009年的预测结果
		packageName="diversification";
		runId="2009-0.5";
		round=50;
		queries_only="2009queries_only";
		input_subquery="input.indri2009subquery_60addRank";
		System.out.println("\n\n1、track为"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		runId="2009-0.6";
		System.out.println("\n\n2、track为"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);

		//产生2010年的预测结果
		packageName="diversification";
		runId="2010-0.5";
		round=48;
		queries_only="2010queries_only";
		input_subquery="input.indri2010subquery_60addRank";
		System.out.println("\n\n3、track为"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		runId="2010-0.6";
		System.out.println("\n\n4、track为"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);

		//产生2011年的预测结果
		packageName="diversification";
		runId="2011-0.5";
		round=50;
		queries_only="2011queries_only";
		input_subquery="input.indri2011subquery_60addRank";
		System.out.println("\n\n5、track为"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		runId="2011-0.6";
		System.out.println("\n\n6、track为"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);

		//产生2012年的预测结果
		packageName="diversification";
		runId="2012-0.5";
		round=50;
		queries_only="2012queries_only";
		input_subquery="input.indri2012subquery_60addRank";
		System.out.println("\n\n7、track为"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		runId="2012-0.6";
		System.out.println("\n\n8、track为"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		System.out.println("产生2009-2012年的预测结果,已完成..");
	}
	/**
	 * 4个 best xQuAD系统,师姐的检索结果
	 * @throws Exception 
	 */
	public static void wait_forDelete2() throws Exception{
		//convert_queryId();

		String packageName=null;
		String runId=null;
		int round=0;
		String queries_only=null;
		String input_subquery=null;

		//产生2009年的预测结果
		packageName="diversification";
		runId="2009-0.5";
		round=50;
		queries_only="2009queries_only";
		input_subquery="input.indri2009subquery_60addRank";
		System.out.println("\n\n1、track为"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);

		//产生2010年的预测结果
		packageName="diversification";
		runId="2010-0.8";
		round=48;
		queries_only="2010queries_only";
		input_subquery="input.indri2010subquery_60addRank";
		System.out.println("\n\n2、track为"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);

		//产生2011年的预测结果
		packageName="diversification";
		runId="2011-0.3";
		round=50;
		queries_only="2011queries_only";
		input_subquery="input.indri2011subquery_60addRank";
		System.out.println("\n\n3、track为"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);

		//产生2012年的预测结果
		packageName="diversification";
		runId="2012-0.8";
		round=50;
		queries_only="2012queries_only";
		input_subquery="input.indri2012subquery_60addRank";
		System.out.println("\n\n4、track为"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		System.out.println("产生2009-2012年的预测结果,已完成..");
	}
	/**
	 * 可以删除这个方法
	 * 在20170313,4 best pm2系统 my
	 * @throws Exception 
	 */
	public static void wait_forDelete_pm2() throws Exception{
		//convert_queryId();
		
		String packageName=null;
		String runId=null;
		int round=0;
		String queries_only=null;
		String input_subquery=null;
		
		//产生2009年的预测结果
		packageName="diversification";
		runId="indri2009pm2_60addRank";
		round=50;
		queries_only="2009queries_only";
		input_subquery="input.indri2009subquery_60addRank";
		System.out.println("\n\n1、track为"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		
		//产生2010年的预测结果
		packageName="diversification";
		runId="indri2010pm2_60addRank";
		round=47;
		queries_only="2010queries_only";
		input_subquery="input.indri2010subquery_60addRank";
		System.out.println("\n\n2、track为"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		
		//产生2011年的预测结果
		packageName="diversification";
		runId="indri2011pm2_60addRank";
		round=50;
		queries_only="2011queries_only";
		input_subquery="input.indri2011subquery_60addRank";
		System.out.println("\n\n3、track为"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		
		//产生2012年的预测结果
		packageName="diversification";
		runId="indri2012pm2_60addRank";
		round=48;
		queries_only="2012queries_only";
		input_subquery="input.indri2012subquery_60addRank";
		System.out.println("\n\n4、track为"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		System.out.println("产生2009-2012年的预测结果,已完成..");
	}
	
	/**
	 * 可以删除这个方法
	 * 在20170313,4 best combSUM系统 my
	 * @throws Exception 
	 */
	public static void wait_forDelete_combSUM() throws Exception{
		//convert_queryId();
		
		String packageName=null;
		String runId=null;
		int round=0;
		String queries_only=null;
		String input_subquery=null;
		
		//产生2009年的预测结果
		packageName="diversification";
		runId="indri2009combSUM_60addRank";
		round=50;
		queries_only="2009queries_only";
		input_subquery="input.indri2009subquery_60addRank";
		System.out.println("\n\n1、track为"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		
		//产生2010年的预测结果
		packageName="diversification";
		runId="indri2010combSUM_60addRank";
		round=47;
		queries_only="2010queries_only";
		input_subquery="input.indri2010subquery_60addRank";
		System.out.println("\n\n2、track为"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		
		//产生2011年的预测结果
		packageName="diversification";
		runId="indri2011combSUM_60addRank";
		round=50;
		queries_only="2011queries_only";
		input_subquery="input.indri2011subquery_60addRank";
		System.out.println("\n\n3、track为"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		
		//产生2012年的预测结果
		packageName="diversification";
		runId="indri2012combSUM_60addRank";
		round=48;
		queries_only="2012queries_only";
		input_subquery="input.indri2012subquery_60addRank";
		System.out.println("\n\n4、track为"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		System.out.println("产生2009-2012年的预测结果,已完成..");
	}
	
	
	/**
	 * 可以删除这个方法
	 * 4 best pm2系统 师姐的检索结果 已删除了得分为0.0的文档
	 * @throws Exception 
	 */
	public static void wait_forDelete4() throws Exception{
		//convert_queryId();

		String packageName=null;
		String runId=null;
		int round=0;
		String queries_only=null;
		String input_subquery=null;

		//产生2009年的预测结果
		packageName="diversification";
		runId="2009-0.5";
		round=48;
		queries_only="2009queries_only";
		input_subquery="input.indri2009subquery_60addRank";
		System.out.println("\n\n1、track为"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);

		//产生2010年的预测结果
		packageName="diversification";
		runId="2010-0.4";
		round=47;
		queries_only="2010queries_only";
		input_subquery="input.indri2010subquery_60addRank";
		System.out.println("\n\n2、track为"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);

		//产生2011年的预测结果
		packageName="diversification";
		runId="2011-0.6";
		round=50;
		queries_only="2011queries_only";
		input_subquery="input.indri2011subquery_60addRank";
		System.out.println("\n\n3、track为"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);

		//产生2012年的预测结果
		packageName="diversification";
		runId="2012-0.3";
		round=50;
		queries_only="2012queries_only";
		input_subquery="input.indri2012subquery_60addRank";
		System.out.println("\n\n4、track为"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		System.out.println("产生2009-2012年的预测结果,已完成..");
	}

}
