import java.util.*;
import java.io.*;


public class MainSimulation extends GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
    	Event actEvent;
    	State actState = new State(); // The state that should be used
    	// Some events must be put in the event list at the beginning

        insertEvent(ARRIVAL_A, 0);
		// Add first measurement a while into the simulation to start measuring after the burn in period.
		insertEvent(MEASURE, 100);

		// The main simulation loop
    	while (actState.noMeasurements < 1000000){
    		actEvent = eventList.fetchEvent();
    		time = actEvent.eventTime;
    		actState.treatEvent(actEvent);
    	}
    	
    	// Printing the result of the simulation
    	System.out.println("Mean number of jobs in buffer: " + (float)actState.accumulatedQueue/actState.noMeasurements);
	}
}