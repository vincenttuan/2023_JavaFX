package socket.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;

import repository.Data;
import socket.model.StockInfo;

public class StockInfoClient {
	
	public static void main(String[] args) {
		//final String symbols = "2330,2412,1101";
		final String symbols = "2330";
		try(Socket socket = new Socket("localhost", 5000)) {
			System.out.println("Connected to server");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.US_ASCII), 8192);
			
			Gson gson = new Gson();
			String line;
			while ((line = in.readLine()) != null) {
				StockInfo stockInfo = gson.fromJson(line, StockInfo.class);
				if(symbols.contains(stockInfo.getSymbol())) {
					System.out.println(stockInfo);
				}
				// 將最新資料設定給 LastStockInfo
				Data.getInstance().quote.setLastStockInfo(stockInfo.getSymbol(), stockInfo.getLastPrice(), stockInfo.getMatchTime());
				Thread.sleep(1);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
