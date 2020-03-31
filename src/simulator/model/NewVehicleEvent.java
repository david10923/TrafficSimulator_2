package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event{
	
	//private int time; 
	private String id; 
	private int maxSpeed; 
	private int contClass; 
	private List<String> Itinerary; 
	
	

	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
		super(time);
		this.id = id; 
		this.maxSpeed = maxSpeed; 
		this.contClass = contClass; 
		this.Itinerary = itinerary; 
	}

	@Override
	void execute(RoadMap map) {
		String id;
		Junction j;
		List<Junction> junctionItinerary = new ArrayList<>();
		
		
		for(int i = 0;i< this.Itinerary.size();i++){			
			id = this.Itinerary.get(i);
			j = map.getJunction(id);
			junctionItinerary.add(i, j);	
			
		}		
		
		try {
			Vehicle v = new Vehicle(this.id,this.maxSpeed,this.contClass,junctionItinerary);
			map.addVehicle(v);
			v.moveToNextRoad(); 
		} catch (Exception e) {			
			e.getMessage();
		}
		
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
