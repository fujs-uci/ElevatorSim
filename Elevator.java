import java.util.*;

public class Elevator implements Runnable
{
	//attributes
	private int elevatorId; 
	private int currentFloor;
	private int numPassengers; 
	private int totalLoadedPassengers; 
	private int totalUnloadedPassengers;
	private int[] totalPassengerDestination;
	private ArrayList<ElevatorEvent> moveQueue; 
	private int[] passengerDestination;
	private BuildingManager manager;
	private int moveQueueIndex;

	//constructor
	public Elevator(int elevatorId, BuildingManager manager ){
		this.moveQueue = new ArrayList<ElevatorEvent>();
		this.elevatorId = elevatorId;
		this.manager = manager;
		this.passengerDestination = new int[]{0,0,0,0,0};
		this.totalPassengerDestination = new int[]{0,0,0,0,0};
		this.currentFloor = 0;
		this.totalLoadedPassengers = 0;
		this.totalUnloadedPassengers = 0;
		this.moveQueueIndex = 0;
	}
	//Turns the elevator arrays into strings
	public String elevatorArrayToString(int[] elevatorArray){
		String tempString = String.format("{%-2d, %-2d, %-2d, %-2d, %-2d}", elevatorArray[0],elevatorArray[1],
																  elevatorArray[2],elevatorArray[3],
																  elevatorArray[4]);
		return tempString;
	}
	//Prints out the information for the Elevator
	public void elevatorToString(){
		System.out.println("Elevator ID: " + this.elevatorId);
		System.out.println("Total Passengers Entered: " + this.totalLoadedPassengers);
		System.out.println("\t\t\t  0   1   2   3   4");
		System.out.println("Total Passengers Exited: " + this.elevatorArrayToString(this.totalPassengerDestination));
		System.out.println("Current Passengers:\t"+ " " + this.elevatorArrayToString(this.passengerDestination));
	}
	//creates a string for floor destinations
	public String stringFloorDestinations(){
		String tempString = "";
		for( int i = 0; i < this.passengerDestination.length; i++){
			if( this.passengerDestination[i] != 0 ){
				tempString += Integer.toString(i) + ", ";
			}
		}
		return tempString;
	}
	//Called every simulated second
	//Controls the movement of the elevators
	public void run(){
		if( this.moveQueue.size() == 0){
			int floorNumb = manager.checkFloorPassengers();
			if(floorNumb != -1 && manager.checkApproaching(this.elevatorId, floorNumb)){
				System.out.format("Elevator %d: is approaching Floor %d for passenger loading.\n", this.elevatorId, floorNumb);
				setElevatorEvent(floorNumb);
				this.passengerDestination = manager.setElevatorLoading(this.elevatorId, floorNumb); 
				sumOfPassengers();
				createDropOffEvents(floorNumb);
				}
		}
		ElevatorEvent currentEvent;
			
		while(this.moveQueue.size()!=0){ 
			currentEvent = this.moveQueue.get(this.moveQueueIndex);
			if(SimClock.getTime() == currentEvent.getExpectedArrival()){ 
				if( this.moveQueue.indexOf(currentEvent) == 0){ 
					this.currentFloor = currentEvent.getDestination();
					manager.getBuildingFloor(this.currentFloor).setApproachingElevator(-1);
					this.moveQueueIndex += 1;	
					System.out.format("Elevator %d: reached Floor %d going to floor(s) %s with %d passengers loaded.\n", 
										this.elevatorId, this.currentFloor, this.stringFloorDestinations(),this.numPassengers);
					currentEvent = this.moveQueue.get(this.moveQueueIndex);
					this.changeElevatorEvent(currentEvent, currentEvent.getDestination());
				}else{
					if (this.moveQueueIndex < this.moveQueue.size()) {
						int passengerAmount = this.passengerDestination[currentEvent.getDestination()];
						this.manager.getBuildingFloor(currentEvent.getDestination()).setArrivedPassenger(this.currentFloor, passengerAmount);
						this.currentFloor = currentEvent.getDestination();
						this.numPassengers -= passengerAmount;
						this.totalUnloadedPassengers+= passengerAmount;
						this.moveQueueIndex+=1;
						System.out.format("Elevator %d: reached Floor %d and unloaded %d passengers.\n"
										, this.elevatorId, this.currentFloor, passengerAmount);
						if( this.moveQueueIndex != this.moveQueue.size()){
							currentEvent = this.moveQueue.get(this.moveQueueIndex);
							this.changeElevatorEvent(currentEvent, currentEvent.getDestination());
						}else{
							this.moveQueue = new ArrayList<ElevatorEvent>();
							this.moveQueueIndex = 0;
							continue;
						}
					}
				}
			}
			break;
		}
	}
	//Creates elevator events after the elevator has loaded passengers
	//These events are added in the order of which passengers to dropp off first
	public void createDropOffEvents(int destinationFloor){
		int checkingDirection = checkDirection( destinationFloor );
		if( checkingDirection > 0){
			for(int i = destinationFloor; i< this.passengerDestination.length; i++){
				if(this.passengerDestination[i] != 0){
					setElevatorEvent(i);
				}
			}
		}else{
			for(int i = destinationFloor; i>= 0; i--){
				if(this.passengerDestination[i] != 0){
					setElevatorEvent(i);
				}
			}
		}
	}
	//Checks which direction to drop off passengers
	public int checkDirection(int destinationFloor){
		int checkingDirection = 0;
		for(int i = 0; i < this.passengerDestination.length; i ++ ){
			if( this.passengerDestination[i] != 0){
				if( i < destinationFloor){
					checkingDirection -= 1;
				}else{
					checkingDirection += 1;
				}
			}
		}
		return checkingDirection;
	}
	//creates elevator events and adds them to the move queue, calculates the expected time of arrival as well
	public void setElevatorEvent(int floorNumb){
		int expectedArrival = ((Math.abs(floorNumb - this.currentFloor) * 5) + 10) + SimClock.getTime();
		ElevatorEvent newEvent = new ElevatorEvent(floorNumb, expectedArrival);
		this.moveQueue.add(newEvent);
	}
	//Updates the expected time of arrival
	public void changeElevatorEvent(ElevatorEvent elevatorEvent, int floorNumb) {
		int expectedArrival = ((Math.abs(floorNumb - this.currentFloor) * 5) + 10) + SimClock.getTime();
		elevatorEvent.setExpectedArrival(expectedArrival);
	}
	//Calculates the total number of passengers currently in the elevator
	//updates the total amount of passengers the elevator has loaded
	public void sumOfPassengers(){
		int totalPassengers = 0;
		for( int i = 0; i < passengerDestination.length; i++ ){
			totalPassengers += passengerDestination[i];
			this.totalPassengerDestination[i] += passengerDestination[i];
		}
		this.numPassengers = totalPassengers;
		this.totalLoadedPassengers += totalPassengers;
	}
}
