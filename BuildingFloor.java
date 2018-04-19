public class BuildingFloor
{
	//Attributes
	private int[] totalDestinationRequests;
	private int[] arrivedPassenger;
	private int[] passengerRequest;
	private int approachingElevator;
	
	//constructor
	public BuildingFloor(){
		this.totalDestinationRequests = new int[]{0,0,0,0,0};
		this.arrivedPassenger = new int[]{0,0,0,0,0};
		this.passengerRequest = new int[]{0,0,0,0,0};
		this.approachingElevator = -1;
	}
	//Getters
	public int[] getTotalDestinationRequests()
	{
		return totalDestinationRequests;
	}

	public int[] getArrivedPassenger()
	{
		return arrivedPassenger;
	}

	public int[] getPassengerRequest()
	{
		return passengerRequest;
	}
	public int getApproachingElevator()
	{
		return approachingElevator;
	}
	//Setters
	public void setTotalDestinationRequests(int floorNumb, int passengerNumb){
		this.totalDestinationRequests[floorNumb] += passengerNumb;
	}

	public void setArrivedPassenger( int floorNumb, int passengerNumb ){
		this.arrivedPassenger[floorNumb] += passengerNumb;
	}
	public void setPassengerRequest( int floorNumb, int passengerNumb ){
		this.passengerRequest[floorNumb] = passengerNumb;
	}
	public void setApproachingElevator(int approachingElevator)
	{
		this.approachingElevator = approachingElevator;	
	}
	//Decides of an elevator should pick up people going up or down. Then returns an array which will show
	//who the elevator loaded. It also updates the elevator Id, as well as the passenger request array after loading
	public int[] updatePassengerRequest( int floorNumb){
		int[] newArray = new int[]{0,0,0,0,0};
		boolean has_passengers = false;
		for (int i = floorNumb; i < this.passengerRequest.length; i++) {
			if (this.passengerRequest[i] != 0) {
				newArray[i] = this.passengerRequest[i];
				this.passengerRequest[i] = 0;
				has_passengers = true;
			}
		}
		if (!has_passengers) {
			for (int i = 0; i < this.passengerRequest.length; i++) {
				if (this.passengerRequest[i] != 0) {
					newArray[i] = passengerRequest[i];
					this.passengerRequest[i] = 0;
				}
			}
		}
		return newArray;	
	}
	//Checks if the floor currently has passengers waiting to be loaded
	public boolean checkPassengerRequest(){
		for( int i = 0; i < this.passengerRequest.length; i++ ){
			if( this.passengerRequest[i] > 0){
				return true;
			}
		}
		return false;
	}
	//To string method for each of the arrays
	public String floorArrayToString( int[] floorArray){
		String finalString = "";
		for( int i = 0; i < floorArray.length; i++){
			String tempString = String.format("%-2d", floorArray[i]);
			finalString +=  tempString + " ";
		}
		return finalString;
	}
}
