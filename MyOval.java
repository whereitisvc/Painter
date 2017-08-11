import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
public class MyOval extends MyShape
{
	Random randnum = new Random();
	int gx1 = 5+randnum.nextInt(300);
	int gy1 = 30+randnum.nextInt(300);
	int gx2 = 35+randnum.nextInt(300);
	int gy2 = 100+randnum.nextInt(300);
    public MyOval(int _x1,int _y1,int _x2,int _y2, Color _color1, Color _color2, float _linew, boolean _filled, boolean _grad, boolean _dashed, int _dashl)
    {
        x1=_x1; x2=_x2; y1=_y1; y2=_y2;
        if( x2>x1 && y2>y1 ){ ox = x1; oy = y1; }
	else if( x2<x1 && y2<y1 ){ ox = x2; oy = y2; }
	else if( x2<x1 && y2>y1 ){ ox = x2; oy = y1; }
	else if( x2>x1 && y2<y1 ){ ox = x1; oy = y2; }		
	if( x2>x1 ) width = x2-x1;
	else        width = x1-x2;				
	if( y2>y1 ) height = y2-y1;
	else        height = y1-y2;       
        
        color1 = _color1;
        color2 = _color2;
        
        linew = _linew;
        
        filled = _filled;
        gradient = _grad;
        dashed = _dashed;
        dashl = _dashl;
    }
    public void drawshape( Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
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
            g2.draw( new Ellipse2D.Double(ox,oy,width,height) );
        else if(filled)
            g2.fill( new Ellipse2D.Double(ox,oy,width,height) );
    }
}
