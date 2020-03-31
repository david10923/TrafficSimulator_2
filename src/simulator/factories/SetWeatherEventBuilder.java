package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import Exceptions.InvalidArgumentException;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder  extends Builder<Event>{

	private static String type = "set_weather";
	
	private int time ; 
	private List<Pair<String,Weather>> ws;
	
	private JSONArray JsonArray ;
	


	public SetWeatherEventBuilder() {
		super(type);
		
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		
		Event e = null ;
		
		if(data.has("time") && data.has("info")) {
			
			this.time = data.getInt("time");
			
			JsonArray  = data.getJSONArray("info");
			
			this.ws = new ArrayList<Pair<String,Weather>>();
			
			for(int i= 0 ; i< JsonArray.length();i++) {
				JSONObject pair = JsonArray.getJSONObject(i);
				this.ws.add(new Pair<String,Weather>(pair.getString("road"),Weather.getWeather(pair.getString("weather"))));
			}
			
			 try {
				e = new SetWeatherEvent(this.time ,this.ws);
			} catch (InvalidArgumentException e1) {
				// TODO Auto-generated catch block
				e1.getMessage();
			}
			
		}
		
		
		return e;
		
	}

}
