package com.splitwise.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import com.splitwise.gui.custom.CJPanel;
import com.splitwise.gui.custom.ShadowBorder;

import javafx.scene.effect.DropShadow;

public class MidPanel  extends CJPanel{

	private int borderLeft = 12;
	private int borderRight = 12;
	MidPanel() {
		configureComponents();
	}
	
	@Override
	public void initComponents() {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void computePlacement() {
		// TODO Auto-generated method stub
		
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(new Color(204,204,204)); //Border Color
		g.fillRect(borderLeft, 0, 1, getSize().height);
		g.fillRect(getSize().width-borderRight-1, 0, 1, getSize().height);
		
	}

}
