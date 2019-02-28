package com.splitwise.gui;

import javax.swing.JPanel;

import com.splitwise.gui.custom.PanelInterface;
import com.splitwise.gui.theme.DefaultTheme;

public class MainContentPanel extends JPanel implements PanelInterface {

	MainContentPanel() {
		configureComponents();
	}
	@Override
	public void initComponents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configureComponents() {
		setLayout(null);
		setBackground(DefaultTheme.getColor("mainContentPanelBackground"));
		
	}

	@Override
	public void computeSize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void computePlacement() {
		// TODO Auto-generated method stub
		
	}

}
