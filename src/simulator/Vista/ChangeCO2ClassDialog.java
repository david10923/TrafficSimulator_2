package simulator.Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import extra.dialog.Dish;
import simulator.misc.Pair;
import simulator.model.NewSetContClassEvent;
import simulator.model.TrafficSimulator;
import simulator.model.Vehicle;

public class ChangeCO2ClassDialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton ok; 
	private JButton cancelar ; 
	private JLabel info ;	 
	private JSpinner Co2Class;
	private JSpinner ticks; 
	private JLabel infoVehicles; 
	private JLabel infoCo2; 
	private JLabel infoTicks;
	private String INFO= "Schedule an event to change the CO2 class of a vehicle after  a given number of simulation ticks from now.";
	
	
	private int status;
	private JComboBox<String> vehicle ;
	private DefaultComboBoxModel<String> vehicleModel ;
	
	
	 ChangeCO2ClassDialog(JPanel controlPanel){
		 super();
		iniciarVentana();
		anadircomponentes();
		
	}
	
	

	private void iniciarVentana(){
		this.setBounds(400,300,650,150);
		this.setVisible(true);	
		this.setLayout(new FlowLayout());
		this.setTitle("Change C02 Class");
	
	}
	
	private void anadircomponentes(){
		
		Box caja = Box.createHorizontalBox();
		
		/// LA INFO DEL TEXTO /////
		this.info = new  JLabel(INFO);
		this.add(this.info); 
		
		//////////////////////////////////////////////////////////
		
		//// EJEMPLO PARECIDO EN EL EXTRA JDIALOG////
		///LOS VEHICLES EN EL CENTRO /////
		this.infoVehicles = new JLabel("Vehicles :");
		
		this.vehicleModel  = new DefaultComboBoxModel<>();
		this.vehicle = new JComboBox<>(this.vehicleModel); 
		
		this.add(this.vehicle);
		
		
		caja.add(Box.createHorizontalStrut(100));
		caja.add(this.infoVehicles); 
		caja.add(Box.createHorizontalStrut(4));
		caja.add(this.vehicle);		
		caja.add(Box.createHorizontalStrut(10));
		
		
		////////////////////////////////////////////////////////////////////////
		
		//CO2 // 
		this.infoCo2 = new JLabel("C02  Class :");
		this.Co2Class = new JSpinner(new SpinnerNumberModel(0,0,10,1));
		caja.add(this.infoCo2); 
		caja.add(Box.createHorizontalStrut(4));
		caja.add(this.Co2Class);	
		caja.add(Box.createHorizontalStrut(10));
		
		
		
		/////////////////////////////////////////////////////////////////////////
		//AÑADIMOS LOS TICKS//
		this.infoTicks = new JLabel("Ticks :");
		this.ticks = new JSpinner (new SpinnerNumberModel(0,0,147483647,1));
		caja.add(this.infoTicks); 
		caja.add(this.ticks);	
		caja.add(Box.createHorizontalStrut(200));
		
		this.add(caja);
		
		
		
		//////////////////////////////////////////////////////////////////////////////
		////AÑADIR LOS BOTONES///
		this.cancelar = new JButton("Cancelar");
		this.add(this.cancelar,BorderLayout.SOUTH);
		this.cancelar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				status = 0;
				ChangeCO2ClassDialog.this.setVisible(false);
				
			}
			
		});
		
		
		this.ok = new JButton("Ok");
		this.add(this.ok);
		this.ok.addActionListener(new ActionListener (){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(vehicleModel.getSelectedItem() != null){
					status = 1; 
					ChangeCO2ClassDialog.this.setVisible(false);
				}
					
				
			}
			
			
		});
		
		
		//pack();
		setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//salta exception aqui
	}
	
	public int open(List<String> vehicles){
		
		this.vehicleModel.removeAllElements();
		
		for (String v : vehicles){
			this.vehicleModel.addElement(v);
		}
		
		
		setVisible(true);
	
		return status;
	}

	
	String getVehicle(){
		return  vehicleModel.getSelectedItem().toString();
	}

	public JSpinner getCo2Class() {
		return Co2Class;
	}


	public void setCo2Class(JSpinner co2Class) {
		Co2Class = co2Class;
	}


	public JSpinner getTicks() {
		return ticks;
	}


	public void setTicks(JSpinner ticks) {
		this.ticks = ticks;
	}


	

}
