package diversification;

import java.io.IOException;

public class Diversification_taskControl {
	
	/**
	 * 对input.indri2009mainquery,input.indri2009subquery文件使用
	 * 1/(60+rank)方式进行规范化
	 * @throws IOException 
	 */
	public static void normalizeInput_with60addRank() throws IOException{
		//规范化2009年的input文件
		String input1="./diversification/input.indri2009mainquery";
		String input2="./diversification/input.indri2009subquery";
		InputScoreNormalization.normalization_60addRank(input1);
		InputScoreNormalization.normalization_60addRank(input2);
		
		//规范化2010年的input文件
		input1="./diversification/input.indri2010mainquery";
		input2="./diversification/input.indri2010subquery";
		InputScoreNormalization.normalization_60addRank(input1);
		InputScoreNormalization.normalization_60addRank(input2);
		
		//规范化2011年的input文件
		input1="./diversification/input.indri2011mainquery";
		input2="./diversification/input.indri2011subquery";
		InputScoreNormalization.normalization_60addRank(input1);
		InputScoreNormalization.normalization_60addRank(input2);
		
		//规范化2012年的input文件
		input1="./diversification/input.indri2012mainquery";
		input2="./diversification/input.indri2012subquery";
		InputScoreNormalization.normalization_60addRank(input1);
		InputScoreNormalization.normalization_60addRank(input2);
		
	}
	/**
	 * 使用xQuAD方法对input文件中的文档进行多样化重排
	 * @throws Exception 
	 */
	public static void use_xQuAD() throws Exception{
		//多样化重排2009年的文档
		String initResultPath="./diversification/input.indri2009mainquery_60addRank";
		String subResultPath="./diversification/input.indri2009subquery_60addRank";
		String output="./diversification/input.indri2009xQuAD_60addRank";
		int qid_first=1;
		xQuAD.generate_xQuADFile(initResultPath, subResultPath, output, qid_first);

		//多样化重排2010年的文档
		initResultPath="./diversification/input.indri2010mainquery_60addRank";
		subResultPath="./diversification/input.indri2010subquery_60addRank";
		output="./diversification/input.indri2010xQuAD_60addRank";
		qid_first=51;
		xQuAD.generate_xQuADFile(initResultPath, subResultPath, output, qid_first);

		//多样化重排2011年的文档
		initResultPath="./diversification/input.indri2011mainquery_60addRank";
		subResultPath="./diversification/input.indri2011subquery_60addRank";
		output="./diversification/input.indri2011xQuAD_60addRank";
		qid_first=101;
		xQuAD.generate_xQuADFile(initResultPath, subResultPath, output, qid_first);

		//多样化重排2012年的文档
		initResultPath="./diversification/input.indri2012mainquery_60addRank";
		subResultPath="./diversification/input.indri2012subquery_60addRank";
		output="./diversification/input.indri2012xQuAD_60addRank";
		qid_first=151;
		xQuAD.generate_xQuADFile(initResultPath, subResultPath, output, qid_first);
		
	}
	/**
	 * 产生去除垃圾信息后的2009-2012年的input文件
	 * @throws IOException 
	 */
	public static void generateInput_spammed() throws IOException{
		String input1=null;
		String input2=null;
		//产生2009年的input文件 spammed
		input1="./diversification/input.indri2009mainquery";
		input2="./diversification/input.indri2009subquery";
		clueweb09spam.Spamming.remove_spam(input1,70);
		clueweb09spam.Spamming.remove_spam(input2,70);
		
		//产生2010年的input文件 spammed
		input1="./diversification/input.indri2010mainquery";
		input2="./diversification/input.indri2010subquery";
		clueweb09spam.Spamming.remove_spam(input1,70);
		clueweb09spam.Spamming.remove_spam(input2,70);
		
		//产生2011年的input文件 spammed
		input1="./diversification/input.indri2011mainquery";
		input2="./diversification/input.indri2011subquery";
		clueweb09spam.Spamming.remove_spam(input1,70);
		clueweb09spam.Spamming.remove_spam(input2,70);
		
		//产生2012年的input文件 spammed
		input1="./diversification/input.indri2012mainquery";
		input2="./diversification/input.indri2012subquery";
		clueweb09spam.Spamming.remove_spam(input1,70);
		clueweb09spam.Spamming.remove_spam(input2,70);
	}
	/**
	 * @throws Exception 
	 * 
	 */
	public static void processTask() throws Exception{
		/*
		//对input文件使用1/(60+rank)方式进行规范化
		normalizeInput_with60addRank();
		*/
		
		//使用xQuAD方法对input文件中的文档进行多样化重排
		//use_xQuAD();
		
		//使用PM2方法对input文件中的文档进行多样化重排
		use_pm2();
		
		//使用CombSUM方法对input文件中的文档进行多样化重排
		//use_combSUM();
	}
	/**
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception{
		/*//对input文件使用1/(60+rank)方式进行规范化
		normalizeInput_with60addRank();
		
		//使用xQuAD方法对input文件中的文档进行多样化重排
		use_xQuAD();
		*/
		
	}
	/**
	 * 可以删除这个方法
	 * 产生2009-2012年的pm2重排结果
	 * @throws Exception 
	 */
	public static void use_pm2() throws Exception{
		//多样化重排2009年的文档
		String initResultPath="./diversification/input.indri2009mainquery_60addRank";
		String subResultPath="./diversification/input.indri2009subquery_60addRank";
		String output="./diversification/input.indri2009pm2_60addRank";
		int qid_first=1;
		PM2.generate_pm2File(initResultPath, subResultPath, output, qid_first);

		//多样化重排2010年的文档
		initResultPath="./diversification/input.indri2010mainquery_60addRank";
		subResultPath="./diversification/input.indri2010subquery_60addRank";
		output="./diversification/input.indri2010pm2_60addRank";
		qid_first=51;
		PM2.generate_pm2File(initResultPath, subResultPath, output, qid_first);

		//多样化重排2011年的文档
		initResultPath="./diversification/input.indri2011mainquery_60addRank";
		subResultPath="./diversification/input.indri2011subquery_60addRank";
		output="./diversification/input.indri2011pm2_60addRank";
		qid_first=101;
		PM2.generate_pm2File(initResultPath, subResultPath, output, qid_first);

		//多样化重排2012年的文档
		initResultPath="./diversification/input.indri2012mainquery_60addRank";
		subResultPath="./diversification/input.indri2012subquery_60addRank";
		output="./diversification/input.indri2012pm2_60addRank";
		qid_first=151;
		PM2.generate_pm2File(initResultPath, subResultPath, output, qid_first);
	}
	
	/**
	 * 可以删除这个方法
	 * 产生2009-2012年的combSUM重排结果
	 * @throws Exception 
	 */
	public static void use_combSUM() throws Exception{
		//多样化重排2009年的文档
		String initResultPath="./diversification/input.indri2009mainquery_60addRank";
		String subResultPath="./diversification/input.indri2009subquery_60addRank";
		String output="./diversification/input.indri2009combSUM_60addRank";
		int qid_first=1;
		CombSUM.generate_combSUMFile(initResultPath, subResultPath, output, qid_first);

		//多样化重排2010年的文档
		initResultPath="./diversification/input.indri2010mainquery_60addRank";
		subResultPath="./diversification/input.indri2010subquery_60addRank";
		output="./diversification/input.indri2010combSUM_60addRank";
		qid_first=51;
		CombSUM.generate_combSUMFile(initResultPath, subResultPath, output, qid_first);

		//多样化重排2011年的文档
		initResultPath="./diversification/input.indri2011mainquery_60addRank";
		subResultPath="./diversification/input.indri2011subquery_60addRank";
		output="./diversification/input.indri2011combSUM_60addRank";
		qid_first=101;
		CombSUM.generate_combSUMFile(initResultPath, subResultPath, output, qid_first);

		//多样化重排2012年的文档
		initResultPath="./diversification/input.indri2012mainquery_60addRank";
		subResultPath="./diversification/input.indri2012subquery_60addRank";
		output="./diversification/input.indri2012combSUM_60addRank";
		qid_first=151;
		CombSUM.generate_combSUMFile(initResultPath, subResultPath, output, qid_first);
	}

}
