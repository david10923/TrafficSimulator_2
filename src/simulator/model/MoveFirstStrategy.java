package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveFirstStrategy implements DequeingStrategy {
	
	private final int CERO = 0;
	
	
	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		
		List<Vehicle> listaAux = new ArrayList<Vehicle>();
		
		if(q.size() >0){			
			listaAux.add(q.get(CERO));
			 
		}
		return listaAux;
		
	}
	
	

}
