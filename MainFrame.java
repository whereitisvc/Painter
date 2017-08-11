import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainFrame extends JFrame implements ActionListener,ItemListener{
    private JPanel color1p,color2p;
    private JLabel statusbar,mousestatus;
    private JButton undob,redob,clearb,color1b,color2b;
	private JButton saveb,loadb;
    private JComboBox shapecombobox;
    private static final String[] shapestr={"Brush","Oval","Rectangle","Line"};
    private JCheckBox fillbox,gradientbox,dashbox,starbox;
    private JTextField linewfield,dashlfield;
    
    private DrawPanel drawpanel;
    
    private Color color1 = Color.BLUE,color2 = Color.RED;
    private float linew = 4;
    private int dashl = 4;
    private int mode =1;
	//private int brush_mode =1;
    
    public MainFrame()
    {
        super("Painter");
        this.setSize(800,600);
        //setLayout(new BorderLayout());
        statusbar = new JLabel("(0,0)");
        mousestatus = new JLabel("mousestatus");
        drawpanel = new DrawPanel(statusbar,mousestatus);
        drawpanel.setcolor1(color1);
        drawpanel.setcolor2(color2);
        drawpanel.setlinew(linew);
        drawpanel.setdashl(dashl);
        
        
        JPanel funcP1 = new JPanel();
        JPanel funcP2 = new JPanel();
        
        undob = new JButton("Undo");
                undob.addActionListener(this);
        redob = new JButton("Redo");
                redob.addActionListener(this);
        clearb = new JButton("Clear");
				clearb.addActionListener(this);
		saveb = new JButton("SAVE");
				saveb.addActionListener(this);
		loadb = new JButton("LOAD");
				loadb.addActionListener(this);	
				
        shapecombobox = new JComboBox(shapestr);
        shapecombobox.addItemListener(this);
        
		starbox = new JCheckBox("Star(brush mode)");
		starbox.addItemListener(this);
                
        funcP1.add(undob);
        funcP1.add(redob);
        funcP1.add(clearb);
		funcP1.add(saveb);
		funcP1.add(loadb);
        funcP1.add(new JLabel("Shape:"));
        funcP1.add(shapecombobox);
        funcP1.add(starbox);
        
        
        gradientbox = new JCheckBox("Use Gradient");
        gradientbox.addItemListener(this);
        color1b = new JButton("1st Color...");
        color1b.addActionListener(this);
        color2b = new JButton("2nd Color...");
        color2b.addActionListener(this);
        linewfield = new JTextField("4",5);
        linewfield.setHorizontalAlignment(JTextField.RIGHT);
        linewfield.setEditable(true);
        linewfield.addActionListener(this);
        dashlfield = new JTextField("4",5);
        dashlfield.setHorizontalAlignment(JTextField.RIGHT);
        dashlfield.setEditable(true);
        dashlfield.addActionListener(this);
        dashbox = new JCheckBox("Dashed");
        dashbox.addItemListener(this);
		fillbox = new JCheckBox("Filled");
		fillbox.addItemListener(this);
        
        funcP2.add(gradientbox);
        funcP2.add(color1b);
        color1p = new JPanel();
        color1p.setBackground(color1);
        funcP2.add(color1p);
        funcP2.add(color2b);
        color2p = new JPanel();
        color2p.setBackground(color2);
        funcP2.add(color2p);
        funcP2.add(new JLabel("Line width:"));
        funcP2.add(linewfield);
        funcP2.add(new JLabel("Dash Length:"));
        funcP2.add(dashlfield);
        funcP2.add(dashbox);
		funcP2.add(fillbox);
        
        JPanel funcall = new JPanel();
        funcall.setLayout(new GridLayout(2,1,2,2));
        funcall.add(funcP1);
        funcall.add(funcP2);
        this.add(funcall,BorderLayout.NORTH);
        this.add(drawpanel,BorderLayout.CENTER);
        JPanel statusp =new JPanel();
        statusp.setLayout(new GridLayout(1,2,2,2));
        statusp.add(statusbar);
        statusp.add(mousestatus);
        this.add(statusp,BorderLayout.SOUTH);
        drawpanel.setFocusable(true);
        

    }
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==color1b)
        {
            color1 = JColorChooser.showDialog(MainFrame.this, "Choose a color", color1);
            if(color1==null)
                color1=Color.BLACK;
            color1p.setBackground(color1);
            drawpanel.setcolor1(color1);
        }
        else if(e.getSource()==color2b)
        {
            color2 = JColorChooser.showDialog(MainFrame.this, "Choose a color", color1);
            if(color2==null)
                color2=Color.BLACK;
            color2p.setBackground(color2);
            drawpanel.setcolor2(color2);
        }
        else if(e.getSource()==linewfield)
        {
            linew = Integer.valueOf( e.getActionCommand() );
            drawpanel.setlinew(linew);
        }
        else if(e.getSource()==dashlfield)
        {
            dashl = Integer.valueOf( e.getActionCommand() );
            drawpanel.setdashl(dashl);
        }
	else if(e.getSource()==undob)
	{
            System.out.println("Undob be pressed");
            drawpanel.panelundo();
	}
    else if(e.getSource()==redob)
	{
            System.out.println("Redob be pressed");
            drawpanel.panelredo();
	}
	else if(e.getSource()==clearb)
	{
            System.out.println("Clearb be pressed");
            drawpanel.panelclear();
	}
	else if(e.getSource()==saveb)
	{
            System.out.println("SAVEb be pressed");
            drawpanel.save();
	}
	else if(e.getSource()==loadb)
	{
            System.out.println("LOADb be pressed");
            drawpanel.load();
	}
        
    }
    public void itemStateChanged(ItemEvent e)
    {
        if(e.getStateChange()==ItemEvent.SELECTED){
                drawpanel.setmode(shapecombobox.getSelectedIndex()+1);
				System.out.println("Now in mode"+(shapecombobox.getSelectedIndex()+1));  
            drawpanel.setfillmode(fillbox.isSelected());
            drawpanel.setgradientmode(gradientbox.isSelected());
            drawpanel.setdashmode(dashbox.isSelected());
			drawpanel.setstarmode(starbox.isSelected());
        }
        else if (e.getStateChange()==ItemEvent.DESELECTED)
        {
            drawpanel.setfillmode(fillbox.isSelected());
            drawpanel.setgradientmode(gradientbox.isSelected());
            drawpanel.setdashmode(dashbox.isSelected());
			drawpanel.setstarmode(starbox.isSelected());
        }
    }
    //private void CheckBoxHandler implements
}