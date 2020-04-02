package simulator.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import Exceptions.InvalidArgumentException;

public class Junction extends SimulatedObject {
	private int xCoor;
	private int yCoor;
	private List<Road> IncomingRoadList;
	private Map<Junction, Road> OutgoingRoadList;
	private List<List<Vehicle>> QueueList;
	private int TrafficLight;
	private int Last_TrafficLight_change;
	private LightSwitchingStrategy Strategy_of_Change;
	private DequeingStrategy Strategy_of_droping_vehicles;
	private Map<Road, List<Vehicle>> mapOfQueueRoad;

	private final int ReedLight = -1;

	Junction(String id, LightSwitchingStrategy isStrategy, DequeingStrategy dqStrategy, int xCoor, int yCoor)
			throws InvalidArgumentException {
		super(id);

		if (isStrategy == null || dqStrategy == null)
			throw new InvalidArgumentException("There are some null atributes while creating the juction");

		else if (xCoor < 0 || yCoor < 0) {
			throw new InvalidArgumentException("There are some negatives values while creating");
		} else {

			this.setyCoor(xCoor);
			this.setyCoor(yCoor);

			this.OutgoingRoadList = new HashMap<Junction, Road>();
			this.QueueList = new ArrayList<List<Vehicle>>();
			this.IncomingRoadList = new ArrayList<Road>();
			this.mapOfQueueRoad = new HashMap<Road, List<Vehicle>>();
			
			this.QueueList = new ArrayList<List<Vehicle>>();
			

			this.Strategy_of_droping_vehicles = dqStrategy;
			this.Strategy_of_Change = isStrategy;
			
			this.Last_TrafficLight_change = 0;
			this.TrafficLight = -1;
		}

	}

	@Override
	void advance(int time) {

		if (this.TrafficLight != this.ReedLight) {
			if(!this.QueueList.isEmpty()) {
				List<Vehicle> q = this.QueueList.get(this.TrafficLight);
				List<Vehicle> list = new ArrayList<Vehicle>();

				list = this.Strategy_of_droping_vehicles.dequeue(q);

				for (int i = 0; i < list.size(); i++) {
					try {
						list.get(i).moveToNextRoad();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					this.QueueList.get(this.TrafficLight).remove(list.get(i));
					

				}
				
			}
			
		}
		
		/*

		if(this.TrafficLight != this.Strategy_of_Change.chooseNextGreen(this.IncomingRoadList, this.QueueList,
				this.TrafficLight, this.Last_TrafficLight_change, time)) {
			
			this.TrafficLight =  this.Strategy_of_Change.chooseNextGreen(this.IncomingRoadList, this.QueueList,
					this.TrafficLight, this.Last_TrafficLight_change, time);
			
			this.Last_TrafficLight_change = time;
		}
		*/
		
		int nextGreen= this.Strategy_of_Change.chooseNextGreen(this.IncomingRoadList, this.QueueList,
				this.TrafficLight, this.Last_TrafficLight_change, time);
		
		if(nextGreen  != this.TrafficLight){
			this.TrafficLight = nextGreen; 
			this.Last_TrafficLight_change = time; 
			
		}
				
		
	
		
		
		
		//this.Last_TrafficLight_change++;
		

	}

	@Override
	public JSONObject report() {

		JSONObject Junction = new JSONObject();

		Junction.put("id", this._id);

		if (this.TrafficLight == this.ReedLight) {
			Junction.put("green", "none");
		} else {
			Junction.put("green", this.IncomingRoadList.get(TrafficLight).getId());
		}

		Junction.put("queues",getQueues() );

	
		return Junction;
	}
	

    JSONArray getQueues() {

        JSONArray arr = new JSONArray();

        for (int i = 0; i < this.mapOfQueueRoad.size(); i++) {
        	
            arr.put(reportQueues(this.IncomingRoadList.get(i)));
        }

        return arr;

    }

    JSONObject reportQueues(Road r) {

        JSONObject obj = new JSONObject();

        obj.put("road", r.getId());
        obj.put("vehicles", getVehiclesList(r));

        return obj;

    }

  
    JSONArray getVehiclesList(Road r) {

        JSONArray list = new JSONArray();

        //for (Vehicle v : this.mapOfQueueRoad.get(r))
        for(int i = 0 ;i < this.mapOfQueueRoad.get(r).size();i++){
        	 list.put(this.mapOfQueueRoad.get(r).get(i).getId());
        }
           

        return list;

    }
    
    
    

	void addIncomingRoad(Road r) throws Exception {
		int indexOfTheRoad;

		if (r.getDestination() != this) {
			throw new Exception("The road that you specified is not an incoming road");
		} 
		
		IncomingRoadList.add(r);
		List<Vehicle> cola = new ArrayList<Vehicle>();	
	
		QueueList.add(cola);
		this.mapOfQueueRoad.put(r, cola);
		
		
		
		
		

	}

	void addOutgoingRoad(Road r) throws Exception {
		
		if(!this.OutgoingRoadList.isEmpty()){
			if ((this.OutgoingRoadList.containsKey(r.getDestination()))){// si alguna carretera va al cruce o
																						// r es un cruce entrante
				throw new Exception("The road can not be a OutgoingRoad");
			}
			if((r.getSource() != this)){
				throw new Exception("The road can not be a OutgoingRoad");
			}
		
		}
		
			this.OutgoingRoadList.put(r.getDestination(), r);

		}

	public void enter(Vehicle v) {
		
		mapOfQueueRoad.get(v.getRoad()).add(v);
		
	
		/*
		 * boolean ok = false;
		int i = 0;
		List<Vehicle> lista = new ArrayList<Vehicle>();
		lista = this.mapOfQueueRoad.get(v.getRoad());
		

			while (i < this.QueueList.size() && ok) {
				if (lista == this.QueueList.get(i)) {
					ok = true;
				}
			}

			lista.add(v);	
			

			try {
				v.getRoad().enter(v);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
			
			this.QueueList.set(i, lista);
			this.mapOfQueueRoad.put(v.getRoad(), lista);
			
			
			
		//}
		 * 
		 */

	
	}

	public Road roadTo(Junction j) {
		
		return this.OutgoingRoadList.get(j);
	}
	

	public int getyCoor() {

		return yCoor;
	}

	public void setyCoor(int yCoor) {
		this.yCoor = yCoor;
	}

	public int setxCoor() {
		return xCoor;
	}

	public void setxCoor(int xCoor) {
		this.xCoor = xCoor;
	}

	public List<Road> getIncomingRoadList() {
		return IncomingRoadList;
	}

	public void setRoadList(List<Road> roadList) {
		IncomingRoadList = roadList;
	}

	public Map<Junction, Road> getOutgoingRoadList() {
		return OutgoingRoadList;
	}

	public void setOutgoingRoadList(Map<Junction, Road> outgoingRoadList) {
		OutgoingRoadList = outgoingRoadList;
	}

	public List<List<Vehicle>> getQueueList() {
		return QueueList;
	}

	public void setQueueList(List<List<Vehicle>> queueList) {
		QueueList = queueList;
	}

	public int getTrafficLight() {
		return TrafficLight;
	}

	public void setTrafficLight(int trafficLight) {
		TrafficLight = trafficLight;
	}

	public int getLast_TrafficLight_change() {
		return Last_TrafficLight_change;
	}

	public void setLast_TrafficLight_change(int last_TrafficLight_change) {
		Last_TrafficLight_change = last_TrafficLight_change;
	}

	public LightSwitchingStrategy getStrategy_of_Change() {
		return Strategy_of_Change;
	}

	public void setStrategy_of_Change(LightSwitchingStrategy strategy_of_Change) {
		Strategy_of_Change = strategy_of_Change;
	}

	public DequeingStrategy getStrategy_of_droping_vehicles() {
		return Strategy_of_droping_vehicles;
	}

	public void setStrategy_of_droping_vehicles(DequeingStrategy strategy_of_droping_vehicles) {
		Strategy_of_droping_vehicles = strategy_of_droping_vehicles;
	}

	public Map<Road, List<Vehicle>> getMapOfQueueRoad() {
		return mapOfQueueRoad;
	}

	public void setMapOfQueueRoad(Map<Road, List<Vehicle>> mapOfQueueRoad) {
		this.mapOfQueueRoad = mapOfQueueRoad;
	}

	/*
	 * public Road getValue(Junction j) { return this.OutgoingRoadList.get(j); }
	 */

	
}
