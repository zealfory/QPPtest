package webTrackAdhoc2014;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
/**
 * 和CorrelationCoefficient.Kendall相似。
 * 由于input文件的格式不同,此程序有些修改
 */
public class Kendall {
	private double[] m;
	private double[] n;
	private int k;//存放m,n数组长度的较小值
	
	//By ChenJiawei strict coherence
	public int getCoherence(double[] a,double[] b){
		int count=0;
		double ra=0;
		double rb=0;
		for(int i=0;i<k-1;i++){
			for(int j=i+1;j<k;j++){
				ra=a[i]-a[j];
				rb=b[i]-b[j];
				if(ra>0 && rb>0) count++;
				if(ra<0 && rb<0) count++;
				if(ra==0 && rb==0) count++;
			}
		}
		return count;
	}
	//By ChenJiawei
	public double getCoefficientKendall(){
		double total=k*(k-1)/2;
		double coherence=getCoherence(m,n);
		double coefficientKen=(2*coherence-total)/total;
		return coefficientKen;
	}
	/**
	 * 计算kendall系数
	 */
	public void computeKendall(double[] score1,double[] score2){
		double kendallCoefficient=0;
		m=score1;
		n=score2;
		k=score1.length>score2.length?score2.length:score1.length;
		kendallCoefficient=getCoefficientKendall();
		System.out.println("kendallCoefficient= "+kendallCoefficient);
	}
	/**
	 * 和CorrelationCoefficient.Kendall相似 
	 * 由于input文件的格式不同,此程序有些修改
	 * 
	 * 需根据input文件的格式对此函数进行修改
	 * 根据summary.std-gd.CiirWikiRm获取其中的ndcg@20数据
	 * 
	 * 此处加载的文件为: ./webTrackAdhoc2014/nQCScore.CiirWikiRm  ./webTrackAdhoc2014/summary.std-gd.CiirWikiRm
	 * 此处加载的文件为: ./webTrackAdhoc2014/sDScore.CiirWikiRm  ./webTrackAdhoc2014/summary.std-gd.CiirWikiRm
	 * 此处加载的文件为: ./webTrackAdhoc2014/wIGScore.CiirWikiRm  ./webTrackAdhoc2014/summary.std-gd.CiirWikiRm
	 * 此处加载的文件为: ./webTrackAdhoc2014/sMVScore.CiirWikiRm  ./webTrackAdhoc2014/summary.std-gd.CiirWikiRm
	 * 计算input1和input2的Kendall系数
	 * 
	 * @track为CiirSdm
	 * @track为CiirSub1
	 * @throws IOException 
	 * */
	public void loadScoreAndComputeKendall(String input1,String input2) throws IOException{
		FileReader fileReader=null;
		LineNumberReader lineNumberReader=null;
		double[] score1=null;//存放input1数据
		double[] score2=null;//存放input2数据
		ArrayList<Double> arrayList=new ArrayList<Double>();
		int scoreCount=0;//存放score数量
		double score=0;
		String tempLine=null;
		String[] terms=null;
		
		//读取input1中的score
		fileReader=new FileReader(input1);
		lineNumberReader=new LineNumberReader(fileReader);
		while((tempLine=lineNumberReader.readLine())!=null){
			terms=tempLine.split("\t");
			score=Double.parseDouble(terms[3]);
			arrayList.add(score);
		}
		//把arrayList转化为double数组
		scoreCount=arrayList.size();
		score1=new double[scoreCount];
		for(int i=0;i<scoreCount;i++)
			score1[i]=arrayList.get(i);
		//关闭IO文件
		lineNumberReader.close();
		
		//读取input2中的score,清空arrayList,修改terms[x]的x
		fileReader=new FileReader(input2);
		lineNumberReader=new LineNumberReader(fileReader);
		arrayList.clear();//清空arrayList
		while((tempLine=lineNumberReader.readLine())!=null){
			//若tempLine为文件的第一行数据,读取下一行
			if(tempLine.equalsIgnoreCase("runid,topic,ndcg@20,err@20"))
				tempLine=lineNumberReader.readLine();
			//若tempLine含有amean,为总结行,便结束读取信息
			if(tempLine.contains("amean"))
				break;
			
			terms=tempLine.split(",");
			score=Double.parseDouble(terms[3]);//terms[2]为ndcg@20,terms[3]为err@20
			arrayList.add(score);
		}
		//把arrayList转化为double数组
		scoreCount=arrayList.size();
		score2=new double[scoreCount];
		for(int i=0;i<scoreCount;i++)
			score2[i]=arrayList.get(i);
		//关闭IO文件
		lineNumberReader.close();
		//计算kendall
		computeKendall(score1,score2);
	}
	
	public static void main(String[] args){
		Kendall kendall=new Kendall();
		try {
			//nQC对应的kendall
			kendall.loadScoreAndComputeKendall("./webTrackAdhoc2014/nQCScore.CiirSub1","./webTrackAdhoc2014/summary.std-gd.CiirSub1");
			//sD对应的kendall
			kendall.loadScoreAndComputeKendall("./webTrackAdhoc2014/sDScore.CiirSub1","./webTrackAdhoc2014/summary.std-gd.CiirSub1");
			//wIG对应的kendall
			kendall.loadScoreAndComputeKendall("./webTrackAdhoc2014/wIGScore.CiirSub1","./webTrackAdhoc2014/summary.std-gd.CiirSub1");
			//sMV对应的kendall
			kendall.loadScoreAndComputeKendall("./webTrackAdhoc2014/sMVScore.CiirSub1","./webTrackAdhoc2014/summary.std-gd.CiirSub1");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//根据input.CiirWikiRm.inversed
	//ndcg@20

}
