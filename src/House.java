import java.util.ArrayList;

public class House{

	private City city;
	private int capacity;
	private double rent;
	
	private ArrayList<Citizen> tenants = new ArrayList<Citizen>();
	
	private int id;
	
	public static int totalHousingCapacity;
	public static int totalHouseCount;
	public static ArrayList<House> allHouses = new ArrayList<House>();
	
	public House(City nCity){
		city = nCity;
		capacity = (int)Math.round(1+Math.random()*30);
		totalHousingCapacity += capacity;
		rent = 5+5*(10 + Math.random()*3)/(2+(double)capacity/3);
		
		city.changeHousingCapacity(capacity);
		
		
		for(int i = 0; i < capacity; i++){
			if(Math.random() < 0.9){
				tenants.add(new Citizen(this, city));
			}
		}
		
		id = totalHouseCount;
		totalHouseCount++;
		allHouses.add(this);
	}
	
	public double getRent(){
		return rent;
	}
	
	public int getHousingCapacity(){
		return capacity;
	}
	
	public ArrayList<Citizen> getTenants(){
		return tenants;
	}
	
	public int getId(){
		return id;
	}
	
	public int getTenantCount(){
		return tenants.size();
	}
	
	public City getCity(){
		return city;
	}
	
	public static int getTotalHousingCapacity(){
		return totalHousingCapacity;
	}
	
	public static House getById(int id){
		for(House house: allHouses){
			if(house.getId() == id){
				return house;
			}
		}
		return null;
	}
}
