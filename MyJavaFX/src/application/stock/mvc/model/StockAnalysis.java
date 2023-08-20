package application.stock.mvc.model;

/*

問題: 請問台灣股票代號(stockSymbol)2330.TW的股票名稱(stockName)為何 ? 投資建議敘述(investmentAdvice)? 買進理由(buyingReason)? 賣出理由(sellingReason)?根據買進理由與賣出理由給予投資方向(investmentDirection)?目標價格(targetPrice) ?各為多少,目標價格儘量找到符合市場的價格, 回答請你自由發想, 最後加入投資警語(investmentWarning)
並用 json 格式回答例如: 
{
   "stockSymbol": "2330", "stockName": "台積電", "investmentAdvice": "略..", "buyingReason":"...", "sellingReason":"...", "investmentDirection": "建議買進or建議賣出 or 觀望", "targetPrice": "700.0","investmentWarning": "..."
}
最後根據 json 建立一個 StockAnalysis 的 pojo 物件

 * */
public class StockAnalysis {

    private String stockSymbol;
    private String stockName;
    private String investmentAdvice;
    private String buyingReason;
    private String sellingReason;
    private String investmentDirection;
    private String targetPrice;
    private String investmentWarning;

    // Default constructor
    public StockAnalysis() {}

    // Parameterized constructor
    public StockAnalysis(String stockSymbol, String stockName, String investmentAdvice, String buyingReason, 
                         String sellingReason, String investmentDirection, String targetPrice, String investmentWarning) {
        this.stockSymbol = stockSymbol;
        this.stockName = stockName;
        this.investmentAdvice = investmentAdvice;
        this.buyingReason = buyingReason;
        this.sellingReason = sellingReason;
        this.investmentDirection = investmentDirection;
        this.targetPrice = targetPrice;
        this.investmentWarning = investmentWarning;
    }

    // Getter and Setter methods
    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getInvestmentAdvice() {
        return investmentAdvice;
    }

    public void setInvestmentAdvice(String investmentAdvice) {
        this.investmentAdvice = investmentAdvice;
    }

    public String getBuyingReason() {
        return buyingReason;
    }

    public void setBuyingReason(String buyingReason) {
        this.buyingReason = buyingReason;
    }

    public String getSellingReason() {
        return sellingReason;
    }

    public void setSellingReason(String sellingReason) {
        this.sellingReason = sellingReason;
    }

    public String getInvestmentDirection() {
        return investmentDirection;
    }

    public void setInvestmentDirection(String investmentDirection) {
        this.investmentDirection = investmentDirection;
    }

    public String getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(String targetPrice) {
        this.targetPrice = targetPrice;
    }

    public String getInvestmentWarning() {
        return investmentWarning;
    }

    public void setInvestmentWarning(String investmentWarning) {
        this.investmentWarning = investmentWarning;
    }

    @Override
    public String toString() {
        return "StockAnalysis [stockSymbol=" + stockSymbol + ", stockName=" + stockName + ", investmentAdvice=" + investmentAdvice
                + ", buyingReason=" + buyingReason + ", sellingReason=" + sellingReason + ", investmentDirection=" + investmentDirection
                + ", targetPrice=" + targetPrice + ", investmentWarning=" + investmentWarning + "]";
    }
}


