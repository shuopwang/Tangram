package model;

import java.awt.geom.Point2D;

public class Vector2D {
	public double [] v = new double[2];

	public Vector2D() {
		v[0] = v[1] = 0;
	}

	public Vector2D( double x, double y ) {
		v[0] = x;
		v[1] = y;
	}
	
	
	
	public Vector2D( Point2D P ) {
		v[0] =  P.getX();
		v[1] = P.getY();
	}
	
	public Vector2D(Point2D p1,Point2D p2){
		v[0]=p1.getX()-p2.getX();
		v[1]=p1.getY()-p2.getY();
	}
	
	public double x() { return v[0]; }
	public double y() { return v[1]; }
	
	public double dot( Vector2D a, Vector2D b ) {
		return a.x()*b.x() + a.y()*b.y();
	}
}
