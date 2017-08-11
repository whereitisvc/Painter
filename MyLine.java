import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;

public class MyLine extends MyShape
{
	Random randnum = new Random();
	boolean starm = false;
	int gx1 = 5+randnum.nextInt(300);
	int gy1 = 30+randnum.nextInt(300);
	int gx2 = 35+randnum.nextInt(300);
	int gy2 = 100+randnum.nextInt(300);
    public MyLine(int _x1,int _y1,int _x2,int _y2, Color _color1, Color _color2, float _linew, boolean _filled, boolean _grad, boolean _dashed, boolean _starm, int _dashl)
    {
        x1=_x1; x2=_x2; y1=_y1; y2=_y2;
        color1 = _color1;
        color2 = _color2;
        
        linew = _linew;
        
        filled = _filled;
        gradient = _grad;
        dashed = _dashed;
		starm = _starm;
        dashl = _dashl;
    }
    public void drawshape( Graphics g )
    {
        Graphics2D g2 = (Graphics2D) g;
		
		if( !starm )
		{
		
        if(!gradient)
            g2.setPaint(color1);
        else if(gradient)
            g2.setPaint( new GradientPaint(gx1,gy1,color1,gx2,gy2,color2,true) );
        
        if(!dashed)
            g2.setStroke( new BasicStroke(linew) );
        else if(dashed)
        {
            float[] dashes = {dashl};
                    g2.setStroke( new BasicStroke(linew, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10, dashes, 0));
        }
        
        if(!filled)
            g2.draw( new Line2D.Double(x1,y1,x2,y2) );
        else if(filled)
            g2.fill( new Line2D.Double(x1,y1,x2,y2) );
			
		}
		
		else if( starm )
		{	
		int xPoints[] = { 55,67,109,73,83,55,27,37,1,43 };
		int yPoints[] = { 0,36,36,54,96,72,96,54,36,36 };
		GeneralPath star = new GeneralPath();
		star.moveTo( xPoints[0], yPoints[0] );
		for(int i=1;i<10;i++)  star.lineTo( xPoints[i],yPoints[i] );
		star.closePath();
		
		BufferedImage starimg = new BufferedImage( 100,100,BufferedImage.TYPE_4BYTE_ABGR );
		Graphics2D gg = starimg.createGraphics();
		gg.setColor( color1 );
		gg.fill( star );
		
		int size = 50;
		int xbias = 22, ybias = 18;
		int count = 0;
		double distance = 0;
		int xn = 0,yn = 0;
		
		g.drawImage( starimg,x1-xbias,y1-ybias,size,size,null );
		/*
		distance = Math.pow( ( (x2-x1)*(x2-x1)+(y2-y1)*(y2-y1) ) , 1/2 );
		count = (int)( (distance-size)/size );  System.out.printf("xdis = %d count = %d\n",(int)(x2-x1),count);
		if(count!=0)
		{	xn = (int)((x2-x1)/count); yn = (int)((y2-y1)/count); }
		else{ xn = 0; yn = 0; }
		for(int i=0;i<count;i++)
		{
			g.drawImage( starimg,x1+xn*i-xbias,y1+yn*i-ybias,size,size,null );
		}
		
		//g.drawImage( starimg,x1-xbias,y1-ybias,size,size,null );*/
		
		}
		
    }
}
