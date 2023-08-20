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

import application.stock.mvc.model.StockAnalysis;
import application.stock.mvc.model.StockInfo;
import application.stock.service.OpenAiApi;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
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
	
	@FXML private Label stockSymbolLabel;
	@FXML private Label stockNameLabel;
	@FXML private Label investmentAdviceLabel;
	@FXML private Label buyingReasonLabel;
	@FXML private Label sellingReasonLabel;
	@FXML private Label investmentDirectionLabel;
	@FXML private Label targetPriceLabel;
	@FXML private Label investmentWarningLabel;
	
	
	private List<String> symbols = new CopyOnWriteArrayList<String>(Arrays.asList("2344", "1101", "2330"));
	// 建構一個 Map 根據 symbol 找到指定 StockInfo
	private ConcurrentHashMap<String, StockInfo> stockInfoMap = new ConcurrentHashMap<>();
	
	private ObservableList<StockInfo> stockInfos = FXCollections.observableArrayList();
	private ExecutorService executorService = Executors.newSingleThreadExecutor();
	private ExecutorService executorAIService = Executors.newSingleThreadExecutor();
	
	private OpenAiApi openAiApi = new OpenAiApi();
	// 目前選到的 symbol
	private	String currentSymbol;
		
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
		bidVolumeCol.setCellValueFactory(data -> {
			ObservableList<Object> row = data.getValue();
			if(row == null) {
				return new SimpleObjectProperty<>(0);
			}	
			return new SimpleObjectProperty<>((Integer)row.get(1));
			
		});
		
		bidPriceCol.setCellValueFactory(data -> {
			ObservableList<Object> row = data.getValue();
			return new SimpleObjectProperty<>((Double)row.get(0));
		});
		
		
		askPriceCol.setCellValueFactory(data -> {
			ObservableList<Object> row = data.getValue();
			return new SimpleObjectProperty<>((Double)row.get(2));
		});
		
		askVolumeCol.setCellValueFactory(data -> {
			ObservableList<Object> row = data.getValue();
			return new SimpleObjectProperty<>((Integer)row.get(3));
		});
		
		// 在 tableview 中按下左鍵紀錄關注 symbol 並更新五檔, 股票分析資料
		tableView.setOnMouseClicked(event -> {
	        if (event.getButton() == MouseButton.PRIMARY && tableView.getSelectionModel().getSelectedItem() != null) {
	        	StockInfo stockInfo = tableView.getSelectionModel().getSelectedItem();
	        	// 保存目前所點選的 symbol
	        	currentSymbol = stockInfo.getSymbol();
	        	// 更新五檔
	            updateFiveTableView(stockInfo);
	            // 更新股票分析資料
	            updateStockAnalysis();
	        }
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
	
	// 更新股票分析資料
	private void updateStockAnalysis() {
		// 啟動執行緒來抓取分析資料
		executorAIService.submit(() -> {
			try {
				StockAnalysis stockAnalysis = openAiApi.getAnalysisOffline(currentSymbol);
				if(stockAnalysis == null) return;
				// 在執行緒上變更 FX UI Thread (FX application thread) 要透過 Platform.runLater()
				Platform.runLater(() -> {
					stockSymbolLabel.setText(stockAnalysis.getStockSymbol());
					stockNameLabel.setText(stockAnalysis.getStockName());
					investmentAdviceLabel.setText(stockAnalysis.getInvestmentAdvice());
					buyingReasonLabel.setText(stockAnalysis.getBuyingReason());
					sellingReasonLabel.setText(stockAnalysis.getSellingReason());
					investmentDirectionLabel.setText(stockAnalysis.getInvestmentDirection());
					targetPriceLabel.setText(stockAnalysis.getTargetPrice());
					investmentWarningLabel.setText(stockAnalysis.getInvestmentWarning());
				});
				
			} catch (Exception e) {
				e.printStackTrace();
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
		if (!stockInfo.getSymbol().equals(currentSymbol)) {
	        return;
	    }
		
		//System.out.println("updateFiveTableView: " + stockInfo);
		// 清除 fiveTableView 的數據
		fivetableView.getItems().clear();
		
		// 資料布局
		try {
	    	// 添加 askPrices 和 askVolumes 到行0-4
		    for (int i = 0; i < 5; i++) {
		        ObservableList<Object> dataRow = FXCollections.observableArrayList();

		        // 為bidPrices和bidVolumes添加空白值

		        dataRow.add(null);
		        dataRow.add(null);

		        // 如果askPrices和askVolumes存在資料，則添加到行（從末尾開始），否則添加null
		        int reversedIndex = stockInfo.getAskPrices().size() - 1 - i;
		        dataRow.add(reversedIndex >= 0 ? stockInfo.getAskPrices().get(reversedIndex) : null);
		        dataRow.add(reversedIndex >= 0 ? stockInfo.getAskVolumes().get(reversedIndex) : null);
		        
		        fivetableView.getItems().add(dataRow);
		    }

		    // 添加 bidPrices 和 bidVolumes 到行5-9
		    for (int i = 0; i < 5; i++) {
		        ObservableList<Object> dataRow = FXCollections.observableArrayList();
		        
		        // 如果bidPrices和bidVolumes存在資料，則添加到行，否則添加null
		        dataRow.add(stockInfo.getBidPrices().size() > i ? stockInfo.getBidPrices().get(i) : null);
		        dataRow.add(stockInfo.getBidVolumes().size() > i ? stockInfo.getBidVolumes().get(i) : null);

		        // 為askPrices和askVolumes添加空白值
		        dataRow.add(null);
		        dataRow.add(null);

		        fivetableView.getItems().add(dataRow);
		    }
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
}












