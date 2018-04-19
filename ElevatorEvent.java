public class ElevatorEvent
{
	//attributes
	private int destination;
	private int expectedArrival;

	//constructor
	public ElevatorEvent(int destination, int expectedArrival) {
		this.destination = destination;
		this.expectedArrival = expectedArrival;
	}
	//getters and setters to maintain the state
	public int getDestination()
	{
		return destination;
	}
	public void setDestination(int destination)
	{
		this.destination = destination;
	}
	public int getExpectedArrival()
	{
		return expectedArrival;
	}
	public void setExpectedArrival(int expectedArrival)
	{
		this.expectedArrival = expectedArrival;
	}
}
