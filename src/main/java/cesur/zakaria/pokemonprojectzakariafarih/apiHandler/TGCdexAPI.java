package cesur.zakaria.pokemonprojectzakariafarih.apiHandler;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TGCdexAPI {
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public String sendGetRequest(String url) throws IOException, InterruptedException  {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    //Fetch list of all cards, call /v2/:lang/cards endpoint
    public String getAllCards(String lang) throws IOException, InterruptedException {
        return sendGetRequest("https://api.tgcdex.net/v2/" + lang + "/cards");
    }

    //Fetch a single card by its ID
    public String getSingleCard(String lang, String id) throws IOException, InterruptedException {
        return sendGetRequest("https://api.tgcdex.net/v2/" + lang + "/cards/" + id);
    }

    //Fetch a Sets list
    public String getSetsList(String lang) throws IOException, InterruptedException {
        String url = "https://api.tgcdex.net/v2/" + lang + "/sets";
        return sendGetRequest(url);
    }

    //Fetch List of cards by Field
    public String getCardsByField(String lang, String field, String fieldValue) throws IOException, InterruptedException {
        String url = "https://api.tgcdex.net/v2/" + lang + "/" + field + "/" + fieldValue;
        return sendGetRequest(url);
    }
}
