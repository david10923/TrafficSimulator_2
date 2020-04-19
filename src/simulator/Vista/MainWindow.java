package simulator.Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

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

	//private boolean _stopped ;
	
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
		mainPanel .add( viewsPanel , BorderLayout. CENTER );
		
		
		
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
		//////// SCROLL PARA LOS EVENTOS , NO SE SI HAY QUE PONERLO 
		
		eventsView.add(new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		
		eventsView .setPreferredSize( new Dimension(500, 200));
		tablesPanel .add( eventsView );
		
		
		////AÑADES LA TABLA DE VEHICULOS //////
		JPanel vehicleView =
			createViewPanel( new JTable( new VehiclesTableModel( _ctrl )), "Vehicles" );
			vehicleView .setPreferredSize( new Dimension(500, 200));
			tablesPanel .add( vehicleView );
		
		///////////AÑADES LA TABLA DE LOS VEHICULOS ////////
		JPanel roadView =
			createViewPanel( new JTable( new RoadTableModel( _ctrl )), "Roads" );
			roadView .setPreferredSize( new Dimension(500, 200));
			tablesPanel .add( roadView );

			
		//////////////AÑADES LA TABLA DE JUNCTIONS///////
		JPanel junctionView =
			createViewPanel( new JTable( new JunctionTableModel(_ctrl )), "Junctions" );
			junctionView .setPreferredSize( new Dimension(500, 200));
			tablesPanel .add( junctionView );
			
		///////AÑADE LA VISTA DE ARRIBA A LA DERECHA //////	
		JPanel mapView = createViewPanel( new MapComponent( _ctrl ), "Map" );
			mapView .setPreferredSize( new Dimension(500, 400));
			mapsPanel .add( mapView );
			
		
		////////AÑADIR LA VISTA DE ABAJO A LA DERECHA ///			
	//	JPanel mapByRoad = createViewPanel();
	//	mapByRoad.setPreferredSize(new Dimension(500,400));
	//	mapsPanel.add(mapByRoad);
	
			
			
		this .setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this .pack();
		this .setVisible( true );
		}
	
	
	
	/*
	private void run_sim( int n ) {
		if ( n > 0 && ! _stopped ) {
			try {
			_ctrl .run(1);
			} catch (Exception e ) {
			// TODO show error message
			_stopped = true ;
			return ;
			}
		SwingUtilities.invokeLater( new Runnable() {
		@Override
		public void run() {
			run_sim( n - 1);
		}
		});
		} else {
			enableToolBar( true );
			_stopped = true ;
		}
		}
	
	
		private void enableToolBar(boolean b) {
			if(b){
				
			}else{
				
			}
		}


		private void stop() {
			_stopped = true ;
		}
	*/
	
	
		private JPanel createViewPanel(JComponent c , String title ) {
				JPanel p = new JPanel( new BorderLayout() );
				p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 1), title,
						TitledBorder.LEFT, TitledBorder.TOP));
				p .add( new JScrollPane( c ));
		return p ;
		}
		
	
}
