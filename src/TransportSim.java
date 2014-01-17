
public class TransportSim{
	private static double gameDayPerSecond = 0.04;
	private static Player player;
	
	public static void main(String[] args){
		
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.start();
				
		World world = new World(50, 30, 10, args);
		
		player = new Player(1000.0);
		
		GameWindow gameWindow = new GameWindow(world);
		
		GameLoop gameLoop = new GameLoop(60, gameWindow, gameDayPerSecond);
		gameLoop.start();
	}
	
	public static double getGameDayPerSecond(){
		return gameDayPerSecond;
	}
	
	public static Player getPlayer(){
		return player;
	}
}
