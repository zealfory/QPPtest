package temporary;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 对input.runId文件进行分数规范化 score=1/(60+rank) 
 * @author 1
 *
 */
public class Test6 {
	
	/**
	 * 对input.runId文件进行分数规范化 score=1/(60+rank) 
	 * @param args
	 * @throws IOException
	 */
	public static void normalizeScore() throws IOException{
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		FileWriter fileWriter=null;
		String tempLine=null;
		String[] terms=null;
		double score=0;
		
		fileReader=new FileReader("webTrackDiversity2010/input.qirdcsuog3");
		bufferedReader=new BufferedReader(fileReader);
		fileWriter=new FileWriter("webTrackDiversity2010/input.qirdcsuog3_2");
		while((tempLine=bufferedReader.readLine())!=null){
			terms=tempLine.split("  | \t| |\t");
			score=1.0/(60+Integer.parseInt(terms[3]));
			tempLine=terms[0]+"\t"+terms[1]+"\t"+terms[2]+"\t"+terms[3]+"\t"+String.valueOf(score)+"\t"+terms[5]+"\n";
			fileWriter.write(tempLine);
		}
		fileWriter.close();
		bufferedReader.close();
		System.out.println("score=1/(60+rank) 分数规范化完成..");
	}
	
	/**
	 * 对pearson,kendall系数进行规范化,保留小数点后3位,使用了四舍五入
	 * @param input
	 * @throws IOException 
	 */
	public static void normalizeCoefficient(String input) throws IOException{
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		FileWriter fileWriter=null;
		String tempLine=null;
		String[] terms=null;
		
		fileReader=new FileReader(input);
		bufferedReader=new BufferedReader(fileReader);
		fileWriter=new FileWriter(input+"_withCoefficientNormalized");
		while((tempLine=bufferedReader.readLine())!=null){
			if(tempLine.contains("=")){
				terms=tempLine.split("=");
				tempLine=terms[0]+"="+String.format("%.3f", Double.parseDouble(terms[1].trim()));
			}
			fileWriter.write(tempLine+"\n");
		}
		fileWriter.close();
		bufferedReader.close();
		System.out.println("对pearson,kendall系数进行规范化,保留小数点后3位,已完成..");
	}
	
	/**
	 * 读取pearson,kendall系数文件,以表格的形式显示文件的内容,并更新原文件。
	 * @throws IOException 
	 * 
	 */
	public static void tableCoefficient(String input) throws IOException{
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		String tempLine=null;
		ArrayList<Run_coefficient> array_run=new ArrayList<Run_coefficient>();
		Run_coefficient run=null;
		FileWriter fileWriter=null;
		
		fileReader=new FileReader(input);
		bufferedReader=new BufferedReader(fileReader);
		
		//读取pearson,kendall系数文件,存入array_run中
		while((tempLine=bufferedReader.readLine())!=null){
			run=new Run_coefficient();
			//系数文件中,一个系统占了35行,于是程序中以11行作为循环体。
			run.runId=tempLine.replaceFirst("[\\d]+、track为", "");
			bufferedReader.readLine();
			bufferedReader.readLine();
			
			
			tempLine=bufferedReader.readLine();
			run.p_nQC=tempLine.split("=")[1];
			tempLine=bufferedReader.readLine();
			run.p_sD2=tempLine.split("=")[1];
			tempLine=bufferedReader.readLine();
			run.p_wIG=tempLine.split("=")[1];
			tempLine=bufferedReader.readLine();
			run.p_sMV=tempLine.split("=")[1];
			tempLine=bufferedReader.readLine();
			run.p_iA_SUM=tempLine.split("=")[1];
			tempLine=bufferedReader.readLine();
			run.p_sD2MultiWIG=tempLine.split("=")[1];
			tempLine=bufferedReader.readLine();
			run.p_iASUM2=tempLine.split("=")[1];
			tempLine=bufferedReader.readLine();
			run.p_iASUM3=tempLine.split("=")[1];
			tempLine=bufferedReader.readLine();
			run.p_cF=tempLine.split("=")[1];
			tempLine=bufferedReader.readLine();
			run.p_cF2=tempLine.split("=")[1];
			
			
			tempLine=bufferedReader.readLine();
			run.k_nQC=tempLine.split("=")[1];
			tempLine=bufferedReader.readLine();
			run.k_sD2=tempLine.split("=")[1];
			tempLine=bufferedReader.readLine();
			run.k_wIG=tempLine.split("=")[1];
			tempLine=bufferedReader.readLine();
			run.k_sMV=tempLine.split("=")[1];
			tempLine=bufferedReader.readLine();
			run.k_iA_SUM=tempLine.split("=")[1];
			tempLine=bufferedReader.readLine();
			run.k_sD2MultiWIG=tempLine.split("=")[1];
			tempLine=bufferedReader.readLine();
			run.k_iASUM2=tempLine.split("=")[1];
			tempLine=bufferedReader.readLine();
			run.k_iASUM3=tempLine.split("=")[1];
			tempLine=bufferedReader.readLine();
			run.k_cF=tempLine.split("=")[1];
			tempLine=bufferedReader.readLine();
			run.k_cF2=tempLine.split("=")[1];
			
			
			tempLine=bufferedReader.readLine();
			run.s_nQC=tempLine.split("=")[1];
			tempLine=bufferedReader.readLine();
			run.s_sD2=tempLine.split("=")[1];
			tempLine=bufferedReader.readLine();
			run.s_wIG=tempLine.split("=")[1];
			tempLine=bufferedReader.readLine();
			run.s_sMV=tempLine.split("=")[1];
			tempLine=bufferedReader.readLine();
			run.s_iA_SUM=tempLine.split("=")[1];
			tempLine=bufferedReader.readLine();
			run.s_sD2MultiWIG=tempLine.split("=")[1];
			tempLine=bufferedReader.readLine();
			run.s_iASUM2=tempLine.split("=")[1];
			tempLine=bufferedReader.readLine();
			run.s_iASUM3=tempLine.split("=")[1];
			tempLine=bufferedReader.readLine();
			run.s_cF=tempLine.split("=")[1];
			tempLine=bufferedReader.readLine();
			run.s_cF2=tempLine.split("=")[1];
			
			
			//把run存入array_run中
			array_run.add(run);
			bufferedReader.readLine();
			bufferedReader.readLine();
		}
		//关闭bufferedReader
		bufferedReader.close();
		//把array_run中的run存入写入文件中
		fileWriter=new FileWriter(input+"_table");
		//把预测值与ERR-IA@20的pearson系数写入文件
		tempLine="预测值与ERR-IA@20的pearson系数:\n";
		//tempLine=tempLine+"runId\tWIG\tSD\tSMV\tNQC\tIA_SUM\tSDMulti\tSDMultiWIG_IASUM\tCF\tCF_IASUM\n";
		tempLine=tempLine+"runId\tWIG\tSD2\tSMV\tNQC\tIASUM\tSD2MultiWIG\tIASUM2\tIASUM3\tCF\tCF2\n";
		
		fileWriter.write(tempLine);
		for(int i=0;i<array_run.size();i++){
			run=array_run.get(i);
			tempLine=run.runId+"\t"+run.p_wIG+"\t"+run.p_sD2+"\t"+run.p_sMV+"\t"+run.p_nQC+"\t"+run.p_iA_SUM+"\t"+run.p_sD2MultiWIG+"\t"+run.p_iASUM2+"\t"+run.p_iASUM3+"\t"+run.p_cF+"\t"+run.p_cF2+"\n";
			
			fileWriter.write(tempLine);
		}
		//把预测值与ERR-IA@20的kendall系数写入文件
		tempLine="\n\n预测值与ERR-IA@20的kendall系数:\n";
		tempLine=tempLine+"runId\tWIG\tSD2\tSMV\tNQC\tIASUM\tSD2MultiWIG\tIASUM2\tIASUM3\tCF\tCF2\n";
				
		fileWriter.write(tempLine);
		for(int i=0;i<array_run.size();i++){
			run=array_run.get(i);
			tempLine=run.runId+"\t"+run.k_wIG+"\t"+run.k_sD2+"\t"+run.k_sMV+"\t"+run.k_nQC+"\t"+run.k_iA_SUM+"\t"+run.k_sD2MultiWIG+"\t"+run.k_iASUM2+"\t"+run.k_iASUM3+"\t"+run.k_cF+"\t"+run.k_cF2+"\n";
					
			fileWriter.write(tempLine);
		}
		//把预测值与ERR-IA@20的spearman系数写入文件
		tempLine="\n\n预测值与ERR-IA@20的spearman系数:\n";
		tempLine=tempLine+"runId\tWIG\tSD2\tSMV\tNQC\tIASUM\tSD2MultiWIG\tIASUM2\tIASUM3\tCF\tCF2\n";
				
		fileWriter.write(tempLine);
		for(int i=0;i<array_run.size();i++){
			run=array_run.get(i);
			tempLine=run.runId+"\t"+run.s_wIG+"\t"+run.s_sD2+"\t"+run.s_sMV+"\t"+run.s_nQC+"\t"+run.s_iA_SUM+"\t"+run.s_sD2MultiWIG+"\t"+run.s_iASUM2+"\t"+run.s_iASUM3+"\t"+run.s_cF+"\t"+run.s_cF2+"\n";
				
			fileWriter.write(tempLine);
		}
		fileWriter.close();
		System.out.println("读取pearson,kendall,spearman系数文件,以表格的形式显示文件的内容,已完成..");
	}
	
	public static void main(String[] args) throws IOException{
		String input=null;
		//input="./webTrackDiversity2010/新建文本文档 (3).txt";
		//input="./webTrack2011/新建文本文档 (3).txt";
		input="./diversification/新建文本文档 (3).txt";
		//input="./trec/新建文本文档 (3).txt";
		normalizeCoefficient(input);
		//input="./webTrackDiversity2010/新建文本文档 (3).txt_withCoefficientNormalized";
		//input="./webTrack2011/新建文本文档 (3).txt_withCoefficientNormalized";
		input="./diversification/新建文本文档 (3).txt_withCoefficientNormalized";
		//input="./trec/新建文本文档 (3).txt_withCoefficientNormalized";
		tableCoefficient(input);
		
		
	}

}
class Run_coefficient{
	String runId;
	String p_nQC;//pearson_nQC系数
	String p_sD;
	String p_wIG;
	String p_sMV;
	String p_iA_SUM;
	String p_sDMultiWIG;
	String p_sDMultiWIG_IASUM;
	
	String p_sD2;
	String p_sD2MultiWIG;
	String p_iASUM2;
	String p_iASUM3;
	String p_cF;
	String p_cF2;
	
	
	String k_nQC;//kendall_nQC系数
	String k_sD;
	String k_wIG;
	String k_sMV;
	String k_iA_SUM;
	String k_sDMultiWIG;
	String k_sDMultiWIG_IASUM;
	String k_sD_IASUM;
	String k_cF_IASUM;
	
	String k_sD2;
	String k_sD2MultiWIG;
	String k_iASUM2;
	String k_iASUM3;
	String k_cF;
	String k_cF2;
	
	
	String s_nQC;//spearman_nQC系数
	String s_sD;
	String s_wIG;
	String s_sMV;
	
	String s_sD2;
	String s_iA_SUM;
	String s_sD2MultiWIG;
	String s_iASUM2;
	String s_iASUM3;
	String s_cF;
	String s_cF2;
	
	
}
