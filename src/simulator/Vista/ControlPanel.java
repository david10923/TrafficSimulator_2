	package simulator.Vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Image;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
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
	
	private JToolBar toolBar;
	
	private ChangeCO2ClassDialog cambioCo2;// para la ventana emergente de contaminacion
	private ChangeWeatherDialog cambioTiempo; // para la ventana emergente de tiempo 
	
	
	private boolean _stopped = false ;
	private final File archivo;
	
	
	 ControlPanel(Controller controller){
		 controller.addObserver(this);
		this.controller = controller;		
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		this.archivo = new File("src/icons");
		inicializaComponentes();
		
	}
	
	 
	 private void  LoadEventsbutton(){
		 
		 this.fichero = new JButton ();
	//////BOTON QUE CUANDO PULSES SALDRA UN JFILECHOOSER////
		 
		 
		 if(!archivo.exists()){
			 this.fichero.setText("Load Events");
		 }else{
			 this.fichero.setIcon(new ImageIcon("src/icons/open.png"));;// habria que pasarle por parametro el icono 
		 }
		 
			
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
			

	 }
	 private void Co2Button(){
		 this.contaminacion = new JButton();
		 
		 if(!archivo.exists()){
			 this.contaminacion.setText("Contaminacion");
		 }else{
			 this.contaminacion.setIcon(new ImageIcon("src/icons/co2class.png"));// habria que pasarle por parametro el icono 
		 }
		 
		
			this.contaminacion.setVisible(true);
			this.contaminacion.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {				
					Selected_Vehicle();
				}
				
			});
			
	 }
	 
	 private void WeatherButton(){
		 this.cambioContaminacion = new JButton();
		
		 // por si no encuentra el icono 
		 if(!archivo.exists()){
			this.cambioContaminacion.setText("Weather");
		 }else{
			 this.cambioContaminacion.setIcon( new ImageIcon("src/icons/weather.png"));
		 }
		 
		 
			this.cambioContaminacion.setVisible(true);
			this.cambioContaminacion.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {	//////////////////////// NO SE COMO VER SI SE HA PULSADO OK 
					Selected_Road();
				}
				
			});
	 }
	 
	 private void PlayButton(){
		 this.play= new JButton();
		 	
		 	 // por si no encuentra el icono 
		 	if(!archivo.exists()){
		 		this.play.setText("Play");
		 	}else{
		 		this.play.setIcon(new ImageIcon("src/icons/run.png"));
		 	}
		 	
		 	
			this.play.setVisible(true);
			this.play.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					enableToolBar(false);
					_stopped= false;				
					run_sim((int)numeroDeTicks.getValue());				
					
				}
				
			});
	 }
	 
	 private void StopButton(){
		 this.stop = new JButton();
		 if(!archivo.exists()){
			 this.stop.setText("Stop");
		 }else{
			 this.stop.setIcon( new ImageIcon("src/icons/stop.png"));
		 }
		 
		
			this.stop.setVisible(true);
			this.stop.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					stop();
					
				}
				
			});
	 }
	 
	 private void SpinnerTicks(){

			this.numeroDeTicks = new JSpinner(new SpinnerNumberModel(0,0,147483647,1));
			this.numeroDeTicks.setPreferredSize(new Dimension(80,30));
			this.numeroDeTicks.setMaximumSize(new Dimension(60,40));
			this.setVisible(true);
			
	 }
	 
	 private void closeButton(){
		 this.close = new JButton();
		 
		 if(!archivo.exists()){
			this.close.setText("Close");	
		 }else{
			 this.close.setIcon( new ImageIcon("src/icons/exit.png"));		
		 }
		
			this.close.setVisible(true);
			this.close.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent arg0) {
				      int i = JOptionPane.showConfirmDialog(null, "Do you want to close the app?", "EXIT", JOptionPane.YES_NO_OPTION,
		                        JOptionPane.ERROR_MESSAGE);

		                if (i == JOptionPane.YES_OPTION)
		                    System.exit(0);

					//new ExitOperation();
				}
			});
	 }
	
	private void inicializaComponentes(){
		
		this.toolBar = new JToolBar();
		
		LoadEventsbutton();		
		this.toolBar.add(this.fichero);
		this.toolBar.addSeparator();
		
		Co2Button();	
		this.toolBar.add(this.contaminacion);
		this.toolBar.addSeparator();
		
		
		WeatherButton();		
		this.toolBar.add(this.cambioContaminacion);
		this.toolBar.addSeparator();
		
				
		PlayButton();		
		this.toolBar.add(this.play);
		this.toolBar.addSeparator();
		
	
		StopButton();
		this.toolBar.add(this.stop);
		this.toolBar.addSeparator();
		
		
		
		/////////ETIQUETA DE LOS TICKS DEL SIMULADOR///
		this.ticks = new JLabel("Ticks"); 
		this.ticks.setSize(new Dimension(10,5));
		this.ticks.setVisible(true);
		
		this.toolBar.add(this.ticks);
		this.toolBar.addSeparator();
		
		
		SpinnerTicks();
		this.toolBar.add(this.numeroDeTicks);
		
	
		
		closeButton();
		toolBar.add(Box.createGlue());
		toolBar.add(this.close);
		 
		
		this.add(toolBar);
		//this.add(this.toolBar,BorderLayout.NORTH);
		
		
		
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
