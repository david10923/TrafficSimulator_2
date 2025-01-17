package simulator.model;

import Exceptions.InvalidArgumentException;

public abstract class Event implements Comparable<Event> {

	protected int _time;

	Event(int time) {
		if (time < 1)
			throw new IllegalArgumentException("Time must be positive (" + time + ")");
		else
			_time = time;
	}

	public int getTime() {
		return _time;
	}

	@Override
	public int compareTo(Event o) {	
		return Integer.valueOf(this.getTime()).compareTo(o.getTime());
	}

	abstract void execute(RoadMap map);
}
