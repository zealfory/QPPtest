package teacher;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SD_WIG {
	
	/**
	 * ����input_sD,input_wIG�ļ�,����SD_WIGֵ,������output�ļ�
	 * @param sDScore
	 * @param wIGScore
	 * @return
	 * @throws IOException 
	 */
	public void computeSD_WIG(String input_sD,String input_wIG,String output) throws IOException{
		BufferedReader buffReader=null;
		String tempLine=null;
		
		//��ȡinput_sD�ļ��е���Ϣ
		ArrayList<Struct_score> array_info=new ArrayList<Struct_score>();
		Struct_score info=null;
		buffReader=new BufferedReader(new FileReader(input_sD));
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
		
		//��ȡinput_wIG�ļ��е���Ϣ
		ArrayList<Struct_score> array_info2=new ArrayList<Struct_score>();
		buffReader=new BufferedReader(new FileReader(input_wIG));
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
			tempLine="queryId:\t"+info.topic+"\tSD_WIG:\t"+info.rank+"\n";
			buffWriter.write(tempLine);
		}
		buffWriter.close();
		System.out.println("����SD_WIGֵ,������output�ļ�,�����..");
	}
	
	/**
	 * �����Ѿ�����õ�sDScore.runId,wIGScore.runId�ļ�����ÿ��query��SD_WIGֵ,����SD_WIGֵ�����ļ�
	 * @throws IOException 
	 *  
	 * */
	public void getSD_WIGScores(String input,String output) throws IOException{
		String input_sD=null;
		String input_wIG=null;
		input_sD=input.replaceFirst("input\\.", "sDScore.");
		input_wIG=input.replaceFirst("input\\.", "wIGScore.");
		computeSD_WIG(input_sD,input_wIG,output);		
	}

}

class Struct_score{
	int topic;
	double score;
	int rank;//����score�����Դ�С,��rankֵ
	
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public int getTopic() {
		return topic;
	}
	public void setTopic(int topic) {
		this.topic = topic;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public Struct_score(){
		
	}
	public Struct_score(String tempLine){
		String[] terms=tempLine.split(" |\t");
		topic=Integer.parseInt(terms[1]);
		score=Double.parseDouble(terms[3]);
		rank=0;
	}
}

/**
 * ����score,��С��������
 */
class Compare implements Comparator<Struct_score>{

	@Override
	public int compare(Struct_score arg0, Struct_score arg1) {
		if(arg0.getScore()<arg1.getScore())
			return -1;
		if(arg0.getScore()>arg1.getScore())
			return 1;
		return 0;
	}
}
/**
 * ����topic,��С��������
 */
class Compare2 implements Comparator<Struct_score>{

	@Override
	public int compare(Struct_score arg0, Struct_score arg1) {
		if(arg0.getTopic()<arg1.getTopic())
			return -1;
		if(arg0.getTopic()>arg1.getTopic())
			return 1;
		return 0;
	}
}


