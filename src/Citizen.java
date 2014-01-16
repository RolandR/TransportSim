import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map;

public class Citizen{
	
	private House house;
	private City city;
	private Job job;
	
	private double education;
	
	private int id;
	
	private City currentPosition;
	private City currentDestination;
	private Connection connectionUsed;
	private double distanceTravelled = 0.0;
	private boolean currentlyTravelling = false;
	
	public static int totalCitizenCount = 0;
	public static ArrayList<Citizen> allCitizens = new ArrayList<Citizen>();
	
	public static int currentlyTravellingCount = 0;
	
	public Citizen(House nhouse, City ncity){
		house = nhouse;
		city = ncity;
		currentPosition = city;
		education = Math.random();
		id = totalCitizenCount;
		totalCitizenCount++;
		
		city.addCitizen(this);
		
		allCitizens.add(this);
	}
	
	public boolean searchJob(){
		SortedMap<Double, Job> possibleJobs = new TreeMap<Double, Job>();
		
		for(Job consideredJob: Job.getAllJobs()){
			double jobPreferability = consideredJob.getSalary();
			jobPreferability -= city.getConnectionTo(consideredJob.getCity()).getCost();
			
			jobPreferability = 0 - jobPreferability;
			possibleJobs.put(jobPreferability, consideredJob);
		}
		
		for(Map.Entry<Double, Job> entry: possibleJobs.entrySet()) {
			if(job == null){
				if(entry.getValue().applyForJob(this)){
					job = entry.getValue();
					break;
				}
			} else {
				break;
			}
		}
		
		/*if(id == 0){
			
			for(Map.Entry<Double, Job> entry : possibleJobs.entrySet()) {
			  Double key = entry.getKey();
			  Job value = entry.getValue();

			  ConsoleHandler.print(key + " => " + value.getId());
			}
		}*/
		
		return false;
	}
	
	public void quitJob(boolean fired){
		if(!fired){
			job.employeeQuits();
		}
		job = null;
	}
	
	public void travel(){
		double time = World.getTime();
		if(currentlyTravelling){
			distanceTravelled += connectionUsed.getSpeed() * TransportSim.getGameDayPerSecond()*10;
			if(distanceTravelled > connectionUsed.getDistance()){
				currentlyTravelling = false;
				currentPosition = currentDestination;
				connectionUsed = null;
				currentDestination = null;
				distanceTravelled = 0;
				currentlyTravellingCount--;
			}
		} else {
			if(job != null){
				if(city != job.getCity()){
					if(currentPosition == city && time > (11/24.0 + (Util.gaussianRandom() * (5/24.9))) && time < (12/24.0)){ // It's between about 06:00 and 12:00. Let's go to work!
						currentlyTravelling = true;
						currentDestination = job.getCity();
						connectionUsed = city.getConnectionTo(job.getCity());
						distanceTravelled = 0;
						currentlyTravellingCount++;
					} else if(currentPosition == job.getCity() && time > (20/24.0 + (Util.gaussianRandom() * (5/24.0)))){ // It's around 16:00. Let's go home!
						currentlyTravelling = true;
						currentDestination = city;
						connectionUsed = city.getConnectionTo(job.getCity());
						distanceTravelled = 0;
						currentlyTravellingCount++;
					}
				}
			}
		}
	}
	
	public static void updateTravel(){
		for(Citizen currentCitizen: allCitizens){
			currentCitizen.travel();
		}
	}
	
	public int getId(){
		return id;
	}
	
	public House getHouse(){
		return house;
	}
	
	public City getCity(){
		return city;
	}
	
	public Job getJob(){
		return job;
	}
	
	public double getEducation(){
		return education;
	}
	
	public boolean isCurrentlyTravelling(){
		return currentlyTravelling;
	}
	
	public City getCurrentPosition(){
		return currentPosition;
	}
	
	public double getDistanceTravelled(){
		return distanceTravelled;
	}
	
	public City getCurrentDestination(){
		return currentDestination;
	}
	
	public Connection getConnectionUsed(){
		return connectionUsed;
	}
	
	public static void init(){
		for(Citizen citizen: allCitizens){
			citizen.searchJob();
		}
	}
	
	public static void unemployedLookForJobs(){
		for(Citizen citizen: allCitizens){
			if(citizen.getJob() == null){
				if(Math.random() > 0.5){
					citizen.searchJob();
				}
			}
		}
	}
	
	public static Citizen getById(int id){
		for(Citizen citizen: allCitizens){
			if(citizen.getId() == id){
				return citizen;
			}
		}
		return null;
	}
	
	public static ArrayList<Citizen> getAllCitizens(){
		return allCitizens;
	}
	
	public static int getTotalCitizenCount(){
		return totalCitizenCount;
	}
	
	public static int getCurrentlyTravellingCount(){
		return currentlyTravellingCount;
	}
}
