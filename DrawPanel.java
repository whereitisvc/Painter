import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.LinkedList;
import java.util.Iterator;
import javax.swing.*;
import javax.swing.undo.*;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener{

	int width = 600, height = 600;
	int x1,y1;
	int x2,y2;
	int x,y;
	boolean first = true;
	boolean ispress = false;
	boolean end = true;
        boolean filled = false;
        boolean gradient = false;
        boolean dashed = false;
		boolean star = false;
        int dashl;

	///////////////////////////////////
        private JLabel statusbar = new JLabel("(0,0)");
        private JLabel mousestatus = new JLabel("mousestatus");
        private Color color1,color2;
        private float linew;
        private int mode =1;
        ///////////////////////////// 
        UndoManager undomgr;
        LinkedList myshapes;
	///////////////////////////
	int brushcount = 0;
	int brushcount_array[];
	int strokes = 0;
	int array_length = 0;
	////////////////////////////
	private BufferedImage buffimage;
	
	public DrawPanel(JLabel statusbar,JLabel mousestatus)
	{
            ////////////////////////////////
            undomgr = new UndoManager();
            myshapes = new LinkedList();
            /////////////////////////////////
			brushcount_array = new int[1000];
			undomgr.setLimit(1000);

            this.statusbar = statusbar;
            this.mousestatus = mousestatus;
            this.linew = linew;
            
            this.setBackground(Color.white);
            //this.setSize(800, 600);
            //this.add(statusbar,BorderLayout.SOUTH);
            
            this.addMouseListener(this);
            this.addMouseMotionListener(this);
	}
	
	//MouseListener
	public void mousePressed(MouseEvent e){

            mousestatus.setText("mousePressed");
            if(mode == 1){}
            else if(mode == 2)
            {
               end = false;
            }
			else if(mode == 3)
            {
               end = false;
            }
			else if(mode == 4)
            {
               end = false;
            }
          
		
	}
	public void mouseReleased(MouseEvent e){
			
            //System.out.println("mouseReleased");
            mousestatus.setText("mouseReleased");
            if( mode==1 )
			{	
				array_length = strokes;
				brushcount_array[strokes] = brushcount;	
				strokes++;
				brushcount = 0;
			}
			else if( mode==2 )
			{	
				array_length = strokes;
				brushcount_array[strokes] = 1;		
				strokes++;
                //System.out.println("Oval be finished");
			}
			else if( mode==3 )
			{
				array_length = strokes;
				brushcount_array[strokes] = 1;		
				strokes++;
                //System.out.println("Rectangle be finished");
			}
			else if( mode==4 )
			{	
				array_length = strokes;
				brushcount_array[strokes] = 1;		
				strokes++;
                //System.out.println("Line be finished");
			}
			first = true;
			end = true;
			
			System.out.printf("brushcount_array[%d] = ",strokes-1);
			for(int i=0;i<strokes;i++)
				System.out.printf("%d  ",brushcount_array[i]);
			System.out.println();
          
	}
    public void mouseClicked(MouseEvent event){mousestatus.setText("mouseClicked");}
    public void mouseEntered(MouseEvent event){mousestatus.setText("mouseEntered");}
    public void mouseExited(MouseEvent event){mousestatus.setText("mouseExited");}
	
	//MouseMotionListener
	public void mouseMoved( MouseEvent event ){
 
            mousestatus.setText("mouseMoved");
            
            int x3=event.getX();
            int y3=(this.getHeight())-(event.getY());
            statusbar.setText("("+x3+","+y3+")");
             
			first = true;
			end = true;
        }
	public void mouseDragged( MouseEvent e ){
		
            mousestatus.setText("mouseDragged");

            int x3=e.getX();
            int y3=(this.getHeight())-(e.getY());
            statusbar.setText("("+x3+","+y3+")");
            	    
            if(mode == 1)
            {
                if(first)
                {  x1 = e.getX(); y1 = e.getY(); first = false; }
            
                    //Shape temp;
                    x2 = e.getX(); y2 = e.getY();

                    double x1d = new Double(x1);
                    double x2d = new Double(x2);
                    double y1d = new Double(y1);
                    double y2d = new Double(y2);
                    
                    //temp = new Line2D.Double(x1d,y1d,x2d,y2d);
                    MyLine templine;
                    templine = new MyLine(x1,y1,x2,y2,color1,color2,linew,false,gradient,dashed,star,dashl);
                    
                    x1 = e.getX(); y1 = e.getY();

                     myshapes.add(templine);
			repaint();
			UndoableEdit edit = new graphEdit(templine);
			undomgr.addEdit(edit);
					brushcount++;
            }
            if(mode == 2)
            {
				boolean remove = true;
				
				if(first)
                {  x1 = e.getX(); y1 = e.getY(); first = false; remove = false; }							
                if( !end && remove )
                {
						try{  undomgr.undo(); }
						catch(CannotUndoException ex)
						{    System.err.println("Can't Undo More");  }
                }
				
				
                x2 = e.getX(); y2 = e.getY();
                    MyOval tempoval;
                    tempoval = new MyOval(x1,y1,x2,y2,color1,color2,linew,filled,gradient,dashed,dashl);
                     myshapes.add(tempoval);
				repaint();
				UndoableEdit edit = new graphEdit(tempoval);
				undomgr.addEdit(edit);
            }
            
            else if(mode == 3)
            {
				boolean remove = true;
				
				if(first)
                {  x1 = e.getX(); y1 = e.getY(); first = false; remove = false; }							
                if( !end && remove )
                {
						try{  undomgr.undo(); }
						catch(CannotUndoException ex)
						{    System.err.println("Can't Undo More");  }
                }
				
				int ow=0,oh=0,ox=0,oy=0;
                x2 = e.getX(); y2 = e.getY();
                    MyRectangle temprec;
                    temprec = new MyRectangle(x1,y1,x2,y2,color1,color2,linew,filled,gradient,dashed,dashl);
                     myshapes.add(temprec);
				repaint();
				UndoableEdit edit = new graphEdit(temprec);
				undomgr.addEdit(edit);
            }
            else if(mode == 4)
            {
				boolean remove = true;
				
				if(first)
                {  x1 = e.getX(); y1 = e.getY(); first = false; remove = false; }							
                if( !end && remove )
                {
						try{  undomgr.undo(); }
						catch(CannotUndoException ex)
						{    System.err.println("Can't Undo More");  }
                }

                x2 = e.getX(); y2 = e.getY();
                    MyLine templine;
                    templine = new MyLine(x1,y1,x2,y2,color1,color2,linew,false,gradient,dashed,star,dashl);
                     myshapes.add(templine);
				repaint();
				UndoableEdit edit = new graphEdit(templine);
				undomgr.addEdit(edit);
            }
            end = false;

	}
	
	public void paintComponent(Graphics g)
        {
            Graphics2D g2 = (Graphics2D) g;
            
            g2.setColor(Color.white);
			g2.fill3DRect(0,0,getWidth(),getHeight(),true);
            //g2.setColor(color1);
            
            Iterator it;
            MyShape shape;

			for(it = myshapes.iterator();it.hasNext();){
				shape = (MyShape)it.next();
				shape.drawshape(g);	
            }
        }
        
        public void setcolor1(Color color)
        {
            color1 = color;
        }
        public void setcolor2(Color color)
        {
            color2 = color;
        }
        public void setlinew(float w)
        {
            linew = w;
        }
        public void setmode(int m)
        {
            mode = m;
        }
		public void setstarmode(boolean s)
        {
            star = s;
        }
        public void setfillmode(boolean fill)
        {
            filled = fill;
        }
        public void setgradientmode(boolean grad)
        {
            gradient = grad;
        }
        public void setdashmode(boolean dash)
        {
            dashed = dash;
        }
        public void setdashl(int l)
        {
            dashl = l;
        }

		
	public void panelundo()
	{
            try
			{
					strokes--;
					if(strokes<0) strokes = 0;
					for(int i=0;i<brushcount_array[strokes];i++)
					{   undomgr.undo();   }
					
					System.out.printf("brushcount_array[%d] = ",strokes-1);
					for(int i=0;i<strokes;i++)
						System.out.printf("%d  ",brushcount_array[i]);
					System.out.println();

				
            }catch(CannotUndoException ex){
                System.err.println("Can't Undo More");
            }
	}
    public void panelredo()
	{
            try{
				 if( !(strokes > array_length) && !(array_length < 0) )
				 {
					for(int i=0;i<brushcount_array[strokes];i++)
					{   undomgr.redo();   }
					strokes++;
					
					System.out.printf("brushcount_array[%d] = ",strokes-1);
					for(int i=0;i<strokes;i++)
						System.out.printf("%d  ",brushcount_array[i]);
					System.out.println();
				 }
				 else System.out.println("Can't Redo by logic");
			
			
            }catch(CannotRedoException ex){
                System.err.println("Can't Redo More");
            }
	}
    public void panelclear()
    {      
             MyRectangle temprec;
             temprec = new MyRectangle(0,0,1366,768,Color.WHITE,Color.WHITE,linew,true,false,dashed,dashl);
             myshapes.add(temprec);
             repaint();
             UndoableEdit edit = new graphEdit(temprec);
             undomgr.addEdit(edit);
             
             array_length = strokes;
             brushcount_array[strokes] = 1;		
             strokes++;
    }
	public void save(){
	
			buffimage = new BufferedImage( getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB ); //TYPE_INT_BGR TYPE_INT_RGB
			Graphics2D gg = buffimage.createGraphics();
			
            gg.setColor(Color.white);
			gg.fill3DRect(0,0,getWidth(),getHeight(),true);
            //g2.setColor(color1);
            
            Iterator it;
            MyShape shape;

			for(it = myshapes.iterator();it.hasNext();){
				shape = (MyShape)it.next();
				shape.drawshape(gg);	
            }

					try{
							ImageIO.write(buffimage, "jpg", new File("output.jpg") );
					}
					catch(IOException e) 
					{       System.out.println("Cannot save.");  }
	
	}
	public void load(){
			
			//Image image;
			try
			{
					MyImage tempimg;
					Image image = ImageIO.read(new File("output.jpg"));
					tempimg = new MyImage(image);
					myshapes.add(tempimg);
					repaint();
					UndoableEdit edit = new graphEdit(tempimg);
					undomgr.addEdit(edit);
			} 
			catch (IOException e)
			{		 System.out.println("Cannot load.");   }
			
	}
        
    class graphEdit extends AbstractUndoableEdit{
        MyShape myshape;
	public graphEdit(MyShape _shape){
			
			myshape = _shape;

	}
	public void undo(){
                        myshapes.remove(myshape);
	    repaint();
	    //System.out.println("undo draw line");
	}
	public void redo(){
            myshapes.add(myshape);
	    repaint();
	    //System.out.println("redo draw line");
	}
	
    }
}