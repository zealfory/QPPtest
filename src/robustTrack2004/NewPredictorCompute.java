package robustTrack2004;

import java.io.IOException;

public class NewPredictorCompute {
	
	public static void processPrediction(String runId) throws IOException{
		//计算NQCSMV
		newPredictor.NQCSMV predictorNQCSMV=new newPredictor.NQCSMV();
		predictorNQCSMV.setK(100);//把predictorNQC的k设为100
		predictorNQCSMV.getNQCSMVScores("./resultsOfTRECs/input."+runId, "./resultsOfTRECs/nQCSMVScore."+runId);
		System.out.println("根据input计算每个query的NQCSMV值,并将NQCSMVScore存入文件,已完成..");
		
		//计算SMVWIG
		newPredictor.SMVWIG predictorSMVWIG=new newPredictor.SMVWIG();
		predictorSMVWIG.setK(5);//把predictorNQC的k设为100
		predictorSMVWIG.getSMVWIGScores("./resultsOfTRECs/input."+runId, "./resultsOfTRECs/SMVWIGScore."+runId);
		System.out.println("根据input计算每个query的SMVWIG值,并将SMVWIGScore存入文件,已完成..");
		
		//计算NQCWIG
		newPredictor.NQCWIG predictorNQCWIG=new newPredictor.NQCWIG();
		predictorNQCWIG.setK(5);//把predictorNQC的k设为100
		predictorNQCWIG.getNQCWIGScores("./resultsOfTRECs/input."+runId, "./resultsOfTRECs/nQCWIGScore."+runId);
		System.out.println("根据input计算每个query的NQCWIG值,并将NQCWIGScore存入文件,已完成..");
		
		//计算NQCSMV_2
		newPredictor.NQCSMV_2 predictorNQCSMV_2=new newPredictor.NQCSMV_2();
		predictorNQCSMV_2.setK(100);//把predictorNQC的k设为100
		predictorNQCSMV_2.getNQCSMV_2Scores("./resultsOfTRECs/input."+runId, "./resultsOfTRECs/nQCSMV_2Score."+runId);
		System.out.println("根据input计算每个query的NQCSMV_2值,并将NQCSMVScore_2存入文件,已完成..");		
		
		//计算NQCWIG_2
		newPredictor.NQCWIG_2 predictorNQCWIG_2=new newPredictor.NQCWIG_2();
		predictorNQCWIG_2.setK(100);//把predictorNQC的k设为100
		predictorNQCWIG_2.setK2(5);//把predictorNQCWIG_2的WIG方面的截断值设为5
		predictorNQCWIG_2.getNQCWIG_2Scores("./resultsOfTRECs/input."+runId, "./resultsOfTRECs/nQCWIG_2Score."+runId);
		System.out.println("根据input计算每个query的NQCWIG_2值,并将NQCWIG_2Score存入文件,已完成..");
		
		//计算NQCWIG_3
		newPredictor.NQCWIG_3 predictorNQCWIG_3=new newPredictor.NQCWIG_3();
		predictorNQCWIG_3.setK(5);//把predictorNQC的k设为100
		predictorNQCWIG_3.getNQCWIG_3Scores("./resultsOfTRECs/input."+runId, "./resultsOfTRECs/nQCWIG_3Score."+runId);
		System.out.println("根据input计算每个query的NQCWIG_3值,并将NQCWIG_3Score存入文件,已完成..");
		
		//计算NQCWIG_4
		newPredictor.NQCWIG_4 predictorNQCWIG_4=new newPredictor.NQCWIG_4();
		predictorNQCWIG_4.setK(100);//把predictorNQC的k设为100
		predictorNQCWIG_4.setK2(5);//把predictorNQCWIG_4的WIG方面的截断值设为5
		predictorNQCWIG_4.getNQCWIG_4Scores("./resultsOfTRECs/input."+runId, "./resultsOfTRECs/nQCWIG_4Score."+runId);
		System.out.println("根据input计算每个query的NQCWIG_4值,并将NQCWIG_4Score存入文件,已完成..");
		
		//计算SMVWIG_2
		newPredictor.SMVWIG_2 predictorSMVWIG_2=new newPredictor.SMVWIG_2();
		predictorSMVWIG_2.setK1(100);//把predictorNQC的k设为100
		predictorSMVWIG_2.setK2(5);//把predictorNQCWIG_4的WIG方面的截断值设为5
		predictorSMVWIG_2.getSMVWIG_2Scores("./resultsOfTRECs/input."+runId, "./resultsOfTRECs/sMVWIG_2Score."+runId);
		System.out.println("根据input计算每个query的SMVWIG_2值,并将SMVWIG_2Score存入文件,已完成..");
		
	}
	
	public static void computePearson(String runId){
		//运行pearson算法
		try {
			//nQCSMV对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./resultsOfTRECs/nQCSMVScore."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//sMVWIG对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./resultsOfTRECs/sMVWIGScore."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//nQCWIG对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./resultsOfTRECs/nQCWIGScore."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//nQCSMV_2对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./resultsOfTRECs/nQCSMV_2Score."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//nQCWIG_2对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./resultsOfTRECs/nQCWIG_2Score."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//nQCWIG_3对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./resultsOfTRECs/nQCWIG_3Score."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//nQCWIG_4对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./resultsOfTRECs/nQCWIG_4Score."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//sMVWIG_2对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./resultsOfTRECs/sMVWIG_2Score."+runId,"./resultsOfTRECs/map.normalized."+runId);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void computeKendall(String runId){
		//运行kendall算法
		try {
			//nQCSMV对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./resultsOfTRECs/nQCSMVScore."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//sMVWIG对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./resultsOfTRECs/sMVWIGScore."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//nQCWIG对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./resultsOfTRECs/nQCWIGScore."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//nQCSMV_2对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./resultsOfTRECs/nQCSMV_2Score."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//nQCWIG_2对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./resultsOfTRECs/nQCWIG_2Score."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//nQCWIG_3对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./resultsOfTRECs/nQCWIG_3Score."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//nQCWIG_4对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./resultsOfTRECs/nQCWIG_4Score."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//sMVWIG_2对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./resultsOfTRECs/sMVWIG_2Score."+runId,"./resultsOfTRECs/map.normalized."+runId);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
