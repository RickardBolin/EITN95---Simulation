import java.util.*;
import java.io.*;
import java.lang.Math;

class State extends GlobalSimulation{

	public int TASK = 3;
	public int numberOfAInQueue = 0, accumulatedQueue = 0, noMeasurements = 0, numberOfBInQueue = 0;
	public double SERVICE_TIME_A = 0.002, SERVICE_TIME_B = 0.004, NEXT_MEASURE = 0.1;

	public int accumulatedB = 0, accumulatedA = 0;
	Random slump = new Random();

	public void treatEvent(Event x){
		switch (x.eventType){
			case ARRIVAL_A :
				arrival(ARRIVAL_A);
				break;
			case READY_A:
				ready(READY_A);
				break;
			case ARRIVAL_B:
				arrival(ARRIVAL_B);
				break;
			case READY_B:
				ready(READY_B);
				break;
			case MEASURE:
				measure();
				break;
		}
	}

	private void arrival(int type){
		if(type == ARRIVAL_A) {
			if (numberOfAInQueue + numberOfBInQueue == 0) {
				// If first in line, schedule a "Ready_A"
				insertEvent(READY_A, time + SERVICE_TIME_A);
			}
			numberOfAInQueue++;
			// Schedule a new arrival
			insertEvent(ARRIVAL_A, time + getArrival());
		} else {
			if (numberOfAInQueue + numberOfBInQueue == 0) {
				// If first in line, schedule a "Ready_B"
				insertEvent(READY_B, time + SERVICE_TIME_B);
			}
			numberOfBInQueue++;
		}
	}
	
	private void ready(int type) {
		if (type == READY_A) {
			numberOfAInQueue--;
			insertEvent(ARRIVAL_B, time + getDelay(TASK));
		} else {
			numberOfBInQueue--;
		}

		if (TASK != 3) {
			if (numberOfBInQueue > 0) {
				insertEvent(READY_B, time + SERVICE_TIME_B);
			} else if (numberOfAInQueue > 0) {
				insertEvent(READY_A, time + SERVICE_TIME_A);
			}
		} else {
			// In task 3, we flip the priority of A and B
			if (numberOfAInQueue > 0) {
				insertEvent(READY_A, time + SERVICE_TIME_A);
			} else if (numberOfBInQueue > 0) {
				insertEvent(READY_B, time + SERVICE_TIME_B);
			}
		}
	}
	
	private void measure(){
		accumulatedA += numberOfAInQueue;
		accumulatedB += numberOfBInQueue;
		accumulatedQueue += numberOfAInQueue + numberOfBInQueue;
		noMeasurements++;
		insertEvent(MEASURE, time + NEXT_MEASURE);
	}

	private double getDelay(int task){
		if (task == 2){
			return -Math.log(slump.nextDouble());
		} else {
			return 1;
		}
	}

	private double getArrival(){
		return -Math.log(slump.nextDouble())/150;
	}
}