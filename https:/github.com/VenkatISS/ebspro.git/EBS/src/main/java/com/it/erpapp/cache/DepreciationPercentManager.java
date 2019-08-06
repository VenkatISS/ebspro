package com.it.erpapp.cache;

import java.util.HashMap;
import java.util.Map;

public class DepreciationPercentManager {

	private static DepreciationPercentManager _instance;

	private DepreciationPercentManager() {
	}

	private Map<String, String> dahyMap = new HashMap<String, String>();
	private Map<String, String> dafyMap = new HashMap<String, String>();

	public String getDepreciationPercentForHalfYear(String itemId) {
		return dahyMap.get(itemId);
	}

	public String getDepreciationPercentForFullYear(String itemId) {
		return dafyMap.get(itemId);
	}

	public static DepreciationPercentManager getInstance() {

		if (_instance == null) {
			synchronized (DepreciationPercentManager.class) {
				if (_instance == null) {
					// create instance
					_instance = new DepreciationPercentManager();
					// populate Maps
					_instance.populateMaps();
				}
			}
		}
		return _instance;
	}

	private void populateMaps() {

		// Half Year Percentages
		dahyMap.put("141", "0");
		dahyMap.put("142", "5");
		dahyMap.put("143", "0");
		dahyMap.put("144", "5");
		dahyMap.put("145", "15");
		dahyMap.put("146", "25");
		dahyMap.put("147", "0");

		// Full Year Percentages
		dafyMap.put("141", "0");
		dafyMap.put("142", "10");
		dafyMap.put("143", "0");
		dafyMap.put("144", "10");
		dafyMap.put("145", "30");
		dafyMap.put("146", "50");
		dafyMap.put("147", "0");

	}
}
