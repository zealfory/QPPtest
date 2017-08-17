package ComputeMyIndex;

public class WIG {
	private int k;
    public WIG(){
    	this.k=5;
    }
	
	public double wig(double[] score,int n,String query){
		double s=0,ScoreD=mean(score,n);
		int qlen=queryLen(query);
		for(int i=0;i<k;i++){
			s+=(score[i]-ScoreD)/Math.sqrt(qlen);
		}
		return  s/k;
	}
	
	public double mean(double[] m, int n){
		double s=0;
		for(int i=0;i<n;i++){
			s+=m[i];
		}
		return  s/n;
	}
	
	public int queryLen(String q){
		String[] query=q.split(" ");
		return query.length;
	}

}
