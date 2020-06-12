package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeingStrategy;
import simulator.model.Event;
import simulator.model.MoveAllStrategy;
import simulator.model.MoveFirstStrategy;

public class MoveAllStrategyBuilder extends Builder<DequeingStrategy>{	

	private static String type ="most_all_dqs";

	public MoveAllStrategyBuilder() {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected DequeingStrategy createTheInstance(JSONObject data) {
		
		DequeingStrategy mv = new MoveAllStrategy();
		
		return mv;
	}

}
