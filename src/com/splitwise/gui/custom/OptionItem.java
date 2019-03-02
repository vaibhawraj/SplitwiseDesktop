package com.splitwise.gui.custom;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.splitwise.gui.LeftPanel;
import com.splitwise.gui.MainFrame;
import com.splitwise.gui.theme.DefaultTheme;

public class OptionItem extends CJPanel{
	private String text;
	
	// Placing
	private int paddingLeft = 8;
	private int paddingTop = 3;
	private int paddingRight = 8;
	private int paddingBottom = 2;
	private int highlighterLeft = 6;
	private int height = 24;
	private boolean isSelected;
	private boolean isHighlighted;
	private boolean isSeparator;
	private boolean isMessage;
	private int fontSize;
	
	// Components
	private JLabel textLabel;
	private JLabel icon;
	private Font font;
	
	// Callback
	private Callback callback;
	public static String MESSAGE = "MESSAGE";
	public static String HEADER = "HEADER";
	public static String OPTION = "OPTION";
	
	public OptionItem(String text, String type) {
		this.text = text;
		fontSize = 16;
		if(type == MESSAGE) {
			this.isMessage = true;
			fontSize = 11;
			height = 30;
		} else if(type == HEADER) {
			this.isSeparator = true;
			height = 20;
			fontSize = 11;
		} 
		this.font = new Font("Helvetica Neue",Font.PLAIN, fontSize);
		this.init();
		if(type == OPTION) {
			addListener();
		}
		
	}
	
	public OptionItem(String text) {
		this(text, OPTION);
	}
	
	public OptionItem(String text, Callback callback) {
		this(text);
		this.callback = callback;
	}

	@Override
	public void initComponents() {
		textLabel = new JLabel(text);
		
		textLabel.setForeground(DefaultTheme.getColor("OptionForeground"));
		if(isSeparator) {
			textLabel.setText(textLabel.getText().toUpperCase());
			textLabel.setForeground(DefaultTheme.getColor("OptionSeparatorForeground"));
		}
		if(isMessage) {
			textLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		textLabel.setFont(font);
		add(textLabel);
	}

	@Override
	public void configureComponents() {
		setLayout(null);
		setBackground(Color.WHITE);
		setSize(getSize().width, height);
	}

	@Override
	public void computeSize() {
		textLabel.setSize(getSize().width - paddingLeft - paddingRight - highlighterLeft, getSize().height - paddingTop - paddingBottom);
	}

	@Override
	public void computePlacement() {
		textLabel.setLocation(paddingLeft + highlighterLeft,paddingTop);
	}
	
	public void setSelected(boolean value) {
		if(this.isSelected != value) {
			this.isSelected = value;
			if(isSelected) {
				textLabel.setFont(new Font("Helvetica Neue",Font.BOLD, 16));
				textLabel.setForeground(DefaultTheme.getColor("OptionSelectedColor"));
			} else {
				textLabel.setFont(font);
				textLabel.setForeground(DefaultTheme.getColor("OptionForeground"));
			}
			computeSize();
			computePlacement();
			//this.getParent().revalidate();
			this.repaint();
			//MainFrame.getInstance().repaint();
		}
	}
	
	public void setHighlight(boolean value) {
		if(this.isHighlighted != value) {
			this.isHighlighted = value;
			repaint();
		}
	}
	
	public void addListener() {
		OptionItem that = this;
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(that.getParent() instanceof LeftPanel) {
					LeftPanel lp = (LeftPanel)that.getParent();
					OptionItem oi = lp.getSelectedItem();
					if (oi != null){
						LOGGER.info(oi.text + " -> " + that.text);
						if(oi != that) {
							oi.setSelected(false);
							lp.setSelectedItem(that);
							if(callback != null) {
								callback.callback();
							}
						}
					} else {
						LOGGER.info("null" + " -> " + that.text);
						lp.setSelectedItem(that);
					}
					that.setSelected(true);
				}
				LOGGER.info("Clicked");
			}

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
	
	public void paint(Graphics g) {
		super.paint(g);
		if(isHighlighted) {
			g.setColor(DefaultTheme.getColor("OptionHighlightColor"));
			g.fillRect(0, 0, getSize().width, getSize().height);
		}
		if(isSelected) {
			g.setColor(DefaultTheme.getColor("OptionSelectedColor"));
			g.fillRect(0, 0, highlighterLeft, getSize().height);
		}
		if(isSeparator) {
			g.setColor(DefaultTheme.getColor("OptionSeparatorBackground"));
			g.fillRect(highlighterLeft, 0, getSize().width - highlighterLeft, getSize().height);
			g.setColor(DefaultTheme.getColor("OptionSeparatorBorder"));
			g.fillRect(highlighterLeft, getSize().height - 2, getSize().width - highlighterLeft, getSize().height);
		}
		super.paintChildren(g);
	}
	public boolean isSeparator() {
		return isSeparator;
	}
	
	public void clickAction() {
		if(callback != null) {
			callback.callback();
		}
	}
	public static interface Callback {
		public void callback();
	}
}
