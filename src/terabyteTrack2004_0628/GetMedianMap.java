package terabyteTrack2004_0628;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetMedianMap {
	// ����summary�ļ���
	public static void main(String[] str) throws IOException {
		File f = null;
		BufferedReader read = new BufferedReader(new InputStreamReader(
				System.in)); // ���ܿ���̨������
		System.out.println("������һ��Ŀ¼:"); // ��ʾ����Ŀ¼
		String path = read.readLine(); // ��ȡ·��
		f = new File(path); // �½��ļ�ʵ��
		File[] list = f.listFiles(); /* �˴���ȡ�ļ����µ������ļ� */

		/*
		 * for (int i = 0; i < list.length; i++){ //
		 * System.out.println(list[i].getAbsolutePath());// ��ӡȫ·�������Ը���Ϊ���Լ���Ҫ�ķ���
		 * //System.out.println(list[i].getName()); }
		 */

		System.out.println(list.length);

		// by ChenJiawei
		String fileName = null;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		String tempLine = null;
		int count = 0;// ��¼�ļ�������
		String map=null;//�洢mapֵ

		for (int i = 0; i < list.length; i++) {
			fileName = list[i].getName();
			// ����input�ļ��Ƿ���500000��
			if (!fileName.endsWith(".gz")) {// ������ѹ�����ļ�
				fileReader = new FileReader(list[i]);
				bufferedReader = new BufferedReader(fileReader);
				while ((tempLine = bufferedReader.readLine()) != null) {
					count++;
				}
				if (count >= 500000)
					System.out.println(fileName);
				bufferedReader.close();
				count = 0;// count��λ
			}
			
			//��summary�ļ�,��ȡmapֵ
			/*if(!fileName.endsWith(".gz")){//������ѹ�����ļ�
				fileReader=new FileReader(list[i]);
				bufferedReader=new BufferedReader(fileReader);
				while((tempLine=bufferedReader.readLine())!=null){
					if(tempLine.startsWith("map")&&tempLine.contains("all")){
						StringBuffer s=new StringBuffer();
						s.append(tempLine);
						
						SummaryAnalysis.nextString(s, 1);
						SummaryAnalysis.nextString(s, 2);
						map=SummaryAnalysis.nextString(s, 3);
						System.out.println(fileName+"\t"+map);
					}
				}
				bufferedReader.close();
			}*/


		}
	}

}
