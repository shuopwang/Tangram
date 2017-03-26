package model;

import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.transitions.PressOnTag;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import view.Main_Interface;
import view.View;

public class SM_main extends CStateMachine{
	Main_Interface mif;
	State hello;
	View main_view; 
	public SM_main(Main_Interface themif){
		this.mif=themif;
		
		hello= new State(){
			Transition ck= new PressOnTag("d",BUTTON1){
				public void action(){
						//mif.flag=true;
						main_view= new View("Tangram version0.5",600,600,true) ;
						mif.dispose();
				}
			};
			Transition ck2= new PressOnTag("p",BUTTON1){
				public void action(){
					main_view= new View("Tangram version0.5",600,600,false) ;
					mif.dispose();
				}
			};
			
			Transition ck3= new PressOnTag("q",BUTTON1){
				public void action(){
					System.exit(0);
					
				}
			};
		};
	}
}
