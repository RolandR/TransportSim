import java.util.ArrayList;

public class Job{

	private Company company;
	private double salary;
	private boolean isOccupied;
	private Citizen employee;
	private City city;
	private int id;
	
	private static int totalJobCount;
	
	public ArrayList<Job> allJobs = new ArrayList<Job>();
	
	public Job(Company nCompany, double salaryModifier){
		company = nCompany;
		city = company.getCity();
		salary = (40+Math.random()*20)*salaryModifier;
		
		id = totalJobCount;
		totalJobCount++;
		allJobs.add(this);
	}
	
	public Citizen getEmployee(){
		return employee;
	}
	
	public double getSalary(){
		return salary;
	}
	
	public boolean getIsOccupied(){
		return isOccupied;
	}
	
	public int getId(){
		return id;
	}
	
	public static int getTotalJobCount(){
		return totalJobCount;
	}
}
