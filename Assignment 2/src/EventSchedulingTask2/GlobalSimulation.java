public class GlobalSimulation{

	public static final int ARRIVAL_A = 1, READY_A = 2, MEASURE = 3, ARRIVAL_B = 4, READY_B = 5; // The events, add or remove if needed!
	public static double time = 0; // The global time variable
	public static EventListClass eventList = new EventListClass(); // The event list used in the program
	public static void insertEvent(int type, double TimeOfEvent){  // Just to be able to skip dot notation
		eventList.InsertEvent(type, TimeOfEvent);
	}
}