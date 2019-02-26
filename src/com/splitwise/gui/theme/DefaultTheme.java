package com.splitwise.gui.theme;

import java.awt.Color;
import java.util.HashMap;

public class DefaultTheme {
	static Color colorPalette[] = {};
	static HashMap<String, Color> colorMap = new HashMap<String, Color>();
	
	static {
		// Header Panel
		colorMap.put("headerPanelBackground",null);
		colorMap.put("headerPanelForeground",null);
	}
	
	public static Color getColor(String name) {
		return colorMap.get(name);
	}
}
