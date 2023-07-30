package application;
	
import java.io.File;
import java.io.IOException;

import application.config.PropertiesConfig;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	private static final String flagFilePath = PropertiesConfig.PROP.get("flagFilePath")+"";
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		// 避免二次啟動檔案
		File flagFile = new File(flagFilePath);
		if(flagFile.exists()) {
			System.out.println("請勿重新啟動系統");
			System.exit(1);
			return;
		}
		flagFile.createNewFile();
		flagFile.deleteOnExit();
		launch(args);
	}
}
