package resultsOfTRECs_0628;

import java.io.IOException;
import java.util.Scanner;

public class GenerateResult {

	public static void processSummary(String runId,String packageName,int round){
		//����summary�ļ�
		predictor.SummaryAnalysis.round = round;
		predictor.SummaryAnalysis.extractAveragePrecision("./"+packageName+"/summary."+runId, "./"+packageName+"/map."+runId);
		predictor.SummaryAnalysis.setTermSize(5);
		predictor.SummaryAnalysis.normalizeAveragePrecision("./"+packageName+"/map."+runId, "./"+packageName+"/map.normalized."+runId);
	}
	public static void processPrediction(String runId,String packageName,String queries_only) throws IOException{
		//����NQC
		predictor.NQC predictorNQC=new predictor.NQC();
		predictorNQC.setK(100);//��predictorNQC��k��Ϊ100
		predictorNQC.getNQCScores("./"+packageName+"/input."+runId, "./"+packageName+"/nQCScore."+runId);
		System.out.println("����input����ÿ��query��NQCֵ,����NQCScore�����ļ�,�����..");

		//����SD
		predictor.SD predictorSD=new predictor.SD();
		predictorSD.setK(100);//��predictorSD��k��Ϊ100
		predictorSD.getSDScores("./"+packageName+"/input."+runId,"./"+packageName+"/sDScore."+runId);
		System.out.println("����input����ÿ��query��SDֵ,����SDScore�����ļ�,�����..");

		//����SMV
		predictor.SMV predictorSMV = new predictor.SMV();
		predictorSMV.setK(100);//��predictorSMV��k��Ϊ100
		predictorSMV.getSMVScores("./"+packageName+"/input."+runId,"./"+packageName+"/sMVScore."+runId);
		System.out.println("����input����ÿ��query��SMVֵ,����SMVScore�����ļ�,�����..");

		//����WIG
		predictor.WIG predictorWIG=new predictor.WIG();
		predictorWIG.setK(5);//��predictorWIG��k��Ϊ5
		/**�����QueryLength.getQueryLength()ΪpackageName���е�,
		 * ��temporary.QueryLength.java��ͬ
		 */
		if(queries_only!=null) predictorWIG.setQueryMap(QueryLength.getQueryLength("./"+packageName+"/"+queries_only));
		predictorWIG.getWIGScores("./"+packageName+"/input."+runId,"./"+packageName+"/wIGScore."+runId);
		System.out.println("����input����ÿ��query��WIGֵ,����WIGScore�����ļ�,�����..");

		/*
		//����SMV_2
		newPredictor.SMV_2 newPredictorSMV_2 = new newPredictor.SMV_2();
		newPredictorSMV_2.setK(100);//��newPredictorSMV_2��k��Ϊ100
		newPredictorSMV_2.getSMV_2Scores("./"+packageName+"/input."+runId,"./"+packageName+"/sMV_2Score."+runId);
		System.out.println("����input����ÿ��query��SMV_2ֵ,����SMV_2Score�����ļ�,�����..");
		 */
		//����SD_WIG
		teacher.SD_WIG teacherSD_WIG=new teacher.SD_WIG();
		teacherSD_WIG.getSD_WIGScores("./"+packageName+"/input."+runId,"./"+packageName+"/sD_WIGScore."+runId);
		//����WIG_NQC
		teacher.WIG_NQC teacherWIG_NQC=new teacher.WIG_NQC();
		teacherWIG_NQC.getWIG_NQCScores("./"+packageName+"/input."+runId,"./"+packageName+"/wIG_NQCScore."+runId);
		
		//����SD_Multi_WIG
		teacher.SD_Multi_WIG teacherSD_Multi_WIG=new teacher.SD_Multi_WIG();
		teacherSD_Multi_WIG.getSD_Multi_WIGScores("./"+packageName+"/input."+runId,"./"+packageName+"/sD_Multi_WIGScore."+runId);
		//����WIG_Multi_NQC
		teacher.WIG_Multi_NQC teacherWIG_Multi_NQC=new teacher.WIG_Multi_NQC();
		teacherWIG_Multi_NQC.getWIG_Multi_NQCScores("./"+packageName+"/input."+runId,"./"+packageName+"/wIG_Multi_NQCScore."+runId);
		
	}

	public static void main(String[] args) throws IOException {
		String runId=null;
		runId="CnQst2";
		String packageName=null;
		int round=0;
		String queries_only=null;
		//����summary�ļ�,��ȡaverage Precision��Ϣ
		processSummary(runId,packageName,round);

		//����input�ļ�,����Ԥ���㷨,�õ�Ԥ����Ϣ
		processPrediction(runId,packageName,queries_only);


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
		scanner.close();


		//����pearson�㷨
		try {
			//nQC��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./resultsOfTRECs/nQCScore."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//sD��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./resultsOfTRECs/sDScore."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//wIG��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./resultsOfTRECs/wIGScore."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//sMV��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./resultsOfTRECs/sMVScore."+runId,"./resultsOfTRECs/map.normalized."+runId);

		} catch (IOException e) {
			e.printStackTrace();
		}

		//����kendall�㷨
		try {
			//nQC��Ӧ��kendall
			temporary.Kendall.loadScoreAndComputeKendall("./resultsOfTRECs/nQCScore."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//sD��Ӧ��kendall
			temporary.Kendall.loadScoreAndComputeKendall("./resultsOfTRECs/sDScore."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//wIG��Ӧ��kendall
			temporary.Kendall.loadScoreAndComputeKendall("./resultsOfTRECs/wIGScore."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//sMV��Ӧ��kendall
			temporary.Kendall.loadScoreAndComputeKendall("./resultsOfTRECs/sMVScore."+runId,"./resultsOfTRECs/map.normalized."+runId);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * �ɹ�GenerateResult_batch�����
	 * @param args
	 * @throws IOException
	 */
	public static void getGeneratedResult(String packageName,String runId,int round,String queries_only) throws IOException {

		//����summary�ļ�,��ȡaverage Precision��Ϣ
		processSummary(runId,packageName,round);

		//����input�ļ�,����Ԥ���㷨,�õ�Ԥ����Ϣ
		processPrediction(runId,packageName,queries_only);


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


		//Ԥ��ֵ��map��pearson kendallϵ��:
		System.out.println("Ԥ��ֵ��map��pearson kendallϵ��:\n");
		//����pearson�㷨
		try {
			//nQC��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/nQCScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//sD��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/sDScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//wIG��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/wIGScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//sMV��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/sMVScore."+runId,"./"+packageName+"/map.normalized."+runId);

			//sD_WIG��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/sD_WIGScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//wIG_NQC��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/wIG_NQCScore."+runId,"./"+packageName+"/map.normalized."+runId);
			
			//sD_Multi_WIG��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/sD_Multi_WIGScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//wIG_Multi_NQC��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/wIG_Multi_NQCScore."+runId,"./"+packageName+"/map.normalized."+runId);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		//����kendall�㷨
		try {
			//nQC��Ӧ��kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/nQCScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//sD��Ӧ��kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/sDScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//wIG��Ӧ��kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/wIGScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//sMV��Ӧ��kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/sMVScore."+runId,"./"+packageName+"/map.normalized."+runId);

			//sD_WIG��Ӧ��kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/sD_WIGScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//wIG_NQC��Ӧ��kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/wIG_NQCScore."+runId,"./"+packageName+"/map.normalized."+runId);
			
			//sD_Multi_WIG��Ӧ��kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/sD_Multi_WIGScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//wIG_Multi_NQC��Ӧ��kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/wIG_Multi_NQCScore."+runId,"./"+packageName+"/map.normalized."+runId);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		//����spearman�㷨
		try {
			//nQC��Ӧ��spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/nQCScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//sD��Ӧ��spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/sDScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//wIG��Ӧ��spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/wIGScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//sMV��Ӧ��spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/sMVScore."+runId,"./"+packageName+"/map.normalized."+runId);
			
			//sD_WIG��Ӧ��spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/sD_WIGScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//wIG_NQC��Ӧ��spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/wIG_NQCScore."+runId,"./"+packageName+"/map.normalized."+runId);
			
			//sD_Multi_WIG��Ӧ��spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/sD_Multi_WIGScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//wIG_Multi_NQC��Ӧ��spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/wIG_Multi_NQCScore."+runId,"./"+packageName+"/map.normalized."+runId);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
