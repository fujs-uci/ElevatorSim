public class BuildingManager
{
	//Attributes
	private static BuildingFloor[] floors; // represents the state of all floors in the building
	//Constructor
	public BuildingManager(){
		BuildingManager.floors = new BuildingFloor[5];
		setFloors();
	}
	//Fills the BuildingFLoor[] with BuildingFloors
	public void setFloors(){
		for( int i = 0; i < 5; i++ ){
			BuildingManager.floors[i] = new BuildingFloor();
		}
	}
	//Fills the arrays of the floors that keep track of passengers
	public void passengerSpawnConfig( int currFloor, PassengerArrival passengerConfig ){
		int passengerNumb = passengerConfig.getNumPassenger();
		int nextFloor = passengerConfig.getDestinationFloor();
		BuildingManager.floors[currFloor].setTotalDestinationRequests(nextFloor, passengerNumb);
		BuildingManager.floors[currFloor].setPassengerRequest(nextFloor, passengerNumb);
		System.out.format(" Floor %d has spawned %d passengers going to floor %d.\n"
				,currFloor, passengerNumb, nextFloor);
	}
	//Prints all the information of the building manager
	public void printInfo(){
		System.out.format("TIME = %-3d\t\tFLOOR %d__________FLOOR %d_________"+ "FLOOR %d_________FLOOR %d_________FLOOR %d__________\n", SimClock.getTime(),0,1,2,3,4);
		System.out.print("Total Requests  \t| ");
		for( int i =0; i< BuildingManager.floors.length; i++){
			BuildingFloor tempFloor = BuildingManager.floors[i];
			String tempArray = tempFloor.floorArrayToString(tempFloor.getTotalDestinationRequests());
			System.out.print(tempArray + "|");
		}
		System.out.print("\n");
		System.out.print("Arrived Passengers\t| ");
		for( int i =0; i< BuildingManager.floors.length; i++){
			BuildingFloor tempFloor = BuildingManager.floors[i];
			String tempArray = tempFloor.floorArrayToString(tempFloor.getArrivedPassenger());
			System.out.print(tempArray + "|");
		}
		System.out.print("\n");
		System.out.print("Current Requests\t| ");
		for( int i =0; i< BuildingManager.floors.length; i++){
			BuildingFloor tempFloor = BuildingManager.floors[i];
			String tempArray = tempFloor.floorArrayToString(tempFloor.getPassengerRequest());
			System.out.print(tempArray + "|");
		}
		System.out.print("\n");
		System.out.format("Approaching Elevators\t|ELEVATOR %d\t | ELEVATOR %d\t | ELEVATOR %d\t | ELEVATOR %d\t | ELEVATOR %d\t |\n", 
			BuildingManager.floors[0].getApproachingElevator(),BuildingManager.floors[1].getApproachingElevator(),
			BuildingManager.floors[2].getApproachingElevator(),BuildingManager.floors[3].getApproachingElevator(),
			BuildingManager.floors[4].getApproachingElevator());
	}
	//Returns a specific floor
	public BuildingFloor getBuildingFloor( int floorNumb ){
		return BuildingManager.floors[floorNumb];
	}
	//Returns amount of floors in the building
	public int getFloorTotal(){
		return BuildingManager.floors.length;
	}
	//Elevators use this to check if any floor has passengers waiting to be picked up
	public int checkFloorPassengers(){
		for( int i = 0; i < BuildingManager.floors.length; i++){
			if(BuildingManager.floors[i].checkPassengerRequest()){
				return i;
			}
		}
		return -1;
	}
	//picks up the passengers from the floor
	//updates the floor's current passengerRequest array
	//adds in the elevator Id
	public synchronized int[] setElevatorLoading(int elevatorId, int floorNumb){
		return BuildingManager.floors[floorNumb].updatePassengerRequest(floorNumb);
	}
	//checks if an elevator is already approaching a floor for loading
	public boolean checkApproaching( int elevatorId, int floorNumb ){
		if( BuildingManager.floors[floorNumb].getApproachingElevator() < 0 ){
			BuildingManager.floors[floorNumb].setApproachingElevator(elevatorId);
			return true;
		}
		return false;
	}

}
