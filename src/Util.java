import java.util.Random;

class Util{
	private static Random randomGen = new java.util.Random();
	
	public static String formatMoney(double amount){
		String roundedAmount = (Math.round(amount*20)/20.0)+"";
		
		String afterDecimal = roundedAmount.substring(roundedAmount.indexOf("."));
		
		if(afterDecimal.length() == 2){
			roundedAmount+= "0";
		}
	
		return roundedAmount;
	}
	
	public static String formatTime(double time){
		String timeString = "";
		
		time = time * 24;
		int hours = (int)time;
		
		timeString += (String.format("%02d", hours)+":");
		
		timeString += (String.format("%02d", (int)((time - hours)*60)));
		
	
		return timeString;
	}
	
	public static double gaussianRandom(){
		return randomGen.nextGaussian() / 3;
	}
}
