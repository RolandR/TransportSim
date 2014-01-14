import java.util.ArrayList;

public class Citizen{
	
	private House house;
	private City city;
	private Job job;
	
	private double education;
	
	private int id;
	
	private static int totalCitizenCount = 0;
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
	
	public static int getTotalCitizenCount(){
		return totalCitizenCount;
	}
	
}
