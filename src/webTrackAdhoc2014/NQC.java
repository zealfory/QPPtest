package webTrackAdhoc2014;
/**
 * ����input.CiirWikiRm����ÿ��query��NQCֵ,����NQCScore�����ļ���
 * ��predictor.NQC.java��ͬ,main()������ͬ
 * 
 * ����input.CiirSdm.inversed����ÿ��query��NQCֵ,����NQCScore�����ļ���
 * 
 * @trackΪCiirSub1
 * 
 * */
public class NQC {
	
	public static void main(String[] args){
		predictor.NQC predictorNQC=new predictor.NQC();
		predictorNQC.setK(1000);//��predictorNQC��k��Ϊ1000
		predictorNQC.getNQCScores("./webTrackAdhoc2014/input.CiirSub1.inversed", "./webTrackAdhoc2014/nQCScore.CiirSub1");
		System.out.println("����input.CiirSub1.inversed����ÿ��query��NQCֵ,����NQCScore�����ļ�,�����..");
	}

}

