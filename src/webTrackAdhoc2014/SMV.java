package webTrackAdhoc2014;
/**
 * 根据input.CiirWikiRm计算每个query的SMV值,并将SMVScore存入文件。
 * 和predictor.SMV.java相同,main()函数不同。
 * 根据input.CiirSdm.inversed计算每个query的SMV值,并将SMVScore存入文件。
 * 
 * @track为CiirSub1
 * */
public class SMV {
	
	public static void main(String[] args) {
		predictor.SMV predictorSMV = new predictor.SMV();
		predictorSMV.setK(1000);//把predictorSMV的k设为1000
		predictorSMV.getSMVScores("./webTrackAdhoc2014/input.CiirSub1.inversed",
				"./webTrackAdhoc2014/sMVScore.CiirSub1");
		System.out
		.println("根据input.CiirSub1.inversed计算每个query的SMV值,并将SMVScore存入文件,已完成..");
	}

}
