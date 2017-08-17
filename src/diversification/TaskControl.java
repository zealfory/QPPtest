package diversification;

public class TaskControl {
	
	/**
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception{
		//对input文件进行规范化和多样化重排
		//Diversification_taskControl.processTask();
		
		//产生summary文件,确定最后用于预测的summary和input文件
		//evaluation.Evaluation_taskControl.processTask();
		
		//转换queryId格式,并产生预测结果
		Prediction_taskControl.processTask();
		
		
		System.out.println("TaskControl已运行结束..");
	}

}
