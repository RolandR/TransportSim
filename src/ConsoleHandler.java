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
		print("│   Params: <c[o]nnection, c[i]ty, citize[n], [h]ouse, compan[y]> <ID>      │");
		print("├────────┬────────┬─────────────────────────────────────────────────────────┤");
		print("│list    │ l      │ Lists all items of a type                               │");
		print("├────────┴────────┴─────────────────────────────────────────────────────────┤");
		print("│   Params: <c[o]nnections, c[i]ties, citize[n]s, [h]ouses, companies [y]>  │");
		print("├────────┬────────┬─────────────────────────────────────────────────────────┤");
		print("│buy     │ b      │ Buy something                                           │");
		print("├────────┴────────┴─────────────────────────────────────────────────────────┤");
		print("│   Params: <c[o]nnection> <ID>                                             │");
		print("├────────┬────────┬─────────────────────────────────────────────────────────┤");
		print("│upgrade │ u      │ Upgrade something                                       │");
		print("├────────┴────────┴─────────────────────────────────────────────────────────┤");
		print("│   Params: <c[o]nnection> <ID>                                             │");
		print("└───────────────────────────────────────────────────────────────────────────┘");
		
	}
	
	public static void print(String outString){
		System.out.println(outString);
	}
	
	public static void print(){
		System.out.println();
	}
	
	public void run(){
		while(true){
			System.out.print("\n>");
			try {
				String inputLine = bufferedReader.readLine();
				inputLine = inputLine.trim();
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
					
					case "examine":
					case "x":
						String examineType;
						int examineId = 0;
						
						try{
							examineType = userInput[1];
						} catch (java.lang.ArrayIndexOutOfBoundsException e){
							do{
								print("Please enter one of the following types:");
								print("c[o]nnection, c[i]ty, citize[n], [h]ouse, compan[y]");
								System.out.print(">");
								examineType = bufferedReader.readLine().split(" ")[0]; // TODO: Check if string is empty/whitespace first
							} while(examineType == null || examineType.equals(""));
						}
						
						
						boolean idIsValid = false;
						
						try{
							examineId = Integer.parseInt(userInput[2]);
							idIsValid = true;
						} catch (java.lang.ArrayIndexOutOfBoundsException e){
							idIsValid = false;
						} catch (java.lang.NumberFormatException e){
							idIsValid = false;
						}
						
						while(!idIsValid){							
							try{
								print("Please enter a valid ID.");
								System.out.print(">");
								examineId = Integer.parseInt(bufferedReader.readLine());
								idIsValid = true;
							} catch (java.lang.NumberFormatException e){
								idIsValid = false;
							}
						}
						
						switch(examineType){
							case "connection":
							case "o":
								Connection examinedConnection = Connection.getById(examineId);
								if(examinedConnection == null){
									print("Could not find connection "+ examineId +".");
								} else {
									print("Connection "+examinedConnection.getId()+":"); 
									print("  Connects city "+examinedConnection.getEndpointA().getId()+" to city "+examinedConnection.getEndpointB().getId());
									print("  Distance:      "+String.format("%1$9s", Math.round(examinedConnection.getDistance()*10)/10.0));
									print("  Speed:         "+String.format("%1$9s", Math.round(examinedConnection.getSpeed()*10)/10.0));
									print("  Duration to cross: "+String.format("%1$5s", Math.round(examinedConnection.getCost()*10)/10.0));
								}
							break;
							case "city":
							case "i":
								City examinedCity = City.getById(examineId);
								if(examinedCity == null){
									print("Could not find city "+ examineId +".");
								} else {
									print("City "+examinedCity.getId()+":"); 
									print("  Coordinates:    "+String.format("%1$7s", examinedCity.getXPos()+"/"+examinedCity.getYPos()));
									print("  Population:        "+String.format("%1$4s", examinedCity.getInhabitants().size()));
									print("  Houses:             "+String.format("%1$3s", examinedCity.getHouses().size()));
									for(House currentHouse: examinedCity.getHouses()){
										print("    House"+String.format("%1$3s", currentHouse.getId())+","+String.format("%1$3s", currentHouse.getTenantCount())+" tenants");
									}
									print("  Housing Capacity:  "+String.format("%1$4s", examinedCity.getHousingCapacity()));
									print("  Average Rent:     "+String.format("%1$5s", Util.formatMoney(examinedCity.getAverageRent())));
									print("  Companies:          "+String.format("%1$3s", examinedCity.getCompanies().size()));
									print("  Jobs:              "+String.format("%1$4s", examinedCity.getJobCount()));
									print("  Connections:       "+String.format("%1$4s", examinedCity.getConnections().size()));
									for(Connection currentConnection: examinedCity.getConnections()){
										print("    Connection"+String.format("%1$3s", currentConnection.getId())
										     +" to city"+String.format("%1$3s", currentConnection.getOtherEndpoint(examinedCity).getId()));
									}
								}
							break;
							case "citizen":
							case "n":
								
								Citizen examinedCitizen = Citizen.getById(examineId);
								if(examinedCitizen == null){
									print("Could not find citizen "+ examineId +".");
								} else {
									print("Citizen "+examinedCitizen.getId()+":"); 
									
									City CitizensCity = examinedCitizen.getCity();
									if(CitizensCity != null){
										print("  Lives in city "+CitizensCity.getId());
									} else {
										print("  Doesn't live in a city.");
									}
									
									House citizensHouse = examinedCitizen.getHouse();
									if(citizensHouse != null){
										print("  Lives in house "+citizensHouse.getId());
									} else {
										print("  Is homeless.");
									}
									
									Job citizensJob = examinedCitizen.getJob();
									if(citizensJob != null){
										print("  Has job "+citizensJob.getId());
										print("  Works in city "+citizensJob.getCity().getId());
										print("  Salary: "+Util.formatMoney(citizensJob.getSalary()));
									} else {
										print("  Is currently unemployed.");
									}
									
									double education = examinedCitizen.getEducation();
									if(education < 0.2){
										print("  Has poor education.");
									} else if(education < 0.4){
										print("  Has below average education.");
									} else if(education < 0.6){
										print("  Has average education.");
									} else if(education < 0.8){
										print("  Has good education.");
									} else {
										print("  Has brilliant education.");
									}
								}
																
							break;
							case "house":
							case "h":
								House examinedHouse = House.getById(examineId);
								if(examinedHouse == null){
									print("Could not find house "+ examineId +".");
								} else {
									print("House "+examinedHouse.getId()+":"); 
									print("  Located in city "+String.format("%1$4s", examinedHouse.getCity().getId()));
									print("  Housing Capacity:"+String.format("%1$3s", examinedHouse.getHousingCapacity()));
									print("  Tenants:       "+String.format("%1$5s", examinedHouse.getTenantCount()));
									print("  "+Math.round(100*examinedHouse.getTenantCount()/examinedHouse.getHousingCapacity())
									     +"% Occupied ("+(examinedHouse.getHousingCapacity()-examinedHouse.getTenantCount())+" free)");
									print("  Rent:        "+String.format("%1$7s", Util.formatMoney(examinedHouse.getRent()))); 
									print("  Living here:");
									for(Citizen currentCitizen: examinedHouse.getTenants()){
										print("    Citizen"+String.format("%1$5s", currentCitizen.getId()));
									}
								}
							break;
							case "company":
							case "y":
								Company examinedCompany = Company.getById(examineId);
								if(examinedCompany == null){
									print("Could not find company "+ examineId +".");
								} else {
									print("Company "+examinedCompany.getId()+":"); 
									print("  Located in city"+String.format("%1$7s", examinedCompany.getCity().getId()));
									print("  Average salary:"+String.format("%1$7s", Util.formatMoney(examinedCompany.getAverageSalary()))); 
									print("  Jobs:       "+String.format("%1$10s", examinedCompany.getJobCount()));
									for(Job currentJob: examinedCompany.getJobs()){
										print("    Job"+String.format("%1$5s", currentJob.getId())+", Salary:"+String.format("%1$6s", Util.formatMoney(currentJob.getSalary())));
									}
								}
							break;
							default:
								print("Undefined item: "+examineType);
							break;
						}
					break;
					
					case "list":
					case "l":
						String listType;
						try{
							listType = userInput[1];
						} catch (java.lang.ArrayIndexOutOfBoundsException e){
							do{
								print("Please enter one of the following types:");
								print("c[o]nnections, c[i]ties, citize[n]s, [h]ouses, companies [y]");
								System.out.print(">");
								listType = bufferedReader.readLine().split(" ")[0]; // TODO: Check if string is empty/whitespace first
							} while(listType == null || listType.equals(""));
						}
						
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
							case "citizens":
							case "n":
								print("World Population: "+Integer.toString(Citizen.totalCitizenCount));
								print();
								
								int cols = 10;
								int charsPerCol = 6;
								
								for(City currentCity: City.allCities){
									int currrentCityPopulation = currentCity.getInhabitants().size();
									print();
									print("Citizens in city "+currentCity.getId()+" (Population "+currrentCityPopulation+"):");
									print();
									
									for(int y = 0; y < currrentCityPopulation; y += cols){
										String row = "";
										for(int x = 0; x < cols; x++){
											if((x + y) < currrentCityPopulation){
												Citizen currentCitizen = currentCity.getInhabitants().get(y + x);
												row += String.format("%1$"+charsPerCol+"s", currentCitizen.getId()); 
											}
										}
										print(row);
									}
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
							case "companies":
							case "y":
								print();
								print("Total companies: "+Integer.toString(Company.getTotalCompanyCount()));
								print("Total jobs:      "+Integer.toString(Job.getTotalJobCount()));
								
								for(City currentCity: City.allCities){
									print("Companies in city "+currentCity.getId()+":");
									for(Company currentCompany: currentCity.getCompanies()){
										String row = "\tCompany "+String.format("%1$3s", currentCompany.getId()); 
										row += " - Jobs: "+String.format("%1$3s", currentCompany.getJobCount()); 
										row += " - Average Salary: "+String.format("%1$5s", Util.formatMoney(currentCompany.getAverageSalary())); 
										print(row);
									}
								}
							break;
							default:
								print("Undefined item: "+listType);
							break;
						}
					break;
					
					case "buy":
					case "b":
						String buyType;
						int buyId = 0;
						
						try{
							buyType = userInput[1];
						} catch (java.lang.ArrayIndexOutOfBoundsException e){
							do{
								print("Please enter one of the following types:");
								print("c[o]nnection");
								System.out.print(">");
								buyType = bufferedReader.readLine().split(" ")[0]; // TODO: Check if string is empty/whitespace first
							} while(buyType == null || buyType.equals(""));
						}
						
						
						boolean buyIdIsValid = false;
						
						try{
							buyId = Integer.parseInt(userInput[2]);
							buyIdIsValid = true;
						} catch (java.lang.ArrayIndexOutOfBoundsException e){
							buyIdIsValid = false;
						} catch (java.lang.NumberFormatException e){
							buyIdIsValid = false;
						}
						
						while(!buyIdIsValid){							
							try{
								print("Please enter a valid ID.");
								System.out.print(">");
								buyId = Integer.parseInt(bufferedReader.readLine());
								buyIdIsValid = true;
							} catch (java.lang.NumberFormatException e){
								buyIdIsValid = false;
							}
						}
						
						switch(buyType){
							case "connection":
							case "o":
								Connection connectionToBuy = Connection.getById(buyId);
								if(connectionToBuy == null){
									print("Could not find connection "+ buyId +".");
								} else {
									int success = TransportSim.getPlayer().checkBuyingConnection(connectionToBuy);
									switch(success){
										case 0:			
											print("Buying connection "
												+connectionToBuy.getId()+" would cost you CHF "
												+Util.formatMoney(connectionToBuy.getPrice())+". Do you want to proceed? (y/n)");
												
											System.out.print(">");
											
											String confirmPurchase = bufferedReader.readLine();
											confirmPurchase = confirmPurchase.trim();
											
											while(!confirmPurchase.equals("y") && !confirmPurchase.equals("n")){
												print("Please enter \"y\" or \"n\".");
												System.out.print(">");
												confirmPurchase = bufferedReader.readLine();
												confirmPurchase = confirmPurchase.trim();
											}
											
											if(confirmPurchase.equals("y")){
												TransportSim.getPlayer().buyConnection(connectionToBuy);
												print("Connection "+connectionToBuy.getId()+" is now yours!");
											} else {
												print("Cancelled.");
											}
										break;
										case 1:
											print("You own connection "+connectionToBuy.getId()+" already.");
										break;
										case 2:
											print("You can't afford to buy connection "+connectionToBuy.getId()+".");
										break;
										default:
										break;
									}
								}
							break;
							
							case "city":
							case "i":
							case "house":
							case "h":
							case "company":
							case "y":
								print("You can not buy this item: "+buyType);
							break;
							
							case "citizen":
							case "n":
								print("You want to buy a citizen? Do I look like a slave trader?");
							break;
							
							default:
								print("Undefined item: "+buyType);
							break;
						}
					break;
					
					case "upgrade":
					case "u":
						String upgradeType;
						int upgradeId = 0;
						
						try{
							upgradeType = userInput[1];
						} catch (java.lang.ArrayIndexOutOfBoundsException e){
							do{
								print("Please enter one of the following types:");
								print("c[o]nnection");
								System.out.print(">");
								upgradeType = bufferedReader.readLine().split(" ")[0]; // TODO: Check if string is empty/whitespace first
							} while(upgradeType == null || upgradeType.equals(""));
						}
						
						
						boolean upgradeIdIsValid = false;
						
						try{
							upgradeId = Integer.parseInt(userInput[2]);
							upgradeIdIsValid = true;
						} catch (java.lang.ArrayIndexOutOfBoundsException e){
							upgradeIdIsValid = false;
						} catch (java.lang.NumberFormatException e){
							upgradeIdIsValid = false;
						}
						
						while(!upgradeIdIsValid){							
							try{
								print("Please enter a valid ID.");
								System.out.print(">");
								upgradeId = Integer.parseInt(bufferedReader.readLine());
								upgradeIdIsValid = true;
							} catch (java.lang.NumberFormatException e){
								upgradeIdIsValid = false;
							}
						}
						
						switch(upgradeType){
							case "connection":
							case "o":
								Connection connectionToUpgrade = Connection.getById(upgradeId);
								if(connectionToUpgrade == null){
									print("Could not find connection "+ upgradeId +".");
								} else {
									print("The connection currently has a speed of "+Math.round(connectionToUpgrade.getSpeed()*100)/100.0+".");
									print("What speed would you like to upgrade it to?");
									
									System.out.print(">");
									
									String upgradeSpeedInput = bufferedReader.readLine();
									upgradeSpeedInput = upgradeSpeedInput.trim();
									
									boolean upgradeSpeedIsValid = false;
									double upgradeSpeed = 0.0;
									
									try{
										upgradeSpeed = Double.parseDouble(upgradeSpeedInput);
										if(upgradeSpeed > 0 && upgradeSpeed <= 1){
											upgradeSpeedIsValid = true;
										} else {
											upgradeSpeedIsValid = false;
										}
									} catch (java.lang.NumberFormatException e){
										upgradeSpeedIsValid = false;
									}
									
									while(!upgradeSpeedIsValid){
										print("Please enter a number larger than 0, maximum 1.");
										System.out.print(">");
										upgradeSpeedInput = bufferedReader.readLine();
										upgradeSpeedInput = upgradeSpeedInput.trim();
										
										try{
											upgradeSpeed = Double.parseDouble(upgradeSpeedInput);
											if(upgradeSpeed > 0 && upgradeSpeed <= 1){
												upgradeSpeedIsValid = true;
											} else {
												upgradeSpeedIsValid = false;
											}
										} catch (java.lang.NumberFormatException e){
											upgradeSpeedIsValid = false;
										}
									}
									
									int success = TransportSim.getPlayer().checkUpgradingConnection(connectionToUpgrade, upgradeSpeed);
									double upgradeCost = connectionToUpgrade.getUpgradeCost(upgradeSpeed);
									switch(success){
										case 0:
											if(upgradeCost >= 0){
												print("Upgrading connection "
													+connectionToUpgrade.getId()+" to a speed of "+upgradeSpeed+" would cost you CHF "
													+Util.formatMoney(upgradeCost)+".");
											} else {
												print("Connection "+connectionToUpgrade.getId()+" is currently faster than "+upgradeSpeed+".");
												print("Downgrading it would give you CHF "+Util.formatMoney(Math.abs(upgradeCost))+".");
											}
											print("Do you want to proceed? (y/n)");
												
											System.out.print(">");
											
											String confirmUpgrade = bufferedReader.readLine();
											confirmUpgrade = confirmUpgrade.trim();
											
											while(!confirmUpgrade.equals("y") && !confirmUpgrade.equals("n")){
												print("Please enter \"y\" or \"n\".");
												System.out.print(">");
												confirmUpgrade = bufferedReader.readLine();
												confirmUpgrade = confirmUpgrade.trim();
											}
											
											if(confirmUpgrade.equals("y")){
												TransportSim.getPlayer().upgradeConnection(connectionToUpgrade, upgradeSpeed);
												print("Successfully upgraded connection "+connectionToUpgrade.getId()+" to speed "+upgradeSpeed);
											} else {
												print("Cancelled.");
											}
										break;
										case 2:
											print("You can't afford to upgrade connection "+connectionToUpgrade.getId()+"to a speed of "+upgradeSpeed+".");
										break;
										default:
										break;
									}
									
								}
							break;
							
							case "city":
							case "i":
							case "house":
							case "h":
							case "company":
							case "y":
							case "citizen":
							case "n":
								print("You can not buy this item: "+upgradeType);
							break;
							
							default:
								print("Undefined item: "+upgradeType);
							break;
						}
					break;
					
					case "":
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
