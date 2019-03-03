package com.splitwise.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.splitwise.gui.custom.CJPanel;
import com.splitwise.gui.custom.CustomButton;
import com.splitwise.gui.custom.CustomImage;
import com.splitwise.gui.custom.ExpenseItem;
import com.splitwise.gui.custom.FlexibleLabel;
import com.splitwise.gui.theme.DefaultTheme;

public class ExpensePanel extends CJPanel {
	private PageHeader pageHeader;
	private JPanel defaultPanel;
	private JPanel defaultTextPanel;
	private JLabel personLabel;
	private FlexibleLabel defaultPanelTitle;
	private FlexibleLabel defaultPanelSubText;
	private CustomButton addBillButton;
	private List<ExpenseItem> expenseList;
	
	//DefaultPanel
	private Insets defaultPanelPadding = new Insets(30, 45, 10, 15);
	private int defaultPanelHeight = 294;
	private Dimension personLabelDimension = new Dimension(150,274);
	ExpensePanel() {
		expenseList = new ArrayList<ExpenseItem>();
		init();
		//showDefaultPanel();
		addItem(new ExpenseItem("02","MAR","Sandesh", "$25.20", "", "Lyft for De Anza", "TL 513"));
		addItem(new ExpenseItem("02","MAR","You", "$25.20", "$25.20", "Bananas", "TL 513"));
		addItem(new ExpenseItem("02","MAR","Perry", "$25.20", "$15.20", "Vegetable Oil", "TL 513"));
		addItem(new ExpenseItem("02","MAR","Abhishek", "$25.20", "$15.20", "Vegetable Oil", ""));
	}
	
	@Override
	public void initComponents() {
		pageHeader = new PageHeader("All expenses");
		defaultPanel = new JPanel();
		
		initDefaultPanel();
		
		addBillButton = new CustomButton("Add a Bill");
		
		pageHeader.add(addBillButton);
		add(defaultPanel);
		add(pageHeader);
	}
	
	public void initDefaultPanel() {
		defaultPanel.setLayout(null);
		defaultPanel.setOpaque(false);
		defaultPanel.setVisible(false);
		
		defaultTextPanel = new JPanel();
		defaultTextPanel.setLayout(null);
		defaultTextPanel.setOpaque(false);
		
		personLabel = new JLabel((new CustomImage("assets/EmptyTable.png")).setSize(personLabelDimension).getImageIcon());
		
		defaultPanelTitle = new FlexibleLabel("You have not added any expenses yet");
		defaultPanelSubText = new FlexibleLabel("To add a new expense, click the orange “Add a bill” button.");
		
		defaultPanelTitle.setHorizontalAlignment(SwingConstants.LEFT);
		defaultPanelSubText.setHorizontalAlignment(SwingConstants.LEFT);
		
		defaultPanelTitle.setVerticalAlignment(SwingConstants.CENTER);
		defaultPanelSubText.setVerticalAlignment(SwingConstants.CENTER);
		
		defaultPanelTitle.setFont(new Font("Helvetica Neue",Font.BOLD,28));
		defaultPanelSubText.setFont(new Font("Helvetica Neue",Font.PLAIN,18));
		
		defaultPanelTitle.setForeground(DefaultTheme.getColor("PrimaryForeground"));
		defaultPanelSubText.setForeground(DefaultTheme.getColor("SecondaryForeground"));
		
		defaultPanelTitle.setSize(defaultPanelTitle.getPreferredSize());
		defaultPanelSubText.setSize(defaultPanelSubText.getPreferredSize());
		
		defaultTextPanel.add(defaultPanelTitle);
		defaultTextPanel.add(defaultPanelSubText);
		
		defaultPanel.add(personLabel);
		defaultPanel.add(defaultTextPanel);
	}
	
	public void hideAll() {
		defaultPanel.setVisible(false);
	}
	public void showDefaultPanel() {
		hideAll();
		defaultPanel.setVisible(true);
	}
	
	public void addItem(ExpenseItem ei) {
		expenseList.add(ei);
		add(ei);
		this.repaint();
	}
	
	public void setHeader(String text) {
		pageHeader.setHeader(text);
		this.repaint();
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
		personLabel.setSize(personLabelDimension);
		defaultTextPanel.setSize(defaultPanel.getSize().width - personLabel.getWidth() - 2*this.defaultPanelPadding.left - this.defaultPanelPadding.right,
				defaultPanel.getSize().height - this.defaultPanelPadding.top - this.defaultPanelPadding.bottom);
		
		defaultPanelTitle.setSize(defaultTextPanel.getSize().width, defaultPanelTitle.getPreferredSize().height);
		defaultPanelSubText.setSize(defaultTextPanel.getSize().width,defaultPanelSubText.getPreferredSize().height);
		
		for(ExpenseItem ei : expenseList) {
			ei.setSize(getSize().width, ei.getPreferredHeight());
		}
	}

	@Override
	public void computePlacement() {
		pageHeader.setLocation(0,0);
		addBillButton.setLocation(pageHeader.getSize().width - addBillButton.getSize().width - pageHeader.getPaddingRight(),
				(pageHeader.getSize().height - addBillButton.getHeight())/2);
		defaultPanel.setLocation(0,pageHeader.getSize().height);
		personLabel.setLocation(defaultPanelPadding.left, defaultPanelPadding.top);
		defaultTextPanel.setLocation(2*defaultPanelPadding.left + personLabel.getSize().width
				, 2*defaultPanelPadding.top);
		defaultPanelTitle.setLocation(0,0);
		defaultPanelSubText.setLocation(0
				, defaultPanelTitle.getLocation().y + defaultPanelTitle.getSize().height + 20);
		
		int relative_y = pageHeader.getSize().height;
		for(ExpenseItem ei : expenseList) {
			ei.setLocation(0, relative_y);
			relative_y += ei.getHeight();
		}
	}

}
