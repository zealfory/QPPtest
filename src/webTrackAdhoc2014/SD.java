package webTrackAdhoc2014;
/**
 * 根据input.CiirWikiRm计算每个query的SD值,并将SDScore存入文件。
 * 和predictor.SD.java相同,main()函数不同。
 * 根据input.CiirSdm.inversed计算每个query的SD值,并将SDScore存入文件。
 * 
 * @track为CiirSub1
 * */
public class SD {
	
	public static void main(String[] args) {
		predictor.SD predictorSD=new predictor.SD();
		predictorSD.setK(1000);//把predictorSD的k设为1000
		predictorSD.getSDScores("./webTrackAdhoc2014/input.CiirSub1.inversed",
				"./webTrackAdhoc2014/sDScore.CiirSub1");
		System.out
		.println("根据input.CiirSub1.inversed计算每个query的SD值,并将SDScore存入文件,已完成..");
	}

}

