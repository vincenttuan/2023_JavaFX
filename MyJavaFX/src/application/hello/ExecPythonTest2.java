package application.hello;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ExecPythonTest2 {

	public static void main(String[] args) throws Exception {
        String pythonPath = "python"; 
        String scriptPath = "C:\\Users\\vince\\git\\2023_JavaFX\\MyJavaFX\\src\\application\\hello\\bfp.py"; // 替換為你的Python檔案的路徑

        ProcessBuilder pb = new ProcessBuilder(pythonPath, scriptPath);
        Process process = pb.start();
        
        // 捕捉輸出
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        int exitCode = process.waitFor();
        System.out.println("Exit Code: " + exitCode);
    }
}
