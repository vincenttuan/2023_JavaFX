package application.stock.single;

import java.util.Arrays;
import java.util.List;

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
	private List<String> symnols = Arrays.asList("2344", "1101", "2330");
	
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
	}
	
	// 取得 tableview 所有項目的初始資料
	private ObservableList<StockInfo> getStockData() {
		ObservableList<StockInfo> stockInfos = FXCollections.observableArrayList();
		for(String symbol : symnols) {
			StockInfo stockInfo = new StockInfo();
			stockInfo.setSymbol(symbol);
			stockInfos.add(stockInfo);
		}
		return stockInfos;
	}
	
}
