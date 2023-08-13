package application.stock.mvc.model;

import java.util.List;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;

// 報價資料
// json: {"msgSeq":"4400854","symbol":"00650L","market":null,"turnover":3060,"lastPrice":null,"lastVolume":null,"bidPrices":[14.600,14.500,14.400,14.200,14.100],"bidVolumes":[61,25,215,349,329],"askPrices":[14.1000,14.1100,14.1200,14.1300,14.1400],"askVolumes":[81,115,70,47,489],"matchTime":"132015439437"},
public class StockInfo {
	
	private StringProperty symbol = new SimpleStringProperty(); // 股票代號
    private DoubleProperty lastPrice = new SimpleDoubleProperty(); // 最新成交價
    private StringProperty matchTime = new SimpleStringProperty(); // 成交時間
    
    private ObservableList<Double> bidPrices = FXCollections.observableArrayList();
    private ObservableList<Integer> bidVolumes = FXCollections.observableArrayList();
    private ObservableList<Double> askPrices = FXCollections.observableArrayList();
    private ObservableList<Integer> askVolumes = FXCollections.observableArrayList();
    
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
	
	// 為 ObservableList 提供 getter / setter 
	public ObservableList<Double> getBidPrices() {
		return bidPrices;
	}
	public void setBidPrices(ObservableList<Double> bidPrices) {
		this.bidPrices = bidPrices;
	}
	public void setBidPrices(Double[] bidPricesArray) {
		this.bidPrices.clear(); // 清除目前的數據
		this.bidPrices.addAll(bidPricesArray); // 添加新的數據
	}
	
	public ObservableList<Integer> getBidVolumes() {
		return bidVolumes;
	}
	public void setBidVolumes(ObservableList<Integer> bidVolumes) {
		this.bidVolumes = bidVolumes;
	}
	public void setBidVolumes(Integer[] bidVolumesArray) {
		this.bidVolumes.clear(); // 清除目前的數據
		this.bidVolumes.addAll(bidVolumesArray); // 添加新的數據
	}
	
	public ObservableList<Double> getAskPrices() {
		return askPrices;
	}
	public void setAskPrices(ObservableList<Double> askPrices) {
		this.askPrices = askPrices;
	}
	public void setAskPrices(Double[] askPricesArray) {
		this.askPrices.clear(); // 清除目前的數據
		this.askPrices.addAll(askPricesArray); // 添加新的數據
	}
	
	public ObservableList<Integer> getAskVolumes() {
		return askVolumes;
	}
	public void setAskVolumes(ObservableList<Integer> askVolumes) {
		this.askVolumes = askVolumes;
	}
	public void setAskVolumes(Integer[] askVolumesArrays) {
		this.askVolumes.clear(); // 清除目前的數據
		this.askVolumes.addAll(askVolumesArrays); // 添加新的數據
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
