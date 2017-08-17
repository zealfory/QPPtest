package newPredictor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 程序中,IA_SUM被简写为IASUM,sD2MultiWIG_IASUMScore简写为s_iASUMScore
 * <br>式子为: IASUM2=SD2*WIG*IASUM
 * @author 1
 *
 */
public class IASUM2 {
	
	/**
	 * @param sD2MultiWIGScore
	 * @param iASUMScore
	 * @return
	 */
	public double computeIASUM2(double sD2MultiWIGScore,double iASUMScore){
		double iASUM2Score=0;
		iASUM2Score=sD2MultiWIGScore*iASUMScore;
		return iASUM2Score;
	}

	/**
	 * 根据已经计算好的sD2MultiWIGScore.runId,iA_SUMScore.runId文件计算每个query的IASUM2值,并将iASUM2Score存入文件。
	 * @throws IOException 
	 * 
	 * */
	public void getIASUM2Scores(String input,String output) throws IOException{
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
		double sD2MultiWIGScore=0;//临时存放sD2MultiWIGScore.runId文件中某queryId对应的sD2MultiWIGScore
		double iASUMScore=0;//临时存放iA_SUMScore.runId文件中某queryId对应的iA_SUMScore
		double iASUM2Score=0;//临时存放一个query的IASUM2Score
		
		
		input_s=input.replaceFirst("input\\.", "sD2MultiWIGScore.");
		input_iASUM=input.replaceFirst("input\\.", "iA_SUMScore.");
		fileReader_s=new FileReader(input_s);
		bufferedReader_s=new BufferedReader(fileReader_s);
		fileReader_iASUM=new FileReader(input_iASUM);
		bufferedReader_iASUM=new BufferedReader(fileReader_iASUM);
		fileWriter=new FileWriter(output);
		//读取input_s,input_iASUM文件中某queryId对应的sD2MultiWIGScore和iA_SUMScore,计算sD2MultiWIG_IASUM值,并存入output文件
		while((tempLine_s=bufferedReader_s.readLine())!=null&&(tempLine_iASUM=bufferedReader_iASUM.readLine())!=null){
			terms_s=tempLine_s.split(" |\t");
			terms_iASUM=tempLine_iASUM.split(" |\t");
			sD2MultiWIGScore=Double.parseDouble(terms_s[3]);
			iASUMScore=Double.parseDouble(terms_iASUM[3]);
			iASUM2Score=computeIASUM2(sD2MultiWIGScore,iASUMScore);
			//把sD2MultiWIG_IASUMScore写入output文件
			fileWriter.write("queryId:\t"+terms_s[1]+"\tIASUM2:\t"+iASUM2Score+"\n");
		}
		fileWriter.close();
		bufferedReader_iASUM.close();
		bufferedReader_s.close();
	}

	public static void main(String[] args){

	}

}
