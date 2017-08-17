package webTrackAdhoc2014;

import java.io.IOException;

/**
 * ����input.CiirWikiRm����ÿ��query��WIGֵ,����WIGScore�����ļ���
 * ��predictor.WIG.java��ͬ,main()������ͬ��
 * ����input.CiirSdm.inversed����ÿ��query��WIGֵ,����WIGScore�����ļ���
 * 
 * @trackΪCiirSub1
 * */
public class WIG {
	
	public static void main(String[] args) throws IOException{
		predictor.WIG predictorWIG=new predictor.WIG();
		predictorWIG.setK(5);//��predictorWIG��k��Ϊ5
		/**�����QueryLength.getQueryLength()ΪwebTrackAdhoc2014 ���е�,
		 * ��temporary.QueryLength.java��ͬ
		 */
		predictorWIG.setQueryMap(QueryLength.getQueryLength());
		predictorWIG.getWIGScores("./webTrackAdhoc2014/input.CiirSub1.inversed",
				"./webTrackAdhoc2014/wIGScore.CiirSub1");
		System.out
		.println("����input.CiirSub1.inversed����ÿ��query��WIGֵ,����WIGScore�����ļ�,�����..");
	}

}
