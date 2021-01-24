import java.util.*;
import java.io.*;

class State extends GlobalSimulation{

	private int TASK = 6;
	private int x = 10;
	private int LAMBDA = 4;
	public int noBusy = 0, noMeasurements = 0;
	private int T = 4;
	private int N = 100;
	Random slump = new Random();

	// Create file
	File f = new File("logTask" + TASK);

	public void treatEvent(Event x){
		switch (x.eventType){
			case ARRIVAL_Q1:
				arrival(ARRIVAL_Q1);
				break;
			case READY_Q1:
				ready(READY_Q1);
				break;
			case MEASURE:
				measure();
				break;
		}
	}

	private void arrival(int type){
		if (noBusy < N) {
			noBusy++;
			insertEvent(READY_Q1, time + x);
		}
		insertEvent(ARRIVAL_Q1, time - Math.log(slump.nextDouble())/LAMBDA);
	}

	private void ready(int type){
		noBusy--;
	}

	private void measure(){
		noMeasurements++;
		insertEvent(MEASURE, time + T);
		log(f, noMeasurements + "," + noBusy);
	}


	public void log(File f, String s) {
		try {
			FileWriter fw = new FileWriter(f, true);
			fw.write(s);
			fw.write(System.lineSeparator());
			fw.close();
		} catch (IOException ex) {
			System.err.println("Couldn't log");
		}
	}
}