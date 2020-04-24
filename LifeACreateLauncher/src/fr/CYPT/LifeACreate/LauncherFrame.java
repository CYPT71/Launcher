package fr.CYPT.LifeACreate;

import javax.swing.JFrame;

import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.util.WindowMover;

public class LauncherFrame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static LauncherFrame instance;
	private LauncherPanel launcherPanel;
	
	public LauncherFrame() {
		this.setTitle("LifeACreate");
		this.setSize(975, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.setIconImage(Swinger.getResource("logo.png"));
		this.setContentPane(launcherPanel = new LauncherPanel());
		
		WindowMover mover = new WindowMover(this);
		this.addMouseListener(mover);
		this.addMouseMotionListener(mover);
		
		this.setVisible(true);
	}
	

	public static void main(String[] args) {
		Swinger.setSystemLookNFeel();
		Swinger.setResourcePath("/fr/CYPT/LifeACreate/menu");
		instance = new LauncherFrame();
	}
	
	public static LauncherFrame getInstance() {
		return instance;
		
	}
	
	public LauncherPanel getLauncherPanel() {
		return this.launcherPanel;
	}
}