package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class NewSetContClassEvent extends Event{

	private List<Pair<String,Integer>>cs;
	

	

	public NewSetContClassEvent(int time,List<Pair<String,Integer>> cs) throws Exception {
		super(time);
		
		if(cs == null) {
			throw new Exception("List is null");
		}
		else {
			this._time = time; 
			this.cs = cs;
		}
	}

	@Override
	void execute(RoadMap map) {
		String id;
		int j = 0;
		boolean ok = true;
		
		
		for(int i = 0;i< this.cs.size();i++) {			
			//try{
				id = this.cs.get(i).getFirst(); 
				
				while(j < map.getVehicles().size()  && ok) {
					
					if(map.getVehicles().get(j).getId().equals(id)) {
						ok = false;
						try {
							map.getVehicles().get(j).setContaminationClass(this.cs.get(i).getSecond());
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.getMessage();
						}
					}
					j++;
				}
				
			//}catch(Exception e )
				//System.out.println("This road not exits");
			ok = true;
		}
		
	}
	
	
	

	public List<Pair<String, Integer>> getCs() {
		return cs;
	}

	public void setCs(List<Pair<String, Integer>> cs) {
		this.cs = cs;
	}
}
