import java.io.*;
import java.util.*;

public class ElevatorSimulation
{
	//Attributes
	private Elevator zeroElevator;
	private Elevator oneElevator;
	private Elevator twoElevator;
	private Elevator threeElevator;
	private Elevator fourElevator;
	private int simulatedTimeLimit;
	private int simulatedSeconds;
	private BuildingManager buildingManager;
	private ArrayList<ArrayList<PassengerArrival>> passengerArrivalConfig;
	private Scanner readFile;
	
	//constructor
	public ElevatorSimulation(){
		this.buildingManager = new BuildingManager();
		this.zeroElevator = new Elevator(0, buildingManager);
		this.oneElevator = new Elevator(1, buildingManager);
		this.twoElevator = new Elevator(2, buildingManager);
		this.threeElevator = new Elevator(3, buildingManager);
		this.fourElevator = new Elevator(4, buildingManager);
		this.passengerArrivalConfig = new ArrayList<ArrayList<PassengerArrival>>();
	}
	//Start() begins the entire Elevator simulation 
	//by retrieving info from the txt file and running the elevators
	public void start(){
		this.setElevatorConfig();
		while( SimClock.getTime() < this.simulatedTimeLimit ){
			try
			{
				Thread.sleep(0);//this.simulatedSeconds);
				System.out.format("***************** %-3d*****************\n", SimClock.getTime());
				this.setPassengerArrival();
				this.runElevators();
				SimClock.tick();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	//prints state of elevator/building in simulation state
	public void printBuildingState(){
		System.out.println("Building State at TIME = " + SimClock.getTime() + "\n*******************************************");
		buildingManager.printInfo();
		this.printElevatorsInfo();

	}
	//Single method to print out all the information of the levators
	public void printElevatorsInfo(){
		System.out.println("\nElevator State at TIME = " + SimClock.getTime() + "\n*******************************************");
		this.zeroElevator.elevatorToString();
		System.out.println("*******************************************");
		this.oneElevator.elevatorToString();
		System.out.println("*******************************************");
		this.twoElevator.elevatorToString();
		System.out.println("*******************************************");
		this.threeElevator.elevatorToString();
		System.out.println("*******************************************");
		this.fourElevator.elevatorToString();
		System.out.println("*******************************************");
	}
	//Single method to activate all elevators
	public void runElevators(){
		this.zeroElevator.run();
		this.oneElevator.run();
		this.twoElevator.run();
		this.threeElevator.run();
		this.fourElevator.run();
	}
	//Reads the text file and extracts the information from each line accordingly
	public void setElevatorConfig(){
		try{
			readFile = new Scanner(new File("C:\\Users\\Justin\\ICS_45j\\Lab4\\src\\ElevatorConfig.txt"));
			readFile.useDelimiter(";");
			for(int i = 0; i <7; i++){
				switch(i){
				case 0:
					this.simulatedTimeLimit = Integer.parseInt(readFile.nextLine());
					break;
				case 1:
					this.simulatedSeconds = Integer.parseInt(readFile.nextLine());
					break;
				default:
					String[] parsedFileLine = readFile.nextLine().split(";");
					ArrayList<PassengerArrival> tempArrayList = new ArrayList<PassengerArrival>();
					for(int j = 0; j<parsedFileLine.length; j++){
						String[] parsedLine = parsedFileLine[j].split(" ");
						PassengerArrival passengerArrival = new PassengerArrival( 	Integer.parseInt(parsedLine[0]), 
																					Integer.parseInt(parsedLine[1]), 
																					Integer.parseInt(parsedLine[2]));
						tempArrayList.add(passengerArrival);
					}
					this.passengerArrivalConfig.add(tempArrayList);
					break;
				}
			}
		}catch( Exception e ){
			System.out.println("Error: File not found." + e);
		}
	}
	//This spawns passengers according to the simulated time as stated by the txt
	public void setPassengerArrival(){
		for(int i = 0; i < this.passengerArrivalConfig.size(); i++){
			ArrayList<PassengerArrival> tempArrayList = passengerArrivalConfig.get(i);
			for( int j = 0; j < tempArrayList.size(); j++){
				PassengerArrival currPassengerArrival = tempArrayList.get(j);
				int passengerSpawn = currPassengerArrival.getTimePeriod();
				if( SimClock.getTime() != 0 && SimClock.getTime() % passengerSpawn == 0 ){
					buildingManager.passengerSpawnConfig(i, currPassengerArrival);
				}
			}
			
		}
	}
	//Prints out the extracted information from the txt file
	public void configToString(){
		for(int i = 0; i < this.passengerArrivalConfig.size(); i++){
			System.out.print("Floor " + i + "| ");
			ArrayList<PassengerArrival> tempArrayList= passengerArrivalConfig.get(i);
			for( int j = 0; j < tempArrayList.size(); j++){
				System.out.print( tempArrayList.get(j).toString() + ", ");
			}
			System.out.print("\n");
		}
	}
}
