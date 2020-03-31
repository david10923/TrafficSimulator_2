package simulator.model;

public enum Weather {
	SUNNY, CLOUDY, RAINY, WINDY, STORM;



	public static Weather getWeather(String s) {
		s.toUpperCase();
		return Weather.valueOf(s);
		
	}
}