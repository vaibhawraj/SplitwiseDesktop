import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import splitwisesdk.SplitwiseSDK;

public class SplitwiseGUI{
    
	SplitwiseSDK sdk;
	SplitwiseGUI() {
		sdk = SplitwiseSDK.getInstance();
	}
	public void init() {
		login();
	}
	
	public void login() {
		JFrame main = new JFrame();
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setSize((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()), (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
		JFXPanel jfxPanel = new JFXPanel();
		main.add(jfxPanel);

		// Creation of scene and future interactions with JFXPanel
		// should take place on the JavaFX Application Thread
		Platform.runLater(() -> {
		    WebView webView = new WebView();
		    jfxPanel.setScene(new Scene(webView));
		    String url = sdk.getAuthorizationURL();
		    webView.getEngine().setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
		    webView.getEngine().load(url);
		    webView.getEngine().getLoadWorker().stateProperty().addListener(
		    	    new ChangeListener() {
		    	        @Override
		    	        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
		    	            String location = webView.getEngine().getLocation();
		    	            //System.out.println(oldValue + " " + newValue + " " + location);
		    	            if(newValue == Worker.State.FAILED && location.startsWith("http://localhost")) {
		    	            	// Remove browser
		    	            	main.remove(jfxPanel);
		    	            	main.repaint();
		    	            	
		    	            	// Get Oauth Verifier
		    	            	String oauth_verifier = location.split("oauth_verifier=")[1];
		    	            	sdk.setOauthVerifier(oauth_verifier);
		    	            	
		    	            	// Get Access Token
		    	            	System.out.println("Following are your access Token. You don't need to login again. Just use following tokens.\n"
		    	            			+ "Copy oauth token and oauth_token_secret and Paste it at \n"
		    	            			+ "splitwisesdk.SplitwiseSDK.java in main method, in place of oauth_access_token and oauth_access_token_secret.");
		    	            	sdk.getAccessToken();
		    	            	JLabel lbl = new JLabel("oauth_access_token");
		    	            	JTextField jtl = new JTextField();
		    	            	jtl.setText(sdk.getOauthToken());
		    	            	
		    	            	jtl.setSize(jtl.getPreferredSize());
		    	            	lbl.setSize(lbl.getPreferredSize());
		    	            	lbl.setLocation(10, 10);
		    	            	jtl.setLocation(lbl.getPreferredSize().width + 10 + 10, 10);
		    	            	
		    	            	JLabel lbl2 = new JLabel("oauth_access_token_secret");
		    	            	JTextField jtl2 = new JTextField();
		    	            	jtl2.setText(sdk.getOauthTokenSecret());
		    	            	
		    	            	jtl2.setSize(jtl2.getPreferredSize());
		    	            	lbl2.setSize(lbl2.getPreferredSize());
		    	            	lbl2.setLocation(10, 40);
		    	            	jtl2.setLocation(lbl2.getPreferredSize().width + 10 + 10, 40);
		    	            	main.getContentPane().setLayout(null);
		    	            	main.getContentPane().add(lbl);
		    	            	main.getContentPane().add(jtl);
		    	            	main.getContentPane().add(lbl2);
		    	            	main.getContentPane().add(jtl2);
		    	            	main.repaint();
		    	            }
		    	            if (newValue == Worker.State.SUCCEEDED) {
		    	                //document finished loading
		    	            }
		    	        }
		    	    });
		});
		
		main.show();
		
	}
}
