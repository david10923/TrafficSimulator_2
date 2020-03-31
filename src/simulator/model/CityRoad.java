package simulator.model;
import java.util.ArrayList;

import simulator.model.Weather;

public class CityRoad extends Road{
	
	private static final int WINDY_STORM_POLLUTION = 10;
	private static final int OTHER_POLLUTION = 2;
	private static final double auxiliar = 11.0;


	protected CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) throws Exception {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		
		/*
	
		this.Max_Speed = maxSpeed; 
		this.Length = length; 
		this.environmental_conditions = weather;
		this.Destination = destJunc; 
		this.Source= srcJunc; 
		this.Current_Max_Speed_limit = this.Max_Speed;
		this.Global_Pollution = contLimit ; 
		*/
	}

	@Override
	protected void reduceTotalContamination() {
		if(this.environmental_conditions == Weather.WINDY || this.environmental_conditions == Weather.STORM ) {
			this.Global_Pollution-= CityRoad.WINDY_STORM_POLLUTION;
			
		}
		else {
			this.Global_Pollution-= CityRoad.OTHER_POLLUTION;
		}
		
		if(this.Global_Pollution < 0) {
			this.Global_Pollution =0;
		}
	}

	@Override

	protected void updateSpeedLimit() {
		//this.Current_Max_Speed_limit = this.Max_Speed;
		
	}
	

	@Override
	protected int calculateVehicleSpeed(Vehicle v) {
		
		return (int) Math.ceil(((CityRoad.auxiliar-v.getDegree_of_Pollution())/CityRoad.auxiliar)*this.Current_Max_Speed_limit);
		
	}

		

}
