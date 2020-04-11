package simulator.Vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver {
	
	private Controller controller; 
	private JLabel ticks ; 
	
	
	public StatusBar(Controller controller){
		this.controller = controller; 
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		inicializa();
		
	}
	
	public void  inicializa(){
		
		//ETIQUETA PARA EL TIMEPO DE EJECUCION ///
		this.ticks = new JLabel ("Ticks : "+controller.get_sim().getTime_of_simulation());
		this.ticks.setSize(new Dimension(10,5));
		this.ticks.setVisible(true);
		this.add(this.ticks,BorderLayout.WEST);
		
		///EVENTO QUE SE HA AÑADIDO ///
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
