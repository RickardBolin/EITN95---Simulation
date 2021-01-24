import java.util.*;
import java.io.*;

import static java.util.Objects.isNull;

//It inherits Proc so that we can use time and the signal names without dot notation

class Dispatcher extends Proc{

	//The random number generator is started:
	Random slump = new Random();
	int totalCustomers = 0;
	int accumulatedCustomersInSystem = 0;
	int noMeasurements = 0;
	//There are two parameters:
	List<QS> queues = new ArrayList<>();


	// What to do when a signal arrives
	public void TreatSignal(Signal x){
		switch (x.signalType){
			case ARRIVAL: {
				SignalList.SendSignal(ARRIVAL, generateDestination(), time);
			} break;
			case MEASURE: {
				for (QS queue : queues){
					accumulatedCustomersInSystem += queue.numberInQueue;
				}
				noMeasurements++;
				SignalList.SendSignal(MEASURE, this, time + slump.nextDouble()*5);
			} break;
		}
	}

	private Proc generateDestination(){
		if (VERSION == 1) {
			return queues.get(slump.nextInt(5));
		}
		else if (VERSION == 2) {
			return queues.get(totalCustomers++%5);
		}
		else {
			List<QS> shortestQueues = new ArrayList<>();
			int min = Integer.MAX_VALUE;
			for (QS queue : queues){
				if(queue.numberInQueue < min){
					shortestQueues.clear();
					shortestQueues.add(queue);
					min = queue.numberInQueue;
				} else if (queue.numberInQueue == min){
					shortestQueues.add(queue);
				}
			}
			return shortestQueues.get(slump.nextInt(shortestQueues.size()));
		}
	}
}
