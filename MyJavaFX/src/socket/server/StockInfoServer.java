package socket.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.ServerSocket;
import java.util.Scanner;

import com.google.gson.Gson;

import socket.model.StockInfo;

public class StockInfoServer {

	public static void main(String[] args) throws FileNotFoundException {
		
		System.out.println("Parsing json data");
		Gson gson = new Gson();
		String jsonStr = new Scanner(new File("C:\\Users\\vince\\OneDrive\\桌面\\price.json")).useDelimiter("\\A").next();
		StockInfo[] stockInfos = gson.fromJson(jsonStr, StockInfo[].class);
		System.out.println("stockInfos length: " + stockInfos.length);
		
		try(ServerSocket serverSocket = new ServerSocket(5000)) {
			
			System.out.println("Server is running....");
			
			serverSocket.close();
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			System.out.println("Server is closed");
		}

	}

}
