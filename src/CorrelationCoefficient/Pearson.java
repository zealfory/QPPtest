package CorrelationCoefficient;

import java.io.IOException;
import java.util.Scanner;

public class Pearson {
	private double[] m;
	private double[] n;
	private int k;

	public Pearson(Scanner scan, int k) {
		this.k = k;
		m = new double[k];
		n = new double[k];
		
		System.out.println("please input the first arow m[k]:");
		for (int i = 0; i < k; i++) {
			m[i] = scan.nextDouble();
		}
		System.out.println("please input the second arow n[k]:");
		for (int i = 0; i < k; i++) {
			n[i] = scan.nextDouble();
		}
		//scan.close();//��ѭ�ȴ򿪺�ر�, ����ȹر�ԭ��
	}

	public double sum(double[] r) {
		double s = 0;
		for (int i = 0; i < k; i++) {
			s += r[i];
		}
		return s;
	}

	public double sumSqure(double[] r) {
		double s = 0;
		for (int i = 0; i < k; i++) {
			s += r[i] * r[i];
		}
		return s;
	}

	public double sumMulti(double[] x, double[] y) {
		double s = 0;
		for (int i = 0; i < k; i++) {
			s += x[i] * y[i];
		}
		return s;
	}

	public double getPearsonCoefficient() {
		double sumXY = sumMulti(m, n);
		double sumX = sum(m);
		double sumY = sum(n);
		double sumX2 = sumSqure(m);
		double sumY2 = sumSqure(n);
		double p = (sumXY - sumX * sumY / k)
				/ Math.sqrt((sumX2 - sumX * sumX / k)
						* (sumY2 - sumY * sumY / k));
		return p;
	}

	public static void main(String[] args) throws IOException {
		int n = 0;
		System.out.print("please input the size of arow:");
		//Scanner��ѭ�ȴ򿪺�ر�, ����ȹر�ԭ��
		Scanner scan = new Scanner(System.in);
		n = scan.nextInt();
		
		Pearson pearson = new Pearson(scan, n);
		double p = pearson.getPearsonCoefficient();
		scan.close();
		System.out.println("X and Y pearson: " + p);
		
	}

}
