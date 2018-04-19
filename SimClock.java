public class SimClock
{
	//attribute
	private static int time;
	//constructor initializes time to 0
	public SimClock(){
		time = 0;
	}
	//ticks the clock forward by 1
	public static void tick(){
		time++;
	}
	//returns the simulated time
	public static int getTime(){
		return time;
	}
}
