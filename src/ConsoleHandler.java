import java.io.*;

public class ConsoleHandler extends Thread{
	
	private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
	
	
	public ConsoleHandler(){
		print("╔══════════════════╗");
		print("║   TransportSim   ║");
		print("║  By Roland Rytz  ║");
		print("╚══════════════════╝");
		print();
		printHelp();
	}
	
	private void printHelp(){
	
		print("The following commands are available:");
		print();
		print("┌────────┬────────┬─────────────────────────────────────────────────────────┐");
		print("│quit    │ q      │ Quits the program                                       │");
		print("├────────┼────────┼─────────────────────────────────────────────────────────┤");
		print("│help    │ ?      │ Prints this list                                        │");
		print("├────────┼────────┼─────────────────────────────────────────────────────────┤");
		print("│examine │ x      │ Prints information about an item                        │");
		print("├────────┴────────┴─────────────────────────────────────────────────────────┤");
		print("│   Params: <c[o]nnection, c[i]ty, c[i]tizen, [h]ouse, compan[y]> <ID>      │");
		print("├────────┬────────┬─────────────────────────────────────────────────────────┤");
		print("│list    │ l      │ Lists items of a type                                   │");
		print("├────────┴────────┴─────────────────────────────────────────────────────────┤");
		print("│   Params: <c[o]nnection, c[i]ty, c[i]tizen, [h]ouse, compan[y]>           │");
		print("└───────────────────────────────────────────────────────────────────────────┘");
		
	}
	
	public void print(String outString){
		System.out.println(outString);
	}
	
	public void print(){
		System.out.println();
	}
	
	public void run(){
		while(true){
			System.out.print("\n>");
			try {
				String inputLine = bufferedReader.readLine();
				inputLine = inputLine.toLowerCase();
				String[] userInput = inputLine.split(" ");
				
				switch(userInput[0]){
					case "quit":
					case "q":
						System.exit(0);
					break;
					
					case "help":
					case "?":
						printHelp();
					break;
					
					case "list":
					case "l":
						String listType;
						try{
							listType = userInput[1];
						} catch (java.lang.ArrayIndexOutOfBoundsException e){
							do{
								print("Please enter one of the following types:");
								print("c[o]nnection, c[i]ty, c[i]tizen, [h]ouse, compan[y]");
								System.out.print(">");
								listType = bufferedReader.readLine().split(" ")[0]; // TODO: Check if string is empty/whitespace first
							} while(listType == null || listType.equals(""));
						}
						
						
						/*
						
						print();
								print("Total houses:           "+Integer.toString(House.totalHouseCount));
								print("Total housing capacity: "+Integer.toString(House.totalHousingCapacity));
								
								//for(House currentHouse: House.allHouses){
								
								int cols = 3;
								int charsPerCol = 15;
								
								for(int y = 0; y < House.allHouses.size(); y += cols){
									String row = "";
									for(int x = 0; x < cols; x++){
										if((x + y) < House.allHouses.size()){
											House currentHouse = House.allHouses.get(y + x);
											row += String.format("%1$"+charsPerCol+"s", "House "+currentHouse.getId()); 
											row += String.format("%1$"+charsPerCol+"s", Util.formatMoney(currentHouse.getRent())); 
											row += "\n";
										}
									}
									print(row);
								}
								
								*/
						
						switch(listType){
							case "connections":
							case "o":
								print();
								print("Total connections: "+Integer.toString(Connection.totalConnectionCount));
								
								for(Connection currentConnection: Connection.allConnections){
									String row = "Conection "+String.format("%1$3s", currentConnection.getId()); 
									row += " - Connects city"+String.format("%1$3s", currentConnection.getEndpointA().getId()); 
									row += " with city"+String.format("%1$3s", currentConnection.getEndpointB().getId()); 
									row += " - Distance"+String.format("%1$6s", (Math.round(currentConnection.getDistance()*10)/10.0)); 
									print(row);
								}
							break;
							case "cities":
							case "i":
								print();
								print("Total cities: "+Integer.toString(City.totalCityCount));
								
								for(City currentCity: City.allCities){
									print("City"+String.format("%1$3s", currentCity.getId())+":"); 
									print("  Population:        "+String.format("%1$4s", currentCity.getInhabitants().size()));
									print("  Houses:             "+String.format("%1$3s", currentCity.getHouses().size()));
									print("  Housing Capacity:  "+String.format("%1$4s", currentCity.getHousingCapacity()));
									print("  Average Rent:     "+String.format("%1$5s", Util.formatMoney(currentCity.getAverageRent())));
									print("  Companies:          "+String.format("%1$3s", currentCity.getCompanies().size()));
									print();
								}
							break;
							case "houses":
							case "h":
								print();
								print("Total houses:           "+Integer.toString(House.totalHouseCount));
								print("Total housing capacity: "+Integer.toString(House.totalHousingCapacity));
								
								for(City currentCity: City.allCities){
									print("Houses in city "+currentCity.getId()+":");
									for(House currentHouse: currentCity.getHouses()){
										String row = "\tHouse "+String.format("%1$3s", currentHouse.getId()); 
										row += " - Rent: "+String.format("%1$6s", Util.formatMoney(currentHouse.getRent())); 
										row += " - Tenants: "+String.format("%1$3s", currentHouse.getTenantCount());
										print(row);
									}
								}
							break;
							default:
								print("Undefined item: "+listType);
							break;
						}
					break;
					
					default:
						print("Undefined command: "+userInput[0]);
					break;
				}
				
			} catch (IOException ioe) {
				System.err.println("Error: Failed to read input from command line.");
				System.exit(1);
			}
		}
	}

}
