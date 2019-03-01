package com.splitwise.gui;

import com.splitwise.gui.custom.CJPanel;

public class DashboardPanel extends CJPanel {
	private PageHeader pageHeader;
	
	DashboardPanel() {
		init();
	}
	@Override
	public void initComponents() {
		pageHeader = new PageHeader("Dashboard");
	
		add(pageHeader);
	}

	@Override
	public void configureComponents() {
		setLayout(null);
		setOpaque(false);
	}

	@Override
	public void computeSize() {
		pageHeader.setSize(getSize().width,pageHeader.getSize().height);
	}

	@Override
	public void computePlacement() {
		pageHeader.setLocation(0,0);
	}

}
