package simulator.factories;

import simulator.model.Event;
import simulator.model.NewInterCityRoadEvent;

public class NewInterCityRoadEventBuilder extends NewRoadEventBuilder {

	private static String type = "new_inter_city_road";
	
	public NewInterCityRoadEventBuilder() {
		super(type);
		
	}

	@Override
	protected Event createTheRoad() {
		
		Event e = new NewInterCityRoadEvent(this.time,this.id,this.src,this.dest,this.lenght,this.co2limit,this.maxspeed,this.weather);
		
		return e ;
		
	}


}
