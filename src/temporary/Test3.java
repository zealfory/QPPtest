package temporary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test3 {
	//遍历summary文件夹
	public static void main(String[] str) throws IOException {
		File f = null;
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in)); // 接受控制台的输入
		System.out.println("请输入一个目录:"); // 提示输入目录
		String path = read.readLine(); // 获取路径
		f = new File(path); // 新建文件实例
		File[] list = f.listFiles(); /* 此处获取文件夹下的所有文件 */
		/*for (int i = 0; i < list.length; i++){
			System.out.println(list[i].getAbsolutePath());// 打印全路径，可以更改为你自己需要的方法
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
			if(!fileName.endsWith(".gz")){//分析非压缩包文件
				
				//fileName含long,short的文件没有map值,需计算
				if(fileName.contains("long")||fileName.contains("short")){
					double[] ap=new double[50];//设置数组长度为50
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
					bufferedReader.close();//关闭IO连接
					//根据ap数组计算map值
					ap_i=0;
					for(ap_i=0;ap_i<ap.length;ap_i++)
						sum_ap=sum_ap+ap[ap_i];
					map=sum_ap/ap.length;
					System.out.println(fileName+"\t"+map);
				}
				
				//fileName不含long,short的文件,可直接获取map值
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
