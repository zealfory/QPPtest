package webTrackAdhoc2014;
/**
 * ����input.CiirWikiRm����ÿ��query��SDֵ,����SDScore�����ļ���
 * ��predictor.SD.java��ͬ,main()������ͬ��
 * ����input.CiirSdm.inversed����ÿ��query��SDֵ,����SDScore�����ļ���
 * 
 * @trackΪCiirSub1
 * */
public class SD {
	
	public static void main(String[] args) {
		predictor.SD predictorSD=new predictor.SD();
		predictorSD.setK(1000);//��predictorSD��k��Ϊ1000
		predictorSD.getSDScores("./webTrackAdhoc2014/input.CiirSub1.inversed",
				"./webTrackAdhoc2014/sDScore.CiirSub1");
		System.out
		.println("����input.CiirSub1.inversed����ÿ��query��SDֵ,����SDScore�����ļ�,�����..");
	}

}

