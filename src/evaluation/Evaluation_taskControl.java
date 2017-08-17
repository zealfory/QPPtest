package evaluation;

import java.io.IOException;
import utils.Arith;

public class Evaluation_taskControl {
	
	/**
	 * ����input.runId,qrels�ļ�,����summary�ļ�
	 * @throws Exception 
	 */
	public static void generateSummary() throws Exception{
		
		double lanta=0;//lanta�Բ���0.1,��0���ӵ�1
		String input=null;
		String qrelfile=null;
		String output=null;
		
		//����2009���summary�ļ�
		qrelfile="./diversification/qrels.2009";
		for(int i=0;i<11;i++){
			input="./diversification/input.indri2009xQuAD_60addRank"+lanta;
			output="./diversification/summary.ndeval.indri2009xQuAD_60addRank"+lanta;
			Ndeval.generateSummary(input, qrelfile, output);
			//lanta����0.1
			lanta=Arith.add(lanta,0.1);
		}
		
		//����2010���summary�ļ�
		lanta=0;//����lantaΪ0
		qrelfile="./diversification/qrels.2010";
		for(int i=0;i<11;i++){
			input="./diversification/input.indri2010xQuAD_60addRank"+lanta;
			output="./diversification/summary.ndeval.indri2010xQuAD_60addRank"+lanta;
			Ndeval.generateSummary(input, qrelfile, output);
			//lanta����0.1
			lanta=Arith.add(lanta,0.1);
		}
		
		//����2011���summary�ļ�
		lanta=0;//����lantaΪ0
		qrelfile="./diversification/qrels.2011";
		for(int i=0;i<11;i++){
			input="./diversification/input.indri2011xQuAD_60addRank"+lanta;
			output="./diversification/summary.ndeval.indri2011xQuAD_60addRank"+lanta;
			Ndeval.generateSummary(input, qrelfile, output);
			//lanta����0.1
			lanta=Arith.add(lanta,0.1);
		}
		
		//����2012���summary�ļ�
		lanta=0;//����lantaΪ0
		qrelfile="./diversification/qrels.2012";
		for(int i=0;i<11;i++){
			input="./diversification/input.indri2012xQuAD_60addRank"+lanta;
			output="./diversification/summary.ndeval.indri2012xQuAD_60addRank"+lanta;
			Ndeval.generateSummary(input, qrelfile, output);
			//lanta����0.1
			lanta=Arith.add(lanta,0.1);
		}
				
	}
	/**
	 * ȷ���������Ԥ���2009-2012���summary��input�ļ�
	 * @throws IOException 
	 */
	public static void generateSummaryFileAndInputFile_final() throws IOException{
		
		String summaryFile=null;
		String inputFile=null;
		//��������Ԥ���2009���summary��input�ļ�
		summaryFile="./diversification/summary.ndeval.indri2009xQuAD_60addRank";
		inputFile="./diversification/input.indri2009xQuAD_60addRank";
		Summary_analy.analyze_bestLanta(summaryFile, inputFile);
		
		//��������Ԥ���2010���summary��input�ļ�
		summaryFile="./diversification/summary.ndeval.indri2010xQuAD_60addRank";
		inputFile="./diversification/input.indri2010xQuAD_60addRank";
		Summary_analy.analyze_bestLanta(summaryFile, inputFile);
		
		//��������Ԥ���2011���summary��input�ļ�
		summaryFile="./diversification/summary.ndeval.indri2011xQuAD_60addRank";
		inputFile="./diversification/input.indri2011xQuAD_60addRank";
		Summary_analy.analyze_bestLanta(summaryFile, inputFile);
		
		//��������Ԥ���2011���summary��input�ļ�
		summaryFile="./diversification/summary.ndeval.indri2012xQuAD_60addRank";
		inputFile="./diversification/input.indri2012xQuAD_60addRank";
		Summary_analy.analyze_bestLanta(summaryFile, inputFile);
				
	}
	/**
	 * @throws Exception 
	 * 
	 */
	public static void processTask() throws Exception{
		//����input.runId,qrels�ļ�,����summary�ļ�
		generateSummary();
		
		//ȷ���������Ԥ���2009-2012���summary��input�ļ�
		generateSummaryFileAndInputFile_final();
	}
	/**
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception{
		/*//����input.runId,qrels�ļ�,����summary�ļ�
		generateSummary();
		
		//ȷ���������Ԥ���2009-2012���summary��input�ļ�
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
	 * ����ɾ���������
	 * @throws Exception 
	 */
	public static void wait_forDelete() throws Exception{
		String input=null;
		String qrelfile=null;
		String output=null;
		
		//����2009���summary�ļ�
		qrelfile="./diversification/qrels.2009";
		input="./diversification/input.2009-0.5";
		output="./diversification/summary.ndeval.2009-0.5";
		Ndeval.generateSummary(input, qrelfile, output);
		input="./diversification/input.2009-0.6";
		output="./diversification/summary.ndeval.2009-0.6";
		Ndeval.generateSummary(input, qrelfile, output);
		
		//����2010���summary�ļ�
		qrelfile="./diversification/qrels.2010";
		input="./diversification/input.2010-0.5";
		output="./diversification/summary.ndeval.2010-0.5";
		Ndeval.generateSummary(input, qrelfile, output);
		input="./diversification/input.2010-0.6";
		output="./diversification/summary.ndeval.2010-0.6";
		Ndeval.generateSummary(input, qrelfile, output);
		
		//����2011���summary�ļ�
		qrelfile="./diversification/qrels.2011";
		input="./diversification/input.2011-0.5";
		output="./diversification/summary.ndeval.2011-0.5";
		Ndeval.generateSummary(input, qrelfile, output);
		input="./diversification/input.2011-0.6";
		output="./diversification/summary.ndeval.2011-0.6";
		Ndeval.generateSummary(input, qrelfile, output);
		
		//����2012���summary�ļ�
		qrelfile="./diversification/qrels.2012";
		input="./diversification/input.2012-0.5";
		output="./diversification/summary.ndeval.2012-0.5";
		Ndeval.generateSummary(input, qrelfile, output);
		input="./diversification/input.2012-0.6";
		output="./diversification/summary.ndeval.2012-0.6";
		Ndeval.generateSummary(input, qrelfile, output);
		
	}
	/**
	 * ����ɾ���������
	 * @throws Exception 
	 */
	public static void wait_forDelete2() throws Exception{
		double lanta=0;//lanta�Բ���0.1,��0���ӵ�1
		String input=null;
		String qrelfile=null;
		String output=null;
		
		//����2009���summary�ļ�
		qrelfile="./diversification/qrels.2009";
		for(int i=0;i<10;i++){
			input="./diversification/input.2009-"+lanta;
			output="./diversification/summary.ndeval.2009-"+lanta;
			Ndeval.generateSummary(input, qrelfile, output);
			//lanta����0.1
			lanta=Arith.add(lanta,0.1);
		}
		
		//����2010���summary�ļ�
		lanta=0;//����lantaΪ0
		qrelfile="./diversification/qrels.2010";
		for(int i=0;i<10;i++){
			input="./diversification/input.2010-"+lanta;
			output="./diversification/summary.ndeval.2010-"+lanta;
			Ndeval.generateSummary(input, qrelfile, output);
			//lanta����0.1
			lanta=Arith.add(lanta,0.1);
		}
		
		//����2011���summary�ļ�
		lanta=0;//����lantaΪ0
		qrelfile="./diversification/qrels.2011";
		for(int i=0;i<10;i++){
			input="./diversification/input.2011-"+lanta;
			output="./diversification/summary.ndeval.2011-"+lanta;
			Ndeval.generateSummary(input, qrelfile, output);
			//lanta����0.1
			lanta=Arith.add(lanta,0.1);
		}
		
		//����2012���summary�ļ�
		lanta=0;//����lantaΪ0
		qrelfile="./diversification/qrels.2012";
		for(int i=0;i<10;i++){
			input="./diversification/input.2012-"+lanta;
			output="./diversification/summary.ndeval.2012-"+lanta;
			Ndeval.generateSummary(input, qrelfile, output);
			//lanta����0.1
			lanta=Arith.add(lanta,0.1);
		}
	}
	/**
	 * ����ɾ���������
	 * ����input.runId,qrels�ļ�,����summary�ļ� pm2
	 * @throws Exception 
	 */
	public static void wait_forDelete_pm2_summary() throws Exception{
		double lanta=0;//lanta�Բ���0.1,��0���ӵ�1
		String input=null;
		String qrelfile=null;
		String output=null;
		
		//����2009���summary�ļ�
		qrelfile="./diversification/qrels.2009";
		for(int i=0;i<11;i++){
			input="./diversification/input.indri2009pm2_60addRank"+lanta;
			output="./diversification/summary.ndeval.indri2009pm2_60addRank"+lanta;
			Ndeval.generateSummary(input, qrelfile, output);
			//lanta����0.1
			lanta=Arith.add(lanta,0.1);
		}
		
		//����2010���summary�ļ�
		lanta=0;//����lantaΪ0
		qrelfile="./diversification/qrels.2010";
		for(int i=0;i<11;i++){
			input="./diversification/input.indri2010pm2_60addRank"+lanta;
			output="./diversification/summary.ndeval.indri2010pm2_60addRank"+lanta;
			Ndeval.generateSummary(input, qrelfile, output);
			//lanta����0.1
			lanta=Arith.add(lanta,0.1);
		}
		
		//����2011���summary�ļ�
		lanta=0;//����lantaΪ0
		qrelfile="./diversification/qrels.2011";
		for(int i=0;i<11;i++){
			input="./diversification/input.indri2011pm2_60addRank"+lanta;
			output="./diversification/summary.ndeval.indri2011pm2_60addRank"+lanta;
			Ndeval.generateSummary(input, qrelfile, output);
			//lanta����0.1
			lanta=Arith.add(lanta,0.1);
		}
		
		//����2012���summary�ļ�
		lanta=0;//����lantaΪ0
		qrelfile="./diversification/qrels.2012";
		for(int i=0;i<11;i++){
			input="./diversification/input.indri2012pm2_60addRank"+lanta;
			output="./diversification/summary.ndeval.indri2012pm2_60addRank"+lanta;
			Ndeval.generateSummary(input, qrelfile, output);
			//lanta����0.1
			lanta=Arith.add(lanta,0.1);
		}
	}
	/**
	 * ����ɾ���������
	 * ȷ���������Ԥ���2009-2012���summary��input�ļ� pm2
	 * @throws IOException 
	 */
	public static void wait_forDelete_pm2() throws IOException{
		String summaryFile=null;
		String inputFile=null;
		//��������Ԥ���2009���summary��input�ļ�
		summaryFile="./diversification/summary.ndeval.indri2009pm2_60addRank";
		inputFile="./diversification/input.indri2009pm2_60addRank";
		Summary_analy.analyze_bestLanta(summaryFile, inputFile);
		
		//��������Ԥ���2010���summary��input�ļ�
		summaryFile="./diversification/summary.ndeval.indri2010pm2_60addRank";
		inputFile="./diversification/input.indri2010pm2_60addRank";
		Summary_analy.analyze_bestLanta(summaryFile, inputFile);
		
		//��������Ԥ���2011���summary��input�ļ�
		summaryFile="./diversification/summary.ndeval.indri2011pm2_60addRank";
		inputFile="./diversification/input.indri2011pm2_60addRank";
		Summary_analy.analyze_bestLanta(summaryFile, inputFile);
		
		//��������Ԥ���2012���summary��input�ļ�
		summaryFile="./diversification/summary.ndeval.indri2012pm2_60addRank";
		inputFile="./diversification/input.indri2012pm2_60addRank";
		Summary_analy.analyze_bestLanta(summaryFile, inputFile);
	}
	/**
	 * ����ɾ���������
	 * @throws Exception 
	 */
	public static void wait_forDelete5() throws Exception{
		String input=null;
		String qrelfile=null;
		String output=null;
		
		//����2009���summary�ļ�
		qrelfile="./diversification/qrels.2009";
		input="./diversification/input.indri2009pm2_60addRank";
		output="./diversification/summary.ndeval.indri2009pm2_60addRank";
		Ndeval.generateSummary(input, qrelfile, output);
		
		//����2010���summary�ļ�
		qrelfile="./diversification/qrels.2010";
		input="./diversification/input.indri2010pm2_60addRank";
		output="./diversification/summary.ndeval.indri2010pm2_60addRank";
		Ndeval.generateSummary(input, qrelfile, output);
		
		//����2011���summary�ļ�
		qrelfile="./diversification/qrels.2011";
		input="./diversification/input.indri2011pm2_60addRank";
		output="./diversification/summary.ndeval.indri2011pm2_60addRank";
		Ndeval.generateSummary(input, qrelfile, output);
		
		//����2012���summary�ļ�
		qrelfile="./diversification/qrels.2012";
		input="./diversification/input.indri2012pm2_60addRank";
		output="./diversification/summary.ndeval.indri2012pm2_60addRank";
		Ndeval.generateSummary(input, qrelfile, output);
	}
	/**
	 * input�ļ�Ϊʦ���4��best xQuAD input�ļ�
	 * ����ɾ���������
	 * @throws Exception 
	 */
	public static void wait_forDelete6() throws Exception{
		String input=null;
		String qrelfile=null;
		String output=null;
		
		//����2009���summary�ļ�
		qrelfile="./diversification/qrels.2009";
		input="./diversification/input.2009-0.5";
		output="./diversification/summary.ndeval.2009-0.5";
		Ndeval.generateSummary(input, qrelfile, output);
		
		//����2010���summary�ļ�
		qrelfile="./diversification/qrels.2010";
		input="./diversification/input.2010-0.8";
		output="./diversification/summary.ndeval.2010-0.8";
		Ndeval.generateSummary(input, qrelfile, output);
		
		//����2011���summary�ļ�
		qrelfile="./diversification/qrels.2011";
		input="./diversification/input.2011-0.3";
		output="./diversification/summary.ndeval.2011-0.3";
		Ndeval.generateSummary(input, qrelfile, output);
		
		//����2012���summary�ļ�
		qrelfile="./diversification/qrels.2012";
		input="./diversification/input.2012-0.8";
		output="./diversification/summary.ndeval.2012-0.8";
		Ndeval.generateSummary(input, qrelfile, output);
	}
	/**
	 * ����ɾ���������
	 * input�ļ�Ϊʦ���4��best pm2 input�ļ�,��ɾ���˵÷�Ϊ0.0���ĵ�
	 * @throws Exception 
	 */
	public static void wait_forDelete7() throws Exception{
		String input=null;
		String qrelfile=null;
		String output=null;
		
		//����2009���summary�ļ�
		qrelfile="./diversification/qrels.2009";
		input="./diversification/input.2009-0.5";
		output="./diversification/summary.ndeval.2009-0.5";
		Ndeval.generateSummary(input, qrelfile, output);
		
		//����2010���summary�ļ�
		qrelfile="./diversification/qrels.2010";
		input="./diversification/input.2010-0.4";
		output="./diversification/summary.ndeval.2010-0.4";
		Ndeval.generateSummary(input, qrelfile, output);
		
		//����2011���summary�ļ�
		qrelfile="./diversification/qrels.2011";
		input="./diversification/input.2011-0.6";
		output="./diversification/summary.ndeval.2011-0.6";
		Ndeval.generateSummary(input, qrelfile, output);
		
		//����2012���summary�ļ�
		qrelfile="./diversification/qrels.2012";
		input="./diversification/input.2012-0.3";
		output="./diversification/summary.ndeval.2012-0.3";
		Ndeval.generateSummary(input, qrelfile, output);
	}
	
	/**
	 * ����ɾ���������
	 * ����input.runId,qrels�ļ�,����summary�ļ� combSUM
	 * @throws Exception 
	 */
	public static void wait_forDelete_combSUM_summary() throws Exception{
		double lanta=0;//lanta�Բ���0.1,��0���ӵ�1
		String input=null;
		String qrelfile=null;
		String output=null;
		
		//����2009���summary�ļ�
		qrelfile="./diversification/qrels.2009";
		for(int i=0;i<11;i++){
			input="./diversification/input.indri2009combSUM_60addRank"+lanta;
			output="./diversification/summary.ndeval.indri2009combSUM_60addRank"+lanta;
			Ndeval.generateSummary(input, qrelfile, output);
			//lanta����0.1
			lanta=Arith.add(lanta,0.1);
		}
		
		//����2010���summary�ļ�
		lanta=0;//����lantaΪ0
		qrelfile="./diversification/qrels.2010";
		for(int i=0;i<11;i++){
			input="./diversification/input.indri2010combSUM_60addRank"+lanta;
			output="./diversification/summary.ndeval.indri2010combSUM_60addRank"+lanta;
			Ndeval.generateSummary(input, qrelfile, output);
			//lanta����0.1
			lanta=Arith.add(lanta,0.1);
		}
		
		//����2011���summary�ļ�
		lanta=0;//����lantaΪ0
		qrelfile="./diversification/qrels.2011";
		for(int i=0;i<11;i++){
			input="./diversification/input.indri2011combSUM_60addRank"+lanta;
			output="./diversification/summary.ndeval.indri2011combSUM_60addRank"+lanta;
			Ndeval.generateSummary(input, qrelfile, output);
			//lanta����0.1
			lanta=Arith.add(lanta,0.1);
		}
		
		//����2012���summary�ļ�
		lanta=0;//����lantaΪ0
		qrelfile="./diversification/qrels.2012";
		for(int i=0;i<11;i++){
			input="./diversification/input.indri2012combSUM_60addRank"+lanta;
			output="./diversification/summary.ndeval.indri2012combSUM_60addRank"+lanta;
			Ndeval.generateSummary(input, qrelfile, output);
			//lanta����0.1
			lanta=Arith.add(lanta,0.1);
		}
	}
	/**
	 * ����ɾ���������
	 * ȷ���������Ԥ���2009-2012���summary��input�ļ� combSUM
	 * @throws IOException 
	 */
	public static void wait_forDelete_combSUM() throws IOException{
		String summaryFile=null;
		String inputFile=null;
		//��������Ԥ���2009���summary��input�ļ�
		summaryFile="./diversification/summary.ndeval.indri2009combSUM_60addRank";
		inputFile="./diversification/input.indri2009combSUM_60addRank";
		Summary_analy.analyze_bestLanta(summaryFile, inputFile);
		
		//��������Ԥ���2010���summary��input�ļ�
		summaryFile="./diversification/summary.ndeval.indri2010combSUM_60addRank";
		inputFile="./diversification/input.indri2010combSUM_60addRank";
		Summary_analy.analyze_bestLanta(summaryFile, inputFile);
		
		//��������Ԥ���2011���summary��input�ļ�
		summaryFile="./diversification/summary.ndeval.indri2011combSUM_60addRank";
		inputFile="./diversification/input.indri2011combSUM_60addRank";
		Summary_analy.analyze_bestLanta(summaryFile, inputFile);
		
		//��������Ԥ���2012���summary��input�ļ�
		summaryFile="./diversification/summary.ndeval.indri2012combSUM_60addRank";
		inputFile="./diversification/input.indri2012combSUM_60addRank";
		Summary_analy.analyze_bestLanta(summaryFile, inputFile);
	}


}
