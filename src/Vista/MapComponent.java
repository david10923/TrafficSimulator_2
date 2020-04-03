package Vista;

import javax.swing.JComponent;

import simulator.control.Controller;

public class MapComponent extends JComponent {
	
	private Controller controller;
	
	public MapComponent(Controller controller){
		super();
		
		this.controller = controller;
		
	}

}
