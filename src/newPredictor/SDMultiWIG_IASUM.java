package newPredictor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 程序中,IA_SUM被简写为IASUM,sDMultiWIG_IASUMScore简写为s_iASUMScore
 * 
 * @author 1
 *
 */
public class SDMultiWIG_IASUM {
	public double tradeoff=0;//sDMultiWIGScore和iASUMScore的权衡
	
	public double getTradeoff() {
		return tradeoff;
	}
	public void setTradeoff(double tradeoff) {
		this.tradeoff = tradeoff;
	}
	/**
	 * changed on 06/12,没有使用tradeoff和ideal_iASUM变量。
	 * @param sDMultiWIGScore
	 * @param iASUMScore
	 * @return
	 */
	public double computeSDMultiWIG_IASUM(double sDMultiWIGScore,double iASUMScore){
		double s_iASUMScore=0;
		//double ideal_iASUM=0.040223;
		//changed on 06/12
		//s_iASUMScore=(1-tradeoff)*sDMultiWIGScore+tradeoff*iASUMScore/ideal_iASUM;
		//s_iASUMScore=sDMultiWIGScore*iASUMScore/ideal_iASUM;
		s_iASUMScore=sDMultiWIGScore*iASUMScore;
		//
		//
		return s_iASUMScore;
	}

	/**
	 * 根据已经计算好的sDMultiWIGScore.runId,iA_SUMScore.runId文件计算每个query的SDMultiWIG_IASUM值,并将s_iASUMScore存入文件。
	 * @throws IOException 
	 * 
	 * */
	public void getSDMultiWIG_IASUMScores(String input,String output) throws IOException{
		String input_s=null;
		String input_iASUM=null;
		FileReader fileReader_s=null;
		BufferedReader bufferedReader_s=null;
		FileReader fileReader_iASUM=null;
		BufferedReader bufferedReader_iASUM=null;
		FileWriter fileWriter=null;
		String tempLine_s=null;
		String tempLine_iASUM=null;
		String[] terms_s=null;
		String[] terms_iASUM=null;
		double sDMultiWIGScore=0;//临时存放sDMultiWIGScore.runId文件中某queryId对应的sDMultiWIGScore
		double iASUMScore=0;//临时存放iA_SUMScore.runId文件中某queryId对应的iA_SUMScore
		double s_iASUMScore=0;//临时存放一个query的sDMultiWIG_IASUMScore
		
		
		input_s=input.replaceFirst("input\\.", "sDMultiWIGScore.");
		input_iASUM=input.replaceFirst("input\\.", "iA_SUMScore.");
		fileReader_s=new FileReader(input_s);
		bufferedReader_s=new BufferedReader(fileReader_s);
		fileReader_iASUM=new FileReader(input_iASUM);
		bufferedReader_iASUM=new BufferedReader(fileReader_iASUM);
		fileWriter=new FileWriter(output);
		//读取input_s,input_iASUM文件中某queryId对应的sDMultiWIGScore和iA_SUMScore,计算sDMultiWIG_IASUM值,并存入output文件
		while((tempLine_s=bufferedReader_s.readLine())!=null&&(tempLine_iASUM=bufferedReader_iASUM.readLine())!=null){
			terms_s=tempLine_s.split(" |\t");
			terms_iASUM=tempLine_iASUM.split(" |\t");
			sDMultiWIGScore=Double.parseDouble(terms_s[3]);
			iASUMScore=Double.parseDouble(terms_iASUM[3]);
			s_iASUMScore=computeSDMultiWIG_IASUM(sDMultiWIGScore,iASUMScore);
			//把sDMultiWIG_IASUMScore写入output文件
			fileWriter.write("queryId:\t"+terms_s[1]+"\tSDMultiWIG_IASUM:\t"+s_iASUMScore+"\n");
		}
		fileWriter.close();
		bufferedReader_iASUM.close();
		bufferedReader_s.close();
	}

	public static void main(String[] args){

	}

}
