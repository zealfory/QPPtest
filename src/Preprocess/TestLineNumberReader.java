package Preprocess;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class TestLineNumberReader {
	
	public void testLineNumberReader() {
		LineNumberReader lineNumberReader = null;
		FileReader fileReader = null;

		try {
			fileReader = new FileReader("./TREC5/map.ETHme1");
			lineNumberReader = new LineNumberReader(fileReader);
			String line = null;
			while ((line = lineNumberReader.readLine()) != null) {
				System.out.println(lineNumberReader.getLineNumber() + ": "+ line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close(lineNumberReader);
			close(fileReader);
		}
	}

	private void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
				closeable = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args){
		TestLineNumberReader tln=new TestLineNumberReader();
		tln.testLineNumberReader();
	}
}
