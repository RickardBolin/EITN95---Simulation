import java.util.*;
import java.io.*;


public class MainSimulation extends GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
    	Event actEvent;
    	State actState = new State(); // The state that should be used
    	// Some events must be put in the event list at the beginning

        insertEvent(ARRIVAL_Q1, 0);
        // Add first measurement a while into the simulation to start measuring after the burn in period.
		insertEvent(MEASURE_Q2, 100);

		// The main simulation loop
    	while (actState.noMeasurements2 < 1000000){
    		actEvent = eventList.fetchEvent();
    		time = actEvent.eventTime;
    		actState.treatEvent(actEvent);
    	}
    	
    	// Printing the result of the simulation
		System.out.println("Probability of rejection: " + (float)actState.noRejected1/actState.totalArrivals);
		System.out.println("Average length of queue 2: " + (float)actState.accumulated2/actState.noMeasurements2);
	}
}