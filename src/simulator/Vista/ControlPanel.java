package simulator.Vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.Weather;

public class ControlPanel extends JPanel implements TrafficSimObserver {
	
	private Controller controller;
	private JButton fichero; 
	private JButton contaminacion; 
	private JButton cambioContaminacion; 
	private JButton play; 
	private JButton stop;
	private JLabel ticks; 
	private JSpinner numeroDeTicks; 
	private JButton close;
	
	private ChangeCO2ClassDialog cambioCo2;// para la ventana emergente de contaminacion
	private ChangeWeatherDialog cambioTiempo; // para la ventana emergente de tiempo 
	
	
	private boolean _stopped = false ;
	
	
	
	 ControlPanel(Controller controller){
		 controller.addObserver(this);
		this.controller = controller;		
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		inicializaComponentes();
	}
	
	
	private void inicializaComponentes(){
		Box caja1 = Box.createHorizontalBox();	
		
		//////BOTON QUE CUANDO PULSES SALDRA UN JFILECHOOSER////
		this.fichero = new JButton (new ImageIcon("resources/icons/open.png"));// habria que pasarle por parametro el icono 
		this.fichero.setVisible(true);
		
		this.fichero.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser ();
				File dir = new File("./resources/examples");
				fc.setCurrentDirectory(dir.getAbsoluteFile());
				
				int respuesta  = fc.showOpenDialog(null); 
				
				if(respuesta == JFileChooser.APPROVE_OPTION){
					File archivoElegido = fc.getSelectedFile();					
					//File dir = new File("src/resources/examples"); /// HAY QUE PONER LA RUTA DEL SRC
					//fc.setCurrentDirectory(dir.getAbsoluteFile());
					
					controller.reset();
					
					InputStream in;
					try {
					// FALTA VER SI EL ARCHIVO EXISTE//////
						in = new FileInputStream(archivoElegido);
						controller.loadEvents(in); 
					} catch (FileNotFoundException e1) {
						
						e1.getMessage();
					}
				
				}
			}
			
		});
		
		caja1.add(fichero);		
		caja1.add(Box.createHorizontalStrut(5));
		
		//////////////////////////////////////////////////////////////////////////////////////////////
		
		////// BOTON DE CONTAMINACION////
		this.contaminacion = new JButton(new ImageIcon("resources/icons/co2class.png"));// habria que pasarle por parametro el icono 
		this.contaminacion.setVisible(true);
		this.contaminacion.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {				
				Selected_Vehicle();
			}
			
		});
		
		caja1.add(contaminacion);		
		caja1.add(Box.createHorizontalStrut(5));
		
		//////////////////////////////////////////////////////////////////////////////
		
		//BOTON DE CAMBIO DE WEATHER ///		
		this.cambioContaminacion = new JButton(new ImageIcon("resources/icons/weather.png"));
		this.cambioContaminacion.setVisible(true);
		this.cambioContaminacion.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {	//////////////////////// NO SE COMO VER SI SE HA PULSADO OK 
				Selected_Road();
			}
			
		});
		caja1.add(this.cambioContaminacion);		
		caja1.add(Box.createHorizontalStrut(5));
		
		
		////////////////////////////////////////////////////////////////////////////////////
		
		/////BOTON DEL PLAY //////
		this.play= new JButton(new ImageIcon("resources/icons/run.png"));
		this.play.setVisible(true);
		this.play.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				enableToolBar(false);
				_stopped= false;				
				run_sim((int)numeroDeTicks.getValue());				
				
			}
			
		});
		caja1.add(this.play); 		
		caja1.add(Box.createHorizontalStrut(5));
		
		///////////////////////////////////////////////////////////////////////////
		
		///BOTON DE STOP 
		this.stop = new JButton(new ImageIcon("resources/icons/stop.png"));
		this.stop.setVisible(true);
		this.stop.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				stop();
				
			}
			
		});
		caja1.add(this.stop);
		caja1.add(Box.createHorizontalStrut(5));
		
		////////////////////////////////////////////////////////////////////////////////////
		
		/////////ETIQUETA DE LOS TICKS DEL SIMULADOR///
		this.ticks = new JLabel("Ticks"); 
		this.ticks.setSize(new Dimension(10,5));
		this.ticks.setVisible(true);
		caja1.add(this.ticks); 
		caja1.add(Box.createHorizontalStrut(5));
		
		////////////////////////////////////////////////////////////////////////////////////
		
		/////////JTEXT FIELD DE LOS TICKS ////
		this.numeroDeTicks = new JSpinner(new SpinnerNumberModel(0,0,147483647,1));
		this.numeroDeTicks.setMaximumSize(new Dimension(60,40));
		this.setVisible(true);
		caja1.add(this.numeroDeTicks);
		
		////////////////////////////////////////////////////////////////////////////////////
		
		///BOTON DE CERRAR LA APP///////////////////////////
		
		this.close = new JButton(new ImageIcon("resources/icons/exit.png"));		
		this.close.setVisible(true);
		this.close.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				new ExitOperation();
			}
			
		});
		this.add(this.close,BorderLayout.EAST);	
		this.add(caja1);
		
		
		
	}
	
	///////METODOS PARA LOS JDIALOG ///////////////////
	
	public void Selected_Vehicle(){
		ChangeCO2ClassDialog dialog = new ChangeCO2ClassDialog((Frame)SwingUtilities.getWindowAncestor(this));
		
		
		List<String>vehicle = new ArrayList<String>();
		
		for(int i = 0; i < controller.get_sim().getMap_of_roads().getVehicles().size(); i++){
			vehicle.add(controller.get_sim().getMap_of_roads().getVehicles().get(i).toString());
		}
		
	
		int status = dialog.open(vehicle);
		
		/*
		if(status==0 ){
			System.out.println("Canceled");			
		}
		else */if (status ==1){ 
			//System.out.println("Ha entrado en ok");
			
			/// TE DEVUELVE LA CLASE  DE CO2 , EL VEHICULO Y LOS TICKS ///////
			
			controller.crearEventoContaminacion(dialog.getVehicle(),(int)dialog.getTicks().getValue(),(int) dialog.getCo2Class().getValue());
		}
	}
	
	
	
	
	
	
	public void Selected_Road(){
		ChangeWeatherDialog dialog = new ChangeWeatherDialog((Frame)SwingUtilities.getWindowAncestor(this));
		
		List<String>road = new ArrayList<String>();
		List<Weather>weather = new ArrayList<Weather>();
		
		Weather arr [] = Weather.values();
		
		for(Weather w : arr){
			weather.add(w);
		}
		
		for(int i = 0; i < controller.get_sim().getMap_of_roads().getRoads().size(); i++){
			road.add(controller.get_sim().getMap_of_roads().getRoads().get(i).toString());
		}
		
		int status = dialog.open(road,weather);
		
		/*if(status==0 ){
			System.out.println("Canceled");			
		}
		else*/ if (status ==1){
			
			/// TE DEVUELVE LA CLASE  DE CO2 , EL VEHICULO Y LOS TICKS ///////
			 
			controller.crearEventoTiempo(dialog.getRoad(), dialog.getWeather(),(int)dialog.getTicks().getValue());
		}
	}

	private void run_sim( int n ) {
		if ( n > 0 && ! _stopped ) {
			try {
				controller.run(1);
			} catch (Exception e ) {
			// TODO show error message
			_stopped = true ;
			return ;
			}
			SwingUtilities.invokeLater( new Runnable() {
				@Override
				public void run() {
					run_sim(n-1);
				}
			});
		}
		else {
			enableToolBar(true);
			_stopped = true ;
		}
	}
	
	
		private void enableToolBar(boolean b) {			
				this.fichero.setEnabled(b);
				this.cambioContaminacion.setEnabled(b);
				this.contaminacion.setEnabled(b);
				this.play.setEnabled(b);
			
		}

	private void stop() {
			_stopped = true ;
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
	
	
	///////GETTTER Y SETTERS/////////////////////////////////////////////////////


	public ChangeCO2ClassDialog getCambioCo2() {
		return cambioCo2;
	}


	public void setCambioCo2(ChangeCO2ClassDialog cambioCo2) {
		this.cambioCo2 = cambioCo2;
	}


	public ChangeWeatherDialog getCambioTiempo() {
		return cambioTiempo;
	}


	public void setCambioTiempo(ChangeWeatherDialog cambioTiempo) {
		this.cambioTiempo = cambioTiempo;
	}


	

	
	

}
