import java.util.ArrayList;

public class City{

	private int xPos;
	private int yPos;
	private int id = 0;
	private static int nextId = 0;
	
	private int housingCapacity;
	private int houseCount;
	private double averageRent;
	
	private int jobCount;
	private int companyCount;
	private double averageSalary;
	
	public static int totalCityCount;
	
	private ArrayList<House> houses = new ArrayList<House>();
	
	private ArrayList<Company> companies = new ArrayList<Company>();
	
	private ArrayList<Citizen> inhabitants = new ArrayList<Citizen>();
	
	private ArrayList<Connection> connections = new ArrayList<Connection>();
	
	public static ArrayList<City> allCities = new ArrayList<City>();
	
	public City(int x, int y){
		totalCityCount++;
		
		id = nextId;
		nextId++;
		
		xPos = x;
		yPos = y;
		housingCapacity = 0;
		
		companyCount = 2 + (int)Math.round(Math.random()*10);
		double totalSalary = 0;
		for(int i = 0; i < companyCount; i++){
			Company createdCompany = createCompany();
			jobCount += createdCompany.getJobCount();
			totalSalary += createdCompany.getAverageSalary() * createdCompany.getJobCount();
		}
		averageSalary = totalSalary / jobCount;	
		
		houseCount = 2 + (int)Math.round(Math.random()*10);
		double totalRent = 0;
		for(int i = 0; i < houseCount; i++){
			House createdHouse = createHouse();
			totalRent += createdHouse.getRent();
		}
		averageRent = totalRent/(double)houseCount;
		
		allCities.add(this);	
		
		for(int i = 0; i < allCities.size(); i++){
			this.connectTo(allCities.get(i));
		}
		
		Citizen.init();
	}
	
	public int getId(){
		return id;
	}
	
	public double connectTo(City otherCity){
		Connection createdConnection = new Connection(this, otherCity);
		this.addConnection(createdConnection);
		otherCity.addConnection(createdConnection);
		return createdConnection.getDistance();
	}
	
	public void addConnection(Connection nConnection){
		if(!this.hasConnection(nConnection)){
			connections.add(nConnection);
		}
	}
	
	public ArrayList<Connection> getConnections(){
		return connections;
	}
	
	public Connection getConnectionTo(City otherCity){
		for(int i = 0; i < connections.size(); i++){
			Connection currentConnection = connections.get(i);
			if(currentConnection.hasEndpoint(otherCity)){
				return currentConnection;
			} 
		}
		return null;
	}
	
	public boolean hasConnection(Connection toFind){
		for(int i = 0; i < connections.size(); i++){
			Connection currentConnection = connections.get(i);
			if(currentConnection.getId() == toFind.getId()){
				return true;
			} 
		}
		return false;
	}
	
	public void printConnections(){
		for(int i = 0; i < connections.size(); i++){
			Connection currentConnection = connections.get(i);
			City connectedCity = currentConnection.getOtherEndpoint(this);
			System.out.println("\t\tConnection "+currentConnection.getId()+":");
			if(connectedCity != null){
				System.out.println("\t\tConnects to city "+connectedCity.getId());
				System.out.println("\t\tLength: "+currentConnection.getDistance());
				//System.out.println("\t\tSpeed: "+currentConnection.getSpeed());
				//System.out.println("\t\tCost: "+currentConnection.getCost());
			} else { 
				System.out.println("\t\tThis connection doesn't involve this city. (bad!)");
			}
			System.out.println();
		}
	}
	
	public void addCitizen(Citizen citizen){
		inhabitants.add(citizen);
	}
	
	public ArrayList<Citizen> getInhabitants(){
		return inhabitants;
	}
	
	private Company createCompany(){
		companies.add(new Company(this));
		return companies.get(companies.size() - 1);
	}
	
	private House createHouse(){
		houses.add(new House(this));
		return houses.get(houses.size() - 1);
	}
	
	public int changeHousingCapacity(int value){
		housingCapacity += value;
		return housingCapacity;
	}
	
	public int getHousingCapacity(){
		return housingCapacity;
	}
	
	public ArrayList<House> getHouses(){
		return houses;
	}
	
	public ArrayList<Company> getCompanies(){
		return companies;
	}
	
	public int getHouseCount(){
		return houseCount;
	}
	
	public double getAverageRent(){
		return averageRent;
	}
	
	public int getCompanyCount(){
		return companyCount;
	}
	
	public int getJobCount(){
		return jobCount;
	}
	
	public double getAverageSalary(){
		return averageSalary;
	}
	
	public int getXPos(){
		return xPos;
	}
	
	public int getYPos(){
		return yPos;
	}
	
	public static ArrayList<City> getAllCities(){
		return allCities;
	}
	
	public static City getById(int id){
		for(City city: allCities){
			if(city.getId() == id){
				return city;
			}
		}
		return null;
	}
	
	public static int getTotalCityCount(){
		return totalCityCount;
	}
}
