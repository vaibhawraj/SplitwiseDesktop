package com.splitwise.gui;

import javax.swing.JPanel;

import com.splitwise.gui.custom.CJPanel;



public class RightPanel  extends CJPanel{

	private int width = 200;
	private SummaryPanel summary;
	RightPanel() {
		init();
	}
	
	@Override
	public void initComponents() {
		// TODO Auto-generated method stub
		summary = new SummaryPanel();
		
	}

	@Override
	public void configureComponents() {
		// TODO Auto-generated method stub
		setSize(width,getSize().height);
		setLayout(null);
		setOpaque(false);
	}
	
	public void showGroupSummary() {
		
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
