package com.splitwise.gui;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JPanel;

import com.splitwise.SplitwiseGUI;
import com.splitwise.gui.custom.CJPanel;
import com.splitwise.gui.custom.OptionItem;

public class LeftPanel extends CJPanel{

	private int width = 200;
	private int optionHeight = 24;
	private List<OptionItem> links;
	private List<OptionItem> groups;
	private List<OptionItem> friends;
	private OptionItem groupsHeader;
	private OptionItem friendsHeader;
	private OptionItem selectedItem = null;
	
	
	private int paddingTop = 10;
	private int paddingRight = 20;
	
	
	LeftPanel() {
		this.init();
		
		links.get(0).setSelected(true);
		selectedItem = links.get(0);
		
	}
	
	@Override
	public void initComponents() {
		// TODO Auto-generated method stub
		links = new ArrayList<OptionItem>();
		
		links.add(new OptionItem("Dashboard",() -> SplitwiseGUI.getInstance().showDashboard()));
		links.add(new OptionItem("Recent activity", () -> SplitwiseGUI.getInstance().showRecentActivity()));
		links.add(new OptionItem("All expenses", () -> SplitwiseGUI.getInstance().showAllExpenses()));
		
		groupsHeader = new OptionItem("Groups", OptionItem.HEADER);
		
		groups = new ArrayList<OptionItem>();
		
		friendsHeader = new OptionItem("Friends", OptionItem.HEADER);
		
		friends = new ArrayList<OptionItem>();
		
		packComponents();
	}
	
	public void packComponents() {
		this.removeAll();
		
		for(OptionItem oi : links) {
			add(oi);
		}
		
		add(groupsHeader);
		
		for(OptionItem oi : groups) {
			add(oi);
		}
		
		if(groups.size() == 0) {
			add(new OptionItem("You do not have any groups yet",OptionItem.MESSAGE));
		}
		
		add(friendsHeader);
		
		for(OptionItem oi : friends) {
			add(oi);
		}
		
		if(friends.size() == 0) {
			add(new OptionItem("You do not have any friends yet",OptionItem.MESSAGE));
		}
	}

	@Override
	public void configureComponents() {
		setSize(width,getSize().height);
		setLayout(null);
		setOpaque(false);
	}

	@Override
	public void computeSize() {
		for(Component oi : this.getComponents()) {
			((OptionItem)oi).setSize(getSize().width - paddingRight,oi.getHeight());
		}
	}

	@Override
	public void computePlacement() {
		int x = 0;
		int y = paddingTop;
		for(Component c : this.getComponents()) {
			c.setLocation(x, y);
			if(c instanceof OptionItem) {
				if(((OptionItem)c).isSeparator()) {
					y = y + paddingTop;
					c.setLocation(x, y);
				}
			}
			y = y + c.getSize().height;
		}
	}

	public OptionItem getSelectedItem() {
		return this.selectedItem;
	}
	
	public void setSelectedItem(OptionItem oi) {
		if(oi != this.selectedItem) { // To avoid multiple click on same options
			this.selectedItem = oi;
		}
	}

}
