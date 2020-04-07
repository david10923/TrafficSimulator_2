package simulator.Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class ChangeCO2ClassDialog extends JDialog implements ActionListener{
	
	private JButton ok; 
	private JButton cancelar ; 
	private JLabel info ;
	private JSpinner vehicle ; 
	private JSpinner Co2Class;
	private JSpinner ticks; 
	private JLabel infoVehicles; 
	private JLabel infoCo2; 
	private JLabel infoTicks;
	private String INFO= "Schedule an event to change the CO2 class of a vehicle after  a given numbre of simulation ticks from now.";
	
	
	public ChangeCO2ClassDialog (){
		iniciarVentana();
		anadircomponentes();
		
	}
	
	
	public void iniciarVentana(){
		this.setBounds(400,300,400,250);
		this.setVisible(true);	
		this.setLayout(new FlowLayout());
		this.setTitle("Change C02 Class");
	
	}
	
	public void anadircomponentes(){
		
		Box caja = Box.createHorizontalBox();
		
		/// LA INFO DEL TEXTO /////
		this.info = new  JLabel(INFO);
		this.add(this.info); 
		
		///LOS VEHICLES EN EL CENTRO /////
		this.infoVehicles = new JLabel("Vehicles :");
		this.vehicle = new JSpinner(); // COMO PONER V1
		caja.add(this.infoVehicles); 
		caja.add(this.vehicle);		
		caja.add(Box.createHorizontalStrut(10));
		
		//CO2 // 
		this.infoCo2 = new JLabel("C02  Class :");
		this.Co2Class = new JSpinner(new SpinnerNumberModel(0,0,10,1));
		caja.add(this.infoCo2); 
		caja.add(this.Co2Class);	
		caja.add(Box.createHorizontalStrut(10));
		
		
		//AÑADIMOS LOS TICKS//
		this.infoTicks = new JLabel("Ticks :");
		this.ticks = new JSpinner (new SpinnerNumberModel(0,0,147483647,1));
		caja.add(this.infoTicks); 
		caja.add(this.ticks);	
		caja.add(Box.createHorizontalStrut(50));
		
		this.add(caja);
		
		
		////AÑADIR LOS BOTONES///
		this.cancelar = new JButton("Cancelar");
		this.add(this.cancelar,BorderLayout.SOUTH);
		this.cancelar.addActionListener(this);
		this.ok = new JButton("Ok");
		this.add(this.ok);
		this.cancelar.addActionListener(this);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);//salta exception aqui
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object botonPulsado = e.getSource();
		
		if(botonPulsado ==this.cancelar){
			
		}else if (botonPulsado == this.ok){
			
		}
		
	}

}
