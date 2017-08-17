package newPredictor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * ������,CFΪcoverageFrequency,sD2MultiWIG_CFScore��дΪs_cFScore
 * <br>ʽ��Ϊ: CF2=SD2*WIG*CF
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
	 * �����Ѿ�����õ�sD2MultiWIGScore.runId,iA_SUMScore.runId�ļ�����ÿ��query��IASUM2ֵ,����iASUM2Score�����ļ���
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
		double sD2MultiWIGScore=0;//��ʱ���sD2MultiWIGScore.runId�ļ���ĳqueryId��Ӧ��sD2MultiWIGScore
		double cFScore=0;//��ʱ���iA_SUMScore.runId�ļ���ĳqueryId��Ӧ��iA_SUMScore
		double cF2Score=0;//��ʱ���һ��query��IASUM2Score
		
		
		input_s=input.replaceFirst("input\\.", "sD2MultiWIGScore.");
		input_cF=input.replaceFirst("input\\.", "cFScore.");
		fileReader_s=new FileReader(input_s);
		bufferedReader_s=new BufferedReader(fileReader_s);
		fileReader_cF=new FileReader(input_cF);
		bufferedReader_cF=new BufferedReader(fileReader_cF);
		fileWriter=new FileWriter(output);
		//��ȡinput_s,input_iASUM�ļ���ĳqueryId��Ӧ��sD2MultiWIGScore��iA_SUMScore,����sD2MultiWIG_IASUMֵ,������output�ļ�
		while((tempLine_s=bufferedReader_s.readLine())!=null&&(tempLine_cF=bufferedReader_cF.readLine())!=null){
			terms_s=tempLine_s.split(" |\t");
			terms_cF=tempLine_cF.split(" |\t");
			sD2MultiWIGScore=Double.parseDouble(terms_s[3]);
			cFScore=Double.parseDouble(terms_cF[3]);
			cF2Score=computeCF2(sD2MultiWIGScore,cFScore);
			//��sD2MultiWIG_IASUMScoreд��output�ļ�
			fileWriter.write("queryId:\t"+terms_s[1]+"\tCF2:\t"+cF2Score+"\n");
		}
		fileWriter.close();
		bufferedReader_cF.close();
		bufferedReader_s.close();
	}

	public static void main(String[] args){

	}

}
