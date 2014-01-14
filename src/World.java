import java.util.ArrayList;

public class World{
	
	private int cityCount;
	private int width;
	private int height;
	
	private boolean verbose = false;
	
	public World(int nWidth, int nHeight, int nCityCount, String[] args){
	
		// TODO: Parse arguments in the TransportSim class.
		// Also, do the logging stuff there.
		// In fact, it'd be neat if all console output was done there.
		// Now that I think of it, I should switch from System.out.println() to something custom.
		// So much code, so little time...
		
		for(String argument: args){
			System.out.println(argument);
			if(argument.equals("-v")){
				verbose = true;
			}
		}
		
		cityCount = nCityCount;
		width = nWidth;
		height = nHeight;
		
		for(int i = 0; i < cityCount; i++){
			
			int cityX = (int)Math.floor(Math.random()*width);
			int cityY = (int)Math.floor(Math.random()*height);
			
			new City(cityX, cityY);
		}
		
		if(verbose){
			for(int i = 0; i < cityCount; i++){
				City currentCity = City.allCities.get(i);
				System.out.println("City "+currentCity.getId()+":");
				System.out.println(
					"\tPos: "+currentCity.getXPos()+"/"+currentCity.getYPos()
				);
				System.out.println();
				System.out.println("\tHouses: "+currentCity.getHouseCount());
				System.out.println("\tHousing capacity: "+currentCity.getHousingCapacity());
				System.out.println("\tAverage rent: "+
					(double)Math.round(currentCity.getAverageRent()*1000)/1000
				);
				System.out.println();
				System.out.println("\tCompanies: "+currentCity.getCompanyCount());
				System.out.println("\tJobs: "+currentCity.getJobCount());
				System.out.println("\tAverage salary: "+
					(double)Math.round(currentCity.getAverageSalary()*1000)/1000
				);
				System.out.println();
				System.out.println("\tConnections:");
				currentCity.printConnections();
			}
		
			System.out.println();
			this.printWorldMap();
			System.out.println();
		
			int housingCapacity = House.getTotalHousingCapacity();
			int jobCount = Job.getTotalJobCount();
			System.out.println("Total housing capacity: "+housingCapacity);
			System.out.println("Total jobs:             "+jobCount);
			System.out.println("   Unemployment rate:   "
				+(double)Math.round(
					((
						(double)housingCapacity - (double)jobCount
					)/(double)housingCapacity
				)*1000)/10
				+"%"
			); // Old and doesn't account for actual citizens
			System.out.println("Total cities:           "+City.getTotalCityCount());
			System.out.println("Total connections:      "+Connection.getTotalConnectionCount());
		
		}
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void printInfo(){
		
		
		
	}
	
	// A wild hack appears!
	// Really, don't use this for anything important.
	public void printWorldMap(){
		for(int h = -1; h <= height; h++){
			String mapLine = "";
			for(int w = -1; w <= width; w++){
				boolean isCity = false;
				int cityId = 0;
				for(int i = 0; i < City.allCities.size(); i++){
					City currentCity = City.allCities.get(i);
					if(currentCity.getXPos() == w && currentCity.getYPos() == h){
						isCity = true;
						cityId = currentCity.getId();
					}
				}	
				if(isCity){
					mapLine += cityId;
				} else if(h == -1 && w == -1){
					mapLine += '╔';
				} else if(h == -1 && w == width){
					mapLine += '╗';
				} else if(h == height && w == -1){
					mapLine += '╚';
				} else if(h == height && w == width){
					mapLine += '╝';
				} else if(h == -1 || h == height){
					mapLine += '═';
				} else if(w == -1 || w == width){
					mapLine += '║';
				} else {
					mapLine += '.';
				}
			}
			System.out.println(mapLine);
		}
	}
}
