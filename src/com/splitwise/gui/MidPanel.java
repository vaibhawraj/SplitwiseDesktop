package com.splitwise.gui;

import java.awt.Color;
import java.awt.Graphics;


import com.splitwise.gui.custom.CJPanel;
import com.splitwise.gui.custom.ShadowBorder;

public class MidPanel  extends CJPanel{

	private int borderLeft = 12;
	private int borderRight = 12;
	private DashboardPanel dashboardPanel;
	
	MidPanel() {
		init();
		showDashboardPanel();
	}
	
	@Override
	public void initComponents() {
		dashboardPanel = new DashboardPanel();
	}

	@Override
	public void configureComponents() {
		// TODO Auto-generated method stub
		setOpaque(false);
		ShadowBorder border = new ShadowBorder(0,borderLeft,0,borderRight,new Color(204,204,204),50);
		
		setBorder(border);
	}

	@Override
	public void computeSize() {
		dashboardPanel.setSize(getSize().width - borderLeft - borderRight,getSize().height);
		
	}

	@Override
	public void computePlacement() {
		dashboardPanel.setLocation(borderLeft,0);
		
	}
	
	public void showDashboardPanel() {
		this.removeAll();
		add(dashboardPanel);
	}
	
	public void showActivityPanel() {
		
	}
	
	public void showExpensePanel() {
		
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(new Color(204,204,204)); //Border Color
		g.fillRect(borderLeft, 0, 1, getSize().height);
		g.fillRect(getSize().width-borderRight-1, 0, 1, getSize().height);
		
	}

}
