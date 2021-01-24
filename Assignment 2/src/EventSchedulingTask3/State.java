import java.util.*;
import java.io.*;
import java.lang.Math;

class State extends GlobalSimulation{

	public int numberInQueue1 = 0, accumulated = 0, noMeasurements = 0, noCustomers = 0;
	public int numberInQueue2 = 0;
	public double accumulatedTimeSpent = 0;
	public double MEAN_SERVICE_TIME = 1;
	public double ARRIVAL_TIME = 2.0;
	LinkedList<Double> timeStamps = new LinkedList<>();
	Random slump = new Random(); // This is just a random number generator

	public void treatEvent(Event x){
		switch (x.eventType){
			case ARRIVAL_Q1 :
				arrival(ARRIVAL_Q1);
				break;
			case READY_Q1:
				ready(READY_Q1);
				break;
			case MEASURE:
				measure(MEASURE);
				break;
			case ARRIVAL_Q2 :
				arrival(ARRIVAL_Q2);
				break;
			case READY_Q2:
				ready(READY_Q2);
				break;
		}
	}

	private void arrival(int type){
		if(type == ARRIVAL_Q1) {
			timeStamps.addLast(time);
			numberInQueue1++;
			if (numberInQueue1 == 1) {
				insertEvent(READY_Q1, time - Math.log(slump.nextDouble())*MEAN_SERVICE_TIME);
			}
			insertEvent(ARRIVAL_Q1, time + getArrival());
		} else {
			numberInQueue2++;
			if (numberInQueue2 == 1) {
				// If this is the first person in line, schedule a "Ready"
				insertEvent(READY_Q2, time - Math.log(slump.nextDouble())*MEAN_SERVICE_TIME);
			}
		}
	}
	
	private void ready(int type){
		if (type == READY_Q1) {
			numberInQueue1--;
			insertEvent(ARRIVAL_Q2, time);
			if (numberInQueue1 > 0) {
				insertEvent(READY_Q1, time - Math.log(slump.nextDouble())*MEAN_SERVICE_TIME);
			}
		} else{
			accumulatedTimeSpent += (time - timeStamps.poll());
			noCustomers++;
			numberInQueue2--;
			if (numberInQueue2 > 0){
				// If there is still someone left in the queue, schedule a new "Ready"
				insertEvent(READY_Q2,time - Math.log(slump.nextDouble())*MEAN_SERVICE_TIME);
			}
		}
	}
	
	private void measure(int type) {
		if (type == MEASURE) {
			accumulated += (numberInQueue1 + numberInQueue2);
			noMeasurements++;
			insertEvent(MEASURE, time - Math.log(slump.nextDouble())*5.0);
		}
	}

	private double getArrival(){
		return -Math.log(slump.nextDouble()) * ARRIVAL_TIME;
	}
}
