
public class TransportSim{
	public static void main(String[] args){
		
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.start();
				
		World world = new World(50, 30, 10, args);
		
		GameWindow gameWindow = new GameWindow(world);
		
		GameLoop gameLoop = new GameLoop(60);
	}
}
