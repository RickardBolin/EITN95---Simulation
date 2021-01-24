import java.util.*;
import java.io.*;
import java.lang.Math;

class State extends GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numberInQueue = 0, noCustomers = 0;
	public double accumulatedTimeSpent = 0;
	public boolean isFinished = false;
	LinkedList<Double> timeStamps = new LinkedList<>();

	Random slump = new Random(); // This is just a random number generator

	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. 
	public void treatEvent(Event x){
		switch (x.eventType) {
			case ARRIVAL_Q1:
				arrival(ARRIVAL_Q1);
				break;
			case READY_Q1:
				ready(READY_Q1);
				break;
		}
	}
	
	// The following methods defines what should be done when an event takes place. This could
	// have been placed in the case in treatEvent, but often it is simpler to write a method if 
	// things are getting more complicated than this.
	
	private void arrival(int type){
		timeStamps.addLast(time);
		numberInQueue++;

		if (numberInQueue == 1) {
			insertEvent(READY_Q1, time + getServiceTime());
		}
		if (time <= 8) {
			insertEvent(ARRIVAL_Q1, time + getArrival());
		}
	}
	
	private void ready(int type){
		numberInQueue--;
		accumulatedTimeSpent += (time - timeStamps.poll());
		noCustomers++;

		if (numberInQueue > 0) {
			insertEvent(READY_Q1, time + getServiceTime());
		}
		if (time > 8 && numberInQueue == 0){
			isFinished = true;
		}
	}

	private double getServiceTime(){
		return (1+slump.nextDouble())/6;
	}

	private double getArrival(){
		return -Math.log(slump.nextDouble())/4;
	}

}