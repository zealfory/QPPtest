package newPredictor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CF_IASUM {
	
	/**
	 * 
	 * @param cFScore
	 * @param iASUMScore
	 * @return
	 */
	public double computeCF_IASUM(double cFScore,double iASUMScore){
		double cF_IASUMScore=0;
		cF_IASUMScore=cFScore*iASUMScore;
		return cF_IASUMScore;
	}
	/**
	 * 根据已经计算好的cFScore.runId,iA_SUMScore.runId文件计算每个query的CF_IASUM值,并将cF_IASUMScore存入文件
	 * @param input
	 * @param output
	 * @throws IOException 
	 */
	public void getCF_IASUMScores(String input,String output) throws IOException{
		String input_cF=null;
		String input_iASUM=null;
		FileReader fileReader_cF=null;
		BufferedReader buffReader_cF=null;
		FileReader fileReader_iASUM=null;
		BufferedReader buffReader_iASUM=null;
		FileWriter fileWriter=null;
		String tempLine_cF=null;
		String tempLine_iASUM=null;
		String[] terms_cF=null;
		String[] terms_iASUM=null;
		double cFScore=0;//临时存放cFScore.runId文件中某queryId对应的cFScore值
		double iASUMScore=0;//临时存放iA_SUMScore.runId文件中某queryId对应的iASUMScore值
		double cF_IASUMScore=0;//临时存放一个query的cF_IASUMScore值

		input_cF=input.replaceFirst("input\\.", "cFScore.");
		input_iASUM=input.replaceFirst("input\\.", "iA_SUMScore.");
		fileReader_cF=new FileReader(input_cF);
		buffReader_cF=new BufferedReader(fileReader_cF);
		fileReader_iASUM=new FileReader(input_iASUM);
		buffReader_iASUM=new BufferedReader(fileReader_iASUM);
		fileWriter=new FileWriter(output);
		//读取input_cF,input_iASUM文件中某queryId对应的cFScore和iASUMScore,计算cF_IASUM值,并存入output文件
		while((tempLine_cF=buffReader_cF.readLine())!=null&&(tempLine_iASUM=buffReader_iASUM.readLine())!=null){
			terms_cF=tempLine_cF.split(" |\t");
			terms_iASUM=tempLine_iASUM.split(" |\t");
			cFScore=Double.parseDouble(terms_cF[3]);
			iASUMScore=Double.parseDouble(terms_iASUM[3]);
			cF_IASUMScore=computeCF_IASUM(cFScore,iASUMScore);
			//把cF_IASUMScore写入output文件
			fileWriter.write("queryId:\t"+terms_cF[1]+"\tCF_IASUM:\t"+cF_IASUMScore+"\n");
		}
		fileWriter.close();
		buffReader_iASUM.close();
		buffReader_cF.close();
	}

}
