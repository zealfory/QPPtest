package diversification;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * P(q_i|q)subTopic的相对重要性计算采用了最简单的Pu公式：即1/subtopic个数
 * 其他方式待考虑
 * @author hcl
 *
 */
public class xQuAD {
	private int qid;
	private int  tao;//重排列表长度
	private double  lanta;//系数
	private int subQueryNum=0;//subtopic个数
	private ArrayList<String> divResList; //最终返回结果		
	private HashMap<String,Double> initScore=new HashMap<String,Double>();//初始结果（id,score）
	private ArrayList<HashMap<String,Double>> subScore=new ArrayList<HashMap<String,Double>>();//subtopic查询结果[subq1 d p,subq2 d p,...]

	public ArrayList<String> getDivResList() {
		return divResList;
	}

	public xQuAD(int qid,int tao, double lanta, int subQueryNum, HashMap<String, Double> initScore,
			ArrayList<HashMap<String, Double>> subScore) {
		super();
		this.qid=qid;
		this.tao=tao;
		this.lanta = lanta;
		this.subQueryNum = subQueryNum;
		this.initScore = initScore;
		this.subScore = subScore;
	}
	public xQuAD(int qid,int tao, double lanta) {
		super();
		this.qid=qid;
		this.tao=tao;
		this.lanta = lanta;

	}






	public int getTao() {
		return tao;
	}

	public void setTao(int tao) {
		this.tao=tao>initScore.size()?initScore.size():tao;
	}

	public int getSubQueryNum() {
		return subQueryNum;
	}

	public void setSubQueryNum(int subQueryNum) {
		this.subQueryNum = subQueryNum;
	}

	public int getQid() {
		return qid;
	}

	public void setQid(int qid) {
		this.qid = qid;
	}

	/**
	 * 文档d在检索到的结果中出现的概率
	 * @return
	 */
	private double p_Query_DOC(String docID,int i){
		HashMap<String,Double> score;
		if(i==-1){
			score=this.initScore;
		}else{
			score=this.subScore.get(i);
		}
		return score.get(docID)==null?0:score.get(docID);	

	}

	/**
	 * 计算子query的重要性
	 * @param i 
	 * @param resList
	 * @param subResList
	 * @param subResNum
	 * @param i  k当前subtopic id
	 * @return
	 */
	private double queryImportance(int type, int i){
		if(type==0){//0表示使用Pu公式计算subtopic相对重要性
			return 1.0/subQueryNum;
		}
		if(type==1){//1表示使用ic公式计算subtopic相对重要性??????
			//			double sum=0f;
			//			double qi_IC_Value=I_C(resList,subResList,subResNum,i);
			//			for(int j=0;j<subQueryNum;j++){
			//				sum+=I_C(resList,subResList,subResNum,j);
			//			}
			//			return qi_IC_Value/sum;
		}
		return 0;
	}

	/**
	 * 计算 ic（q_i|q)
	 * @param resList 初始query检索结果
	 * @param subResList 所有子query的检索结果
	 * @param subResNum  每个子query检索的结果数量
	 * @param i  第i+1个query
	 * @return
	 */
	private double I_C(ArrayList<String> resList,ArrayList<String[]> subResList,int[] subResNum,int i){
		String[] subRes=subResList.get(i);
		int subQueryRetDocNum=subResNum[i];
		int maxSubQueryRetDocNum=maxRetDocNum(subResNum);
		ArrayList<String> inResList=new ArrayList<String>();
		int subQueryRetDocInTopR=subQueryRetDocInTopInitRes(resList,subRes,inResList);

		//算最后一项，即出现在原始query结果的前tao个结果的文档在子query结果的处理
		int inResSize=inResList.size();
		int sum=0;
		for(int j=0;j<inResSize;j++){
			String docID=inResList.get(j);
			if(p_Query_DOC(docID,i)>0){
				sum+=(this.tao-posDocInR(docID, resList));
			}
		}
		return ((double)subQueryRetDocNum/maxSubQueryRetDocNum)*((double)1/subQueryRetDocInTopR)*sum;
	}

	/**
	 * 原始query检索的结果前 tao 个中有子query检索到的文档数量
	 * @param resList
	 * @param subResList
	 * @param inResList 子query中在初始query前tao的文档列表
	 * @return
	 */
	private int subQueryRetDocInTopInitRes(ArrayList<String> resList,String[] subRes,ArrayList<String> inResList){
		ArrayList<String> topList=new ArrayList<String>();
		int tempTao=tao<=resList.size()?tao:resList.size();
		for(int i=0;i<tempTao;i++){
			topList.add(resList.get(i));
		}
		int count=0;
		for(String res:subRes){
			if(topList.contains(res)){
				count++;
				inResList.add(res);
			}
		}
		return count;
	}

	/**
	 * 求所有子query中检索到的结果最多的文档数
	 * @param subResNum
	 * @return
	 */
	private int maxRetDocNum(int[] subResNum){
		int maxDocNum=0;
		for(int data:subResNum){
			if(maxDocNum<data){
				maxDocNum=data;
			}
		}
		return maxDocNum;
	}

	/**
	 * 计算docID在初始查询结果中的位置   j(d,q)
	 * @param docID
	 * @param resList
	 * @return
	 */
	private int posDocInR(String docID,ArrayList<String> resList){
		int size=resList.size();
		int pos=0;
		for(int i=0;i<size;i++){
			if(docID.equals(resList.get(i))){
				pos=i+1;
			}
		}
		return pos;
	}

	/**
	 * 计算 P((not)S|q_i)
	 * 即S中所有文档不满意的概率
	 */
	private double rankingDivres_Not_Query(int i){
		if(divResList.size()==0){
			return 1;
		}
		String firstdoc=divResList.get(0);
		double res=1-this.p_Query_DOC(firstdoc,i);
		int n=1;
		while(n<divResList.size()){
			res*=(1-this.p_Query_DOC(divResList.get(n),i));
			n++;
		}
		return res;
	}
	/**
	 * return index
	 * 
	 * @param results
	 * @return
	 */
	private int chooseMaxResult(double[] results){
		double max=0;
		int size=results.length;
		int index=0;
		for(int i=0;i<size;i++){
			if(max<results[i]){
				index=i;
				max=results[i];
			}
		}
		return index;
	}


	/**
	 * 从此处开始运行，为每个query的原始结果R，生成diversity结果S
	 * @param queryID  当前处理的queryID
	 * @param initRes  此query所对应的初始检索结果（仅包含docid）
	 * @param tao  可调参数 必须小于R中文档个数
	 * @param lanta  可调参数 0-1
	 * @param subQueryNum  子query个数
	 * @param subScore 所有subtopic检索结果
	 */
	public double[] diversificationRes(ArrayList<String> initRes){
		divResList=new ArrayList<String>();   //初始化最终保存结果
		//迭代tao次
		double finalResult;
		double[] discount=new double[this.subQueryNum];
		for(int i=0;i<discount.length;i++){
			discount[i]=1;
		}
		double[] docscore=new double[this.tao];
		int si=0;


		while(divResList.size() < this.tao) {
			int size=initRes.size();//R\S

			double res[]=new double[size];//属于R/S文档的计算得分
			for (int i=0;i<size;i++) {   //从当前的R中选择得分最高的文档，放到S中
				double part_1 = (1 - this.lanta)* this.p_Query_DOC(initRes.get(i), -1);//-1表示在原始结果中查找
				double part_2 = 0.0;
				for (int k = 0; k < this.subQueryNum; k++) {
					//System.out.print(initRes.get(i)+"\t"+this.p_Query_DOC(initRes.get(i), k)+"\t子主题得分\n");
					part_2 += this.queryImportance(0,k)
							* this.p_Query_DOC(initRes.get(i), k)//k表示在subtopic k中查找
							* discount[k];
					//* this.rankingDivres_Not_Query(k);//S中所有文档在subtopic k上的分值总量
				}
				part_2 *= this.lanta;
				finalResult = part_1 + part_2;
				res[i]=finalResult;
			}
			int maxIndex=this.chooseMaxResult(res);    //每一次迭代结束，选择结果最大的文档
			docscore[si]=res[maxIndex];
			si++;
			divResList.add(initRes.get(maxIndex));     //把选中的文档加入S
			for(int i=0;i<discount.length;i++){
				discount[i]*=(1-this.p_Query_DOC(initRes.get(maxIndex), i));
				//	discount[i]/=Math.pow(2,this.p_Query_DOC(initRes.get(maxIndex), i));
			}
			initRes.remove(maxIndex);                 //把选中的文档从R中移除
		}
		return docscore;
	}

	/**
	 * return subScore.size()
	 * @param initRes  此query所对应的初始检索结果（仅包含docid）
	 */
	public int getInput(ArrayList<String> initRes,String InitResultPath,String subResultPath) throws Exception
	{

		BufferedReader initbf=new BufferedReader(new FileReader(InitResultPath));
		String templine="";
		while((templine=initbf.readLine())!=null){
			String[] item=templine.split(" |\t");
			int queryid=Integer.parseInt(item[0]);
			//找到了该queryid
			if(queryid==qid){
				String docid=item[2];
				double s=Double.parseDouble(item[4]);
				initRes.add(docid);
				initScore.put(docid, s);

			}	
			if(queryid!=qid&& initRes.size()>0 ){//
				break;
			}
		}
		//System.out.println("获取qid下的初始结果结束");


		BufferedReader subresbr=new BufferedReader(new FileReader(subResultPath));
		String tempLine="";
		int temp_subtopic=-1;
		HashMap<String, Double> hmap=null;
		while((tempLine=subresbr.readLine())!=null){
			String[] item=tempLine.split(" |\t");
			int id=Integer.parseInt(item[0]);
			int queryid=id/100;
			if(queryid==qid){
				int subid=id%100;
				String docid=item[2];
				double s=Double.parseDouble(item[4]);
				if(subid!=temp_subtopic){
					if(hmap!=null){
						subScore.add(hmap);
					}
					hmap=new HashMap<String, Double>();	
					hmap.put(docid, s);
					temp_subtopic=subid;
				}else{
					hmap.put(docid, s);
				}
			}
			if(queryid!=qid && subScore.size()>0){//subResultPath文件中同一qid下的subids排在一块
				break;
			}
		}
		if(hmap!=null){
			subScore.add(hmap);
		}
		initbf.close();
		subresbr.close();
		//System.out.println(qid+"   "+subScore.size());
		return subScore.size();
	}
	/**
	 * return subScore.size()
	 * @param subResultPath
	 * @return
	 * @throws Exception
	 */
	public int getSubResult(String subResultPath)throws Exception
	{
		BufferedReader subresbr=new BufferedReader(new FileReader(subResultPath));
		String tempLine="";
		int temp_subtopic=-1;
		HashMap<String, Double> hmap=null;
		while((tempLine=subresbr.readLine())!=null){
			String[] item=tempLine.split(" |\t");
			int id=Integer.parseInt(item[0]);
			int queryid=id/100;
			if(queryid==qid){
				int subid=id%100;
				String docid=item[2];
				double s=Double.parseDouble(item[4]);
				if(subid!=temp_subtopic){
					if(hmap!=null){
						subScore.add(hmap);
					}
					hmap=new HashMap<String, Double>();	
					hmap.put(docid, s);
					temp_subtopic=subid;
				}else{
					hmap.put(docid, s);
				}
			}
			if(queryid!=qid && subScore.size()>0){
				break;
			}
		}
		if(hmap!=null){
			subScore.add(hmap);
		}
		subresbr.close();
		return subScore.size();
	}
	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main_2(String[] args) throws Exception{
		int tao=100;
		double lanta=0.5;
		String initResultPath=null;
		String subResultPath=null;
		FileWriter fileWriter=null;
		BufferedWriter buffWriter=null;

		initResultPath="./webTrackDiversity2010/input.indri2010_60addRank";
		subResultPath="./webTrackDiversity2010/input.indri2010subquery_60addRank";
		fileWriter=new FileWriter("./webTrackDiversity2010/input.indri2010_60addRank_xQuAD");
		buffWriter=new BufferedWriter(fileWriter);
		//qid的取值范围需根据年份调整
		for(int qid=51;qid<=100;qid++){
			//
			if(qid==95||qid==100) continue;

			ArrayList<String> initRes = new ArrayList<String>();//原始结果文档id集合
			xQuAD d=new xQuAD(qid,tao,lanta);
			int subQueryNum = d.getInput(initRes,initResultPath,subResultPath);

			//System.out.println("initRes.size()="+initRes.size()+"\tsubQueryNum="+subQueryNum);
			d.setSubQueryNum(subQueryNum);
			d.setTao(tao);//tao=tao>initScore.size()?initScore.size():tao;
			double[] docscore=d.diversificationRes(initRes);
			//
			for (int i=0;i<d.getDivResList().size();i++){
				String RankResult=d.getQid()+"\tQ0\t"+d.getDivResList().get(i)+"\t"+(i+1)+"\t"+docscore[i]+"\tteam\n";
				buffWriter.write(RankResult);
			}
			buffWriter.flush();
		}
		buffWriter.close();
		System.out.println("xQuAD已完成..");
	}
	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception{
		int tao=100;
		double lanta=0.5;
		String initResultPath=null;
		String subResultPath=null;
		FileWriter fileWriter=null;
		BufferedWriter buffWriter=null;

		initResultPath="./webTrack2011/input.indri2011_60addRank";
		subResultPath="./webTrack2011/input.indri2011subquery_60addRank";
		fileWriter=new FileWriter("./webTrack2011/input.indri2011_60addRank_xQuAD");
		buffWriter=new BufferedWriter(fileWriter);
		//qid的取值范围需根据年份调整
		for(int qid=101;qid<=150;qid++){
			//
			if(qid==95||qid==100) continue;

			ArrayList<String> initRes = new ArrayList<String>();//原始结果文档id集合
			xQuAD d=new xQuAD(qid,tao,lanta);
			int subQueryNum = d.getInput(initRes,initResultPath,subResultPath);

			//System.out.println("initRes.size()="+initRes.size()+"\tsubQueryNum="+subQueryNum);
			d.setSubQueryNum(subQueryNum);
			d.setTao(tao);//tao=tao>initScore.size()?initScore.size():tao;
			double[] docscore=d.diversificationRes(initRes);
			//
			for (int i=0;i<d.getDivResList().size();i++){
				String RankResult=d.getQid()+"\tQ0\t"+d.getDivResList().get(i)+"\t"+(i+1)+"\t"+docscore[i]+"\tteam\n";
				buffWriter.write(RankResult);
			}
			buffWriter.flush();
		}
		buffWriter.close();
		System.out.println("xQuAD已完成..");
	}
	/**
	 * 对输入的文件进行多样化分析,产生多样化文件
	 * @throws Exception 
	 */
	public static void generate_xQuADFile(String initResultPath,String subResultPath,String output,int qid_first) throws Exception{
		int tao=100;
		double lanta=0;
		FileWriter fileWriter=null;
		BufferedWriter buffWriter=null;
		//lanta为0.0,0.1,0.2,...,1.0
		for(int time=0;time<11;time++){
			fileWriter=new FileWriter(output+lanta);
			buffWriter=new BufferedWriter(fileWriter);
			//qid的取值范围需根据年份调整
			for(int qid=qid_first;qid<=(qid_first+49);qid++){
				//
				if(qid==95||qid==100) continue;

				ArrayList<String> initRes = new ArrayList<String>();//原始结果文档id集合
				xQuAD d=new xQuAD(qid,tao,lanta);
				int subQueryNum = d.getInput(initRes,initResultPath,subResultPath);

				//System.out.println("initRes.size()="+initRes.size()+"\tsubQueryNum="+subQueryNum);
				d.setSubQueryNum(subQueryNum);
				d.setTao(tao);//tao=tao>initScore.size()?initScore.size():tao;
				double[] docscore=d.diversificationRes(initRes);
				//
				for (int i=0;i<d.getDivResList().size();i++){
					String RankResult=d.getQid()+"\tQ0\t"+d.getDivResList().get(i)+"\t"+(i+1)+"\t"+docscore[i]+"\tteam\n";
					buffWriter.write(RankResult);
				}
				buffWriter.flush();
			}
			buffWriter.close();
			System.out.println(output+lanta+"文件已产生..");
			//tradeoff增加0.1
			lanta=add(lanta,0.1);
		}
		System.out.println("xQuAD已完成..");
	}
	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1 被加数
	 * @param v2 加数
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	public static void main_1(String[] args)throws Exception{
		int tao=150;
		double lanta=0.1;
		int qid=1;
		//
		for(int y=2011;y<2012;y++)
		{
			for(int lan=1;lan<10;lan++)
			{
				DecimalFormat df = new DecimalFormat(".00");//?
				lanta= Double.parseDouble(df.format(lan*0.1));
				for(int num=1;num<2;num++)
				{
					//2010ctt.initial   2009160120xcl.initial  20110119xml.initial 
					String initResultPath="G:/Fusiondata/Evalresult/2011/fusionresult0413/2011LCLR0414";
					String SubResultPath="G:/Fusiondata/fusionresult/0302initial/20110119xml.initial";
					BufferedWriter writer_result=new BufferedWriter(new FileWriter("G:/Fusiondata/Evalresult/2011/"+y+" "+lanta+"XQuadLCLR0414.txt"));
					for(qid=((y-2009)*50+1);qid<=((y-2009)*50+50);qid++)
					{

						if (qid == 95 || qid == 100) {
							continue;
						}

						ArrayList<String> initRes = new ArrayList<String>();//原始结果文档id集合			
						xQuAD d=new xQuAD(qid,tao,lanta);
						int subQueryNum = d.getInput(initRes,initResultPath,SubResultPath);

						System.out.println("initRes.size()="+initRes.size()+"\tsubQueryNum="+subQueryNum);

						d.setSubQueryNum(subQueryNum);
						d.setTao(tao);//tao=tao>initScore.size()?initScore.size():tao;

						double[] docscore=d.diversificationRes(initRes);

						for (int i=0;i<d.getDivResList().size();i++){
							String RankResult=d.getQid()+"\tQ0\t"+d.getDivResList().get(i)+"\t"+(i+1)+"\t"+docscore[i]+"\tteam\n";
							System.out.println(RankResult+"\tlanta="+lanta+"\n");
							writer_result.write(RankResult);
						}
						writer_result.flush();
					}

					writer_result.close();
				}
			}
		}
	}
}
