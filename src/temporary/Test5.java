package temporary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Test5 {
	public static int k=1000;
	
	public static double uMean(double[] scores){
		double sum=0;
		for(int i=0;i<k;i++)
			sum=sum+scores[i];
		return sum/k;
	}
	
	public static void compute(double[] scores){
		double u=uMean(scores);
		double temporary=0;
		for(int i=0;i<k;i++){
			temporary=scores[i]*Math.abs(Math.log(scores[i]/u));
			temporary=Math.abs(temporary);
			System.out.println("i="+(i+1)+"\t"+temporary);
		}
		System.out.println("ok");
		System.out.println("u="+u+"\tMath.abs ln(score[0]/u)="+Math.abs(Math.log(scores[0]/u)));
		System.out.println("score[0]="+scores[0]);
	}
	
	
	/**
	 * 根据input.apl10wc,分析SMV特点
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		double[] scores=new double[1000];
		double score=0;
		String tempLine=null;
		int count=0;//记录行数
		String[] term=null;
		
		
		//fileReader=new FileReader("./webTrackDiversity2010/input.cmuWi10D.inversed");
		fileReader=new FileReader("./webTrackDiversity2010/input.cmuComb10.inversed");
		bufferedReader=new BufferedReader(fileReader);
		while((tempLine=bufferedReader.readLine())!=null){
			if(count<1000){
				term=tempLine.split(" |\t");
				score=Double.parseDouble(term[4]);
				scores[count++]=score;
			}else
				break;
		}
		bufferedReader.close();
		//
		compute(scores);
		
		
	}

}
