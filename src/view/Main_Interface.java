package view;
import javax.swing.JButton;
import javax.swing.JFrame;

import fr.lri.swingstates.canvas.CImage;
import fr.lri.swingstates.canvas.CWidget;
import fr.lri.swingstates.canvas.Canvas;
import model.SM_main;

public class Main_Interface extends JFrame{
	 /**
	 * In the version final
	 * I try to figure out the way(the task list unfinished):
	 *  make the model 
	 *  create the position win
	 */
	private static final long serialVersionUID = 3943485003920376714L;
	@SuppressWarnings("unused")
	public Canvas canvas;
	
	SM_main smm;
	
	String background_path="image/background_real.jpg";
	CImage background;
	
	JButton quit=new JButton("QUIT");
	public CWidget quit_button;
	
	JButton develop=new JButton("DEVELOPER");
	public CWidget develop_button;
	
	JButton player= new JButton("PLAYER");
	public CWidget player_button;
	
	 public Main_Interface(){
		 this.setSize(600,600);
		 this.setLocation(300,70);
		 this.setUndecorated(true);
		 
		 canvas = new Canvas(600, 600) ;
		 getContentPane().add(canvas) ;
		 
		 background=canvas.newImage(0, 0, background_path);
		 background.aboveAll();
		 
		 develop_button=canvas.newWidget(develop, 400, 230, 100, 60);
		 develop_button.addTag("d");
		 player_button=canvas.newWidget(player, 400, 350, 100, 60);
		 player_button.addTag("p");
		 quit_button=canvas.newWidget(quit, 400, 470,100,60);
		 quit_button.addTag("q");
		 
		 smm=new SM_main(this);
		 smm.attachTo(canvas);
		 setVisible(true) ;
	 }
	public static void main(String[] args) {
		   //View main_view = new View("Tangram version0.5",600,600,false) ;
			Main_Interface m_interface = new Main_Interface();
	    }
}
