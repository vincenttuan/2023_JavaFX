package application.stock.mvc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import application.stock.mvc.model.StockInfo;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import repository.Data;

public class StockPriceController {
	
	@FXML private TextField symbolInput;
	
	@FXML private TableView<StockInfo> tableView;
	@FXML private TableColumn<StockInfo, String> symbolCol;
	@FXML private TableColumn<StockInfo, Double> lastPriceCol;
	@FXML private TableColumn<StockInfo, String> matchTimeCol;
	
	@FXML private TableView<ObservableList<Object>> fivetableView;
	@FXML private TableColumn<ObservableList<Object>, Integer> bidVolumeCol;
	@FXML private TableColumn<ObservableList<Object>, Double> bidPriceCol;
	@FXML private TableColumn<ObservableList<Object>, Double> askPriceCol;
	@FXML private TableColumn<ObservableList<Object>, Integer> askVolumeCol;
	
	private List<String> symbols = new CopyOnWriteArrayList<String>(Arrays.asList("2344", "1101", "2330"));
	// 建構一個 Map 根據 symbol 找到指定 StockInfo
	private ConcurrentHashMap<String, StockInfo> stockInfoMap = new ConcurrentHashMap<>();
	
	private ObservableList<StockInfo> stockInfos = FXCollections.observableArrayList();
	private ExecutorService executorService = Executors.newSingleThreadExecutor();
	
	@FXML private void initialize() {
		
		// 設定股票代號輸入欄位按下 enter 要做的事
		symbolInput.setOnAction(event -> {
			String newSymbol = symbolInput.getText().trim();
			if(!newSymbol.isEmpty() && !symbols.contains(newSymbol)) {
				symbols.add(newSymbol);
				StockInfo stockInfo = new StockInfo();
				stockInfo.setSymbol(newSymbol);
				stockInfos.add(stockInfo);
				stockInfoMap.put(newSymbol, stockInfo);
			}
			symbolInput.clear(); // 清空輸入框
		});
		
		// 增加右鍵選單
		ContextMenu contextMenu = new ContextMenu();
		MenuItem deleteItem = new MenuItem("Delete");
		contextMenu.getItems().add(deleteItem);
		
		// 將右鍵選單設定給 tableview
		tableView.setContextMenu(contextMenu);
		
		// 為 "Delete" 選項設定事件
		deleteItem.setOnAction(event -> {
			StockInfo selectedStockInfo = tableView.getSelectionModel().getSelectedItem();
			if(selectedStockInfo != null) {
				// 從 symbols 刪除
				symbols.remove(selectedStockInfo.getSymbol());
				// 從 stockInfoMap 刪除
				stockInfoMap.remove(selectedStockInfo.getSymbol());
				// 從 tableview 刪除
				tableView.getItems().remove(selectedStockInfo);
			}
		});
		
		// 設定 tableview
		symbolCol.setCellValueFactory(new PropertyValueFactory<>("symbol"));
		lastPriceCol.setCellValueFactory(new PropertyValueFactory<>("lastPrice"));
		matchTimeCol.setCellValueFactory(new PropertyValueFactory<>("matchTime"));
		
		tableView.setItems(getStockData());
		
		// 設定 fivetableView
		bidPriceCol.setCellValueFactory(data -> {
			ObservableList<Object> row = data.getValue();
			return new SimpleObjectProperty<>((Double)row.get(0));
		});
		
		bidVolumeCol.setCellValueFactory(data -> {
			ObservableList<Object> row = data.getValue();
			return new SimpleObjectProperty<>((Integer)row.get(1));
		});
		
		askPriceCol.setCellValueFactory(data -> {
			ObservableList<Object> row = data.getValue();
			return new SimpleObjectProperty<>((Double)row.get(2));
		});
		
		askVolumeCol.setCellValueFactory(data -> {
			ObservableList<Object> row = data.getValue();
			return new SimpleObjectProperty<>((Integer)row.get(3));
		});
		
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
							// 注入五檔
							stockInfo.setBidPrices(lastStockInfo.getBidPrices());
							stockInfo.setBidVolumes(lastStockInfo.getBidVolumes());
							stockInfo.setAskPrices(lastStockInfo.getAskPrices());
							stockInfo.setAskVolumes(lastStockInfo.getAskVolumes());
							
							// 更新 fiveTableView
							updateFiveTableView(stockInfo);
							//System.out.println(stockInfo);
					   });
				
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt(); // 恢復中斷
					break; // 跳出循環
				}
			}
		});
		
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
	
	// 更新 fiveTableView 的數據邏輯
	private void updateFiveTableView(StockInfo stockInfo) {
		// 檢查事是否是關注的 symbol
		// tableView.getSelectionModel().getSelectedItem().getSymbol() 得到目前點選紀錄的股票代號
		if(!stockInfo.getSymbol().equals(tableView.getSelectionModel().getSelectedItem().getSymbol())) {
			return;
		}
		
		// 清除 fiveTableView 的數據
		fivetableView.getItems().clear();
		
		// 資料布局
		try {
			// 添加 askPrices 和 askVolumes 到 0~4 列
			for(int i=0;i<5;i++) {
				ObservableList<Object> dataRow = FXCollections.observableArrayList();
				dataRow.add(null); // bid 數量
				dataRow.add(null); // bid 價格
				dataRow.add(stockInfo.getAskPrices().get(i)); // ask 價格
				dataRow.add(stockInfo.getAskVolumes().get(i)); // ask 數量
				// 將 dataRow 加入到 fiveTableView
				fivetableView.getItems().add(dataRow);
			}
			
			// 添加 bidPrices 和 bidVolumes 到 5~9 列
			for(int i=0;i<5;i++) {
				ObservableList<Object> dataRow = FXCollections.observableArrayList();
				dataRow.add(stockInfo.getBidVolumes().get(i)); // bid 數量
				dataRow.add(stockInfo.getBidPrices().get(i)); // bid 價格
				dataRow.add(null); // ask 價格
				dataRow.add(null); // ask 數量
				// 將 dataRow 加入到 fiveTableView
				fivetableView.getItems().add(dataRow);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
}












