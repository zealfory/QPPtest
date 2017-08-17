package robustTrack2004;

import java.io.IOException;
import java.util.Scanner;

public class GenerateResult {
	
	public static void processSummary(String runId){
		//����summary�ļ�
		predictor.SummaryAnalysis.round = 249;
		predictor.SummaryAnalysis.extractAveragePrecision("./resultsOfTRECs/summary."+runId, "./resultsOfTRECs/map."+runId);
		predictor.SummaryAnalysis.setTermSize(5);
		predictor.SummaryAnalysis.normalizeAveragePrecision("./resultsOfTRECs/map."+runId, "./resultsOfTRECs/map.normalized."+runId);
	}
	public static void processPrediction(String runId) throws IOException{
		//����NQC
		predictor.NQC predictorNQC=new predictor.NQC();
		predictorNQC.setK(100);//��predictorNQC��k��Ϊ100
		predictorNQC.getNQCScores("./resultsOfTRECs/input."+runId, "./resultsOfTRECs/nQCScore."+runId);
		System.out.println("����input����ÿ��query��NQCֵ,����NQCScore�����ļ�,�����..");

		//����SD
		predictor.SD predictorSD=new predictor.SD();
		predictorSD.setK(100);//��predictorSD��k��Ϊ100
		predictorSD.getSDScores("./resultsOfTRECs/input."+runId,"./resultsOfTRECs/sDScore."+runId);
		System.out.println("����input����ÿ��query��SDֵ,����SDScore�����ļ�,�����..");

		//����SMV
		predictor.SMV predictorSMV = new predictor.SMV();
		predictorSMV.setK(100);//��predictorSMV��k��Ϊ100
		predictorSMV.getSMVScores("./resultsOfTRECs/input."+runId,"./resultsOfTRECs/sMVScore."+runId);
		System.out.println("����input����ÿ��query��SMVֵ,����SMVScore�����ļ�,�����..");
		
		
		//����WIG
		//predictor.WIG predictorWIG=new predictor.WIG();
		//predictorWIG.setK(5);//��predictorWIG��k��Ϊ5
		/**�����QueryLength.getQueryLength()ΪwebTrackDistillation2002 ���е�,
		 */
		/*
		predictorWIG.setQueryMap(QueryLength.getQueryLength());
		predictorWIG.getWIGScores("./resultsOfTRECs/input."+runId,"./resultsOfTRECs/wIGScore."+runId);
		System.out.println("����input����ÿ��query��WIGֵ,����WIGScore�����ļ�,�����..");
		*/
		//����newPredictor
		//NewPredictorCompute.processPrediction(runId);
	}
	
	public static void main(String[] args) throws IOException {
		String runId=null;
		String packageName=null;//������ݵ��ļ�������
		packageName="resultsOfTRECs";
		runId="pircRB04t3";
		
		//����summary�ļ�,��ȡaverage Precision��Ϣ
		processSummary(runId);
		
		//����input�ļ�,����Ԥ���㷨,�õ�Ԥ����Ϣ
		processPrediction(runId);
		
		
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
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/nQCScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//sD��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/sDScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//wIG��Ӧ��pearson
			//CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/wIGScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//sMV��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/sMVScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//newPredictor��Ӧ��pearson
			//NewPredictorCompute.computePearson(runId);
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/AuPRScore."+runId,"./"+packageName+"/map.normalized."+runId);
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
			//temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/wIGScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//sMV��Ӧ��kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/sMVScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//newPredictor��Ӧ��kendall
			//NewPredictorCompute.computeKendall(runId);
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/AuPRScore."+runId,"./"+packageName+"/map.normalized."+runId);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
	}

}
