package simulator.model;

import java.util.ArrayList;
import java.util.List;

import Exceptions.InvalidArgumentException;
import simulator.misc.Pair;

public class SetWeatherEvent extends Event {
	
	private List<Pair<String,Weather>> ws;
	
	
	public SetWeatherEvent(int time,List<Pair<String,Weather>> ws)  {
		super(time);
		this.ws = new ArrayList<Pair<String,Weather>>();
		
		if(ws != null) {
			this.ws = ws;
			this._time = time;
		}
		else {
			throw new IllegalArgumentException("The list is null");
		}
		
	}

	@Override
	void execute(RoadMap map)  {		 
		for (Pair<String, Weather> p : ws) {			
            
            Road road = map.getRoad(p.getFirst());
            
            if(road == null){
            	throw new IllegalArgumentException("This road does not exits");
            }
            else{
            	   road.setWeather(p.getSecond());
            }
             
         
        

        }		
	}
	
	public String toString(){
				
		return "New Weather event " + "[( " +this.ws.get(this.ws.size()-1).getFirst() + " ,"+this.ws.get(this.ws.size()-1).getSecond() + " )]";
	}
	
	public List<Pair<String, Weather>> getWs() {
		return ws;
	}

	public void setWs(List<Pair<String, Weather>> ws) {
		this.ws = ws;
	}

}
