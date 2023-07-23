package application.stock.single;

import java.util.List;

import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// 報價資料
// json: {"msgSeq":"4400854","symbol":"00650L","market":null,"turnover":3060,"lastPrice":null,"lastVolume":null,"bidPrices":[14.600,14.500,14.400,14.200,14.100],"bidVolumes":[61,25,215,349,329],"askPrices":[14.1000,14.1100,14.1200,14.1300,14.1400],"askVolumes":[81,115,70,47,489],"matchTime":"132015439437"},
public class StockInfo {
	
	private StringProperty symbol = new SimpleStringProperty(); // 股票代號
    private DoubleProperty lastPrice = new SimpleDoubleProperty(); // 最新成交價
    private StringProperty matchTime = new SimpleStringProperty(); // 成交時間
    
    public String getSymbol() {
		return symbol.get();
	}
	public void setSymbol(String symbol) {
		this.symbol.set(symbol);
	}
	
	public Double getLastPrice() {
		return lastPrice.get();
	}
	
	public void setLastPrice(Double lastPrice) {
		this.lastPrice.set(lastPrice);
	}
		
	public String getMatchTime() {
		return matchTime.get();
	}
	
	public void setMatchTime(String matchTime) {
		this.matchTime.setValue(matchTime);
	}
	
	//----------------------------------------------
	// 設定監聽的方法
	
	public StringProperty symbolProperty() {
        return symbol;
    }
	
	public DoubleProperty lastPriceProperty() {
        return lastPrice;
    }
	
	public StringProperty matchTimeProperty() {
        return matchTime;
    }
	
}
