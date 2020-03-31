package simulator.factories;



import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.Weather;

public abstract class NewRoadEventBuilder extends Builder<Event>{
	
	protected int time ; 
	protected String id;
	protected String src; 
	protected String dest; 
	protected int lenght;
	protected int co2limit;
	protected int maxspeed;
	protected Weather weather;

	NewRoadEventBuilder(String type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		Event e;
		
		if(data.has("time") && data.has("id") && data.has("src") && data.has("dest") && data.has("length") 
				&& data.has("co2limit") && data.has("maxspeed") && data.has("weather")) {
			
			this.time  = data.getInt("time"); 
			this.id = data.getString("id");
			this.src = data.getString("src"); 
			this.dest = data.getString("dest");
			this.lenght = data.getInt("length");
			this.co2limit = data.getInt("co2limit");
			this.maxspeed = data.getInt("maxspeed"); 
			this.weather = 	Weather.getWeather(data.getString("weather"));
					
			
			e = createTheRoad();
			
		}
		else {
			return null;
		}
		
		return e;
	}

	protected abstract Event createTheRoad();
}
