package evaluation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import utils.Arith;

public class Summary_analy {
	
	/**
	 * ��ȡsummary�ļ��е�amean ERR-IA@20ֵ
	 * @param input
	 * @throws IOException 
	 */
	public static double getERR_IA20amean(String input) throws IOException{
		FileReader fileReader=null;
		BufferedReader buffReader=null;
		String tempLine=null;
		String[] terms=null;
		double err=0;
		
		fileReader=new FileReader(input);
		buffReader=new BufferedReader(fileReader);
		while((tempLine=buffReader.readLine())!=null){
			if(tempLine.contains(",amean,")){
				terms=tempLine.split(",");
				err=Double.valueOf(terms[4]);
			}
		}
		buffReader.close();
		return err;
	}
	/**
	 * ��summaryFile��lantaƴ��,�γ�summary�ļ���ַ,����summary�ļ���amean ERR-IA@20,
	 * ��ȡ����amean ERR-IA@20��Ӧ��lantaֵ��
	 * ֮��ɾ��summaryFile�ļ�,����summaryFile+lanta �ļ�����ΪsummaryFile;
	 * ����ɾ��inputFile�ļ�,����inputFile+lanta �ļ�����ΪinputFile
	 *  
	 * @throws IOException 
	 * 
	 */
	public static void analyze_bestLanta(String summaryFile,String inputFile) throws IOException{
		double lanta=0;//lanta�Բ���0.1,��0���ӵ�1
		String summary_2=null;//�洢summaryFile��lantaƴ�ɵĵ�ַ
		double[] err=new double[11];
		
		//��ȡsummary_2�ļ��е�amean ERR-IA@20ֵ
		for(int i=0;i<11;i++){
			summary_2=summaryFile+lanta;
			err[i]=getERR_IA20amean(summary_2);
			//lanta����0.1
			lanta=Arith.add(lanta,0.1);
		}
		//��ȡ���amean ERR-IA@20ֵ��Ӧ��lantaֵ
		double err_best=err[0];
		int i_best=0;//�洢err_best��Ӧ��iֵ
		for(int i=0;i<11;i++){
			if(err_best<err[i]){
				err_best=err[i];
				i_best=i;
			}
		}
		lanta=Arith.div(i_best, 10);
		//ɾ��summaryFile�ļ�,����summaryFile+lanta �ļ�����ΪsummaryFile
		File file1=null;
		File file2=null;
		file1=new File(summaryFile);
		file1.delete();
		file2=new File(summaryFile+lanta);
		file2.renameTo(file1);
		//����ɾ��inputFile�ļ�,����inputFile+lanta �ļ�����ΪinputFile
		file1=new File(inputFile);
		file1.delete();
		file2=new File(inputFile+lanta);
		file2.renameTo(file1);
		System.out.println("���amean ERR-IA@20ֵ��Ӧ��lanta,lanta="+lanta);
	}
	/**
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException{
		//
		wait_forDelete2();
	}
	/**
	 * ����ɾ���������
	 * @throws IOException 
	 */
	public static void wait_forDelete(String summaryFile,String inputFile) throws IOException{
		double lanta=0;//lanta�Բ���0.1,��0���ӵ�1
		String summary_2=null;//�洢summaryFile��lantaƴ�ɵĵ�ַ
		double[] err=new double[11];
		
		//��ȡsummary_2�ļ��е�amean ERR-IA@20ֵ
		for(int i=0;i<10;i++){
			summary_2=summaryFile+lanta;
			err[i]=getERR_IA20amean(summary_2);
			//lanta����0.1
			lanta=Arith.add(lanta,0.1);
		}
		//��ȡ���amean ERR-IA@20ֵ��Ӧ��lantaֵ
		double err_best=err[0];
		int i_best=0;//�洢err_best��Ӧ��iֵ
		for(int i=0;i<10;i++){
			if(err_best<err[i]){
				err_best=err[i];
				i_best=i;
			}
		}
		lanta=Arith.div(i_best, 10);
		//��summaryFile+lanta�ļ�����ΪsummaryFile+lanta+"_best"
		File file1=null;
		File file2=null;
		file1=new File(summaryFile+lanta);
		file2=new File(summaryFile+lanta+"_best");
		file1.renameTo(file2);
		//��inputFile+lanta�ļ�����ΪinputFile+lanta+"_best"
		file1=new File(inputFile+lanta);
		file2=new File(inputFile+lanta+"_best");
		file1.renameTo(file2);
		System.out.println("���amean ERR-IA@20ֵ��Ӧ��lanta,lanta="+lanta);
	}
	/**
	 * ����ɾ���������
	 * @throws IOException 
	 */
	public static void wait_forDelete2() throws IOException{
		String summaryFile=null;
		String inputFile=null;
		//�ҵ�2009�����õ�summaryFile,inputFile
		summaryFile="./diversification/summary.ndeval.2009-";
		inputFile="./diversification/input.2009-";
		wait_forDelete(summaryFile,inputFile);
		
		//�ҵ�2010�����õ�summaryFile,inputFile
		summaryFile="./diversification/summary.ndeval.2010-";
		inputFile="./diversification/input.2010-";
		wait_forDelete(summaryFile,inputFile);
		
		//�ҵ�2011�����õ�summaryFile,inputFile
		summaryFile="./diversification/summary.ndeval.2011-";
		inputFile="./diversification/input.2011-";
		wait_forDelete(summaryFile,inputFile);
		
		//�ҵ�2012�����õ�summaryFile,inputFile
		summaryFile="./diversification/summary.ndeval.2012-";
		inputFile="./diversification/input.2012-";
		wait_forDelete(summaryFile,inputFile);
		
	}

}
