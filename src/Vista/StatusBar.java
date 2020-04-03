package Vista;

import java.awt.Component;

import simulator.control.Controller;

public class StatusBar extends Component {
	
	private Controller controller; 
	
	public StatusBar(Controller controller){
		
		this.setController(controller);
		
		
	}
	
	
	
	
	
	
	

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

}
