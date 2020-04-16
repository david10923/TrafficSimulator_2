package simulator.Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import simulator.misc.Pair;
import simulator.model.Road;
import simulator.model.TrafficSimulator;
import simulator.model.Vehicle;
import simulator.model.Weather;

public class ChangeWeatherDialog extends JDialog  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton ok; 
	private JButton cancelar ; 
	private JLabel info ;
	private JSpinner ticks; 	
	private JLabel infoRoad; 
	private JLabel infoweather; 
	private JLabel infoTicks;
	private String INFO= "Schedule an event to change the weather  of a road after  a given number of simulation ticks from now.";
	

	private int status; 
	private JComboBox<Road> road ;
	private DefaultComboBoxModel<Road> roadModel ;
	
	private JComboBox<Weather> weather;
	private DefaultComboBoxModel<Weather> weatherModel;
	
	public ChangeWeatherDialog(JPanel controlPanel){
		super();
		iniciarVentana();
		anadircomponentes();
		
	}
	
	
	private  void iniciarVentana(){
		this.setBounds(400,300,650,150);
		this.setVisible(true);	
		this.setLayout(new FlowLayout());
		this.setTitle("Change Road Weather");
	
	}
	
	private void anadircomponentes(){
		
		Box caja = Box.createHorizontalBox();
		
		/// LA INFO DEL TEXTO /////
		this.info = new  JLabel(INFO);
		this.add(this.info); 
		
		///LOS VEHICLES EN EL CENTRO /////
		this.infoRoad = new JLabel("Road :");
		this.roadModel = new DefaultComboBoxModel<>();
		this.road = new JComboBox<>(this.roadModel);
		
		this.add(this.road);
		
		caja.add(Box.createHorizontalStrut(100));
		caja.add(this.infoRoad); 
		caja.add(Box.createHorizontalStrut(4));
		caja.add(this.road);		
		caja.add(Box.createHorizontalStrut(10));
		
		//CO2 // 
		this.infoweather = new JLabel("Weather :");
		this.weatherModel = new DefaultComboBoxModel<>();
		this.weather = new JComboBox<>(this.weatherModel);
		
		this.add(this.weather);
		
		caja.add(this.infoweather); 
		caja.add(Box.createHorizontalStrut(4));
		caja.add(this.weather);	
		caja.add(Box.createHorizontalStrut(10));
		
		
		//AÑADIMOS LOS TICKS//
		this.infoTicks = new JLabel("Ticks :");
		this.ticks = new JSpinner (new SpinnerNumberModel(0,0,147483647,1));
		caja.add(this.infoTicks); 
		caja.add(this.ticks);	
		caja.add(Box.createHorizontalStrut(200));
		
		this.add(caja);
		
		
		////AÑADIR LOS BOTONES///
		this.cancelar = new JButton("Cancelar");
		this.add(this.cancelar,BorderLayout.SOUTH);
		this.cancelar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				status = 0;
				ChangeWeatherDialog.this.setVisible(false);
			}
			
		});
		
		this.ok = new JButton("Ok");
		this.add(this.ok);
		this.ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(weatherModel.getSelectedItem() != null){
					status = 1;
					ChangeWeatherDialog.this.setVisible(false);
				}
				
				
			}
			
		});
		
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//salta exception aqui
	}
	
	
	/////// POR SI CAMBIAN LOS ELEMENTOS DEL JCOMBOBOX , DEL WEATHER NO SE HARIA YA QUE SIEMPRE ESTAN LOS MISMOS //
	
	public int open(List<Road> roads){
		
		this.roadModel.removeAllElements();
		
		for (Road r : roads){
			this.roadModel.addElement(r);
		}
		
		//setLocation(getParent().getLocation().x +10 , getParent().getLocation().y+10);
		setVisible(true);
		
		return status;
	}
		
	
	
	//////// METODOS ANADIDOS PARA LUEGO PODER COGER LA INFORMACION EN EL CONTROL PANEL /////

	Road getRoad(){
		return (Road) roadModel.getSelectedItem();
	}
	
	Weather getWeather(){
		return (Weather) weatherModel.getSelectedItem();
	}
	
	
	public JSpinner getTicks() {
		return ticks;
	}


	public void setTicks(JSpinner ticks) {
		this.ticks = ticks;
	}


}
