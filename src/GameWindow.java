import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.Insets;
import java.awt.geom.*;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class GameWindow extends JFrame {
	private JComponent pane = new GameDisplay();
	
	private double worldScale = 10;
	private int xWindowPadding = 10;
	private int yWindowPadding = 10;
	private int xWorldPadding = 10 + xWindowPadding;
	private int yWorldPadding = 10 + yWindowPadding;
	
	private World world;
	
	public GameWindow(World nWorld) {
		world = nWorld;
		Insets i = getJFrameInsets();
		setSize(500, 500);
		setLocale(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Transport Sim");
		getContentPane().setBackground(Color.BLACK);
		add(pane);
		setVisible(true);
		repaint();
	}
	
	public static Insets getJFrameInsets() {
		JFrame temp = new JFrame();
		temp.pack();
		return temp.getInsets();
	}
	
	public void refresh(){
		repaint();
	}
	
	public class GameDisplay extends JComponent {
		
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D) g;
			
			g2d.setRenderingHint(
			RenderingHints.KEY_TEXT_ANTIALIASING,
			RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			
			// Draw world borders
			int mapWidth = (int)(world.getWidth() * worldScale + 2*xWorldPadding - xWindowPadding);
			int mapHeight = (int)(world.getHeight() * worldScale + 2*yWorldPadding - yWindowPadding);
			
			g2d.drawRect(xWindowPadding, yWindowPadding, mapWidth, mapHeight);
			
			// Draw Connections
			for(Connection currentConnection: Connection.getAllConnections()){
							
				int startX = (int)(currentConnection.getEndpointA().getXPos() * worldScale) + xWorldPadding;
				int startY = (int)(currentConnection.getEndpointA().getYPos() * worldScale) + yWorldPadding;
				int endX = (int)(currentConnection.getEndpointB().getXPos() * worldScale) + xWorldPadding;
				int endY = (int)(currentConnection.getEndpointB().getYPos() * worldScale) + yWorldPadding;
				
				int colYellow = 255 - (int)((currentConnection.getSpeed() - 0.25)*2 * 255);
				int colBlue = (int)((currentConnection.getSpeed() - 0.25)*2 * 255);
				
				g2d.setColor(new Color(colYellow, colYellow, colBlue));
				
				g2d.drawLine(startX, startY, endX, endY);
			}
			
			// Draw Citizens
			for(Citizen currentCitizen: Citizen.getAllCitizens()){
				if(currentCitizen.isCurrentlyTravelling() && currentCitizen.getConnectionUsed().getDistance() != 0){
					double targetX = currentCitizen.getCurrentDestination().getXPos();
					double targetY = currentCitizen.getCurrentDestination().getYPos();
					double startX = currentCitizen.getCurrentPosition().getXPos();
					double startY = currentCitizen.getCurrentPosition().getYPos();
					
					double travelProgress = (currentCitizen.getDistanceTravelled() / currentCitizen.getConnectionUsed().getDistance());
					
				//	if(currentCitizen.getId() == 0){
				//		ConsoleHandler.print(travelProgress+"");
				//	}
					
					double currentX = startX + (targetX - startX) * travelProgress;
					double currentY = startY + (targetY - startY) * travelProgress;
					
					currentX *= worldScale;
					currentY *= worldScale;
					
					currentX += xWorldPadding;
					currentY += yWorldPadding;
					
					int dotRadius = 4;
					if(currentCitizen.getCity() == currentCitizen.getCurrentDestination()){
						g2d.setColor(Color.GREEN);
					} else {
						g2d.setColor(Color.WHITE);
					}
				
					g2d.fillOval((int)currentX - dotRadius, (int)currentY - dotRadius, dotRadius * 2, dotRadius * 2);
				
					g2d.setColor(Color.BLACK);
				
					g2d.drawOval((int)currentX - dotRadius, (int)currentY - dotRadius, dotRadius * 2, dotRadius * 2);
					
				} else {
					//Citizen sits in a city and doesn't need to be rendered.
				}
			}
			
			// Draw Cities
			for(City currentCity: City.getAllCities()){
				int cityX = (int)(currentCity.getXPos() * worldScale) + xWorldPadding;
				int cityY = (int)(currentCity.getYPos() * worldScale) + yWorldPadding;
				int dotRadius = 5;
				dotRadius += (int)(currentCity.getHousingCapacity() * 0.1);
				
				g2d.setColor(Color.BLACK);
				
				g2d.fillOval(cityX - dotRadius, cityY - dotRadius, dotRadius * 2, dotRadius * 2);
				
				g2d.setColor(Color.GREEN);
				
				g2d.drawOval(cityX - dotRadius, cityY - dotRadius, dotRadius * 2, dotRadius * 2);
				
				g2d.setColor(Color.WHITE);
				
				g.drawString(Integer.toString(currentCity.getId()), cityX - 4, cityY + 5);
			}
			
			// Print Information
			int housingCapacity = House.getTotalHousingCapacity();
			int jobCount = Job.getTotalJobCount();
			int cityCount = City.getTotalCityCount();
			int connectionCount = Connection.getTotalConnectionCount();
			int citizenCount = Citizen.getTotalCitizenCount();
			int unemployed = citizenCount - Job.getTotalEmployees();
			int unemploymentRate = (int)(100 * unemployed / citizenCount);
			double currentTime = World.getTime();
			
			int tabulatorWidth = 190;
			int lineHeight = 18;
			int spaceBetweenMapAndInfo = 50;
			
			g2d.setColor(Color.WHITE);
			
			g2d.setFont(new Font("Courier", Font.PLAIN, 15)); 
			
			
			g2d.drawString("Time: "+Util.formatTime(currentTime), xWindowPadding, yWindowPadding + mapHeight + 20);
			
			
			g2d.drawString("City Count:", xWindowPadding, yWindowPadding + spaceBetweenMapAndInfo + mapHeight + lineHeight * 1);
			g2d.drawString(Integer.toString(cityCount), xWindowPadding + tabulatorWidth, yWindowPadding + mapHeight + spaceBetweenMapAndInfo + lineHeight * 1);
			
			g2d.drawString("Connection Count:", xWindowPadding, yWindowPadding + spaceBetweenMapAndInfo + mapHeight +  lineHeight * 2);
			g2d.drawString(Integer.toString(connectionCount), xWindowPadding + tabulatorWidth, yWindowPadding + mapHeight + spaceBetweenMapAndInfo + lineHeight * 2);
			
			g2d.drawString("Housing Capacity:", xWindowPadding, yWindowPadding + spaceBetweenMapAndInfo + mapHeight +  lineHeight * 3);
			g2d.drawString(Integer.toString(housingCapacity), xWindowPadding + tabulatorWidth, yWindowPadding + mapHeight + spaceBetweenMapAndInfo + lineHeight * 3);
			
			g2d.drawString("Job Count:", xWindowPadding, yWindowPadding + spaceBetweenMapAndInfo + mapHeight +  lineHeight * 4);
			g2d.drawString(Integer.toString(jobCount), xWindowPadding + tabulatorWidth, yWindowPadding + mapHeight + spaceBetweenMapAndInfo + lineHeight * 4);
			
			g2d.drawString("Population:", xWindowPadding, yWindowPadding + spaceBetweenMapAndInfo + mapHeight +  lineHeight * 5);
			g2d.drawString(Integer.toString(citizenCount), xWindowPadding + tabulatorWidth, yWindowPadding + mapHeight + spaceBetweenMapAndInfo + lineHeight * 5);
			
			g2d.drawString("Unemployed:", xWindowPadding, yWindowPadding + spaceBetweenMapAndInfo + mapHeight +  lineHeight * 6);
			g2d.drawString(Integer.toString(unemployed), xWindowPadding + tabulatorWidth, yWindowPadding + mapHeight + spaceBetweenMapAndInfo + lineHeight * 6);
			
			g2d.drawString("Unemployment Rate:", xWindowPadding, yWindowPadding + spaceBetweenMapAndInfo + mapHeight +  lineHeight * 7);
			g2d.drawString(Integer.toString(unemploymentRate) + "%", xWindowPadding + tabulatorWidth, yWindowPadding + mapHeight + spaceBetweenMapAndInfo + lineHeight * 7);
			
			g2d.drawString("Citizens travelling:", xWindowPadding, yWindowPadding + spaceBetweenMapAndInfo + mapHeight +  lineHeight * 8);
			g2d.drawString(Integer.toString(Citizen.getCurrentlyTravellingCount()), xWindowPadding + tabulatorWidth, yWindowPadding + mapHeight + spaceBetweenMapAndInfo + lineHeight * 8);
			
		}
	}
	
}
