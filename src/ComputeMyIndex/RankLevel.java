package ComputeMyIndex;

public class RankLevel {
	public static int c = 100;

	public RankLevel(int n) {
		RankLevel.c = n;
	}

	public static double[] constructGrade() {
		double[] grade = new double[c];
		for (int rank = 0; rank < c; rank++) {
			grade[rank] =(double)2 * (c + 1 - rank) / (c * (c + 1));
		}
		return grade;
	}
	public static void main(String[] args){
		System.out.println(2.0/3);
		System.out.println(((double)2)/3);
	}

}
