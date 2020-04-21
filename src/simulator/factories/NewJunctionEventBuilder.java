package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.DequeingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event>{
	private static String type= "new_junction";
	private final int CERO = 0; 
	private final int ONE = 1;
	private Factory<LightSwitchingStrategy> lssFactory; 
	private Factory<DequeingStrategy> dqsFactory;
	
	
	

	public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lssFactory ,Factory<DequeingStrategy> dqsFactory) {
		super(type);
		this.lssFactory = lssFactory; 
		this.dqsFactory = dqsFactory;
		// TODO Auto-generated constructor stub
	}
	

	@Override
	protected Event createTheInstance(JSONObject data) {
		JSONArray CoorList;
	
		Event e;
		
		if(data.has("time") && data.has("id") && data.has("coor") && data.has("ls_strategy") && data.has("dq_strategy")) {
			CoorList = data.getJSONArray("coor");
						
			e = new NewJunctionEvent(data.getInt("time"),data.getString("id"),
					this.lssFactory.createInstance(data.getJSONObject("ls_strategy")), this.dqsFactory.createInstance(data.getJSONObject("dq_strategy")),
					 CoorList.getInt(CERO),CoorList.getInt(ONE));
						
				
		}
		else {			
			return null;
		}
			
		
		
		return e;
	}

}
