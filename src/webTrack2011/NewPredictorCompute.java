package webTrack2011;

import java.io.IOException;

public class NewPredictorCompute {
	
	public static void processPrediction(String runId) throws IOException{
		//����NQCSMV
		newPredictor.NQCSMV predictorNQCSMV=new newPredictor.NQCSMV();
		predictorNQCSMV.setK(100);//��predictorNQC��k��Ϊ100
		predictorNQCSMV.getNQCSMVScores("./resultsOfTRECs/input."+runId, "./resultsOfTRECs/nQCSMVScore."+runId);
		System.out.println("����input����ÿ��query��NQCSMVֵ,����NQCSMVScore�����ļ�,�����..");
		
		//����SMVWIG
		newPredictor.SMVWIG predictorSMVWIG=new newPredictor.SMVWIG();
		predictorSMVWIG.setK(5);//��predictorNQC��k��Ϊ100
		predictorSMVWIG.getSMVWIGScores("./resultsOfTRECs/input."+runId, "./resultsOfTRECs/SMVWIGScore."+runId);
		System.out.println("����input����ÿ��query��SMVWIGֵ,����SMVWIGScore�����ļ�,�����..");
		
		//����NQCWIG
		newPredictor.NQCWIG predictorNQCWIG=new newPredictor.NQCWIG();
		predictorNQCWIG.setK(5);//��predictorNQC��k��Ϊ100
		predictorNQCWIG.getNQCWIGScores("./resultsOfTRECs/input."+runId, "./resultsOfTRECs/nQCWIGScore."+runId);
		System.out.println("����input����ÿ��query��NQCWIGֵ,����NQCWIGScore�����ļ�,�����..");
		
		//����NQCSMV_2
		newPredictor.NQCSMV_2 predictorNQCSMV_2=new newPredictor.NQCSMV_2();
		predictorNQCSMV_2.setK(100);//��predictorNQC��k��Ϊ100
		predictorNQCSMV_2.getNQCSMV_2Scores("./resultsOfTRECs/input."+runId, "./resultsOfTRECs/nQCSMV_2Score."+runId);
		System.out.println("����input����ÿ��query��NQCSMV_2ֵ,����NQCSMVScore_2�����ļ�,�����..");		
		
		//����NQCWIG_2
		newPredictor.NQCWIG_2 predictorNQCWIG_2=new newPredictor.NQCWIG_2();
		predictorNQCWIG_2.setK(100);//��predictorNQC��k��Ϊ100
		predictorNQCWIG_2.setK2(5);//��predictorNQCWIG_2��WIG����Ľض�ֵ��Ϊ5
		predictorNQCWIG_2.getNQCWIG_2Scores("./resultsOfTRECs/input."+runId, "./resultsOfTRECs/nQCWIG_2Score."+runId);
		System.out.println("����input����ÿ��query��NQCWIG_2ֵ,����NQCWIG_2Score�����ļ�,�����..");
		
		//����NQCWIG_3
		newPredictor.NQCWIG_3 predictorNQCWIG_3=new newPredictor.NQCWIG_3();
		predictorNQCWIG_3.setK(5);//��predictorNQC��k��Ϊ100
		predictorNQCWIG_3.getNQCWIG_3Scores("./resultsOfTRECs/input."+runId, "./resultsOfTRECs/nQCWIG_3Score."+runId);
		System.out.println("����input����ÿ��query��NQCWIG_3ֵ,����NQCWIG_3Score�����ļ�,�����..");
		
		//����NQCWIG_4
		newPredictor.NQCWIG_4 predictorNQCWIG_4=new newPredictor.NQCWIG_4();
		predictorNQCWIG_4.setK(100);//��predictorNQC��k��Ϊ100
		predictorNQCWIG_4.setK2(5);//��predictorNQCWIG_4��WIG����Ľض�ֵ��Ϊ5
		predictorNQCWIG_4.getNQCWIG_4Scores("./resultsOfTRECs/input."+runId, "./resultsOfTRECs/nQCWIG_4Score."+runId);
		System.out.println("����input����ÿ��query��NQCWIG_4ֵ,����NQCWIG_4Score�����ļ�,�����..");
		
		//����SMVWIG_2
		newPredictor.SMVWIG_2 predictorSMVWIG_2=new newPredictor.SMVWIG_2();
		predictorSMVWIG_2.setK1(100);//��predictorNQC��k��Ϊ100
		predictorSMVWIG_2.setK2(5);//��predictorNQCWIG_4��WIG����Ľض�ֵ��Ϊ5
		predictorSMVWIG_2.getSMVWIG_2Scores("./resultsOfTRECs/input."+runId, "./resultsOfTRECs/sMVWIG_2Score."+runId);
		System.out.println("����input����ÿ��query��SMVWIG_2ֵ,����SMVWIG_2Score�����ļ�,�����..");
		
	}
	
	public static void computePearson(String runId){
		//����pearson�㷨
		try {
			//nQCSMV��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./resultsOfTRECs/nQCSMVScore."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//sMVWIG��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./resultsOfTRECs/sMVWIGScore."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//nQCWIG��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./resultsOfTRECs/nQCWIGScore."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//nQCSMV_2��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./resultsOfTRECs/nQCSMV_2Score."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//nQCWIG_2��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./resultsOfTRECs/nQCWIG_2Score."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//nQCWIG_3��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./resultsOfTRECs/nQCWIG_3Score."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//nQCWIG_4��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./resultsOfTRECs/nQCWIG_4Score."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//sMVWIG_2��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./resultsOfTRECs/sMVWIG_2Score."+runId,"./resultsOfTRECs/map.normalized."+runId);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void computeKendall(String runId){
		//����kendall�㷨
		try {
			//nQCSMV��Ӧ��kendall
			temporary.Kendall.loadScoreAndComputeKendall("./resultsOfTRECs/nQCSMVScore."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//sMVWIG��Ӧ��kendall
			temporary.Kendall.loadScoreAndComputeKendall("./resultsOfTRECs/sMVWIGScore."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//nQCWIG��Ӧ��kendall
			temporary.Kendall.loadScoreAndComputeKendall("./resultsOfTRECs/nQCWIGScore."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//nQCSMV_2��Ӧ��kendall
			temporary.Kendall.loadScoreAndComputeKendall("./resultsOfTRECs/nQCSMV_2Score."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//nQCWIG_2��Ӧ��kendall
			temporary.Kendall.loadScoreAndComputeKendall("./resultsOfTRECs/nQCWIG_2Score."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//nQCWIG_3��Ӧ��kendall
			temporary.Kendall.loadScoreAndComputeKendall("./resultsOfTRECs/nQCWIG_3Score."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//nQCWIG_4��Ӧ��kendall
			temporary.Kendall.loadScoreAndComputeKendall("./resultsOfTRECs/nQCWIG_4Score."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//sMVWIG_2��Ӧ��kendall
			temporary.Kendall.loadScoreAndComputeKendall("./resultsOfTRECs/sMVWIG_2Score."+runId,"./resultsOfTRECs/map.normalized."+runId);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
