import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Programme extends JFrame  {
	
	private static final int WIDTH=550, HEIGHT=200;
	private static Point SG=null, ID=null;
	private JPanel pan=new JPanel();
	private JPanel okPan=new JPanel();
	private JPanel[] underPan=new JPanel[2];
	private JPanel pausePan=new JPanel();
	private static JLabel instruction=new JLabel();

	private static JTextField t=new JTextField();
	private JButton ok=new JButton();
	private JButton pause=new JButton();
	private int etape=0;
	private boolean stop=false, menu=false;
	private int largeurVie=0, hauteurVie=0;
	private static int MAX=0;
	private static double valTP=0;
	  public Programme() throws AWTException{
		 
		  this.setSize(WIDTH, HEIGHT);
          this.setTitle("BotRealm");
          this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	            
          this.setLocationRelativeTo(null);
          this.setResizable(false);
          pan.setLayout(new GridLayout(3,1));
          okPan.setLayout(new GridLayout(1,2));
          this.setContentPane(pan);  
          this.setVisible(true);
          instruction.setText("Hello");
          t.setPreferredSize(new Dimension(100,25));
          ok.setText("OK");
          ok.setMaximumSize(new Dimension(50,50));
          ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				etape++;
			}				
		});
          pause.setText("PAUSE");
          pause.setMaximumSize(new Dimension(50,50));
          pause.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				stop=!stop;
				if(stop==true)instruction.setText("En pause");  
				else instruction.setText("En marche");
			}				
		});
          underPan[0]=new JPanel();
          underPan[1]=new JPanel();
          underPan[0].add(ok);
          underPan[1].add(pause);
          pan.add(instruction);
          okPan.add(underPan[0], BorderLayout.EAST);
          okPan.add(underPan[1], BorderLayout.WEST);
          pausePan.add(t);
          pan.add(pausePan);
          pan.add(okPan);
          
          pan.setBackground(Color.lightGray);
         
		 
	  }
	  
	  public void attendre(long timer){
			try {
	    		Thread.sleep(timer);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
	  }
	  public void presentation(){
	    
		  	
		  	
		  	
	    	while(etape==0)	instruction.setText("Une fois Realm of the mad god a l'ecran, cliquez sur OK pour commencer");
	    	
	    	Point lastLocation=null;
	    	int stabilisation=0;    	
	    	while(etape==1){
	    			 instruction.setText("Placer votre sourie sur le coin superieur gauche de votre barre de vie pendant 5s");	    			
	    			 PointerInfo pointer = MouseInfo.getPointerInfo();
	    			 Point location = pointer.getLocation(); 
	    			 if(lastLocation != null && (lastLocation.x==location.x && lastLocation.y==location.y )){
	    				 System.out.println("stabilisation en cours ..");
	    				 stabilisation++;
	    			 }
	    			 else stabilisation=0;
	    			 
	    			 
	    			 lastLocation=(Point) location.clone();
	    			 if(stabilisation>=50000) etape++;
	    		 
	    	}
	    	SG=(Point) lastLocation.clone();
	    	instruction.setText("Coin supérieure gauche detecté, cliquer sur OK pour commencer la deuxieme detection");	  
	    	while(etape==2)attendre(1);
	    	
	    	lastLocation=null;
	    	stabilisation=0; 
	    	while(etape==3)
	    	{
	    		instruction.setText("Placer votre sourie sur le coin inferieur droit de votre barre de vie 5s");
	    		PointerInfo pointer = MouseInfo.getPointerInfo();
	    		Point location = pointer.getLocation(); 
	    		if(lastLocation != null && (lastLocation.x==location.x && lastLocation.y==location.y )){
	    			System.out.println("stabilisation en cours ..");
	    			stabilisation++;
	    		}
	    		else stabilisation=0;
   			 
   			 
   			 lastLocation=(Point) location.clone();
   			 if(stabilisation>=50000) etape++;
	    	}
	    	ID=(Point) lastLocation.clone();
	    	instruction.setText("Coin inferieure droit detecté !");  
	    	attendre(2000);
	    	
	    	while(etape==4){
	    		instruction.setText("Entrez le pourcentage de PV pour etre TP ! Cliquez sur OK une fois fini"); 
	    		
	    	}
	    	valTP=(((double)(Integer.parseInt(t.getText())))/100.0);
	    	instruction.setText("En marche");  
	    	largeurVie=ID.x-SG.x;
	    	hauteurVie=ID.y-SG.y;
	    	System.out.println("SG " + SG + " ID " + ID + " largeur vie " + largeurVie + " hauteurVie " + hauteurVie);
	    	
	    	
	     
	    }
	  
	  public void cliq(int x, int y){
		  try {
	    		
				Robot robot=new Robot();
				robot.mouseMove(x, y);
			} catch (AWTException e) {
				e.printStackTrace();
			}
	  }
	  
	  public void clavier(int c){
		  try {
	    		
				Robot robot=new Robot();
				robot.keyPress(c);
				robot.delay(500);
				robot.keyRelease(c);
				robot.delay(5000);
			} catch (AWTException e) {
				e.printStackTrace();
			}
	  }
	  public static void main(String[] args) throws AWTException {
		 Programme p=new Programme();
		ColorFinder c=new ColorFinder();
		boolean modif = false;
		int val=0;
		double valModif=0;
		
		 p.presentation();
		 MAX=c.getPixels(SG.x, SG.y, ID.x, ID.y, ColorFinder.C, ColorFinder.MARGIN, ColorFinder.STEP).size();
		 while(true){
			 try{
			 	PointerInfo pointer = MouseInfo.getPointerInfo();
	    		Point location = pointer.getLocation(); 
	    		
	    		
	    		if(Double.parseDouble(t.getText())!=valTP*100) modif=true;
	    		
	    		
	    		if(modif){
	    			System.out.println(Double.parseDouble(t.getText()));
	    			System.out.println(valTP);
	    			val=p.etape;
	    			instruction.setText("En modification ... Cliquez sur OK pour confirmer la valeur")	;
	    			while(val==p.etape){
		    			if((valModif=(Double.parseDouble(t.getText())))>0.0 && valModif<100 ){
		    				valTP=valModif/100;
		    				modif=false;
		    			}
	    			}
	    		}
	    		
				if(location.x>SG.x) p.menu=true;
				else p.menu=false;
				
				if(!p.stop && !p.menu && !modif){
				
					valTP=(((double)(Integer.parseInt(t.getText())))/100.0);
					instruction.setText("En marche ...   " + "	Vie actuellement : " + c.getPixels(SG.x, SG.y, ID.x, ID.y, ColorFinder.C, ColorFinder.MARGIN, ColorFinder.STEP).size() + " pixels  (" + (c.getPixels(SG.x, SG.y, ID.x, ID.y, ColorFinder.C, ColorFinder.MARGIN, ColorFinder.STEP).size())*100/MAX +     "%) TP fixée a " + valTP*MAX + " pixels (" + t.getText() + " %)")	; 
					System.out.println(valTP*MAX);
					System.out.println(c.getPixels(SG.x, SG.y, ID.x, ID.y, ColorFinder.C, ColorFinder.MARGIN, ColorFinder.STEP).size());
					if(c.getPixels(SG.x, SG.y, ID.x, ID.y, ColorFinder.C, ColorFinder.MARGIN, ColorFinder.STEP).size()<valTP*MAX && c.getPixels(SG.x, SG.y, ID.x, ID.y, ColorFinder.C, ColorFinder.MARGIN, ColorFinder.STEP).size()!=0 ){
						p.pan.setBackground(Color.RED);
						p.clavier(70);
					}
					else p.pan.setBackground(Color.lightGray);
					
				}
				p.attendre(200);
			 }
			catch(Exception e){
				instruction.setText("ERREUR de pourcentage")	;
			}
		 }
	    }
}
