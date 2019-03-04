package com.splitwise.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import com.splitwise.SplitwiseCore;
import com.splitwise.SplitwiseGUI;
import com.splitwise.core.Expense;
import com.splitwise.core.Group;
import com.splitwise.core.People;
import com.splitwise.gui.custom.CJPanel;
import com.splitwise.gui.custom.CustomButton;
import com.splitwise.gui.custom.CustomImage;
import com.splitwise.gui.custom.CustomScrollBarUI;
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
	private JScrollPane scrollPane;
	private JPanel listPanel;
	
	//DefaultPanel
	private Insets defaultPanelPadding = new Insets(30, 45, 10, 15);
	private int defaultPanelHeight = 294;
	private Dimension personLabelDimension = new Dimension(150,274);
	private long friendId;
	private long groupId;
	
	ExpensePanel() {
		expenseList = new ArrayList<ExpenseItem>();
		init();
		showExpenseList();
	}
	
	@Override
	public void initComponents() {
		pageHeader = new PageHeader("All expenses");
		defaultPanel = new JPanel();
		
		initDefaultPanel();
		initScrollPane();
		
		addBillButton = new CustomButton("Add a Bill");
		addBillButton.addCallback(()-> showAddBill());
		
		pageHeader.add(addBillButton);
		add(defaultPanel);
		add(scrollPane);
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
	
	public void initScrollPane() {
		listPanel = new JPanel();
		listPanel.setLayout(null);
		listPanel.setOpaque(false);
		
		scrollPane = new JScrollPane(listPanel);
		
		// Scroll Pane configuration
		scrollPane.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
				);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		scrollPane.setBorder(null);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setOpaque(false);

		scrollPane.getVerticalScrollBar().setSize(
				10,
				scrollPane.getVerticalScrollBar().getSize().height
				);
		scrollPane.getVerticalScrollBar().setOpaque(false);
		scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		scrollPane.getVerticalScrollBar().setPreferredSize(
				new Dimension(5, 0)
				);
		scrollPane.setVisible(false);
	}
	
	public void hideAll() {
		defaultPanel.setVisible(false);
		scrollPane.setVisible(false);
	}
	public void showDefaultPanel() {
		hideAll();
		defaultPanel.setVisible(true);
	}
	
	public void showExpenseList() {
		hideAll();
		if(SplitwiseCore.getInstance().getExpenses().size() == 0) {
			showDefaultPanel();
		} else {
			scrollPane.setVisible(true);
			SplitwiseCore core = SplitwiseCore.getInstance();
			expenseList.clear();
			listPanel.removeAll();
			for(Expense expense : core.getExpenses()) {
				String _date = ((expense.getUpdatedAt().getDate()>9)?"":"0") + expense.getUpdatedAt().getDate();
				String _month = Month.of(expense.getUpdatedAt().getMonth() + 1).getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
				People createdBy = core.getCurrentUser().getFriend(expense.getCreatedById());
				String name = (createdBy.getId() == core.getCurrentUser().getId())? "You":createdBy.getFirstName();
				Group group = expense.getGroupId() == 0?null:core.getCurrentUser().getGroup(expense.getGroupId());
				
				float your_share = expense.getOwedShare(core.getCurrentUser().getId());
				
				addItem(new ExpenseItem(
							_date,
							_month,
							name,
							String.valueOf(expense.getCost()), 
							your_share > 0?String.valueOf(your_share):"",
							expense.getDescription(),
							(group!=null)?group.getName():""));
			}
		}
		this.repaint();
		LOGGER.info("Scroll Pane" + scrollPane.getBounds().toString());
		LOGGER.info("List Pane" + listPanel.getBounds());
	}
	public void addItem(ExpenseItem ei) {
		expenseList.add(ei);
		listPanel.add(ei);
		computeSize();
		computePlacement();
	}
	
	public void setHeader(String text) {
		pageHeader.setHeader(text);
		this.repaint();
	}
	
	private void showAddBill() {
		LOGGER.info("Add Bill Button on Dashboard Clicked");
		SplitwiseGUI.getInstance().showAddBill();
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
		
		int height = 0;
		for(ExpenseItem ei : expenseList) {
			ei.setSize(getSize().width, ei.getPreferredHeight());
			height +=  ei.getPreferredHeight();
		}
		
		listPanel.setSize(getSize().width,height);
		listPanel.setPreferredSize(listPanel.getSize());
		scrollPane.setSize(getSize().width, getSize().height - pageHeader.getSize().height);
		scrollPane.setPreferredSize(scrollPane.getSize());
		scrollPane.setSize(556,834);
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
		
		int relative_y = 0;
		for(ExpenseItem ei : expenseList) {
			ei.setLocation(0, relative_y);
			relative_y += ei.getHeight();
		}
		listPanel.setLocation(0, 0);
		scrollPane.setLocation(0, pageHeader.getSize().height);
	}

	public void setFriendId(long friendId) {
		this.friendId = friendId;
		pageHeader.setHeader(SplitwiseCore.getInstance().getCurrentUser().getFriend(friendId).getName());
		pageHeader.computeSize();
		pageHeader.computePlacement();
	}
	
	public void setGroupId(long groupId) {
		this.groupId = groupId;
		pageHeader.setHeader(SplitwiseCore.getInstance().getCurrentUser().getGroup(groupId).getName());
		pageHeader.computeSize();
		pageHeader.computePlacement();
	}

	public void setDefaultHeader() {
		this.groupId = 0;
		this.friendId = 0;
		pageHeader.setHeader("All Expenses");
		pageHeader.computeSize();
		pageHeader.computePlacement();
	}

}
