package socket.model;

import java.util.Arrays;

/*
{
	"msgSeq":"4324318",
	"symbol":"2330  ",
	"market":null,
	"turnover":6600,
	"lastPrice":529.0,
	"lastVolume":1,
	"bidPrices":[529.0,528.0,527.0,526.0,525.0],
	"bidVolumes":[19,687,258,360,388],
	"askPrices":[530.0,531.0,532.0,533.0,534.0],
	"askVolumes":[859,471,787,650,599],
	"matchTime":"131539674237"
}
* */

public class StockInfo {
	private String symbol;
    private Double lastPrice;
    private String matchTime;
    
    // 加入5檔
    private Double[] bidPrices;
    private Integer[] bidVolumes;
    private Double[] askPrices;
    private Integer[] askVolumes;
    
    
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public Double getLastPrice() {
		return lastPrice;
	}
	public void setLastPrice(Double lastPrice) {
		this.lastPrice = lastPrice;
	}
	public String getMatchTime() {
		return matchTime;
	}
	
	public void setMatchTime(String matchTime) {
		this.matchTime = matchTime;
	}
	public Double[] getBidPrices() {
		return bidPrices;
	}
	public void setBidPrices(Double[] bidPrices) {
		this.bidPrices = bidPrices;
	}
	public Integer[] getBidVolumes() {
		return bidVolumes;
	}
	public void setBidVolumes(Integer[] bidVolumes) {
		this.bidVolumes = bidVolumes;
	}
	public Double[] getAskPrices() {
		return askPrices;
	}
	public void setAskPrices(Double[] askPrices) {
		this.askPrices = askPrices;
	}
	public Integer[] getAskVolumes() {
		return askVolumes;
	}
	public void setAskVolumes(Integer[] askVolumes) {
		this.askVolumes = askVolumes;
	}
	@Override
	public String toString() {
		return "StockInfo [symbol=" + symbol + ", lastPrice=" + lastPrice + ", matchTime=" + matchTime + ", bidPrices="
				+ Arrays.toString(bidPrices) + ", bidVolumes=" + Arrays.toString(bidVolumes) + ", askPrices="
				+ Arrays.toString(askPrices) + ", askVolumes=" + Arrays.toString(askVolumes) + "]";
	}
	
	

}
