package trec4_0831;

import java.io.IOException;
import java.util.Scanner;

public class GenerateResult {
	
	public static void processSummary(String runId,String packageName,int round){
		//处理summary文件
		predictor.SummaryAnalysis.round = round;
		predictor.SummaryAnalysis.extractAveragePrecision("./"+packageName+"/summary."+runId, "./"+packageName+"/map."+runId);
		predictor.SummaryAnalysis.setTermSize(5);
		predictor.SummaryAnalysis.normalizeAveragePrecision("./"+packageName+"/map."+runId, "./"+packageName+"/map.normalized."+runId);
	}
	public static void processPrediction(String runId,String packageName,String queries_only) throws IOException{
		//计算NQC
		predictor.NQC predictorNQC=new predictor.NQC();
		predictorNQC.setK(100);//把predictorNQC的k设为100
		predictorNQC.getNQCScores("./"+packageName+"/input."+runId, "./"+packageName+"/nQCScore."+runId);
		System.out.println("根据input计算每个query的NQC值,并将NQCScore存入文件,已完成..");

		//计算SD
		predictor.SD predictorSD=new predictor.SD();
		predictorSD.setK(100);//把predictorSD的k设为100
		predictorSD.getSDScores("./"+packageName+"/input."+runId,"./"+packageName+"/sDScore."+runId);
		System.out.println("根据input计算每个query的SD值,并将SDScore存入文件,已完成..");

		//计算SMV
		predictor.SMV predictorSMV = new predictor.SMV();
		predictorSMV.setK(100);//把predictorSMV的k设为100
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
		
		//计算SMV_2
		newPredictor.SMV_2 newPredictorSMV_2 = new newPredictor.SMV_2();
		newPredictorSMV_2.setK(100);//把newPredictorSMV_2的k设为100
		newPredictorSMV_2.getSMV_2Scores("./"+packageName+"/input."+runId,"./"+packageName+"/sMV_2Score."+runId);
		System.out.println("根据input计算每个query的SMV_2值,并将SMV_2Score存入文件,已完成..");
		
	}
	
	public static void main(String[] args) throws IOException {
		String runId=null;
		runId="CnQst2";
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
			
			//sMV_2对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/sMV_2Score."+runId,"./"+packageName+"/map.normalized."+runId);
			
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
			
			//sMV_2对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/sMV_2Score."+runId,"./"+packageName+"/map.normalized."+runId);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
