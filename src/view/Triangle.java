package view;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

import fr.lri.swingstates.canvas.CPolyLine;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.Canvas;
@SuppressWarnings("serial")
public class Triangle extends CShape{
	public CPolyLine polyline;
	Point2D centre;
	Point2D first;
	Point2D second;
	Point2D third;
	public double angle;
	Color c=new Color(255,102,102);
	public Triangle(Point2D p,double a,int level,Canvas canvas){

		//System.out.println(p);
		polyline=canvas.newPolyLine(p);
		first=p;
		if (level==1)
		{
			
			second=new Point2D.Double(p.getX(),p.getY()+a/2);
			third=new Point2D.Double(p.getX()+a/2,p.getY());
			polyline.lineTo(second);
			polyline.lineTo(third);
		
		}
		if(level==2)
		{
			second=new Point2D.Double(p.getX(),p.getY()+a/2);
			third= new Point2D.Double(p.getX()+a/4,p.getY()+a/4);
			polyline.lineTo(second);
			polyline.lineTo(third);
			
		}
		if(level==3)
		{
			second=new Point2D.Double(p.getX()+a/2,p.getY());
			third=new Point2D.Double(p.getX()+a/4,p.getY()+a/4);
			polyline.lineTo(second);
			polyline.lineTo(third);
		}
		if(level==4)
		{	
			second=new Point2D.Double(p.getX()+a,p.getY());
			third=new Point2D.Double(p.getX()+a/2,p.getY()-a/2);
			polyline.lineTo(second);
			polyline.lineTo(third);
		}
		if(level==5)
		{	
			second=new Point2D.Double(p.getX(),p.getY()+a);
			third=new Point2D.Double(p.getX()-a/2,p.getY()+a/2);
			polyline.lineTo(second);
			polyline.lineTo(third);
		}
		polyline.lineTo(first);
		polyline.addTag("draggable");
		polyline.setFillPaint(c);
		System.out.println("Triangle"+level+" "+"x:"+polyline.getCenterX()+"y:"+polyline.getCenterY());
	}

	public double getCX()
	{
		return polyline.getCenterX();
	}	
	public double getCY()
	{
		return polyline.getCenterY();
	}
	public double getRotation()
	{
		return polyline.getRotation();
	}

	public CShape get_Shape(){
		return polyline;
	}
	public void setColor(Color color){
		this.c=color;
	}
	public void setangle(double a){
		this.angle=a;
	}
	
}
