import java.util.ArrayList;

public class Citizen{
	
	private House house;
	private City city;
	private Job job;
	
	private double education;
	
	private int id;
	
	public static int totalCitizenCount = 0;
	public static ArrayList<Citizen> allCitizens = new ArrayList<Citizen>();
	
	public Citizen(House nhouse, City ncity){
		house = nhouse;
		city = ncity;
		education = Math.random();
		id = totalCitizenCount;
		totalCitizenCount++;
		
		city.addCitizen(this);
		
		allCitizens.add(this);
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
	
	public static Citizen getById(int id){
		for(Citizen citizen: allCitizens){
			if(citizen.getId() == id){
				return citizen;
			}
		}
		return null;
	}
	
	public static int getTotalCitizenCount(){
		return totalCitizenCount;
	}
}
