package diversification;

import java.io.IOException;

public class Prediction_taskControl {
	
	/**
	 * ����2009-2012���Ԥ����
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
		
		//����2009���Ԥ����
		packageName="diversification";
		runId="indri2009xQuAD_60addRank";
		round=50;
		queries_only="2009queries_only";
		input_subquery="input.indri2009subquery_60addRank";
		System.out.println("\n\n1��trackΪ"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		
		//����2010���Ԥ����
		packageName="diversification";
		runId="indri2010xQuAD_60addRank";
		round=47;
		queries_only="2010queries_only";
		input_subquery="input.indri2010subquery_60addRank";
		System.out.println("\n\n2��trackΪ"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		
		//����2011���Ԥ����
		packageName="diversification";
		runId="indri2011xQuAD_60addRank";
		round=50;
		queries_only="2011queries_only";
		input_subquery="input.indri2011subquery_60addRank";
		System.out.println("\n\n3��trackΪ"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		
		//����2012���Ԥ����
		packageName="diversification";
		runId="indri2012xQuAD_60addRank";
		round=48;
		queries_only="2012queries_only";
		input_subquery="input.indri2012subquery_60addRank";
		System.out.println("\n\n4��trackΪ"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		System.out.println("����2009-2012���Ԥ����,�����..");
		
	}
	/**
	 * ת��input_subquery�ļ���queryId��ʽ,ת����ĸ�ʽΪmainQueryId-subQueryId
	 * @throws Exception 
	 */
	public static void convert_queryId() throws Exception{
		
		String input=null;
		//ת��2009���input_subquery�ļ��ĸ�ʽ
		input="./diversification/input.indri2009subquery_60addRank";
		predictorIA_SUM.Format_div2010.queryIdConvertion(input);
		
		//ת��2010���input_subquery�ļ��ĸ�ʽ
		input="./diversification/input.indri2010subquery_60addRank";
		predictorIA_SUM.Format_div2010.queryIdConvertion(input);
		
		//ת��2011���input_subquery�ļ��ĸ�ʽ
		input="./diversification/input.indri2011subquery_60addRank";
		predictorIA_SUM.Format_div2010.queryIdConvertion(input);
		
		//ת��2012���input_subquery�ļ��ĸ�ʽ
		input="./diversification/input.indri2012subquery_60addRank";
		predictorIA_SUM.Format_div2010.queryIdConvertion(input);
		
	}
	/**
	 * @throws Exception 
	 * 
	 */
	public static void processTask() throws Exception{
		//ת��2009-2012��input_subquery�ļ���queryId��ʽ
		convert_queryId();
		
		//����2009-2012���Ԥ����
		getGeneratedResult();
		System.out.println("�����������,�����!");
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
	 * 8��xQuADϵͳ,ʦ��ļ������
	 * ����ɾ���������
	 * @throws Exception 
	 */
	public static void wait_forDelete() throws Exception{
		//convert_queryId();

		String packageName=null;
		String runId=null;
		int round=0;
		String queries_only=null;
		String input_subquery=null;

		//����2009���Ԥ����
		packageName="diversification";
		runId="2009-0.5";
		round=50;
		queries_only="2009queries_only";
		input_subquery="input.indri2009subquery_60addRank";
		System.out.println("\n\n1��trackΪ"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		runId="2009-0.6";
		System.out.println("\n\n2��trackΪ"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);

		//����2010���Ԥ����
		packageName="diversification";
		runId="2010-0.5";
		round=48;
		queries_only="2010queries_only";
		input_subquery="input.indri2010subquery_60addRank";
		System.out.println("\n\n3��trackΪ"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		runId="2010-0.6";
		System.out.println("\n\n4��trackΪ"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);

		//����2011���Ԥ����
		packageName="diversification";
		runId="2011-0.5";
		round=50;
		queries_only="2011queries_only";
		input_subquery="input.indri2011subquery_60addRank";
		System.out.println("\n\n5��trackΪ"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		runId="2011-0.6";
		System.out.println("\n\n6��trackΪ"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);

		//����2012���Ԥ����
		packageName="diversification";
		runId="2012-0.5";
		round=50;
		queries_only="2012queries_only";
		input_subquery="input.indri2012subquery_60addRank";
		System.out.println("\n\n7��trackΪ"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		runId="2012-0.6";
		System.out.println("\n\n8��trackΪ"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		System.out.println("����2009-2012���Ԥ����,�����..");
	}
	/**
	 * 4�� best xQuADϵͳ,ʦ��ļ������
	 * @throws Exception 
	 */
	public static void wait_forDelete2() throws Exception{
		//convert_queryId();

		String packageName=null;
		String runId=null;
		int round=0;
		String queries_only=null;
		String input_subquery=null;

		//����2009���Ԥ����
		packageName="diversification";
		runId="2009-0.5";
		round=50;
		queries_only="2009queries_only";
		input_subquery="input.indri2009subquery_60addRank";
		System.out.println("\n\n1��trackΪ"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);

		//����2010���Ԥ����
		packageName="diversification";
		runId="2010-0.8";
		round=48;
		queries_only="2010queries_only";
		input_subquery="input.indri2010subquery_60addRank";
		System.out.println("\n\n2��trackΪ"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);

		//����2011���Ԥ����
		packageName="diversification";
		runId="2011-0.3";
		round=50;
		queries_only="2011queries_only";
		input_subquery="input.indri2011subquery_60addRank";
		System.out.println("\n\n3��trackΪ"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);

		//����2012���Ԥ����
		packageName="diversification";
		runId="2012-0.8";
		round=50;
		queries_only="2012queries_only";
		input_subquery="input.indri2012subquery_60addRank";
		System.out.println("\n\n4��trackΪ"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		System.out.println("����2009-2012���Ԥ����,�����..");
	}
	/**
	 * ����ɾ���������
	 * ��20170313,4 best pm2ϵͳ my
	 * @throws Exception 
	 */
	public static void wait_forDelete_pm2() throws Exception{
		//convert_queryId();
		
		String packageName=null;
		String runId=null;
		int round=0;
		String queries_only=null;
		String input_subquery=null;
		
		//����2009���Ԥ����
		packageName="diversification";
		runId="indri2009pm2_60addRank";
		round=50;
		queries_only="2009queries_only";
		input_subquery="input.indri2009subquery_60addRank";
		System.out.println("\n\n1��trackΪ"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		
		//����2010���Ԥ����
		packageName="diversification";
		runId="indri2010pm2_60addRank";
		round=47;
		queries_only="2010queries_only";
		input_subquery="input.indri2010subquery_60addRank";
		System.out.println("\n\n2��trackΪ"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		
		//����2011���Ԥ����
		packageName="diversification";
		runId="indri2011pm2_60addRank";
		round=50;
		queries_only="2011queries_only";
		input_subquery="input.indri2011subquery_60addRank";
		System.out.println("\n\n3��trackΪ"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		
		//����2012���Ԥ����
		packageName="diversification";
		runId="indri2012pm2_60addRank";
		round=48;
		queries_only="2012queries_only";
		input_subquery="input.indri2012subquery_60addRank";
		System.out.println("\n\n4��trackΪ"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		System.out.println("����2009-2012���Ԥ����,�����..");
	}
	
	/**
	 * ����ɾ���������
	 * ��20170313,4 best combSUMϵͳ my
	 * @throws Exception 
	 */
	public static void wait_forDelete_combSUM() throws Exception{
		//convert_queryId();
		
		String packageName=null;
		String runId=null;
		int round=0;
		String queries_only=null;
		String input_subquery=null;
		
		//����2009���Ԥ����
		packageName="diversification";
		runId="indri2009combSUM_60addRank";
		round=50;
		queries_only="2009queries_only";
		input_subquery="input.indri2009subquery_60addRank";
		System.out.println("\n\n1��trackΪ"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		
		//����2010���Ԥ����
		packageName="diversification";
		runId="indri2010combSUM_60addRank";
		round=47;
		queries_only="2010queries_only";
		input_subquery="input.indri2010subquery_60addRank";
		System.out.println("\n\n2��trackΪ"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		
		//����2011���Ԥ����
		packageName="diversification";
		runId="indri2011combSUM_60addRank";
		round=50;
		queries_only="2011queries_only";
		input_subquery="input.indri2011subquery_60addRank";
		System.out.println("\n\n3��trackΪ"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		
		//����2012���Ԥ����
		packageName="diversification";
		runId="indri2012combSUM_60addRank";
		round=48;
		queries_only="2012queries_only";
		input_subquery="input.indri2012subquery_60addRank";
		System.out.println("\n\n4��trackΪ"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		System.out.println("����2009-2012���Ԥ����,�����..");
	}
	
	
	/**
	 * ����ɾ���������
	 * 4 best pm2ϵͳ ʦ��ļ������ ��ɾ���˵÷�Ϊ0.0���ĵ�
	 * @throws Exception 
	 */
	public static void wait_forDelete4() throws Exception{
		//convert_queryId();

		String packageName=null;
		String runId=null;
		int round=0;
		String queries_only=null;
		String input_subquery=null;

		//����2009���Ԥ����
		packageName="diversification";
		runId="2009-0.5";
		round=48;
		queries_only="2009queries_only";
		input_subquery="input.indri2009subquery_60addRank";
		System.out.println("\n\n1��trackΪ"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);

		//����2010���Ԥ����
		packageName="diversification";
		runId="2010-0.4";
		round=47;
		queries_only="2010queries_only";
		input_subquery="input.indri2010subquery_60addRank";
		System.out.println("\n\n2��trackΪ"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);

		//����2011���Ԥ����
		packageName="diversification";
		runId="2011-0.6";
		round=50;
		queries_only="2011queries_only";
		input_subquery="input.indri2011subquery_60addRank";
		System.out.println("\n\n3��trackΪ"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);

		//����2012���Ԥ����
		packageName="diversification";
		runId="2012-0.3";
		round=50;
		queries_only="2012queries_only";
		input_subquery="input.indri2012subquery_60addRank";
		System.out.println("\n\n4��trackΪ"+runId);
		GenerateResult.getGeneratedResult(packageName, runId,round,queries_only,input_subquery);
		Thread.sleep(10000);
		System.out.println("����2009-2012���Ԥ����,�����..");
	}

}
