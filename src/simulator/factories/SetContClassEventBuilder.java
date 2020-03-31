package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;
import simulator.model.Weather;

public class SetContClassEventBuilder extends Builder {

	private static String type = "set_cont_class";
	private int time; 
	private List<Pair<String,Integer>>cs;
	
	private JSONArray j;
	

	public SetContClassEventBuilder() {
		super(type);
		
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		Event e = null; 
		
		if(data.has("time")&& data.has("info")) {			

			this.time = data.getInt("time");			
			cs = new ArrayList<Pair<String,Integer>>();
			
			j = data.getJSONArray("info");
			
			for(int i = 0; i< j.length();i++) {
				JSONObject pair = j.getJSONObject(i);
				cs.add(new Pair<String,Integer>(pair.getString("vehicle"),pair.getInt("class")));

			}			
			
			try {
				e = new NewSetContClassEvent(this.time,this.cs);				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.getMessage();
			}
			
		}
		
		
		return e;
			
	}

}
