package ComputeMyIndex;

import java.io.FileWriter;
import java.io.IOException;

public class MyIndex {
	
	private  int k=1000;  //k ���ڿ���NQCָ��Ľض�ָ�ꡣ

	private  String id;
	public MyIndex(){
		
	}
	
	public MyIndex(String id){
		this.id=id;
	}
	//standardDev�㷨
	public double standardDev(double[] m,int n){
		double  midd=mean(m,n);
		double   s = 0;
		for(int i=0 ; i<n ;i++){
			s+=(m[i]-midd)*(m[i]-midd);
		}
		double iner= s/(n-1);
		return Math.sqrt(iner);
	}
	
	public double mean(double[] m, int n){
		double s=0;
		for(int i=0;i<n;i++){
			s+=m[i];
		}
		return  s/n;
	}
	//NQC�㷨
	public double NQC(double[] score,int n){
		double u=Umean(score);
		double s=0;
		for(int i=0;i<k;i++){
			s+=(score[i]-u)*(score[i]-u);
		}
		double numerator=Math.sqrt(s/k);
		double denom=Math.abs(mean(score,n));
		return numerator/denom;
	}
	
	public double Umean(double[] m){
		double s=0;
		for(int i=0;i<k;i++){
			s+=m[i];
		}
		return  s/k;
	}
	//KDS�㷨
	public double KDS(double[] score,int n){
		double u=Umean(score);
		double s=0;
		for(int i=0;i<k;i++){
			s+=score[i]*Math.abs(Math.log(score[i]/u));
		}
		double numerator=s/k;
		double denom=mean(score,n);
		return numerator/denom;
	}
	//WIG�㷨
	public double WIG(double[] score, int n){
		this.k=5;
		double s=0,ScoreD=mean(score,n); 
		for(int i=0;i<k;i++){
			s+=score[i]-ScoreD;
		}
		return  s/k;
	}
	//for KDS
	public void KDS_anly(double[] score,int n,FileWriter writer) throws IOException{
		double u=Umean(score);
		for(int i=0;i<k;i++){
			writer.write(id+"\t"+score[i]+"\t"+Math.abs(Math.log(score[i]/u))+"\t");
			writer.write(score[i]*Math.abs(Math.log(score[i]/u))+"\n");
		}
		writer.write("\n");
	}
	
	//
	//
	//
	//�״γ���score[i]<=u��ֹͣ������ǰi��score��KDS
	public double KDSpos(double[] score,int n){
		double u=Umean(score);
		double s=0;
		int i;
		for(i=0;i<k;i++){
			if(score[i]>u){
			 s+=score[i]*Math.log(score[i]/u);
			}else
				break;
		}
		double numerator=s/i;
		double denom=mean(score,n);
		System.out.println("i="+i);
		return numerator/denom ;
	}
	//score[i]<uʱ��KDS
	public double KDSneg(double[] score,int n){
		double u=Umean(score);
		double s=0;
		int neg=0;
		for(int i=0;i<k;i++){
			if(score[i]<u){
			 s+=(score[i])*Math.log(score[i]/u)*(-1);
			 neg++;
			}
		}
		double numerator=s/neg;
		double denom=mean(score,n);
		System.out.println("neg="+neg);
		return numerator/denom ;
	}
	//KL�㷨 score[i]>u
	public double KL(double[] score){
		double u=Umean(score);
		double s=0;
		int i;
		for(i=0;i<k;i++){
			if(score[i]>u){
			 s+=score[i]*Math.log(score[i]/u);
			}else
				break;
		}
		double numerator=s/i;
		return numerator;
	}
	/**
	 * Math.sqrt(iner)*mean(m,n)
	 * */
	public double u$standardDev(double[] m,int n){
		double  midd=mean(m,n);
		double  s = 0;
		for(int i=0;i<n;i++){
			s+=(m[i]-midd)*(m[i]-midd);
		}
		double iner= s/(n-1);
		return Math.sqrt(iner)*midd;
	}
	/**
	 * score[i]*score[i]*(score[i]-u)*(score[i]-u)
	 * */
	public double uNQC(double[] score, int n){
		double u=Umean(score);
		double s=0;
		for(int i=0;i<k;i++){
			s+=score[i]*score[i]*(score[i]-u)*(score[i]-u);
		}
		double numerator=Math.sqrt(s/k);
		double denom=Math.abs(mean(score,n));
		return  numerator/denom;
	}
	/**
	 * 0.6*kds+0.4*wig
	 * */
	public double Combine(double[] score,int n){
		double kds=KDS(score,n);
		double wig=WIG(score,n);
		return 0.6*kds+0.4*wig;
	}
	
	/**
	 * 0.5*clus+0.5*NQC(score,10)
	 * */
	public double Cluster_NQC(double[] score){
		double clus=standardDev(score,10);
		return 0.5*clus+0.5*NQC(score,10);
      }
	/**
	 * m+=grade[i]*(score[i]-score[i+1])
	 * */
	public double rankGrade(double[] score){
		double m=0;
		double[] grade=null;
		grade=RankLevel.constructGrade();
		for(int i=0;i<RankLevel.c;i++){
			m+=grade[i]*(score[i]-score[i+1]);
		}
		return  m;
	}
	
	/**
	 * rankNQCʹ��ʱ�����޸�, Kֵ��RankLevel.c��ֵ��ȡ�
	 */
	public double rankNQC(double[] score,int n){
		double u=Umean(score);
		double s=0,difference=0;
		double[] grade=null;
		grade=RankLevel.constructGrade();
		for(int i=0;i<k;i++){
			difference=(score[i]-u);
			s+=(1+grade[i])*difference*difference;
		}
		double numerator=Math.sqrt(s/k);
		double denom=Math.abs(mean(score,n));
		return numerator/denom;
	}
	/**
	 * score[0]
	 * */
	public double firstScore(double[] score){
		return score[0];
	}
	
	/**
	 * curr��bottom��TOP K�ı�׼���÷֡�
	 * @param score
	 * @param n
	 * @return
	 */
	public double smoothRankNQC(double[] score, int n){
		double u=Umean(score), curr = u-score[k-1],bottom= score[0]-score[k-1];
		double s=0,diff=0;
		double[] grade=null;
		grade=RankLevel.constructGrade();
		for(int i=0;i<k;i++){
			diff=(score[i]-u);
			s+=(1+grade[i])*diff*diff;
		}
		double numerator=Math.sqrt(s/k);
		double denom=Math.abs(mean(score,n));
		return 0.7*numerator/denom+0.3*curr/bottom;
	}
	
	public double[]  normaliseScore(double[] score){
		return null;
	}
	
	public static void main(String[] args){
		double b=Math.log(Math.E);
		System.out.println("Math.log "+b+" Math.E="+Math.E);
	}

}
