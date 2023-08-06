package repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import application.stock.mvc.model.StockInfo;

public class Data {
	
	public Quote quote = new Quote();
	public Order order = new Order();
	
	// 關於報價的資料
	public class Quote {
		// 存放最新報價 by symbol
		private ConcurrentHashMap<String, StockInfo> lastStockInfoMap = new ConcurrentHashMap<>();
		
		// 給 SocketClient 調用來設定最新資料
		public void setLastStockInfo(String symbol, Double lastPrice, String matchTime) {
			if(lastPrice == null) return;
			StockInfo lastStockInfo = new StockInfo();
			lastStockInfo.setSymbol(symbol);
			lastStockInfo.setLastPrice(lastPrice);
			// 將時間格式化 (硬編碼)
			//matchTime = matchTime.substring(0, 2) + ":" + matchTime.substring(2, 4) + ":" + matchTime.substring(4, 6);
			lastStockInfo.setMatchTime(formatMatchTime(matchTime));
			// 將 StockInfo 加入到 lastStockInfoMap
			lastStockInfoMap.put(symbol, lastStockInfo);
			//System.out.println(lastStockInfoMap);
		}
		
		public String formatMatchTime(String matchTime) {
			StringBuilder sb = new StringBuilder();
			sb.append(matchTime.substring(0, 2));
			sb.append(":");
			sb.append(matchTime.substring(2, 4));
			sb.append(":");
			sb.append(matchTime.substring(4, 6));
			return sb.toString();
		}
		
		// 給 JavaFX UI(Controller) 來取得最新資料
		public StockInfo getLastStockInfo(String symbol) {
			//System.out.println("symbol = " + symbol + " " + lastStockInfoMap.get(symbol));
			return lastStockInfoMap.get(symbol);
		}
		
	}
	
	// 關於下單的資料
	public static class Order {
		
	}
	
	
	//---------------------------------------------------------------------------
	// 急初始化
	//private static Data _instance = new Data();
	// 緩初始化
	private static Data _instance;
	
	private Data() {
		
	}
	
	public static Data getInstance() {
		if(_instance == null) {
			_instance = new Data();
		}
		return _instance;
	}
}
