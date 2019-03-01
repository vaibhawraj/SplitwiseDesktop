package com.splitwise.gui;

import javax.swing.JPanel;

import com.splitwise.gui.custom.CJPanel;
import com.splitwise.gui.theme.DefaultTheme;

public class MainContentPanel extends CJPanel {
	private RightPanel rightPanel;
	private LeftPanel leftPanel;
	private MidPanel midPanel;
	
	private int contentWidth = 980;
	
	MainContentPanel() {
		configureComponents();
		initComponents();
	}
	@Override
	public void initComponents() {
		rightPanel = new RightPanel();
		leftPanel = new LeftPanel();
		midPanel = new MidPanel();
		
		add(rightPanel);
		add(midPanel);
		add(leftPanel);
	}

	@Override
	public void configureComponents() {
		setLayout(null);
		setBackground(DefaultTheme.getColor("mainContentPanelBackground"));
	}

	@Override
	public void computeSize() {
		// TODO Auto-generated method stub
		midPanel.setSize(
				contentWidth 
				- leftPanel.getSize().width
				- rightPanel.getSize().width,
				getSize().height
				);
		leftPanel.setSize(leftPanel.getSize().width, getSize().height);
		leftPanel.computeSize();
		rightPanel.setSize(rightPanel.getSize().width, getSize().height);
	}

	@Override
	public void computePlacement() {
		// TODO Auto-generated method stub
		midPanel.setLocation((getSize().width - midPanel.getSize().width)/2,0);
		leftPanel.setLocation(midPanel.getLocation().x - leftPanel.getSize().width,0);
		leftPanel.computePlacement();
		rightPanel.setLocation(midPanel.getLocation().x + midPanel.getSize().width,0);
		
	}

}
