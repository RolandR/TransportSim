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
			for(int i = 0; i < Connection.allConnections.size(); i++){
				Connection currentConnection = Connection.allConnections.get(i);
				
				int startX = (int)(currentConnection.getEndpointA().getXPos() * worldScale) + xWorldPadding;
				int startY = (int)(currentConnection.getEndpointA().getYPos() * worldScale) + yWorldPadding;
				int endX = (int)(currentConnection.getEndpointB().getXPos() * worldScale) + xWorldPadding;
				int endY = (int)(currentConnection.getEndpointB().getYPos() * worldScale) + yWorldPadding;
				
				int colYellow = 255 - (int)((currentConnection.getSpeed() - 0.25)*2 * 255);
				int colBlue = (int)((currentConnection.getSpeed() - 0.25)*2 * 255);
				
				g2d.setColor(new Color(colYellow, colYellow, colBlue));
				
				g2d.drawLine(startX, startY, endX, endY);
			}
			
			// Draw Cities
			for(int i = 0; i < City.allCities.size(); i++){
				int cityX = (int)(City.allCities.get(i).getXPos() * worldScale) + xWorldPadding;
				int cityY = (int)(City.allCities.get(i).getYPos() * worldScale) + yWorldPadding;
				int dotRadius = 5;
				dotRadius += (int)(City.allCities.get(i).getHousingCapacity() * 0.1);
				
				g2d.setColor(Color.BLACK);
				
				g2d.fillOval(cityX - dotRadius, cityY - dotRadius, dotRadius * 2, dotRadius * 2);
				
				g2d.setColor(Color.GREEN);
				
				g2d.drawOval(cityX - dotRadius, cityY - dotRadius, dotRadius * 2, dotRadius * 2);
				
				g2d.setColor(Color.WHITE);
				
				g.drawString(Integer.toString(City.allCities.get(i).getId()), cityX - 4, cityY + 5);
			}
			
			// Print Information
			int housingCapacity = House.getTotalHousingCapacity();
			int jobCount = Job.getTotalJobCount();
			int cityCount = City.getTotalCityCount();
			int connectionCount = Connection.getTotalConnectionCount();
			int citizenCount = Citizen.getTotalCitizenCount();
		/*	double unemploymentRate = (double)(Math.round(
					((
						(double)housingCapacity - (double)jobCount
					)/(double)housingCapacity
				)*1000)/10
			);*/
			
			int tabulatorWidth = 180;
			int lineHeight = 18;
			
			g2d.setColor(Color.WHITE);
			
			g2d.setFont(new Font("Courier", Font.PLAIN, 15)); 
			
			g2d.drawString("City Count:", xWindowPadding, yWindowPadding + mapHeight + lineHeight * 1);
			g2d.drawString(Integer.toString(cityCount), xWindowPadding + tabulatorWidth, yWindowPadding + mapHeight +  lineHeight * 1);
			
			g2d.drawString("Connection Count:", xWindowPadding, yWindowPadding + mapHeight +  lineHeight * 2);
			g2d.drawString(Integer.toString(connectionCount), xWindowPadding + tabulatorWidth, yWindowPadding + mapHeight +  lineHeight * 2);
			
			g2d.drawString("Housing Capacity:", xWindowPadding, yWindowPadding + mapHeight +  lineHeight * 3);
			g2d.drawString(Integer.toString(housingCapacity), xWindowPadding + tabulatorWidth, yWindowPadding + mapHeight +  lineHeight * 3);
			
			g2d.drawString("Job Count:", xWindowPadding, yWindowPadding + mapHeight +  lineHeight * 4);
			g2d.drawString(Integer.toString(jobCount), xWindowPadding + tabulatorWidth, yWindowPadding + mapHeight +  lineHeight * 4);
			
			g2d.drawString("Population:", xWindowPadding, yWindowPadding + mapHeight +  lineHeight * 5);
			g2d.drawString(Integer.toString(citizenCount), xWindowPadding + tabulatorWidth, yWindowPadding + mapHeight +  lineHeight * 5);
			
			
		}
	}
	
}
