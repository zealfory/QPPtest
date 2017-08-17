package Preprocess;

public class test {
	public String[] q = new String[3];

	public String[] set() {
		q[0] = "sfa";
		q[1] = "sfffffffffff";
		System.out.println("set()ÖÐµÄq[2]=" + q[2]);
		q[2] = "³Â¼ÑÎ°ÄãºÃÑ½";
		return q;
	}
	public static void console(String out){
		System.out.println(out);
	}

	public static void main(String[] args) {
		/*String a1="hello, i  am 	a s		tud		ent. \n";
		String[] a2=a1.split(" |\t|\n");
		for(int i=0;i<a2.length;i++)
			System.out.println("a2["+i+"]="+a2[i]);
		System.out.println(a2[9]==null);
		String a3="   hello  my name is   ";
		String[] a4=a3.split(" ");
		System.out.println(a4.length);
		for(int i=0;i<a4.length;i++)
			System.out.println("a4["+(i+1)+"]="+a4[i]);*/
		String str="  </top>  hello";
		//if(str.matches("</top>.*"))
		//	System.out.println("matched..");
		if(str.contains("</top>"))
			System.out.println("matched..");
		StringBuffer buffer=new StringBuffer();
		System.out.println(buffer.length()+" "+buffer.substring(0, 0).trim());
	}
}
