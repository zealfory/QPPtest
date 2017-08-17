package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;


/**
 * 供webTrack2011,webTackDiversity2010使用
 *
 */
public class SummaryAnalysis {
	public static int round =0;// summary文件中query数
	
	/**
	 * 获取summary.std-gd.runId文件的err@20值，形成err_20.runId文件
	 * summary.gdeval.runId
	 * @param input
	 * @param output
	 */
	public static void extractERR_20(String input, String output) {
		FileReader fileReader = null;
		LineNumberReader lineNumberReader = null;
		FileWriter fileWriter = null;
		String tempLine = null;
		String[] terms=null;//存放tempLine的数据
		try {
			fileReader = new FileReader(input);
			lineNumberReader = new LineNumberReader(fileReader);
			fileWriter = new FileWriter(output, false);
			lineNumberReader.readLine();
			for(int i=0;i<round;i++){
				tempLine=lineNumberReader.readLine();
				terms=tempLine.trim().split(",");
				fileWriter.write("Queryid\t(Num):\t"+terms[1].trim()+"\t"+terms[0].trim()+"\t"+terms[3].trim()+"\n");
			}
			System.out.println("ERR@20 data have been extracted..");
		} catch (IOException e) {
			System.err.println("读取数据出错!");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.close();
				fileReader.close();
			} catch (IOException e) {
				System.err.println("关闭IO文件流出错!");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 获取summary.std-nd.runId文件的ERR-IA@20值，形成err_IA20.runId文件
	 * @param input
	 * @param output
	 */
	public static void extractERR_IA20(String input, String output) {
		FileReader fileReader = null;
		LineNumberReader lineNumberReader = null;
		FileWriter fileWriter = null;
		String tempLine = null;
		String[] terms=null;//存放tempLine的数据
		try {
			fileReader = new FileReader(input);
			lineNumberReader = new LineNumberReader(fileReader);
			fileWriter = new FileWriter(output, false);
			lineNumberReader.readLine();
			for(int i=0;i<round;i++){
				tempLine=lineNumberReader.readLine();
				terms=tempLine.trim().split(",");
				fileWriter.write("Queryid\t(Num):\t"+terms[1].trim()+"\t"+terms[0].trim()+"\t"+terms[4].trim()+"\n");
			}
			System.out.println("ERR-IA@20 data have been extracted..");
		} catch (IOException e) {
			System.err.println("读取数据出错!");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.close();
				fileReader.close();
			} catch (IOException e) {
				System.err.println("关闭IO文件流出错!");
				e.printStackTrace();
			}
		}
	}
	/**
	 * 获取summary.ndeval.runId文件中的alphaNDCG@20信息
	 * @param input
	 * @param output
	 */
	public static void extract_alphaNDCG20(String input,String output){
		FileReader fileReader = null;
		BufferedReader buffReader = null;
		FileWriter fileWriter = null;
		BufferedWriter buffWriter=null;
		String tempLine = null;
		String[] terms=null;//存放tempLine的数据
		
		try {
			fileReader = new FileReader(input);
			buffReader = new BufferedReader(fileReader);
			fileWriter = new FileWriter(output, false);
			buffWriter=new BufferedWriter(fileWriter);
			
			buffReader.readLine();
			for(int i=0;i<round;i++){
				tempLine=buffReader.readLine();
				terms=tempLine.trim().split(",");
				buffWriter.write("Queryid\t(Num):\t"+terms[1].trim()+"\t"+terms[0].trim()+"\t"+terms[13].trim()+"\n");
			}
			System.out.println("alpha-NDCG@20 data have been extracted..");
		} catch (IOException e) {
			System.err.println("读取数据出错!");
			e.printStackTrace();
		} finally {
			try {
				buffWriter.close();
				buffReader.close();
			} catch (IOException e) {
				System.err.println("关闭IO文件流出错!");
				e.printStackTrace();
			}
		}	
	}
	/**
	 * 获取summary.ndeval.runId文件中的NRBP信息
	 * @param input
	 * @param output
	 */
	public static void extract_nRBP(String input,String output){
		FileReader fileReader = null;
		BufferedReader buffReader = null;
		FileWriter fileWriter = null;
		BufferedWriter buffWriter=null;
		String tempLine = null;
		String[] terms=null;//存放tempLine的数据
		
		try {
			fileReader = new FileReader(input);
			buffReader = new BufferedReader(fileReader);
			fileWriter = new FileWriter(output, false);
			buffWriter=new BufferedWriter(fileWriter);
			
			buffReader.readLine();
			for(int i=0;i<round;i++){
				tempLine=buffReader.readLine();
				terms=tempLine.trim().split(",");
				buffWriter.write("Queryid\t(Num):\t"+terms[1].trim()+"\t"+terms[0].trim()+"\t"+terms[14].trim()+"\n");
			}
			System.out.println("NRBP data have been extracted..");
		} catch (IOException e) {
			System.err.println("读取数据出错!");
			e.printStackTrace();
		} finally {
			try {
				buffWriter.close();
				buffReader.close();
			} catch (IOException e) {
				System.err.println("关闭IO文件流出错!");
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args){
		
	}

}
