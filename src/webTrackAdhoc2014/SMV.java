package webTrackAdhoc2014;
/**
 * ����input.CiirWikiRm����ÿ��query��SMVֵ,����SMVScore�����ļ���
 * ��predictor.SMV.java��ͬ,main()������ͬ��
 * ����input.CiirSdm.inversed����ÿ��query��SMVֵ,����SMVScore�����ļ���
 * 
 * @trackΪCiirSub1
 * */
public class SMV {
	
	public static void main(String[] args) {
		predictor.SMV predictorSMV = new predictor.SMV();
		predictorSMV.setK(1000);//��predictorSMV��k��Ϊ1000
		predictorSMV.getSMVScores("./webTrackAdhoc2014/input.CiirSub1.inversed",
				"./webTrackAdhoc2014/sMVScore.CiirSub1");
		System.out
		.println("����input.CiirSub1.inversed����ÿ��query��SMVֵ,����SMVScore�����ļ�,�����..");
	}

}
