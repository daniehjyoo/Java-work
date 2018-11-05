import java.io.*;
import java.net.*;
import java.util.*;
import com.google.gson.*;

// city name and zip code must be valid inputs

public class api_weatherTempature {

	private URL url;
	private static JsonObject weather;
	private static String cityName;
	private static String tempature;
	private boolean zip;
	
	api_weatherTempature() throws Exception{
		
		//uses WeatherUnlocked as source for API
		String weatherapiURL = "https://api.openweathermap.org/data/2.5/weather?"; 
		String weatherKEY = "47440f47a4c1792110cfaa72e752cd03";
		String weatherURL;
		// api.openweathermap.org/data/2.5/weather?zip={zip code},{country code}
		// api.openweathermap.org/data/2.5/weather?q={city name}

		if (zip) {
			 weatherURL = weatherapiURL + "zip=" + cityName + "&units=celcius&appid=" + weatherKEY;
		}
		else
			 weatherURL = weatherapiURL + "q=" + cityName + "&units=celcius&appid=" + weatherKEY;

		try {
			url = new URL(weatherURL);
		}
		catch(MalformedURLException e) {
			System.err.println("URL failed. Exception: " + e.getMessage());
		}
		
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		
		BufferedReader buffread = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		
		String inputLine;
		StringBuffer lines = new StringBuffer();
		
		while((inputLine = buffread.readLine()) != null) {
			lines.append(inputLine);
		}
		String data = lines.toString();
		
		tempature = parseJsonObject(data);
	}

	public api_weatherTempature(String city) {
		cityName = city;
	}

	public String parseJsonObject(String data) {
		JsonObject weatherObj = new JsonParser().parse(data).getAsJsonObject();
		JsonObject main = weatherObj.getAsJsonObject("main");
		double temp = main.get("temp").getAsDouble();
		return temp+"";
	}

	public void setZip(boolean b) {
		zip = b;
	}
	
}