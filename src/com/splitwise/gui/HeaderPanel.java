package com.splitwise.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.splitwise.SplitwiseCore;
import com.splitwise.gui.custom.CJPanel;
import com.splitwise.gui.custom.CustomImage;
import com.splitwise.gui.theme.DefaultTheme;

public class HeaderPanel extends CJPanel {

	private int height = 30;
	private String splitwiseLogoFilename = "assets/Splitwise.png";
	private int contentWidth = 980;
	
	// Fonts
	private Font headerFont = new Font("Helvetica Neue",Font.PLAIN, 14);
	
	// Border
	private int bottomBorderPixel = 4; //Others are 0 for now
	
	// Components
	private JLabel headerText;
	private JLabel usernameLabel;
	private CustomImage image;
	
	HeaderPanel() {
		init();
	}
	
	@Override
	public void initComponents() {
		image = new CustomImage(splitwiseLogoFilename);
		headerText = new JLabel(image.getImageIcon());
		usernameLabel = new JLabel("");
		usernameLabel.setText(SplitwiseCore.getInstance().getCurrentUser().getName());
		
		usernameLabel.setForeground(DefaultTheme.getColor("headerPanelForeground"));
		usernameLabel.setFont(headerFont);
		computeSize();
		computePlacement();
		
		add(headerText);
		add(usernameLabel);
	}

	@Override
	public void configureComponents() {
		// TODO Auto-generated method stub
		setLayout(null);
		setBackground(DefaultTheme.getColor("headerPanelBackground"));
		setSize(getSize().width,this.height);
		setOpaque(true);
		
		// Configure Border
		Border matteBorder = BorderFactory.createMatteBorder(0,0,bottomBorderPixel,1,DefaultTheme.getColor("headerPanelBorderColor"));
		setBorder(matteBorder);
		
		
	}

	@Override
	public void computeSize() {
		
		headerText.setSize(165,12);
		headerText.setIcon(image.setSize(headerText.getSize()).getImageIcon());
		
		usernameLabel.setSize(usernameLabel.getPreferredSize());
	}

	@Override
	public void computePlacement() {
		
		int leftX = (getSize().width - contentWidth)/2;
		
		int relativeX = leftX + 10;
		int relativeY = (getSize().height - headerText.getSize().height)/2;
		headerText.setLocation(relativeX, relativeY);
		
		relativeX = leftX + contentWidth - usernameLabel.getSize().width;
		relativeY = (getSize().height - usernameLabel.getSize().height)/2;
		usernameLabel.setLocation(relativeX, relativeY);
		
	}

}
