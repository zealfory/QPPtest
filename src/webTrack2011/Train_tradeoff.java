package webTrack2011;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import utils.QueryLength;
import utils.SummaryAnalysis;

/**
 * SDMultiWIG_IASUM�ı���tradeoff��0���ӵ�1,��tradeoff���ӹ�����,ĳϵͳ��Ӧ��coefficient��֮�仯��
 * �˳��������Щ��Ϣ��
 * @author 1
 *
 */
public class Train_tradeoff {
	
	public static void processSummary(String runId,String packageName){
		//����summary�ļ�,for webTrackDiversity2010
		
		SummaryAnalysis.round = 50;
		
		//��ȡERR-IA@20��Ϣ
		SummaryAnalysis.extractERR_IA20("./"+packageName+"/summary.ndeval."+runId, "./"+packageName+"/err_IA20."+runId);
	}
	public static void processPrediction(String runId,String packageName) throws IOException{
		
		//����SD
		predictor.SD predictorSD=new predictor.SD();
		predictorSD.setK(100);//��predictorSD��k��Ϊ1000
		predictorSD.getSDScores("./"+packageName+"/input."+runId,"./"+packageName+"/sDScore."+runId);
		System.out.println("����input����ÿ��query��SDֵ,����SDScore�����ļ�,�����..");

		//����WIG
		predictor.WIG predictorWIG=new predictor.WIG();
		predictorWIG.setK(5);//��predictorWIG��k��Ϊ5
		/**
		 * �����QueryLength.getQueryLength()ΪpackageName���е�,
		 */
		predictorWIG.setQueryMap(QueryLength.getQueryLength("./"+packageName+"/queries.101-150.txt"));
		predictorWIG.getWIGScores("./"+packageName+"/input."+runId,"./"+packageName+"/wIGScore."+runId);
		System.out.println("����input����ÿ��query��WIGֵ,����WIGScore�����ļ�,�����..");
		
		//����IA_SUM
		predictorIA_SUM.IA_SUM.getIA_SUMScores("./"+packageName+"/input."+runId, "./"+packageName+"/input.indri2011subquery_60addRank", "./"+packageName+"/output_middle", "./"+packageName+"/iA_SUMScore."+runId);
		
		//����SDMultiWIG
		newPredictor.SDMultiWIG newPredictorSDMultiWIG=new newPredictor.SDMultiWIG();
		newPredictorSDMultiWIG.getSDMultiWIGScores("./"+packageName+"/input."+runId, "./"+packageName+"/sDMultiWIGScore."+runId);
		System.out.println("����input����ÿ��query��SDMultiWIGֵ,����sDMultiWIGScore�����ļ�,�����..");
		
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		/*FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		String runIdFile=null;
		String tempLine=null;
		String packageName=null;
		String runId=null;
		int n=0;
		
		runIdFile="./webTrack2011/runId.txt";
		fileReader=new FileReader(runIdFile);
		bufferedReader=new BufferedReader(fileReader);
		packageName="webTrack2011";
		
		while((tempLine=bufferedReader.readLine())!=null){
			runId=tempLine.split("\\.")[1];
			System.out.println("\n\n"+(++n)+"��trackΪ"+runId);
			getGeneratedResult(packageName, runId);
			Thread.sleep(40000);
		}
		bufferedReader.close();
		System.out.println("�����������,�����!");
		*/
		String input=null;
		//input="./webTrack2011/�½��ı��ĵ� (3).txt_withCoefficientNormalized";
		input="./webTrack2011/�½��ı��ĵ� (3).txt";
		analyzeTradeoff(input);
		
	}
	
	/**
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void getGeneratedResult(String packageName,String runId) throws IOException {

		//����summary�ļ�,��ȡERR-IA@20��Ϣ
		processSummary(runId,packageName);

		//����input�ļ�,����Ԥ���㷨,�õ�Ԥ����Ϣ
		processPrediction(runId,packageName);
		
		System.out.println("\n");
		//��ʾÿ��tradeoff�µ�pearson,kendallϵ��
		double tradeoff=0;
		for(int i=0;i<101;i++){
			//����SDMultiWIG_IASUM
			newPredictor.SDMultiWIG_IASUM newPredictorSDMultiWIG_IASUM=new newPredictor.SDMultiWIG_IASUM();
			newPredictorSDMultiWIG_IASUM.setTradeoff(tradeoff);//��SDMultiWIG_IASUM��tradeoff��Ϊ0.5
			newPredictorSDMultiWIG_IASUM.getSDMultiWIG_IASUMScores("./"+packageName+"/input."+runId, "./"+packageName+"/sDMultiWIG_IASUMScore."+runId);
			//System.out.println("����input����ÿ��query��SDMultiWIG_IASUMֵ,����sDMultiWIG_IASUMScore�����ļ�,�����..");
			
			System.out.println("tradeoff="+tradeoff);
			//Ԥ��ֵ��ERR-IA@20��pearson kendallϵ��:
			//����pearson�㷨
			//sDMultiWIG_IASUM��Ӧ��pearson
			CorrelationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/sDMultiWIG_IASUMScore."+runId,"./"+packageName+"/err_IA20."+runId);

			//����kendall�㷨
			//sDMultiWIG_IASUM��Ӧ��kendall
			temporary.Kendall.loadScoreAndComputeKendall("./"+packageName+"/sDMultiWIG_IASUMScore."+runId,"./"+packageName+"/err_IA20."+runId);
			
			//tradeoff����0.01
			tradeoff=add(tradeoff,0.01);
		}

	}

	/**
	 * �ṩ��ȷ�ļӷ����㡣
	 * 
	 * @param v1 ������
	 * @param v2 ����
	 * @return ���������ĺ�
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}
	/**
	 * ����input�ļ��е�tradeoff,��ú��ʵ�tradeoff��
	 * @throws IOException 
	 * 
	 */
	public static void analyzeTradeoff(String input) throws IOException{
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		String tempLine=null;
		ArrayList<Run_tradeoff> array_run=new ArrayList<Run_tradeoff>();
		Run_tradeoff run=null;
		
		//��input�е���Ϣ����array_run��
		fileReader=new FileReader(input);
		bufferedReader=new BufferedReader(fileReader);
		
		while((tempLine=bufferedReader.readLine())!=null){
			run=new Run_tradeoff();
			run.runId=tempLine.replaceFirst("[\\d]+��trackΪ", "");
			//tradeoffΪ0,0.01,0.02...1,��101��
			for(int i=0;i<101;i++){
				bufferedReader.readLine();
				tempLine=bufferedReader.readLine();
				run.pearson[i]=Double.parseDouble(tempLine.split("=")[1].trim());
				tempLine=bufferedReader.readLine();
				run.kendall[i]=Double.parseDouble(tempLine.split("=")[1].trim());
			}
			//ѡȡpearson,kendall�е����ֵ,��ֵ��p_bestTradeoff,p_bestCoeff,k_bestTradeoff,k_bestCoeff��
			run.chooseP_best();
			run.chooseK_best();
			//��run����array_run��
			array_run.add(run);
			//����������
			bufferedReader.readLine();
			bufferedReader.readLine();
		}
		bufferedReader.close();
		
		//����array_run,��ȡÿ��run��bestTradeoff,bestCoeff,�����p_tradeoffƽ��ֵ,k_tradeoffƽ��ֵ
		double p_tradeoff=0;
		double k_tradeoff=0;
		//����p_tradeoff��ƽ��ֵ
		for(int i=0;i<array_run.size();i++){
			run=array_run.get(i);
			p_tradeoff=add(p_tradeoff,run.p_bestTradeoff);
			System.out.println(run.runId+"\t"+run.p_bestTradeoff+"\t"+run.p_bestCoeff);
		}
		p_tradeoff=p_tradeoff/array_run.size();
		System.out.println("\np_tradeoff��ƽ��ֵ="+p_tradeoff+"\n\n");
		//����k_tradeoff��ƽ��ֵ
		for(int i=0;i<array_run.size();i++){
			run=array_run.get(i);
			k_tradeoff=add(k_tradeoff,run.k_bestTradeoff);
			System.out.println(run.runId+"\t"+run.k_bestTradeoff+"\t"+run.k_bestCoeff);
		}
		k_tradeoff=k_tradeoff/array_run.size();
		System.out.println("\nk_tradeoff��ƽ��ֵ="+k_tradeoff+"\n\n");
		//��ú��ʵ�tradeoff
		double tradeoff=0;
		tradeoff=(p_tradeoff+k_tradeoff)/2;
		System.out.println("���ʵ�tradeoff="+tradeoff);
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
	 * ѡ��pearson�е����ֵ,��ֵ��p_bestTradeoff,p_bestCoeff;
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
	 * ѡ��kendall�е����ֵ,��ֵ��k_bestTradeoff,k_bestCoeff;
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
