package com.splitwise.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.splitwise.gui.custom.CustomImage;
import com.splitwise.gui.theme.DefaultTheme;

import java.util.logging.*;

public class MainFrame extends JFrame implements ComponentListener{
	private static MainFrame instance;
	private HeaderPanel headerPanel;
	private MainContentPanel mainContentPanel;
	private JLabel splitwiseLogo;
	private CustomImage splitwiseLogoImage;
	private JPanel defaultPanel;
	private LayoutManager defaultLayoutManager;
	private JLayeredPane layeredPane;
	
	private String splitwiseLogoFilename = "assets/SplitwiseLogo.png";
	
	final private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public MainFrame() {
		configureComponents();
		initComponents();
		computeSize();
		computePlacement();
		
		//LOGGER.finest("Size of Content Pane" + getContentPane().getSize());
		//LOGGER.finest("Size of Default Pane" + defaultPanel.getSize());
		//LOGGER.setLevel(Level.FINEST);
	}
	
	public static MainFrame getInstance() {
		if(instance == null) {
			instance = new MainFrame();
		}
		return instance;
	}
	private void initComponents() {
		layeredPane = getLayeredPane();
		
		defaultPanel = new JPanel();
		defaultPanel.setLayout(null);
		defaultPanel.setBackground(DefaultTheme.getColor("mainFrameBackground"));
		
		splitwiseLogoImage = new CustomImage(splitwiseLogoFilename);
		splitwiseLogo = new JLabel(splitwiseLogoImage.setSize(200,200).getImageIcon());
		
		defaultPanel.add(splitwiseLogo);
		computeSize();
		
		//getContentPane().setLayout(null);
		//getContentPane().add(defaultPanel);
		
		addComponentListener(this);
	}
	
	public void initMainPane() {
		LOGGER.info("Initializing Main Page");
		getContentPane().setLayout(null);
		
		//Initialize Header Panel
		headerPanel = new HeaderPanel();
		
		//Initialize Main Content Panel
		mainContentPanel = new MainContentPanel();
		
		computeSize();
		computePlacement();
		
		revalidate();
	}
	
	public void showMainPane() {
		if(headerPanel == null && mainContentPanel == null) {
			initMainPane();
		}
		getContentPane().removeAll();
		getContentPane().add(headerPanel);
		getContentPane().add(mainContentPanel);
		
		showAllExpenses();
	}
	
	public void showDefaultPane() {
		getContentPane().removeAll();
		getContentPane().setLayout(null);
		getContentPane().add(defaultPanel);
	}
	
	public void showDashboard() {
		this.mainContentPanel.showDashboard();
	}
	public void showAllExpenses() {
		this.mainContentPanel.showAllExpenses();
		repaint();
	}
	
	public void showRecentActivity() {
		this.mainContentPanel.showRecentActivity();
		repaint();
	}
	public LayoutManager getDefaultLayoutManager() {
		return this.defaultLayoutManager;
	}
	
	private void configureComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()), (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
		getContentPane().setBackground(DefaultTheme.getColor("mainFrameBackground"));
		defaultLayoutManager = getContentPane().getLayout();
	}
	private void computeSize() {
		
		getContentPane().setSize(getSize());
		Dimension contentPanelDimension = getContentPane().getSize();
		
		if(defaultPanel != null) {
			defaultPanel.setSize(contentPanelDimension);
			splitwiseLogo.setSize(200,200);
		}
		
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
		
		if(defaultPanel != null) {
			defaultPanel.setLocation(0, 0);
			splitwiseLogo.setLocation(
					(defaultPanel.getSize().width - splitwiseLogo.getSize().width)/2,
					(defaultPanel.getSize().height - splitwiseLogo.getSize().height)/2
					);
		}
		
		
		if(headerPanel != null) {
			headerPanel.setLocation(0, 0);
			headerPanel.computePlacement();
		}
		
		if(mainContentPanel != null) {
			mainContentPanel.setLocation(0, headerPanel.getSize().height);
			mainContentPanel.computePlacement();
		}
	}
	
	/*public void paint(Graphics g) {
		//computeSize();
		//computePlacement();
		super.paint(g);
		LOGGER.info("Main Frame Paint event triggered");
	}*/

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		LOGGER.info("Main Frame resized event triggered");
		LOGGER.info("Content Pane Size" + getContentPane().getSize());
		getContentPane().revalidate();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
}
