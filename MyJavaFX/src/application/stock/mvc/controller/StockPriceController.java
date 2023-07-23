package application.stock.mvc.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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

public class StockPriceController {
	
	@FXML private TableView<StockInfo> tableView;
	@FXML private TableColumn<StockInfo, String> symbolCol;
	@FXML private TableColumn<StockInfo, Double> lastPriceCol;
	@FXML private TableColumn<StockInfo, String> matchTimeCol;
	
	private List<String> symbols = Arrays.asList("2344", "1101", "2330");
	private ObservableList<StockInfo> stockInfos = FXCollections.observableArrayList();
	
	@FXML private void initialize() {
		
		symbolCol.setCellValueFactory(new PropertyValueFactory<>("symbol"));
		lastPriceCol.setCellValueFactory(new PropertyValueFactory<>("lastPrice"));
		matchTimeCol.setCellValueFactory(new PropertyValueFactory<>("matchTime"));
		
		tableView.setItems(getStockData());
		
		// 啟動一條執行緒, 來變更報價資料
		new Thread(() -> {
			while (true) {
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
					} catch (Exception e) {
					}
				}
			}
		}).start();
	}
	
	private ObservableList<StockInfo> getStockData() {
		symbols.forEach(symbol -> {
			StockInfo stockInfo = new StockInfo();
			stockInfo.setSymbol(symbol);
			stockInfos.add(stockInfo);
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












