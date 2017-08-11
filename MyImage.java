import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

public class MyImage extends MyShape
{
	Image image;
    public MyImage( Image i )
    {
        image = i;
    }
    public void drawshape( Graphics g )
    {
        g.drawImage(image, 0,0, null);
    }
}
