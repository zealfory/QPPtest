package webTrackAdhoc2014;

import java.io.IOException;

/**
 * 根据input.CiirWikiRm计算每个query的WIG值,并将WIGScore存入文件。
 * 和predictor.WIG.java相同,main()函数不同。
 * 根据input.CiirSdm.inversed计算每个query的WIG值,并将WIGScore存入文件。
 * 
 * @track为CiirSub1
 * */
public class WIG {
	
	public static void main(String[] args) throws IOException{
		predictor.WIG predictorWIG=new predictor.WIG();
		predictorWIG.setK(5);//把predictorWIG的k设为5
		/**这里的QueryLength.getQueryLength()为webTrackAdhoc2014 包中的,
		 * 与temporary.QueryLength.java不同
		 */
		predictorWIG.setQueryMap(QueryLength.getQueryLength());
		predictorWIG.getWIGScores("./webTrackAdhoc2014/input.CiirSub1.inversed",
				"./webTrackAdhoc2014/wIGScore.CiirSub1");
		System.out
		.println("根据input.CiirSub1.inversed计算每个query的WIG值,并将WIGScore存入文件,已完成..");
	}

}
