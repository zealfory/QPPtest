package trec4_0831;

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
		
		//����SMV_2
		newPredictor.SMV_2 newPredictorSMV_2 = new newPredictor.SMV_2();
		newPredictorSMV_2.setK(100);//��newPredictorSMV_2��k��Ϊ100
		newPredictorSMV_2.getSMV_2Scores("./"+packageName+"/input."+runId,"./"+packageName+"/sMV_2Score."+runId);
		System.out.println("����input����ÿ��query��SMV_2ֵ,����SMV_2Score�����ļ�,�����..");
		
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
			
			//sMV_2��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/sMV_2Score."+runId,"./"+packageName+"/map.normalized."+runId);
			
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
			
			//sMV_2��Ӧ��kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/sMV_2Score."+runId,"./"+packageName+"/map.normalized."+runId);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
