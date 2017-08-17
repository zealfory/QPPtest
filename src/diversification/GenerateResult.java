package diversification;

import java.io.IOException;
import java.util.Scanner;

import utils.QueryLength;
import utils.SummaryAnalysis;


public class GenerateResult {

	public static void processSummary(String runId,String packageName,int round){
		
		SummaryAnalysis.round = round;
		
		//��ȡERR-IA@20��Ϣ
		SummaryAnalysis.extractERR_IA20("./"+packageName+"/summary.ndeval."+runId, "./"+packageName+"/err_IA20."+runId);
		
		/*
		//��ȡalpha-nDCG@20��Ϣ
		SummaryAnalysis.extract_alphaNDCG20("./"+packageName+"/summary.ndeval."+runId, "./"+packageName+"/alphaNDCG20."+runId);
		*/
		/*
		//��ȡNRBP��Ϣ
		SummaryAnalysis.extract_nRBP("./"+packageName+"/summary.ndeval."+runId, "./"+packageName+"/nRBP."+runId);
		*/
		
	}
	public static void processPrediction(String runId,String packageName,String queries_only,String input_subquery) throws IOException{
		
		//����NQC
		predictor.NQC predictorNQC=new predictor.NQC();
		predictorNQC.setK(100);//��predictorNQC��k��Ϊ1000
		predictorNQC.getNQCScores("./"+packageName+"/input."+runId, "./"+packageName+"/nQCScore."+runId);
		System.out.println("����input����ÿ��query��NQCֵ,����NQCScore�����ļ�,�����..");
		
		// ����SD2
		predictor.SD2 predictorSD2 = new predictor.SD2();
		predictorSD2.setX(0.5);// ��predictorSD2��x��Ϊ0.5
		predictorSD2.setQueryMap(QueryLength.getQueryLength("./"+packageName+"/"+queries_only));
		predictorSD2.getSD2Scores("./" + packageName + "/input." + runId, "./"+ packageName + "/sD2Score." + runId);
		System.out.println("����input����ÿ��query��SD2ֵ,����SD2Score�����ļ�,�����..");
		
		//����SMV
		predictor.SMV predictorSMV = new predictor.SMV();
		predictorSMV.setK(100);//��predictorSMV��k��Ϊ1000
		predictorSMV.getSMVScores("./"+packageName+"/input."+runId,"./"+packageName+"/sMVScore."+runId);
		System.out.println("����input����ÿ��query��SMVֵ,����SMVScore�����ļ�,�����..");
		
		//����WIG
		predictor.WIG predictorWIG=new predictor.WIG();
		predictorWIG.setK(5);//��predictorWIG��k��Ϊ5
		predictorWIG.setQueryMap(QueryLength.getQueryLength("./"+packageName+"/"+queries_only));
		predictorWIG.getWIGScores("./"+packageName+"/input."+runId,"./"+packageName+"/wIGScore."+runId);
		System.out.println("����input����ÿ��query��WIGֵ,����WIGScore�����ļ�,�����..");
		
		
		//����IA_SUM
		predictorIA_SUM.IA_SUM.k_subquery=1000;//���Ӳ�ѯsubquery�Ľضϲ�����Ϊ500
		predictorIA_SUM.IA_SUM.k_mainQuery=100;
		predictorIA_SUM.IA_SUM.getIA_SUMScores("./"+packageName+"/input."+runId, "./"+packageName+"/"+input_subquery, "./"+packageName+"/output_middle", "./"+packageName+"/iA_SUMScore."+runId);
		
		//����SD2MultiWIG
		newPredictor.SD2MultiWIG newPredictorSD2MultiWIG=new newPredictor.SD2MultiWIG();
		newPredictorSD2MultiWIG.getSD2MultiWIGScores("./"+packageName+"/input."+runId, "./"+packageName+"/sD2MultiWIGScore."+runId);
		System.out.println("����input����ÿ��query��SD2MultiWIGֵ,����sD2MultiWIGScore�����ļ�,�����..");
		
		//����IASUM2
		newPredictor.IASUM2 newPredictorIASUM2=new newPredictor.IASUM2();
		newPredictorIASUM2.getIASUM2Scores("./"+packageName+"/input."+runId, "./"+packageName+"/iASUM2Score."+runId);
		System.out.println("����input����ÿ��query��IASUM2ֵ,����iASUM2Score�����ļ�,�����..");
		
		//����IASUM3
		predictorIA_SUM.IASUM3.k_subquery=1000;//���Ӳ�ѯsubquery�Ľضϲ�����Ϊ500
		predictorIA_SUM.IASUM3.k_mainQuery=100;
		predictorIA_SUM.IASUM3.getIASUM3Scores("./"+packageName+"/input."+runId, "./"+packageName+"/"+input_subquery, "./"+packageName+"/output_middle", "./"+packageName+"/iASUM3Score."+runId);
		
		//����CF
		predictorIA_SUM.CF.k_subquery=1000;//���Ӳ�ѯsubquery�Ľضϲ�����Ϊ500
		predictorIA_SUM.CF.k_mainQuery=20;//������ѯmainQuery�Ľضϲ�����Ϊ20
		predictorIA_SUM.CF.getCFScores("./"+packageName+"/input."+runId, "./"+packageName+"/"+input_subquery, "./"+packageName+"/cFScore."+runId);
		
		//����CF2
		newPredictor.CF2 newPredictorCF2=new newPredictor.CF2();
		newPredictorCF2.getCF2Scores("./"+packageName+"/input."+runId, "./"+packageName+"/cF2Score."+runId);
		System.out.println("����input����ÿ��query��CF2ֵ,����cF2Score�����ļ�,�����..");
		
	}

	public static void main(String[] args) throws IOException {
		
	}
	
	/**
	 * �ɹ�GenerateResult_batch�����
	 * @param args
	 * @throws IOException
	 */
	public static void getGeneratedResult(String packageName,String runId,int round,String queries_only,String input_subquery) throws IOException {
		
		//����summary�ļ�,��ȡERR-IA@20��Ϣ,��ERR@20��Ϣ
		processSummary(runId,packageName,round);
		
		//����input�ļ�,����Ԥ���㷨,�õ�Ԥ����Ϣ
		processPrediction(runId,packageName,queries_only,input_subquery);
		
		//����Ԥ���㷨��ָ��,ȡ�ļ���
		String metric=null;
		//
		//
		//ִ��ǰ��ȷ�ϴ�ָ������
		metric="err_IA20"+"."+runId;
		
		
		//�鿴�Ƿ���ɾ�� Ԥ�����ļ��ж������Ϣ
		String yourOrder=null;
		Scanner scanner=new Scanner(System.in);
		while(true){
			System.out.println("�ȴ�������:ok, �����鿴�Ƿ���ɾ�� Ԥ�����ļ��ж������Ϣ��");
			yourOrder=scanner.nextLine();
			if(yourOrder.equalsIgnoreCase("ok")){
				System.out.println("��ʼִ��pearson,kendall�㷨..");
				break;
			}
		}
		//scanner.close();
		
		
		//Ԥ��ֵ��ERR-IA@20��pearson kendallϵ��:
		System.out.println("Ԥ��ֵ��ERR-IA@20��pearson kendallϵ��:\n");
		//����pearson�㷨
		try {
			
			//nQC��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/nQCScore."+runId,"./"+packageName+"/"+metric);
			//sD2��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/sD2Score."+runId,"./"+packageName+"/"+metric);
			//wIG��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/wIGScore."+runId,"./"+packageName+"/"+metric);	
			//sMV��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/sMVScore."+runId,"./"+packageName+"/"+metric);
			
			
			//iA_SUM��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/iA_SUMScore."+runId,"./"+packageName+"/"+metric);
			//sD2MultiWIG��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/sD2MultiWIGScore."+runId,"./"+packageName+"/"+metric);
			//iASUM2��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/iASUM2Score."+runId,"./"+packageName+"/"+metric);
			//iASUM3��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/iASUM3Score."+runId,"./"+packageName+"/"+metric);
			//cF��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/cFScore."+runId,"./"+packageName+"/"+metric);
			//cF2��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/cF2Score."+runId,"./"+packageName+"/"+metric);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		//����kendall�㷨
		try {
			
			//nQC��Ӧ��kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/nQCScore."+runId,"./"+packageName+"/"+metric);
			//sD2��Ӧ��kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/sD2Score."+runId,"./"+packageName+"/"+metric);
			//wIG��Ӧ��kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/wIGScore."+runId,"./"+packageName+"/"+metric);	
			//sMV��Ӧ��kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/sMVScore."+runId,"./"+packageName+"/"+metric);
			
			
			//iA_SUM��Ӧ��kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/iA_SUMScore."+runId,"./"+packageName+"/"+metric);
			//sD2MultiWIG��Ӧ��kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/sD2MultiWIGScore."+runId,"./"+packageName+"/"+metric);
			//iASUM2��Ӧ��kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/iASUM2Score."+runId,"./"+packageName+"/"+metric);
			//iASUM3��Ӧ��kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/iASUM3Score."+runId,"./"+packageName+"/"+metric);
			//cF��Ӧ��kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/cFScore."+runId,"./"+packageName+"/"+metric);
			//cF2��Ӧ��kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/cF2Score."+runId,"./"+packageName+"/"+metric);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//����spearman�㷨
		try{
			//nQC��Ӧ��spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/nQCScore."+runId,"./"+packageName+"/"+metric);
			//sD2��Ӧ��spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/sD2Score."+runId,"./"+packageName+"/"+metric);
			//wIG��Ӧ��spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/wIGScore."+runId,"./"+packageName+"/"+metric);	
			//sMV��Ӧ��spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/sMVScore."+runId,"./"+packageName+"/"+metric);
			
			
			//iA_SUM��Ӧ��spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/iA_SUMScore."+runId,"./"+packageName+"/"+metric);
			//sD2MultiWIG��Ӧ��spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/sD2MultiWIGScore."+runId,"./"+packageName+"/"+metric);
			//iASUM2��Ӧ��spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/iASUM2Score."+runId,"./"+packageName+"/"+metric);
			//iASUM3��Ӧ��spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/iASUM3Score."+runId,"./"+packageName+"/"+metric);
			//cF��Ӧ��spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/cFScore."+runId,"./"+packageName+"/"+metric);
			//cF2��Ӧ��spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/cF2Score."+runId,"./"+packageName+"/"+metric);
						
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}

}
