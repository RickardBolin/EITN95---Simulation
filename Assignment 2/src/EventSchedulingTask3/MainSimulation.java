import java.util.*;
import java.io.*;


public class MainSimulation extends GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
    	Event actEvent;
    	State actState = new State(); // The state that should be used

        insertEvent(ARRIVAL_Q1, 0);
		// Add first measurement a while into the simulation to start measuring after the burn in period.
		insertEvent(MEASURE, 100);

		// The main simulation loop
    	while (actState.noMeasurements < 1000000){
    		actEvent = eventList.fetchEvent();
    		time = actEvent.eventTime;
    		actState.treatEvent(actEvent);
    	}
    	
    	// Printing the result of the simulation
		System.out.println("Mean number of customers in queuing network: " + (float)actState.accumulated/actState.noMeasurements);
		System.out.println("Average time spent in queuing network: " + (float)actState.accumulatedTimeSpent/actState.noCustomers);
	}
}
