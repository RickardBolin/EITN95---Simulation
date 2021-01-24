import java.util.*;
import java.io.*;


public class MainSimulation extends GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
    	Event actEvent;
    	State actState = new State(); // The state that should be used
    	// Some events must be put in the event list at the beginning

        insertEvent(ARRIVAL_Q1, 0);
		insertEvent(MEASURE, 1);

		int M = 4000;
    	while (actState.noMeasurements < M){
    		actEvent = eventList.fetchEvent();
    		time = actEvent.eventTime;
    		actState.treatEvent(actEvent);
    	}
    	
    	// Printing the result of the simulation
		System.out.println("Number of customers served at the end of the run: " + actState.noBusy);
	}
}
