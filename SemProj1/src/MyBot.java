import java.io.*;
import java.util.regex.*;
import org.apache.commons.lang3.*;
import org.jibble.pircbot.*;
import com.google.gson.*;

public class MyBot extends PircBot {
    private String city, country;
    private api_weatherTempature api_weather;
    private api_topTracksGeo api_TTG;

   
	public MyBot() {
        this.setName("HeejaeBot");
    }
    
    //method that reads message on the chat
    public void onMessage(String channel, String sender,
                       String login, String hostname, String message) {
        
        if (message.equalsIgnoreCase("Hello") || message.equalsIgnoreCase("Hi")) {
        	sendMessage(channel, "Hello " + sender + "! You can ask me a question about the weather or top 5 tracks in a country!");
        }
        
        
        message = message.trim(); //cuts off excessive spaces of the last word
        //Get exact string of country, city, or zipcode
        Pattern weather = Pattern.compile("What is the weather in (.*)", Pattern.CASE_INSENSITIVE);
        Pattern songsCountry = Pattern.compile("What are the top tracks in (.*)", Pattern.CASE_INSENSITIVE);
        
        Matcher matchWeather = weather.matcher(message);
        Matcher matchSongs = songsCountry.matcher(message);

     
        if (matchWeather.find()) {
        	city = matchWeather.group(1);
        	try {
        		if (StringUtils.isNumeric(matchWeather+"")) {    // checks if its a string of letters for city or digits for zip code
        			api_weather.setZip(true);
        		}
        		api_weather = new api_weatherTempature(city);
        		String tempature = api_weather.parseJsonObject(city);
        		sendMessage(channel, sender + ", the weather in " + city + " is " + tempature);
        	}
        	catch(RuntimeException e){
        		sendMessage(channel, sender + ", sorry that is not the proper input. Try 'What is the weather in city/zipcode");
        		e.printStackTrace();
        	}
        }
        else if (matchSongs.find()) {
        	country = matchSongs.group(1);
        	try {
        		api_TTG = new api_topTracksGeo(country);
        		JsonObject[] songsArr = api_TTG.accessArray();
        		//JsonObject[] artistsArr = api_TTG.accessArray();
        		sendMessage(channel, sender + ", The top 5 tracks in " + country + " are:");
        		for (int i = 0; i < 5; i++) {
        			sendMessage(channel, "Track: "  + songsArr[i].get("name").getAsString());
        		//	sendMessage(channel, "By Artist: " + artistsArr[i].get("name").getAsString());
        			sendMessage(channel, "\n");
        		}
        	}
        	catch(RuntimeException e) {
        		sendMessage(channel, sender + ", sorry that is not a proper input. 'Try What are the top tracks in 'country'");
        		e.printStackTrace();
        	}
        }
      //  else
        //	sendMessage(channel, sender + "No match. Try the format 'What is the weather in' or 'What are the top tracks in '.");

    }
}
