package com.splitwise.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JLayeredPane;

import com.splitwise.SplitwiseCore;
import com.splitwise.core.Group;
import com.splitwise.core.People;
import com.splitwise.gui.custom.CJPanel;
import com.splitwise.gui.custom.CustomButton;
import com.splitwise.gui.custom.FlexibleLabel;
import com.splitwise.gui.custom.OptionItem;
import com.splitwise.gui.theme.DefaultTheme;

public class AddGroupMemberModel extends CJPanel {

	private int preferredHeight = 100;
	private int preferredWidth = 350;
	
	private int maxBodyHeight = 150; //Use to introduce scrollPane
	
	private int headerPanelHeight = 39;
	private int paddingLeft = 10;
	private int paddingTop = 20;
	private int paddingRight = 10;
	
	private JPanel headerPanel;
	private JLabel headerText;
	
	//private JPanel inputFieldPanel;
	//private FlexibleLabel inputArea;
	
	// For Body
	private JLayeredPane body;
	private JScrollPane scrollPane;
	private List<OptionItem> friendList;
	
	//private FlexibleLabel description;
	
	private JPanel buttonPanel;
	private CustomButton cancelButton;
	private CustomButton saveButton;
	
	private Callback saveCallback;
	
	public AddGroupMemberModel() {
		init();
	}
	
	@Override
	public void initComponents() {
		headerPanel = new JPanel();
		headerPanel.setLayout(null);
		headerPanel.setOpaque(false);
		//headerPanel.setBackground(DefaultTheme.getColor("ModelHeaderBackground"));
		
		headerText = new JLabel("Add Group Member");
		
		headerPanel.add(headerText);
		
		body = new JLayeredPane();
		body.setLayout(null);
		body.setOpaque(false);
		
		addFriendOptions();
		
		scrollPane = new JScrollPane(body);
		scrollPane.setBackground(Color.BLUE);
		//TODO Initialize Scroll Pane
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(null);
		buttonPanel.setOpaque(false);
		
		saveButton = new CustomButton("Save");
		saveButton.setTheme(CustomButton.GREENTHEME);

		saveButton.addCallback(()->saveAction());
		cancelButton = new CustomButton("Cancel");
		cancelButton.addCallback(()-> MainFrame.getInstance().hideBackdrop());
		cancelButton.setTheme(CustomButton.GREYTHEME);

		
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		
		
		add(buttonPanel, JLayeredPane.DEFAULT_LAYER);
		add(scrollPane);
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
	
	private void addFriendOptions() {
		List<People> friends = SplitwiseCore.getInstance().getFriends();
		friendList = new ArrayList<OptionItem>();
		for(People friend : friends) {
			OptionItem oi = new OptionItem(friend.getName());
			oi.setFriendId(friend.getId());
			friendList.add(oi);
			body.add(oi);
		}
	}

	@Override
	public void computeSize() {
		headerPanel.setSize(getSize().width, headerPanelHeight);
		headerText.setSize(headerText.getPreferredSize());
		
		// Input
		
		// Body Width & Height
		//description.setSize(250, 31);
		buttonPanel.setSize(getSize().width, 55);
		int bodyHeight = 0;
		for(OptionItem oi : friendList) {
			((OptionItem)oi).setSize(getSize().width - paddingRight,oi.getHeight());
			bodyHeight += oi.getHeight();
		}
		
		body.setSize(getSize().width, bodyHeight);
		body.setPreferredSize(new Dimension(getSize().width, bodyHeight));
		scrollPane.setSize(getSize().width, Math.min(maxBodyHeight, bodyHeight));
	}

	@Override
	public void computePlacement() {
		headerPanel.setLocation(0,0);
		headerText.setLocation(paddingLeft, (headerPanel.getSize().height - headerText.getSize().height)/2);
		
		//body.setLocation(0, );
		
		scrollPane.setLocation(0, headerPanel.getSize().height + paddingTop);
		int paddingBetween = 10;
		int relative_y = scrollPane.getY() + scrollPane.getSize().height + paddingBetween;
		buttonPanel.setLocation(0,relative_y);
		
		saveButton.setLocation(buttonPanel.getSize().width - paddingLeft - saveButton.getWidth(), 0);
		cancelButton.setLocation(saveButton.getX() - paddingLeft - cancelButton.getWidth(), 0);
		preferredHeight = scrollPane.getSize().height + headerPanel.getSize().height + paddingBetween + buttonPanel.getSize().height + paddingBetween;
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
		
		
		int x1 = 0;
		int x2 = getSize().width;
		int y = buttonPanel.getY();
		g.drawLine(x1, y, x2, y);
		
		g.translate(-body.getX(), -body.getY());
		
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

	private String calcDate(long millisecs) {
        SimpleDateFormat date_format = new SimpleDateFormat("MMM dd,yyyy");
        Date resultdate = new Date(millisecs);
        return date_format.format(resultdate);
    }
	
	public void saveAction() {
		/*String name = description.getText();
		LOGGER.info("Name :" + name);
		if(this.saveCallback != null) {
			HashMap<String,String> args = new HashMap<String,String>();
			args.put("name",name);
			saveCallback.callback(args);
		}*/
		MainFrame.getInstance().hideBackdrop();
	}
	public void setSaveCallback(Callback callback) {
		this.saveCallback = callback;
	}
	public static interface Callback {
		public void callback(Map<String,String> arguments);
	}
}
