import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.Ellipse2D;
import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class TransportSim{
	public static void main(String[] args){
		World world = new World(50, 30, 10);
		
		GameWindow gameWindow = new GameWindow(world);
	}
}
