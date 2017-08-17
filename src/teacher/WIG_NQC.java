package teacher;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class WIG_NQC {

	/**
	 * ����input_wIG,input_nQC�ļ�,����WIG_NQCֵ,������output�ļ�
	 * @throws IOException 
	 */
	public void computeWIG_NQC(String input_wIG,String input_nQC,String output) throws IOException{
		BufferedReader buffReader=null;
		String tempLine=null;

		//��ȡinput_wIG�ļ��е���Ϣ
		ArrayList<Struct_score> array_info=new ArrayList<Struct_score>();
		Struct_score info=null;
		buffReader=new BufferedReader(new FileReader(input_wIG));
		while((tempLine=buffReader.readLine())!=null){
			info=new Struct_score(tempLine);
			array_info.add(info);
		}
		buffReader.close();
		//����score��array_info�����еĶ�������
		Collections.sort(array_info, new Compare());
		//��array_info�����ж����rank��ֵ
		for(int i=0;i<array_info.size();i++){
			info=array_info.get(i);
			info.rank=i+1;
		}
		//����topic��array_info�����еĶ�������
		Collections.sort(array_info,new Compare2());

		//��ȡinput_nQC�ļ��е���Ϣ
		ArrayList<Struct_score> array_info2=new ArrayList<Struct_score>();
		buffReader=new BufferedReader(new FileReader(input_nQC));
		while((tempLine=buffReader.readLine())!=null){
			info=new Struct_score(tempLine);
			array_info2.add(info);
		}
		buffReader.close();
		//����score��array_info2�����еĶ�������
		Collections.sort(array_info2, new Compare());
		//��array_info2�����ж����rank��ֵ
		for(int i=0;i<array_info2.size();i++){
			info=array_info2.get(i);
			info.rank=i+1;
		}
		//����topic��array_info2�����еĶ�������
		Collections.sort(array_info2,new Compare2());

		//����array_info��array_info2����array_info3
		ArrayList<Struct_score> array_info3=new ArrayList<Struct_score>();
		for(int i=0;i<array_info.size();i++){
			info=array_info.get(i);
			info.rank=info.rank+array_info2.get(i).rank;
			array_info3.add(info);
		}

		//���array_info3�е���Ϣ
		BufferedWriter buffWriter=new BufferedWriter(new FileWriter(output));

		for(int i=0;i<array_info3.size();i++){
			info=array_info3.get(i);
			tempLine="queryId:\t"+info.topic+"\tWIG_NQC:\t"+info.rank+"\n";
			buffWriter.write(tempLine);
		}
		buffWriter.close();
		System.out.println("����WIG_NQCֵ,������output�ļ�,�����..");
	}

	/**
	 * �����Ѿ�����õ�wIGScore.runId,nQCScore.runId�ļ�����ÿ��query��WIG_NQCֵ,����WIG_NQCֵ�����ļ�
	 * @throws IOException 
	 *  
	 * */
	public void getWIG_NQCScores(String input,String output) throws IOException{
		String input_wIG=null;
		String input_nQC=null;
		input_wIG=input.replaceFirst("input\\.", "wIGScore.");
		input_nQC=input.replaceFirst("input\\.", "nQCScore.");
		computeWIG_NQC(input_wIG,input_nQC,output);		
	}

}
