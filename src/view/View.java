package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import control.Mouseaction;
import fr.lri.swingstates.canvas.CImage;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.CText;
import fr.lri.swingstates.canvas.CWidget;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.debug.StateMachineVisualization;
import model.Clock;
import model.Model;

public class View extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public double a=100;// a is the parameter to control the size of the square
	
	public int x=450,y=400;//the first position of Tangram
	public Canvas canvas;
	public Triangle t1;
	public Triangle t2;
	public Triangle t3;
	public Triangle t4;
	public Triangle t5;
	public Quadrangle qdg;
	public Square sqr;
	
	public Triangle lt1;
	public Triangle lt2;
	public Triangle lt3;
	public Triangle lt4;
	public Triangle lt5;
	public Quadrangle lqdg;
	public Square lsqr;
	
	public JPanel fill;
	public CShape fill_area;
	public CText chosecolor;
	
	public JButton plus= new JButton("+");
	public JButton moin= new JButton("-");
	public CWidget Plus;
    public	CWidget Moin;
	
	
	public CText tips;
	
	public JFrame smviz;
	public Model model;
	public Clock clock;
	public Mouseaction ma=new Mouseaction();
	
	public JTextArea ta=new JTextArea(11,10);
	public CWidget skill;
	
	public CText time;
	public CText temp;
	
	public JButton quit=new JButton("quit");
	public CWidget quit_button;
	
	public JButton player=new JButton("player");
	public CWidget player_button;
	
	public JButton save=new JButton("save");
	public CWidget save_button;
	
	public JButton reset=new JButton("reset");
	public CWidget reset_button;
	
	public JButton load=new JButton("load");
	public CWidget load_button;
	
	String background_path="image/bg.jpg";
	CImage background;
	public boolean control=true;
	
	public View(String title, int width, int height,boolean flag){
		   super(title) ;
		   this.setSize(width,height);
		   this.setLocation(300,70);
		   canvas = new Canvas(width, height) ;
		   getContentPane().add(canvas) ;
		   
		   background=canvas.newImage(0, 0, background_path);
		   background.aboveAll();
		   //create the square
		   t1= new Triangle(new Point2D.Double(x,y), a, 1, canvas);
		   t2= new Triangle(new Point2D.Double(x+a/4,y+a/4), a, 2,canvas);
		   t3= new Triangle(new Point2D.Double(x+a/2,y), a, 3,canvas);
		   t4= new Triangle(new Point2D.Double(x,y+a), a, 4, canvas);
		   t5= new Triangle(new Point2D.Double(x+a,y), a, 5, canvas);
		   qdg=new Quadrangle(new Point2D.Double(x,y+a/2),a,1,canvas);
		   sqr=new Square(new Point2D.Double(x+a/4,y+a/4),a,1,canvas);
		   
		   lt1= new Triangle(new Point2D.Double(x,y), a, 1, canvas);
		   lt2= new Triangle(new Point2D.Double(x+a/4,y+a/4), a, 2,canvas);
		   lt3= new Triangle(new Point2D.Double(x+a/2,y), a, 3,canvas);
		   lt4= new Triangle(new Point2D.Double(x,y+a), a, 4, canvas);
		   lt5= new Triangle(new Point2D.Double(x+a,y), a, 5, canvas);
		   lqdg=new Quadrangle(new Point2D.Double(x,y+a/2),a,1,canvas);
		   lsqr=new Square(new Point2D.Double(x+a/4,y+a/4),a,1,canvas);
		  
		   lt1.polyline.above(background);
		   lt2.polyline.above(background);
		   lt3.polyline.above(background);
		   lt4.polyline.above(background);
		   lt5.polyline.above(background);
		   lqdg.polyline.above(background);
		   lsqr.polyline.above(background);
		   if (flag==false)
		   {
			  
		   }
		   
		   
		   time=canvas.newText(x+50,y-320, "TIME", new Font("Times New Roman",Font.ITALIC+Font.BOLD,18));
		   temp=canvas.newText(x+50, y-290,"0", new Font("Times New Roman",Font.ITALIC+Font.BOLD,18));
		   tips=canvas.newText(x+50, y-250, "TIPS", new Font("Times New Roman",Font.ITALIC+Font.BOLD,18));
		   
		   ta.setLineWrap(true);
		   ta.setText("Control:"+"\n"
		   		+ "click the left mouse button to select the object,"
		   		+ " click the right mouse button to deselect the object.\n"
		   		+ "after select the object use the keyword 'a' and 'd' to rotate");
		   skill=canvas.newWidget(ta,x,y-220);
		   skill.setFillPaint(new Color(153,153,255));
		   
		  clock=new Clock(this);
		   clock.attachTo(temp);
		   clock.armTimer(1000, true);
		   
		   model=new Model(this);
		   model.attachTo(canvas);
		  
		   
		   quit_button=canvas.newWidget(quit, x+50, y+120,50,20);
		   quit_button.detachSM(model);
		   quit.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}});
		   if (flag)
		   {   //create the button to chose color;
			   smviz = new JFrame("StateMachine Viz") ;
			   fill = createColorSample(Color.LIGHT_GRAY);
			   chosecolor=canvas.newText(20, 370, "choose a color",new Font("Times New Roman",Font.BOLD,18));
		   	   fill_area=canvas.newWidget(fill, 20, y,40,20);
		   	   
		   	   save_button=canvas.newWidget(save, 20, y-60 ,50,20);
			   save_button.addTag("sv");
			   
			   reset_button=canvas.newWidget(reset, 20, y-100 ,50,20);
			   reset_button.addTag("rs");
			   
			   load_button=canvas.newWidget(load, 80, y-60 ,50,20);
			   load_button.addTag("ld");
		   	   
		   	   Plus=canvas.newWidget(plus,20,y+20,20,20);
		   	   Plus.addTag("+");
		   	   
		   	   Moin=canvas.newWidget(moin,40,y+20,20,20);
		   	   Moin.addTag("-");
		   	   
		   	   fill_area.detachSM(model);
		   	   tips.addTag("draggable");
		   	   skill.addTag("draggable");
		   	   player_button=canvas.newWidget(player, x, y+120,50,20);
		   	   player_button.addTag("change");
		   	   //create the state machine;
			   
			   Container pane = smviz.getContentPane() ;
			   pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
			   pane.add(new JLabel("developer")) ;
			   pane.add(new StateMachineVisualization(model)) ;
			   pane.add(new JLabel("clock")) ;
			   pane.add(new StateMachineVisualization(clock)) ;
			   smviz.pack() ;
			   smviz.setVisible(true) ;
		   }
		   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
		   setVisible(true) ;
	}
	private JPanel createColorSample(Color c) {
		JPanel p = new JPanel();
		p.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		p.setOpaque(true);
		p.setBackground(c);
		p.setMaximumSize(new Dimension(500, 150));
		p.addMouseListener(ma);
		return p;
	}



}
