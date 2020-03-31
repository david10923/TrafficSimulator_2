package simulator.model;

import java.util.ArrayList;
import java.util.List;

import Exceptions.InvalidArgumentException;
import simulator.misc.Pair;

public class SetWeatherEvent extends Event {
	
	private List<Pair<String,Weather>> ws;
	
	
	public SetWeatherEvent(int time,List<Pair<String,Weather>> ws) throws InvalidArgumentException {
		super(time);
		this.ws = new ArrayList<Pair<String,Weather>>();
		
		if(ws != null) {
			this.ws = ws;
			this._time = time;
		}
		else {
			throw new InvalidArgumentException("The list is null");
		}
		
	}

	@Override
	void execute(RoadMap map)  {		 
		for (Pair<String, Weather> p : ws) {			
            try {
                Road road = map.getRoad(p.getFirst());
                try {
                    road.setWeather(p.getSecond());

                } catch (Exception e) {
                    e.getMessage();
                }

            } catch (Exception e) {
                System.out.println("This road not exits");
            }

        }		
	}
	
	
	public List<Pair<String, Weather>> getWs() {
		return ws;
	}

	public void setWs(List<Pair<String, Weather>> ws) {
		this.ws = ws;
	}

}
