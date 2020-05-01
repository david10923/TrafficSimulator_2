package simulator.Vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

public class MapByRoadComponent extends JComponent implements TrafficSimObserver {
	
	private static final int _JRADIUS = 10;
	
	
	private static final Color _BG_COLOR = Color.WHITE;
	private static final Color _JUNCTION_COLOR = Color.BLUE;
	//private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color _RED_LIGHT_COLOR = Color.RED;
	
	private static final Color _BLACK_COLOR = Color.BLACK;

	private RoadMap _map;

	private Image _car;
	private  int x1 = 50;
	
	

	private Image _weather_conditions ; 
	private Image _cont_class;
	
	
	public MapByRoadComponent (Controller controller){
		controller.addObserver(this);
		initGUI();
		
	}

	private void initGUI() {
		_car = loadImage("car.png");
	}

	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		////EL COLOR BLANCO DE FONDO //////////
		g.setColor(_BG_COLOR);
		
		///PARA HACER LAS LINEAS////
		g.clearRect(0, 0, getWidth(), getHeight());

		if (_map == null || _map.getJunctions().size() == 0) {
			g.setColor(Color.red);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			updatePrefferedSize();
			drawMap(g);
		}
	}

	private void drawMap(Graphics g) {
		drawRoads(g);
		drawVehicles(g);		
		
		
	}

	private void drawRoads(Graphics g) {
		for(int i = 0; i < _map.getRoads().size();i++){
			
			///DIBUJAR UNA LINEA //////////
			g.setColor(_BLACK_COLOR);
			g.drawLine(x1, (i+1)*x1,getWidth()-100,(i+1)*x1 );			
		
			
			
			//////CRUCE ORIGEN /////
			g.setColor( _JUNCTION_COLOR );
			g.fillOval(x1- _JRADIUS,((i+1)*x1 )- _JRADIUS / 2, _JRADIUS, _JRADIUS);
			
			
			
			/////////CRUCE DESTINO///
			
		
			if(_map.getRoads().get(i).getDestination().getTrafficLight() == i){ 
				g.setColor(_GREEN_LIGHT_COLOR);
			}else{			
				g.setColor(_RED_LIGHT_COLOR);
			}
						
			g.fillOval( getWidth()-100 - _JRADIUS, ((i+1)*x1 )- _JRADIUS / 2, _JRADIUS, _JRADIUS);
			
			
			g.setColor(_BLACK_COLOR);		
			
			
			//// PARA PONER LOS NOMBRES DE LOS JUNCTIONS ///			
			g.drawString(_map.getRoads().get(i).getSrc().getId(), x1, (i+1)*x1);			
			g.drawString(_map.getRoads().get(i).getDest().getId(), getWidth()-100, (i+1)*x1);
			
			///PARA PONER EL NOMBRE A LAS CARRETERAS ///
			g.drawString(_map.getRoads().get(i).getId(),x1-40,(i+1)*x1);
			
			////DIBUJAR UNA IMAGEN CON LAS CONDICIONES DE LA CARRETERA ////////
			switch(_map.getRoads().get(i).getEnviromental_Conditions()){
			case SUNNY:
				this._weather_conditions = loadImage("sun.png");
				break; 			
			case WINDY:
				this._weather_conditions = loadImage("wind.png");
				break;
			case STORM: 
				this._weather_conditions = loadImage("storm.png");
				break; 
			case CLOUDY: 
				this._weather_conditions = loadImage("cloud.png");
				break; 
			case RAINY: 
				this._weather_conditions = loadImage("rain.png");
				break; 
			}
			g.drawImage(this._weather_conditions, getWidth()-100+10,(i+1)*x1-20,32,32,this);
			
			//////IMAGEN PARA LA CONTAMINACION DE LA CARRETERA/////
			int A = _map.getRoads().get(i).getMasive_Pollution();
			int B = _map.getRoads().get(i).getGlobal_Pollution();
			
			int c = (int) Math.floor(Math.min((double)A/(1.0 +(double)B), 1.0) /0.19); 
			
			this._cont_class = loadImage("cont_"+ c + ".png");	
			
			g.drawImage(this._cont_class, getWidth()-100+50,(i+1)*x1-20,32,32,this);
			
			
			
			
		}

	}

	private void drawVehicles(Graphics g) {
		for (Vehicle v : _map.getVehicles()) {
			if (v.getStatus() != VehicleStatus.ARRIVED) {

				//caculamos la posicion relativa de los vehiculos
				
				Road r = v.getRoad();
				
				int x1 = r.getSrc().getX();
				
				int x2 = r.getDest().getX();
				
				
				int vX= x1 +(int) ((x2 - x1) *((double )v.getLocalization() / (double)  r.getLength()));
				
				
				g.drawImage(_car, vX, x1 - 6, 16, 16, this);
				g.drawString(v.getId(), vX, x1 - 6);
			}
		}
	}



	// this method is used to update the preffered and actual size of the component,
	// so when we draw outside the visible area the scrollbars show up
	private void updatePrefferedSize() {
		int maxW = 200;
		int maxH = 200;
		for (Junction j : _map.getJunctions()) {
			maxW = Math.max(maxW, j.getX());
			maxH = Math.max(maxH, j.getY());
		}
		maxW += 20;
		maxH += 20;
				
		if (maxW > getWidth() || maxH > getHeight()) {
		    setPreferredSize(new Dimension(maxW, maxH));
		   setSize(new Dimension(maxW, maxH));
		}
	}

	
	
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
			
		}
		return i;
	}

	public void update(RoadMap map) {
		_map = map;
		repaint();
	}


	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(map);
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(map);
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		update(map);
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}
	
	

}
