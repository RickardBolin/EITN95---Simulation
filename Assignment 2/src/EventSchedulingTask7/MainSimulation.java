import java.util.*;
import java.io.*;


public class MainSimulation extends GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
    	Event actEvent;
    	State actState = new State(); // The state that should be used
    	// Some events must be put in the event list at the beginning

		// The main simulation loop
		double accumulatedLifetime = 0;
		int runs = 1000;
		for (int i = 0; i < runs; i++) {
			actState.brokenComponents = new boolean[5];
			insertEvent(COMP_1, actState.getBreakdownTime());
			insertEvent(COMP_2, actState.getBreakdownTime());
			insertEvent(COMP_3, actState.getBreakdownTime());
			insertEvent(COMP_4, actState.getBreakdownTime());
			insertEvent(COMP_5, actState.getBreakdownTime());

			while (!actState.allBroken(actState.brokenComponents)) {
				actEvent = eventList.fetchEvent();
				time = actEvent.eventTime;
				actState.treatEvent(actEvent);
			}
			accumulatedLifetime += time;
		}
    	// Printing the result of the simulation, in this case a mean value

		System.out.println("Average lifetime: " + accumulatedLifetime/runs);
	}
}