package teacher;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WIG_Multi_NQC {

	public double computeWIG_Multi_NQC(double wIGScore,double nQCScore){
		double score=0;
		//��ʱ�ĳ���WIG_Add_NQC,��*�ĳ���+
		score=wIGScore*nQCScore;
		return score;
	}

	/**
	 * �����Ѿ�����õ�wIGScore.runId,nQCScore.runId�ļ�����ÿ��query��WIG_Multi_NQCֵ,����WIG_Multi_NQCֵ�����ļ�
	 * @throws IOException 
	 *  
	 * */
	public void getWIG_Multi_NQCScores(String input,String output) throws IOException{
		String input_wIG=null;
		String input_nQC=null;
		FileReader fileReader_wIG=null;
		BufferedReader bufferedReader_wIG=null;
		FileReader fileReader_nQC=null;
		BufferedReader bufferedReader_nQC=null;
		FileWriter fileWriter=null;
		BufferedWriter buffWriter=null;

		String tempLine_wIG=null;
		String tempLine_nQC=null;
		String[] terms_wIG=null;
		String[] terms_nQC=null;
		double wIGScore=0;//��ʱ���sDScore.runId�ļ���ĳqueryId��Ӧ��sDScoreֵ
		double nQCScore=0;//��ʱ���wIGScore.runId�ļ���ĳqueryId��Ӧ��wIGScoreֵ
		double wIG_Multi_NQCScore=0;//��ʱ���һ��query��sD_Multi_WIGScoreֵ

		input_wIG=input.replaceFirst("input\\.", "wIGScore.");
		input_nQC=input.replaceFirst("input\\.", "nQCScore.");
		fileReader_wIG=new FileReader(input_wIG);
		bufferedReader_wIG=new BufferedReader(fileReader_wIG);
		fileReader_nQC=new FileReader(input_nQC);
		bufferedReader_nQC=new BufferedReader(fileReader_nQC);
		fileWriter=new FileWriter(output);
		buffWriter=new BufferedWriter(fileWriter);
		//��ȡinput_sD,input_wIG�ļ���ĳqueryId��Ӧ��sDScore��wIGScore,����sD_Multi_WIGֵ,������output�ļ�
		while((tempLine_wIG=bufferedReader_wIG.readLine())!=null&&(tempLine_nQC=bufferedReader_nQC.readLine())!=null){
			terms_wIG=tempLine_wIG.split(" |\t");
			terms_nQC=tempLine_nQC.split(" |\t");
			wIGScore=Double.parseDouble(terms_wIG[3]);
			nQCScore=Double.parseDouble(terms_nQC[3]);
			wIG_Multi_NQCScore=computeWIG_Multi_NQC(wIGScore,nQCScore);
			//��wIG_Multi_NQCScoreд��output�ļ�
			fileWriter.write("queryId:\t"+terms_wIG[1]+"\tWIG_Multi_NQC:\t"+wIG_Multi_NQCScore+"\n");
		}
		buffWriter.close();
		bufferedReader_nQC.close();
		bufferedReader_wIG.close();
	}

	public static void main(String[] args){

	}

}
