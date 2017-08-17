package evaluation;

import java.io.IOException;
import utils.Arith;

public class Evaluation_taskControl {
	
	/**
	 * 根据input.runId,qrels文件,产生summary文件
	 * @throws Exception 
	 */
	public static void generateSummary() throws Exception{
		
		double lanta=0;//lanta以步长0.1,从0增加到1
		String input=null;
		String qrelfile=null;
		String output=null;
		
		//产生2009年的summary文件
		qrelfile="./diversification/qrels.2009";
		for(int i=0;i<11;i++){
			input="./diversification/input.indri2009xQuAD_60addRank"+lanta;
			output="./diversification/summary.ndeval.indri2009xQuAD_60addRank"+lanta;
			Ndeval.generateSummary(input, qrelfile, output);
			//lanta增加0.1
			lanta=Arith.add(lanta,0.1);
		}
		
		//产生2010年的summary文件
		lanta=0;//重置lanta为0
		qrelfile="./diversification/qrels.2010";
		for(int i=0;i<11;i++){
			input="./diversification/input.indri2010xQuAD_60addRank"+lanta;
			output="./diversification/summary.ndeval.indri2010xQuAD_60addRank"+lanta;
			Ndeval.generateSummary(input, qrelfile, output);
			//lanta增加0.1
			lanta=Arith.add(lanta,0.1);
		}
		
		//产生2011年的summary文件
		lanta=0;//重置lanta为0
		qrelfile="./diversification/qrels.2011";
		for(int i=0;i<11;i++){
			input="./diversification/input.indri2011xQuAD_60addRank"+lanta;
			output="./diversification/summary.ndeval.indri2011xQuAD_60addRank"+lanta;
			Ndeval.generateSummary(input, qrelfile, output);
			//lanta增加0.1
			lanta=Arith.add(lanta,0.1);
		}
		
		//产生2012年的summary文件
		lanta=0;//重置lanta为0
		qrelfile="./diversification/qrels.2012";
		for(int i=0;i<11;i++){
			input="./diversification/input.indri2012xQuAD_60addRank"+lanta;
			output="./diversification/summary.ndeval.indri2012xQuAD_60addRank"+lanta;
			Ndeval.generateSummary(input, qrelfile, output);
			//lanta增加0.1
			lanta=Arith.add(lanta,0.1);
		}
				
	}
	/**
	 * 确定最后用于预测的2009-2012年的summary和input文件
	 * @throws IOException 
	 */
	public static void generateSummaryFileAndInputFile_final() throws IOException{
		
		String summaryFile=null;
		String inputFile=null;
		//产生用于预测的2009年的summary和input文件
		summaryFile="./diversification/summary.ndeval.indri2009xQuAD_60addRank";
		inputFile="./diversification/input.indri2009xQuAD_60addRank";
		Summary_analy.analyze_bestLanta(summaryFile, inputFile);
		
		//产生用于预测的2010年的summary和input文件
		summaryFile="./diversification/summary.ndeval.indri2010xQuAD_60addRank";
		inputFile="./diversification/input.indri2010xQuAD_60addRank";
		Summary_analy.analyze_bestLanta(summaryFile, inputFile);
		
		//产生用于预测的2011年的summary和input文件
		summaryFile="./diversification/summary.ndeval.indri2011xQuAD_60addRank";
		inputFile="./diversification/input.indri2011xQuAD_60addRank";
		Summary_analy.analyze_bestLanta(summaryFile, inputFile);
		
		//产生用于预测的2011年的summary和input文件
		summaryFile="./diversification/summary.ndeval.indri2012xQuAD_60addRank";
		inputFile="./diversification/input.indri2012xQuAD_60addRank";
		Summary_analy.analyze_bestLanta(summaryFile, inputFile);
				
	}
	/**
	 * @throws Exception 
	 * 
	 */
	public static void processTask() throws Exception{
		//根据input.runId,qrels文件,产生summary文件
		generateSummary();
		
		//确定最后用于预测的2009-2012年的summary和input文件
		generateSummaryFileAndInputFile_final();
	}
	/**
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception{
		/*//根据input.runId,qrels文件,产生summary文件
		generateSummary();
		
		//确定最后用于预测的2009-2012年的summary和input文件
		generateSummaryFileAndInputFile_final();
		*/
		//wait_forDelete();
		//wait_forDelete2();
		wait_forDelete_pm2_summary();
		wait_forDelete_pm2();
		//wait_forDelete5();
		//wait_forDelete6();
		//wait_forDelete7();
		
		//wait_forDelete_combSUM_summary();
		//wait_forDelete_combSUM();
		
	}
	/**
	 * 可以删除这个方法
	 * @throws Exception 
	 */
	public static void wait_forDelete() throws Exception{
		String input=null;
		String qrelfile=null;
		String output=null;
		
		//产生2009年的summary文件
		qrelfile="./diversification/qrels.2009";
		input="./diversification/input.2009-0.5";
		output="./diversification/summary.ndeval.2009-0.5";
		Ndeval.generateSummary(input, qrelfile, output);
		input="./diversification/input.2009-0.6";
		output="./diversification/summary.ndeval.2009-0.6";
		Ndeval.generateSummary(input, qrelfile, output);
		
		//产生2010年的summary文件
		qrelfile="./diversification/qrels.2010";
		input="./diversification/input.2010-0.5";
		output="./diversification/summary.ndeval.2010-0.5";
		Ndeval.generateSummary(input, qrelfile, output);
		input="./diversification/input.2010-0.6";
		output="./diversification/summary.ndeval.2010-0.6";
		Ndeval.generateSummary(input, qrelfile, output);
		
		//产生2011年的summary文件
		qrelfile="./diversification/qrels.2011";
		input="./diversification/input.2011-0.5";
		output="./diversification/summary.ndeval.2011-0.5";
		Ndeval.generateSummary(input, qrelfile, output);
		input="./diversification/input.2011-0.6";
		output="./diversification/summary.ndeval.2011-0.6";
		Ndeval.generateSummary(input, qrelfile, output);
		
		//产生2012年的summary文件
		qrelfile="./diversification/qrels.2012";
		input="./diversification/input.2012-0.5";
		output="./diversification/summary.ndeval.2012-0.5";
		Ndeval.generateSummary(input, qrelfile, output);
		input="./diversification/input.2012-0.6";
		output="./diversification/summary.ndeval.2012-0.6";
		Ndeval.generateSummary(input, qrelfile, output);
		
	}
	/**
	 * 可以删除这个方法
	 * @throws Exception 
	 */
	public static void wait_forDelete2() throws Exception{
		double lanta=0;//lanta以步长0.1,从0增加到1
		String input=null;
		String qrelfile=null;
		String output=null;
		
		//产生2009年的summary文件
		qrelfile="./diversification/qrels.2009";
		for(int i=0;i<10;i++){
			input="./diversification/input.2009-"+lanta;
			output="./diversification/summary.ndeval.2009-"+lanta;
			Ndeval.generateSummary(input, qrelfile, output);
			//lanta增加0.1
			lanta=Arith.add(lanta,0.1);
		}
		
		//产生2010年的summary文件
		lanta=0;//重置lanta为0
		qrelfile="./diversification/qrels.2010";
		for(int i=0;i<10;i++){
			input="./diversification/input.2010-"+lanta;
			output="./diversification/summary.ndeval.2010-"+lanta;
			Ndeval.generateSummary(input, qrelfile, output);
			//lanta增加0.1
			lanta=Arith.add(lanta,0.1);
		}
		
		//产生2011年的summary文件
		lanta=0;//重置lanta为0
		qrelfile="./diversification/qrels.2011";
		for(int i=0;i<10;i++){
			input="./diversification/input.2011-"+lanta;
			output="./diversification/summary.ndeval.2011-"+lanta;
			Ndeval.generateSummary(input, qrelfile, output);
			//lanta增加0.1
			lanta=Arith.add(lanta,0.1);
		}
		
		//产生2012年的summary文件
		lanta=0;//重置lanta为0
		qrelfile="./diversification/qrels.2012";
		for(int i=0;i<10;i++){
			input="./diversification/input.2012-"+lanta;
			output="./diversification/summary.ndeval.2012-"+lanta;
			Ndeval.generateSummary(input, qrelfile, output);
			//lanta增加0.1
			lanta=Arith.add(lanta,0.1);
		}
	}
	/**
	 * 可以删除这个方法
	 * 根据input.runId,qrels文件,产生summary文件 pm2
	 * @throws Exception 
	 */
	public static void wait_forDelete_pm2_summary() throws Exception{
		double lanta=0;//lanta以步长0.1,从0增加到1
		String input=null;
		String qrelfile=null;
		String output=null;
		
		//产生2009年的summary文件
		qrelfile="./diversification/qrels.2009";
		for(int i=0;i<11;i++){
			input="./diversification/input.indri2009pm2_60addRank"+lanta;
			output="./diversification/summary.ndeval.indri2009pm2_60addRank"+lanta;
			Ndeval.generateSummary(input, qrelfile, output);
			//lanta增加0.1
			lanta=Arith.add(lanta,0.1);
		}
		
		//产生2010年的summary文件
		lanta=0;//重置lanta为0
		qrelfile="./diversification/qrels.2010";
		for(int i=0;i<11;i++){
			input="./diversification/input.indri2010pm2_60addRank"+lanta;
			output="./diversification/summary.ndeval.indri2010pm2_60addRank"+lanta;
			Ndeval.generateSummary(input, qrelfile, output);
			//lanta增加0.1
			lanta=Arith.add(lanta,0.1);
		}
		
		//产生2011年的summary文件
		lanta=0;//重置lanta为0
		qrelfile="./diversification/qrels.2011";
		for(int i=0;i<11;i++){
			input="./diversification/input.indri2011pm2_60addRank"+lanta;
			output="./diversification/summary.ndeval.indri2011pm2_60addRank"+lanta;
			Ndeval.generateSummary(input, qrelfile, output);
			//lanta增加0.1
			lanta=Arith.add(lanta,0.1);
		}
		
		//产生2012年的summary文件
		lanta=0;//重置lanta为0
		qrelfile="./diversification/qrels.2012";
		for(int i=0;i<11;i++){
			input="./diversification/input.indri2012pm2_60addRank"+lanta;
			output="./diversification/summary.ndeval.indri2012pm2_60addRank"+lanta;
			Ndeval.generateSummary(input, qrelfile, output);
			//lanta增加0.1
			lanta=Arith.add(lanta,0.1);
		}
	}
	/**
	 * 可以删除这个方法
	 * 确定最后用于预测的2009-2012年的summary和input文件 pm2
	 * @throws IOException 
	 */
	public static void wait_forDelete_pm2() throws IOException{
		String summaryFile=null;
		String inputFile=null;
		//产生用于预测的2009年的summary和input文件
		summaryFile="./diversification/summary.ndeval.indri2009pm2_60addRank";
		inputFile="./diversification/input.indri2009pm2_60addRank";
		Summary_analy.analyze_bestLanta(summaryFile, inputFile);
		
		//产生用于预测的2010年的summary和input文件
		summaryFile="./diversification/summary.ndeval.indri2010pm2_60addRank";
		inputFile="./diversification/input.indri2010pm2_60addRank";
		Summary_analy.analyze_bestLanta(summaryFile, inputFile);
		
		//产生用于预测的2011年的summary和input文件
		summaryFile="./diversification/summary.ndeval.indri2011pm2_60addRank";
		inputFile="./diversification/input.indri2011pm2_60addRank";
		Summary_analy.analyze_bestLanta(summaryFile, inputFile);
		
		//产生用于预测的2012年的summary和input文件
		summaryFile="./diversification/summary.ndeval.indri2012pm2_60addRank";
		inputFile="./diversification/input.indri2012pm2_60addRank";
		Summary_analy.analyze_bestLanta(summaryFile, inputFile);
	}
	/**
	 * 可以删除这个方法
	 * @throws Exception 
	 */
	public static void wait_forDelete5() throws Exception{
		String input=null;
		String qrelfile=null;
		String output=null;
		
		//产生2009年的summary文件
		qrelfile="./diversification/qrels.2009";
		input="./diversification/input.indri2009pm2_60addRank";
		output="./diversification/summary.ndeval.indri2009pm2_60addRank";
		Ndeval.generateSummary(input, qrelfile, output);
		
		//产生2010年的summary文件
		qrelfile="./diversification/qrels.2010";
		input="./diversification/input.indri2010pm2_60addRank";
		output="./diversification/summary.ndeval.indri2010pm2_60addRank";
		Ndeval.generateSummary(input, qrelfile, output);
		
		//产生2011年的summary文件
		qrelfile="./diversification/qrels.2011";
		input="./diversification/input.indri2011pm2_60addRank";
		output="./diversification/summary.ndeval.indri2011pm2_60addRank";
		Ndeval.generateSummary(input, qrelfile, output);
		
		//产生2012年的summary文件
		qrelfile="./diversification/qrels.2012";
		input="./diversification/input.indri2012pm2_60addRank";
		output="./diversification/summary.ndeval.indri2012pm2_60addRank";
		Ndeval.generateSummary(input, qrelfile, output);
	}
	/**
	 * input文件为师姐的4个best xQuAD input文件
	 * 可以删除这个方法
	 * @throws Exception 
	 */
	public static void wait_forDelete6() throws Exception{
		String input=null;
		String qrelfile=null;
		String output=null;
		
		//产生2009年的summary文件
		qrelfile="./diversification/qrels.2009";
		input="./diversification/input.2009-0.5";
		output="./diversification/summary.ndeval.2009-0.5";
		Ndeval.generateSummary(input, qrelfile, output);
		
		//产生2010年的summary文件
		qrelfile="./diversification/qrels.2010";
		input="./diversification/input.2010-0.8";
		output="./diversification/summary.ndeval.2010-0.8";
		Ndeval.generateSummary(input, qrelfile, output);
		
		//产生2011年的summary文件
		qrelfile="./diversification/qrels.2011";
		input="./diversification/input.2011-0.3";
		output="./diversification/summary.ndeval.2011-0.3";
		Ndeval.generateSummary(input, qrelfile, output);
		
		//产生2012年的summary文件
		qrelfile="./diversification/qrels.2012";
		input="./diversification/input.2012-0.8";
		output="./diversification/summary.ndeval.2012-0.8";
		Ndeval.generateSummary(input, qrelfile, output);
	}
	/**
	 * 可以删除这个方法
	 * input文件为师姐的4个best pm2 input文件,已删除了得分为0.0的文档
	 * @throws Exception 
	 */
	public static void wait_forDelete7() throws Exception{
		String input=null;
		String qrelfile=null;
		String output=null;
		
		//产生2009年的summary文件
		qrelfile="./diversification/qrels.2009";
		input="./diversification/input.2009-0.5";
		output="./diversification/summary.ndeval.2009-0.5";
		Ndeval.generateSummary(input, qrelfile, output);
		
		//产生2010年的summary文件
		qrelfile="./diversification/qrels.2010";
		input="./diversification/input.2010-0.4";
		output="./diversification/summary.ndeval.2010-0.4";
		Ndeval.generateSummary(input, qrelfile, output);
		
		//产生2011年的summary文件
		qrelfile="./diversification/qrels.2011";
		input="./diversification/input.2011-0.6";
		output="./diversification/summary.ndeval.2011-0.6";
		Ndeval.generateSummary(input, qrelfile, output);
		
		//产生2012年的summary文件
		qrelfile="./diversification/qrels.2012";
		input="./diversification/input.2012-0.3";
		output="./diversification/summary.ndeval.2012-0.3";
		Ndeval.generateSummary(input, qrelfile, output);
	}
	
	/**
	 * 可以删除这个方法
	 * 根据input.runId,qrels文件,产生summary文件 combSUM
	 * @throws Exception 
	 */
	public static void wait_forDelete_combSUM_summary() throws Exception{
		double lanta=0;//lanta以步长0.1,从0增加到1
		String input=null;
		String qrelfile=null;
		String output=null;
		
		//产生2009年的summary文件
		qrelfile="./diversification/qrels.2009";
		for(int i=0;i<11;i++){
			input="./diversification/input.indri2009combSUM_60addRank"+lanta;
			output="./diversification/summary.ndeval.indri2009combSUM_60addRank"+lanta;
			Ndeval.generateSummary(input, qrelfile, output);
			//lanta增加0.1
			lanta=Arith.add(lanta,0.1);
		}
		
		//产生2010年的summary文件
		lanta=0;//重置lanta为0
		qrelfile="./diversification/qrels.2010";
		for(int i=0;i<11;i++){
			input="./diversification/input.indri2010combSUM_60addRank"+lanta;
			output="./diversification/summary.ndeval.indri2010combSUM_60addRank"+lanta;
			Ndeval.generateSummary(input, qrelfile, output);
			//lanta增加0.1
			lanta=Arith.add(lanta,0.1);
		}
		
		//产生2011年的summary文件
		lanta=0;//重置lanta为0
		qrelfile="./diversification/qrels.2011";
		for(int i=0;i<11;i++){
			input="./diversification/input.indri2011combSUM_60addRank"+lanta;
			output="./diversification/summary.ndeval.indri2011combSUM_60addRank"+lanta;
			Ndeval.generateSummary(input, qrelfile, output);
			//lanta增加0.1
			lanta=Arith.add(lanta,0.1);
		}
		
		//产生2012年的summary文件
		lanta=0;//重置lanta为0
		qrelfile="./diversification/qrels.2012";
		for(int i=0;i<11;i++){
			input="./diversification/input.indri2012combSUM_60addRank"+lanta;
			output="./diversification/summary.ndeval.indri2012combSUM_60addRank"+lanta;
			Ndeval.generateSummary(input, qrelfile, output);
			//lanta增加0.1
			lanta=Arith.add(lanta,0.1);
		}
	}
	/**
	 * 可以删除这个方法
	 * 确定最后用于预测的2009-2012年的summary和input文件 combSUM
	 * @throws IOException 
	 */
	public static void wait_forDelete_combSUM() throws IOException{
		String summaryFile=null;
		String inputFile=null;
		//产生用于预测的2009年的summary和input文件
		summaryFile="./diversification/summary.ndeval.indri2009combSUM_60addRank";
		inputFile="./diversification/input.indri2009combSUM_60addRank";
		Summary_analy.analyze_bestLanta(summaryFile, inputFile);
		
		//产生用于预测的2010年的summary和input文件
		summaryFile="./diversification/summary.ndeval.indri2010combSUM_60addRank";
		inputFile="./diversification/input.indri2010combSUM_60addRank";
		Summary_analy.analyze_bestLanta(summaryFile, inputFile);
		
		//产生用于预测的2011年的summary和input文件
		summaryFile="./diversification/summary.ndeval.indri2011combSUM_60addRank";
		inputFile="./diversification/input.indri2011combSUM_60addRank";
		Summary_analy.analyze_bestLanta(summaryFile, inputFile);
		
		//产生用于预测的2012年的summary和input文件
		summaryFile="./diversification/summary.ndeval.indri2012combSUM_60addRank";
		inputFile="./diversification/input.indri2012combSUM_60addRank";
		Summary_analy.analyze_bestLanta(summaryFile, inputFile);
	}


}
