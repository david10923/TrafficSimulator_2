package simulator.model;

import Exceptions.InvalidArgumentException;

public abstract class NewRoadEvent extends Event {
	
	//protected  int time; 
	protected  String id; 
	protected  String srcJunc; 
	protected String destJunc; 
	protected int length; 
	protected int co2Limit;
	protected int maxSpeed; 
	protected Weather weather;
	protected Junction src; 
	protected Junction dest; 
	

	NewRoadEvent(int time) {
		super(time);
		// TODO Auto-generated constructor stub
	}
	
	
	void execute(RoadMap map) {
		
			this.src =map.getJunction(this.srcJunc);
			this.dest = map.getJunction(this.destJunc);
			
			try {
				map.addRoad(createRoadObject());
			} catch (InvalidArgumentException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
			
			
			
			
		}
		
	public abstract Road createRoadObject();
}
