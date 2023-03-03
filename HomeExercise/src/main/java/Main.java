import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
public class Main {
    public static void main(String[] args) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://api.nbp.pl/api/exchangerates/rates/a/GBP/")).build();

        double rate = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(Main::parse)
                .join();

        new UserInterface(rate);
    }
    public static double parse(String responseBody){
        JSONObject currency = new JSONObject(responseBody);
        return currency.getJSONArray("rates").getJSONObject(0).getDouble("mid");
    }
}