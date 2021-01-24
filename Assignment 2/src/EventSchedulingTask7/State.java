import java.util.*;

class State extends GlobalSimulation{

	boolean[] brokenComponents = new boolean[5];
	Random slump = new Random(); // This is just a random number generator

	public void treatEvent(Event x){
		arrival(x.eventType);
	}

	private void arrival(int type){
		switch (type){
			case COMP_1:
				brokenComponents[type] = true;
				brokenComponents[1] = true;
				brokenComponents[4] = true;
				break;
			case COMP_3:
				brokenComponents[type] = true;
				brokenComponents[3] = true;
				break;
			default:
				brokenComponents[type] = true;
				break;
		}
	}

	public double getBreakdownTime(){
		return 1+4*slump.nextDouble();
	}

	public boolean allBroken(boolean[] brokenComponents){
		for(boolean component : brokenComponents){
			if (!component){
				return false;
			}
		}
		return true;
	}
}