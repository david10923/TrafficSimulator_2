package simulator.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import Exceptions.InvalidArgumentException;
import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimulator;

public class Controller {
	
	private TrafficSimulator _sim;
	private Factory<Event> _eventsFactory;
	
	
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) throws InvalidArgumentException{
		
		if(sim == null || eventsFactory == null) {
			throw new InvalidArgumentException("The arguments are null");
		}
		else {
			this._sim = sim; 
			this._eventsFactory = eventsFactory;
		}
		
	
	}
	
	
	
	public void loadEvents(InputStream in ) {
		
		JSONObject jo = new JSONObject(new JSONTokener(in));
		
		
		JSONArray events = jo.getJSONArray("events");
		
		
		for (int i = 0; i < events.length(); i++) {
			_sim.addEvent(_eventsFactory.createInstance(events.getJSONObject(i)));
		}
		
	}
	
	
	
	public void run (int in , OutputStream out ) {
		int i =0;
		
		if (out == null) {
			out = new OutputStream() {
				@Override
				public void write(int b) throws IOException {}
				};
		}
		
		PrintStream p = new PrintStream(out);
		p.println("{");
		p.println(" \"states\": [");	
		
		while(i < in-1){
			 _sim.advance();
			 p.print(_sim.report());
			 p.println(",");
			 i++;
		
		}
		_sim.advance();
		p.print(_sim.report());		
		
		p.println("]");
		p.println("}");
		
	}
	
	
	public void reset () {
		this._sim.reset();
	}
	
	
	


	public TrafficSimulator get_sim() {
		return _sim;
	}


	public void set_sim(TrafficSimulator _sim) {
		this._sim = _sim;
	}


	public Factory<Event> get_eventsFactory() {
		return _eventsFactory;
	}


	public void set_eventsFactory(Factory<Event> _eventsFactory) {
		this._eventsFactory = _eventsFactory;
	}
	
	
	
	
	
	
}
