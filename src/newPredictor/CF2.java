package newPredictor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 程序中,CF为coverageFrequency,sD2MultiWIG_CFScore简写为s_cFScore
 * <br>式子为: CF2=SD2*WIG*CF
 * @author 1
 *
 */
public class CF2 {
	
	/**
	 * @param sD2MultiWIGScore
	 * @param cFScore
	 * @return
	 */
	public double computeCF2(double sD2MultiWIGScore,double cFScore){
		double cF2Score=0;
		cF2Score=sD2MultiWIGScore*cFScore;
		return cF2Score;
	}

	/**
	 * 根据已经计算好的sD2MultiWIGScore.runId,iA_SUMScore.runId文件计算每个query的IASUM2值,并将iASUM2Score存入文件。
	 * @throws IOException 
	 * 
	 * */
	public void getCF2Scores(String input,String output) throws IOException{
		String input_s=null;
		String input_cF=null;
		FileReader fileReader_s=null;
		BufferedReader bufferedReader_s=null;
		FileReader fileReader_cF=null;
		BufferedReader bufferedReader_cF=null;
		FileWriter fileWriter=null;
		String tempLine_s=null;
		String tempLine_cF=null;
		String[] terms_s=null;
		String[] terms_cF=null;
		double sD2MultiWIGScore=0;//临时存放sD2MultiWIGScore.runId文件中某queryId对应的sD2MultiWIGScore
		double cFScore=0;//临时存放iA_SUMScore.runId文件中某queryId对应的iA_SUMScore
		double cF2Score=0;//临时存放一个query的IASUM2Score
		
		
		input_s=input.replaceFirst("input\\.", "sD2MultiWIGScore.");
		input_cF=input.replaceFirst("input\\.", "cFScore.");
		fileReader_s=new FileReader(input_s);
		bufferedReader_s=new BufferedReader(fileReader_s);
		fileReader_cF=new FileReader(input_cF);
		bufferedReader_cF=new BufferedReader(fileReader_cF);
		fileWriter=new FileWriter(output);
		//读取input_s,input_iASUM文件中某queryId对应的sD2MultiWIGScore和iA_SUMScore,计算sD2MultiWIG_IASUM值,并存入output文件
		while((tempLine_s=bufferedReader_s.readLine())!=null&&(tempLine_cF=bufferedReader_cF.readLine())!=null){
			terms_s=tempLine_s.split(" |\t");
			terms_cF=tempLine_cF.split(" |\t");
			sD2MultiWIGScore=Double.parseDouble(terms_s[3]);
			cFScore=Double.parseDouble(terms_cF[3]);
			cF2Score=computeCF2(sD2MultiWIGScore,cFScore);
			//把sD2MultiWIG_IASUMScore写入output文件
			fileWriter.write("queryId:\t"+terms_s[1]+"\tCF2:\t"+cF2Score+"\n");
		}
		fileWriter.close();
		bufferedReader_cF.close();
		bufferedReader_s.close();
	}

	public static void main(String[] args){

	}

}
