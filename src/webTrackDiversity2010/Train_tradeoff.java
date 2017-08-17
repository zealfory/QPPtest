package webTrackDiversity2010;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import utils.QueryLength;
import utils.SummaryAnalysis;

/**
 * SDMultiWIG_IASUM的变量tradeoff从0增加到1,在tradeoff增加过程中,某系统对应的coefficient随之变化。
 * 此程序输出这些信息。
 * @author 1
 *
 */
public class Train_tradeoff {
	
	public static void processSummary(String runId,String packageName){
		//处理summary文件,for webTrackDiversity2010
		
		SummaryAnalysis.round = 48;
		
		//获取ERR-IA@20信息
		SummaryAnalysis.extractERR_IA20("./"+packageName+"/summary.ndeval."+runId, "./"+packageName+"/err_IA20."+runId);
	}
	public static void processPrediction(String runId,String packageName) throws IOException{
		
		//计算SD
		predictor.SD predictorSD=new predictor.SD();
		predictorSD.setK(100);//把predictorSD的k设为1000
		predictorSD.getSDScores("./"+packageName+"/input."+runId,"./"+packageName+"/sDScore."+runId);
		System.out.println("根据input计算每个query的SD值,并将SDScore存入文件,已完成..");

		//计算WIG
		predictor.WIG predictorWIG=new predictor.WIG();
		predictorWIG.setK(5);//把predictorWIG的k设为5
		/**
		 * 这里的QueryLength.getQueryLength()为packageName包中的,
		 */
		predictorWIG.setQueryMap(QueryLength.getQueryLength("./"+packageName+"/wt2010-topics.queries-only"));
		predictorWIG.getWIGScores("./"+packageName+"/input."+runId,"./"+packageName+"/wIGScore."+runId);
		System.out.println("根据input计算每个query的WIG值,并将WIGScore存入文件,已完成..");
		
		//计算IA_SUM
		predictorIA_SUM.IA_SUM.getIA_SUMScores("./"+packageName+"/input."+runId, "./"+packageName+"/input.indri2010subquery_60addRank", "./"+packageName+"/output_middle", "./"+packageName+"/iA_SUMScore."+runId);
		
		//计算SDMultiWIG
		newPredictor.SDMultiWIG newPredictorSDMultiWIG=new newPredictor.SDMultiWIG();
		newPredictorSDMultiWIG.getSDMultiWIGScores("./"+packageName+"/input."+runId, "./"+packageName+"/sDMultiWIGScore."+runId);
		System.out.println("根据input计算每个query的SDMultiWIG值,并将sDMultiWIGScore存入文件,已完成..");
		
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		/*FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		String runIdFile=null;
		String tempLine=null;
		String packageName=null;
		String runId=null;
		int n=0;
		
		runIdFile="./webTrackDiversity2010/runId.txt";
		fileReader=new FileReader(runIdFile);
		bufferedReader=new BufferedReader(fileReader);
		packageName="webTrackDiversity2010";
		
		while((tempLine=bufferedReader.readLine())!=null){
			runId=tempLine.split("\\.")[1];
			System.out.println("\n\n"+(++n)+"、track为"+runId);
			getGeneratedResult(packageName, runId);
			Thread.sleep(40000);
		}
		bufferedReader.close();
		System.out.println("批量产生结果,已完成!");
		*/
		String input=null;
		//input="./webTrackDiversity2010/新建文本文档 (3).txt_withCoefficientNormalized";
		input="./webTrackDiversity2010/新建文本文档 (3).txt";
		analyzeTradeoff(input);
		
	}
	
	/**
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void getGeneratedResult(String packageName,String runId) throws IOException {
		
		//分析summary文件,获取ERR-IA@20信息
		processSummary(runId,packageName);

		//根据input文件,运行预测算法,得到预测信息
		processPrediction(runId,packageName);
		
		System.out.println("\n");
		//显示每个tradeoff下的pearson,kendall系数
		double tradeoff=0;
		for(int i=0;i<101;i++){
			//计算SDMultiWIG_IASUM
			newPredictor.SDMultiWIG_IASUM newPredictorSDMultiWIG_IASUM=new newPredictor.SDMultiWIG_IASUM();
			newPredictorSDMultiWIG_IASUM.setTradeoff(tradeoff);//把SDMultiWIG_IASUM的tradeoff设为0.5
			newPredictorSDMultiWIG_IASUM.getSDMultiWIG_IASUMScores("./"+packageName+"/input."+runId, "./"+packageName+"/sDMultiWIG_IASUMScore."+runId);
			//System.out.println("根据input计算每个query的SDMultiWIG_IASUM值,并将sDMultiWIG_IASUMScore存入文件,已完成..");
			
			System.out.println("tradeoff="+tradeoff);
			//预测值与ERR-IA@20的pearson kendall系数:
			//运行pearson算法
			//sDMultiWIG_IASUM对应的pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/sDMultiWIG_IASUMScore."+runId,"./"+packageName+"/err_IA20."+runId);

			//运行kendall算法
			//sDMultiWIG_IASUM对应的kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/sDMultiWIG_IASUMScore."+runId,"./"+packageName+"/err_IA20."+runId);
			
			//tradeoff增加0.01
			tradeoff=add(tradeoff,0.01);
		}

	}

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1 被加数
	 * @param v2 加数
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}
	/**
	 * 分析input文件中的tradeoff,获得合适的tradeoff。
	 * @throws IOException 
	 * 
	 */
	public static void analyzeTradeoff(String input) throws IOException{
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		String tempLine=null;
		ArrayList<Run_tradeoff> array_run=new ArrayList<Run_tradeoff>();
		Run_tradeoff run=null;
		
		//把input中的信息存入array_run中
		fileReader=new FileReader(input);
		bufferedReader=new BufferedReader(fileReader);
		
		while((tempLine=bufferedReader.readLine())!=null){
			run=new Run_tradeoff();
			run.runId=tempLine.replaceFirst("[\\d]+、track为", "");
			//tradeoff为0,0.01,0.02...1,共101组
			for(int i=0;i<101;i++){
				bufferedReader.readLine();
				tempLine=bufferedReader.readLine();
				run.pearson[i]=Double.parseDouble(tempLine.split("=")[1].trim());
				tempLine=bufferedReader.readLine();
				run.kendall[i]=Double.parseDouble(tempLine.split("=")[1].trim());
			}
			//选取pearson,kendall中的最大值,赋值给p_bestTradeoff,p_bestCoeff,k_bestTradeoff,k_bestCoeff。
			run.chooseP_best();
			run.chooseK_best();
			//把run存入array_run中
			array_run.add(run);
			//读两个空行
			bufferedReader.readLine();
			bufferedReader.readLine();
		}
		bufferedReader.close();
		
		//遍历array_run,获取每个run的bestTradeoff,bestCoeff,计算出p_tradeoff平均值,k_tradeoff平均值
		double p_tradeoff=0;
		double k_tradeoff=0;
		//计算p_tradeoff的平均值
		for(int i=0;i<array_run.size();i++){
			run=array_run.get(i);
			p_tradeoff=add(p_tradeoff,run.p_bestTradeoff);
			System.out.println(run.runId+"\t"+run.p_bestTradeoff+"\t"+run.p_bestCoeff);
		}
		p_tradeoff=p_tradeoff/array_run.size();
		System.out.println("\np_tradeoff的平均值="+p_tradeoff+"\n\n");
		//计算k_tradeoff的平均值
		for(int i=0;i<array_run.size();i++){
			run=array_run.get(i);
			k_tradeoff=add(k_tradeoff,run.k_bestTradeoff);
			System.out.println(run.runId+"\t"+run.k_bestTradeoff+"\t"+run.k_bestCoeff);
		}
		k_tradeoff=k_tradeoff/array_run.size();
		System.out.println("\nk_tradeoff的平均值="+k_tradeoff+"\n\n");
		//获得合适的tradeoff
		double tradeoff=0;
		tradeoff=(p_tradeoff+k_tradeoff)/2;
		System.out.println("合适的tradeoff="+tradeoff);
	}

}
class Run_tradeoff{
	String runId;
	double[] pearson;
	double[] kendall;
	double p_bestTradeoff;
	double p_bestCoeff;
	double k_bestTradeoff;
	double k_bestCoeff;
	
	public Run_tradeoff(){
		pearson=new double[101];
		kendall=new double[101];
	}
	/**
	 * 选择pearson中的最大值,赋值给p_bestTradeoff,p_bestCoeff;
	 */
	public void chooseP_best(){
		double maxCoeff=pearson[0];
		double maxTradeoff=0;
		for(int i=0;i<pearson.length;i++){
			if(pearson[i]>maxCoeff){
				maxCoeff=pearson[i];
				maxTradeoff=i/100.0;
			}
		}
		p_bestTradeoff=maxTradeoff;
		p_bestCoeff=maxCoeff;
	}
	/**
	 * 选择kendall中的最大值,赋值给k_bestTradeoff,k_bestCoeff;
	 */
	public void chooseK_best(){
		double maxCoeff=kendall[0];
		double maxTradeoff=0;
		for(int i=0;i<kendall.length;i++){
			if(kendall[i]>maxCoeff){
				maxCoeff=kendall[i];
				maxTradeoff=i/100.0;
			}
		}
		k_bestTradeoff=maxTradeoff;
		k_bestCoeff=maxCoeff;
	}

}
