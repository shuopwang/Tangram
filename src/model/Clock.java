package model;

import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.transitions.TimeOut;
import view.View;

public class Clock extends CStateMachine{
	View view;
	State change_time;
	
	public Clock(View view){
		this.view=view;
		change_time = new State(){
			TimeOut change = new TimeOut(){
				public void action(){
					String t=view.temp.getText();
					//System.out.println(t);
					int ot=Integer.parseInt(t);
					String nt = Integer.toString(ot+1);
					view.temp.setText(nt);
					if (view.control==false)
						{
							view.temp.setText("0");
							view.control=true;
						}
				}
			};
		};
	}
}
