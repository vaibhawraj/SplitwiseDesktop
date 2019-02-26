package com.splitwise.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	private static MainFrame instance;
	private HeaderPanel headerPanel;
	private MainContentPanel mainContentPanel;
	
	private MainFrame() {
		configureComponents();
		initComponents();
		computeSize();
		computePlacement();
	}
	
	public static MainFrame getInstance() {
		if(instance == null) {
			instance = new MainFrame();
		}
		return instance;
	}
	private void initComponents() {}
	
	public void initMainPane() {
		getContentPane().setLayout(null);
		
		//Initialize Header Panel
		headerPanel = new HeaderPanel();
		
		//Initialize Main Content Panel
		mainContentPanel = new MainContentPanel();
		
		getContentPane().add(headerPanel);
		getContentPane().add(mainContentPanel);
	}
	
	private void configureComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	private void computeSize() {
		setSize((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()), (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
		
		Dimension contentPanelDimension = getContentPane().getSize();
		
		if(headerPanel != null) {
			headerPanel.setSize(contentPanelDimension.width,headerPanel.getSize().height);
			headerPanel.computeSize();
		}
		
		if(mainContentPanel != null) {
			mainContentPanel.setSize(contentPanelDimension.width, contentPanelDimension.height - headerPanel.getSize().height);
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
