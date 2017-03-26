package view;

import java.awt.Color;
import java.awt.geom.Point2D;

import fr.lri.swingstates.canvas.CPolyLine;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.Canvas;

public class Square extends CShape{
	public CPolyLine polyline;
	public double angle;
	Color c=new Color(255,102,102);
	
	public Square(Point2D p,double a,int level,Canvas canvas){
		polyline=canvas.newPolyLine(p);
		
		polyline.lineTo(p.getX()+a/4,p.getY()+a/4);
		polyline.lineTo(p.getX()+a/2,p.getY());
		polyline.lineTo(p.getX()+a/4,p.getY()-a/4);
		polyline.lineTo(p.getX(),p.getY());
		
		polyline.addTag("draggable");
		if (level>0)
		polyline.setFillPaint(c);
	
		System.out.println("Square "+"x:"+polyline.getCenterX()+"y:"+polyline.getCenterY());

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
	public CShape get_Shape()
	{
		return polyline;
		
	}
	public void setColor(Color color){
		this.c=color;
	}
	public void setangle(double a){
		this.angle=a;
	}
}
