package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveAllStrategy  implements DequeingStrategy{
	
	

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		List<Vehicle> listaAux = new ArrayList<>();
		
		//listaAux = q;
		
		for(int i = 0;i< q.size();i++) {
			listaAux.set(i, q.get(i));
			
		}
		return listaAux;
	}
	

}
