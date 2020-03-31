package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder{

	private static String type= "new_vehicle";
	private int time; 
	
	private String id; 
	private int maxSpeed; 
	private int contClass; 
	private List<String> Itinerary; 
	
	private JSONArray JsonArray ;
	

	public NewVehicleEventBuilder() {
		super(type);
		
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		
		Event e ; 
		
		if((data.has("time") && data.has("id") && data.has("maxspeed") && data.has("class") && data.has("itinerary"))){
			this.time = data.getInt("time"); 
			this.id = data.getString("id");
			this.maxSpeed = data.getInt("maxspeed");
			this.contClass = data.getInt("class");
			
			JsonArray = data.getJSONArray("itinerary");			
			this.Itinerary = new ArrayList<>();
			
			for(int i = 0 ; i< JsonArray.length();i++) {
				this.Itinerary.add(JsonArray.getString(i));
				
			}
			e = new NewVehicleEvent(time, id, maxSpeed, contClass, Itinerary);
			return e;
		}
		else {
			return null;
		}
	}

	
	
	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public int getContClass() {
		return contClass;
	}

	public void setContClass(int contClass) {
		this.contClass = contClass;
	}

	public List<String> getItinerary() {
		return Itinerary;
	}

	public void setItinerary(List<String> itinerary) {
		Itinerary = itinerary;
	}
}
