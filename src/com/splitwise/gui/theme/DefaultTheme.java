package com.splitwise.gui.theme;

import java.awt.Color;
import java.util.HashMap;

public class DefaultTheme {
	static Color colorPalette[] = {
			fromHex("#5bc5a7"),
			fromHex("#eeeeee"),
			fromHex("#48be9d"),
			fromHex("#999999"),
			fromHex("#cccccc"),
			fromHex("#f6f6f6")
	};
	static HashMap<String, Color> colorMap = new HashMap<String, Color>();
	
	static {
		// Main Frame
		colorMap.put("mainFrameBackground",Color.WHITE);
		// Header Panel
		colorMap.put("headerPanelBackground",colorPalette[0]);
		colorMap.put("headerPanelForeground",Color.WHITE);
		colorMap.put("headerPanelBorderColor",colorPalette[2]);
		
		
		// Main Content Panel
		colorMap.put("mainContentPanelBackground", Color.WHITE);
		
		// Left Panel Option
		colorMap.put("OptionSelectedColor", colorPalette[0]);
		colorMap.put("OptionHighlightColor", colorPalette[1]);
		colorMap.put("OptionForeground",colorPalette[3]);
		colorMap.put("OptionSeparatorForeground", colorPalette[4]);
		colorMap.put("OptionSeparatorBackground", colorPalette[5]);
		colorMap.put("OptionSeparatorBorder", colorPalette[1]);
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
