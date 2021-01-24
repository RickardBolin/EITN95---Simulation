import java.util.*;
import java.io.*;


public class MainSimulation extends GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
    	Event actEvent;
    	State actState = new State(); // The state that should be used
    	// Some events must be put in the event list at the beginning

		// The main simulation loop
		double accumulatedEndOfDay = 0;
		int nbDays = 1000;
		for (int i = 0; i < nbDays; i++) {
			insertEvent(ARRIVAL_Q1, 0);
			while (!actState.isFinished) {
				actEvent = eventList.fetchEvent();
				time = actEvent.eventTime;
				actState.treatEvent(actEvent);
			}
			accumulatedEndOfDay += time;
			actState.isFinished = false;
		}
    	// Printing the result of the simulation

		System.out.println("Average time per customer: " + (float)actState.accumulatedTimeSpent/actState.noCustomers);
		System.out.println("Average end of day: " + accumulatedEndOfDay/nbDays);
	}
}