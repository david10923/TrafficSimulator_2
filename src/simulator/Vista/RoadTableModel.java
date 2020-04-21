package simulator.Vista;

import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;



public class RoadTableModel extends AbstractTableModel implements TrafficSimObserver {

	Controller controller ;
	
	private String[] _colNames = { "Id","Location","Itinerary","CO2 Class","Max Speed","Speed","Total CO2","Distance" };
	private List<Vehicle>vehicles;	
	
	
	RoadTableModel(Controller controller){		
		//this.controller.addObserver(this);
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
		return null;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
	
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
