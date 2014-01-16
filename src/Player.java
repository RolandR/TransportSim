import java.util.ArrayList;

public class Player{
	private static double balance;
	
	private static ArrayList<Connection> ownedConnections;
	
	public Player(double initialBalance){
		balance = initialBalance;
	}
	
	public int buyConnection(Connection connection){
		for(Connection currentConnection: ownedConnections){
			if(currentConnection == connection){
				return 1; // 1: Owned already
			} else {
				double connectionPrice = connection.getDistance() * connection.getSpeed();
				if(connectionPrice > balance){
					return 2; // 2: Too expensive
				} else {
					ownedConnections.add(connection);
					balance -= connectionPrice;
				}
			}
		}
		return 0;
	}
	
	public void getPaid(double income){
		balance += income;
	}
}
