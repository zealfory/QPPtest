package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;


/**
 * ��webTrack2011,webTackDiversity2010ʹ��
 *
 */
public class SummaryAnalysis {
	public static int round =0;// summary�ļ���query��
	
	/**
	 * ��ȡsummary.std-gd.runId�ļ���err@20ֵ���γ�err_20.runId�ļ�
	 * summary.gdeval.runId
	 * @param input
	 * @param output
	 */
	public static void extractERR_20(String input, String output) {
		FileReader fileReader = null;
		LineNumberReader lineNumberReader = null;
		FileWriter fileWriter = null;
		String tempLine = null;
		String[] terms=null;//���tempLine������
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
			System.err.println("��ȡ���ݳ���!");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.close();
				fileReader.close();
			} catch (IOException e) {
				System.err.println("�ر�IO�ļ�������!");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ��ȡsummary.std-nd.runId�ļ���ERR-IA@20ֵ���γ�err_IA20.runId�ļ�
	 * @param input
	 * @param output
	 */
	public static void extractERR_IA20(String input, String output) {
		FileReader fileReader = null;
		LineNumberReader lineNumberReader = null;
		FileWriter fileWriter = null;
		String tempLine = null;
		String[] terms=null;//���tempLine������
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
			System.err.println("��ȡ���ݳ���!");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.close();
				fileReader.close();
			} catch (IOException e) {
				System.err.println("�ر�IO�ļ�������!");
				e.printStackTrace();
			}
		}
	}
	/**
	 * ��ȡsummary.ndeval.runId�ļ��е�alphaNDCG@20��Ϣ
	 * @param input
	 * @param output
	 */
	public static void extract_alphaNDCG20(String input,String output){
		FileReader fileReader = null;
		BufferedReader buffReader = null;
		FileWriter fileWriter = null;
		BufferedWriter buffWriter=null;
		String tempLine = null;
		String[] terms=null;//���tempLine������
		
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
			System.err.println("��ȡ���ݳ���!");
			e.printStackTrace();
		} finally {
			try {
				buffWriter.close();
				buffReader.close();
			} catch (IOException e) {
				System.err.println("�ر�IO�ļ�������!");
				e.printStackTrace();
			}
		}	
	}
	/**
	 * ��ȡsummary.ndeval.runId�ļ��е�NRBP��Ϣ
	 * @param input
	 * @param output
	 */
	public static void extract_nRBP(String input,String output){
		FileReader fileReader = null;
		BufferedReader buffReader = null;
		FileWriter fileWriter = null;
		BufferedWriter buffWriter=null;
		String tempLine = null;
		String[] terms=null;//���tempLine������
		
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
			System.err.println("��ȡ���ݳ���!");
			e.printStackTrace();
		} finally {
			try {
				buffWriter.close();
				buffReader.close();
			} catch (IOException e) {
				System.err.println("�ر�IO�ļ�������!");
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args){
		
	}

}
