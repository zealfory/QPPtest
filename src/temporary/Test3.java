package temporary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test3 {
	//����summary�ļ���
	public static void main(String[] str) throws IOException {
		File f = null;
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in)); // ���ܿ���̨������
		System.out.println("������һ��Ŀ¼:"); // ��ʾ����Ŀ¼
		String path = read.readLine(); // ��ȡ·��
		f = new File(path); // �½��ļ�ʵ��
		File[] list = f.listFiles(); /* �˴���ȡ�ļ����µ������ļ� */
		/*for (int i = 0; i < list.length; i++){
			System.out.println(list[i].getAbsolutePath());// ��ӡȫ·�������Ը���Ϊ���Լ���Ҫ�ķ���
			System.out.println(list[i].getName());
		}*/
		System.out.println(list.length);
		
		
		//by ChenJiawei
		String fileName=null;
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		String tempLine=null;
		//int count=0;
		for(int i=0;i<list.length;i++){
			fileName=list[i].getName();
			if(!fileName.endsWith(".gz")){//������ѹ�����ļ�
				
				//fileName��long,short���ļ�û��mapֵ,�����
				if(fileName.contains("long")||fileName.contains("short")){
					double[] ap=new double[50];//�������鳤��Ϊ50
					int ap_i=0;
					double sum_ap=0;
					double map=0;
					fileReader=new FileReader(list[i]);
					bufferedReader=new BufferedReader(fileReader);
					while((tempLine=bufferedReader.readLine())!=null){
						if(tempLine.contains("Average precision (non-interpolated) over all rel docs")){
							tempLine=bufferedReader.readLine();
							ap[ap_i++]=Double.parseDouble(tempLine.trim());
						}
					}
					bufferedReader.close();//�ر�IO����
					//����ap�������mapֵ
					ap_i=0;
					for(ap_i=0;ap_i<ap.length;ap_i++)
						sum_ap=sum_ap+ap[ap_i];
					map=sum_ap/ap.length;
					System.out.println(fileName+"\t"+map);
				}
				
				//fileName����long,short���ļ�,��ֱ�ӻ�ȡmapֵ
				if(!(fileName.contains("long")||fileName.contains("short"))){
					fileReader=new FileReader(list[i]);
					bufferedReader=new BufferedReader(fileReader);
					while((tempLine=bufferedReader.readLine())!=null){
						if(tempLine.contains("Queryid (Num):")&&tempLine.contains("all")){
							for(int j=0;j<18;j++)
								tempLine=bufferedReader.readLine();
							System.out.println(fileName+"\t"+tempLine.trim());
						}
					}
					bufferedReader.close();
				}
				
			}
		}
	}

}
