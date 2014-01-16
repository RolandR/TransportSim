import java.util.ArrayList;

public class Job{

	private Company company;
	private double salary;
	private boolean isOccupied;
	private Citizen employee;
	private City city;
	private int id;
	
	private static double randomFireRate = 0.05;
	
	private static int totalEmployees;
	
	private static int totalJobCount;
	
	public static ArrayList<Job> allJobs = new ArrayList<Job>();
	
	public Job(Company nCompany, double salaryModifier){
		company = nCompany;
		city = company.getCity();
		salary = (40+Math.random()*20)*salaryModifier;
		
		id = totalJobCount;
		totalJobCount++;
		allJobs.add(this);
	}
	
	public boolean applyForJob(Citizen citizen){
		if(employee != null){
			return false;
		} else if(Math.random() > 0.5 + (0.4*citizen.getEducation())){
			employee = citizen;
			totalEmployees++;
			
			return true;
		} else {		
			return false;
		}
	}
	
	public void employeeQuits(){
		employee = null;
		totalEmployees--;
	}
	
	public void fireEmployee(){
		if(employee != null){
			employee.quitJob(true);
			employee = null;
			totalEmployees--;
		}
	}
	
	public Citizen getEmployee(){
		return employee;
	}
	
	public City getCity(){
		return city;
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
	
	public static void randomlyFireSomeEmployees(){ // Because that's what all good businesses do
		for(Job currentJob: allJobs){
			if(Math.random() < randomFireRate){
				currentJob.fireEmployee();
			}
		}
	}
	
	public static int getTotalEmployees(){
		return totalEmployees;
	}
	
	public static ArrayList<Job> getAllJobs(){
		return allJobs;
	}
	
	public static int getTotalJobCount(){
		return totalJobCount;
	}
}
