package predictor;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * changed on 20170313,这里要保证数组m中m[0]的分数>=0,若m[0]==0,SD2值为0,并输出提示信息。<br>
 * @author 1
 * <br>changed on 20170307,暂时性修改。若SD2预测值为NaN,改为0,并输出修改的提示,
 * 在方法getSD2Scores(String input, String output),作了修改<br>
 */
public class SD2 {
	
	private double x=0.5;//x为SD的截断参数
	private HashMap<String,String> queryMap=null;//queryMap中有queryId和queryLen
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public HashMap<String, String> getQueryMap() {
		return queryMap;
	}
	public void setQueryMap(HashMap<String, String> queryMap) {
		this.queryMap = queryMap;
	}
	
	/**
	 * 这里要保证数组m中m[0]的分数>=0,若m[0]==0,dev=0,并输出提示信息。<br>
	 * 计算SD2值
	 * @param m
	 * @return
	 */
	public double computeSD2(double[] m,String queryId){
		if(m[0]<0){System.out.println("m[0]<0,程序已终止.."); System.exit(1);}
		//若m[0]==0,dev=0,并输出提示信息
		if(m[0]==0){System.out.println("\n\n运行SD2算法时,出现m[0]==0");}
		
		//获取qlen信息
		int qlen=0;
		if(queryMap!=null){
			qlen=Integer.parseInt(queryMap.get(queryId));
		}else{
			qlen=1;
		}
		
		double firstScore=m[0];
		double  threshold= firstScore*x;
		int k=m.length;//根据阈值x得到对应的k值,k的初始值置为m.length
		for(int i=0;i<m.length;i++){
			if(m[i]<threshold){
				k=i;
				break;
			}
		}
		//开始计算方差
		//计算数组m前k项的平均值
		double u=0;
		for(int i=0;i<k;i++){
			u=u+m[i];
		}
		u=u/k;
		//计算方差
		double dev=0;
		for(int i=0;i<k;i++){
			dev=dev+(m[i]-u)*(m[i]-u);
		}
		dev=Math.sqrt(dev/(k-1))/Math.sqrt(qlen);
		return dev;
	}
	
	/**
	 * 根据input.runId计算每个query的SD2值, 并将SD2Score存入文件
	 * <br>若sD2Score为NaN,暂时改为0
	 * */
	public void getSD2Scores(String input, String output) {
		FileReader fileReader = null;
		BufferedReader buffReader = null;
		FileWriter fileWriter = null;
		String tempLine = null;
		String[] terms = null;// 分析tempLine
		String preQueryId = null;
		ArrayList<Double> arrayList = new ArrayList<Double>();
		double score = 0;// 临时存放terms[4]的score
		double[] scores = null;// 临时存放一个query对应的score数组
		int scoreCount = 0;// 临时存放score数组的长度
		double SD2Score = 0;// 临时存放一个query的SD2值

		try {
			fileReader = new FileReader(input);
			buffReader = new BufferedReader(fileReader);
			fileWriter = new FileWriter(output, false);
			while ((tempLine = buffReader.readLine()) != null) {
				terms = tempLine.split("\t| ");
				// 起初preQueryId为null
				if (preQueryId == null)
					preQueryId = terms[0];
				// queryId相同,存入score
				if (preQueryId.equalsIgnoreCase(terms[0])) {
					score = Double.parseDouble(terms[4]);
					arrayList.add(score);
				}
				// queryId不同,计算preQueryId的SDScore,写入文件,清空arrayList信息,处理terms信息
				if (!preQueryId.equalsIgnoreCase(terms[0])) {
					// 把arrayList转化为double数组
					scoreCount = arrayList.size();
					scores = new double[scoreCount];
					for (int i = 0; i < scoreCount; i++)
						scores[i] = arrayList.get(i);
					
					// 调用computeSD2()计算此query的SD2值
					SD2Score = computeSD2(scores,preQueryId);
					// 把queryId和SD2Score写入文件
					tempLine="queryId:\t"+preQueryId+"\tSD2:\t"+SD2Score+"\n";
					//若tempLine的sD2Score为NaN,暂时改为0
					fileWriter.write(convertNaN(tempLine));
					// 清空arrayList
					arrayList.clear();
					//开始处理terms信息
					preQueryId = terms[0];
					score = Double.parseDouble(terms[4]);
					arrayList.add(score);
				}
			}
			// 最后queryId对应的scores未处理,计算其SD2Score,并写入文件
			// 把arrayList转化为double数组
			scoreCount = arrayList.size();
			scores = new double[scoreCount];
			for (int i = 0; i < scoreCount; i++)
				scores[i] = arrayList.get(i);
			// 调用computeSD2()计算此query的SD2值
			SD2Score = computeSD2(scores,preQueryId);
			// 把queryId和SD2Score写入文件
			tempLine="queryId:\t"+preQueryId+"\tSD2:\t"+SD2Score+"\n";
			//若tempLine的sD2Score为NaN,暂时改为0
			fileWriter.write(convertNaN(tempLine));
			
		} catch (IOException e) {
			System.err.println("处理数据出错!");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.close();
				buffReader.close();
			} catch (IOException e) {
				System.err.println("关闭IO连接错误!");
				e.printStackTrace();
			}
		}
	}
	/**
	 * 若sD2为NaN,改为0
	 * @return
	 */
	public String convertNaN(String tempLine){
		String[] terms=null;
		String newTempLine=null;
		//需去掉\n,使用trim()方法
		terms=tempLine.trim().split(" |\t");
		//若sD2为NaN,改为0
		if(terms[3].equals("NaN")){
			terms[3]="0";
			newTempLine=terms[0]+"\t"+terms[1]+"\t"+terms[2]+"\t"+terms[3]+"\n";
			System.out.println("\n\n此条记录"+tempLine+" 中存在NaN值,已被改为0,并存入sD2Score.runId文件中。");
			return newTempLine;
		}else{
			return tempLine;
		}
	}

	public static void main(String[] args) {
		/*SD2 sD2 = new SD2();
		sD2.x=0.5;
		
		System.out.println("根据input.runId计算每个query的SD2值,并将SD2Score存入文件,已完成..");
		*/
		
	}
}
