package webTrack2011;

import java.io.IOException;
import java.util.Scanner;

import utils.QueryLength;
import utils.SummaryAnalysis;


public class GenerateResult {

	public static void processSummary(String runId,String packageName){
		//处理summary文件,for webTrackDiversity2010
		
		SummaryAnalysis.round = 50;
		/*
		//获取ERR-IA@20信息
		SummaryAnalysis.extractERR_IA20("./"+packageName+"/summary.ndeval."+runId, "./"+packageName+"/err_IA20."+runId);
		*/
		/*
		//获取alpha-nDCG@20信息
		SummaryAnalysis.extract_alphaNDCG20("./"+packageName+"/summary.ndeval."+runId, "./"+packageName+"/alphaNDCG20."+runId);
		*/
		//获取NRBP信息
		SummaryAnalysis.extract_nRBP("./"+packageName+"/summary.ndeval."+runId, "./"+packageName+"/nRBP."+runId);
				
	}
	public static void processPrediction(String runId,String packageName) throws IOException{
		//计算NQC
		predictor.NQC predictorNQC=new predictor.NQC();
		predictorNQC.setK(100);//把predictorNQC的k设为1000
		predictorNQC.getNQCScores("./"+packageName+"/input."+runId, "./"+packageName+"/nQCScore."+runId);
		System.out.println("根据input计算每个query的NQC值,并将NQCScore存入文件,已完成..");
		
		// 计算SD2
		predictor.SD2 predictorSD2 = new predictor.SD2();
		predictorSD2.setX(0.5);// 把predictorSD2的x设为0.5
		predictorSD2.setQueryMap(QueryLength.getQueryLength("./"+packageName+"/2011queries_only"));
		predictorSD2.getSD2Scores("./" + packageName + "/input." + runId, "./"+ packageName + "/sD2Score." + runId);
		System.out.println("根据input计算每个query的SD2值,并将SD2Score存入文件,已完成..");
		
		//计算SMV
		predictor.SMV predictorSMV = new predictor.SMV();
		predictorSMV.setK(100);//把predictorSMV的k设为1000
		predictorSMV.getSMVScores("./"+packageName+"/input."+runId,"./"+packageName+"/sMVScore."+runId);
		System.out.println("根据input计算每个query的SMV值,并将SMVScore存入文件,已完成..");
		
		//计算WIG
		predictor.WIG predictorWIG=new predictor.WIG();
		predictorWIG.setK(5);//把predictorWIG的k设为5
		/**
		 * 这里的QueryLength.getQueryLength()为packageName包中的,
		 */
		predictorWIG.setQueryMap(QueryLength.getQueryLength("./"+packageName+"/2011queries_only"));
		predictorWIG.getWIGScores("./"+packageName+"/input."+runId,"./"+packageName+"/wIGScore."+runId);
		System.out.println("根据input计算每个query的WIG值,并将WIGScore存入文件,已完成..");
		
		
		//计算IA_SUM
		predictorIA_SUM.IA_SUM.k_subquery=1000;//把子查询subquery的截断参数设为500
		predictorIA_SUM.IA_SUM.k_mainQuery=100;
		predictorIA_SUM.IA_SUM.getIA_SUMScores("./"+packageName+"/input."+runId, "./"+packageName+"/input.indri2011subquery", "./"+packageName+"/output_middle", "./"+packageName+"/iA_SUMScore."+runId);
		
		//计算SD2MultiWIG
		newPredictor.SD2MultiWIG newPredictorSD2MultiWIG=new newPredictor.SD2MultiWIG();
		newPredictorSD2MultiWIG.getSD2MultiWIGScores("./"+packageName+"/input."+runId, "./"+packageName+"/sD2MultiWIGScore."+runId);
		System.out.println("根据input计算每个query的SD2MultiWIG值,并将sD2MultiWIGScore存入文件,已完成..");
		
		//计算IASUM2
		newPredictor.IASUM2 newPredictorIASUM2=new newPredictor.IASUM2();
		newPredictorIASUM2.getIASUM2Scores("./"+packageName+"/input."+runId, "./"+packageName+"/iASUM2Score."+runId);
		System.out.println("根据input计算每个query的IASUM2值,并将iASUM2Score存入文件,已完成..");
		
		//计算IASUM3
		predictorIA_SUM.IASUM3.k_subquery=1000;//把子查询subquery的截断参数设为500
		predictorIA_SUM.IASUM3.k_mainQuery=100;
		predictorIA_SUM.IASUM3.getIASUM3Scores("./"+packageName+"/input."+runId, "./"+packageName+"/input.indri2011subquery", "./"+packageName+"/output_middle", "./"+packageName+"/iASUM3Score."+runId);
		
		//计算CF
		predictorIA_SUM.CF.k_subquery=1000;//把子查询subquery的截断参数设为500
		predictorIA_SUM.CF.k_mainQuery=20;//把主查询mainQuery的截断参数设为20
		predictorIA_SUM.CF.getCFScores("./"+packageName+"/input."+runId, "./"+packageName+"/input.indri2011subquery", "./"+packageName+"/cFScore."+runId);
		
		//计算CF2
		newPredictor.CF2 newPredictorCF2=new newPredictor.CF2();
		newPredictorCF2.getCF2Scores("./"+packageName+"/input."+runId, "./"+packageName+"/cF2Score."+runId);
		System.out.println("根据input计算每个query的CF2值,并将cF2Score存入文件,已完成..");
		
	}

	public static void main(String[] args) throws IOException {
		String runId=null;
		String packageName=null;//存放数据的文件夹名称
		packageName="webTrack2011";
		runId="qutir11b";

		//分析summary文件,获取ERR-IA@20信息
		processSummary(runId,packageName);

		//根据input文件,运行预测算法,得到预测信息
		processPrediction(runId,packageName);


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

		
		//预测值与ERR-IA@20的pearson kendall系数:
		System.out.println("预测值与ERR-IA@20的pearson kendall系数:\n");
		//运行pearson算法
		try {
			//nQC对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/nQCScore."+runId,"./"+packageName+"/err_IA20."+runId);
			//sD对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/sDScore."+runId,"./"+packageName+"/err_IA20."+runId);
			//wIG对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/wIGScore."+runId,"./"+packageName+"/err_IA20."+runId);
			//sMV对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/sMVScore."+runId,"./"+packageName+"/err_IA20."+runId);
			//iA_SUM对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/iA_SUMScore."+runId,"./"+packageName+"/err_IA20."+runId);


		} catch (IOException e) {
			e.printStackTrace();
		}

		//运行kendall算法
		try {
			//nQC对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/nQCScore."+runId,"./"+packageName+"/err_IA20."+runId);
			//sD对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/sDScore."+runId,"./"+packageName+"/err_IA20."+runId);
			//wIG对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/wIGScore."+runId,"./"+packageName+"/err_IA20."+runId);
			//sMV对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/sMVScore."+runId,"./"+packageName+"/err_IA20."+runId);
			//iA_SUM对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/iA_SUMScore."+runId,"./"+packageName+"/err_IA20."+runId);


		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 可供GenerateResult_batch类调用
	 * @param args
	 * @throws IOException
	 */
	public static void getGeneratedResult(String packageName,String runId) throws IOException {
		
		//分析summary文件,获取ERR-IA@20信息,或ERR@20信息
		processSummary(runId,packageName);
		
		//根据input文件,运行预测算法,得到预测信息
		processPrediction(runId,packageName);
		
		//评价预测算法的指标,取文件名
		String metric=null;
		//
		//
		//执行前需确认此指标名称
		metric="nRBP"+"."+runId;
		
		
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
		
		
		//预测值与ERR-IA@20的pearson kendall系数:
		System.out.println("预测值与ERR-IA@20的pearson kendall系数:\n");
		//运行pearson算法
		try {
			
			//nQC对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/nQCScore."+runId,"./"+packageName+"/"+metric);
			//sD2对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/sD2Score."+runId,"./"+packageName+"/"+metric);
			//wIG对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/wIGScore."+runId,"./"+packageName+"/"+metric);	
			//sMV对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/sMVScore."+runId,"./"+packageName+"/"+metric);
			
			
			//iA_SUM对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/iA_SUMScore."+runId,"./"+packageName+"/"+metric);
			//sD2MultiWIG对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/sD2MultiWIGScore."+runId,"./"+packageName+"/"+metric);
			//iASUM2对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/iASUM2Score."+runId,"./"+packageName+"/"+metric);
			//iASUM3对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/iASUM3Score."+runId,"./"+packageName+"/"+metric);
			//cF对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/cFScore."+runId,"./"+packageName+"/"+metric);
			//cF2对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/cF2Score."+runId,"./"+packageName+"/"+metric);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		//运行kendall算法
		try {
			
			//nQC对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/nQCScore."+runId,"./"+packageName+"/"+metric);
			//sD2对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/sD2Score."+runId,"./"+packageName+"/"+metric);
			//wIG对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/wIGScore."+runId,"./"+packageName+"/"+metric);	
			//sMV对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/sMVScore."+runId,"./"+packageName+"/"+metric);
			
			
			//iA_SUM对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/iA_SUMScore."+runId,"./"+packageName+"/"+metric);
			//sD2MultiWIG对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/sD2MultiWIGScore."+runId,"./"+packageName+"/"+metric);
			//iASUM2对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/iASUM2Score."+runId,"./"+packageName+"/"+metric);
			//iASUM3对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/iASUM3Score."+runId,"./"+packageName+"/"+metric);
			//cF对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/cFScore."+runId,"./"+packageName+"/"+metric);
			//cF2对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/cF2Score."+runId,"./"+packageName+"/"+metric);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//运行spearman算法
		try{
			//nQC对应的spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/nQCScore."+runId,"./"+packageName+"/"+metric);
			//sD2对应的spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/sD2Score."+runId,"./"+packageName+"/"+metric);
			//wIG对应的spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/wIGScore."+runId,"./"+packageName+"/"+metric);	
			//sMV对应的spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/sMVScore."+runId,"./"+packageName+"/"+metric);
			
			
			//iA_SUM对应的spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/iA_SUMScore."+runId,"./"+packageName+"/"+metric);
			//sD2MultiWIG对应的spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/sD2MultiWIGScore."+runId,"./"+packageName+"/"+metric);
			//iASUM2对应的spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/iASUM2Score."+runId,"./"+packageName+"/"+metric);
			//iASUM3对应的spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/iASUM3Score."+runId,"./"+packageName+"/"+metric);
			//cF对应的spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/cFScore."+runId,"./"+packageName+"/"+metric);
			//cF2对应的spearman
			new CorrelationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/cF2Score."+runId,"./"+packageName+"/"+metric);
						
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}
