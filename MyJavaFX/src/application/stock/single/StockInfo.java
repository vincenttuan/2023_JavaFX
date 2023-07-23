package application.stock.single;

import java.util.List;

// 報價資料
// json: {"msgSeq":"4400854","symbol":"00650L","market":null,"turnover":3060,"lastPrice":null,"lastVolume":null,"bidPrices":[14.600,14.500,14.400,14.200,14.100],"bidVolumes":[61,25,215,349,329],"askPrices":[14.1000,14.1100,14.1200,14.1300,14.1400],"askVolumes":[81,115,70,47,489],"matchTime":"132015439437"},
public class StockInfo {
	private String msgSeq; // 序號 1~n (不會重複)
	private String symbol; // 股票代號
	private String market; // 市場
	private Integer turnover; // 總量
	private Double lastPrice; // 最新成交價
	private Integer lastVolume; // 最新成交量
	private List<Double> bidPrices; // 最新五檔買價
	private List<Integer> bidVolumes; // 最新五檔買量
	private List<Double> askPrices; // 最新五檔賣價
	private List<Integer> askVolumes; // 最新五檔賣量
	private String matchTime; // 成交時間
	public String getMsgSeq() {
		return msgSeq;
	}
	public void setMsgSeq(String msgSeq) {
		this.msgSeq = msgSeq;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public Integer getTurnover() {
		return turnover;
	}
	public void setTurnover(Integer turnover) {
		this.turnover = turnover;
	}
	public Double getLastPrice() {
		return lastPrice;
	}
	public void setLastPrice(Double lastPrice) {
		this.lastPrice = lastPrice;
	}
	public Integer getLastVolume() {
		return lastVolume;
	}
	public void setLastVolume(Integer lastVolume) {
		this.lastVolume = lastVolume;
	}
	public List<Double> getBidPrices() {
		return bidPrices;
	}
	public void setBidPrices(List<Double> bidPrices) {
		this.bidPrices = bidPrices;
	}
	public List<Integer> getBidVolumes() {
		return bidVolumes;
	}
	public void setBidVolumes(List<Integer> bidVolumes) {
		this.bidVolumes = bidVolumes;
	}
	public List<Double> getAskPrices() {
		return askPrices;
	}
	public void setAskPrices(List<Double> askPrices) {
		this.askPrices = askPrices;
	}
	public List<Integer> getAskVolumes() {
		return askVolumes;
	}
	public void setAskVolumes(List<Integer> askVolumes) {
		this.askVolumes = askVolumes;
	}
	public String getMatchTime() {
		return matchTime;
	}
	public void setMatchTime(String matchTime) {
		this.matchTime = matchTime;
	}
	@Override
	public String toString() {
		return "StockInfo [msgSeq=" + msgSeq + ", symbol=" + symbol + ", market=" + market + ", turnover=" + turnover
				+ ", lastPrice=" + lastPrice + ", lastVolume=" + lastVolume + ", bidPrices=" + bidPrices
				+ ", bidVolumes=" + bidVolumes + ", askPrices=" + askPrices + ", askVolumes=" + askVolumes
				+ ", matchTime=" + matchTime + "]";
	}
	
	
}
