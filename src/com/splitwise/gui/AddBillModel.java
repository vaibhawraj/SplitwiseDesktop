package com.splitwise.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;


import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;

import com.splitwise.gui.custom.CJPanel;
import com.splitwise.gui.custom.FlexibleLabel;
import com.splitwise.gui.theme.DefaultTheme;

public class AddBillModel extends CJPanel {

	private int preferredHeight = 100;
	private int preferredWidth = 350;
	
	private int headerPanelHeight = 39;
	private int paddingLeft = 10;
	
	private JPanel headerPanel;
	private JLabel headerText;
	
	private JPanel inputFieldPanel;
	private JLabel inputFieldLabel;
	private FlexibleLabel inputArea;
	
	// For Body
	private JLayeredPane body;
	private FlexibleLabel description;
	private FlexibleLabel amount;
	
	public AddBillModel() {
		init();
	}
	
	@Override
	public void initComponents() {
		headerPanel = new JPanel();
		headerPanel.setLayout(null);
		headerPanel.setOpaque(false);
		//headerPanel.setBackground(DefaultTheme.getColor("ModelHeaderBackground"));
		
		headerText = new JLabel("Add a bill");
		
		headerPanel.add(headerText);
		
		inputFieldPanel = new JPanel();
		inputFieldPanel.setLayout(null);
		inputFieldPanel.setOpaque(false);
		// For padding
		inputFieldPanel.setBorder(BorderFactory.createEmptyBorder(4,10,4,10));
		
		inputFieldLabel = new JLabel("<html>With <b>you</b> and:</html>");
		inputArea = new FlexibleLabel("");
		inputArea.setPlaceholder("Enter Name");
		inputArea.setEditable(true);
		inputArea.setRows(1);
		inputFieldPanel.add(inputFieldLabel);
		inputFieldPanel.add(inputArea);
		
		body = new JLayeredPane();
		body.setLayout(null);
		body.setOpaque(false);
		
		description = new FlexibleLabel();
		description.setPlaceholder("Enter a description");
		
		
		
		add(inputFieldPanel);
		add(headerPanel);
	}

	@Override
	public void configureComponents() {
		
		setSize(preferredWidth, preferredHeight);
		setOpaque(false);
		//setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		headerText.setFont(new Font("Helvetica Neue",Font.BOLD,18));
		headerText.setForeground(DefaultTheme.getColor("ModelHeaderForeground"));
		
	}

	@Override
	public void computeSize() {
		headerPanel.setSize(getSize().width, headerPanelHeight);
		headerText.setSize(headerText.getPreferredSize());
		
		// Input
		inputFieldLabel.setSize(inputFieldLabel.getPreferredSize());
		Insets insets = inputFieldPanel.getInsets();
		inputArea.setSize(getSize().width - insets.left - insets.right - inputFieldLabel.getSize().width - paddingLeft, inputFieldLabel.getPreferredSize().height);
		inputFieldPanel.setSize(getSize().width, inputFieldLabel.getPreferredSize().height + inputFieldPanel.getInsets().bottom + inputFieldPanel.getInsets().top);
		
		// Body Width & Height
		body.setSize(getSize().width, getPreferredHeight(body));
	}

	@Override
	public void computePlacement() {
		headerPanel.setLocation(0,0);
		headerText.setLocation(paddingLeft, (headerPanel.getSize().height - headerText.getSize().height)/2);
		inputFieldPanel.setLocation(0,headerPanel.getSize().height);
		Insets insets = inputFieldPanel.getInsets();
		inputFieldLabel.setLocation(insets.left, insets.top);
		inputArea.setLocation(insets.left + inputFieldLabel.getSize().width + paddingLeft, insets.top);
		
		body.setLocation(0, inputFieldPanel.getY() + inputFieldPanel.getSize().height);
		preferredHeight = body.getY() + body.getSize().height;
	}
	
	public int getPreferredHeight() {
		return this.preferredHeight;
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(preferredWidth, preferredHeight);
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRoundRect(0, 0, getSize().width, getSize().height, 20, 20);
		g.setColor(DefaultTheme.getColor("ModelHeaderBackground"));
		g.fillArc(0, 0, 20, 20, 90, 90);
		g.fillArc(getSize().width - 20, 0, 20, 20, 0, 90);
		g.fillRect(10, 0, getSize().width - 20, 10);
		g.fillRect(0, 10, getSize().width, headerPanel.getSize().height - 10);
		
		// Border after inputFieldPanel
		g.setColor(DefaultTheme.getColor("PageHeaderBorder"));
		int y = inputFieldPanel.getY() + inputFieldPanel.getSize().height;
		g.drawLine(0, y, getSize().width, y);
		
		super.paint(g);
	}
	
	private int getPreferredHeight(Container c) {
		int height = 0;
		for(Component comp : c.getComponents()) {
			if(comp.isVisible()) {
				height = Math.max(height, comp.getY() + comp.getSize().height);
			}
		}
		return height;
	}

}
