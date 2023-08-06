package application.stock.mvc;


import application.stock.mvc.controller.StockPriceController;
import javafx.application.Application;
import javafx.stage.Stage;

public class StockPriceApp extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// 呼叫 controller 的 start() 方法
		StockPriceController controller = new StockPriceController();
		controller.start(stage);
		
		
	}
	
	
	
}
