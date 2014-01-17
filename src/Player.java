import java.util.ArrayList;

public class Player{
	private static double balance;
	
	private static ArrayList<Connection> ownedConnections = new ArrayList<Connection>();
	
	public Player(double initialBalance){
		balance = initialBalance;
	}
	
	public int buyConnection(Connection connection){
		int check = checkBuyingConnection(connection);
		if(check == 0){
			double connectionPrice = connection.getPrice();
			ownedConnections.add(connection);
			balance -= connectionPrice;
			connection.changeOwner(this);
			return 0;
		} else {
			return check;
		}
	}
	
	public int checkBuyingConnection(Connection connection){
		if(ownsConnection(connection)){
			return 1; // 1: Owned already
		} else {
			double connectionPrice = connection.getPrice();
			if(connectionPrice > balance){
				return 2; // 2: Too expensive
			} else {
				return 0; // 0: Can buy
			}
		}
	}
	
	public int checkUpgradingConnection(Connection connection, double upgradeSpeed){
		if(!ownsConnection(connection)){
			return 1; // 1: Needs to own for upgrade
		} else {
			double connectionPrice = connection.getUpgradeCost(upgradeSpeed);
			if(connectionPrice > balance){
				return 2; // 2: Too expensive
			} else {
				return 0; // 0: Can upgrade
			}
		}
	}
	
	public int upgradeConnection(Connection connection, double upgradeSpeed){
		int check = checkUpgradingConnection(connection, upgradeSpeed);
		if(check == 0){
			double upgradeCost = connection.getUpgradeCost(upgradeSpeed);
			balance -= upgradeCost;
			connection.upgradeSpeed(upgradeSpeed);
			return 0;
		} else {
			return check;
		}
	}
	
	public void getPaid(double income){
		balance += income;
	}
	
	public double getBalance(){
		return balance;
	}
	
	public boolean ownsConnection(Connection connection){
		for(Connection currentConnection: ownedConnections){
			if(currentConnection == connection){
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Connection> getOwnedConnections(){
		return ownedConnections;
	}
	
	public int getOwnedConnectionsCount(){
		return ownedConnections.size();
	}
}
