
public class House{

	private City city;
	private int capacity;
	private double rent;
	public static int totalHousingCapacity;
	
	public House(City nCity){
		city = nCity;
		capacity = (int)Math.round(1+Math.random()*30);
		totalHousingCapacity += capacity;
		rent = 5+5*(10 + Math.random()*3)/(2+(double)capacity/3);
		//System.out.println("rent: "+rent);
		//System.out.println("capa: "+capacity);
		//System.out.println();
		
		city.changeHousingCapacity(capacity);
	}
	
	public double getRent(){
		return rent;
	}
	
	public static int getTotalHousingCapacity(){
		return totalHousingCapacity;
	}
}
