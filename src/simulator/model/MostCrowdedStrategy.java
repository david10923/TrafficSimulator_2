package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {
	
	private int timeSlot;
	
	private final int AllRed = -1;
	
	
	public MostCrowdedStrategy(int timeSlot) {
		this.timeSlot = timeSlot; 
		
	}

	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		int tamActual , tamMayor=-1 , colaMasLarga=0;
		
		//1
		if(roads.isEmpty()) {
			return -1;
		}
		//2
		else if(currGreen == AllRed) {
			
			for(int i =0 ; i< qs.size();i++) {
				tamActual = qs.get(i).size();
				
				if(tamActual > tamMayor){
					tamMayor = tamActual;
					colaMasLarga = i;
				}
				
			}
		}
		//
		else if((currTime-lastSwitchingTime)< this.timeSlot) {
			return currGreen;
		}
		
		
		//4
		else {
			colaMasLarga = ((currGreen +1) % roads.size()); 
			int aux = colaMasLarga;
			
			for(int i = colaMasLarga+1;i< qs.size();i++ ) {
				if(qs.get(i).size() > qs.get(colaMasLarga).size()) {
					colaMasLarga = i;
				}
			}
			
			for(int j =0 ; j < aux;j++) {
				if(qs.get(j).size() > qs.get(colaMasLarga).size()) {
					colaMasLarga = j;
				}
			}
			
			
		}
		
		return colaMasLarga;
	}
	
	
	public int getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(int timeSlot) {
		this.timeSlot = timeSlot;
	}

}
