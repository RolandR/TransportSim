import java.util.ArrayList;

public class Company{

	private City city;
	private double salaryModifier;
	private int jobCount;
	private double averageSalary;
	
	private ArrayList<Job> jobs = new ArrayList<Job>();
	
	public Company(City nCity){
		city = nCity;
		salaryModifier = (2+Math.random())/3;
		jobCount = 1+(int)Math.round(Math.random()*30);
		
		//System.out.println();
		//System.out.println("Salary Modifier: "+salaryModifier);
		
		double totalSalary = 0;
		for(int i = 0; i < jobCount; i++){
			Job createdJob = createJob();
			totalSalary += createdJob.getSalary();
		}
		averageSalary = totalSalary / (double)jobCount;
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
	
	public double getAverageSalary(){
		return averageSalary;
	}
}
