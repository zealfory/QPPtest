package evaluation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import utils.Arith;

public class Summary_analy {
	
	/**
	 * 获取summary文件中的amean ERR-IA@20值
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
	 * 把summaryFile和lanta拼接,形成summary文件地址,分析summary文件的amean ERR-IA@20,
	 * 获取最大的amean ERR-IA@20对应的lanta值。
	 * 之后删除summaryFile文件,并把summaryFile+lanta 文件改名为summaryFile;
	 * 接着删除inputFile文件,并把inputFile+lanta 文件改名为inputFile
	 *  
	 * @throws IOException 
	 * 
	 */
	public static void analyze_bestLanta(String summaryFile,String inputFile) throws IOException{
		double lanta=0;//lanta以步长0.1,从0增加到1
		String summary_2=null;//存储summaryFile和lanta拼成的地址
		double[] err=new double[11];
		
		//获取summary_2文件中的amean ERR-IA@20值
		for(int i=0;i<11;i++){
			summary_2=summaryFile+lanta;
			err[i]=getERR_IA20amean(summary_2);
			//lanta增加0.1
			lanta=Arith.add(lanta,0.1);
		}
		//获取最大amean ERR-IA@20值对应的lanta值
		double err_best=err[0];
		int i_best=0;//存储err_best对应的i值
		for(int i=0;i<11;i++){
			if(err_best<err[i]){
				err_best=err[i];
				i_best=i;
			}
		}
		lanta=Arith.div(i_best, 10);
		//删除summaryFile文件,并把summaryFile+lanta 文件改名为summaryFile
		File file1=null;
		File file2=null;
		file1=new File(summaryFile);
		file1.delete();
		file2=new File(summaryFile+lanta);
		file2.renameTo(file1);
		//接着删除inputFile文件,并把inputFile+lanta 文件改名为inputFile
		file1=new File(inputFile);
		file1.delete();
		file2=new File(inputFile+lanta);
		file2.renameTo(file1);
		System.out.println("最大amean ERR-IA@20值对应的lanta,lanta="+lanta);
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
	 * 可以删除这个方法
	 * @throws IOException 
	 */
	public static void wait_forDelete(String summaryFile,String inputFile) throws IOException{
		double lanta=0;//lanta以步长0.1,从0增加到1
		String summary_2=null;//存储summaryFile和lanta拼成的地址
		double[] err=new double[11];
		
		//获取summary_2文件中的amean ERR-IA@20值
		for(int i=0;i<10;i++){
			summary_2=summaryFile+lanta;
			err[i]=getERR_IA20amean(summary_2);
			//lanta增加0.1
			lanta=Arith.add(lanta,0.1);
		}
		//获取最大amean ERR-IA@20值对应的lanta值
		double err_best=err[0];
		int i_best=0;//存储err_best对应的i值
		for(int i=0;i<10;i++){
			if(err_best<err[i]){
				err_best=err[i];
				i_best=i;
			}
		}
		lanta=Arith.div(i_best, 10);
		//把summaryFile+lanta文件改名为summaryFile+lanta+"_best"
		File file1=null;
		File file2=null;
		file1=new File(summaryFile+lanta);
		file2=new File(summaryFile+lanta+"_best");
		file1.renameTo(file2);
		//把inputFile+lanta文件改名为inputFile+lanta+"_best"
		file1=new File(inputFile+lanta);
		file2=new File(inputFile+lanta+"_best");
		file1.renameTo(file2);
		System.out.println("最大amean ERR-IA@20值对应的lanta,lanta="+lanta);
	}
	/**
	 * 可以删除这个方法
	 * @throws IOException 
	 */
	public static void wait_forDelete2() throws IOException{
		String summaryFile=null;
		String inputFile=null;
		//找到2009年的最好的summaryFile,inputFile
		summaryFile="./diversification/summary.ndeval.2009-";
		inputFile="./diversification/input.2009-";
		wait_forDelete(summaryFile,inputFile);
		
		//找到2010年的最好的summaryFile,inputFile
		summaryFile="./diversification/summary.ndeval.2010-";
		inputFile="./diversification/input.2010-";
		wait_forDelete(summaryFile,inputFile);
		
		//找到2011年的最好的summaryFile,inputFile
		summaryFile="./diversification/summary.ndeval.2011-";
		inputFile="./diversification/input.2011-";
		wait_forDelete(summaryFile,inputFile);
		
		//找到2012年的最好的summaryFile,inputFile
		summaryFile="./diversification/summary.ndeval.2012-";
		inputFile="./diversification/input.2012-";
		wait_forDelete(summaryFile,inputFile);
		
	}

}
