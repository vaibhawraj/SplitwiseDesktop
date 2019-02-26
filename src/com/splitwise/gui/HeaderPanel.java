package com.splitwise.gui;

import javax.swing.JPanel;

import com.splitwise.gui.custom.PanelInterface;
import com.splitwise.gui.theme.DefaultTheme;

public class HeaderPanel extends JPanel implements PanelInterface {

	private static int height = 50;
	
	@Override
	public void initComponents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configureComponents() {
		// TODO Auto-generated method stub
		setBackground(DefaultTheme.getColor("headerPanelBackground"));
		setSize(getSize().width,height);
		
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
