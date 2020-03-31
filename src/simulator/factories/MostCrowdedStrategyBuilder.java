package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;
import simulator.model.RoundRobinStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy>{

	private static String type = "move_first_dqs";
	private final int ONE = 1;

	public MostCrowdedStrategyBuilder() {
		super(type);		
		// TODO Auto-generated constructor stub
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		
		if(data.has("timeslot")) 
			data.put("timeslot", data.getInt("timeslot"));		
		else 
			data.put("timeslot",ONE);
		
		
		LightSwitchingStrategy rr = new MostCrowdedStrategy(data.getInt("timeslot"));
		
		
		return rr;
	}

}
