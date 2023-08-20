package application.hello;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ExecPythonTest {

	public static void main(String[] args) throws Exception {
		ProcessBuilder pb = new ProcessBuilder("python", "-c", "a=10;b=20;print('中文Hello, Python3!', a, b, a+b)");
		Process process = pb.start();
		int exitCode = process.waitFor();
		System.out.println(exitCode);
		InputStream inputStream = process.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String output = reader.readLine();
		System.out.println(output);

	}

}
