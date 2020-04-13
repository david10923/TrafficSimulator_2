package simulator.Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import simulator.misc.Pair;
import simulator.model.TrafficSimulator;
import simulator.model.Vehicle;

public class ChangeWeatherDialog extends JDialog  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton ok; 
	private JButton cancelar ; 
	private JLabel info ;
	private JSpinner road ; 
	private JSpinner weather;
	private JSpinner ticks; 	
	private JLabel infoRoad; 
	private JLabel infoweather; 
	private JLabel infoTicks;
	private String INFO= "Schedule an event to change the weather  of a road after  a given number of simulation ticks from now.";
	
	
	//private TrafficSimulator sim; 

	
	public ChangeWeatherDialog(){
		iniciarVentana();
		anadircomponentes();
		
	}
	
	
	public void iniciarVentana(){
		this.setBounds(400,300,650,150);
		this.setVisible(true);	
		this.setLayout(new FlowLayout());
		this.setTitle("Change Road Weather");
	
	}
	
	public void anadircomponentes(){
		
		Box caja = Box.createHorizontalBox();
		
		/// LA INFO DEL TEXTO /////
		this.info = new  JLabel(INFO);
		this.add(this.info); 
		
		///LOS VEHICLES EN EL CENTRO /////
		this.infoRoad = new JLabel("Road :");
		this.road = new JSpinner(); //hay que poner r
		caja.add(Box.createHorizontalStrut(100));
		caja.add(this.infoRoad); 
		caja.add(Box.createHorizontalStrut(4));
		caja.add(this.road);		
		caja.add(Box.createHorizontalStrut(10));
		
		//CO2 // 
		this.infoweather = new JLabel("Weather :");
		this.weather = new JSpinner(new SpinnerNumberModel(0,0,10,1));
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
				dispose();
			}
			
		});
		
		this.ok = new JButton("Ok");
		this.add(this.ok);
		this.ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) { // crear el evento 
				
				
			}
			
		});
	
		
		
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//salta exception aqui
	}
	
	

	public JSpinner getTicks() {
		return ticks;
	}


	public void setTicks(JSpinner ticks) {
		this.ticks = ticks;
	}


}
