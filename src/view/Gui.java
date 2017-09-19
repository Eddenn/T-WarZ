package view;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import work.*;


public class Gui extends JFrame implements ActionListener,MouseListener,MouseMotionListener {

	private static final long serialVersionUID = 1L;
	final int sizeX = 9;	//Odd number
	final int sizeY = 9;	//Odd number
	
	static ArrayList<Unit> alAlliesUnits = new ArrayList<Unit>();
	static ArrayList<Unit> alEnemiesUnits = new ArrayList<Unit>();
	static Unit unitSelected = null ;
	private static DrawPanel mapPanel;
	
	private static JButton endOfTurnButton = new JButton("End of turn");
	private static JLabel unitSide = new JLabel("Side: ");
	private static JLabel unitName = new JLabel("Name: ");
	//private static JLabel unitHealth = new JLabel("Health: ",new ImageIcon("E:/Mes Documents/Workspace_Eclipse/T-WarZ/img/36px-Heart.png"), SwingConstants.LEFT);
	private static JLabel unitHealth = new JLabel("Health: ");
	private static JLabel unitMovePts = new JLabel("Move: ");
	private static JLabel unitAttackPts = new JLabel("Attack: ");
	private static JLabel unitAttackDamage = new JLabel("Damage: ");
	private static JLabel unitAttackRange = new JLabel("Range: ");
			
	public Gui() throws IOException{
		setTitle("T-WarZ");
        setSize(50*sizeX+50,50*sizeY+50);
        setLocation(0,0);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        endOfTurnButton.addActionListener(this);
        
        //Map creation
        alAlliesUnits.add(new Soldiers(0,50,50));
        alAlliesUnits.add(new Soldiers(0,200,250));
        alEnemiesUnits.add(new Soldiers(0,300,50));
        
        //Content
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(15,1) );
        infoPanel.add( new JLabel("         Information         ") );
        infoPanel.add( unitSide );
        infoPanel.add( unitName );
        infoPanel.add( unitHealth );
        infoPanel.add( unitMovePts );
        infoPanel.add( unitAttackPts );
        infoPanel.add( unitAttackDamage );
        infoPanel.add( unitAttackRange );
        infoPanel.add( new JLabel() );
        infoPanel.add( new JLabel() );
        infoPanel.add( new JLabel() );
        infoPanel.add( new JLabel() );
        infoPanel.add( new JLabel() );
        infoPanel.add( new JLabel() );
        infoPanel.add( endOfTurnButton );
        
        
		mapPanel = new DrawPanel(sizeX,sizeY);
		mapPanel.addMouseListener(this);
		mapPanel.addMouseMotionListener(this);
		
		add(infoPanel,BorderLayout.WEST);
		add(mapPanel,BorderLayout.CENTER);
		
		pack();
        setVisible(true);
	}
	
	//Return Unit at this position (x,y)
	//if there isn't Unit then return null
	public static Unit getUnit(int x,int y){
		for(Unit u : alAlliesUnits)
		{
			if( u.getX() == x && u.getY() == y)
				return u;
		}
		for(Unit u : alEnemiesUnits)
		{
			if( u.getX() == x && u.getY() == y)
				return u;
		}
		return null;
	}
	public static String getUnitSide(Unit u)
	{
		return (alEnemiesUnits.contains( u )?"Allies":"Enemies");
	}
	//Attack the Unit on this position (x,y)
	//Return true if the attack is successful
	//Remove Unit without Health
	public static boolean attackUnit(Unit u,int x,int y){
		int verticalRange = Math.abs(u.getX()-x);
		int horizontalRange = Math.abs(u.getY()-y);	
		if( Gui.getUnit( x , y ) != null  && verticalRange+horizontalRange <= u.getAttackRange()*50 && verticalRange+horizontalRange != 0 )
		{
			Gui.getUnit( x , y ).setHealthPts( Gui.getUnit( x , y ).getHealthPts()-u.getAttackDamage() );
			u.setAttackPts(u.getAttackPts()-1);
			if( Gui.getUnit( x , y ).getHealthPts() < 1 )
			{
				if( getUnitSide(u).equals("Enemies") )
					alEnemiesUnits.remove( Gui.getUnit( x , y ) );
				else
					alAlliesUnits.remove( Gui.getUnit( x , y ) );
			}
			Gui.unitSelected = null;
			mapPanel.repaint();
			return true;
		}
		return false;
	}
	//Move the Unit to this position (x,y) return true
	//if there is a unit on this position return false
	public static boolean moveUnit(Unit u,int x,int y){
		int verticalMove = Math.abs(u.getX()-x);
		int horizontalMove = Math.abs(u.getY()-y);		
		if( Gui.getUnit( x , y ) != null  || verticalMove+horizontalMove > u.getMovePts()*50 )
		{
			if( Gui.getUnit( x , y ) != null )
				if( Gui.unitSelected.getAttackPts() > 0 && 
					 (  (getUnitSide(u).equals("Enemies")&& getUnitSide(Gui.getUnit( x , y )).equals("Allies" ))
					  ||(getUnitSide(u).equals("Allies" )&& getUnitSide(Gui.getUnit( x , y )).equals("Enemies")) ) ) 
					attackUnit(Gui.unitSelected, x , y);
			Gui.unitSelected = null;
			mapPanel.setPosMouse( -1,-1 );
			return false;
		}
		u.setX(x);
		u.setY(y);
		u.setMovePts(0);
		Gui.unitSelected = null;
		mapPanel.setPosMouse( -1,-1 ); 
		return true;
	}
	//Mouse Event
	public void mousePressed(MouseEvent e) 	{}
	public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) 	{}
    public void mouseExited(MouseEvent e) 	{}
    public void mouseClicked(MouseEvent e) 	{		
    	int mouseX = (int)(e.getPoint().getX()/50)*50;
		int mouseY = (int)(e.getPoint().getY()/50)*50;
		mapPanel.setPosMouse( mouseX,mouseY ); 
		
		if( Gui.unitSelected != null )
				moveUnit( Gui.unitSelected , mouseX , mouseY );
		else Gui.unitSelected = Gui.getUnit( mouseX , mouseY );

		repaint();
	}
    //MouseMotion Event
	public void mouseDragged(MouseEvent e) 	{}
	public void mouseMoved(MouseEvent e) 	{
		int mouseX = (int)(e.getPoint().getX()/50)*50;
		int mouseY = (int)(e.getPoint().getY()/50)*50;
		if( Gui.getUnit(mouseX, mouseY) != null ){
			unitSide.setText(   "Side: "+getUnitSide( Gui.getUnit(mouseX, mouseY) ));
			unitName.setText(   "Name: "  + Gui.getUnit(mouseX, mouseY).getName()      );
			unitHealth.setText( "Health: "+ Gui.getUnit(mouseX, mouseY).getHealthPts() );
	        unitMovePts.setText( "Move: " + Gui.getUnit(mouseX, mouseY).getMovePts()   );
	        unitAttackPts.setText( "Attack: " + Gui.getUnit(mouseX, mouseY).getAttackPts()   );
	        unitAttackDamage.setText( "Damage: " + Gui.getUnit(mouseX, mouseY).getAttackDamage()   );
	        unitAttackRange.setText( "Range: " + Gui.getUnit(mouseX, mouseY).getAttackRange()   );
		}
		/*else {
			unitSide.setText(   "Side: "   );
			unitName.setText(   "Name: "   );
			unitHealth.setText( "Health: " );
	        unitMovePts.setText( "Move: ");
	        unitAttackPts.setText( "Attack: ");
	        unitAttackDamage.setText( "Damage: ");
	        unitAttackRange.setText( "Range: ");
		}*/
	}
    
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == endOfTurnButton )
		{
			for(Unit u : alAlliesUnits ){
				u.setMovePts( u.getDefaultMovePts() );
				u.setAttackPts(u.getDefaultAttackPts());
			}
			for(Unit u : alEnemiesUnits ){
				u.setMovePts( u.getDefaultMovePts() );
				u.setAttackPts(u.getDefaultAttackPts());
			}
		}
	}
	
	public static void main(String[] args) throws IOException{
		@SuppressWarnings("unused")
		Gui gui = new Gui();
	}
}
