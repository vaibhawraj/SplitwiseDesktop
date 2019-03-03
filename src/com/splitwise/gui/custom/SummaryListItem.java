package com.splitwise.gui.custom;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import com.splitwise.gui.theme.DefaultTheme;

public class SummaryListItem extends CJPanel {

	private JLabel personNameText;
	private JLabel amountText;
	
	private int preferredHeight = 49;
	private int lineGap = 10;
	private int paddingLeft = 15;
	
	public static int OWE = 0;
	public static int OWED = 1;
	private int whoOwe;
	private boolean isHighlighted;
	
	public SummaryListItem(String friendName, String amount, int whoOwe) {
		this.whoOwe = whoOwe;
		
		init();
		personNameText.setText(friendName);
		if(whoOwe == 0) {
			amountText.setText("you owe " + amount);
		} else {
			amountText.setText("owes you " + amount);
		}
		
		addListener();
	}
	@Override
	public void initComponents() {
		personNameText = new JLabel();
		amountText = new JLabel();
		
		add(personNameText);
		add(amountText);
	}

	@Override
	public void configureComponents() {
		personNameText.setFont(new Font("Helvetica Neue",Font.PLAIN,16));
		amountText.setFont(new Font("Helvetica Neue",Font.PLAIN,13));
		if(whoOwe == 0) {
			amountText.setForeground(DefaultTheme.getColor("NegativeForeground"));
		} else {
			amountText.setForeground(DefaultTheme.getColor("PositiveForeground"));
		}
		
	}

	@Override
	public void computeSize() {
		personNameText.setSize(personNameText.getPreferredSize());
		amountText.setSize(personNameText.getPreferredSize());
	}

	@Override
	public void computePlacement() {
		int relative_x = paddingLeft;
		int relative_y = (getSize().height/2) - (personNameText.getSize().height - lineGap/2); 
		personNameText.setLocation(relative_x, relative_y);
		relative_x = paddingLeft;
		relative_y = (getSize().height/2) + lineGap/2;
		amountText.setLocation(relative_x, relative_y);
		
	}
	
	public void addListener() {
		SummaryListItem that = this;
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {
				that.setHighlight(true);
			}			

			@Override
			public void mouseExited(MouseEvent e) {
				that.setHighlight(false);
			}
		});
	}
	
	protected void setHighlight(boolean b) {
		if(b != isHighlighted) {
			isHighlighted = b;
			this.repaint();
		}
		
	}
	
	public int getPreferredHeight() {
		return this.preferredHeight;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		if(isHighlighted) {
			g.setColor(DefaultTheme.getColor("OptionHighlightColor"));
			g.fillRect(0, 0, getSize().width, getSize().height);
		}
		super.paintChildren(g);
	}
}