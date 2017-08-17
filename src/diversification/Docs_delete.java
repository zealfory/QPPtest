package diversification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Docs_delete {
	
	/**
	 * 删除input文件中得分为0的文档
	 * @param input
	 * @throws IOException 
	 */
	public static void delete_docs(String input) throws IOException{
		FileReader fileReader=null;
		BufferedReader buffReader=null;
		FileWriter fileWriter=null;
		String tempLine=null;
		String[] terms=null;
		
		fileReader=new FileReader(input);
		buffReader=new BufferedReader(fileReader);
		fileWriter=new FileWriter(input+"_extracted");
		
		while((tempLine=buffReader.readLine())!=null){
			terms=tempLine.split(" |\t");
			if(!terms[4].equals("0.0")){
				fileWriter.write(tempLine+"\n");
			}
		}
		fileWriter.close();
		buffReader.close();
		//用input+"_extracted"文件替换input文件
		File file1=null;
		File file2=null;
		file1=new File(input);
		file2=new File(input+"_extracted");
		file1.delete();
		file2.renameTo(file1);
		System.out.println("删除input文件中得分为0的文档,已完成..");
	}
	/**
	 * 删除2009-2012年input文件中得分为0.0的文档
	 * @throws IOException 
	 */
	public static void getInputFile_extracted() throws IOException{
		String input=null;
		//删除2009年input文件中得分为0.0的文档
		input="./diversification/input.indri2009pm2_60addRank";
		delete_docs(input);
		
		//删除2010年input文件中得分为0.0的文档
		input="./diversification/input.indri2010pm2_60addRank";
		delete_docs(input);
		
		//删除2011年input文件中得分为0.0的文档
		input="./diversification/input.indri2011pm2_60addRank";
		delete_docs(input);
		
		//删除2012年input文件中得分为0.0的文档
		input="./diversification/input.indri2012pm2_60addRank";
		delete_docs(input);
	}
	/**
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException{
		/*//删除2009-2012年input文件中得分为0.0的文档
		getInputFile_extracted();
		*/
		wait_forDelete();
	}
	/**
	 * 可以删除这个方法
	 * 更新4 best pm2 runs,师姐的检索结果
	 * @throws IOException 
	 */
	public static void wait_forDelete() throws IOException{
		String input=null;
		//删除2009年input文件中得分为0.0的文档
		input="./diversification/input.2009-0.5";
		delete_docs(input);
		
		//删除2010年input文件中得分为0.0的文档
		input="./diversification/input.2010-0.4";
		delete_docs(input);
		
		//删除2011年input文件中得分为0.0的文档
		input="./diversification/input.2011-0.6";
		delete_docs(input);
		
		//删除2012年input文件中得分为0.0的文档
		input="./diversification/input.2012-0.3";
		delete_docs(input);
	}

}
