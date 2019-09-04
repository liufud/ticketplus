package external;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TicketMasterClient {
	private static final String HOST = "https://app.ticketmaster.com";
	private static final String PATH = "/discovery/v2/events.json";
	private static final String DEFAULT_KEYWORD = "event";
	private static final int DEFAULT_RADIUS = 50;
	private static final String API_KEY = "D23Qk0sSJP9hPLOEQpgIh3Ph995H7btT";

	public JSONArray search(double lat, double lon, String keyword) {
		// Step 1: Build URL
		// Step 2: Open HTTP connection
		// Step 3: Read API input steam
		// Step 4: Parse input string to JSON
		// Step 5: Return JSON key value pairs needed
		if (keyword == null) {
			keyword = DEFAULT_KEYWORD;
		}

		try {
			URLEncoder.encode(keyword, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String query = String.format("apikey=%s&latlong=%s,%s&keyword=%s&radius=%s", API_KEY, lat, lon, keyword,
				DEFAULT_RADIUS);
		String url = HOST + PATH + "?" + query;

		StringBuilder response = new StringBuilder();
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setRequestMethod("GET");
			connection.connect();

			System.out.println("Sending request to: " + url);

			int responseCode = connection.getResponseCode();
			System.out.println("Response code: " + responseCode);

			if (responseCode != 200) {
				return new JSONArray();
			}

			// connection.getInputStream() returns the response body
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			reader.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			JSONObject obj = new JSONObject(response.toString());
			if (!obj.isNull("_embedded")) {
				JSONObject embeded = obj.getJSONObject("_embedded");
				return embeded.getJSONArray("events");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return new JSONArray();
	}

	public static void main(String[] args) {
		TicketMasterClient client = new TicketMasterClient();
		JSONArray events = client.search(37.38, -122.08, null);
		try {
			for (int i = 0; i < events.length(); ++i) {
				JSONObject event = events.getJSONObject(i);
				System.out.println(event.toString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
