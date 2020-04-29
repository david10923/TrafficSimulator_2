package simulator.Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;

public class MainWindow extends JFrame  {
	
	private Controller _ctrl ;

	
	public MainWindow(Controller ctrl ) {
		super ( "Traffic Simulator" );
		_ctrl = ctrl ;
		initGUI();
		
	}
	
	
	private void initGUI() {		
		JPanel mainPanel = new JPanel( new BorderLayout());
		this .setContentPane( mainPanel );
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension tamanio = tk.getScreenSize();
		
		this.setLocation((int)tamanio.getWidth()/6,(int)tamanio.getHeight()/9);
		
		/////////AÑADES LA BARRA DE ARRIBA  Y LA DDE ABAJO //////
		mainPanel .add( new ControlPanel( _ctrl ), BorderLayout. PAGE_START );
		mainPanel .add( new StatusBar( _ctrl ),BorderLayout. PAGE_END );
		
		///////////AÑADES EL PANEL DE LA DERECHA  SOBRE EL PRINCIPAL ///////
		JPanel viewsPanel = new JPanel( new GridLayout(1, 2));
		mainPanel .add( viewsPanel , BorderLayout.CENTER );
		
		
		
		/////////AÑADES EL PANEL DE LA IZQUIERDA SOBRE EL PRINCIPAL /////
		JPanel tablesPanel = new JPanel();
		tablesPanel .setLayout( new BoxLayout( tablesPanel , BoxLayout. Y_AXIS ));
		viewsPanel .add( tablesPanel );
		
		
		/////AÑADIMOS EL PANEL DE MAPAS A LA DERECHA DEL PANEL PRINCIPAL////////////////
		JPanel mapsPanel = new JPanel();
		mapsPanel .setLayout( new BoxLayout( mapsPanel , BoxLayout. Y_AXIS ));
		viewsPanel .add( mapsPanel );
		
		
		
		
		///////AÑADES LA TABLA DE LOS EVENTOS /////////
		JPanel eventsView =
		createViewPanel( new JTable( new EventsTableModel( _ctrl )), "Events" );		
		eventsView .setPreferredSize( new Dimension(500, 200));
		//eventsView.setMaximumSize( new Dimension(500, 200));
		//eventsView.setMinimumSize(new Dimension(500,200));
		tablesPanel .add( eventsView );
		
		
		////AÑADES LA TABLA DE VEHICULOS //////
		JPanel vehicleView =
			createViewPanel( new JTable( new VehiclesTableModel( _ctrl )), "Vehicles" );
			vehicleView .setPreferredSize( new Dimension(500, 200));
			//vehicleView.setMinimumSize(new Dimension(500, 200));
			//vehicleView.setMaximumSize(new Dimension(500, 200));			
			tablesPanel .add( vehicleView );
		
		///////////AÑADES LA TABLA DE LOS VEHICULOS ////////
		JPanel roadView =
			createViewPanel( new JTable( new RoadTableModel( _ctrl )), "Roads" );
			roadView .setPreferredSize( new Dimension(500, 200));
			//roadView .setMinimumSize( new Dimension(500, 200));
			//roadView .setMaximumSize( new Dimension(500, 200));			
			tablesPanel .add( roadView );

			
		//////////////AÑADES LA TABLA DE JUNCTIONS///////
		JPanel junctionView =
			createViewPanel( new JTable( new JunctionTableModel(_ctrl )), "Junctions" );
			junctionView .setPreferredSize( new Dimension(500, 200));
			//junctionView.setMinimumSize( new Dimension(500, 200));
			//junctionView.setMaximumSize( new Dimension(500, 200));			
			tablesPanel .add( junctionView );
			
			
			
		///////AÑADE LA VISTA DE ARRIBA A LA DERECHA //////	
		JPanel mapView = createViewPanel( new MapComponent( _ctrl ), "Map" );
			mapView .setPreferredSize( new Dimension(500, 400));
			//mapView .setMaximumSize( new Dimension(500, 400));
			//mapView.setMinimumSize(new Dimension(500, 400));			
			mapsPanel .add( mapView );
			
		
		////////AÑADIR LA VISTA DE ABAJO A LA DERECHA ///			
		JPanel mapByRoad = createViewPanel(new MapByRoadComponent(_ctrl),"Map by Road");
		mapByRoad.setPreferredSize(new Dimension(300,200));
		//mapByRoad.setMaximumSize(new Dimension(300,200));
		//mapByRoad.setMinimumSize(new Dimension(300,200));		
		mapsPanel.add(mapByRoad);
	
		
			
		this .setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this .pack();
		this .setVisible( true );
		}
	
	
		private JPanel createViewPanel(JComponent c , String title ) {
				JPanel p = new JPanel( new BorderLayout() );
				p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 1), title,
						TitledBorder.LEFT, TitledBorder.TOP));
				p .add( new JScrollPane( c ));
		return p ;
		}
		
	
}
