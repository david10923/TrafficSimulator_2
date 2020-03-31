
package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy> {

	private static String type = "round_robin_lss";
	private final int ONE = 1;

	public RoundRobinStrategyBuilder() {
		super(type);
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
			
		if(data.has("timeslot")) 
			data.put("timeslot", data.getInt("timeslot"));		
		else 
			data.put("timeslot",ONE);
	
		
		
		LightSwitchingStrategy rr = new RoundRobinStrategy(data.getInt("timeslot"));
		
		
		return rr;
	}

}
