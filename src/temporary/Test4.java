package temporary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test4 {
	// 遍历summary文件夹
	public static void main(String[] str) throws IOException {
		File f = null;
		BufferedReader read = new BufferedReader(new InputStreamReader(
				System.in)); // 接受控制台的输入
		System.out.println("请输入一个目录:"); // 提示输入目录
		String path = read.readLine(); // 获取路径
		f = new File(path); // 新建文件实例
		File[] list = f.listFiles(); /* 此处获取文件夹下的所有文件 */

		/*for (int i = 0; i < list.length; i++){
			// System.out.println(list[i].getAbsolutePath());// 打印全路径，可以更改为你自己需要的方法
			//System.out.println(list[i].getName()); 
		}*/

		System.out.println(list.length);

		// by ChenJiawei
		String fileName = null;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		String tempLine = null;
		int count = 0;//记录文件的行数
		String[] terms=null;//存放tempLine的数据
		String preQueryId=null;
		int queryId_count=0;
		boolean meetCondition=true;
		double map=0;
		double map_max=0;
		String fileName_max=null;
		
		for (int i = 0; i < list.length; i++) {
			fileName = list[i].getName();
			//分析input文件是否有250000行
			/*if (!fileName.endsWith(".gz")) {// 分析非压缩包文件
				fileReader = new FileReader(list[i]);
				bufferedReader = new BufferedReader(fileReader);
				while ((tempLine = bufferedReader.readLine()) != null) {
					count++;
				}
				if(count>=500000) System.out.println(fileName);
				bufferedReader.close();
				count=0;//count复位
			}*/
			//分析input文件中每个queryId对应的文档数是否都大于等于100,若都大于等于100,且queryId_count=50,输出文件名.
			/*if (!fileName.endsWith(".gz")) {// 分析非压缩包文件
				fileReader = new FileReader(list[i]);
				bufferedReader = new BufferedReader(fileReader);
				preQueryId=null;//preQueryId置为空
				queryId_count=0;//queryId_count置为0
				count=0;//count复位
				meetCondition=true;//meetCondition置为true
				while ((tempLine = bufferedReader.readLine()) != null) {
					terms=tempLine.split("\t| ");
					//起初preQueryId为null
					if(preQueryId==null){
						preQueryId=terms[0];
						queryId_count++;
					}
					//queryId相同,count++
					if(preQueryId.equalsIgnoreCase(terms[0])){
						count++;
					}
					//queryId不同,则queryId_count++,分析preQueryId的count值,若count<100,退出循环;若count>=100,继续分析新的queryId,清空count信息,处理terms信息.
					if(!preQueryId.equalsIgnoreCase(terms[0])){
						queryId_count++;
						//若count<100,退出循环
						if(count<100){
							meetCondition=false;
							break;
						}
						//若count>=100,继续分析新的queryId.清空count信息,处理terms信息.
						count=0;
						preQueryId=terms[0];
						count++;
					}
				}
				//最后queryId对应的count未处理,分析count值
				if(count<100) meetCondition=false;
				if(queryId_count==50&&meetCondition==true) System.out.println(fileName);
				bufferedReader.close();
			}
			*/
			/*
			//分析summary文件,获取map值
			if(!fileName.endsWith(".gz")){// 分析非压缩包文件
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
			
			//分析summary文件,获取ERR-IA@20值
			if(!fileName.endsWith(".gz")){//分析非压缩包文件
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
		System.out.println("map最大的值对应的信息:");
		System.out.println(fileName_max+"\tmap_max="+map_max);
		*/
	}

}
