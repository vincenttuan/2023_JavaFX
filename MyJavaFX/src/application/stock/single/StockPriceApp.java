package application.stock.single;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class StockPriceApp extends Application {
	
	private TableView<StockInfo> tableView = new TableView<>();
	private List<String> symbols = Arrays.asList("2344", "1101", "2330");
	private ObservableList<StockInfo> stockInfos = FXCollections.observableArrayList();
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// 建立 TableColumn
		TableColumn<StockInfo, String> symbolCol = new TableColumn<>("symbol");
		symbolCol.setCellValueFactory(new PropertyValueFactory<>("symbol"));
		
		TableColumn<StockInfo, Double> lastPriceCol = new TableColumn<>("lastPrice");
		lastPriceCol.setCellValueFactory(new PropertyValueFactory<>("lastPrice"));
		
		TableColumn<StockInfo, String> matchTimeCol = new TableColumn<>("matchTime");
		matchTimeCol.setCellValueFactory(new PropertyValueFactory<>("matchTime"));
		
		// 配置 Columns
		tableView.getColumns().add(symbolCol);
		tableView.getColumns().add(lastPriceCol);
		tableView.getColumns().add(matchTimeCol);
		
		// 配置所有項目資料
		tableView.setItems(getStockData());
		
		// 建立 Scene
		Scene scene = new Scene(tableView, 800, 400);
		stage.setScene(scene);
		stage.show();
		
		// 啟動一條執行緒, 來變更報價資料
		new Thread(() -> {
			while (true) {
				for(int i=0;i<symbols.size();i++) {
					StockInfo stockInfo = stockInfos.get(i);
					// 更新報價
					stockInfo.setLastPrice(new Random().nextDouble(100));
					// 更新 tableview
					//tableView.refresh(); // 整頁更新效率最差
					// 更新該筆紀錄(Refresh 紀錄)
					stockInfos.set(i, stockInfo);
					try {
						Thread.sleep(10);
					} catch (Exception e) {
					}
				}
			}
		}).start();
	}
	
	// 取得 tableview 所有項目的初始資料
	private ObservableList<StockInfo> getStockData() {
		
		for(String symbol : symbols) {
			StockInfo stockInfo = new StockInfo();
			stockInfo.setSymbol(symbol);
			stockInfos.add(stockInfo);
		}
		return stockInfos;
	}
	
}
