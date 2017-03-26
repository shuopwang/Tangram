package control;

import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import view.View;

public class Mouseaction implements MouseInputListener{
	@Override
	
	public void mouseClicked(MouseEvent e) {
		
		JPanel p = (JPanel) e.getSource();
		Color c = p.getBackground();
		c = JColorChooser.showDialog(null, "Select a color", c);
		p.setBackground(c);
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
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
