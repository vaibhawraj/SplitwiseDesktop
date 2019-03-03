package com.splitwise.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;

import com.splitwise.gui.custom.CJPanel;

public class AddBillModel extends CJPanel {

	private int preferredHeight = 100;
	private int preferredWidth = 350;
	
	public AddBillModel() {
		init();
	}
	
	@Override
	public void initComponents() {
		// TODO Auto-generated method stub
		setSize(preferredWidth, preferredHeight);
	}

	@Override
	public void configureComponents() {
		//setBackground(Color.white);
		setOpaque(false);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
	}

	@Override
	public void computeSize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void computePlacement() {
		// TODO Auto-generated method stub
		
	}
	
	public int getPreferredHeight() {
		return this.preferredHeight;
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(preferredWidth, preferredHeight);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.WHITE);
		g.fillRoundRect(0, 0, getSize().width, getSize().height, 10, 10);
		super.paintChildren(g);
	}

}
