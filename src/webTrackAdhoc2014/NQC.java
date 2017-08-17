package webTrackAdhoc2014;
/**
 * 根据input.CiirWikiRm计算每个query的NQC值,并将NQCScore存入文件。
 * 和predictor.NQC.java相同,main()函数不同
 * 
 * 根据input.CiirSdm.inversed计算每个query的NQC值,并将NQCScore存入文件。
 * 
 * @track为CiirSub1
 * 
 * */
public class NQC {
	
	public static void main(String[] args){
		predictor.NQC predictorNQC=new predictor.NQC();
		predictorNQC.setK(1000);//把predictorNQC的k设为1000
		predictorNQC.getNQCScores("./webTrackAdhoc2014/input.CiirSub1.inversed", "./webTrackAdhoc2014/nQCScore.CiirSub1");
		System.out.println("根据input.CiirSub1.inversed计算每个query的NQC值,并将NQCScore存入文件,已完成..");
	}

}

