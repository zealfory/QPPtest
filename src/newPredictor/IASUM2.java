package newPredictor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * ������,IA_SUM����дΪIASUM,sD2MultiWIG_IASUMScore��дΪs_iASUMScore
 * <br>ʽ��Ϊ: IASUM2=SD2*WIG*IASUM
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
	 * �����Ѿ�����õ�sD2MultiWIGScore.runId,iA_SUMScore.runId�ļ�����ÿ��query��IASUM2ֵ,����iASUM2Score�����ļ���
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
		double sD2MultiWIGScore=0;//��ʱ���sD2MultiWIGScore.runId�ļ���ĳqueryId��Ӧ��sD2MultiWIGScore
		double iASUMScore=0;//��ʱ���iA_SUMScore.runId�ļ���ĳqueryId��Ӧ��iA_SUMScore
		double iASUM2Score=0;//��ʱ���һ��query��IASUM2Score
		
		
		input_s=input.replaceFirst("input\\.", "sD2MultiWIGScore.");
		input_iASUM=input.replaceFirst("input\\.", "iA_SUMScore.");
		fileReader_s=new FileReader(input_s);
		bufferedReader_s=new BufferedReader(fileReader_s);
		fileReader_iASUM=new FileReader(input_iASUM);
		bufferedReader_iASUM=new BufferedReader(fileReader_iASUM);
		fileWriter=new FileWriter(output);
		//��ȡinput_s,input_iASUM�ļ���ĳqueryId��Ӧ��sD2MultiWIGScore��iA_SUMScore,����sD2MultiWIG_IASUMֵ,������output�ļ�
		while((tempLine_s=bufferedReader_s.readLine())!=null&&(tempLine_iASUM=bufferedReader_iASUM.readLine())!=null){
			terms_s=tempLine_s.split(" |\t");
			terms_iASUM=tempLine_iASUM.split(" |\t");
			sD2MultiWIGScore=Double.parseDouble(terms_s[3]);
			iASUMScore=Double.parseDouble(terms_iASUM[3]);
			iASUM2Score=computeIASUM2(sD2MultiWIGScore,iASUMScore);
			//��sD2MultiWIG_IASUMScoreд��output�ļ�
			fileWriter.write("queryId:\t"+terms_s[1]+"\tIASUM2:\t"+iASUM2Score+"\n");
		}
		fileWriter.close();
		bufferedReader_iASUM.close();
		bufferedReader_s.close();
	}

	public static void main(String[] args){

	}

}
