package newPredictor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SDAndWIG {
	
	public double computeSDAndWIG(double sDScore,double wIGScore){
		double sDAndWIGScore=0;
		sDAndWIGScore=(Math.atan(sDScore)+Math.atan(wIGScore))/Math.PI;
		return sDAndWIGScore;
	}

	/**
	 * 根据已经计算好的sDScore.runId,wIGScore.runId文件计算每个query的SDAndWIG值,并将SDAndWIGScore存入文件
	 * @throws IOException 
	 *  
	 * */
	public void getSDAndWIGScores(String input,String output) throws IOException{
		String input_sD=null;
		String input_wIG=null;
		FileReader fileReader_sD=null;
		BufferedReader bufferedReader_sD=null;
		FileReader fileReader_wIG=null;
		BufferedReader bufferedReader_wIG=null;
		FileWriter fileWriter=null;
		String tempLine_sD=null;
		String tempLine_wIG=null;
		String[] terms_sD=null;
		String[] terms_wIG=null;
		double sDScore=0;//临时存放sDScore.runId文件中某queryId对应的sDScore值
		double wIGScore=0;//临时存放wIGScore.runId文件中某queryId对应的wIGScore值
		double sDAndWIGScore=0;//临时存放一个query的sDAndWIGScore值
		
		input_sD=input.replaceFirst("input\\.", "sDScore.");
		input_wIG=input.replaceFirst("input\\.", "wIGScore.");
		fileReader_sD=new FileReader(input_sD);
		bufferedReader_sD=new BufferedReader(fileReader_sD);
		fileReader_wIG=new FileReader(input_wIG);
		bufferedReader_wIG=new BufferedReader(fileReader_wIG);
		fileWriter=new FileWriter(output);
		//读取input_sD,input_wIG文件中某queryId对应的sDScore和wIGScore,计算sDAndWIG值,并存入output文件
		while((tempLine_sD=bufferedReader_sD.readLine())!=null&&(tempLine_wIG=bufferedReader_wIG.readLine())!=null){
			terms_sD=tempLine_sD.split(" |\t");
			terms_wIG=tempLine_wIG.split(" |\t");
			sDScore=Double.parseDouble(terms_sD[3]);
			wIGScore=Double.parseDouble(terms_wIG[3]);
			sDAndWIGScore=computeSDAndWIG(sDScore,wIGScore);
			//把sDAndWIGScore写入output文件
			fileWriter.write("queryId:\t"+terms_sD[1]+"\tSDAndWIG:\t"+sDAndWIGScore+"\n");
		}
		fileWriter.close();
		bufferedReader_wIG.close();
		bufferedReader_sD.close();
	}
	
	public static void main(String[] args){
		
	}

}
