package simulator.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONObject;


import Exceptions.InvalidArgumentException;

import java.math.*;

public class Vehicle extends SimulatedObject implements Comparable<Vehicle> {
	
	private List<Junction> Itinerary; 
	private  int Max_Speed; 
	private int Current_Speed;
	private VehicleStatus Status;
	private Road Road; 
	private  int Localization;
	private int Degree_of_Pollution; //contaminacion del vehiculo en cada paso de la simulacion
	private int Pollution; // durante todo el recorrido 
	private int Global_distance_traveled;	
	
	private int Last_Junction_index;	 
	private int ancientLocalization;
	private final int number = 10 ;
	private final int CERO = 0;
	private final int UNO = 1;
	
	
	
	 Vehicle(String id,int maxSpeed ,int contClass,List<Junction> itinerary) throws Exception{
		super(id);
		
		if(maxSpeed < 0 ) {
			throw new InvalidArgumentException("Incorrect speed,Max Speed of the vehicle is less than 0");
		}
		else if (contClass<0 || contClass >10) {
			throw new InvalidArgumentException("Incorrect contClass value");
		}
		else if(itinerary.size()<2) {
			throw new InvalidArgumentException("Incorrect , the cont of the list is less than 2");
		}
		else {
			this._id = id; 
			this.Max_Speed = maxSpeed;
			this.Itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
			
			
			this.ancientLocalization = 0;
			this.Localization=0;
			this.Degree_of_Pollution = contClass;
			this.Pollution = 0;
			this.Last_Junction_index=0;
			this.Status= VehicleStatus.PENDING;
			this.Current_Speed = 0;
			
			
		}
	}

	@Override
	public void advance(int time)  {
		int c = 0;
		
		if(this.Status == VehicleStatus.TRAVELING) {		
		
			
			//this.Global_distance_traveled += this.Localization-this.ancientLocalization;
			int suma = this.Localization+this.Current_Speed; 
			this.Localization = Math.min(suma, this.Road.getLength());
			this.Global_distance_traveled += (this.Localization-this.ancientLocalization);
			
			
			c = ((this.Localization-this.ancientLocalization)*this.Degree_of_Pollution);
			this.Pollution+=c;		
			
			try {
				this.Road.addContamination(c); 
			} catch (InvalidArgumentException e) {
				e.getMessage();
			}
			
			this.ancientLocalization = this.Localization;
			
			if(this.Localization >= this.Road.getLength()) {			// igual lo otro es en un else
				
				this.Itinerary.get(this.Last_Junction_index+1).enter(this);
				this.Status = VehicleStatus.WAITING;
				this.Current_Speed= 0;			
				//this.Localization = 0;
				this.ancientLocalization=0;
				this.Last_Junction_index++;
				
			}
			
		//	this.ancientLocalization = this.Localization;
			
			
			
		}
		
	}

	@Override
	public JSONObject report() {
		
		JSONObject vehicle = new JSONObject();
		
		
		vehicle.put("id", this._id);
		vehicle.put("speed", this.Current_Speed);
		vehicle.put("distance", this.Global_distance_traveled);
		vehicle.put("co2",this.Pollution);
		vehicle.put("class",this.Degree_of_Pollution);
		vehicle.put("status",this.Status);
		if(this.Status==VehicleStatus.TRAVELING || this.Status == VehicleStatus.WAITING ) {
			vehicle.put("road", this.Road);	
			vehicle.put("location",this.Localization);			
		}
		
		return vehicle;
	}
	
	
	 void setSpeed (int s) throws Exception {		
		if(s <0 ) {
			throw new InvalidArgumentException("Incorrect,the Speed is negative");
		}
		else {
			this.Current_Speed = Math.min(s, this.Max_Speed);
		}
	}
	
	 void setContaminationClass(int c) throws Exception{
		if(c<0 || c >10) {
			throw new InvalidArgumentException("Incorrect contClass value");	
			
		}else {
			this.Degree_of_Pollution = c;
		}
		
	}
	
	 void moveToNextRoad() throws Exception {
		Junction actualJunc , nextJunc;
		
		if(this.Status == VehicleStatus.ARRIVED){
			throw new Exception("The vehice is travelling");
		}
		if( this.Status == VehicleStatus.TRAVELING){
			
			throw new Exception("The vehice is travelling");
		}
		
		
		
		if(this.Last_Junction_index +1 == this.Itinerary.size()) {
			this.Road.exit(this);
			this.Status = VehicleStatus.ARRIVED;			
		}
		else {

			if(this.Status == VehicleStatus.PENDING ) { 
				
				actualJunc = this.Itinerary.get(CERO);
				
				nextJunc=this.Itinerary.get(UNO); 
				
				this.Road = actualJunc.roadTo(nextJunc);
				
				this.Status = VehicleStatus.TRAVELING;
				
				try {
					this.Road.enter(this);
				}
				catch(Exception e ) {
					e.getMessage();
				}
				
				
			}
			else {	
				this.Road.exit(this);
				
				actualJunc= this.Itinerary.get(this.Last_Junction_index); 
				
				nextJunc=this.Itinerary.get(this.Last_Junction_index +1); 			
				
				this.Road=actualJunc.roadTo(nextJunc);	
				
				this.Localization = CERO; 
				this.Current_Speed = CERO;	
				
				
				
				
				if(this.Road != null) {
					try {
						this.Road.enter(this);
						//this.setContaminationClass(c);
					}
					catch(Exception e ) {
						e.getMessage();
					}
					
					
				}
				Status= VehicleStatus.TRAVELING;
				
			}
			
		}
		
	}

	public List<Junction> getItinerary() {
		return Itinerary;
	}

	public void setItinerary(List<Junction> itinerary) {
		Itinerary = itinerary;
	}
	

	

	public Road getRoad() {
		return Road;
	}

	public void setRoad(Road road) {
		Road = road;
	}

	public VehicleStatus getStatus() {
		return Status;
	}

	public void setStatus(VehicleStatus status) {
		Status = status;
	}
	
	
	
	public int getDegree_of_Pollution() {
		return Degree_of_Pollution;
	}


	public void setDegree_of_Pollution(int degree_of_Pollution) {
		Degree_of_Pollution = degree_of_Pollution;
	}


	public int getPollution() {
		return Pollution;
	}


	public void setPollution(int pollution) {
		Pollution = pollution;
	}


	public int getGlobal_distance_traveled() {
		return Global_distance_traveled;
	}


	public void setGlobal_distance_traveled(int global_distance_traveled) {
		Global_distance_traveled = global_distance_traveled;
	}


	public int getLast_Junction_index() {
		return Last_Junction_index;
	}


	public void setLast_Junction_index(int last_Junction_index) {
		Last_Junction_index = last_Junction_index;
	}


	public int getAncientLocalization() {
		return ancientLocalization;
	}


	public void setAncientLocalization(int ancientLocalization) {
		this.ancientLocalization = ancientLocalization;
	}

	
	
	public int getMax_Speed() {
		return Max_Speed;
	}

	public void setMax_Speed(int max_Speed) {
		Max_Speed = max_Speed;
	}

	public int getCurrent_Speed() {
		return Current_Speed;
	}

	public void setCurrent_Speed(int current_Speed) {
		Current_Speed = current_Speed;
	}

	public int getLocalization() {
		return Localization;
	}

	public void setLocalization(int localization) {
		Localization = localization;
	}


	@Override
	public int compareTo(Vehicle o) {
		return Integer.valueOf(o.Localization).compareTo(this.Localization);
		//return Integer.valueOf(this._id).compareTo(o.getId());
	}

	
	
	
	boolean recorreItinerario () {
		boolean ok = true; 
		
		
		for (int i = 0; i < this.Itinerary.size()-1;i++){
			 if(! this.Itinerary.get(i).getOutgoingRoadList().containsKey(this.Itinerary.get(i+1))){ // si el mapa de ese junction contiene 
				 // el siguiente junction 
				 
				 ok = false; 
			 }
			
		}
		
		return ok; 
			
	}


	public int getCERO() {
		return CERO;
	}
	
	
	
	
	
	

}
