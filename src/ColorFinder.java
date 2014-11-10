

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.sql.Time;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class ColorFinder {

	public static final int MARGIN=125, STEP=1;
	public static final Color C=Color.RED;
    private Robot robot;
   
    private Toolkit toolkit;
    private static JFrame j;
    private BufferedImage capture;
    private int screenWidth, screenHeight, hitMouse=0;
    private final static int WIDTH=1600;
	private final static int HEIGHT=900;
    private Rectangle zoneCapture;
    private Image im;
    private Graphics graph;
  
    public ColorFinder() throws AWTException {
    	
        robot = new Robot();
        toolkit = Toolkit.getDefaultToolkit();       
       
         
        Dimension screenSize = toolkit.getScreenSize();     
        screenWidth = screenSize.width;
        screenHeight= screenSize.height;
        zoneCapture = new Rectangle(screenSize);
        capture  = robot.createScreenCapture(zoneCapture);
       
    }


    public Set<Point> getPixels(Color color, double margin, int step) {        
        int x, y;
        Color pixelColor;
        Set<Point> pixels = new HashSet<Point>();
        double colorDistance;
        capture  = robot.createScreenCapture(zoneCapture);
        for (x = 0; x < screenWidth; x += step) {

            for (y = 0; y < screenHeight; y += step) {

                pixelColor = new Color(capture.getRGB(x, y));
                colorDistance = getDistance(color, pixelColor);

                if (colorDistance <= margin) {
                    pixels.add(new Point(x, y));
                }

            }

        }

        return pixels;

    }

    public Set<Point> getPixels(int xDeb, int yDeb, int xFin, int yFin, Color color, double margin, int step) {        
        int x, y;
        Color pixelColor;
        Set<Point> pixels = new HashSet<Point>();
        double colorDistance;
        capture  = robot.createScreenCapture(zoneCapture);
        for (x = xDeb; x < xFin; x += step) {

            for (y = yDeb; y < yFin; y += step) {

                pixelColor = new Color(capture.getRGB(x, y));
                colorDistance = getDistance(color, pixelColor);

                if (colorDistance <= margin) {
                    pixels.add(new Point(x, y));
                }

            }

        }

        return pixels;

    }
    public void paintPixel(){
    	int x, y;
    	 Color pixelColor;

         Set<Point> pixels = new HashSet<Point>();
         double colorDistance;
    	  for (x = 0; x < screenWidth; x +=STEP) {

              for (y = 0; y < screenHeight; y +=STEP) {

                  pixelColor = new Color(capture.getRGB(x, y));
                  colorDistance = getDistance(C, pixelColor);
                  if (colorDistance <= MARGIN) {
                	  capture.setRGB(x, y, 0);
                  }
              }
          }
    }
    public static double getDistance(Color color1, Color color2) {

        int r1 = color1.getRed();
        int r2 = color2.getRed();
        int g1 = color1.getGreen();
        int g2 = color1.getGreen();
        int b1 = color1.getBlue();
        int b2 = color2.getBlue();
        int rDistance = Math.abs(r1 - r2);
        int gDistance = Math.abs(g1 - g2);
        int bDistance = Math.abs(b1 - b2);   

        return (rDistance+gDistance+bDistance);

    }
    public void paintComponent(Graphics g){
    	graph=g;
		g.drawImage(capture, 0, 0, screenWidth, screenHeight,null);      
    }
    
    public void dessine(){    	
    	capture  = robot.createScreenCapture(zoneCapture);
    }
    
  
    public void afficherCouleurTouche(ColorFinder colorFinder){
    	 for(int i=0;i<100;i++){
         	colorFinder.dessine();
         }
         
         colorFinder.paintPixel();
         System.out.println("lol");
    }
    
   
  

  
        
      /*  */




}
