import java.util.*;
import java.io.*;
import java.lang.Math;

class State extends GlobalSimulation{

	public int numberInQueue1 = 0, accumulated1 = 0, noMeasurements1 = 0, totalArrivals = 0, noRejected1 = 0;
	public int numberInQueue2 = 0, accumulated2 = 0, noMeasurements2 = 0;
	public double MEAN_SERVICE_TIME_1 = 2.1, SERVICE_TIME_2 = 2.0;
	public double ARRIVAL_TIME = 5;

	Random slump = new Random();

	public void treatEvent(Event x){
		switch (x.eventType){
			case ARRIVAL_Q1 :
				arrival(ARRIVAL_Q1);
				break;
			case READY_Q1:
				ready(READY_Q1);
				break;
			case MEASURE_Q1:
				measure(MEASURE_Q1);
				break;
			case ARRIVAL_Q2 :
				arrival(ARRIVAL_Q2);
				break;
			case READY_Q2:
				ready(READY_Q2);
				break;
			case MEASURE_Q2:
				measure(MEASURE_Q2);
				break;
		}
	}

	private void arrival(int type){
		if(type == ARRIVAL_Q1) {
			totalArrivals++;
			if (numberInQueue1 < 10) {
				numberInQueue1++;
			} else {
				noRejected1++;
			}
			if (numberInQueue1 == 1) {
				insertEvent(READY_Q1, time - Math.log(slump.nextDouble())*MEAN_SERVICE_TIME_1);
			}
			insertEvent(ARRIVAL_Q1, time + ARRIVAL_TIME);
		} else {
			numberInQueue2++;
			if (numberInQueue2 == 1) {
				// If this is the first person in line, schedule a "Ready"
				insertEvent(READY_Q2, time + SERVICE_TIME_2);
			}
		}
	}
	
	private void ready(int type){
		if (type == READY_Q1) {
			numberInQueue1--;
			insertEvent(ARRIVAL_Q2, time);
			if (numberInQueue1 > 0) {
				insertEvent(READY_Q1, time - Math.log(slump.nextDouble())*MEAN_SERVICE_TIME_1);
			}
		} else{
			numberInQueue2--;
			if (numberInQueue2 > 0){
				// If there is still someone left in the queue, schedule a new "Ready"
				insertEvent(READY_Q2,time + SERVICE_TIME_2);
			}
		}
	}
	
	private void measure(int type){
		if (type == MEASURE_Q1) {
			accumulated1 += numberInQueue1;
			noMeasurements1++;
			insertEvent(MEASURE_Q1, time - Math.log(slump.nextDouble())*5);
		} else{
			accumulated2 += numberInQueue2;
			noMeasurements2++;
			insertEvent(MEASURE_Q2, time - Math.log(slump.nextDouble())*5);
		}
	}
}