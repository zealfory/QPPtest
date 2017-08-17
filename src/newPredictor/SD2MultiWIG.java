package newPredictor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 20170309,SD2MultiWIG=SD2*WIG,直接相乘。
 * @author 1
 *
 */
public class SD2MultiWIG {

	public double computeSD2MultiWIG(double sD2Score,double wIGScore){
		double sDMultiWIGScore=0;
		sDMultiWIGScore=sD2Score*wIGScore;
		return sDMultiWIGScore;
		
	}

	/**
	 * 根据已经计算好的sD2Score.runId,wIGScore.runId文件计算每个query的SD2MultiWIG值,并将SD2MultiWIGScore存入文件
	 * @throws IOException 
	 *  
	 * */
	public void getSD2MultiWIGScores(String input,String output) throws IOException{
		String input_sD2=null;
		String input_wIG=null;
		FileReader fileReader_sD2=null;
		BufferedReader bufferedReader_sD2=null;
		FileReader fileReader_wIG=null;
		BufferedReader bufferedReader_wIG=null;
		FileWriter fileWriter=null;
		String tempLine_sD2=null;
		String tempLine_wIG=null;
		String[] terms_sD2=null;
		String[] terms_wIG=null;
		double sD2Score=0;//临时存放sD2Score.runId文件中某queryId对应的sD2Score值
		double wIGScore=0;//临时存放wIGScore.runId文件中某queryId对应的wIGScore值
		double sD2MultiWIGScore=0;//临时存放一个query的sD2MultiWIGScore值

		input_sD2=input.replaceFirst("input\\.", "sD2Score.");
		input_wIG=input.replaceFirst("input\\.", "wIGScore.");
		fileReader_sD2=new FileReader(input_sD2);
		bufferedReader_sD2=new BufferedReader(fileReader_sD2);
		fileReader_wIG=new FileReader(input_wIG);
		bufferedReader_wIG=new BufferedReader(fileReader_wIG);
		fileWriter=new FileWriter(output);
		//读取input_sD2,input_wIG文件中某queryId对应的sD2Score和wIGScore,计算sD2MultiWIG值,并存入output文件
		while((tempLine_sD2=bufferedReader_sD2.readLine())!=null&&(tempLine_wIG=bufferedReader_wIG.readLine())!=null){
			terms_sD2=tempLine_sD2.split(" |\t");
			terms_wIG=tempLine_wIG.split(" |\t");
			sD2Score=Double.parseDouble(terms_sD2[3]);
			wIGScore=Double.parseDouble(terms_wIG[3]);
			sD2MultiWIGScore=computeSD2MultiWIG(sD2Score,wIGScore);
			//把sD2MultiWIGScore写入output文件
			fileWriter.write("queryId:\t"+terms_sD2[1]+"\tSD2MultiWIG:\t"+sD2MultiWIGScore+"\n");
		}
		fileWriter.close();
		bufferedReader_wIG.close();
		bufferedReader_sD2.close();
	}

	public static void main(String[] args){

	}

}
