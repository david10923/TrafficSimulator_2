package simulator.model;

import Exceptions.InvalidArgumentException;

public class NewInterCityRoadEvent extends NewRoadEvent {
	
	public NewInterCityRoadEvent(int time ,String id,String srcJun ,String destJunc,int length,int co2Limit , int maxSpeed , Weather weather ) {
		super(time);
		//this._time = time; 
		this.id = id; 
		this.srcJunc = srcJun; 
		this.destJunc = destJunc; 
		this.length = length; 
		this.co2Limit = co2Limit; 
		this.maxSpeed = maxSpeed; 
		this.weather = weather; 
		
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public Road createRoadObject() {
		Road r = null;
		
			try {
				r = new InterCityRoad(this.id,this.src,this.dest,this.maxSpeed,this.co2Limit,this.length,this.weather);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
		
		 
		return r;
	}


	


}
