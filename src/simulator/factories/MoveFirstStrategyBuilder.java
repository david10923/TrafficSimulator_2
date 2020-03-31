package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeingStrategy;
import simulator.model.MoveFirstStrategy;

public class MoveFirstStrategyBuilder  extends Builder{

	private static String type = "move_first_dqs";

	public MoveFirstStrategyBuilder(){
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected DequeingStrategy createTheInstance(JSONObject data) {
		
		DequeingStrategy mv = new MoveFirstStrategy();
		
		return mv;
		
		
	}

}
