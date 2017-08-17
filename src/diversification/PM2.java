package diversification;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * 在20170321,对PM2做了修改,初始的votes[i]为1.0/n_subquery<br>
 * 我对PM2做了修改,没有使用jugefile文件提供的votes值,而是采用了统一值 1。<br>
 * @author 1
 *
 */
public class PM2 {
	private int qid;
	public int tao;// S集合上限，即重排结果大小
	public double lanta;// 系数
	public  int subQueryNum = 0;// subtopic个数
	private  HashMap<String, Double> initScore = new HashMap<String, Double>();// 初始结果（id,score）
	private  ArrayList<HashMap<String, Double>> subScore = new ArrayList<HashMap<String, Double>>();//subtopic查询结果
	//
	private ArrayList<String> chooseSubQuery=new ArrayList<String>();
	private ArrayList<String> divResList; // 最终返回结果

	public PM2(int qid, double lanta) {
		super();
		this.qid = qid;
		this.lanta = lanta;
	}
	public int getQid() {
		return qid;
	}
	public void setQid(int qid) {
		this.qid = qid;
	}
	public int getTao() {
		return tao;
	}
	public void setTao(int tao) {
		this.tao = tao;
	}
	public double getLanta() {
		return lanta;
	}
	public void setLanta(double lanta) {
		this.lanta = lanta;
	}
	public int getSubQueryNum() {
		return subQueryNum;
	}
	public void setSubQueryNum(int subQueryNum) {
		this.subQueryNum = subQueryNum;
	}
	public HashMap<String, Double> getInitScore() {
		return initScore;
	}
	public void setInitScore(HashMap<String, Double> initScore) {
		this.initScore = initScore;
	}
	public ArrayList<HashMap<String, Double>> getSubScore() {
		return subScore;
	}
	public void setSubScore(ArrayList<HashMap<String, Double>> subScore) {
		this.subScore = subScore;
	}
	public void setDivResList(ArrayList<String> divResList) {
		this.divResList = divResList;
	}
	public ArrayList<String> getDivResList() {
		return divResList;
	}

	/**
	 * 文档d在检索到的结果中出现的概率
	 * 
	 * @return
	 */
	public double p_Query_DOC(String docID, int i) {
		HashMap<String, Double> score;
		score = this.subScore.get(i);
		return score.get(docID) == null ? 0 : score.get(docID);
	}

	private int getMaxIndex(double[] array) {
		int index = 0;
		double max = 0;
		for (int i = 0; i < array.length; i++) {
			if (max < array[i]) {
				max = array[i];
				index = i;
			}
		}
		return index;
	}

	/**
	 * 获取重排文档PM_2
	 * 
	 * @param initRes
	 */
	public double[] diversification_PM2(ArrayList<String> initRes){
		divResList = new ArrayList<String>(); // 初始化最终保存结果
		// 迭代tao次
		double[] seats = new double[this.subQueryNum];// 每个subtopic所占席位
		double[] docscore = new double[this.tao];
		int si = 0;
		//votes为统一值,1.0/subqueryNum
		double votes=1.0/this.subQueryNum;

		while (divResList.size() < this.tao) {
			// 确定当选党派subtopic
			double[] quotient = new double[this.subQueryNum];//每个subtopic当前所拥有的议席配额
			for (int i = 0; i < this.subQueryNum; i++) {
				//votes[i]=1.0;
				quotient[i] = votes / (2 * seats[i] + 1);
			}

			int maxqt_i = getMaxIndex(quotient);

			// 确定该党派中最合适的议员doc
			int size = initRes.size();// R\S
			double res[] = new double[size];// 属于R/S文档的计算得分
			for (int j = 0; j < size; j++) { // 从当前的R中选择得分最高的文档，放到S中
				double part_1 = lanta * quotient[maxqt_i]* p_Query_DOC(initRes.get(j), maxqt_i);
				double part_2 = 0;
				for (int i = 0; i < this.subQueryNum; i++) {
					if (i != maxqt_i) {
						part_2 += (quotient[i] * p_Query_DOC(initRes.get(j), i));
					}
				}
				part_2 *= (1.0-lanta);
				res[j] = part_1 + part_2;
			}
			int maxdoc_i = getMaxIndex(res);
			docscore[si] = res[maxdoc_i];
			si++;
			String select_doc = initRes.get(maxdoc_i);
			divResList.add(select_doc);
			initRes.remove(maxdoc_i);

			double ts = 0;
			for (int j = 0; j < this.subQueryNum; j++) {
				ts += p_Query_DOC(select_doc, j);
			}
			if (ts > 0) {
				for (int i = 0; i < this.subQueryNum; i++) {
					seats[i] += p_Query_DOC(select_doc, i) / ts;
				}
			}
		}
		return docscore;
	}
	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main_1(String[] args) throws Exception {

	}
	/**
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception{
		FileWriter fileWriter=null;
		int tao=100;
		String resultpath="./diversification/input.indri2009mainquery_60addRank";
		String subpath="./diversification/input.indri2009subquery_60addRank";
		String output="./diversification/input.indri2009pm2_60addRank";
		double lanta=0.5;
		fileWriter=new FileWriter(output);

		for(int qid=1;qid<=50;qid++){
			if (qid == 95 || qid == 100) continue;

			ArrayList<String> initRes = new ArrayList<String>();
			PM2 d = new PM2(qid, lanta);
			int subQueryNum = d.getInput(initRes, resultpath, subpath);
			//System.out.println(subQueryNum);

			d.setTao(tao > initRes.size() ? initRes.size() : tao);
			d.setSubQueryNum(subQueryNum);

			double[] docscore = d.diversification_PM2(initRes);
			//输出qid对应的pm2重排结果
			String tempLine=null;
			for (int i = 0; i < d.getDivResList().size(); i++) {
				tempLine = d.getQid() + "\tQ0\t"+ d.getDivResList().get(i) + "\t" + (i+1) + "\t"+ docscore[i] + "\tteam\n";
				fileWriter.write(tempLine);
			}
		}
		fileWriter.close();
		System.out.println("使用pm2方法对input文件进行重排,已完成..");
	}
	/**
	 * 对输入的文件进行多样化分析,产生多样化文件
	 * @throws Exception 
	 */
	public static void generate_pm2File(String resultpath,String subpath,String output,int qid_first) throws Exception{
		FileWriter fileWriter=null;
		BufferedWriter buffWriter=null;
		int tao=100;
		double lanta=0;

		for(int time=0;time<11;time++){
			fileWriter=new FileWriter(output+lanta);
			buffWriter=new BufferedWriter(fileWriter);

			for(int qid=qid_first;qid<=(qid_first+49);qid++){
				if (qid == 95 || qid == 100) continue;

				ArrayList<String> initRes = new ArrayList<String>();
				PM2 d = new PM2(qid, lanta);
				int subQueryNum = d.getInput(initRes, resultpath, subpath);
				//System.out.println(subQueryNum);
				d.setTao(tao > initRes.size() ? initRes.size() : tao);
				d.setSubQueryNum(subQueryNum);

				double[] docscore = d.diversification_PM2(initRes);
				//输出qid对应的pm2重排结果
				String tempLine=null;
				for (int i = 0; i < d.getDivResList().size(); i++) {
					tempLine = d.getQid() + "\tQ0\t"+ d.getDivResList().get(i) + "\t" + (i+1) + "\t"+ docscore[i] + "\tteam\n";
					buffWriter.write(tempLine);
				}
				buffWriter.flush();
			}
			buffWriter.close();
			System.out.println(output+lanta+"文件已产生..");
			//lanta增加0.1
			lanta=utils.Arith.add(lanta, 0.1);
		}
		System.out.println("产生pm2多样化结果,已完成..");
	}
	/**
	 * 
	 * @param initRes
	 * @param resultpath
	 * @param subpath
	 * @return
	 * @throws Exception
	 */
	private int getInput(ArrayList<String> initRes, String resultpath,String subpath) throws Exception {
		subScore = new ArrayList<HashMap<String, Double>>();
		BufferedReader reader_result = new BufferedReader(new FileReader(resultpath));
		String templine;
		while ((templine = reader_result.readLine()) != null) {
			String[] item = templine.split(" |\t");
			int queryid = Integer.parseInt(item[0]);
			if (queryid == qid) {
				String docid = item[2];
				initRes.add(docid);
				initScore.put(docid, Double.parseDouble(item[4]));
			}
			if (queryid != qid && initRes.size() > 0) {
				break;
			}
		}
		reader_result.close();
		//存储subpath文件的信息
		BufferedReader reader_subresult = new BufferedReader(new FileReader(subpath));
		int temp_subtopic = -1;
		HashMap<String, Double> hmap = null;
		while ((templine = reader_subresult.readLine()) != null) {
			String[] item = templine.split(" |\t");
			int id = Integer.parseInt(item[0]);
			int queryid = id / 100;
			if (queryid == qid) {
				int subid = id % 100;
				String docid = item[2];
				double s = Double.parseDouble(item[4]);
				if (subid != temp_subtopic) {
					chooseSubQuery.add(subid+"");

					if (hmap != null) { subScore.add(hmap);}

					hmap = new HashMap<String, Double>();
					hmap.put(docid, s);
					temp_subtopic = subid;
				} else {
					hmap.put(docid, s);
				}
			}
			if (queryid != qid && subScore.size() > 0) {
				break;
			}
		}
		//最后的hmap还未存入subScore中
		if (hmap != null) {
			subScore.add(hmap);
		}
		reader_subresult.close();
		return subScore.size();
	}

	private  double[] getQueryVotes(int[] is) {
		double[] subvotes = new double[subQueryNum];
		int subQueryCount = 0;
		// System.out.println(subQueryNum);
		for (int i = 0; i < is.length; i++) {
			if (is[i] > 0 ) {
				subvotes[subQueryCount] = is[i];
				subQueryCount++;

			}
		}
		for(int i=0;i<subvotes.length;i++)
		{
			System.out.println(subvotes[i]);
		}
		return subvotes;
	}

	private static HashMap<String,int[]> getVotes(String judgepath)throws Exception {
		HashMap<String, int[]> votes = new HashMap<String, int[]>();
		BufferedReader r = new BufferedReader(new FileReader(judgepath));
		String templine;
		while ((templine = r.readLine()) != null) {
			String[] items = templine.split(" |\t");
			String qid = items[0];
			int[] subid = new int[11];
			int[] v = new int[11];
			for (int k = 1; k < 12; k++) {
				try {
					v[k - 1] = Integer.parseInt(items[k].split(":")[1]);
				} catch (Exception e) {

					System.out.println(qid);
				}

				subid[k - 1] = Integer.parseInt(items[k].split(":")[0]);
			}
			votes.put(qid, v);
		}
		return votes;
	}




	private static HashMap<String, int[]> getVotes_xu(String judgepath,String year)throws Exception {
		//System.out.println("调用了许");
		HashMap<String, int[]> votes = new HashMap<String, int[]>();
		BufferedReader r = new BufferedReader(new FileReader(judgepath));
		String qid=String.valueOf(((Integer.parseInt(year)-2009)*50+1));
		int y=(Integer.parseInt(year)-2009)*50+1;
		String templine;

		ArrayList<String> tempal=new ArrayList<String>();
		while ((templine = r.readLine()) != null) {
			String[] items = templine.split(" |\t");
			if(qid.equals(items[0]))
			{
				tempal.add(items[2]);

			}else{
				int [] v=new int[tempal.size()];
				for(int m=0;m<tempal.size();m++)
				{

					v[m]=Integer.parseInt(tempal.get(m));
					System.out.println(qid+v[m]);

				}
				tempal=new ArrayList<String>();
				votes.put(qid, v);
				tempal.add(items[2]);
				y++;
				qid=String.valueOf(y);
			}

		}

		int [] v=new int[tempal.size()];
		for(int m=0;m<tempal.size();m++)
		{

			v[m]=Integer.parseInt(tempal.get(m));
			System.out.println(qid+v[m]);

		}
		votes.put(qid, v);

		r.close();
		return votes;
	}

}
