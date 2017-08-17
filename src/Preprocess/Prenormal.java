package Preprocess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;

/**
 * ±ê×¼»¯summary file.
 * @author Clark
 *
 */
public class Prenormal {
	public void process(String input,String output){
		File file1=null;
		FileReader fileR=null;
		LineNumberReader lnReader=null;
		FileWriter fileW=null;
		String rLine="";
		try{
			rLine = "initial line";
			file1 = new File(input);
			fileR =new FileReader(file1);
			lnReader = new LineNumberReader(fileR);
			fileW = new FileWriter(output,false);
			
			for(int j=0;j<50;j++){ 
				rLine= lnReader.readLine();
			    rLine= lnReader.readLine();
				fileW.write(rLine+'\t');
				for(int i=1;i<=18;i++){
					rLine= lnReader.readLine();
				}
				fileW.write(rLine+'\n');
				for(int i=1;i<=12;i++){
					rLine= lnReader.readLine();
				}
			}
			fileR.close();
			fileW.close();
			System.out.println("file is Prenormaled...");
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try {
				fileR.close();
				fileW.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
	public void showResult(String inputStr) throws IOException{
		FileReader fr=null;
		BufferedReader br=null;
		String info=null;
		fr=new FileReader(inputStr);
		br=new BufferedReader(fr);
		while((info=br.readLine())!=null){
			System.out.println(info);
		}
		br.close();
		fr.close();
	}
	public  static void main(String[] args){
		Prenormal   pre = new Prenormal();
		pre.process("./TREC5/summary.ETHme1", "./TREC5/map.ETHme1");
		try {
			pre.showResult("./TREC5/map.ETHme1");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
