package simulator.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import Exceptions.InvalidArgumentException;
import simulator.misc.SortedArrayList;

public class TrafficSimulator {
	
	private RoadMap map_of_roads;
	private List<Event> list_of_events; 
	private int time_of_simulation; 
	
	public TrafficSimulator(){
		this.list_of_events = new SortedArrayList<Event>();
		this.time_of_simulation= 0;
		this.map_of_roads = new RoadMap();
	}
	
	
	
	public void addEvent (Event e) {
		this.list_of_events.add(e); 
	}
	
	public void advance() {
		this.time_of_simulation++;
		
		Iterator<Event> it  = this.list_of_events.iterator();
		
		while(it.hasNext()){
			Event e = it.next();
			if(e.getTime() == this.time_of_simulation){
				e.execute(this.map_of_roads);
				it.remove();
			}
		}
		
		//paso 3
		
		for(int i = 0; i< this.map_of_roads.getJunctions().size();i++) {
			this.map_of_roads.getJunctions().get(i).advance(this.time_of_simulation);
		}
		
		
		// paso 4 
		
		for(int i = 0; i< this.map_of_roads.getRoads().size();i++) {
			this.map_of_roads.getRoads().get(i).advance(this.time_of_simulation);
		}
		
		
	
	}
	
	public void reset() {// no se utiliza 
		
		this.map_of_roads.reset(); 		
		this.list_of_events.clear();	
		this.time_of_simulation = 0;
	}
	
	
	
	public JSONObject report() {
		JSONObject j = new JSONObject();
		
		j.put("time", this.time_of_simulation);
		j.put("state", this.map_of_roads.report()); 	
		
		return j;	
		
	}


	public RoadMap getMap_of_roads() {
		return map_of_roads;
	}


	public void setMap_of_roads(RoadMap map_of_roads) {
		this.map_of_roads = map_of_roads;
	}


	public List<Event> getList_of_events() {
		return list_of_events;
	}


	public void setList_of_events(List<Event> list_of_events) {
		this.list_of_events = list_of_events;
	}


	public int getTime_of_simulation() {
		return time_of_simulation;
	}


	public void setTime_of_simulation(int time_of_simulation) {
		this.time_of_simulation = time_of_simulation;
	}

}
