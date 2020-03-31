package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import Exceptions.InvalidArgumentException;

public abstract class Road extends SimulatedObject {
	protected int Length; 
	protected Junction Destination; 
	protected Junction Source;
	protected int Max_Speed; 
	protected int Current_Max_Speed_limit; 
	protected int Masive_Pollution;
	protected Weather environmental_conditions; 
	protected int Global_Pollution; 
	protected List<Vehicle> Vehicles; 
	
	
	
	
	 Road(String id ,Junction srcJunc ,Junction destJunc ,int maxSpeed,int contLimit ,int length,Weather weather)throws Exception {
		super(id);		
		
		if(maxSpeed <0 ) 
			throw new InvalidArgumentException("Incorrect road , the max speed is less than 0");
		
		else if(contLimit<0) {
			throw new InvalidArgumentException("Incorrect road ,the contLimit is negative");
		}
		else if(length<0) {
			throw new InvalidArgumentException("Incorrect road ,the contLimit is negative");
		}
		else if(srcJunc == null || destJunc == null || weather == null) {
			throw new InvalidArgumentException("Incorrect road ,the contLimit is negative");
		}
		else {
		
			this.Max_Speed = maxSpeed; 
			this.Length = length; 
			this.environmental_conditions = weather;
			this.Destination = destJunc; 
			this.Source= srcJunc; 
			this.Current_Max_Speed_limit = this.Max_Speed;
			this.Vehicles = new ArrayList<Vehicle>();
			this.Masive_Pollution= contLimit; 
			this.Global_Pollution=0;
			
			/*
			try {
				this.Source.addOutgoingRoad(this);
				this.Destination.addIncomingRoad(this);			
				
			} catch (Exception e) {
				e.getMessage();
			}
			*/
		}
	}
			
		
		

	@Override
	void advance(int time) {
	
		
		//1
		reduceTotalContamination();
		//2
		updateSpeedLimit();
		//3
		for(Vehicle c : this.Vehicles) {
						
			try {
				if(c.getStatus() != VehicleStatus.WAITING){
					c.setSpeed(calculateVehicleSpeed(c));
				}				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
			c.advance(time);		
		}	
		
		
		Collections.sort(this.Vehicles); 			
		
	}
	
	

	@Override
	public JSONObject report() {
		
		JSONObject road = new JSONObject();
		
		road.put("id", this._id);
		road.put("speedlimit", this.Current_Max_Speed_limit);
		road.put("weather", this.environmental_conditions);
		road.put("co2", this.Global_Pollution);
		road.put("vehicles",reportVehicle());
		return road;
	}
	
	public void enter(Vehicle v) throws Exception {
		
		if(v.getLocalization()==0 && v.getCurrent_Speed() ==0) {
			this.Vehicles.add(v);
		}
		else {
			throw new Exception("The localization or speed of the vehicle is not 0");
		}
			
	}
	
	public void exit(Vehicle v) {
		
		this.Vehicles.remove(v);
	}
	
	
	
	public void setWeather(Weather w) throws Exception{
		if(w==null)
			throw new Exception("The weather is null");
		else {
			this.environmental_conditions = w ;
		}
		 
	}
	
	
	public void addContamination(int c) throws InvalidArgumentException{// el mismo caso que weather
		if(c<0) 
			throw new InvalidArgumentException("The pollution is less than cero");
		
		//System.out.println("He añadido "+ c);
	
		this.Global_Pollution += c ;
			
	}
	
	
	
	public int getLength() {
		return Length;
	}

	public void setLength(int length) {
		Length = length;
	}

	public Junction getDestination() {
		return Destination;
	}

	public void setDestination(Junction destination) {
		Destination = destination;
	}

	public Junction getSource() {
		return Source;
	}

	public void setSource(Junction source) {
		Source = source;
	}
	
	public int compareTo(Vehicle v1 , Vehicle v2) {		
		return Integer.valueOf(v1.getLocalization()).compareTo(v2.getLocalization());			
		
	}
	
	public JSONArray reportVehicle (){
		JSONArray j = new JSONArray();
		
		for(int i =0 ; i< this.Vehicles.size();i++) {
			j.put(this.Vehicles.get(i).getId());
		}
		
		return j;
		
	}
	
	protected abstract void reduceTotalContamination() ;
	protected abstract void updateSpeedLimit(); 
	protected abstract int calculateVehicleSpeed(Vehicle v);
	

}
