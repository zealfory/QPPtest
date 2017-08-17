package newPredictor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SDMultiWIG {

	public double computeSDMultiWIG(double sDScore,double wIGScore){
		double sDMultiWIGScore=0;
		sDMultiWIGScore=(4*Math.atan(sDScore)*Math.atan(wIGScore))/Math.pow(Math.PI, 2);
		return sDMultiWIGScore;
	}

	/**
	 * �����Ѿ�����õ�sDScore.runId,wIGScore.runId�ļ�����ÿ��query��SDMultiWIGֵ,����SDMultiWIGScore�����ļ�
	 * @throws IOException 
	 *  
	 * */
	public void getSDMultiWIGScores(String input,String output) throws IOException{
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
		double sDScore=0;//��ʱ���sDScore.runId�ļ���ĳqueryId��Ӧ��sDScoreֵ
		double wIGScore=0;//��ʱ���wIGScore.runId�ļ���ĳqueryId��Ӧ��wIGScoreֵ
		double sDMultiWIGScore=0;//��ʱ���һ��query��sDMultiWIGScoreֵ

		input_sD=input.replaceFirst("input\\.", "sDScore.");
		input_wIG=input.replaceFirst("input\\.", "wIGScore.");
		fileReader_sD=new FileReader(input_sD);
		bufferedReader_sD=new BufferedReader(fileReader_sD);
		fileReader_wIG=new FileReader(input_wIG);
		bufferedReader_wIG=new BufferedReader(fileReader_wIG);
		fileWriter=new FileWriter(output);
		//��ȡinput_sD,input_wIG�ļ���ĳqueryId��Ӧ��sDScore��wIGScore,����sDMultiWIGֵ,������output�ļ�
		while((tempLine_sD=bufferedReader_sD.readLine())!=null&&(tempLine_wIG=bufferedReader_wIG.readLine())!=null){
			terms_sD=tempLine_sD.split(" |\t");
			terms_wIG=tempLine_wIG.split(" |\t");
			sDScore=Double.parseDouble(terms_sD[3]);
			wIGScore=Double.parseDouble(terms_wIG[3]);
			sDMultiWIGScore=computeSDMultiWIG(sDScore,wIGScore);
			//��sDMultiWIGScoreд��output�ļ�
			fileWriter.write("queryId:\t"+terms_sD[1]+"\tSDMultiWIG:\t"+sDMultiWIGScore+"\n");
		}
		fileWriter.close();
		bufferedReader_wIG.close();
		bufferedReader_sD.close();
	}

	public static void main(String[] args){

	}

}
