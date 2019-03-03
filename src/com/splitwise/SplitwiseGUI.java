package com.splitwise;



import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;

import com.splitwise.gui.LoginPanel;
import com.splitwise.gui.MainFrame;
import com.splitwise.splitwisesdk.SplitwiseSDK;


public class SplitwiseGUI{
	private static SplitwiseGUI instance;
    private MainFrame mainFrame;
    
    final private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
	SplitwiseSDK sdk;
	SplitwiseGUI() {}
	public void init() {
		
		instance = this;
		mainFrame = MainFrame.getInstance();
		mainFrame.showDefaultPane();
		mainFrame.setVisible(true);
		
		// Default action is to first login
		login();
	}
	
	public void login() {
		// Since login requires asynchronous call to web server
		new Thread(){
			public void run() {
				try {
					Thread.sleep(2000);
				} catch(Exception e) {
					
				}
				LOGGER.info("Checking valid access token");
				sdk = SplitwiseSDK.getInstance();
				if(!sdk.hasValidAccessToken()) {
					LOGGER.info("Not Has valid access token");
					showLoginPanel();
					
				} else {
					LOGGER.info("Has valid access token");
					grantLogin();	
				}
			}
		}.start();
	}
	
	public void showLoginPanel() {
			try {
				Thread.sleep(2000);
			} catch(Exception e) {}
			LOGGER.info("Loading login panel");
			LoginPanel lp = LoginPanel.getInstance();
			String url = sdk.getAuthorizationURL();
			lp.load(url);
			lp.setVisible(false);			
			
			mainFrame.getContentPane().add(lp);
			mainFrame.repaint();
	}
	
	public void grantLogin() {
		LOGGER.info("Granting Loging");
		// TODO show loading screen
		// TODO fetch data from the server on separate thread
		mainFrame.showMainPane();
		mainFrame.repaint();
	}
	
	public void showDashboard() {
		// Do All necessary activity before loading Dashboard
		mainFrame.showDashboard();
	}
	
	public void showAllExpenses() {
		// Do All necessary activity before loading All Expenses
		mainFrame.showAllExpenses();
	}
	
	public void showRecentActivity() {
		// Do All necessary activity before loading Recent Activity
		mainFrame.showRecentActivity();
	}
	
	public static SplitwiseGUI getInstance() {
		return instance;
	}
	public void showAddBill() {
		mainFrame.showAddBill();
	}
}
