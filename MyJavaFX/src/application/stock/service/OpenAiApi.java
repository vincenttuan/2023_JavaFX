package application.stock.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;
import java.util.Map;
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
		//System.out.println(new OpenAiApi().getAnalysis("2330"));
		System.out.println(new OpenAiApi().getAnalysisOffline("2330"));
	}
	
	public StockAnalysis getAnalysisOffline(String symbol) {
		return gson.fromJson(investOfflineMap.get(symbol), StockAnalysis.class);
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
	
	private static Map<String, String> investOfflineMap = new HashMap<>();
	static {
		investOfflineMap.put("2330", "{\r\n"
				+ "   \"stockSymbol\": \"2330.TW\",\r\n"
				+ "   \"stockName\": \"台積電\",\r\n"
				+ "   \"investmentAdvice\": \"在當前全球半導體市場中，台積電持續保持其領導地位。\",\r\n"
				+ "   \"buyingReason\": \"由於5G、AI和高性能計算的需求日益增加，對半導體的需求也相對增加。\",\r\n"
				+ "   \"sellingReason\": \"隨著全球經濟的不確定性和供應鏈的問題，可能會對短期業績造成壓力。\",\r\n"
				+ "   \"investmentDirection\": \"建議買進\",\r\n"
				+ "   \"targetPrice\": \"715.0\",\r\n"
				+ "   \"investmentWarning\": \"投資股票有相應的風險，一切投資決策需謹慎考慮。\"\r\n"
				+ "}\r\n"
				+ "");
		
		investOfflineMap.put("2317", "{\r\n"
				+ "   \"stockSymbol\": \"2317.TW\",\r\n"
				+ "   \"stockName\": \"鴻海\",\r\n"
				+ "   \"investmentAdvice\": \"鴻海作為全球最大的電子製造服務商，在全球供應鏈中佔有重要地位。\",\r\n"
				+ "   \"buyingReason\": \"由於全球消費電子需求的增加，鴻海可能受益於此。\",\r\n"
				+ "   \"sellingReason\": \"全球半導體短缺可能影響其生產能力。\",\r\n"
				+ "   \"investmentDirection\": \"建議買進\",\r\n"
				+ "   \"targetPrice\": \"105.0\",\r\n"
				+ "   \"investmentWarning\": \"投資股票有相應的風險，一切投資決策需謹慎考慮。\"\r\n"
				+ "}\r\n"
				+ "");
		
		investOfflineMap.put("0050", "{\r\n"
				+ "   \"stockSymbol\": \"0050.TW\",\r\n"
				+ "   \"stockName\": \"元大台灣50\",\r\n"
				+ "   \"investmentAdvice\": \"台灣50 ETF 是一個反映台灣股市大型股表現的投資工具，適合想要持有台灣大型股但又不想單一投資某家公司的投資者。\",\r\n"
				+ "   \"buyingReason\": \"全球經濟回溫，以及半導體需求強勁可能使得這些大型股獲利。\",\r\n"
				+ "   \"sellingReason\": \"全球經濟不確定性增加，以及半導體業超出供過於求的情況可能使得這ETF價格下滑。\",\r\n"
				+ "   \"investmentDirection\": \"觀望\",\r\n"
				+ "   \"targetPrice\": \"95.0\",\r\n"
				+ "   \"investmentWarning\": \"投資股票和ETF均有風險，過去的績效不代表未來的表現，請謹慎考慮您的投資決策。\"\r\n"
				+ "}\r\n"
				+ "");
		
		investOfflineMap.put("1101", "{\r\n"
				+ "   \"stockSymbol\": \"1101.TW\",\r\n"
				+ "   \"stockName\": \"台灣水泥\",\r\n"
				+ "   \"investmentAdvice\": \"台灣水泥在國內外都有優質的水泥製造技術和市場占有率。投資者若看好基礎建設和建築業的未來成長，可以考慮此股。\",\r\n"
				+ "   \"buyingReason\": \"由於全球都在重啟經濟，很多國家的基礎建設計劃和建築業都在復甦中，對水泥的需求可能會增加。\",\r\n"
				+ "   \"sellingReason\": \"水泥產業是高固定成本和資本密集型的產業，如果市場需求不如預期，可能會對獲利造成壓力。\",\r\n"
				+ "   \"investmentDirection\": \"觀望\",\r\n"
				+ "   \"targetPrice\": \"42.5\",\r\n"
				+ "   \"investmentWarning\": \"投資股票均有風險，過去的績效不代表未來的表現，請謹慎考慮您的投資決策。\"\r\n"
				+ "}\r\n"
				+ "");
		
		investOfflineMap.put("2344", "{\r\n"
				+ "   \"stockSymbol\": \"2344.TW\",\r\n"
				+ "   \"stockName\": \"華邦電子\",\r\n"
				+ "   \"investmentAdvice\": \"華邦電子是半導體業的關鍵玩家，尤其在DRAM和NAND Flash領域。若投資者看好半導體的未來成長，可以考慮此股。\",\r\n"
				+ "   \"buyingReason\": \"隨著5G、AI和IoT等技術的發展，對半導體的需求持續增加，華邦電子作為這些領域的供應商將直接受益。\",\r\n"
				+ "   \"sellingReason\": \"半導體產業週期性明顯，若遭遇供過於求或技術轉型時，可能會對公司獲利造成壓力。\",\r\n"
				+ "   \"investmentDirection\": \"建議買進\",\r\n"
				+ "   \"targetPrice\": \"38.0\",\r\n"
				+ "   \"investmentWarning\": \"投資股票均有風險，過去的績效不代表未來的表現，請謹慎考慮您的投資決策。\"\r\n"
				+ "}\r\n"
				+ "");
	}
	
}
