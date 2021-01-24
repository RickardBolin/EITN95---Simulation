import java.util.*;
import java.io.*;

//It inherits Proc so that we can use time and the signal names without dot notation


public class MainSimulation extends Global{

	public static void main(String[] args) throws IOException {

    	// The signal list is started and actSignal is declaree. actSignal is the latest signal that has been fetched from the
    	// signal list in the main loop below.

    	Signal actSignal;
    	new SignalList();

    	// Here process instances are created (two queues and one generator) and their parameters are given values.

		Gen generator = new Gen();
		Dispatcher dispatcher = new Dispatcher();
		for (int i = 0; i < 5; i++){
			dispatcher.queues.add(new QS());
		}
		generator.sendTo = dispatcher;

    	//To start the simulation the first signals are put in the signal list
    	SignalList.SendSignal(READY, generator, time);
		// Add first measurement a while into the simulation to start measuring after the burn in period.
		SignalList.SendSignal(MEASURE, dispatcher, 100 + time);

		// This is the main loop
    	while (time < 100000){
    		actSignal = SignalList.FetchSignal();
    		time = actSignal.arrivalTime;
    		actSignal.destination.TreatSignal(actSignal);
    	}
    	double accumulatedTime = 0;
    	int totalCustomersServed = 0;
		for (QS queue : dispatcher.queues) {
			accumulatedTime += queue.accumulatedTimeSpent;
			totalCustomersServed += queue.customersServed;
		}

    	//Finally the result of the simulation is printed below:

    	System.out.println("Average number of customers in the system (L): " + (float)dispatcher.accumulatedCustomersInSystem/dispatcher.noMeasurements);
		System.out.println("Average time spent in store: " + (float)accumulatedTime/totalCustomersServed);

	}
}