import java.util.ArrayList;

public class Company{

	private City city;
	private double salaryModifier;
	private int jobCount;
	private double averageSalary;
	
	private int id;
	
	private static int totalCompanyCount;
	
	private ArrayList<Job> jobs = new ArrayList<Job>();
	
	public static ArrayList<Company> allCompanies = new ArrayList<Company>();
	
	public Company(City nCity){
		city = nCity;
		salaryModifier = (2+Math.random())/3;
		jobCount = 1+(int)Math.round(Math.random()*30);
		
		double totalSalary = 0;
		for(int i = 0; i < jobCount; i++){
			Job createdJob = createJob();
			totalSalary += createdJob.getSalary();
		}
		averageSalary = totalSalary / (double)jobCount;
		
		id = totalCompanyCount;
		totalCompanyCount++;
		
		allCompanies.add(this);
	}
	
	private Job createJob(){
		jobs.add(new Job(this, salaryModifier));
		return jobs.get(jobs.size() - 1);
	}
	
	public double getSalaryModifier(){
		return salaryModifier;
	}
	
	public int getJobCount(){
		return jobCount;
	}
	
	public City getCity(){
		return city;
	}
	
	public int getId(){
		return id;
	}
	
	public ArrayList<Job> getJobs(){
		return jobs;
	}
	
	public double getAverageSalary(){
		return averageSalary;
	}
	
	public static int getTotalCompanyCount(){
		return totalCompanyCount;
	}
	
	public static Company getById(int id){
		for(Company company: allCompanies){
			if(company.getId() == id){
				return company;
			}
		}
		return null;
	}
}
