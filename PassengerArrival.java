public class PassengerArrival
{	
	//attributes
	private int numPassenger;
	private int destinationFloor;
	private int timePeriod;
	private int expectedTimeOfArrival;
	//Constructor
	public PassengerArrival(int numPassenger, int destinationFloor, int timePeriod){
		this.numPassenger = numPassenger;
		this.destinationFloor = destinationFloor;
		this.timePeriod = timePeriod;
	}
	//to string method 
	public String toString()
	{
		return "PassengerArrival [" + numPassenger
			+ ", " + destinationFloor + ", "
			+ timePeriod + ", " + expectedTimeOfArrival
			+ "]";
	}

	//Getters and setters
	public int getNumPassenger()
	{
		return numPassenger;
	}
	public int getDestinationFloor()
	{
		return destinationFloor;
	}
	public int getTimePeriod()
	{
		return timePeriod;
	}
	public int getExpectedTimeOfArrival()
	{
		return expectedTimeOfArrival;
	}
	public void setExpectedTimeOfArrival(int expectedTimeOfArrival)
	{
		this.expectedTimeOfArrival = expectedTimeOfArrival;
	}
}
