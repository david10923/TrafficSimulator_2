package simulator.Vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

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
	private JSpinner numeroDeTicks; 
	
	
	public ControlPanel(Controller controller){
		this.controller = controller;		
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		inicializaComponentes();
	}
	
	
	public void inicializaComponentes(){
		Box caja1 = Box.createHorizontalBox();	
		
		//////BOTON QUE CUANDO PULSES SALDRA UN JFILECHOOSER////
		this.fichero = new JButton (new ImageIcon("resources/icons/open.png"));// habria que pasarle por parametro el icono 
		this.fichero.setVisible(true);
		this.fichero.addActionListener(this);
		caja1.add(fichero);
		
		caja1.add(Box.createHorizontalStrut(5));
		
		////// BOTON DE CONTAMINACION////
		this.contaminacion = new JButton(new ImageIcon("resources/icons/co2class.png"));// habria que pasarle por parametro el icono 
		this.contaminacion.setVisible(true);
		this.contaminacion.addActionListener(this);
		caja1.add(contaminacion);		
		caja1.add(Box.createHorizontalStrut(5));
		
		//BOTON DE CAMBIO DE CONTAMINACION ///		
		this.cambioContaminacion = new JButton(new ImageIcon("resources/icons/weather.png"));
		this.cambioContaminacion.setVisible(true);
		this.cambioContaminacion.addActionListener(this);
		caja1.add(this.cambioContaminacion);		
		caja1.add(Box.createHorizontalStrut(5));
		
		
		/////BOTON DEL PLAY //////
		this.play= new JButton(new ImageIcon("resources/icons/run.png"));
		this.play.setVisible(true);
		this.cambioContaminacion.addActionListener(this);
		caja1.add(this.play); 		
		caja1.add(Box.createHorizontalStrut(5));
		
		
		
		///BOTON DE STOP 
		this.stop = new JButton(new ImageIcon("resources/icons/stop.png"));
		this.stop.setVisible(true);
		this.stop.addActionListener(this);
		caja1.add(this.stop);
		caja1.add(Box.createHorizontalStrut(5));
		
		/////////ETIQUETA DE LOS TICKS DEL SIMULADOR///
		this.ticks = new JLabel("Ticks"); 
		this.ticks.setSize(new Dimension(10,5));
		this.ticks.setVisible(true);
		caja1.add(this.ticks); 
		caja1.add(Box.createHorizontalStrut(5));
		
		
		/////////JTEXT FIELD DE LOS TICKS ////
		this.numeroDeTicks = new JSpinner(new SpinnerNumberModel(0,0,147483647,1));// QUE NO ES UN JtEXTFIELD
		this.numeroDeTicks.setMaximumSize(new Dimension(60,40));
		this.setVisible(true);
		caja1.add(this.numeroDeTicks);
		
	
		
		
		this.add(caja1);
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
		
		if(botonPulsado == this.fichero){ 
			
			JFileChooser fc = new JFileChooser ();
			int respuesta  = fc.showOpenDialog(this); 
			
			if(respuesta == JFileChooser.APPROVE_OPTION){
				File archivoElegido = fc.getSelectedFile();
				File dir = new File("src/resources/examples"); /// HAY QUE PONER LA RUTA DEL SRC
				fc.setCurrentDirectory(dir.getAbsoluteFile());
				
				controller.reset();
				
				InputStream in;
				try {
				// FALTA VER SI EL ARCHIVO EXISTE//////
					in = new FileInputStream(archivoElegido);
					controller.loadEvents(in); 
				} catch (FileNotFoundException e1) {
					
					e1.getMessage();
				}
				
				///// ¿ HAY QUE AÑADIR EL NOMBRE DEL FICHERO ELEGIODO ????///
			}
			
			
		}
		else if (botonPulsado == this.contaminacion){
			new ChangeCO2ClassDialog();
		}
		else if (botonPulsado == this.cambioContaminacion){
			
		}
		else if (botonPulsado == this.play){
			
		}
		else if(botonPulsado == this.stop){
			
		}
	}

}
