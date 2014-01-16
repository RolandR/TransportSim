
public class GameLoop extends Thread{
	
	int delayInMilliseconds;
	int bigTickInMilliseconds = 1000;
	double gameDayPerSecond;
	
	GameWindow gameWindow;
	
	public GameLoop(int fps, GameWindow ngameWindow, double ngameDayPerSecond){
		delayInMilliseconds = 1000/fps;
		gameDayPerSecond = ngameDayPerSecond;
		gameWindow = ngameWindow;
		
		long lastBigTick = System.currentTimeMillis();
		long currentTime;
		
		while(true){
			currentTime = System.currentTimeMillis();
			
			World.setTime(((currentTime / 1000.0) % (1/gameDayPerSecond))*gameDayPerSecond);
			
			smallTick();
			
			if(currentTime - lastBigTick >= bigTickInMilliseconds){
				bigTick();
				lastBigTick = System.currentTimeMillis();
			}
			
			try{
				GameLoop.sleep(delayInMilliseconds);
			} catch(InterruptedException e){
				
			}
		}
	}
	
	private void smallTick(){
		Citizen.updateTravel();
		gameWindow.refresh();
	}
	
	private void bigTick(){
		Job.randomlyFireSomeEmployees();
		Citizen.unemployedLookForJobs();
		gameWindow.refresh();
	}
	
}
