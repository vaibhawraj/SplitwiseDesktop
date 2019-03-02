package com.splitwise.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.splitwise.gui.custom.CJPanel;
import com.splitwise.gui.custom.CustomButton;
import com.splitwise.gui.custom.CustomImage;
import com.splitwise.gui.theme.DefaultTheme;

public class RecentActivityPanel extends CJPanel {
	private PageHeader pageHeader;
	private JPanel defaultPanel;
	private JLabel defaultPanelSubText;
	
	//DefaultPanel
	private Insets defaultPanelPadding = new Insets(20, 15, 10, 15);
	private int defaultPanelHeight = 294;
	
	RecentActivityPanel() {
		init();
		showDefaultPanel();
	}
	@Override
	public void initComponents() {
		pageHeader = new PageHeader("Recent Activity");
		defaultPanel = new JPanel();
		
		initDefaultPanel();
		
		add(defaultPanel);
		add(pageHeader);
	}
	
	public void initDefaultPanel() {
		defaultPanel.setLayout(null);
		defaultPanel.setOpaque(false);
		
		defaultPanelSubText = new JLabel("There is no activity in your account yet. Try adding an expense!");
		defaultPanelSubText.setHorizontalAlignment(SwingConstants.LEFT);
		
		defaultPanelSubText.setVerticalAlignment(SwingConstants.CENTER);
		defaultPanelSubText.setFont(new Font("Helvetica Neue",Font.PLAIN,16));
		defaultPanelSubText.setForeground(DefaultTheme.getColor("SecondaryForeground"));
		
		defaultPanelSubText.setSize(defaultPanelSubText.getPreferredSize());
		
		defaultPanel.add(defaultPanelSubText);
	}
	
	public void showDefaultPanel() {
		defaultPanel.setVisible(true);
	}
	
	public void hideDefaultPanel() {
		defaultPanel.setVisible(false);
	}

	@Override
	public void configureComponents() {
		setLayout(null);
		setOpaque(false);
	}

	@Override
	public void computeSize() {
		pageHeader.setSize(getSize().width,pageHeader.getSize().height);
		
		defaultPanel.setSize(getSize().width,defaultPanelHeight);
		defaultPanelSubText.setSize(defaultPanelSubText.getPreferredSize());
	}

	@Override
	public void computePlacement() {
		pageHeader.setLocation(0,0);
		defaultPanel.setLocation(0,pageHeader.getSize().height);
		defaultPanelSubText.setLocation(defaultPanelPadding.left, defaultPanelPadding.top);
	}

}