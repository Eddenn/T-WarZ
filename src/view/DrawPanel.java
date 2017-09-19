package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import work.Unit;

public class DrawPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private int sizeX;
	private int sizeY;
	private int posMouseX;
	private int posMouseY;
	private static BufferedImage imgAllySoldier;
	
	public DrawPanel(int sizeX,int sizeY) throws IOException{
		super();
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.posMouseX = -1;
		this.posMouseY = -1;
		imgAllySoldier = ImageIO.read( getClass().getResource("/soldier_50x50.png") );
	}
	
    @Override public void paintComponent(Graphics g) {
    	super.paintComponent(g);
		boolean switchColor = true;
		Graphics2D g2d = (Graphics2D) g;
		//Draw Background
		for(int i=0; i<(sizeX*50) ;i=i+50){
			for(int j=0; j<(sizeY*50) ;j=j+50){
				if( switchColor == true) g2d.setColor( new Color(200,200,200) );
				else 		  g2d.setColor( new Color(175,175,175) );
				g2d.fillRect( i, j, 50, 50 );
				switchColor ^= true;
				//Draw Unit
				if(Gui.getUnit(i,j) != null && Gui.getUnit(i,j).getName().equals("Soldiers") ){	
					if( Gui.alEnemiesUnits.contains( Gui.getUnit(i, j) ) ) 
						g2d.setColor( new Color(255,100,100) );
					else
						g2d.setColor( new Color(100,100,255) );
					g2d.fillRect( i, j, 50, 50 );
					g2d.drawImage(imgAllySoldier,i,j,null);
				}
				//Draw Unit Selected
				if( Gui.unitSelected != null)
				{
					if( Gui.unitSelected.getAttackPts() > 0 )
						paintUnitAttackPossibilities(Gui.unitSelected,g2d);
					g2d.setColor( new Color(100,100,100,2) );
					g2d.fillRect( Gui.unitSelected.getX(),Gui.unitSelected.getY(), 50, 50 ); 
				}
				
			}
		}
		
		if( Gui.getUnit(posMouseX,posMouseY) != null )
			paintUnitMovePossibilities(Gui.getUnit(posMouseX,posMouseY),g2d);
    }
    
    private void paintUnitAttackPossibilities(Unit u,Graphics2D g2d) {
		g2d.setColor( new Color(255,0,0,100) );
		if(u.getAttackRange() > 0 ) { 
			 if( Gui.getUnit(u.getX()-50,u.getY()   ) != null )g2d.drawRect( u.getX()-50, u.getY()   , 50, 50 );
			 if( Gui.getUnit(u.getX()   ,u.getY()-50) != null )g2d.drawRect( u.getX()   , u.getY()-50, 50, 50 );
			 if( Gui.getUnit(u.getX()+50,u.getY()   ) != null )g2d.drawRect( u.getX()+50, u.getY()   , 50, 50 );
			 if( Gui.getUnit(u.getX()   ,u.getY()+50) != null )g2d.drawRect( u.getX()   , u.getY()+50, 50, 50 );
		}
		if(u.getAttackRange() > 1 ) {
			if( Gui.getUnit(u.getX()-100,u.getY()    ) != null )g2d.drawRect( u.getX()-100, u.getY()   , 50, 50 );
			if( Gui.getUnit(u.getX()    ,u.getY()-100) != null )g2d.drawRect( u.getX()    , u.getY()-100, 50, 50 );
			if( Gui.getUnit(u.getX()+100,u.getY()    ) != null )g2d.drawRect( u.getX()+100, u.getY()    , 50, 50 );
			if( Gui.getUnit(u.getX()    ,u.getY()+100) != null )g2d.drawRect( u.getX()    , u.getY()+100, 50, 50 );
			if( Gui.getUnit(u.getX()+50 ,u.getY()+50 ) != null )g2d.drawRect( u.getX()+50 , u.getY()+50 , 50, 50 );
			if( Gui.getUnit(u.getX()-50 ,u.getY()+50 ) != null )g2d.drawRect( u.getX()-50 , u.getY()+50 , 50, 50 );
			if( Gui.getUnit(u.getX()+50 ,u.getY()-50 ) != null )g2d.drawRect( u.getX()+50 , u.getY()-50 , 50, 50 );
			if( Gui.getUnit(u.getX()-50 ,u.getY()-50 ) != null )g2d.drawRect( u.getX()-50 , u.getY()-50 , 50, 50 );
		}
    }
    private void paintUnitMovePossibilities(Unit u,Graphics2D g2d) {
		g2d.setColor( new Color(0,255,0,100) );
		if(u.getMovePts() > 0 ) {
			 if( Gui.getUnit(u.getX()-50,u.getY()   ) == null )g2d.fillRect( u.getX()-50, u.getY()   , 50, 50 );
			 if( Gui.getUnit(u.getX()   ,u.getY()-50) == null )g2d.fillRect( u.getX()   , u.getY()-50, 50, 50 );
			 if( Gui.getUnit(u.getX()+50,u.getY()   ) == null )g2d.fillRect( u.getX()+50, u.getY()   , 50, 50 );
			 if( Gui.getUnit(u.getX()   ,u.getY()+50) == null )g2d.fillRect( u.getX()   , u.getY()+50, 50, 50 );
		}
		if(u.getMovePts() > 1 ) {
			if( Gui.getUnit(u.getX()-100,u.getY()    ) == null )g2d.fillRect( u.getX()-100, u.getY()   , 50, 50 );
			if( Gui.getUnit(u.getX()    ,u.getY()-100) == null )g2d.fillRect( u.getX()    , u.getY()-100, 50, 50 );
			if( Gui.getUnit(u.getX()+100,u.getY()    ) == null )g2d.fillRect( u.getX()+100, u.getY()    , 50, 50 );
			if( Gui.getUnit(u.getX()    ,u.getY()+100) == null )g2d.fillRect( u.getX()    , u.getY()+100, 50, 50 );
			if( Gui.getUnit(u.getX()+50 ,u.getY()+50 ) == null )g2d.fillRect( u.getX()+50 , u.getY()+50 , 50, 50 );
			if( Gui.getUnit(u.getX()-50 ,u.getY()+50 ) == null )g2d.fillRect( u.getX()-50 , u.getY()+50 , 50, 50 );
			if( Gui.getUnit(u.getX()+50 ,u.getY()-50 ) == null )g2d.fillRect( u.getX()+50 , u.getY()-50 , 50, 50 );
			if( Gui.getUnit(u.getX()-50 ,u.getY()-50 ) == null )g2d.fillRect( u.getX()-50 , u.getY()-50 , 50, 50 );
		}
    }
    
    public void setPosMouse(int posMouseX,int posMouseY) {
    	if( posMouseX == -1 && posMouseY == -1)
    	{
        	this.posMouseX = -1;
        	this.posMouseY = -1;
    	}
    	this.posMouseX = posMouseX;
    	this.posMouseY = posMouseY;
    }
    
    @Override public Dimension getPreferredSize(){
    	return new Dimension(sizeX*50,sizeY*50);
    }
}
