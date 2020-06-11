package simulator.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observer;

import org.json.JSONObject;
import simulator.model.TrafficSimObserver;

import Exceptions.InvalidArgumentException;
import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver> {
	
	private RoadMap map_of_roads;
	private List<Event> list_of_events; 
	private int time_of_simulation; 
	private List<TrafficSimObserver> listaObservadores ; 
	
	public TrafficSimulator(){
		this.list_of_events = new SortedArrayList<Event>();
		this.time_of_simulation= 0;
		this.map_of_roads = new RoadMap();
		this.listaObservadores = new ArrayList<TrafficSimObserver>();
	}
	
	///////CADA VEZ QUE CMABIO ALGO SE  LO TENGO QUE NOTIFICAR A LOS OBSERVADORES PARA QUE HAGAN SUS CAMBIOS ///
	
	public void addEvent (Event e) {
		this.list_of_events.add(e); ////////INDICO A TODOS LOS OBSERVADORES QUE AÑADO UN EVENTO //////
		
		for(TrafficSimObserver o : this.listaObservadores){
			o.onEventAdded(this.map_of_roads,this.list_of_events,e,this.time_of_simulation);
		}
			
	}
	
	public void advance() {
		/*
		if (this.time_of_simulation > 1) {
			
            try {
                //Thread.sleep(50);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            
        }
	 */
		
		this.time_of_simulation++;
		
		////////INDICO A TODOS LOS OBSERVADORES QUE AVANZO  //////
		for(TrafficSimObserver o : this.listaObservadores){
			o.onAdvanceStart(this.map_of_roads,this.list_of_events,this.time_of_simulation);
		}
		
		Iterator<Event> it  = this.list_of_events.iterator();
		
		while(it.hasNext()){
			Event e = it.next();
			if(e.getTime() == this.time_of_simulation){
				e.execute(this.map_of_roads);
				it.remove();
			}
		}
		
	
		
		for(int i = 0; i< this.map_of_roads.getJunctions().size();i++) {
			try {
				this.map_of_roads.getJunctions().get(i).advance(this.time_of_simulation);
			} catch (Exception e) {
				
				for(TrafficSimObserver o : this.listaObservadores){
					o.onError(e.getMessage());					
				}
				
				e.getMessage();
			}
		}
		
		
	
		
		for(int i = 0; i< this.map_of_roads.getRoads().size();i++) {
			try {
				this.map_of_roads.getRoads().get(i).advance(this.time_of_simulation);
			} catch (Exception e) {
				////////INDICO A TODOS LOS OBSERVADORES QUE HAY UN ERROR //////
				for(TrafficSimObserver o : this.listaObservadores){
					o.onError(e.getMessage());					
				}
				
				e.getMessage();
			}
		}
		
		
		for(TrafficSimObserver o : this.listaObservadores){
			o.onAdvanceEnd(this.map_of_roads,this.list_of_events,this.time_of_simulation);
		}
		
		
	
		
	
	}
	
	public void reset() {// no se utiliza 
		
		this.map_of_roads.reset(); 		
		this.list_of_events.clear();	
		this.time_of_simulation = 0;
		
		for (TrafficSimObserver o : this.listaObservadores) {
			o.onReset(this.map_of_roads,this.list_of_events,this.time_of_simulation);
		}
	
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
	
	
	



	@Override
	public void addObserver(TrafficSimObserver o) {
		this.listaObservadores.add(o);
		
		for(TrafficSimObserver e : this.listaObservadores){
			e.onRegister(this.map_of_roads,this.list_of_events,this.time_of_simulation);
		}
	
		
	}

	public void removeObserver(TrafficSimObserver o) {
		this.listaObservadores.remove(o);
		
	}


}
