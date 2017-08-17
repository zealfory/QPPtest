package evaluation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * some changes has been made.. by chenjiawei.<br>
 * on 20160101,a little change made by ChenJiawei and these changes make map-ia and nrbp correct.
 * @author 1
 *
 */
public class Ndeval {
	public int rTopics;//num of topics in run
	public int qTopics;//num of topics in qrels
	public int bTopics;//num of topics in baseline
	public int actualTopics;// actual num of topics for evaluation
	public String runid;
	public String brunid;
	public static int DEPTH=20;
	public static int LARGE_ENOUGH=100000;

	public String baseline=null;
	public double alpha=0.5;
	public double beta=0.5;
	public double riskAlpha=0;
	public String riskAlphaDefString = "0"; /* set to match riskAlpha */
	public String riskAlphaStr = null;

	public int cFlag=0,aFlag=0;
	public int traditional=0;//use traditional TREC sort order for runs 0or1
	public int depthM=0;//cut off rank for runs (0 => no cut off) 


	public void setAlpha(double a){
		if (a < 0.0 || a > 1.0)
			System.err.println("wrong");
		else
			alpha=a;
	}
	public void setBeta(double a){
		if (a < 0.0 || a > 1.0)
			System.err.println("wrong");
		else
			beta=a;
	}
	public void setDepth(int a){
		if (a<=0)
			System.err.println("wrong");
		else
			depthM=a;
	}
	public void setTraditional(int traditional) {
		this.traditional = traditional;
	}	
	public void setBaseline(String baseline) {
		this.baseline = baseline;
	}
	public void setRiskAlpha(double riskAlpha) {
		this.riskAlpha = riskAlpha;
	}
	public void setRiskAlphaStr(String riskAlphaStr) {
		this.riskAlphaStr = riskAlphaStr;
	}
	public void setaFlag(int aFlag) {
		this.aFlag = aFlag;
	}



	/**
	 * a single result, with a pointer to relevance judgments 
	 * @author hcl
	 *
	 */
	public class result{
		public String docno;
		public int topic,rank;
		public int[] rel;
		public double score;
		public result(String docno, int topic) {
			super();
			this.docno = docno;
			this.topic = topic;
			this.rank = 0;
			this.rel = null;
			this.score = 0;
		}
		public result(String docid, int topic, int rank, double score) {
			super();
			this.docno = docid;
			this.topic = topic;
			this.rank = rank;
			this.rel = null;
			this.score = score;
		}	
	}
	/**
	 * result list (or summarized qrels) for a single topic
	 * @author hcl
	 *
	 */
	public class rList{
		public ArrayList<result> list;
		public int topic,subtopics,actualSubtopics;
		public int nrel;//num of relevant documents for a topic
		public int[] nrelSub;
		public double mapIA,map;
		public double nrbp,nnrbp;
		public double[] dcg,ndcg;//depth size
		public double[] err,nerr;//depth size
		public double[] precision,strec;
		public rList(int topic){
			list=new ArrayList<>();
			this.topic=topic;
			subtopics=0;
			actualSubtopics=0;
			nrel=0;
			nrelSub=null;
			dcg=new double[DEPTH];
			ndcg=new double[DEPTH];
			err=new double[DEPTH];
			nerr=new double[DEPTH];
			precision=new double[DEPTH];
			strec=new double[DEPTH];
		}
	}
	/**
	 * a single qrel
	 * @author hcl
	 *
	 */
	public class qrel{
		public String docno;
		public int topic,subtopic,judgment;
		public qrel(String docno, int topic, int subtopic, int judgment) {
			super();
			this.docno = docno;
			this.topic = topic;
			this.subtopic = subtopic;
			this.judgment = judgment;
		}

	}

	/**
	 * �ȸ���topic�Ƚϣ���θ���docno�Ƚ�
	 * @author hcl
	 *
	 */
	private class qrelCompare implements Comparator<qrel>{
		@Override
		public int compare(qrel aq, qrel bq) {
			if (aq.topic < bq.topic)
				return -1;
			if (aq.topic > bq.topic)
				return 1;
			return aq.docno.compareTo(bq.docno);
		}

	}
	/**
	 * �ȸ���topic�Ƚϣ���θ���rank�Ƚ�
	 * @author hcl
	 *
	 */
	private class resultCompareByRank implements Comparator<result>{
		@Override
		public int compare(result ar, result br) {
			if (ar.topic < br.topic)
				return -1;
			if (ar.topic > br.topic)
				return 1;
			return ar.rank - br.rank;
		}

	}
	/**
	 * �ȸ���topic�Ƚϣ���θ���docno�Ƚ�
	 * @author hcl
	 *
	 */
	private class resultCompareByDocno implements Comparator<result>{

		@Override
		public int compare(result ar, result br) {
			if (ar.topic < br.topic)
				return -1;
			if (ar.topic > br.topic)
				return 1;
			return ar.docno.compareTo(br.docno);
		}

	}
	/**
	 * �ȸ���topic�Ƚϣ���θ���score�Ƚϣ�������docno�Ƚ�
	 * @author hcl
	 *
	 */
	private class resultCompareByScore implements Comparator<result>{

		@Override
		public int compare(result ar, result br) {
			if (ar.topic < br.topic)
				return -1;
			if (ar.topic > br.topic)
				return 1;
			if (ar.score < br.score)
				return 1;
			if (ar.score > br.score)
				return -1;
			return br.docno.compareTo(ar.docno);
		}

	}


	public double TopicVal(int z,double e){
		return z>0?0:e;
	}
	public double RiskBased(double r, double b, double a){
		double result;
		if(r>b){
			result=r-b;
		}else{
			result=(a+1)*(r-b);
		}
		//changed by chenjiawei
		//if(Double.valueOf(result).isNaN()) return 0;
		return result;
	}

	/**
	 * for a given qrel result list, compute overall number of relevant documents
     by assuming that a document which is relevant to any subtopic is relevant
     to the topic as a whole; used to compute (non-intent-aware) MAP.
	 * @param rl
	 */
	public void nrel(rList rl){
		int i, j;
		rl.nrel = 0;
		for (i = 0; i < rl.list.size(); i++){
			int todo = 1;
			for (j = 0; todo>0 && j < rl.subtopics; j++)
				if (rl.list.get(i).rel[j]>0){
					rl.nrel++;
					todo = 0;
				}
		}
	}
	/**
	 * for a given qrel result list, determine the number of subtopics actually
     represented; if a subtopic has never received a positive judgment, we
     ignore it.
	 * @param rl
	 */
	public void actualSubtopics(rList rl){
		rl.actualSubtopics = 0;
		for (int i = 0; i < rl.subtopics; i++)
			if (rl.nrelSub[i]>0){
				rl.actualSubtopics++;
			}
	}
	/**
	 * for a qrel result list, assign ranks to maximize gain at each rank;
     the problem is NP-complete, but a simple greedy algorithm works fine
	 * @param rl
	 */
	public void idealResult(rList rl){
		double[] subtopicGain =new double[rl.subtopics];
		for (int i = 0; i < rl.subtopics; i++)
			subtopicGain[i] = 1.0;

		/* horrible quadratic greedy approximation of the ideal result */
		for (int rank = 1; rank <= rl.list.size(); rank++){
			int where = -1;
			double maxScore = 0.0; 

			for (int i = 0; i < rl.list.size(); i++){
				if (rl.list.get(i).rank == 0){
					double currentScore = 0.0;
					for (int j = 0; j < rl.subtopics; j++)
						if (rl.list.get(i).rel[j]>0)
							currentScore += subtopicGain[j];

					/* tied scores are arbitrarily resolved by docno */
					if ( where == -1 || currentScore > maxScore || (
							currentScore == maxScore && rl.list.get(i).docno.compareTo(rl.list.get(where).docno) > 0)){
						maxScore = currentScore;
						where = i;
					}
				}
			}
			rl.list.get(where).rank = rank;

			for (int i = 0; i < rl.subtopics; i++)
				if (rl.list.get(where).rel[i]>0)
					subtopicGain[i] *= (1.0 - alpha);
		}
	}

	private void computePrecision(rList rl) {	
		if(rl.actualSubtopics==0)
			return;
		int count = 0;
		for(int i = 0; i < DEPTH && i < rl.list.size(); i++){
			if (rl.list.get(i).rel!=null){
				for (int j = 0; j < rl.subtopics; j++)
					if (rl.list.get(i).rel[j]>0)
						count++;
			}
			rl.precision[i] =(double)count/((i + 1)*rl.actualSubtopics);
		}		
	}

	/**
	 * compute intent-aware mean average precision (MAP-IA);
     also computes standard MAP by assuming that a document relevant to any
     subtopic is relevant to the topic as a whole.
	 * @param rl
	 */
	public void computeMAP(rList rl){
		double count = 0.0, total = 0.0;
		int[] subtopicCount =new int[rl.subtopics];
		double[] subtopicTotal =new double[rl.subtopics];

		rl.map=rl.mapIA=0.0;
		if(rl.actualSubtopics==0)
			return;
		for(int i=0;i<rl.subtopics;i++){
			subtopicCount[i]=0;
			subtopicTotal[i]=0.0;
		}

		for(int i=0;i<rl.list.size();i++){
			int todo=1;
			if(rl.list.get(i).rel!=null){//���ĵ�����صģ����ĵ����ٸ���һ��Subtopic
				for(int j=0;j<rl.subtopics;j++){
					if(rl.list.get(i).rel[j]>0){
						subtopicCount[j]++;
						subtopicTotal[j]+=subtopicCount[j]/(double)(i+1);//compute the precision at the position i on a subtopic
						if(todo>0){
							count++;
							total+=count/((double)(i+1));//for the map
							todo=0;
						}
					}
				}
			}
		}

		rl.map=total/rl.nrel;
		rl.mapIA=0.0;
		for(int j=0;j<rl.subtopics;j++){
			if(rl.nrelSub[j]>0){
				rl.mapIA+=subtopicTotal[j]/rl.nrelSub[j];
			}
		}
		rl.mapIA/=rl.actualSubtopics;
	}
	/**
	 * compute NRBP for a result list;
     assumes all results are labeled with relevance judgments
	 * @param rl
	 */
	public void computeNRBP(rList rl){
		double decay = 1.0;
		double[] subtopicGain = new double[rl.subtopics];
		rl.nrbp=0.0;
		if(rl.actualSubtopics==0)
			return;
		for(int i=0;i<rl.subtopics;i++){
			subtopicGain[i]=1.0;
		}
		//changed by ChenJiawei
		for(int i=0;i<rl.list.size();i++){
			double score=0;
			if(rl.list.get(i).rel!=null)
				for(int j=0;j<rl.subtopics;j++)
					if(rl.list.get(i).rel[j]>0){
						score+=subtopicGain[j];
						subtopicGain[j]*=(1.0-alpha);
					}
			rl.nrbp+=score*decay;
			decay*=beta;

		}
		rl.nrbp*=(1-(1-alpha)*beta)/rl.actualSubtopics;
	}

	/**
	 * compute (and cache) ranked based discount for ndcg
	 * @param rank
	 */
	public double discount(int rank){
		double[] cache=new double[LARGE_ENOUGH];
		int top = 0;
		if (rank > 0){
			if (rank < top)
				return cache[rank];
			else if (rank < LARGE_ENOUGH){
				do{
					cache[top] = Math.log(2.0)/Math.log(top + 2.0);
					top++;
				}while (rank >= top); 
				return cache[rank];
			}
			else
				return Math.log(2.0)/Math.log(rank + 2.0);
		}
		else
			return 1;
	}
	/**
	 * compute DCG for a result list (a run or qrels);
     assumes all results are labeled with relevance judgments;
     the result is normalized using a simple "ideal ideal" normalization
     (standard nDCG normalization comes later)
	 * @param rl
	 */
	public void computeDCG(rList rl){
		double[] subtopicGain = new double[rl.subtopics];
		double[] idealIdeal=new double[DEPTH];
		double idealIdealGain=rl.actualSubtopics;

		for (int i = 0; i < DEPTH; i++)
			rl.dcg[i] = 0.0;

		if (rl.actualSubtopics == 0)
			return;

		for (int i = 0; i < rl.subtopics; i++)
			subtopicGain[i] = 1.0;

		for (int i = 0; i < DEPTH && i < rl.list.size(); i++){
			double score = 0.0;
			if (rl.list.get(i).rel!=null)
				for (int j = 0; j < rl.subtopics; j++)
					if (rl.list.get(i).rel[j]>0){
						score += subtopicGain[j];
						subtopicGain[j] *= (1.0 - alpha);
					}
			rl.dcg[i] = score*discount(i);
		}

		for (int i = 0; i < DEPTH; i++){
			idealIdeal[i] = idealIdealGain*discount(i);
			idealIdealGain *= (1.0 - alpha);
		}

		for (int i = 1; i < DEPTH; i++){
			/* cumulative gain */
			rl.dcg[i] += rl.dcg[i-1];
			idealIdeal[i] += idealIdeal[i - 1];
		}

		/* simple normalization ("ideal ideal normalization") */
		for (int i = 1; i < DEPTH; i++)
			rl.dcg[i] /= idealIdeal[i];
	}
	/**
	 * compute ERR for a result list (a run or qrels);
     assumes all results are labeled with relevance judgments;
     the result is normalized using a simple "ideal ideal" normalization
	 * @param rl
	 */
	public void computeERR(rList rl){
		double[] subtopicGain = new double[rl.subtopics];
		double[] idealIdeal=new double[DEPTH];
		double idealIdealGain=(double)rl.actualSubtopics;
		for (int i = 0; i < DEPTH; i++)
			rl.err[i] = 0.0;

		if (rl.actualSubtopics == 0)
			return;

		for (int i = 0; i < rl.subtopics; i++)
			subtopicGain[i] = 1.0;

		for (int i = 0; i < DEPTH && i < rl.list.size(); i++){
			double score = 0.0;
			if (rl.list.get(i).rel!=null){
				for (int j = 0; j < rl.subtopics; j++)
					if (rl.list.get(i).rel[j]>0){
						score += subtopicGain[j];
						subtopicGain[j] *= (1.0 - alpha);
					}
			}		
			rl.err[i] = score/((double)(i + 1));
		}

		for (int i = 0; i < DEPTH; i++){
			idealIdeal[i] = idealIdealGain/((double)(i + 1));
			idealIdealGain *= (1.0 - alpha);
		}

		for (int i = 1; i < DEPTH; i++){
			rl.err[i] += rl.err[i-1];
			idealIdeal[i] += idealIdeal[i - 1];
		}

		/* simple normalization ("ideal ideal normalization") */
		for (int i = 1; i < DEPTH; i++)
			rl.err[i] /= idealIdeal[i];
	}


	/**
	 * populate an ideal result list from the qrels;
     also used to label the run with relevance judgments
	 * @param q
	 * @param n
	 * @param rl
	 * @param topics
	 */
	public ArrayList<rList> qrelPopulateResultList(ArrayList<qrel> q){
		ArrayList<rList> rl=new ArrayList<>();
		//������q�н����topic���ֳɶ��rList����
		int currentTopic=-1,j=-1;

		for (int i = 0; i < q.size(); i++){
			if (q.get(i).topic != currentTopic){
				j++;
				currentTopic = q.get(i).topic;
				rl.add(new rList(currentTopic));
			}
			if (rl.get(j).subtopics <= q.get(i).subtopic)
				rl.get(j).subtopics = q.get(i).subtopic + 1;	
		}
		this.qTopics=j+1;//����qrel��topic����
		//��һ����ʵrList����
		int k = -1;
		currentTopic=-1;
		j=-1;
		String currentDocno = "";	
		for (int i = 0; i < q.size(); i++){
			if (q.get(i).topic != currentTopic){
				j++;
				currentTopic = q.get(i).topic;
				k = -1;
				currentDocno = "";
				rl.get(j).nrelSub=new int[rl.get(j).subtopics];
			}
			if (q.get(i).docno.compareTo(currentDocno) != 0){
				currentDocno = q.get(i).docno;
				k++;
				result r=new result(currentDocno, q.get(i).topic);
				r.rel=new int[rl.get(j).subtopics];
				rl.get(j).list.add(r);
			}
			rl.get(j).list.get(k).rel[q.get(i).subtopic]=q.get(i).judgment;
			rl.get(j).nrelSub[q.get(i).subtopic]+=q.get(i).judgment;
		}
		//����rList��measure
		for (int i = 0; i < rl.size(); i++){
			nrel(rl.get(i));
			actualSubtopics (rl.get(i));
			idealResult (rl.get(i));
			Collections.sort(rl.get(i).list, new resultCompareByRank());
			computeDCG (rl.get(i));
			computeNRBP(rl.get(i));
			computeERR (rl.get(i));
			Collections.sort(rl.get(i).list, new resultCompareByDocno());
		}
		return rl;
	}

	/**
	 * process the qrels file, constructing an ideal result list
	 * @param fileName
	 * @return
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public ArrayList<rList> processQrels (String fileName) throws NumberFormatException, IOException{
		BufferedReader reader=new BufferedReader(new FileReader(fileName));
		StringTokenizer tokenizer;
		ArrayList<qrel> qrels=new ArrayList<>();
		String templine=null;
		int topic,subtopic,rel;
		String docid;
		while((templine=reader.readLine())!=null){
			tokenizer=new StringTokenizer(templine);
			topic=Integer.parseInt(tokenizer.nextToken());
			subtopic=Integer.parseInt(tokenizer.nextToken());
			docid=tokenizer.nextToken();
			rel=Integer.parseInt(tokenizer.nextToken());
			if(rel>0){
				//changed by ChenJiawei
				rel=1;
				qrels.add(new qrel(docid, topic, subtopic, rel));
			}	
		}
		reader.close();
		Collections.sort(qrels, new qrelCompare());	
		return qrelPopulateResultList (qrels);
	}

	/**
	 * populate a result list from a run
	 * @param result
	 */
	public ArrayList<rList> populateResultList(ArrayList<result> r, boolean isbaseline){
		ArrayList<rList> rl=new ArrayList<>();
		//������r�н����topic���ֳɶ��rList����
		int currentTopic = -1;
		int j = -1;
		for (int i = 0; i < r.size(); i++){
			if (r.get(i).topic != currentTopic){
				j++;
				currentTopic = r.get(i).topic;
				rl.add(new rList(currentTopic));
			}
			rl.get(j).list.add(r.get(i));
		}
		if(isbaseline)
			this.bTopics=j+1;
		else
			this.rTopics=j+1;
		return rl;
	}

	/* forceTraditionalRanks:
    Re-assign ranks so that runs are sorted by score and then by docno,
    which is the traditional sort order for TREC runs.
	 */
	public void forceTraditionalRanks (ArrayList<result> r){
		int i, rank = 0, currentTopic = -1;
		Collections.sort(r,new resultCompareByScore());

		for (i = 0; i < r.size(); i++){
			if (r.get(i).topic != currentTopic){
				currentTopic = r.get(i).topic;
				rank = 1;
			}
			r.get(i).rank = rank;
			rank++;
		}
	}

	/* applyCutoff:
    Throw away results deeper than a specified depth.
    Run must be sorted by topic and then rank.
    Return number of remaing results.
	 */
	public ArrayList<result> applyCutoff(ArrayList<result> r,int depthMax){
		ArrayList<result> rj=new ArrayList<>();
		int depth = -1, currentTopic = -1;
		for (int i = 0; i < r.size(); i++){
			if (r.get(i).topic != currentTopic){
				currentTopic = r.get(i).topic;
				depth = 1;
			}
			else
				depth++;
			if (depth <= depthMax){
				rj.add(r.get(i));
			}
		}
		return rj;
	}


	/* processRun:
    process a run file, returning a result list
	 */
	public ArrayList<rList> processRun (File input,boolean isbaseline) throws Exception{
		BufferedReader reader=new BufferedReader(new FileReader(input));
		boolean needRunid = true;
		ArrayList<result> r=new ArrayList<>();//all result in a run

		String templine=null;
		StringTokenizer tokenizer=null;
		int topic,rank;
		String docid;
		double score;

		while ((templine=reader.readLine())!=null){
			tokenizer=new StringTokenizer(templine);
			topic=Integer.parseInt(tokenizer.nextToken());
			tokenizer.nextToken();//Q0
			docid=tokenizer.nextToken();
			rank=Integer.parseInt(tokenizer.nextToken());
			score=Double.parseDouble(tokenizer.nextToken());
			if (needRunid){
				if(isbaseline)
					this.brunid=tokenizer.nextToken();
				else
					this.runid = tokenizer.nextToken();
				needRunid = false;
			}
			r.add(new result(docid, topic,rank,score));
		}
		reader.close();

		/* force ranks to be consistent with traditional TREC sort order */
		if (traditional==1)
			forceTraditionalRanks (r);

		Collections.sort(r, new resultCompareByRank());

		/* for each topic, verify that ranks have not been duplicated */
		for (int i = 1; i < r.size(); i++)
			if (r.get(i).topic == r.get(i-1).topic && r.get(i).rank == r.get(i-1).rank)
				throw new Exception("duplicate rank "+r.get(i).rank+" for topic "+r.get(i).topic+
						" in run file "+input);

		/* apply depth cutoff if specified on the command line */
		if (depthM > 0)
			r = applyCutoff (r, depthM);

		/* for each topic, verify that docnos have not been duplicated */
		Collections.sort(r, new resultCompareByDocno());
		for (int i = 1; i < r.size(); i++)
			if (r.get(i).topic == r.get(i-1).topic && r.get(i).docno.compareTo(r.get(i-1).docno) == 0)
				throw new Exception("duplicate docno "+r.get(i).docno+" for topic "+r.get(i).topic+
						" in run file "+input);

		return populateResultList (r,isbaseline);
	}

	/* applyJudgments:
    copy relevance judgments from qrel results to run results;
    assumes results are sorted by docno
	 */
	public void applyJudgments (ArrayList<result> q, ArrayList<result> r){
		int i = 0, j = 0;

		while (i < q.size() && j < r.size()){
			int cmp = q.get(i).docno.compareTo(r.get(j).docno);
			if (cmp < 0)
				i++;
			else if (cmp > 0)
				j++;
			else{
				r.get(j).rel = q.get(i).rel;
				i++;
				j++;
			}
		}
	}

	/* renormalize:
    normalize a result list against an ideal result list (created from qrels)
	 */
	public void renormalize (rList ql,rList rl){
		for (int i = 0; i < DEPTH; i++)
			if (rl.dcg[i]>0){
				rl.ndcg[i] = rl.dcg[i]/ql.dcg[i];
				rl.nerr[i] = rl.err[i]/ql.err[i];
			}
		rl.nnrbp = rl.nrbp/ql.nrbp;
	}

	/* applyQrels:
    transfer relevance judgments from qrels to a run
	 */
	public  void applyQrels(ArrayList<rList> qrl,ArrayList<rList> rrl){
		int actualtopics=0;
		int i = 0, j = 0;

		while (i < qTopics && j < rTopics){
			if (qrl.get(i).topic < rrl.get(j).topic)
				i++;
			else if (qrl.get(i).topic > rrl.get(j).topic)
				j++;
			else{
				rrl.get(j).subtopics = qrl.get(i).subtopics;
				rrl.get(j).actualSubtopics = qrl.get(i).actualSubtopics;
				rrl.get(j).nrel = qrl.get(i).nrel;
				rrl.get(j).nrelSub = qrl.get(i).nrelSub;
				applyJudgments(qrl.get(i).list, rrl.get(j).list);
				Collections.sort(rrl.get(j).list, new resultCompareByRank());
				computeDCG (rrl.get(j));
				computeNRBP(rrl.get(j));
				computeERR (rrl.get(j));
				renormalize (qrl.get(i), rrl.get(j));
				computeSTRecall (rrl.get(j));
				computePrecision (rrl.get(j));
				computeMAP (rrl.get(j));
				//				computePrecision(rrl.get(j));
				i++;
				j++;
				actualtopics++;
			}
		}
		this.actualTopics=actualtopics;
	}



	private void computeSTRecall(rList rList) {
		
	}
	/**
	 * ĳ��ָ�������в�ѯ������ֵ
	 * <p>����query��ĳһ��ָ���ϵĽ��������1��query��ERR-IA@20,2��query��ERR-IA@20,...</p>
	 * @param rl
	 * @param bl
	 * @param metricid ָ�꣨ERR-IA@20��alpha-nDCG@20,MAP-IA,Prec-IA@20��*����ֻ�ṩ4��
	 * @return
	 */
	public double[] statisticalAMetric(ArrayList<rList> rl,ArrayList<rList> bl,String metricid){
		double[] result=new double[50];
		if(this.actualTopics==0)
			return result;
		int  ZeroBaseline=0;
		if ((bl == null) || (this.bTopics == 0)){
			ZeroBaseline = 1;
		}  

		int i, j, nexti, nextj, numTopics;
		i = j = nexti = nextj = numTopics = 0;

		while ((i < rTopics) || ((j < bTopics) && cFlag>0)){
			int ZeroRun = 0;
			int ZeroBase = ZeroBaseline;
			int topic = 0;
			if (i >= rTopics)
				ZeroRun = 1;
			if (j >= bTopics)
				ZeroBase = 1;
			if (ZeroRun==0 && ZeroBase==0){//run��baseline������
				if (rl.get(i).topic == bl.get(j).topic){
					topic = rl.get(i).topic;
					nexti++;
					nextj++;
				}
				else if (rl.get(i).topic > bl.get(j).topic){
					/* process baseline, run is zero */
					ZeroRun = 1;
					topic = bl.get(j).topic;
					nextj++;
					if (cFlag==0){
						i = nexti;
						j = nextj;
						continue;
					}
				}
				else /* (rl[i].topic < bl[j].topic) */{
					/* process run, baseline is zero */
					topic = rl.get(i).topic;
					ZeroBase = 1;
					nexti++;
				}
			}
			else if (ZeroRun==1){//run������
				topic = bl.get(j).topic;
				nextj++; /* process baseline */
			}
			else /* (ZeroBase) */{//baseline������
				topic = rl.get(i).topic;
				nexti++; /* process run */
			}

			//compute
			switch (metricid) {
			case "ERR-IA@20":
				result[i]=RiskBased(TopicVal(ZeroRun,rl.get(i).err[19]),TopicVal(ZeroBase,bl==null?0:bl.get(j).err[19]), riskAlpha);
				break;
			case "alpha-nDCG@20":	
				result[i]=RiskBased(TopicVal(ZeroRun,rl.get(i).ndcg[19]),TopicVal(ZeroBase,bl==null?0:bl.get(j).ndcg[19]), riskAlpha);
				break;	
			case "MAP-IA":	
				result[i]=RiskBased(TopicVal(ZeroRun,rl.get(i).mapIA),TopicVal(ZeroBase,bl==null?0:bl.get(j).mapIA), riskAlpha);
				break;
			case "Prec-IA@20":
				result[i]=RiskBased(TopicVal(ZeroRun, rl.get(i).precision[19]), TopicVal(ZeroBase, bl==null?0:bl.get(j).precision[19]), riskAlpha);
				//			
				//			case 15:
				//				result[i]=RiskBased(TopicVal(ZeroRun,rl.get(i).err[4]),TopicVal(ZeroBase,bl==null?0:bl.get(j).err[4]), riskAlpha);
				//				break;
				//			case 12:
				//				result[i]=RiskBased(TopicVal(ZeroRun,rl.get(i).err[9]),TopicVal(ZeroBase,bl==null?0:bl.get(j).err[9]), riskAlpha);
				//				break;			
				//			case 16:					
				//				result[i]=RiskBased(TopicVal(ZeroRun,rl.get(i).nerr[4]),TopicVal(ZeroBase,bl==null?0:bl.get(j).nerr[4]), riskAlpha);
				//				break;
				//			case 5:	
				//				result[i]=RiskBased(TopicVal(ZeroRun,rl.get(i).nerr[9]),TopicVal(ZeroBase,bl==null?0:bl.get(j).nerr[9]), riskAlpha);
				//				break;
				//			case 6:	
				//				result[i]=RiskBased(TopicVal(ZeroRun,rl.get(i).nerr[19]),TopicVal(ZeroBase,bl==null?0:bl.get(j).nerr[19]), riskAlpha);
				//				break;
				//			case 7:	
				//				result[i]=RiskBased(TopicVal(ZeroRun,rl.get(i).dcg[4]),TopicVal(ZeroBase,bl==null?0:bl.get(j).dcg[4]), riskAlpha);
				//				break;
				//			case 8:	
				//				result[i]=RiskBased(TopicVal(ZeroRun,rl.get(i).dcg[9]),TopicVal(ZeroBase,bl==null?0:bl.get(j).dcg[9]), riskAlpha);
				//				break;
				//			case 9:	
				//				result[i]=RiskBased(TopicVal(ZeroRun,rl.get(i).dcg[19]),TopicVal(ZeroBase,bl==null?0:bl.get(j).dcg[19]), riskAlpha);
				//				break;
				//			case 10:	
				//				result[i]=RiskBased(TopicVal(ZeroRun,rl.get(i).ndcg[4]),TopicVal(ZeroBase,bl==null?0:bl.get(j).ndcg[4]), riskAlpha);
				//				break;
				//			case 11:	
				//				result[i]=RiskBased(TopicVal(ZeroRun,rl.get(i).ndcg[9]),TopicVal(ZeroBase,bl==null?0:bl.get(j).ndcg[9]), riskAlpha);
				//				break;
				//			
				//			case 13:	
				//				result[i]=RiskBased(TopicVal(ZeroRun,rl.get(i).nrbp),TopicVal(ZeroBase,bl==null?0:bl.get(j).nrbp), riskAlpha);
				//				break;
				//			case 14:	
				//				result[i]=RiskBased(TopicVal(ZeroRun,rl.get(i).nnrbp),TopicVal(ZeroBase,bl==null?0:bl.get(j).nnrbp), riskAlpha);
				//				break;
			default:
				break;
			}
			if(new Double(result[i]).isNaN()){
				System.out.println("\n\nresult["+i+"]ΪDouble.NaN,�ѱ���Ϊ0");
				result[i]=0.0;
			}
			i = nexti;
			j = nextj;
			numTopics++;	 
		}
		if ((bl != null) && (bTopics != 0)){
			if (numTopics > actualTopics)
				actualTopics = numTopics; /* not using the full set but baseline contains some not in the run */
		}
		return result;
	}
	/**
	 * �������в�ѯ�ڶ��ָ���ϵ��������飨topicid,ERR-IA@5,ERR-IA@10,ERR-IA@20,nERR-IA@5,nERR-IA@10,nERR-IA@20,
	 * alpha-DCG@5,alpha-DCG@10,alpha-DCG@20,alpha-nDCG@5,alpha-nDCG@10,alpha-nDCG@20,NRBP,nNRBP,MAP-IA,P-IA@20��
	 * @param rl
	 * @param bl
	 * @return
	 */
	public double[][] statisticalAll(ArrayList<rList> rl,ArrayList<rList> bl){
		double[][] result=new double[this.rTopics][17];
		if(this.actualTopics==0)
			return result;
		int  ZeroBaseline=0;
		if ((bl == null) || (this.bTopics == 0)){
			ZeroBaseline = 1;
		}  

		int i, j, nexti, nextj, numTopics;
		i = j = nexti = nextj = numTopics = 0;

		while ((i < rTopics) || ((j < bTopics) && cFlag>0)){
			int ZeroRun = 0;
			int ZeroBase = ZeroBaseline;
			int topic = 0;
			if (i >= rTopics)
				ZeroRun = 1;
			if (j >= bTopics)
				ZeroBase = 1;
			if (ZeroRun==0 && ZeroBase==0){//run��baseline������
				if (rl.get(i).topic == bl.get(j).topic){
					topic = rl.get(i).topic;
					nexti++;
					nextj++;
				}
				else if (rl.get(i).topic > bl.get(j).topic){
					/* process baseline, run is zero */
					ZeroRun = 1;
					topic = bl.get(j).topic;
					nextj++;
					if (cFlag==0){
						i = nexti;
						j = nextj;
						continue;
					}
				}
				else /* (rl[i].topic < bl[j].topic) */{
					/* process run, baseline is zero */
					topic = rl.get(i).topic;
					ZeroBase = 1;
					nexti++;
				}
			}
			else if (ZeroRun==1){//run������
				topic = bl.get(j).topic;
				nextj++; /* process baseline */
			}
			else /* (ZeroBase) */{//baseline������
				topic = rl.get(i).topic;
				nexti++; /* process run */
			}

			//compute

			result[i][0]=topic;
			result[i][1]=RiskBased(TopicVal(ZeroRun,rl.get(i).err[4]),TopicVal(ZeroBase,bl==null?0:bl.get(j).err[4]), riskAlpha);
			result[i][2]=RiskBased(TopicVal(ZeroRun,rl.get(i).err[9]),TopicVal(ZeroBase,bl==null?0:bl.get(j).err[9]), riskAlpha);
			result[i][3]=RiskBased(TopicVal(ZeroRun,rl.get(i).err[19]),TopicVal(ZeroBase,bl==null?0:bl.get(j).err[19]), riskAlpha);
			result[i][4]=RiskBased(TopicVal(ZeroRun,rl.get(i).nerr[4]),TopicVal(ZeroBase,bl==null?0:bl.get(j).nerr[4]), riskAlpha);
			result[i][5]=RiskBased(TopicVal(ZeroRun,rl.get(i).nerr[9]),TopicVal(ZeroBase,bl==null?0:bl.get(j).nerr[9]), riskAlpha);
			result[i][6]=RiskBased(TopicVal(ZeroRun,rl.get(i).nerr[19]),TopicVal(ZeroBase,bl==null?0:bl.get(j).nerr[19]), riskAlpha);
			result[i][7]=RiskBased(TopicVal(ZeroRun,rl.get(i).dcg[4]),TopicVal(ZeroBase,bl==null?0:bl.get(j).dcg[4]), riskAlpha);
			result[i][8]=RiskBased(TopicVal(ZeroRun,rl.get(i).dcg[9]),TopicVal(ZeroBase,bl==null?0:bl.get(j).dcg[9]), riskAlpha);
			result[i][9]=RiskBased(TopicVal(ZeroRun,rl.get(i).dcg[19]),TopicVal(ZeroBase,bl==null?0:bl.get(j).dcg[19]), riskAlpha);
			result[i][10]=RiskBased(TopicVal(ZeroRun,rl.get(i).ndcg[4]),TopicVal(ZeroBase,bl==null?0:bl.get(j).ndcg[4]), riskAlpha);
			result[i][11]=RiskBased(TopicVal(ZeroRun,rl.get(i).ndcg[9]),TopicVal(ZeroBase,bl==null?0:bl.get(j).ndcg[9]), riskAlpha);
			result[i][12]=RiskBased(TopicVal(ZeroRun,rl.get(i).ndcg[19]),TopicVal(ZeroBase,bl==null?0:bl.get(j).ndcg[19]), riskAlpha);
			result[i][13]=RiskBased(TopicVal(ZeroRun,rl.get(i).nrbp),TopicVal(ZeroBase,bl==null?0:bl.get(j).nrbp), riskAlpha);
			result[i][14]=RiskBased(TopicVal(ZeroRun,rl.get(i).nnrbp),TopicVal(ZeroBase,bl==null?0:bl.get(j).nnrbp), riskAlpha);
			result[i][15]=RiskBased(TopicVal(ZeroRun,rl.get(i).mapIA),TopicVal(ZeroBase,bl==null?0:bl.get(j).mapIA), riskAlpha);
			result[i][16]=RiskBased(TopicVal(ZeroRun,rl.get(i).precision[19]),TopicVal(ZeroBase,bl==null?0:bl.get(j).precision[19]), riskAlpha);
			i = nexti;
			j = nextj;
			numTopics++;	 
		}
		if ((bl != null) && (bTopics != 0)){
			if (numTopics > actualTopics)
				actualTopics = numTopics; /* not using the full set but baseline contains some not in the run */
		}
		return result;
	}
	/**
	 * ��ȡ�����ѯ�ϵ�ƽ��ָ��(ERR-IA@5,ERR-IA@10,2:ERR-IA@20,nERR-IA@5,nERR-IA@10,nERR-IA@20, 
	 * alpha-DCG@5,alpha-DCG@10,alpha-DCG@20,alpha-nDCG@5,alpha-nDCG@10,11:alpha-nDCG@20,
	 * NRBP,nNRBP,14:MAP-IA,15:P-IA@20)
	 * @param result
	 * @return
	 */
	public double[] statisticalAverage(double[][] result){
		double[] average=new double[16];
		for(int i=0;i<result.length;i++){
			for(int j=1;j<result[0].length;j++){
				average[j-1]+=result[i][j];
			}
		}
		for(int k=0;k<average.length;k++){
			average[k]/=actualTopics;
		}
		return average;
	}
	public void outputMetrics(ArrayList<rList> rl,ArrayList<rList> bl){
		double[][] result=statisticalAll(rl, bl);	
		double[] average=statisticalAverage(result);
		System.out.print("runid,topic");
		System.out.print (",ERR-IA@5,ERR-IA@10,ERR-IA@20");
		System.out.print (",nERR-IA@5,nERR-IA@10,nERR-IA@20");
		System.out.print (",alpha-DCG@5,alpha-DCG@10,alpha-DCG@20");
		System.out.print (",alpha-nDCG@5,alpha-nDCG@10,alpha-nDCG@20");
		System.out.print (",NRBP,nNRBP");
		System.out.print (",MAP-IA");
		System.out.print(",P-IA@20");
		System.out.print ("\n");

		if(actualTopics==0){
			System.out.println(runid+",amean,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0");
			return;
		}
		String runstring=null;
		if ((bl == null) || (this.bTopics == 0)){
			runstring = runid;
		}else{
			String between = " (rel to. ";
			String rwstr = ", rs=1+a, a="; /* risk-sensitivity weight */
			String end = ")";
			String riskAlphaString=(this.riskAlphaStr==null?this.riskAlphaDefString:this.riskAlphaStr);
			runstring = (runid) + (between) + (brunid) + (rwstr) + (riskAlphaString) + (end);
		}
		for(int i=0;i<result.length;i++){
			String line=runstring+","+(int)result[i][0];
			/*for(int j=1;j<16;j++){
				line+=","+result[i][j];
			}*/
			for(int j=1;j<result[i].length;j++){
				line+=","+String.format("%.6f", result[i][j]);
			}
			System.out.println(line);
		}

		String line=runstring+",amean";	
		for(int i=0;i<15;i++){
			String num=Double.toString(average[i]);
			line+=","+num.substring(0, num.indexOf(".")+7);
		}
		System.out.println(line);
	}

	public void evaluate(File input,String qrelfile) throws Exception{
		ArrayList<rList> qrl=processQrels(qrelfile);	
		ArrayList<rList> rrl=processRun(input, false);
		applyQrels(qrl, rrl);
		//		double[][] result=statisticalAll(rrl, null);
		outputMetrics(rrl, null);
	}
	public static void main(String[] args) throws Exception{
		int year=2010;
		//String input="E://TREC Data/fusedresult/2009.CombMNZ";
		//String qrelfile="E:/����1/TREC Fusion Experiment/Fusiondata_3years/"+year+"/qrels-for-ndeval."+year+".txt";

		//		Ndeval n=new Ndeval();
		//		int year=2010;
		//		String input="D:/Program/Cygwin/home/hcl/trec/test input.txt";
		//		String qrelfile="D:/Program/Cygwin/home/hcl/trec/test qrel s.txt";
		////		eval.evaluate(new File(input), qrelfile);
		//		ArrayList<rList> rrl=n.processRun(new File(input), false);
		//		ArrayList<rList> qrl=n.processQrels(qrelfile);
		//		n.applyQrels(qrl, rrl);	
		//		double[] mean=n.statisticalAverage(n.statisticalAll(rrl, null));
		//		System.out.println("group,ERR-IA@20,alpha-nDCG@20,MAP-IA,p-ia@20");
		//		System.out.println(year+","+mean[2]+","+mean[11]+","+mean[14]+","+mean[15]);
		/*
		 * ��ȡ���в�ѯ��һ��ָ���ϵ����۽��
		 * Ndeval nd=new Ndeval();
		ArrayList<rList> qrl=nd.processQrels(qrelinput);
		for(int i=0;i<inputs.length;i++){
			ArrayList<rList> rrl=nd.processRun(inputs[i], false);
			nd.applyQrels(qrl, rrl);
			p[i]=nd.statisticalAMetric(rrl, null, metricid);
		}
		 */
		//��ȡ���query�ϵ�ƽ��ָ�꣨����ndeval֧�ֵ�ָ�꣩
		//		 Ndeval n=new Ndeval();
		//		ArrayList<rList> rrl=n.processRun(new File(input), false);
		//		ArrayList<rList> qrl=n.processQrels(qrelfile);
		//		n.applyQrels(qrl, rrl);	
		//		double[] mean=n.statisticalAverage(n.statisticalAll(rrl, null));
		//		System.out.println("p,"+mean[2]+","+mean[11]);
		//		Ndeval n=new Ndeval();
		//		input="E:/TREC Data/2010webdiversity/UMd10IASF";
		//		
		//		File f=new File(input);
		//		
		//		ArrayList<rList> rrl=n.processRun(f, false);
		//		ArrayList<rList> qrl=n.processQrels(qrelfile);
		//		n.applyQrels(qrl, rrl);	
		//		double[] mean=n.statisticalAverage(n.statisticalAll(rrl, null));
		//		System.out.println(mean[0]+","+mean[1]+","+mean[2]);


		String input="./input.CWIcIAt5b1";
		String qrelfile="./qrels-for-ndeval.txt";
		Ndeval n=new Ndeval();
		File f=new File(input);
		ArrayList<rList> rrl=n.processRun(f, false);
		ArrayList<rList> qrl=n.processQrels(qrelfile);
		n.applyQrels(qrl, rrl);

		//double[] mean=n.statisticalAverage(n.statisticalAll(rrl, null));
		//System.out.println(mean);
		n.outputMetrics(rrl, null);



	}
	
	/**
	 * �˺���Ĭ��traditional=0,����input�ļ�ԭʼrank,���ĵ��������ۡ�
	 * ������processRun()������ִ��forceTraditionalRanks()��<br>
	 * by ChenJiawei
	 * @throws Exception 
	 */
	public static void generateSummary(String input,String qrelfile,String output) throws Exception{
		Ndeval n=new Ndeval();		
		File f=new File(input);
		
		/**
		 * ������input�ļ����ڴ��,��Ҫ���ڴ��ж�input�ļ���Ϣ������������,������rank��Ϣ,
		 * ����Ҫִ��forceTraditionalRanks()ʱ,��traditional����Ρ�
		 */
		//��traditional��Ϊ1ʱ,��processRun()�ж�r�����������򲢸���rank��Ϣ
		//n.traditional=1;
		
		ArrayList<rList> rrl=n.processRun(f, false);
		ArrayList<rList> qrl=n.processQrels(qrelfile);
		n.applyQrels(qrl, rrl);
		//ʹ��n.qTopics��ΪactualTopics
		n.cFlag=1;
		n.actualTopics=n.qTopics;
		n.outputMetrics_tofile(rrl, null, output);
		System.out.println("���summary�ļ�,�����...");
	}
	/**
	 * ������ָ��û�м��㡣
	 * @param rl
	 * @param bl
	 * @throws IOException 
	 */
	public void outputMetrics_tofile(ArrayList<rList> rl,ArrayList<rList> bl,String output) throws IOException{
		double[][] result=statisticalAll(rl, bl);
		double[] average=statisticalAverage(result);
		//����Ϣ�����ļ�
		FileWriter fileWriter=null;
		BufferedWriter buffWriter=null;
		String tempLine=null;
		
		fileWriter=new FileWriter(output);
		buffWriter=new BufferedWriter(fileWriter);
		tempLine="runid,topic,ERR-IA@5,ERR-IA@10,ERR-IA@20,nERR-IA@5,nERR-IA@10,nERR-IA@20,alpha-DCG@5,alpha-DCG@10,"
				+ "alpha-DCG@20,alpha-nDCG@5,alpha-nDCG@10,alpha-nDCG@20,NRBP,nNRBP,MAP-IA,P-IA@20\n";
		
		//tempLine="runid,topic,ERR-IA@5,ERR-IA@10,ERR-IA@20,nERR-IA@5,nERR-IA@10,nERR-IA@20,...\n";
		buffWriter.write(tempLine);
		
		if(actualTopics==0){
			buffWriter.write("runid,amean,0.0,0.0,0.0,0.0,0.0,0.0\n");
			buffWriter.close();
			return;
		}
		String runstring=null;
		if ((bl == null) || (this.bTopics == 0)){
			runstring = runid;
		}else{
			String between = " (rel to. ";
			String rwstr = ", rs=1+a, a="; /* risk-sensitivity weight */
			String end = ")";
			String riskAlphaString=(this.riskAlphaStr==null?this.riskAlphaDefString:this.riskAlphaStr);
			runstring = (runid) + (between) + (brunid) + (rwstr) + (riskAlphaString) + (end);
		}
		for(int i=0;i<result.length;i++){
			tempLine=runstring+","+(int)result[i][0];
			for(int j=1;j<result[i].length;j++){
				tempLine+=","+String.format("%.6f", result[i][j]);
			}
			buffWriter.write(tempLine+"\n");			
		}
		//���ƽ��ֵ
		tempLine=runstring+",amean";	
		for(int i=0;i<average.length;i++){
			tempLine+=","+String.format("%.6f", average[i]);
		}
		buffWriter.write(tempLine+"\n");
		buffWriter.close();
	}
	
}
