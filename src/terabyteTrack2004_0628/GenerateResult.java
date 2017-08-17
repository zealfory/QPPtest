package terabyteTrack2004_0628;

import java.io.IOException;
import java.util.Scanner;

import teacher.SD_Multi_WIG;

public class GenerateResult {

	public static void processSummary(String runId,String packageName,int round){
		//处理summary文件
		SummaryAnalysis.round = round;
		SummaryAnalysis.extractAveragePrecision("./"+packageName+"/summary."+runId, "./"+packageName+"/map."+runId);
		SummaryAnalysis.normalizeAveragePrecision("./"+packageName+"/map."+runId, "./"+packageName+"/map.normalized."+runId);
	}
	public static void processPrediction(String runId,String packageName,String queries_only) throws IOException{
		//计算NQC
		predictor.NQC predictorNQC=new predictor.NQC();
		predictorNQC.setK(1000);//把predictorNQC的k设为100
		predictorNQC.getNQCScores("./"+packageName+"/input."+runId, "./"+packageName+"/nQCScore."+runId);
		System.out.println("根据input计算每个query的NQC值,并将NQCScore存入文件,已完成..");

		//计算SD
		predictor.SD predictorSD=new predictor.SD();
		predictorSD.setK(1000);//把predictorSD的k设为100
		predictorSD.getSDScores("./"+packageName+"/input."+runId,"./"+packageName+"/sDScore."+runId);
		System.out.println("根据input计算每个query的SD值,并将SDScore存入文件,已完成..");

		//计算SMV
		predictor.SMV predictorSMV = new predictor.SMV();
		predictorSMV.setK(1000);//把predictorSMV的k设为100
		predictorSMV.getSMVScores("./"+packageName+"/input."+runId,"./"+packageName+"/sMVScore."+runId);
		System.out.println("根据input计算每个query的SMV值,并将SMVScore存入文件,已完成..");

		//计算WIG
		predictor.WIG predictorWIG=new predictor.WIG();
		predictorWIG.setK(5);//把predictorWIG的k设为5
		/**这里的QueryLength.getQueryLength()为packageName包中的,
		 * 与temporary.QueryLength.java不同
		 */
		if(queries_only!=null) predictorWIG.setQueryMap(QueryLength.getQueryLength("./"+packageName+"/"+queries_only));
		predictorWIG.getWIGScores("./"+packageName+"/input."+runId,"./"+packageName+"/wIGScore."+runId);
		System.out.println("根据input计算每个query的WIG值,并将WIGScore存入文件,已完成..");

		/*
		//计算SMV_2
		newPredictor.SMV_2 newPredictorSMV_2 = new newPredictor.SMV_2();
		newPredictorSMV_2.setK(1000);//把newPredictorSMV_2的k设为100
		newPredictorSMV_2.getSMV_2Scores("./"+packageName+"/input."+runId,"./"+packageName+"/sMV_2Score."+runId);
		System.out.println("根据input计算每个query的SMV_2值,并将SMV_2Score存入文件,已完成..");
		 */

		//计算SD_WIG
		teacher.SD_WIG teacherSD_WIG=new teacher.SD_WIG();
		teacherSD_WIG.getSD_WIGScores("./"+packageName+"/input."+runId,"./"+packageName+"/sD_WIGScore."+runId);
		//计算WIG_NQC
		teacher.WIG_NQC teacherWIG_NQC=new teacher.WIG_NQC();
		teacherWIG_NQC.getWIG_NQCScores("./"+packageName+"/input."+runId,"./"+packageName+"/wIG_NQCScore."+runId);
		
		//计算SD_Multi_WIG
		teacher.SD_Multi_WIG teacherSD_Multi_WIG=new teacher.SD_Multi_WIG();
		teacherSD_Multi_WIG.getSD_Multi_WIGScores("./"+packageName+"/input."+runId,"./"+packageName+"/sD_Multi_WIGScore."+runId);
		//计算WIG_Multi_NQC
		teacher.WIG_Multi_NQC teacherWIG_Multi_NQC=new teacher.WIG_Multi_NQC();
		teacherWIG_Multi_NQC.getWIG_Multi_NQCScores("./"+packageName+"/input."+runId,"./"+packageName+"/wIG_Multi_NQCScore."+runId);
		
	}

	public static void main(String[] args) throws IOException {
		String runId=null;
		runId=null;
		String packageName=null;
		int round=0;
		String queries_only=null;
		//分析summary文件,获取average Precision信息
		processSummary(runId,packageName,round);

		//根据input文件,运行预测算法,得到预测信息
		processPrediction(runId,packageName,queries_only);


		//查看是否需删除 预测结果文件中多出的信息
		String yourOrder=null;
		Scanner scanner=new Scanner(System.in);
		while(true){
			System.out.println("等待您输入:ok, 请您查看是否需删除 预测结果文件中多出的信息。");
			yourOrder=scanner.nextLine();
			if(yourOrder.equalsIgnoreCase("ok")){
				System.out.println("开始执行pearson,kendall算法..");
				break;
			}
		}
		scanner.close();


		//运行pearson算法
		try {
			//nQC对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./resultsOfTRECs/nQCScore."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//sD对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./resultsOfTRECs/sDScore."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//wIG对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./resultsOfTRECs/wIGScore."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//sMV对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./resultsOfTRECs/sMVScore."+runId,"./resultsOfTRECs/map.normalized."+runId);

		} catch (IOException e) {
			e.printStackTrace();
		}

		//运行kendall算法
		try {
			//nQC对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./resultsOfTRECs/nQCScore."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//sD对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./resultsOfTRECs/sDScore."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//wIG对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./resultsOfTRECs/wIGScore."+runId,"./resultsOfTRECs/map.normalized."+runId);
			//sMV对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./resultsOfTRECs/sMVScore."+runId,"./resultsOfTRECs/map.normalized."+runId);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 可供GenerateResult_batch类调用
	 * @param args
	 * @throws IOException
	 */
	public static void getGeneratedResult(String packageName,String runId,int round,String queries_only) throws IOException {

		//分析summary文件,获取average Precision信息
		processSummary(runId,packageName,round);

		//根据input文件,运行预测算法,得到预测信息
		processPrediction(runId,packageName,queries_only);


		//查看是否需删除 预测结果文件中多出的信息
		String yourOrder=null;
		Scanner scanner=new Scanner(System.in);
		while(true){
			System.out.println("等待您输入:ok, 请您查看是否需删除 预测结果文件中多出的信息。");
			yourOrder=scanner.nextLine();
			if(yourOrder.equalsIgnoreCase("ok")){
				System.out.println("开始执行pearson,kendall算法..");
				break;
			}
		}
		//scanner.close();


		//预测值与map的pearson kendall系数:
		System.out.println("预测值与map的pearson kendall系数:\n");
		//运行pearson算法
		try {
			//nQC对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/nQCScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//sD对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/sDScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//wIG对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/wIGScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//sMV对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/sMVScore."+runId,"./"+packageName+"/map.normalized."+runId);

			//sD_WIG对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/sD_WIGScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//wIG_NQC对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/wIG_NQCScore."+runId,"./"+packageName+"/map.normalized."+runId);

			//sD_Multi_WIG对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/sD_Multi_WIGScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//wIG_Multi_NQC对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/wIG_Multi_NQCScore."+runId,"./"+packageName+"/map.normalized."+runId);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		//运行kendall算法
		try {
			//nQC对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/nQCScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//sD对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/sDScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//wIG对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/wIGScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//sMV对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/sMVScore."+runId,"./"+packageName+"/map.normalized."+runId);

			//sD_WIG对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/sD_WIGScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//wIG_NQC对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/wIG_NQCScore."+runId,"./"+packageName+"/map.normalized."+runId);

			//sD_Multi_WIG对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/sD_Multi_WIGScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//wIG_Multi_NQC对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/wIG_Multi_NQCScore."+runId,"./"+packageName+"/map.normalized."+runId);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		//运行spearman算法
		try {
			//nQC对应的spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/nQCScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//sD对应的spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/sDScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//wIG对应的spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/wIGScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//sMV对应的spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/sMVScore."+runId,"./"+packageName+"/map.normalized."+runId);

			//sD_WIG对应的spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/sD_WIGScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//wIG_NQC对应的spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/wIG_NQCScore."+runId,"./"+packageName+"/map.normalized."+runId);

			//sD_Multi_WIG对应的spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/sD_Multi_WIGScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//wIG_Multi_NQC对应的spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/wIG_Multi_NQCScore."+runId,"./"+packageName+"/map.normalized."+runId);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
