package temporary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test4 {
	// ����summary�ļ���
	public static void main(String[] str) throws IOException {
		File f = null;
		BufferedReader read = new BufferedReader(new InputStreamReader(
				System.in)); // ���ܿ���̨������
		System.out.println("������һ��Ŀ¼:"); // ��ʾ����Ŀ¼
		String path = read.readLine(); // ��ȡ·��
		f = new File(path); // �½��ļ�ʵ��
		File[] list = f.listFiles(); /* �˴���ȡ�ļ����µ������ļ� */

		/*for (int i = 0; i < list.length; i++){
			// System.out.println(list[i].getAbsolutePath());// ��ӡȫ·�������Ը���Ϊ���Լ���Ҫ�ķ���
			//System.out.println(list[i].getName()); 
		}*/

		System.out.println(list.length);

		// by ChenJiawei
		String fileName = null;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		String tempLine = null;
		int count = 0;//��¼�ļ�������
		String[] terms=null;//���tempLine������
		String preQueryId=null;
		int queryId_count=0;
		boolean meetCondition=true;
		double map=0;
		double map_max=0;
		String fileName_max=null;
		
		for (int i = 0; i < list.length; i++) {
			fileName = list[i].getName();
			//����input�ļ��Ƿ���250000��
			/*if (!fileName.endsWith(".gz")) {// ������ѹ�����ļ�
				fileReader = new FileReader(list[i]);
				bufferedReader = new BufferedReader(fileReader);
				while ((tempLine = bufferedReader.readLine()) != null) {
					count++;
				}
				if(count>=500000) System.out.println(fileName);
				bufferedReader.close();
				count=0;//count��λ
			}*/
			//����input�ļ���ÿ��queryId��Ӧ���ĵ����Ƿ񶼴��ڵ���100,�������ڵ���100,��queryId_count=50,����ļ���.
			/*if (!fileName.endsWith(".gz")) {// ������ѹ�����ļ�
				fileReader = new FileReader(list[i]);
				bufferedReader = new BufferedReader(fileReader);
				preQueryId=null;//preQueryId��Ϊ��
				queryId_count=0;//queryId_count��Ϊ0
				count=0;//count��λ
				meetCondition=true;//meetCondition��Ϊtrue
				while ((tempLine = bufferedReader.readLine()) != null) {
					terms=tempLine.split("\t| ");
					//���preQueryIdΪnull
					if(preQueryId==null){
						preQueryId=terms[0];
						queryId_count++;
					}
					//queryId��ͬ,count++
					if(preQueryId.equalsIgnoreCase(terms[0])){
						count++;
					}
					//queryId��ͬ,��queryId_count++,����preQueryId��countֵ,��count<100,�˳�ѭ��;��count>=100,���������µ�queryId,���count��Ϣ,����terms��Ϣ.
					if(!preQueryId.equalsIgnoreCase(terms[0])){
						queryId_count++;
						//��count<100,�˳�ѭ��
						if(count<100){
							meetCondition=false;
							break;
						}
						//��count>=100,���������µ�queryId.���count��Ϣ,����terms��Ϣ.
						count=0;
						preQueryId=terms[0];
						count++;
					}
				}
				//���queryId��Ӧ��countδ����,����countֵ
				if(count<100) meetCondition=false;
				if(queryId_count==50&&meetCondition==true) System.out.println(fileName);
				bufferedReader.close();
			}
			*/
			/*
			//����summary�ļ�,��ȡmapֵ
			if(!fileName.endsWith(".gz")){// ������ѹ�����ļ�
				fileReader=new FileReader(list[i]);
				bufferedReader=new BufferedReader(fileReader);
				while((tempLine=bufferedReader.readLine())!=null){
					if(tempLine.contains("map")&&tempLine.contains("all")){
						terms=tempLine.split(" |\t");
						map=Double.parseDouble(terms[terms.length-1]);
						//
						if(map_max==0){
							map_max=map;
							fileName_max=fileName;
						}
						//
						if(map_max<map){
							map_max=map;
							fileName_max=fileName;
						}
						System.out.println(fileName+"\t"+map);
					}
				}
				bufferedReader.close();
			}
			*/
			
			//����summary�ļ�,��ȡERR-IA@20ֵ
			if(!fileName.endsWith(".gz")){//������ѹ�����ļ�
				fileReader=new FileReader(list[i]);
				bufferedReader=new BufferedReader(fileReader);
				while((tempLine=bufferedReader.readLine())!=null){
					if(tempLine.contains(",amean,")){
						terms=tempLine.trim().split(",");
						System.out.println(fileName+"\t"+terms[4].trim());
					}
				}
				bufferedReader.close();
			}
				
		}
		/*
		//
		System.out.println("map����ֵ��Ӧ����Ϣ:");
		System.out.println(fileName_max+"\tmap_max="+map_max);
		*/
	}

}
