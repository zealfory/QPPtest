package robustTrack2004;

import java.io.IOException;
import java.util.Scanner;

public class GenerateResult {
	
	public static void processSummary(String runId){
		//处理summary文件
		predictor.SummaryAnalysis.round = 249;
		predictor.SummaryAnalysis.extractAveragePrecision("./resultsOfTRECs/summary."+runId, "./resultsOfTRECs/map."+runId);
		predictor.SummaryAnalysis.setTermSize(5);
		predictor.SummaryAnalysis.normalizeAveragePrecision("./resultsOfTRECs/map."+runId, "./resultsOfTRECs/map.normalized."+runId);
	}
	public static void processPrediction(String runId) throws IOException{
		//计算NQC
		predictor.NQC predictorNQC=new predictor.NQC();
		predictorNQC.setK(100);//把predictorNQC的k设为100
		predictorNQC.getNQCScores("./resultsOfTRECs/input."+runId, "./resultsOfTRECs/nQCScore."+runId);
		System.out.println("根据input计算每个query的NQC值,并将NQCScore存入文件,已完成..");

		//计算SD
		predictor.SD predictorSD=new predictor.SD();
		predictorSD.setK(100);//把predictorSD的k设为100
		predictorSD.getSDScores("./resultsOfTRECs/input."+runId,"./resultsOfTRECs/sDScore."+runId);
		System.out.println("根据input计算每个query的SD值,并将SDScore存入文件,已完成..");

		//计算SMV
		predictor.SMV predictorSMV = new predictor.SMV();
		predictorSMV.setK(100);//把predictorSMV的k设为100
		predictorSMV.getSMVScores("./resultsOfTRECs/input."+runId,"./resultsOfTRECs/sMVScore."+runId);
		System.out.println("根据input计算每个query的SMV值,并将SMVScore存入文件,已完成..");
		
		
		//计算WIG
		//predictor.WIG predictorWIG=new predictor.WIG();
		//predictorWIG.setK(5);//把predictorWIG的k设为5
		/**这里的QueryLength.getQueryLength()为webTrackDistillation2002 包中的,
		 */
		/*
		predictorWIG.setQueryMap(QueryLength.getQueryLength());
		predictorWIG.getWIGScores("./resultsOfTRECs/input."+runId,"./resultsOfTRECs/wIGScore."+runId);
		System.out.println("根据input计算每个query的WIG值,并将WIGScore存入文件,已完成..");
		*/
		//计算newPredictor
		//NewPredictorCompute.processPrediction(runId);
	}
	
	public static void main(String[] args) throws IOException {
		String runId=null;
		String packageName=null;//存放数据的文件夹名称
		packageName="resultsOfTRECs";
		runId="pircRB04t3";
		
		//分析summary文件,获取average Precision信息
		processSummary(runId);
		
		//根据input文件,运行预测算法,得到预测信息
		processPrediction(runId);
		
		
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
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/nQCScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//sD对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/sDScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//wIG对应的pearson
			//CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/wIGScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//sMV对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/sMVScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//newPredictor对应的pearson
			//NewPredictorCompute.computePearson(runId);
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/AuPRScore."+runId,"./"+packageName+"/map.normalized."+runId);
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
			//temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/wIGScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//sMV对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/sMVScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//newPredictor对应的kendall
			//NewPredictorCompute.computeKendall(runId);
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/AuPRScore."+runId,"./"+packageName+"/map.normalized."+runId);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
	}

}
