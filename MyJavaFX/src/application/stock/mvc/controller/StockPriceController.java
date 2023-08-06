package application.stock.mvc.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import application.stock.mvc.model.StockInfo;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import repository.Data;

public class StockPriceController {
	
	@FXML private TableView<StockInfo> tableView;
	@FXML private TableColumn<StockInfo, String> symbolCol;
	@FXML private TableColumn<StockInfo, Double> lastPriceCol;
	@FXML private TableColumn<StockInfo, String> matchTimeCol;
	
	private List<String> symbols = Arrays.asList("2344", "1101", "2330");
	// 建構一個 Map 根據 symbol 找到指定 StockInfo
	private ConcurrentHashMap<String, StockInfo> stockInfoMap = new ConcurrentHashMap<>();
	
	private ObservableList<StockInfo> stockInfos = FXCollections.observableArrayList();
	private ExecutorService executorService = Executors.newSingleThreadExecutor();
	
	@FXML private void initialize() {
		
		symbolCol.setCellValueFactory(new PropertyValueFactory<>("symbol"));
		lastPriceCol.setCellValueFactory(new PropertyValueFactory<>("lastPrice"));
		matchTimeCol.setCellValueFactory(new PropertyValueFactory<>("matchTime"));
		
		tableView.setItems(getStockData());
		
		// 啟動一條執行緒, 來變更報價資料
		executorService.submit(() -> {
			while (!Thread.currentThread().isInterrupted()) {
				// 抓取最新報價
				symbols.parallelStream()
					   .forEach(symbol -> {
						   StockInfo lastStockInfo = Data.getInstance().quote.getLastStockInfo(symbol);
							if(lastStockInfo == null) return;
							StockInfo stockInfo = stockInfoMap.get(lastStockInfo.getSymbol());
							if(stockInfo == null) return;
							stockInfo.setSymbol(lastStockInfo.getSymbol());
							stockInfo.setLastPrice(lastStockInfo.getLastPrice());
							stockInfo.setMatchTime(lastStockInfo.getMatchTime());
					   });
				/*
				for(String symbol : symbols) {
					StockInfo lastStockInfo = Data.getInstance().quote.getLastStockInfo(symbol);
					if(lastStockInfo == null) continue;
					StockInfo stockInfo = stockInfoMap.get(lastStockInfo.getSymbol());
					if(stockInfo == null) continue;
					stockInfo.setSymbol(lastStockInfo.getSymbol());
					stockInfo.setLastPrice(lastStockInfo.getLastPrice());
					stockInfo.setMatchTime(lastStockInfo.getMatchTime());
				}
				*/
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt(); // 恢復中斷
					break; // 跳出循環
				}
			}
		});
		/*
		executorService.submit(() -> {
			while (!Thread.currentThread().isInterrupted()) {
				for(int i=0;i<symbols.size();i++) {
					StockInfo stockInfo = stockInfos.get(i);
					
					// 建議在 JavaFX 的執行緒中更新 UI
					Platform.runLater(() -> {
						// 更新報價
						stockInfo.setLastPrice(new Random().nextDouble(100));
						// 更新時間
						stockInfo.setMatchTime("08:59:" + new Random().nextInt(60));
					});
					
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt(); // 恢復中斷
						break; // 跳出循環
					}
				}
			}
		});
		*/
	}
	
	// 當需要停止的時候呼叫此方法
	public void shutdown() {
		executorService.shutdownNow(); // 立即停止服務與其中正在執行的的執行緒
	}
	
	private ObservableList<StockInfo> getStockData() {
		symbols.forEach(symbol -> {
			StockInfo stockInfo = new StockInfo();
			stockInfo.setSymbol(symbol);
			stockInfos.add(stockInfo);
			stockInfoMap.put(symbol, stockInfo);
		});
		
		return stockInfos;
	}
	
	public void start(Stage stage) throws IOException {
		// 載入 fxml
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/StockPriceView.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root, 800, 400);
		// 設定 css
		scene.getStylesheets().add(getClass().getResource("../css/stock.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	
}












