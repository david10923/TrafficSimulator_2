package simulator.model;

import java.util.Map;

import Exceptions.InvalidArgumentException;

public class NewJunctionEvent extends Event {
	
	//private int time;
	private String id; 
	private LightSwitchingStrategy lightStrategy; 
	private DequeingStrategy dqStrategy; 
	private int xCoor; 
	private int yCoor; 
	
	
	
	
	public NewJunctionEvent(int time,String id , LightSwitchingStrategy lsStrategy ,DequeingStrategy dqStrategy, int xCoor , int yCoor) {
		super(time);
		this.id = id; 
		this.lightStrategy = lsStrategy; 
		this.dqStrategy = dqStrategy;  
		this.xCoor = xCoor; 
		this.yCoor = yCoor;
		
	}

	@Override
	void execute(RoadMap map) {
		 
		try {
			Junction j= new Junction(this.id,this.lightStrategy,this.dqStrategy,this.xCoor,this.yCoor);
			map.addJunction(j);		
		} catch (InvalidArgumentException e) {			
			e.getMessage();
		}
	
		
	}
	
	

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LightSwitchingStrategy getLightStrategy() {
		return lightStrategy;
	}

	public void setLightStrategy(LightSwitchingStrategy lightStrategy) {
		this.lightStrategy = lightStrategy;
	}

	public DequeingStrategy getDqStrategy() {
		return dqStrategy;
	}

	public void setDqStrategy(DequeingStrategy dqStrategy) {
		this.dqStrategy = dqStrategy;
	}

	public int getxCoor() {
		return xCoor;
	}

	public void setxCoor(int xCoor) {
		this.xCoor = xCoor;
	}

	public int getyCoor() {
		return yCoor;
	}

	public void setyCoor(int yCoor) {
		this.yCoor = yCoor;
	}

	
}
