package simulator.Vista;

import java.awt.Frame;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver {
	

	private List<Event>_events;	
	private String[] _colNames = { "Time", "Desc." };
	
	public EventsTableModel(Controller _ctrl) {	
		_ctrl.addObserver(this);
	}
	
	@Override
	public int getColumnCount() {
		return _colNames.length;
	}

	@Override
	public int getRowCount() {		
		return _events == null ? 0 : _events.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = _events.get(rowIndex).getTime();
			break;
		case 1:
			s = _events.get(rowIndex).toString();
			break;
		}
		return s;
	}
	
	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}
	
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		
			this._events = events; 			
		
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) { // para notificar que los datos de la tabla han cambiado
		
			this._events = events; 			
			fireTableDataChanged();			
		
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		
			this._events = events; 			
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
