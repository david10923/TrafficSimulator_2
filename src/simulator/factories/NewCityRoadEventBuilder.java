package simulator.factories;

import javax.xml.crypto.Data;

import simulator.model.Event;
import simulator.model.NewCityRoadEvent;


public class NewCityRoadEventBuilder  extends NewRoadEventBuilder{

	private static String type="new_city_road";



	public NewCityRoadEventBuilder() {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheRoad() {
		
		Event e = new NewCityRoadEvent(this.time,this.id,this.src,this.dest,this.lenght,this.co2limit,this.maxspeed,this.weather);
		
		
		return e;
	}
	
	

}
