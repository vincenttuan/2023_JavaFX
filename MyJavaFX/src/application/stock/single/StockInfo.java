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
	
	private StringProperty msgSeq = new SimpleStringProperty(this, "msgSeq", ""); // 序號 1~n (不會重複)
    private StringProperty symbol = new SimpleStringProperty(this, "symbol", ""); // 股票代號
    private StringProperty market = new SimpleStringProperty(this, "market", ""); // 市場
    private IntegerProperty turnover = new SimpleIntegerProperty(this, "turnover", 0); // 總量
    private DoubleProperty lastPrice = new SimpleDoubleProperty(this, "lastPrice", 0.0); // 最新成交價
    private IntegerProperty lastVolume = new SimpleIntegerProperty(this, "lastVolume", 0); // 最新成交量
    private ObservableList<Double> bidPrices = FXCollections.observableArrayList(); // 最新五檔買價
    private ObservableList<Integer> bidVolumes = FXCollections.observableArrayList(); // 最新五檔買量
    private ObservableList<Double> askPrices = FXCollections.observableArrayList(); // 最新五檔賣價
    private ObservableList<Integer> askVolumes = FXCollections.observableArrayList(); // 最新五檔賣量
    private StringProperty matchTime = new SimpleStringProperty(this, "matchTime", ""); // 成交時間
    
	public String getMsgSeq() {
		return msgSeq.get();
	}
	public void setMsgSeq(String msgSeq) {
		this.msgSeq.set(msgSeq);
	}
	public String getSymbol() {
		return symbol.get();
	}
	public void setSymbol(String symbol) {
		this.symbol.set(symbol);
	}
	public String getMarket() {
		return market.get();
	}
	public void setMarket(String market) {
		this.market.set(market);
	}
	public Integer getTurnover() {
		return turnover.get();
	}
	public void setTurnover(Integer turnover) {
		this.turnover.set(turnover);
	}
	public Double getLastPrice() {
		return lastPrice.get();
	}
	public void setLastPrice(Double lastPrice) {
		this.lastPrice.set(lastPrice);
	}
	public Integer getLastVolume() {
		return lastVolume.get();
	}
	public void setLastVolume(Integer lastVolume) {
		this.lastVolume.set(lastVolume);
	}
	public ObservableList<Double> getBidPrices() {
		return bidPrices;
	}
	public void setBidPrices(List<Double> bidPrices) {
		this.bidPrices.setAll(bidPrices);
	}
	public ObservableList<Integer> getBidVolumes() {
		return bidVolumes;
	}
	public void setBidVolumes(List<Integer> bidVolumes) {
		this.bidVolumes.setAll(bidVolumes);
	}
	public ObservableList<Double> getAskPrices() {
		return askPrices;
	}
	public void setAskPrices(List<Double> askPrices) {
		this.askPrices.setAll(askPrices);
	}
	public ObservableList<Integer> getAskVolumes() {
		return askVolumes;
	}
	public void setAskVolumes(List<Integer> askVolumes) {
		this.askVolumes.setAll(askVolumes);
	}
	public String getMatchTime() {
		return matchTime.get();
	}
	public void setMatchTime(String matchTime) {
		this.matchTime.set(matchTime);
	}
	
	@Override
	public String toString() {
		return "StockInfo [msgSeq=" + msgSeq + ", symbol=" + symbol + ", market=" + market + ", turnover=" + turnover
				+ ", lastPrice=" + lastPrice + ", lastVolume=" + lastVolume + ", bidPrices=" + bidPrices
				+ ", bidVolumes=" + bidVolumes + ", askPrices=" + askPrices + ", askVolumes=" + askVolumes
				+ ", matchTime=" + matchTime + "]";
	}

    
	
	
	
}
