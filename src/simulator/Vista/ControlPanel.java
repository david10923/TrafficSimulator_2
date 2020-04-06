package simulator.Vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver ,ActionListener {
	
	private Controller controller;
	private JButton fichero; 
	private JButton contaminacion; 
	private JButton cambioContaminacion; 
	private JButton play; 
	private JButton stop;
	private JLabel ticks; 
	private JTextField numeroDeTicks; // NO ES DE TIPO TYEXT FIELD PERO NO SE DE QUE TIPO ES 
	
	
	public ControlPanel(Controller controller){
		this.controller = controller;		
		this.setLayout(new FlowLayout());
		this.setVisible(true);
	}
	
	
	public void inicializaComponentes(){
		//////BOTON QUE CUANDO PULSES SALDRA UN JFILECHOOSER////
		this.fichero = new JButton ();// habria que pasarle por parametro el icono 
		this.fichero.setVisible(true);
		this.fichero.addActionListener(this);
		this.add(this.fichero);
		
		////// BOTON DE CONTAMINACION////
		this.contaminacion = new JButton();// habria que pasarle por parametro el icono 
		this.contaminacion.setVisible(true);
		this.contaminacion.addActionListener(this);
		this.add(this.contaminacion);
		
		//BOTON DE CAMBIO DE CONTAMINACION ///		
		this.cambioContaminacion = new JButton();
		this.cambioContaminacion.setVisible(true);
		this.cambioContaminacion.addActionListener(this);
		this.add(this.cambioContaminacion);
		
		/////BOTON DEL PLAY //////
		this.play= new JButton();
		this.play.setVisible(true);
		this.cambioContaminacion.addActionListener(this);
		this.add(this.play);
		
		
		///BOTON DE STOP 
		this.stop = new JButton();
		this.stop.setVisible(true);
		this.stop.addActionListener(this);
		this.add(this.stop);
		
		/////////ETIQUETA DE LOS TICKS DEL SIMULADOR///
		this.ticks = new JLabel("Ticks"); 
		this.ticks.setSize(new Dimension(10,5));
		this.ticks.setVisible(true);
		this.add(this.ticks);
		
		/////////JTEXT FIELD DE LOS TICKS ////
		this.numeroDeTicks = new JTextField();
		this.setVisible(true);
		this.add(this.numeroDeTicks);
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
		// TODO Auto-generated method stub
		
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


	@Override
	public void actionPerformed(ActionEvent e) { //////PREGUNTAR SI ESTÁ BIEN HECHO DE ESTA MANERA ////
		Object botonPulsado = e.getSource();
		
		if(botonPulsado == this.fichero){ /// EL ICONO DE LA CARPETA 
			
			JFileChooser fc = new JFileChooser ();
			int respuesta  = fc.showOpenDialog(this); 
			
			if(respuesta == JFileChooser.APPROVE_OPTION){
				File archivoElegido = fc.getSelectedFile();
				controller.reset();
				
				//controller.loadEvents(in); // el imput sera del fichero pero no es de tipo Imput
				///// ¿ HAY QUE AÑADIR EL NOMBRE DEL FICHERO ELEGIODO ????///
			}
			
			
		}
		else if (botonPulsado == this.contaminacion){
			
		}
		else if (botonPulsado == this.cambioContaminacion){
			
		}
		else if (botonPulsado == this.play){
			
		}
		else if(botonPulsado == this.stop){
			
		}
	}

}
