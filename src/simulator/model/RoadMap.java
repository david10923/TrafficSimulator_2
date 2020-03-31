package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import Exceptions.InvalidArgumentException;

public class RoadMap {
	private  List<Junction> JunctionList;
	private List<Road> RoadList; 
	private List<Vehicle> VehicleList;
	private Map<String,Junction> IdJunctionMap;
	private Map<String,Road> IdRoadMap;
	private Map<String,Vehicle> IdVehicleMap;
	
	
	public RoadMap() {
		this.IdJunctionMap = new HashMap<String,Junction>();
		this.JunctionList = new ArrayList<Junction>();
		this.RoadList = new ArrayList<Road>();
		this.VehicleList = new ArrayList<Vehicle>();
		this.IdRoadMap = new HashMap<String,Road>();
		this.IdVehicleMap = new HashMap<String,Vehicle>();
	}
	
	protected void addJunction(Junction j) {
		if(!this.IdJunctionMap.containsKey(j.getId())) {
			this.JunctionList.add(j); 
			this.IdJunctionMap.put(j.getId(), j);
			
		}
		
	}
	protected void addRoad(Road r) throws InvalidArgumentException {
	
		if(this.IdRoadMap.containsKey(r.getId())){
			throw new InvalidArgumentException("The road is in the map");
		}
		if(!this.JunctionList.contains(r.getSource())){
			throw new InvalidArgumentException("The junction is not in the map");
		}
		if(!this.JunctionList.contains(r.getDestination())){
			throw new InvalidArgumentException("The junction is not in the map");
		}
		
		this.IdRoadMap.put(r.getId(), r);
		this.RoadList.add(r);	
		
		
		try {
			r.Source.addOutgoingRoad(r);
			r.Destination.addIncomingRoad(r);			
			
		} catch (Exception e) {
			e.getMessage();
		}
		
		
	}
	
	protected void addVehicle(Vehicle v) throws InvalidArgumentException {
		boolean  ok2= false;
		
		
		if (!v.recorreItinerario()) {
			throw new InvalidArgumentException("The itinerary is not possible");
		}
		if(this.VehicleList.contains(v)) {
			throw new  InvalidArgumentException("This Vehicle can not be add : "+ v._id);
		}
		
			
			this.VehicleList.add(v);
			this.IdVehicleMap.put(v._id, v);
		
	}
	
	public Vehicle getVehicle(String id) {
		
		if(this.IdVehicleMap.containsKey(id)){
			return this.IdVehicleMap.get(id);
		}
		return null;
		
	}
	
	
	public Road getRoad(String id ) {
		
		if(this.IdRoadMap.containsKey(id)){
			return this.IdRoadMap.get(id);
		}
		return null;
	}
	
	public Junction getJunction(String id ) {
		if(this.IdJunctionMap.containsKey(id)){
			return this.IdJunctionMap.get(id);
		}
		return null;
	}
	
	
	
	public Map<String, Junction> getIdJunctionMap() {
		return IdJunctionMap;
	}

	public void setIdJunctionMap(Map<String, Junction> idJunctionMap) {
		IdJunctionMap = idJunctionMap;
	}

	public Map<String, Road> getIdRoadMap() {
		return IdRoadMap;
	}

	public void setIdRoadMap(Map<String, Road> idRoadMap) {
		IdRoadMap = idRoadMap;
	}

	public Map<String, Vehicle> getIdVehicleMap() {
		return IdVehicleMap;
	}

	public void setIdVehicleMap(Map<String, Vehicle> idVehicleMap) {
		IdVehicleMap = idVehicleMap;
	}


	

	public List<Junction> getJunctions() {
		List<Junction> aux ; 
		aux = Collections.unmodifiableList(new ArrayList<>(this.JunctionList));
		return aux;
	}

	public List<Road> getRoads() {
		List<Road> aux ; 
		aux = Collections.unmodifiableList(new ArrayList<>(this.RoadList));
		return aux;
	}

	public List<Vehicle> getVehicles() {
		List<Vehicle> aux ; 
		aux = Collections.unmodifiableList(new ArrayList<>(this.VehicleList));
		return aux;
	}



	protected void reset() {
		
		this.IdJunctionMap.clear();
		this.IdRoadMap.clear();
		this.IdVehicleMap.clear();
		this.JunctionList.clear(); 
		this.RoadList.clear();
		this.VehicleList.clear();
	}
	
	
	
	public JSONObject report() {
		
		JSONObject roadMap = new JSONObject();
		
		roadMap.put("junctions" ,reportJunctions()); 
		roadMap.put("roads",reportRoads());
		roadMap.put("vehicles",reportVehicles());
		
		
		
		return roadMap;
		
	}
	
	
	
	public JSONArray reportJunctions () {
		JSONArray jlist = new JSONArray();
		 
		 for(int i =0 ; i < JunctionList.size();i++) {
			 jlist.put(this.JunctionList.get(i).report());
		 }
		
		return jlist; 
		
	}
	
	public JSONArray reportRoads () {
		JSONArray j= new JSONArray ();
		 
		 for(int i =0 ; i < this.RoadList.size();i++) {
			j.put(this.RoadList.get(i).report());
		 }
		
		return j; 
		
	}
	
	public JSONArray reportVehicles () {
		 JSONArray jlist = new JSONArray();
		 
		 for(int i =0 ; i < this.VehicleList.size();i++) {
			 jlist.put(this.VehicleList.get(i).report());
		 }
		
		return jlist; 
		
	}
	


	
}
