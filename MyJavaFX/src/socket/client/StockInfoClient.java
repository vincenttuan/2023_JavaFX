package socket.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;

import socket.model.StockInfo;

public class StockInfoClient {
	
	public static void main(String[] args) {
		try(Socket socket = new Socket("localhost", 5000)) {
			System.out.println("Connected to server");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.US_ASCII), 8192);
			
			Gson gson = new Gson();
			String line;
			while ((line = in.readLine()) != null) {
				StockInfo stockInfo = gson.fromJson(line, StockInfo.class);
				System.out.println(stockInfo);
				Thread.sleep(1);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
