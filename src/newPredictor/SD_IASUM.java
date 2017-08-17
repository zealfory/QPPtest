package newPredictor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SD_IASUM {
	
	/**
	 * 
	 * @param sDScore
	 * @param iASUMScore
	 * @return
	 */
	public double computeSD_IASUM(double sDScore,double iASUMScore){
		double sD_IASUMScore=0;
		sD_IASUMScore=Math.pow(Math.E+sDScore, iASUMScore);
		return sD_IASUMScore;
	}
	
	/**
	 * 根据已经计算好的sDScore.runId,iA_SUMScore.runId文件计算每个query的SD_IASUM值,并将sD_IASUMScore存入文件
	 * @throws IOException 
	 *  
	 * */
	public void getSD_IASUMScores(String input,String output) throws IOException{
		String input_sD=null;
		String input_iASUM=null;
		FileReader fileReader_sD=null;
		BufferedReader buffReader_sD=null;
		FileReader fileReader_iASUM=null;
		BufferedReader buffReader_iASUM=null;
		FileWriter fileWriter=null;
		String tempLine_sD=null;
		String tempLine_iASUM=null;
		String[] terms_sD=null;
		String[] terms_iASUM=null;
		double sDScore=0;//临时存放sDScore.runId文件中某queryId对应的sDScore值
		double iASUMScore=0;//临时存放iA_SUMScore.runId文件中某queryId对应的iASUMScore值
		double sD_IASUMScore=0;//临时存放一个query的sD_IASUMScore值

		input_sD=input.replaceFirst("input\\.", "sDScore.");
		input_iASUM=input.replaceFirst("input\\.", "iA_SUMScore.");
		fileReader_sD=new FileReader(input_sD);
		buffReader_sD=new BufferedReader(fileReader_sD);
		fileReader_iASUM=new FileReader(input_iASUM);
		buffReader_iASUM=new BufferedReader(fileReader_iASUM);
		fileWriter=new FileWriter(output);
		//读取input_sD,input_iASUM文件中某queryId对应的sDScore和iASUMScore,计算sD_IASUM值,并存入output文件
		while((tempLine_sD=buffReader_sD.readLine())!=null&&(tempLine_iASUM=buffReader_iASUM.readLine())!=null){
			terms_sD=tempLine_sD.split(" |\t");
			terms_iASUM=tempLine_iASUM.split(" |\t");
			sDScore=Double.parseDouble(terms_sD[3]);
			iASUMScore=Double.parseDouble(terms_iASUM[3]);
			sD_IASUMScore=computeSD_IASUM(sDScore,iASUMScore);
			//把sD_IASUMScore写入output文件
			fileWriter.write("queryId:\t"+terms_sD[1]+"\tSD_IASUM:\t"+sD_IASUMScore+"\n");
		}
		fileWriter.close();
		buffReader_iASUM.close();
		buffReader_sD.close();
	}
	
	public static void main(String[] args){
		
	}

}
