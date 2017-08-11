import javax.swing.*;
import java.awt.*;

public abstract class MyShape/* implements Shape*/
{
    protected Color color1,color2;
    protected int x1,y1,x2,y2;
    protected int ox,oy;
    protected int width,height;
    protected float linew;
    protected boolean filled,gradient,dashed;
    protected int dashl;
    
    
    public MyShape()
    {
        color1 = Color.BLACK;
        color2 = Color.BLACK;
        x1=0;x2=0;y1=0;y2=0;
        ox=0; oy=0;
        width=0; height=0;
        filled=false; gradient=false; dashed=false;
        linew=0;
        dashl=0;
    }
    public abstract void drawshape( Graphics g);
}