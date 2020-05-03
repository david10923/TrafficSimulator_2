package simulator.Vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver {
	
	
	private JLabel ticks ; 
	private JLabel etiquetaEventos;
	
	
	public StatusBar(Controller controller){
		controller.addObserver(this); 
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		inicializa();
		
	}
	
	private void  inicializa(){
		
		//Box caja1 = Box.createHorizontalBox();	
		
				
		//ETIQUETA PARA EL TIMEPO DE EJECUCION ///
		//this.ticks = new JLabel ("Ticks : "+ controller.get_sim().getTime_of_simulation());
		this.ticks = new JLabel();
		this.ticks.setSize(new Dimension(10,5));
		this.ticks.setVisible(true);
		this.add(this.ticks,BorderLayout.WEST);
		
		//caja1.add(this.ticks);
		//caja1.add(Box.createHorizontalStrut(20));
		
		///EVENTO QUE SE HA AÑADIDO ///
		this.etiquetaEventos = new JLabel("Welcome!");
		this.etiquetaEventos.setSize(new Dimension(10,5));
		this.etiquetaEventos.setVisible(true);
		this.add(this.etiquetaEventos,BorderLayout.CENTER);
		
		
		//caja1.add(this.etiquetaEventos);
		
		//this.add(caja1);
		
		
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		this.ticks.setText("Ticks: "+  time);
		etiquetaEventos.setText(" ");
		
	}



	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		this.ticks.setText("Ticks: "+  time);	
		etiquetaEventos.setText(" ");
	}



	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		this.ticks.setText("Ticks: "+  time);
		this.etiquetaEventos.setText("Event added :"+ e.toString());
	}


	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {		
		this.ticks.setText("Ticks: "+  time);
	}


	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onError(String err) {
		JOptionPane.showMessageDialog(this, err);
		
	}

}
