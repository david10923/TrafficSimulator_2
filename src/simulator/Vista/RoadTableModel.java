package simulator.Vista;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;



public class RoadTableModel extends AbstractTableModel implements TrafficSimObserver {

	
	private String[] _colNames = {"Id","Length","Weather","Max.Speed","Speed Limit","Total CO2","CO2 Limit"};
	private List<Road>road;	
	
	
	RoadTableModel(Controller controller){		
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
		return road == null ? 0 : road.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = road.get(rowIndex).getId();
			break;
		case 1:
			s = road.get(rowIndex).getLength();
			break;
		case 2: 			
			s = road.get(rowIndex).getEnviromental_Conditions().name();
			break;
		case 3 : 
			s = road.get(rowIndex).getMax_Speed();
			break;
		case 4: 
			s = road.get(rowIndex).getCurrent_Max_Speed();
			break;
		case 5 : 
			s = road.get(rowIndex).getGlobal_Pollution();
			break ; 
		case 6: 
			s = road.get(rowIndex).getMasive_Pollution();
			break;
		}
		return s;
		
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {		
			this.road = map.getRoads();
			fireTableDataChanged();		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {		
			this.road = map.getRoads();
			fireTableDataChanged();	
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {	
			this.road = map.getRoads();
			fireTableDataChanged();		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {	
			this.road = map.getRoads();
			fireTableDataChanged();		
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
