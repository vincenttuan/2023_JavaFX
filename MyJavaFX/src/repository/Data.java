package repository;

import application.stock.mvc.model.StockInfo;

public class Data {
	
	public Quote quote = new Quote();
	public Order order = new Order();
	
	// 關於報價的資料
	public class Quote {
		
		private StockInfo lastStockInfo; // 存放最新報價
		
		// 給 SocketClient 調用來設定最新資料
		public void setLastStockInfo(String symbol, Double lastPrice, String matchTime) {
			if(lastPrice == null) return;
			if(lastStockInfo == null) {
				lastStockInfo = new StockInfo();
			}
			lastStockInfo.setSymbol(symbol);
			lastStockInfo.setLastPrice(lastPrice);
			// 將時間格式化
			matchTime = matchTime.substring(0, 2) + ":" + matchTime.substring(2, 4) + ":" + matchTime.substring(4, 6);
			lastStockInfo.setMatchTime(matchTime);
		}
		
		// 給 JavaFX UI(Controller) 來取得最新資料
		public StockInfo getLastStockInfo() {
			return lastStockInfo;
		}
		
	}
	
	// 關於下單的資料
	public static class Order {
		
	}
	
	
	//---------------------------------------------------------------------------
	
	private static Data _instance = new Data();
	
	private Data() {
		
	}
	
	public static Data getInstance() {
		return _instance;
	}
}
