package terabyteTrack2004_0628;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetMedianMap {
	// 遍历summary文件夹
	public static void main(String[] str) throws IOException {
		File f = null;
		BufferedReader read = new BufferedReader(new InputStreamReader(
				System.in)); // 接受控制台的输入
		System.out.println("请输入一个目录:"); // 提示输入目录
		String path = read.readLine(); // 获取路径
		f = new File(path); // 新建文件实例
		File[] list = f.listFiles(); /* 此处获取文件夹下的所有文件 */

		/*
		 * for (int i = 0; i < list.length; i++){ //
		 * System.out.println(list[i].getAbsolutePath());// 打印全路径，可以更改为你自己需要的方法
		 * //System.out.println(list[i].getName()); }
		 */

		System.out.println(list.length);

		// by ChenJiawei
		String fileName = null;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		String tempLine = null;
		int count = 0;// 记录文件的行数
		String map=null;//存储map值

		for (int i = 0; i < list.length; i++) {
			fileName = list[i].getName();
			// 分析input文件是否有500000行
			if (!fileName.endsWith(".gz")) {// 分析非压缩包文件
				fileReader = new FileReader(list[i]);
				bufferedReader = new BufferedReader(fileReader);
				while ((tempLine = bufferedReader.readLine()) != null) {
					count++;
				}
				if (count >= 500000)
					System.out.println(fileName);
				bufferedReader.close();
				count = 0;// count复位
			}
			
			//析summary文件,获取map值
			/*if(!fileName.endsWith(".gz")){//分析非压缩包文件
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
