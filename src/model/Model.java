package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import fr.lri.swingstates.canvas.CElement;
import fr.lri.swingstates.canvas.CExtensionalTag;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.transitions.ClickOnTag;
import fr.lri.swingstates.canvas.transitions.PressOnTag;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Click;
import fr.lri.swingstates.sm.transitions.Drag;
import fr.lri.swingstates.sm.transitions.KeyPress;
import fr.lri.swingstates.sm.transitions.KeyRelease;
import fr.lri.swingstates.sm.transitions.Release;
import view.Quadrangle;
import view.Square;
import view.Triangle;
import view.View;

public class Model extends CStateMachine{
	
	private CExtensionalTag movableTag ;
	View view;
    private CElement selected ;
	State start,select,dragged,rotated;
	State save, load, reset;
	Point2D pPrevious;
	Point2D G;
	
	double GP,GN,N,PS;
	Vector2D PG,NG; 
	
	double angle;
	private int[] borderwidth = { 1, 2 };
	CShape chosen;
	Color c;
	int i=0;
	public Model(View view){
		this.view=view;

		start = new State() {
			Transition sv = new PressOnTag("sv",BUTTON1,NOMODIFIER, ">> save"){};
			Transition ld = new PressOnTag("ld",BUTTON1,NOMODIFIER, ">> load"){};
			Transition rs = new ClickOnTag("rs",BUTTON1,NOMODIFIER, ">> reset"){};
			
			 Transition move = new ClickOnTag("draggable",BUTTON1,NOMODIFIER, ">> select") {
				    public void action() {
					   pPrevious = getPoint() ;
					   selected = getShape() ;
					   if (view.fill!=null)
					   { 
						   c=view.fill.getBackground();
						   if (c!=Color.LIGHT_GRAY)
						   { 	
							   selected.setFillPaint(c);
						   }
					   }
					   G=new Point2D.Double(selected.getCenterX(),selected.getCenterY());
					   selected.setStroke(new BasicStroke(borderwidth[1]));
					   consumes(true) ;
				    }
				} ;
			Transition change = new PressOnTag("change",BUTTON1){
				public void action(){
					view.canvas.removeShape(view.fill_area);
					view.canvas.removeShape(view.player_button);
					view.canvas.removeShape(view.chosecolor);
					view.canvas.removeShape(view.Plus);
					view.canvas.removeShape(view.Moin);
					view.canvas.removeShape(view.save_button);
					view.canvas.removeShape(view.load_button);

					view.tips.removeTag("draggable");
					view.skill.removeTag("draggable");
					view.control=false;
					view.smviz.setVisible(false);
				
				}
			};
			Transition plus = new PressOnTag("+",BUTTON1){
				public void action(){
					view.canvas.removeShape(view.t1.get_Shape());
					view.canvas.removeShape(view.t2.get_Shape());
					view.canvas.removeShape(view.t3.get_Shape());
					view.canvas.removeShape(view.t4.get_Shape());
					view.canvas.removeShape(view.t5.get_Shape());
					view.canvas.removeShape(view.sqr.get_Shape());
					view.canvas.removeShape(view.qdg.get_Shape());
					view.a=view.a*1.2;
					view.t1= new Triangle(new Point2D.Double(view.x,view.y), view.a, 1,view.canvas);
					view.t2= new Triangle(new Point2D.Double(view.x+view.a/4,view.y+view.a/4), view.a, 2,view.canvas);
					view.t3= new Triangle(new Point2D.Double(view.x+view.a/2,view.y), view.a, 3,view.canvas);
					view.t4= new Triangle(new Point2D.Double(view.x,view.y+view.a), view.a, 4, view.canvas);
					view.t5= new Triangle(new Point2D.Double(view.x+view.a,view.y), view.a, 5,view.canvas);
					view.qdg=new Quadrangle(new Point2D.Double(view.x,view.y+view.a/2),view.a,1,view.canvas);
					view.sqr=new Square(new Point2D.Double(view.x+view.a/4,view.y+view.a/4),view.a,1,view.canvas);
				}
			};
			Transition moins = new PressOnTag("-",BUTTON1){
				public void action(){
					view.canvas.removeShape(view.t1.get_Shape());
					view.canvas.removeShape(view.t2.get_Shape());
					view.canvas.removeShape(view.t3.get_Shape());
					view.canvas.removeShape(view.t4.get_Shape());
					view.canvas.removeShape(view.t5.get_Shape());
					view.canvas.removeShape(view.sqr.get_Shape());
					view.canvas.removeShape(view.qdg.get_Shape());
					view.a=view.a*0.83;
					view.t1= new Triangle(new Point2D.Double(view.x,view.y), view.a, 1, view.canvas);
					view.t2= new Triangle(new Point2D.Double(view.x+view.a/4,view.y+view.a/4), view.a, 2, view.canvas);
					view.t3= new Triangle(new Point2D.Double(view.x+view.a/2,view.y), view.a, 3, view.canvas);
					view.t4= new Triangle(new Point2D.Double(view.x,view.y+view.a), view.a, 4, view.canvas);
					view.t5= new Triangle(new Point2D.Double(view.x+view.a,view.y), view.a, 5,view.canvas);
					view.qdg=new Quadrangle(new Point2D.Double(view.x,view.y+view.a/2),view.a,1,view.canvas);
					view.sqr=new Square(new Point2D.Double(view.x+view.a/4,view.y+view.a/4),view.a,1,view.canvas);
				}
			};
		  } ;
 
	   select = new State() {
			 Transition drag = new Drag(BUTTON1, NOMODIFIER,">>dragged") {
				    public void action() {
				       GP=G.distance(pPrevious);
				       GN=G.distance(getPoint().getX(), getPoint().getY());
				       N=GP*GN;
				       
				       PG=new Vector2D(pPrevious,G);
				       NG=new Vector2D(getPoint(),G);
				       
				       PS=PG.dot(PG,NG);
				       angle=0;
				       if (Math.acos(PS/N)<Math.PI)
				    	   if (Math.acos(PS/N)>-Math.PI)
				    		   angle=Math.acos(PS/N);
				       selected.rotateBy(angle);
	
					   selected.translateBy(getPoint().getX() - pPrevious.getX(), getPoint().getY() - pPrevious.getY()) ;
					   
					   pPrevious = getPoint() ;
					   G=new Point2D.Double(selected.getCenterX(),selected.getCenterY());
					   
				    }
				} ;
			 Transition stop = new Click(BUTTON3, NOMODIFIER, ">> start") {
				 public void action(){
					 selected.setStroke(new BasicStroke(borderwidth[0]));
					 
					 if (Math.abs(selected.getCenterX()-view.lt1.getCX())<10&&Math.abs(selected.getCenterY()-view.lt1.getCY())<10)
					 {
						 System.out.println("coming into the area test:"+view.lt1.angle);
						 selected.translateTo(view.lt1.getCX(),view.lt1.getCY() );
						 selected.rotateTo(view.lt1.angle);
						 i+=1;
					 }
					 if (Math.abs(selected.getCenterX()-view.lt2.getCX())<10&&Math.abs(selected.getCenterY()-view.lt2.getCY())<10)
					 {
						 System.out.println("coming into the area test:"+view.lt1.angle);
						 selected.translateTo(view.lt2.getCX(),view.lt2.getCY() );
						 selected.rotateTo(view.lt2.angle);
						 i+=1;
					 }
					 if (Math.abs(selected.getCenterX()-view.lt3.getCX())<10&&Math.abs(selected.getCenterY()-view.lt3.getCY())<10)
					 {
						 System.out.println("coming into the area test:"+view.lt3.angle);
						 selected.translateTo(view.lt3.getCX(),view.lt3.getCY() );
						 selected.rotateTo(view.lt3.angle);
						 i+=1;
					 }
					 if (Math.abs(selected.getCenterX()-view.lt4.getCX())<10&&Math.abs(selected.getCenterY()-view.lt4.getCY())<10)
					 {
						 System.out.println("coming into the area test:"+view.lt4.angle);
						 selected.translateTo(view.lt4.getCX(),view.lt4.getCY() );
						 selected.rotateTo(view.lt4.angle);
						 i+=1;
					 }
					 if (Math.abs(selected.getCenterX()-view.lt5.getCX())<10&&Math.abs(selected.getCenterY()-view.lt5.getCY())<10)
					 {
						 System.out.println("coming into the area test:"+view.lt5.angle);
						 selected.translateTo(view.lt5.getCX(),view.lt5.getCY() );
						 selected.rotateTo(view.lt5.angle);
						 i+=1;
					 }
					 if (Math.abs(selected.getCenterX()-view.lsqr.getCX())<10&&Math.abs(selected.getCenterY()-view.lsqr.getCY())<10)
					 {
						 System.out.println("coming into the area test:"+view.lsqr.angle);
						 selected.translateTo(view.lsqr.getCX(),view.lsqr.getCY() );
						 selected.rotateTo(view.lsqr.angle);
						 i+=1;
					 }
					 if (Math.abs(selected.getCenterX()-view.lqdg.getCX())<10&&Math.abs(selected.getCenterY()-view.lqdg.getCY())<10)
					 {
						 System.out.println("coming into the area test:"+view.lqdg.angle);
						 selected.translateTo(view.lqdg.getCX(),view.lqdg.getCY() );
						 selected.rotateTo(view.lqdg.angle);
						 i+=1;
					 }
					 if (i>7)
					 {
						 System.out.println("success");
					//todo : make a dialog to show the player win the game
					//todo	 
						 JOptionPane.showMessageDialog (null, "Niveau Reussi!", "Succes", JOptionPane.INFORMATION_MESSAGE);
					 }
				 }
				} ;
			 Transition rotated = new KeyPress(">> rotated"){
				public void action(){
					if (getKeyCode()==65)
						selected.rotateBy(Math.PI*2*45/360);
					if(getKeyCode()==68)
						selected.rotateBy(Math.PI*2*-45/360);
				}
			 };
			 
		  } ;
	   dragged = new State(){
		    Transition drag =  new Drag(BUTTON1, NOMODIFIER) {
			    public void action() {
				       GP=G.distance(pPrevious);
				       GN=G.distance(getPoint().getX(), getPoint().getY());
				       N=GP*GN;
				       
				       PG=new Vector2D(pPrevious,G);
				       NG=new Vector2D(getPoint(),G);
				       PS=PG.dot(PG,NG);
				       if (Math.acos(PS/N)<Math.PI)
				    	   if (Math.acos(PS/N)>-Math.PI)
				    		   angle=Math.acos(PS/N);
				       if (GP<5)
				    	   angle=0;
				       selected.rotateBy(angle);
				   selected.translateBy(getPoint().getX() - pPrevious.getX(), getPoint().getY() - pPrevious.getY()) ;
				   pPrevious = getPoint() ;
				   G=new Point2D.Double(selected.getCenterX(),selected.getCenterY());
				   
			    }
			} ;
			Transition quit = new Release(BUTTON1,NOMODIFIER,">> select"){};
			};
		rotated = new State(){
			Transition rotate= new KeyRelease(">>select"){};

		};
		save = new State() {
			
			Transition sv = new Release(BUTTON1,NOMODIFIER, ">> start"){
				public void action(){
					JFileChooser fileChooser = new JFileChooser();
					Component modalToComponent = null;
					if (fileChooser.showSaveDialog(modalToComponent) == JFileChooser.APPROVE_OPTION) {
					  File file = fileChooser.getSelectedFile();
					  
					try{
					    PrintWriter writer = new PrintWriter(file+".txt", "UTF-8");
					    writer.println(view.t1.getCX());
					    writer.println(view.t1.getCY());
					    writer.println(view.t1.getRotation());
					    
					    writer.println(view.t2.getCX());
					    writer.println(view.t2.getCY());
					    writer.println(view.t2.getRotation());

					    writer.println(view.t3.getCX());
					    writer.println(view.t3.getCY());
					    writer.println(view.t3.getRotation());

					    writer.println(view.t4.getCX());
					    writer.println(view.t4.getCY());
					    writer.println(view.t4.getRotation());

					    writer.println(view.t5.getCX());
					    writer.println(view.t5.getCY());
					    writer.println(view.t5.getRotation());

					    writer.println(view.qdg.getCX());
					    writer.println(view.qdg.getCY());
					    writer.println(view.qdg.getRotation());

					    writer.println(view.sqr.getCX());
					    writer.println(view.sqr.getCY());
					    writer.println(view.sqr.getRotation());
					    
					    writer.println(view.a);

					    
					    writer.close();
					} catch (IOException e1) {
					   // do something
					}
				}
				}
			};
		};
		reset = new State(){
			Transition rs = new Release(BUTTON1,NOMODIFIER, ">> start"){
				public void action()
				{
					int x = 450, y = 400;
					double a = view.a;
					
					view.canvas.removeShape(view.t1.get_Shape());
					view.canvas.removeShape(view.t2.get_Shape());
					view.canvas.removeShape(view.t3.get_Shape());
					view.canvas.removeShape(view.t4.get_Shape());
					view.canvas.removeShape(view.t5.get_Shape());
					view.canvas.removeShape(view.qdg.get_Shape());
					view.canvas.removeShape(view.sqr.get_Shape());
					
					   view.t1= new Triangle(new Point2D.Double(x,y), a, 1, view.canvas);
					   view.t2= new Triangle(new Point2D.Double(x+a/4,y+a/4), a, 2, view.canvas);
					   view.t3= new Triangle(new Point2D.Double(x+a/2,y), a, 3, view.canvas);
					   view.t4= new Triangle(new Point2D.Double(x,y+a), a, 4, view.canvas);
					   view.t5= new Triangle(new Point2D.Double(x+a,y), a, 5, view.canvas);
					   view.qdg=new Quadrangle(new Point2D.Double(x,y+a/2),a,1,view.canvas);
					   view.sqr=new Square(new Point2D.Double(x+a/4,y+a/4),a,1,view.canvas);	
					
				}
			};
		};
		load = new State() {
			Transition ld = new Release(BUTTON1,NOMODIFIER, ">> start"){
				public void action(){	
					JFileChooser fileChooser = new JFileChooser();
					Component modalToComponent = null;
					if (fileChooser.showOpenDialog(modalToComponent) == JFileChooser.APPROVE_OPTION) {
					  File file = fileChooser.getSelectedFile();					  

						String[] l = new String [30];
					    int i = 1;
					    
						try(BufferedReader br = new BufferedReader(new FileReader(file))) {
						    StringBuilder sb = new StringBuilder();
						    String line = br.readLine();
						    l[0] = line;

						    while (line != null) {
						        sb.append(line);
						        sb.append(System.lineSeparator());
						        line = br.readLine();
						        l[i] = line;
						        i++;
						        	
						    }
						    String everything = sb.toString();
						    
						    view.lt1.polyline.translateBy(Double.parseDouble(l[0])-view.t1.getCX(), Double.parseDouble(l[1])-view.t1.getCY());
						    view.lt2.polyline.translateBy(Double.parseDouble(l[3])-view.t2.getCX(), Double.parseDouble(l[4])-view.t2.getCY());
						    view.lt3.polyline.translateBy(Double.parseDouble(l[6])-view.t3.getCX(), Double.parseDouble(l[7])-view.t3.getCY());
						    view.lt4.polyline.translateBy(Double.parseDouble(l[9])-view.t4.getCX(), Double.parseDouble(l[10])-view.t4.getCY());
						    view.lt5.polyline.translateBy(Double.parseDouble(l[12])-view.t5.getCX(), Double.parseDouble(l[13])-view.t5.getCY());
						    view.lqdg.polyline.translateBy(Double.parseDouble(l[15])-view.qdg.getCX(), Double.parseDouble(l[14])-view.qdg.getCY());
						    view.lsqr.polyline.translateBy(Double.parseDouble(l[18])-view.sqr.getCX(), Double.parseDouble(l[19])-view.sqr.getCY());
						    
						  //  view.t1.translateBy(10,10);
						    System.out.println("Saved X : "+Double.parseDouble(l[15]));
						    System.out.println("Current X: "+view.t1.getCX());
						    System.out.println("Translation: " + (Double.parseDouble(l[16])-view.t1.getCX()));
						    
						    view.lt1.polyline.rotateBy(Double.parseDouble(l[2]));
						    view.lt2.polyline.rotateBy(Double.parseDouble(l[5]));
						    view.lt3.polyline.rotateBy(Double.parseDouble(l[8]));
						    view.lt4.polyline.rotateBy(Double.parseDouble(l[11]));
						    view.lt5.polyline.rotateBy(Double.parseDouble(l[14]));
						    view.lqdg.polyline.rotateBy(Double.parseDouble(l[17]));
						    view.lsqr.polyline.rotateBy(Double.parseDouble(l[20]));
						    
						    view.lt1.polyline.setFillPaint(Color.LIGHT_GRAY);
						    view.lt2.polyline.setFillPaint(Color.lightGray);
						    view.lt3.polyline.setFillPaint(Color.lightGray);
						    view.lt4.polyline.setFillPaint(Color.lightGray);
						    view.lt5.polyline.setFillPaint(Color.lightGray);
						    view.lqdg.polyline.setFillPaint(Color.lightGray);
						    view.lsqr.polyline.setFillPaint(Color.lightGray);
						    
						    view.lt1.setangle(Double.parseDouble(l[2]));
						    view.lt2.setangle(Double.parseDouble(l[5]));
						    view.lt3.setangle(Double.parseDouble(l[8]));
						    view.lt4.setangle(Double.parseDouble(l[11]));
						    view.lt5.setangle(Double.parseDouble(l[14]));
						    view.lqdg.setangle(Double.parseDouble(l[17]));
						    view.lsqr.setangle(Double.parseDouble(l[20]));
						    
						    
							/*view.canvas.removeShape(view.t1.get_Shape());
							view.canvas.removeShape(view.t2.get_Shape());
							view.canvas.removeShape(view.t3.get_Shape());
							view.canvas.removeShape(view.t4.get_Shape());
							view.canvas.removeShape(view.t5.get_Shape());
							view.canvas.removeShape(view.qdg.get_Shape());
							view.canvas.removeShape(view.sqr.get_Shape());

							view.t1 = new Triangle(new Point2D.Double(Double.parseDouble(l[0]), Double.parseDouble(l[1])), Double.parseDouble(l[21]), 1, view.canvas);
							view.t2 = new Triangle(new Point2D.Double(Double.parseDouble(l[3]), Double.parseDouble(l[4])), Double.parseDouble(l[21]), 2, view.canvas);
							view.t3 = new Triangle(new Point2D.Double(Double.parseDouble(l[6]), Double.parseDouble(l[7])), Double.parseDouble(l[21]), 3, view.canvas);
							view.t4 = new Triangle(new Point2D.Double(Double.parseDouble(l[9]), Double.parseDouble(l[10])), Double.parseDouble(l[21]), 4, view.canvas);
							view.t5 = new Triangle(new Point2D.Double(Double.parseDouble(l[12]), Double.parseDouble(l[13])), Double.parseDouble(l[21]), 5, view.canvas);
							view.qdg= new Quadrangle(new Point2D.Double(Double.parseDouble(l[15]), Double.parseDouble(l[14])), Double.parseDouble(l[21]), 1, view.canvas);
							view.sqr= new Square(new Point2D.Double(Double.parseDouble(l[18]), Double.parseDouble(l[19])), Double.parseDouble(l[21]), 1, view.canvas);
						 
						    System.out.println("Saved X t1: "+Double.parseDouble(l[0]));
						    System.out.println("Saved Y t1: "+Double.parseDouble(l[1]));
*/
							
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				}
				}
			};
		};
		
	}
	
    public CExtensionalTag getMovableTag() {
	   return movableTag ;
    }
}
