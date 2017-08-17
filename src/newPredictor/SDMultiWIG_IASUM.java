package newPredictor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * ������,IA_SUM����дΪIASUM,sDMultiWIG_IASUMScore��дΪs_iASUMScore
 * 
 * @author 1
 *
 */
public class SDMultiWIG_IASUM {
	public double tradeoff=0;//sDMultiWIGScore��iASUMScore��Ȩ��
	
	public double getTradeoff() {
		return tradeoff;
	}
	public void setTradeoff(double tradeoff) {
		this.tradeoff = tradeoff;
	}
	/**
	 * changed on 06/12,û��ʹ��tradeoff��ideal_iASUM������
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
	 * �����Ѿ�����õ�sDMultiWIGScore.runId,iA_SUMScore.runId�ļ�����ÿ��query��SDMultiWIG_IASUMֵ,����s_iASUMScore�����ļ���
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
		double sDMultiWIGScore=0;//��ʱ���sDMultiWIGScore.runId�ļ���ĳqueryId��Ӧ��sDMultiWIGScore
		double iASUMScore=0;//��ʱ���iA_SUMScore.runId�ļ���ĳqueryId��Ӧ��iA_SUMScore
		double s_iASUMScore=0;//��ʱ���һ��query��sDMultiWIG_IASUMScore
		
		
		input_s=input.replaceFirst("input\\.", "sDMultiWIGScore.");
		input_iASUM=input.replaceFirst("input\\.", "iA_SUMScore.");
		fileReader_s=new FileReader(input_s);
		bufferedReader_s=new BufferedReader(fileReader_s);
		fileReader_iASUM=new FileReader(input_iASUM);
		bufferedReader_iASUM=new BufferedReader(fileReader_iASUM);
		fileWriter=new FileWriter(output);
		//��ȡinput_s,input_iASUM�ļ���ĳqueryId��Ӧ��sDMultiWIGScore��iA_SUMScore,����sDMultiWIG_IASUMֵ,������output�ļ�
		while((tempLine_s=bufferedReader_s.readLine())!=null&&(tempLine_iASUM=bufferedReader_iASUM.readLine())!=null){
			terms_s=tempLine_s.split(" |\t");
			terms_iASUM=tempLine_iASUM.split(" |\t");
			sDMultiWIGScore=Double.parseDouble(terms_s[3]);
			iASUMScore=Double.parseDouble(terms_iASUM[3]);
			s_iASUMScore=computeSDMultiWIG_IASUM(sDMultiWIGScore,iASUMScore);
			//��sDMultiWIG_IASUMScoreд��output�ļ�
			fileWriter.write("queryId:\t"+terms_s[1]+"\tSDMultiWIG_IASUM:\t"+s_iASUMScore+"\n");
		}
		fileWriter.close();
		bufferedReader_iASUM.close();
		bufferedReader_s.close();
	}

	public static void main(String[] args){

	}

}
