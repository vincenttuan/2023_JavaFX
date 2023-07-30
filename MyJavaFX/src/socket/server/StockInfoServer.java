package socket.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import application.config.PropertiesConfig;
import socket.model.StockInfo;

public class StockInfoServer {
	private static Gson gson = new Gson();
	private static GsonBuilder gsonBuilder = new GsonBuilder();
	private static final int socketServerPort = Integer.parseInt(PropertiesConfig.PROP.get("socketServerPort")+"");
	private static final String jsonFilePath = PropertiesConfig.PROP.get("jsonFilePath")+"";
	
	public static void main(String[] args) throws FileNotFoundException {
		
		System.out.println("Parsing json data");
		String jsonStr = new Scanner(new File(jsonFilePath)).useDelimiter("\\A").next();
		/*
		// Json 反序列化
		JsonDeserializer<StockInfo> deserializer = new JsonDeserializer<StockInfo>() {
			@Override
			public StockInfo deserialize(JsonElement json, Type type, JsonDeserializationContext context)
					throws JsonParseException {
				StockInfo stockInfo = gson.fromJson(json, StockInfo.class);
				stockInfo.setSymbol(stockInfo.getSymbol().trim());
				return stockInfo;
			}
		};
		// 註冊反序列化
		gsonBuilder.registerTypeAdapter(StockInfo.class, deserializer);
		// 使用有反序列化的 gson
		gson = gsonBuilder.create();
		*/ 
		StockInfo[] stockInfos = gson.fromJson(jsonStr, StockInfo[].class);
		for(StockInfo stockInfo : stockInfos) {
			stockInfo.setSymbol(stockInfo.getSymbol().trim());
		}
		
		System.out.println("stockInfos length: " + stockInfos.length);
		
		try(ServerSocket serverSocket = new ServerSocket(socketServerPort)) {
			
			System.out.println("Server is running....");
			
			// 等待 client 端連線
			System.out.println("Waiting to client");
			try(Socket clientSocket = serverSocket.accept()) {
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				System.out.println("Connected to client");
				
				for(StockInfo stockInfo : stockInfos) {
					out.println(gson.toJson(stockInfo));
					Thread.sleep(1);
					if(stockInfo.getSymbol().equals("000000") && stockInfo.getMatchTime().equals("999999999999")) {
						System.out.println("今日交易結束");
						break;
					}
				}
				System.out.println("Connection with client finished");
				
			} catch (Exception e) {
				System.out.println("Connection with client lost");
			}
			
			//serverSocket.close();
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			System.out.println("Server is closed");
		}

	}

}
