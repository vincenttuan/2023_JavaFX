package application.config;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesConfig {
	public final static Properties PROP = new Properties();
	
	static {
		String configFileName = "config.properties";
		try(InputStream input = new PropertiesConfig().getClass().getResourceAsStream("/application/config/" + configFileName);
			InputStreamReader read = new InputStreamReader(input, "UTF-8")) {
			PROP.load(read);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
