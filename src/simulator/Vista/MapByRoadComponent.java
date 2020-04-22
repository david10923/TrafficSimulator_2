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
	private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color _RED_LIGHT_COLOR = Color.RED;

	private RoadMap _map;

	private Image _car;
	
	
	

	public MapByRoadComponent (Controller controller){
		controller.addObserver(this);
		initGUI();
	}

	private void initGUI() {
		_car = loadImage("car_front.png");
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
		drawJunctions(g);
	}

	private void drawRoads(Graphics g) {
		for(int i = 0; i < _map.getRoads().size();i++){
			
			int x1 = 50;
			int x2 = getWidth()-100;
			
			
			///DIBUJAR UNA LINEA //////////
			g.drawLine(x1, (i+1)*x1, x2,(i+1)*x1 );
			g.setColor(_JUNCTION_COLOR);		
			
			g.fillOval(x1- _JRADIUS,((i+1)*x1 )- _JRADIUS / 2, _JRADIUS, _JRADIUS);
			
			g.fillOval(x2 - _JRADIUS, ((i+1)*x1 )- _JRADIUS / 2, _JRADIUS, _JRADIUS);
			
			g.setColor(_JUNCTION_LABEL_COLOR);
			
			//// PARA PONER LOS NOMBRES DE LOS JUNCTIONS ///
			g.drawString(_map.getRoads().get(i).getSrc().getId(), x1, (i+1)*x1);
			g.drawString(_map.getRoads().get(i).getSrc().getId(),x2, (i+1)*x1);
			
			
			
			
			
		}

	}

	private void drawVehicles(Graphics g) {
		for (Vehicle v : _map.getVehicles()) {
			if (v.getStatus() != VehicleStatus.ARRIVED) {

				// The calculation below compute the coordinate (vX,vY) of the vehicle on the
				// corresponding road. It is calculated relativly to the length of the road, and
				// the location on the vehicle.
				Road r = v.getRoad();
				int x1 = r.getSrc().getX();
				int y1 = r.getSrc().getY();
				int x2 = r.getDest().getX();
				int y2 = r.getDest().getY();
				double roadLength = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
				double alpha = Math.atan(((double) Math.abs(x1 - x2)) / ((double) Math.abs(y1 - y2)));
				double relLoc = roadLength * ((double) v.getLocalization()) / ((double) r.getLength());
				double x = Math.sin(alpha) * relLoc;
				double y = Math.cos(alpha) * relLoc;
				int xDir = x1 < x2 ? 1 : -1;
				int yDir = y1 < y2 ? 1 : -1;

				int vX = x1 + xDir * ((int) x);
				int vY = y1 + yDir * ((int) y);

				// Choose a color for the vehcile's label and background, depending on its
				// contamination class
				//int vLabelColor = (int) (25.0 * (10.0 - (double) v.getDegree_of_Pollution()));
				//g.setColor(new Color(0, vLabelColor, 0));

				// draw an image of a car (with circle as background) and it identifier
				//g.fillOval(vX - 1, vY - 6, 14, 14);
				g.drawImage(_car, vX, vY - 6, 12, 12, this);
				g.drawString(v.getId(), vX, vY - 6);
			}
		}
	}

	private void drawJunctions(Graphics g) {
/*
		for (Junction j : _map.getJunctions()) {

			//COORDENADAS DEL JUNCTION
			int x = j.getX();
			int y = j.getY();

			// draw a circle with center at (x,y) with radius _JRADIUS
			g.setColor(_JUNCTION_COLOR);			
			g.fillOval(x - _JRADIUS, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
			
			

			// draw the junction's identifier at (x,y)
			g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(j.getId(), x, y);
		}
	*/
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

	
	// loads an image from a file
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
