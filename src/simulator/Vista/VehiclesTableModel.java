package simulator.Vista;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.NewVehicleEvent;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

public class VehiclesTableModel extends AbstractTableModel implements  TrafficSimObserver{
	
	
	
	private String[] _colNames = {"Id","State","Itinerary","CO2 Class","Max Speed","Speed","Total CO2","Distance"};
	private List<Vehicle>vehicles;
	
	
	public VehiclesTableModel(Controller controller){		
		controller.addObserver(this);		
	}

	

	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}
	
	@Override
	public int getColumnCount() {
		return _colNames.length;
	}



	@Override
	public int getRowCount() {
		return vehicles == null ? 0 : vehicles.size();
	}



	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = vehicles.get(rowIndex).getId();
			break;
		case 1:
			s= vehicles.get(rowIndex).getStatus();
			
			 if(s.equals(VehicleStatus.TRAVELING)){				
				return s + ":" +vehicles.get(rowIndex).getLocalization();
			}
			else if (s.equals(VehicleStatus.WAITING)){
				return s + ":"+vehicles.get(rowIndex).getRoad().getDest();
				
			}else if(s.equals(VehicleStatus.ARRIVED) || s.equals(VehicleStatus.PENDING)){
				return s;
			}	
			 
			break;
		case 2 :			
				s = vehicles.get(rowIndex).getItinerary().toString();					
			break; 
		case 3 : 
			 s = vehicles.get(rowIndex).getDegree_of_Pollution();
			break ; 
		case 4 : 
			 s = vehicles.get(rowIndex).getMax_Speed();
			break ; 
		case 5 : 
			s = vehicles.get(rowIndex).getCurrent_Speed();
			break;
		case 6 : 
			 s = vehicles.get(rowIndex).getPollution();
			break; 
		case 7: 
			 s = vehicles.get(rowIndex).getGlobal_distance_traveled();
			break; 
		}
		return s;
		
	}

	

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		
			this.vehicles = map.getVehicles();
			fireTableDataChanged();		
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		
			this.vehicles = map.getVehicles();
			fireTableDataChanged();		
	
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
	
			this.vehicles = map.getVehicles();
			fireTableDataChanged();							
		
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		
			this.vehicles = map.getVehicles();
			fireTableDataChanged();						
		
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		
	}

	@Override
	public void onError(String err) {
		
		
	}



}
