package com.splitwise.gui.custom;

import javax.swing.JTextArea;

public class FlexibleLabel extends JTextArea {
	private int rows = 1;
	private int cols = 10;
	
	public FlexibleLabel(int rows, int cols) {
		super(rows,cols);
		this.rows = rows;
		this.cols = cols;
		init();
	}
	
	public FlexibleLabel(String text) {
		super();
		setRows(rows);
		setColumns(cols);
		setText(text);
		init();
	}
	private void init() {
		configureComponent();
	}
	
	private void configureComponent() {
		setLineWrap(true);
		setWrapStyleWord(true);
		setOpaque(false);
		setEditable(false);
		setSize(getPreferredSize());
	}
	
	public void setHorizontalAlignment(int alignment) {
		this.setAlignmentX(alignment);
	}
	public void setVerticalAlignment(int alignment) {
		this.setAlignmentY(alignment);
	}
}
