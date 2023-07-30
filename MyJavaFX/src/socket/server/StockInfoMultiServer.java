package socket.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
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
				new Thread(new ClientHandler(clientSocket)).start();
			}
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			System.out.println("Server is closed");
		}

	}
	
	private static class ClientHandler implements Runnable {
		private Socket clientSocket;
		
		ClientHandler(Socket clientSocket) {
			this.clientSocket = clientSocket;
		}
		
		@Override
		public void run() {
			System.out.println("Connected to client: " + clientSocket.getPort());
			try(PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
				
				for(StockInfo stockInfo : stockInfos) {
					out.println(gson.toJson(stockInfo));
					Thread.sleep(1);
					if(stockInfo.getSymbol().equals("000000") && stockInfo.getMatchTime().equals("999999999999")) {
						System.out.println("今日交易結束");
						break;
					}
				}
				
			} catch (IOException | InterruptedException e) {
				System.out.println("Connection with client lost");
				System.out.println(e);
			} finally {
				try {
					clientSocket.close();
				} catch (IOException e2) {
					System.out.println(e2);
				}
				System.out.println("Waiting for new connection...");
			}
		}
		
	}
	

}
