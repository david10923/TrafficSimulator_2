package simulator.launcher;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import Exceptions.InvalidArgumentException;
import simulator.Vista.MainWindow;
import simulator.control.Controller;
import simulator.factories.*;
import simulator.model.*;

public class Main {

	private final static Integer _timeLimitDefaultValue = 10;
	private static Integer _timeLimit = null;
	private static String _inFile = null;
	private static String _outFile = null;
	private static Factory<Event> _eventsFactory = null;
	
	private static String time = null; 
	
	/////PARA LA OPCION MODE /////
	private static String mode = null;
	private final static String guimode ="gui";
	
	///////////////
	
	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();
		

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseHelpOption(line, cmdLineOptions);
			parseModeOption(line); //////HAY QUE VER EL ORDEN
			parseInFileOption(line);
			parseOutFileOption(line);
			parseInFileOption2(line);
			

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Events input file").build());
		cmdLineOptions.addOption(
				Option.builder("o").longOpt("output").hasArg().desc("Output file, where reports are written.").build());
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message").build());
		
		cmdLineOptions.addOption(Option.builder("t").longOpt("ticks").hasArg().desc("Ticks to the simulator�s main loop").build());

		///////A�ADIDO EL MODO DE VISUALIZACION////////
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc("Mode of the app").build());
		
		return cmdLineOptions;
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {	
		_inFile = line.getOptionValue("i");		
		////// EL PARAMETRO -I ES OPCIONAL SI ES MODO GUI
		
		if(!mode.equals(guimode)){
			if (_inFile == null) { 
				throw new ParseException("An events file is missing");			
			}
		}
		
	}

	private static void parseOutFileOption(CommandLine line) throws ParseException {
		if(mode != guimode) {
			_outFile = line.getOptionValue("o");
		}		
	}

	private static void parseInFileOption2(CommandLine line) throws ParseException {
		time = line.getOptionValue("t",Integer.toString(_timeLimitDefaultValue));
		
		_timeLimit = Integer.decode(time);
		
		if(_timeLimit <= 0 ){
			throw new ParseException("The value is less than 0");
		}
		
	}
	///////////////A�ADIR LA OPCION -M MODE ////////////////////
	private static void parseModeOption(CommandLine line) throws ParseException {
		mode = line.getOptionValue("m"); // mode puede tener el valor de "gui" o el valor de "console"
		if(mode == null) {
			mode = guimode;
		}
		
	}
	
	
	private static void initFactories() {

		ArrayList<Builder<LightSwitchingStrategy>> lsbs = new ArrayList<>();
		lsbs.add(new RoundRobinStrategyBuilder());
		lsbs.add(new MostCrowdedStrategyBuilder());
		Factory<LightSwitchingStrategy> lssFactory = new BuilderBasedFactory<>(lsbs);
		
		ArrayList<Builder<DequeingStrategy>> dqbs = new ArrayList<>();
		dqbs.add(new MoveFirstStrategyBuilder());
		dqbs.add(new MoveAllStrategyBuilder());
		Factory<DequeingStrategy> dqsFactory = new BuilderBasedFactory<>(dqbs);
		
		List<Builder<Event>> eventBuilders = new ArrayList<>();
		eventBuilders.add(new NewCityRoadEventBuilder());
		eventBuilders.add(new NewJunctionEventBuilder(lssFactory, dqsFactory));
		eventBuilders.add(new NewInterCityRoadEventBuilder());
		eventBuilders.add(new NewVehicleEventBuilder());
		eventBuilders.add(new SetContClassEventBuilder());
		eventBuilders.add(new SetWeatherEventBuilder());
		
		
		// a�adir los demas enventos 
		
		
		
		
		_eventsFactory = new BuilderBasedFactory<>(eventBuilders);
	}

	private static void startBatchMode() throws IOException {
		
		InputStream in = new FileInputStream(new File(_inFile));
		OutputStream out = _outFile == null ?
		System.out : new FileOutputStream(new File(_outFile));
		TrafficSimulator sim = new TrafficSimulator();
		Controller ctrl;
		try {
			ctrl = new Controller(sim, _eventsFactory);
			ctrl.loadEvents(in);
			ctrl.run(_timeLimit, out);
		} catch (InvalidArgumentException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		
		in.close();
		System.out.println("Done!");
	}

	
	////////METODO QUE INICIALIZA CUANDO QUEREMOS LA GUI ////////
	
	private static void startGUImode ()throws IOException {
		
		Controller ctrl;		
		TrafficSimulator sim = new TrafficSimulator();
				
		try {
			ctrl = new Controller(sim, _eventsFactory);	
		
			
				SwingUtilities.invokeLater(new Runnable() {
					public void run () {					
					
						new MainWindow(ctrl);
						if(_inFile!= null) {
							 InputStream in;
							try {
								in = new FileInputStream(new File(_inFile));
								ctrl.loadEvents(in);
								ctrl.run(_timeLimit);
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} 
							
						}
						
					}
				});
			
			
		} catch (InvalidArgumentException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		
		
	}
	
	
	
	
	private static void start(String[] args) throws IOException {
		initFactories();
		parseArgs(args);	
		
		if(mode.equals(guimode)) {			
			startGUImode();
		}else {
			startBatchMode();
		}
	
	}

	// example command lines:
	//
	// -i resources/examples/ex1.json
	// -i resources/examples/ex1.json -t 300
	// -i resources/examples/ex1.json -o resources/tmp/ex1.out.json
	// --help

	public static void main(String[] args) {
		try {
			start(args);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
