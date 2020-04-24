package fr.CYPT.LifeACreate;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import fr.theshark34.openauth.AuthenticationException;
import fr.theshark34.openlauncherlib.launcher.util.UsernameSaver;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredBar;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

public class LauncherPanel extends JPanel implements SwingerEventListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Image background = Swinger.getResource("bg.png");
	
	private UsernameSaver saver = new UsernameSaver(main.SC_INFOS);
	
	
	
	private JTextField usernameField = new JTextField(saver.getUsername(""));
	private JPasswordField passwordField = new JPasswordField(saver.getUsername(""));
	/*private JLabel Pseudo = new JLabel("Pseudo : ");*/
	private STexturedButton playButton = new STexturedButton(Swinger.getResource("play.png"));
	private STexturedButton quit = new STexturedButton(Swinger.getResource("quitter.png"));
	
	private SColoredBar progressBar = new SColoredBar(new Color(255, 255, 255, 15));
	
	private JLabel infoLabel = new JLabel("Cliquer Pour Jouer", SwingConstants.CENTER);
	
	
	
	public LauncherPanel() {
		this.setLayout(null);
		
		usernameField.setBounds(665, 350, 961, 35);
		usernameField.setBackground(null);
		
		playButton.setBounds(665, 450);
		playButton.setSize(200, 100);
		playButton.addEventListener(this);
		
		quit.setSize(30,20);
		quit.setBounds(900, 0);
		quit.addEventListener(this);
		
		
		
		progressBar.setBounds(12, 593, 961,20);
		
		infoLabel.setBounds(12, 560, 961,25);
		
		this.add(usernameField);
		this.add(progressBar);
		this.add(quit);
		this.add(playButton);
		this.add(infoLabel);
		
		
	}
	
	public void onEvent(SwingerEvent e) {
		if(e.getSource() == playButton) {
			setFieldEnable(false);
			
			if(usernameField.getText().replaceAll(" ", " ").length() == 0) {
				JOptionPane.showMessageDialog(this, "tu vas être bannis", "Erreur", JOptionPane.ERROR_MESSAGE);
				setFieldEnable(true);
				return;
			}
			
			Thread t = new Thread() {
				@Override
				public void run(){
					setFieldEnable(false);
					try {
						main.auth(usernameField.getText(), passwordField.getText());
					}catch (AuthenticationException e) {
						JOptionPane.showMessageDialog(LauncherPanel.this, "Erreur d'authentification mc \n impossible de se connecter à : "+e.getErrorModel().getErrorMessage(),"Erreur", JOptionPane.ERROR_MESSAGE);
						setFieldEnable(true);
						return;
					}
					
					try {
						main.update();
					}catch (Exception e) {
						main.interruptThread();
						JOptionPane.showMessageDialog(LauncherPanel.this, "Erreur d'authentification mc \n impossible de télécharger les fichier : "+e,"Erreur", JOptionPane.ERROR_MESSAGE);						
						setFieldEnable(true);
						return;
					}
					
					try {
						main.launch();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(LauncherPanel.this, "Impossible de lancer le jeu","Erreur", JOptionPane.ERROR_MESSAGE);						
						setFieldEnable(true);
						return;
					}
					System.out.println("");
				}
			};
			
			t.start();
			
			
			
			System.out.println("Connected");
		}
		
		else if(e.getSource() == quit) {
			try {main.interruptThread();}catch(Exception error){}
			System.exit(0);
			
		}
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0,975, 600, this);
	}
	
	public void setFieldEnable(boolean enabled) {
		usernameField.setEnabled(enabled);
		passwordField.setEnabled(true);
		playButton.setEnabled(true);
	}
	
	public SColoredBar getProgressBar() {
		return progressBar;
	}
	public void setInfoText(String text) {
		infoLabel.setText(text);
	}
	
}
