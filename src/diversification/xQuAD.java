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
 * P(q_i|q)subTopic�������Ҫ�Լ����������򵥵�Pu��ʽ����1/subtopic����
 * ������ʽ������
 * @author hcl
 *
 */
public class xQuAD {
	private int qid;
	private int  tao;//�����б���
	private double  lanta;//ϵ��
	private int subQueryNum=0;//subtopic����
	private ArrayList<String> divResList; //���շ��ؽ��		
	private HashMap<String,Double> initScore=new HashMap<String,Double>();//��ʼ�����id,score��
	private ArrayList<HashMap<String,Double>> subScore=new ArrayList<HashMap<String,Double>>();//subtopic��ѯ���[subq1 d p,subq2 d p,...]

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
	 * �ĵ�d�ڼ������Ľ���г��ֵĸ���
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
	 * ������query����Ҫ��
	 * @param i 
	 * @param resList
	 * @param subResList
	 * @param subResNum
	 * @param i  k��ǰsubtopic id
	 * @return
	 */
	private double queryImportance(int type, int i){
		if(type==0){//0��ʾʹ��Pu��ʽ����subtopic�����Ҫ��
			return 1.0/subQueryNum;
		}
		if(type==1){//1��ʾʹ��ic��ʽ����subtopic�����Ҫ��??????
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
	 * ���� ic��q_i|q)
	 * @param resList ��ʼquery�������
	 * @param subResList ������query�ļ������
	 * @param subResNum  ÿ����query�����Ľ������
	 * @param i  ��i+1��query
	 * @return
	 */
	private double I_C(ArrayList<String> resList,ArrayList<String[]> subResList,int[] subResNum,int i){
		String[] subRes=subResList.get(i);
		int subQueryRetDocNum=subResNum[i];
		int maxSubQueryRetDocNum=maxRetDocNum(subResNum);
		ArrayList<String> inResList=new ArrayList<String>();
		int subQueryRetDocInTopR=subQueryRetDocInTopInitRes(resList,subRes,inResList);

		//�����һ���������ԭʼquery�����ǰtao��������ĵ�����query����Ĵ���
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
	 * ԭʼquery�����Ľ��ǰ tao ��������query���������ĵ�����
	 * @param resList
	 * @param subResList
	 * @param inResList ��query���ڳ�ʼqueryǰtao���ĵ��б�
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
	 * ��������query�м������Ľ�������ĵ���
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
	 * ����docID�ڳ�ʼ��ѯ����е�λ��   j(d,q)
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
	 * ���� P((not)S|q_i)
	 * ��S�������ĵ�������ĸ���
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
	 * �Ӵ˴���ʼ���У�Ϊÿ��query��ԭʼ���R������diversity���S
	 * @param queryID  ��ǰ�����queryID
	 * @param initRes  ��query����Ӧ�ĳ�ʼ���������������docid��
	 * @param tao  �ɵ����� ����С��R���ĵ�����
	 * @param lanta  �ɵ����� 0-1
	 * @param subQueryNum  ��query����
	 * @param subScore ����subtopic�������
	 */
	public double[] diversificationRes(ArrayList<String> initRes){
		divResList=new ArrayList<String>();   //��ʼ�����ձ�����
		//����tao��
		double finalResult;
		double[] discount=new double[this.subQueryNum];
		for(int i=0;i<discount.length;i++){
			discount[i]=1;
		}
		double[] docscore=new double[this.tao];
		int si=0;


		while(divResList.size() < this.tao) {
			int size=initRes.size();//R\S

			double res[]=new double[size];//����R/S�ĵ��ļ���÷�
			for (int i=0;i<size;i++) {   //�ӵ�ǰ��R��ѡ��÷���ߵ��ĵ����ŵ�S��
				double part_1 = (1 - this.lanta)* this.p_Query_DOC(initRes.get(i), -1);//-1��ʾ��ԭʼ����в���
				double part_2 = 0.0;
				for (int k = 0; k < this.subQueryNum; k++) {
					//System.out.print(initRes.get(i)+"\t"+this.p_Query_DOC(initRes.get(i), k)+"\t������÷�\n");
					part_2 += this.queryImportance(0,k)
							* this.p_Query_DOC(initRes.get(i), k)//k��ʾ��subtopic k�в���
							* discount[k];
					//* this.rankingDivres_Not_Query(k);//S�������ĵ���subtopic k�ϵķ�ֵ����
				}
				part_2 *= this.lanta;
				finalResult = part_1 + part_2;
				res[i]=finalResult;
			}
			int maxIndex=this.chooseMaxResult(res);    //ÿһ�ε���������ѡ���������ĵ�
			docscore[si]=res[maxIndex];
			si++;
			divResList.add(initRes.get(maxIndex));     //��ѡ�е��ĵ�����S
			for(int i=0;i<discount.length;i++){
				discount[i]*=(1-this.p_Query_DOC(initRes.get(maxIndex), i));
				//	discount[i]/=Math.pow(2,this.p_Query_DOC(initRes.get(maxIndex), i));
			}
			initRes.remove(maxIndex);                 //��ѡ�е��ĵ���R���Ƴ�
		}
		return docscore;
	}

	/**
	 * return subScore.size()
	 * @param initRes  ��query����Ӧ�ĳ�ʼ���������������docid��
	 */
	public int getInput(ArrayList<String> initRes,String InitResultPath,String subResultPath) throws Exception
	{

		BufferedReader initbf=new BufferedReader(new FileReader(InitResultPath));
		String templine="";
		while((templine=initbf.readLine())!=null){
			String[] item=templine.split(" |\t");
			int queryid=Integer.parseInt(item[0]);
			//�ҵ��˸�queryid
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
		//System.out.println("��ȡqid�µĳ�ʼ�������");


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
			if(queryid!=qid && subScore.size()>0){//subResultPath�ļ���ͬһqid�µ�subids����һ��
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
		//qid��ȡֵ��Χ�������ݵ���
		for(int qid=51;qid<=100;qid++){
			//
			if(qid==95||qid==100) continue;

			ArrayList<String> initRes = new ArrayList<String>();//ԭʼ����ĵ�id����
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
		System.out.println("xQuAD�����..");
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
		//qid��ȡֵ��Χ�������ݵ���
		for(int qid=101;qid<=150;qid++){
			//
			if(qid==95||qid==100) continue;

			ArrayList<String> initRes = new ArrayList<String>();//ԭʼ����ĵ�id����
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
		System.out.println("xQuAD�����..");
	}
	/**
	 * ��������ļ����ж���������,�����������ļ�
	 * @throws Exception 
	 */
	public static void generate_xQuADFile(String initResultPath,String subResultPath,String output,int qid_first) throws Exception{
		int tao=100;
		double lanta=0;
		FileWriter fileWriter=null;
		BufferedWriter buffWriter=null;
		//lantaΪ0.0,0.1,0.2,...,1.0
		for(int time=0;time<11;time++){
			fileWriter=new FileWriter(output+lanta);
			buffWriter=new BufferedWriter(fileWriter);
			//qid��ȡֵ��Χ�������ݵ���
			for(int qid=qid_first;qid<=(qid_first+49);qid++){
				//
				if(qid==95||qid==100) continue;

				ArrayList<String> initRes = new ArrayList<String>();//ԭʼ����ĵ�id����
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
			System.out.println(output+lanta+"�ļ��Ѳ���..");
			//tradeoff����0.1
			lanta=add(lanta,0.1);
		}
		System.out.println("xQuAD�����..");
	}
	/**
	 * �ṩ��ȷ�ļӷ����㡣
	 * 
	 * @param v1 ������
	 * @param v2 ����
	 * @return ���������ĺ�
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

						ArrayList<String> initRes = new ArrayList<String>();//ԭʼ����ĵ�id����			
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
