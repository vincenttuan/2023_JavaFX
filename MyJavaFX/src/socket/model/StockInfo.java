package socket.model;

public class StockInfo {
	private String symbol;
    private Double lastPrice;
    private String matchTime;
    
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
	
	@Override
	public String toString() {
		return "StockInfo [symbol=" + symbol + ", lastPrice=" + lastPrice + ", matchTime=" + matchTime + "]";
	}

}
