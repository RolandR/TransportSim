
public class Job{

	private Company company;
	private double salary;
	private boolean isOccupied;
	private Citizen employee;
	private City city;
	
	private static int totalJobCount;
	
	public Job(Company nCompany, double salaryModifier){
		totalJobCount++;
		company = nCompany;
		city = company.getCity();
		salary = (40+Math.random()*20)*salaryModifier;
		
		//System.out.println("\t"+salary);
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
	
	public static int getTotalJobCount(){
		return totalJobCount;
	}
}
