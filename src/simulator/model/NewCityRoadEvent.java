package simulator.model;

import Exceptions.InvalidArgumentException;

public class NewCityRoadEvent  extends NewRoadEvent{
	

	
	public NewCityRoadEvent(int time ,String id,String srcJun ,String destJunc,int length,int co2Limit , int maxSpeed , Weather weather ) {
		super(time);
		this._time = time;
		this.id= id; 
		this.srcJunc = srcJun; 
		this.destJunc = destJunc;
		this.length = length; 
		this.co2Limit = co2Limit; 
		this.maxSpeed = maxSpeed; 
		this.weather = weather;
		
		
	}

	@Override
	
	public Road createRoadObject() {
			
		Road r = null;
		
			//try {
				 r= new CityRoad(this.id, this.src,this.dest, this.maxSpeed, this.co2Limit, this.length, this.weather);
				
			//} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.getMessage();
			//}
		
			
		return r;
	}
	
	public String toString(){
		return "New City Road  "+ "[( "+ this.id+ " )]";
	}
	

	
} 

	