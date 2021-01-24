import java.util.*;
import java.io.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class QS extends Proc{
	public int numberInQueue = 0;
	LinkedList<Double> timeStamps = new LinkedList<>();
	public double accumulatedTimeSpent = 0;
	public int customersServed = 0;
	Random slump = new Random();

	public void TreatSignal(Signal x){
		switch (x.signalType){
			case ARRIVAL:{
				numberInQueue++;
				timeStamps.addLast(time);
				if (numberInQueue == 1){
					SignalList.SendSignal(READY, this, time + getServiceTime());
				}
			} break;

			case READY:{
				numberInQueue--;
				accumulatedTimeSpent += (time - timeStamps.poll());
				customersServed++;
				if (numberInQueue > 0){
					SignalList.SendSignal(READY, this, time + getServiceTime());
				}
			} break;
		}
	}

	private double getServiceTime(){
		return -Math.log(slump.nextDouble())*0.5;
	}
}