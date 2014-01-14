
class Util{
	public static String formatMoney(double amount){
		String roundedAmount = (Math.round(amount*20)/20.0)+"";
		
		String afterDecimal = roundedAmount.substring(roundedAmount.indexOf("."));
		
		if(afterDecimal.length() == 2){
			roundedAmount+= "0";
		}
	
		return roundedAmount;
	}
}
