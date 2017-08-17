package teacher;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SD_Multi_WIG {

	public double computeSD_Multi_WIG(double sDScore,double wIGScore){
		double score=0;
		//��ʱ�ĳ�SD_Add_WIG,��*�ĳ���+
		score=sDScore*wIGScore;
		return score;
	}

	/**
	 * �����Ѿ�����õ�sDScore.runId,wIGScore.runId�ļ�����ÿ��query��SD_Multi_WIGֵ,����SD_Multi_WIGֵ�����ļ�
	 * @throws IOException 
	 *  
	 * */
	public void getSD_Multi_WIGScores(String input,String output) throws IOException{
		String input_sD=null;
		String input_wIG=null;
		FileReader fileReader_sD=null;
		BufferedReader bufferedReader_sD=null;
		FileReader fileReader_wIG=null;
		BufferedReader bufferedReader_wIG=null;
		FileWriter fileWriter=null;
		BufferedWriter buffWriter=null;

		String tempLine_sD=null;
		String tempLine_wIG=null;
		String[] terms_sD=null;
		String[] terms_wIG=null;
		double sDScore=0;//��ʱ���sDScore.runId�ļ���ĳqueryId��Ӧ��sDScoreֵ
		double wIGScore=0;//��ʱ���wIGScore.runId�ļ���ĳqueryId��Ӧ��wIGScoreֵ
		double sD_Multi_WIGScore=0;//��ʱ���һ��query��sD_Multi_WIGScoreֵ

		input_sD=input.replaceFirst("input\\.", "sDScore.");
		input_wIG=input.replaceFirst("input\\.", "wIGScore.");
		fileReader_sD=new FileReader(input_sD);
		bufferedReader_sD=new BufferedReader(fileReader_sD);
		fileReader_wIG=new FileReader(input_wIG);
		bufferedReader_wIG=new BufferedReader(fileReader_wIG);
		fileWriter=new FileWriter(output);
		buffWriter=new BufferedWriter(fileWriter);
		//��ȡinput_sD,input_wIG�ļ���ĳqueryId��Ӧ��sDScore��wIGScore,����sD_Multi_WIGֵ,������output�ļ�
		while((tempLine_sD=bufferedReader_sD.readLine())!=null&&(tempLine_wIG=bufferedReader_wIG.readLine())!=null){
			terms_sD=tempLine_sD.split(" |\t");
			terms_wIG=tempLine_wIG.split(" |\t");
			sDScore=Double.parseDouble(terms_sD[3]);
			wIGScore=Double.parseDouble(terms_wIG[3]);
			sD_Multi_WIGScore=computeSD_Multi_WIG(sDScore,wIGScore);
			//��sD_Multi_WIGScoreд��output�ļ�
			fileWriter.write("queryId:\t"+terms_sD[1]+"\tSD_Multi_WIG:\t"+sD_Multi_WIGScore+"\n");
		}
		buffWriter.close();
		bufferedReader_wIG.close();
		bufferedReader_sD.close();
	}

	public static void main(String[] args){

	}

}
