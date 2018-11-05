import java.io.*;
import java.net.*;
import java.util.*;
import com.google.gson.*;

public class api_topTracksGeo {

	private URL url;
	private String country;
	private JsonObject[] ArrTopTracks;
	private JsonObject [] ArrTopTrackArtists;
	
	api_topTracksGeo() throws Exception{		
		String apiURL = "http://ws.audioscrobbler.com/2.0/?method=geo.gettoptracks&country=";
		String apiKEY = "e4a3b7c4be5a6520fa62fc041848c73f";
		String lastFmURL = apiURL + country + "&limit=5&api_key=" + apiKEY + "&format=json";
		
		try {
			url = new URL(lastFmURL);
		}
		catch (MalformedURLException e) {
			System.err.println("URL failed. Exception thrown: " + e.getMessage());
		}
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		
		BufferedReader buffread = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		
		String inputLines;
		StringBuffer lines = new StringBuffer();
		
		while((inputLines = buffread.readLine()) != null) {
			lines.append(inputLines);
		}
		
		String data = lines.toString();
		
		
		ArrTopTracks = parseJsonFunction(data); // 
	}

	public api_topTracksGeo(String country2) {
		country = country2;
	}

	private static JsonObject [] parseJsonFunction(String data) {
		JsonArray TrackArrays = (JsonArray) new JsonParser().parse(data).getAsJsonObject().get("tracks").getAsJsonArray();
		JsonObject [] Tracks = new JsonObject[5];
		for (int i=0; i<5; i++) {
			Tracks[i] = new JsonParser().parse(TrackArrays.get(i)+"").getAsJsonObject();
		}
		return Tracks;
	}


	public JsonObject[] accessArray() {
		return ArrTopTracks;
	}
}
