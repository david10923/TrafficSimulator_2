package simulator.Vista;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class ExitOperation extends JDialog implements ActionListener{
	
	private JButton ok ;
	private JButton cancelar ; 	
	private JLabel info ;
	private String INFO = "Do you want to close the app?";
	
	
	public ExitOperation(){
		this.setTitle("Exit dialog");
		this.setBounds(400,300,400,100);
		this.setLayout(new FlowLayout());		
		this.setVisible(true);
		
		inicializarBotones();
	}
	
	public void  inicializarBotones(){
			
		
		
		/// LA INFO DEL TEXTO /////
		this.info = new  JLabel(INFO);
		this.add(this.info); 
		
		this.add(Box.createHorizontalStrut(400));
		
		///EL BOTON DE ACEPTAR //		
		this.ok = new JButton ("Confirm");
		this.add(this.ok);
		this.ok.addActionListener(this);
		
		//EL BOTON DE CANCELAR///
		this.cancelar= new JButton("Cancel");
		this.add(this.cancelar);
		this.cancelar.addActionListener(this);;
		
	
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object botonPulsado = e.getSource();
		
		if(botonPulsado == this.ok){
			System.exit(0);
		
		}else{
			this.dispose();
		}
		
	}

}
