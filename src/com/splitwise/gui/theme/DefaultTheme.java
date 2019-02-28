package com.splitwise.gui.theme;

import java.awt.Color;
import java.util.HashMap;

public class DefaultTheme {
	static Color colorPalette[] = {
			fromHex("#5bc5a7"),
			null,
			fromHex("#48be9d")
	};
	static HashMap<String, Color> colorMap = new HashMap<String, Color>();
	
	static {
		// Header Panel
		colorMap.put("headerPanelBackground",colorPalette[0]);
		colorMap.put("headerPanelForeground",Color.WHITE);
		colorMap.put("headerPanelBorderColor",colorPalette[2]);
		
		// Main Content Panel
		colorMap.put("mainContentPanelBackground", Color.WHITE);
	}
	
	public static Color getColor(String name) {
		return colorMap.get(name);
	}
	
	static Color fromHex(String hex) {
		int r = Integer.parseInt(hex.substring(1, 3),16);
		int g = Integer.parseInt(hex.substring(3, 5),16);
		int b = Integer.parseInt(hex.substring(5, 7),16);
		return new Color(r,g,b);
	}
}
