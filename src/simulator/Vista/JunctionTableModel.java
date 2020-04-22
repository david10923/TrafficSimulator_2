package simulator.Vista;

import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionTableModel extends AbstractTableModel implements TrafficSimObserver{
	
	
	private String[] _colNames = {"Id","Green","Queues"};
	private List<Junction>junction;	
	
	
	public JunctionTableModel(Controller _ctrl) {		
		_ctrl.addObserver(this);
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
		return junction == null ? 0 : junction.size();
	}

	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = junction.get(rowIndex).getId();
			break;
		case 1:
			//s = junction.get(rowIndex).getIncomingRoadList().get();
			break;
		}
		return s;
		
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
		if(e.toString().equals("New junction")){ // ver si tambien tiene que tener el id
			// tengo que añadirle una nueva fila a las junction
			
		}
		
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
