package diversification;

import java.io.IOException;

public class Diversification_taskControl {
	
	/**
	 * ��input.indri2009mainquery,input.indri2009subquery�ļ�ʹ��
	 * 1/(60+rank)��ʽ���й淶��
	 * @throws IOException 
	 */
	public static void normalizeInput_with60addRank() throws IOException{
		//�淶��2009���input�ļ�
		String input1="./diversification/input.indri2009mainquery";
		String input2="./diversification/input.indri2009subquery";
		InputScoreNormalization.normalization_60addRank(input1);
		InputScoreNormalization.normalization_60addRank(input2);
		
		//�淶��2010���input�ļ�
		input1="./diversification/input.indri2010mainquery";
		input2="./diversification/input.indri2010subquery";
		InputScoreNormalization.normalization_60addRank(input1);
		InputScoreNormalization.normalization_60addRank(input2);
		
		//�淶��2011���input�ļ�
		input1="./diversification/input.indri2011mainquery";
		input2="./diversification/input.indri2011subquery";
		InputScoreNormalization.normalization_60addRank(input1);
		InputScoreNormalization.normalization_60addRank(input2);
		
		//�淶��2012���input�ļ�
		input1="./diversification/input.indri2012mainquery";
		input2="./diversification/input.indri2012subquery";
		InputScoreNormalization.normalization_60addRank(input1);
		InputScoreNormalization.normalization_60addRank(input2);
		
	}
	/**
	 * ʹ��xQuAD������input�ļ��е��ĵ����ж���������
	 * @throws Exception 
	 */
	public static void use_xQuAD() throws Exception{
		//����������2009����ĵ�
		String initResultPath="./diversification/input.indri2009mainquery_60addRank";
		String subResultPath="./diversification/input.indri2009subquery_60addRank";
		String output="./diversification/input.indri2009xQuAD_60addRank";
		int qid_first=1;
		xQuAD.generate_xQuADFile(initResultPath, subResultPath, output, qid_first);

		//����������2010����ĵ�
		initResultPath="./diversification/input.indri2010mainquery_60addRank";
		subResultPath="./diversification/input.indri2010subquery_60addRank";
		output="./diversification/input.indri2010xQuAD_60addRank";
		qid_first=51;
		xQuAD.generate_xQuADFile(initResultPath, subResultPath, output, qid_first);

		//����������2011����ĵ�
		initResultPath="./diversification/input.indri2011mainquery_60addRank";
		subResultPath="./diversification/input.indri2011subquery_60addRank";
		output="./diversification/input.indri2011xQuAD_60addRank";
		qid_first=101;
		xQuAD.generate_xQuADFile(initResultPath, subResultPath, output, qid_first);

		//����������2012����ĵ�
		initResultPath="./diversification/input.indri2012mainquery_60addRank";
		subResultPath="./diversification/input.indri2012subquery_60addRank";
		output="./diversification/input.indri2012xQuAD_60addRank";
		qid_first=151;
		xQuAD.generate_xQuADFile(initResultPath, subResultPath, output, qid_first);
		
	}
	/**
	 * ����ȥ��������Ϣ���2009-2012���input�ļ�
	 * @throws IOException 
	 */
	public static void generateInput_spammed() throws IOException{
		String input1=null;
		String input2=null;
		//����2009���input�ļ� spammed
		input1="./diversification/input.indri2009mainquery";
		input2="./diversification/input.indri2009subquery";
		clueweb09spam.Spamming.remove_spam(input1,70);
		clueweb09spam.Spamming.remove_spam(input2,70);
		
		//����2010���input�ļ� spammed
		input1="./diversification/input.indri2010mainquery";
		input2="./diversification/input.indri2010subquery";
		clueweb09spam.Spamming.remove_spam(input1,70);
		clueweb09spam.Spamming.remove_spam(input2,70);
		
		//����2011���input�ļ� spammed
		input1="./diversification/input.indri2011mainquery";
		input2="./diversification/input.indri2011subquery";
		clueweb09spam.Spamming.remove_spam(input1,70);
		clueweb09spam.Spamming.remove_spam(input2,70);
		
		//����2012���input�ļ� spammed
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
		//��input�ļ�ʹ��1/(60+rank)��ʽ���й淶��
		normalizeInput_with60addRank();
		*/
		
		//ʹ��xQuAD������input�ļ��е��ĵ����ж���������
		//use_xQuAD();
		
		//ʹ��PM2������input�ļ��е��ĵ����ж���������
		use_pm2();
		
		//ʹ��CombSUM������input�ļ��е��ĵ����ж���������
		//use_combSUM();
	}
	/**
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception{
		/*//��input�ļ�ʹ��1/(60+rank)��ʽ���й淶��
		normalizeInput_with60addRank();
		
		//ʹ��xQuAD������input�ļ��е��ĵ����ж���������
		use_xQuAD();
		*/
		
	}
	/**
	 * ����ɾ���������
	 * ����2009-2012���pm2���Ž��
	 * @throws Exception 
	 */
	public static void use_pm2() throws Exception{
		//����������2009����ĵ�
		String initResultPath="./diversification/input.indri2009mainquery_60addRank";
		String subResultPath="./diversification/input.indri2009subquery_60addRank";
		String output="./diversification/input.indri2009pm2_60addRank";
		int qid_first=1;
		PM2.generate_pm2File(initResultPath, subResultPath, output, qid_first);

		//����������2010����ĵ�
		initResultPath="./diversification/input.indri2010mainquery_60addRank";
		subResultPath="./diversification/input.indri2010subquery_60addRank";
		output="./diversification/input.indri2010pm2_60addRank";
		qid_first=51;
		PM2.generate_pm2File(initResultPath, subResultPath, output, qid_first);

		//����������2011����ĵ�
		initResultPath="./diversification/input.indri2011mainquery_60addRank";
		subResultPath="./diversification/input.indri2011subquery_60addRank";
		output="./diversification/input.indri2011pm2_60addRank";
		qid_first=101;
		PM2.generate_pm2File(initResultPath, subResultPath, output, qid_first);

		//����������2012����ĵ�
		initResultPath="./diversification/input.indri2012mainquery_60addRank";
		subResultPath="./diversification/input.indri2012subquery_60addRank";
		output="./diversification/input.indri2012pm2_60addRank";
		qid_first=151;
		PM2.generate_pm2File(initResultPath, subResultPath, output, qid_first);
	}
	
	/**
	 * ����ɾ���������
	 * ����2009-2012���combSUM���Ž��
	 * @throws Exception 
	 */
	public static void use_combSUM() throws Exception{
		//����������2009����ĵ�
		String initResultPath="./diversification/input.indri2009mainquery_60addRank";
		String subResultPath="./diversification/input.indri2009subquery_60addRank";
		String output="./diversification/input.indri2009combSUM_60addRank";
		int qid_first=1;
		CombSUM.generate_combSUMFile(initResultPath, subResultPath, output, qid_first);

		//����������2010����ĵ�
		initResultPath="./diversification/input.indri2010mainquery_60addRank";
		subResultPath="./diversification/input.indri2010subquery_60addRank";
		output="./diversification/input.indri2010combSUM_60addRank";
		qid_first=51;
		CombSUM.generate_combSUMFile(initResultPath, subResultPath, output, qid_first);

		//����������2011����ĵ�
		initResultPath="./diversification/input.indri2011mainquery_60addRank";
		subResultPath="./diversification/input.indri2011subquery_60addRank";
		output="./diversification/input.indri2011combSUM_60addRank";
		qid_first=101;
		CombSUM.generate_combSUMFile(initResultPath, subResultPath, output, qid_first);

		//����������2012����ĵ�
		initResultPath="./diversification/input.indri2012mainquery_60addRank";
		subResultPath="./diversification/input.indri2012subquery_60addRank";
		output="./diversification/input.indri2012combSUM_60addRank";
		qid_first=151;
		CombSUM.generate_combSUMFile(initResultPath, subResultPath, output, qid_first);
	}

}
