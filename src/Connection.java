import java.util.ArrayList;

public class Connection{

	private City endpointA;
	private City endpointB;
	private double distance;
	private double speed;
	private double cost;
	
	public static ArrayList<Connection> allConnections = new ArrayList<Connection>();
	
	private int id;
	
	public static int totalConnectionCount = 0;
	
	public Connection(City nEndpointA, City nEndpointB){
		id = totalConnectionCount;
		totalConnectionCount++;
		
		endpointA = nEndpointA;
		endpointB = nEndpointB;
		
		double xDistance = endpointA.getXPos() - endpointB.getXPos();
		double yDistance = endpointA.getYPos() - endpointB.getYPos();
		
		distance = Math.sqrt(Math.pow(xDistance,2 )+Math.pow(yDistance, 2));
		
		speed = (0.5 + Math.random())/2;
		cost = distance/speed;
		
		allConnections.add(this);
	}
	
	public double getDistance(){
		return distance;
	}
	
	public double getSpeed(){
		return speed;
	}
	
	public double getCost(){
		return cost;
	}
	
	public boolean hasEndpoint(City toCheck){
		return (endpointA == toCheck || endpointB == toCheck);
	}
	
	public City getOtherEndpoint(City firstEndpoint){
		if(firstEndpoint == endpointA){
			return endpointB;
		} else if(firstEndpoint == endpointB){
			return endpointA;
		} else {
			return null;
		}
	}
	
	public City getEndpointA(){
		return endpointA;
	}
	
	public City getEndpointB(){
		return endpointB;
	}
	
	public int getId(){
		return id;
	}
	
	public static int getTotalConnectionCount(){
		return totalConnectionCount;
	}
	
	public static Connection getById(int id){
		for(Connection connection: allConnections){
			if(connection.getId() == id){
				return connection;
			}
		}
		return null;
	}
}
