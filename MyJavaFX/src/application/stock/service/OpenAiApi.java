package application.stock.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import application.stock.mvc.model.StockAnalysis;

public class OpenAiApi {
	
	private Gson gson = new Gson();
	private final String promptFormat = "問題: 請問台灣股票代號(stockSymbol) %s.TW 的股票名稱(stockName)為何 ? 投資建議敘述(investmentAdvice)? 買進理由(buyingReason)? 賣出理由(sellingReason)?根據買進理由與賣出理由給予投資方向(investmentDirection)? 目標價格(targetPrice)?目標價格儘量找到符合市場的價格, 回答請你自由發想, 最後加入投資警語(investmentWarning). 請用 json 格式回答, json 屬性名稱請用我給的英文";
	private final  double temperature = 1; // 0(保守)~1(隨機)
	private final  String apiKey = "your api key";
	private final  String apiUrl = "https://api.openai.com/v1/completions";
	
	public static void main(String[] args) throws Exception {
		System.out.println(new OpenAiApi().getAnalysis("2330"));
	}
	
	public StockAnalysis getAnalysis(String symbol) throws Exception {
		String prompt = String.format(promptFormat, symbol); 

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(
                        "{\n" +
                        "  \"model\": \"text-davinci-003\",\n" +
                        "  \"prompt\": \"" + prompt + "\",\n" +
                        "  \"max_tokens\": 2000,\n" +
                        "  \"temperature\": " + temperature + "\n" +
                        "}"))
                .build();

        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
        String jsonString = response.body();
        System.out.println(jsonString);
        
        
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        
        String text = jsonObject.getAsJsonArray("choices")
                                .get(0).getAsJsonObject()
                                .get("text").getAsString();
        
        int totalTokens = jsonObject.getAsJsonObject("usage")
                                    .get("total_tokens").getAsInt();
        
        System.out.println("Text: " + text.replace("?", "").replace("\n", "").trim());
        System.out.println("Total Tokens: " + totalTokens);
        
        String s = text.replace("?", "").replace("\n", "").trim();
        
        Pattern pattern = Pattern.compile("\\{.*?\\}");
        Matcher matcher = pattern.matcher(s);

        if (matcher.find()) {
            String jsonstr = matcher.group(0);
        	System.out.println(jsonstr);
        	StockAnalysis stockAnalysis = new Gson().fromJson(jsonstr, StockAnalysis.class);
        	return stockAnalysis;
            
        } else {
            System.out.println("No JSON found.");
            return null;
        }
	}
}
