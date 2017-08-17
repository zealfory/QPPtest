package ComputeMyIndex;

public class SMV {
	private final double emda=0.35;//取前35%的文档
	
	public  double  mean(double[] m, int n){
		double s=0;
		for(int i=0;i<n;i++){
			s+=m[i];
		}
		return  s/n;
	}
	public  double  Umean(double[] m, int n){
		double s=0;
		int i=0;
		double critical =m[0]-(m[0]-m[n-1])*emda;
		for( i=0;m[i]>critical;i++){
			s+=m[i];
		}
		return s/i;
	}
	
	public double compute(double[] score, int n){
		double u=Umean(score,n);
		double  critical = score[0]-(score[0]-score[n-1])*emda;
		double s=0;
		int  i=0;
		for(i=0;score[i]>critical;i++){
			s+=score[i]*Math.abs(Math.log(score[i]/u));
		}
		double numerator=s/i;
		double denom=mean(score,n);
		return numerator/denom ;
	}

}
