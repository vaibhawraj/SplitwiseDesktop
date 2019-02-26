package com.splitwise.gui.custom;

public interface PanelInterface {
	
	default void init() {
		initComponents();
		configureComponents();
	}
	
	void initComponents();
	void configureComponents();
	void computeSize();
	void computePlacement();
	
}
