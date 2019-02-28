package com.splitwise.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import java.util.logging.*;

public class MainFrame extends JFrame {
	private static MainFrame instance;
	private HeaderPanel headerPanel;
	private MainContentPanel mainContentPanel;
	
	final private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private MainFrame() {
		configureComponents();
		initComponents();
		
		LOGGER.setLevel(Level.FINEST);
	}
	
	public static MainFrame getInstance() {
		if(instance == null) {
			instance = new MainFrame();
		}
		return instance;
	}
	private void initComponents() {}
	
	public void initMainPane() {
		LOGGER.info("Initializing Main Page");
		getContentPane().setLayout(null);
		
		//Initialize Header Panel
		headerPanel = new HeaderPanel();
		
		//Initialize Main Content Panel
		mainContentPanel = new MainContentPanel();
		
		computeSize();
		computePlacement();
		
		getContentPane().add(headerPanel);
		getContentPane().add(mainContentPanel);
	}
	
	private void configureComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()), (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
	}
	private void computeSize() {
		
		Dimension contentPanelDimension = getContentPane().getSize();
		LOGGER.finest("Content Panel size " + contentPanelDimension);
		if(headerPanel != null) {
			headerPanel.setSize(contentPanelDimension.width,headerPanel.getSize().height);
			LOGGER.finest("Header Panel size " + headerPanel.getSize());
			headerPanel.computeSize();
		}
		
		if(mainContentPanel != null) {
			mainContentPanel.setSize(contentPanelDimension.width, contentPanelDimension.height - headerPanel.getSize().height);
			LOGGER.finest("Main Content Panel size " + mainContentPanel.getSize());
			mainContentPanel.computeSize();
		}
	}
	
	private void computePlacement() {
		if(headerPanel != null) {
			headerPanel.setLocation(0, 0);
			headerPanel.computePlacement();
		}
		
		if(mainContentPanel != null) {
			mainContentPanel.setLocation(0, headerPanel.getSize().height);
			mainContentPanel.computePlacement();
		}
	}
}
