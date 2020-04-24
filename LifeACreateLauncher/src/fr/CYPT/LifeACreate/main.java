package fr.CYPT.LifeACreate;

import java.io.File;
import java.io.IOException;

import org.apache.http.auth.AUTH;

import fr.theshark34.openauth.AuthPoints;
import fr.theshark34.openauth.AuthenticationException;
import fr.theshark34.openauth.Authenticator;
import fr.theshark34.openauth.model.AuthAgent;
import fr.theshark34.openauth.model.response.AuthResponse;
import fr.theshark34.openlauncherlib.launcher.AuthInfos;
import fr.theshark34.openlauncherlib.launcher.GameFolder;
import fr.theshark34.openlauncherlib.launcher.GameInfos;
import fr.theshark34.openlauncherlib.launcher.GameLauncher;
import fr.theshark34.openlauncherlib.launcher.GameTweak;
import fr.theshark34.openlauncherlib.launcher.GameType;
import fr.theshark34.openlauncherlib.launcher.GameVersion;
import fr.theshark34.supdate.BarAPI;
import fr.theshark34.supdate.SUpdate;
import fr.theshark34.swinger.Swinger;

public class main  {
	
	public static final GameVersion SC_VERSION = new GameVersion("1.7.10", GameType.V1_7_10);
	public static final GameInfos SC_INFOS = new GameInfos("LifeACreate", SC_VERSION, true, new GameTweak[] {GameTweak.FORGE});
	public static final File SC_DIR = new File(".LifeACreate");
	
	private static Thread updateThread;
	private static AuthInfos authInfos;
	
	public static void auth(String username, String password) throws AuthenticationException
	{
		/*Authenticator authenticator = new Authenticator(Authenticator.MOJANG_AUTH_URL, AuthPoints.NORMAL_AUTH_POINTS);
		AuthResponse reponse = authenticator.authenticate(AuthAgent.MINECRAFT, username, password, "");*/
		authInfos = new AuthInfos(username, "sry","nope");
	}
	
	public static void update() throws Exception{
		SUpdate su = new SUpdate("https://private.mtxserv.com/", SC_DIR);
		
		updateThread = new Thread() {
			private int val;
			private int max;
			
			@Override
			public void run() {
				
				if(BarAPI.getNumberOfFileToDownload() == BarAPI.getNumberOfDownloadedFiles()) {
					LauncherFrame.getInstance().getLauncherPanel().setInfoText("Verificate File");
				}
				else {
					while(!this.interrupted()) {
				
						val = (int) (BarAPI.getNumberOfTotalDownloadedBytes()/1000);		
						max = (int) (BarAPI.getNumberOfTotalBytesToDownload()/1000);
						
						LauncherFrame.getInstance().getLauncherPanel().getProgressBar().setMaximum(max);
						LauncherFrame.getInstance().getLauncherPanel().getProgressBar().setValue(val);
						
						LauncherFrame.getInstance().getLauncherPanel().setInfoText("Telechargement des fichiers "+
						BarAPI.getNumberOfDownloadedFiles()+ "/" + BarAPI.getNumberOfFileToDownload()+
						Swinger.percentage(val, max)+"%");
					}
				}
			}
		};
		updateThread.start();
		su.start();
		updateThread.interrupt();
		
	}
	
	public static void launch() throws IOException{
		GameLauncher gameLauncher = new GameLauncher(SC_INFOS, GameFolder.BASIC, authInfos);
		Process p = gameLauncher.launch();
		try {
			Thread.sleep(5000L);
		} catch (InterruptedException e) {}
		
		LauncherFrame.getInstance();
		LauncherFrame.getInstance().setVisible(false);
		
		try {
			p.waitFor();
		} catch (InterruptedException e) {}
		
		System.exit(0);
	}
	
	public static void interruptThread() {
		updateThread.interrupt();
	}
}
