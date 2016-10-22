package eric.clapton.musician.util;

import java.util.HashMap;
import java.util.Map;

public class CityUtil {
	private static Map<String, String> map = new HashMap<String, String>();

	static {
		map.put("021", "上海市");
		
		
		map.put("0310", "邯郸市");
		map.put("0311", "石家庄");
		map.put("0312", "保定市");
		map.put("0313", "张家口");
		map.put("0314", "承德市");
		map.put("0315", "唐山市");
		map.put("0316", "廊坊市");
		map.put("0317", "沧州市");
		map.put("0318", "衡水市");
		map.put("0319", "邢台市");
		map.put("0335", "秦皇岛");
	}

	public static String getCity(String code) {
		return map.get(code);
	}

}
