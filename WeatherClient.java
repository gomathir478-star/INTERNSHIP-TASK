import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherClient {

    public static void main(String[] args) {

        try {
            String apiUrl =
                "https://api.open-meteo.com/v1/forecast?latitude=12.97&longitude=77.59&current_weather=true";

            // Create HTTP client
            HttpClient client = HttpClient.newHttpClient();

            // Create request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .GET()
                    .build();

            // Send request
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            // JSON response
            String json = response.body();

            // ---- Simple JSON parsing (no library) ----
            String temperature = extractValue(json, "temperature");
            String windspeed   = extractValue(json, "windspeed");
            String time        = extractValue(json, "time");

            // Display output
            System.out.println("===== WEATHER REPORT =====");
            System.out.println("Temperature : " + temperature + " °C");
            System.out.println("Wind Speed  : " + windspeed + " km/h");
            System.out.println("Time        : " + time);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to extract values from JSON string
    private static String extractValue(String json, String key) {
        int index = json.indexOf("\"" + key + "\"");
        if (index == -1) return "N/A";

        int colon = json.indexOf(":", index);
        int comma = json.indexOf(",", colon);

        if (comma == -1) {
            comma = json.indexOf("}", colon);
        }

        return json.substring(colon + 1, comma)
                   .replace("\"", "")
                   .trim();
    }
}
