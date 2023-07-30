package socket.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import com.google.gson.Gson;

import socket.model.StockInfo;

// 多人連線版本
public class StockInfoMultiServer {
	static Gson gson = new Gson();
	static StockInfo[] stockInfos;
	
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Parsing json data");
		String jsonStr = new Scanner(new File("C:\\Users\\vince\\OneDrive\\桌面\\price.json")).useDelimiter("\\A").next();
		stockInfos = gson.fromJson(jsonStr, StockInfo[].class);
		for(StockInfo stockInfo : stockInfos) {
			stockInfo.setSymbol(stockInfo.getSymbol().trim());
		}
		System.out.println("stockInfos length: " + stockInfos.length);
		
		try(ServerSocket serverSocket = new ServerSocket(5000)) {
			System.out.println("Server is running....");
			// 等待多人連線
			while (true) {
				Socket clientSocket = serverSocket.accept(); // 等待有人連入
				new Thread().start();
			}
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			System.out.println("Server is closed");
		}

	}
	
	private static class ClientHandler implements Runnable {
		private Socket socket;
		
		ClientHandler(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
		
	}
	

}
