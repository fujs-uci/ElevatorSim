public class ElevatorSim
{
	//Creates a Elevator simulation object and starts the simulation
	public static void main(String[] args)
	{
		ElevatorSimulation Elevator = new ElevatorSimulation();
		Elevator.start();
		Elevator.printBuildingState();
	}

}
