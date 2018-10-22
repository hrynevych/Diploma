package ua.karazin.hrynevych.comparing.recurrent;

import java.util.HashMap;
import java.util.Map;

public class MapContainer {
	
	private static Map<String, String> map;
	
	public static void prepareMap() {
		if ((map == null) || (map.isEmpty())) {
			map = new HashMap<String, String>();
			map.put("smoking", "good");
			map.put("sport", "utterly disgusting");
		}
	}
	
	public static String getValue(String key) {
		prepareMap();
		return map.get(key);
	}
	
	public static void setPair(String key, String value) {
		prepareMap();
		map.put(key, value);
	}
}
