package application.stock.mvc.model;

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


