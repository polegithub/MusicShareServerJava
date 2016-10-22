package eric.clapton.infrastructure.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class StringUtils {
	/**
	 * 获取平台特定的换行符。
	 */
	public static final String NEW_LINE = System.getProperty("line.separator");

	/**
	 * 判断字符串是否为 null 或者是空字符串。
	 * 
	 * @param s
	 *            要判断是否为 null 或空的字符串。
	 * @return
	 */
	public static final boolean isNullOrEmpty(String s) {
		return s == null || s.isEmpty();
	}

	public static final int looseCompare(String left, String right) {
		return makeSafe(left).compareTo(makeSafe(right));
	}

	public static final int strictCompare(String left, String right) {
		return strictCompare(left, right, false);
	}

	public static final int strictCompare(String left, String right, boolean ignoreCase) {
		if (left == null) {
			return right == null ? 0 : -1;
		}
		return right == null ? -1 : ignoreCase ? left.compareToIgnoreCase(right) : left.compareTo(right);
	}

	public static final boolean startsWith(String s, String prefix) {
		return startsWith(s, prefix, 0, false);
	}

	public static final boolean startsWith(String s, String prefix, boolean ignoreCase) {
		return startsWith(s, prefix, 0, ignoreCase);
	}

	public static final boolean startsWith(String s, String prefix, int offset) {
		return startsWith(s, prefix, offset, false);
	}

	public static final boolean startsWith(String s, String prefix, int offset, boolean ignoreCase) {
		if (s == null) {
			return prefix == null;
		}
		if (prefix == null) {
			return false;
		}

		return ignoreCase ? upper(s).startsWith(upper(prefix), offset) : s.startsWith(prefix, offset);
	}

	public static final boolean contains(String s, CharSequence search) {
		return contains(s, search, false);
	}

	public static final boolean contains(String s, CharSequence search, boolean ignoreCase) {
		if (s == null) {
			return search == null;
		}
		if (search == null) {
			return false;
		}
		if (search.length() == 0) {
			return true;
		}
		return ignoreCase ? upper(s).contains(upper(search.toString())) : s.contains(search);
	}

	public static final String upper(String input, boolean trim, int maxLength) {
		return makeSafe(input, trim, maxLength).toUpperCase(Locale.ROOT);
	}

	public static final String upper(String input, int maxLength) {
		return upper(input, false, maxLength);
	}

	/**
	 * 将输入的字符串转换成大写形式，而与区域性无关。同时可以指定是否移除字符串的前后导空格。
	 * 
	 * @param input
	 *            要转换为大写的字符串。可以为 <code>null</code>。
	 * @param trim
	 *            如果为 <code>true</code>，则移除字符串的前后导空格，否则不做任何处理。
	 * @return 如果 <code>input</code> 为 <code>null</code>
	 *         ，则为空字符串，否则为按照默认规则转换为大写的字符串表示。
	 */
	public static final String upper(String input, boolean trim) {
		return upper(input, trim, 0);
	}

	public static final String upper(String input) {
		return upper(input, false);
	}

	public static final String lower(String input) {
		return lower(input, false);
	}

	public static final String lower(String input, boolean trim, int maxLength) {
		return makeSafe(input, trim, maxLength).toLowerCase(Locale.ROOT);
	}

	public static final String lower(String input, int maxLength) {
		return lower(input, false, maxLength);
	}

	public static final String lower(String input, boolean trim) {
		return lower(input, trim, 0);
	}

	public static final String makeSafe(String input) {
		return makeSafe(input, false, 0);
	}

	public static final String makeSafe(String input, int maxLength) {
		return makeSafe(input, false, maxLength);
	}

	public static final String makeSafe(String input, boolean trim) {
		return makeSafe(input, trim, 0);
	}

	public static String makeSafe(String input, boolean trim, int maxLength) {
		if (input == null) {
			return "";
		}
		if (trim) {
			input = input.trim();
		}
		if (maxLength < 1) {
			return input;
		}
		if (input.length() > maxLength) {
			input = input.substring(0, maxLength);
		}
		return input;
	}

	public static String repeat(char c, int times) {
		if (times == 0) {
			return "";
		}
		if (times == 1) {
			return String.valueOf(c);
		}

		char[] buffer = new char[times];
		for (int i = 0; i < times; i++) {
			buffer[i] = c;
		}
		return new String(buffer);
	}

	private static final Pattern TWO_OR_MORE_SPACES = Pattern.compile("[\\s]{2,}");
	private static final Pattern HTML_LINE_BREAKS = Pattern.compile("(<[b|B][r|R]/*>)+|(<[p|P](.|\\n)*?>)");
	private static final Pattern NON_BREAKING_SPACES = Pattern.compile("(\\s*&[n|N][b|B][s|S][p|P];\\s*)+");
	private static final Pattern ANY_HTML_TAGS = Pattern.compile("<(.|\\n)*?>");

	/**
	 * <p>
	 * 将输入字符串视为 HTML 进行清理。
	 * </p>
	 * <p>
	 * 此方法对输入字符串做如下清理：
	 * <ul>
	 * <li>去除字符串前后导空格。</li>
	 * <li>将字符串内两个或两个以上的空格替换为一个空格。</li>
	 * <li>将字符串内的 HTML 非换行空白 <code>&amp;nbsp;</code> 替换成空格。</li>
	 * <li>将字符串内的 HTML <code>&lt;br&gt;</code> 和 <code>&lt;p&gt;</code>
	 * 标签替换为平台特定的换行符。</li>
	 * <li>移除字符串内的其他 HTML 标签。</li>
	 * <li>如果 <code>maxLength</code> 大于 0，且字符串长度大于 <code>maxLength</code>，取前
	 * <code>maxLength</code> 个字符。</li>
	 * </ul>
	 * </p>
	 * 
	 * @param text
	 *            要清理的字符串，或者为 <code>null</code>。
	 * @param maxLength
	 *            返回的字符串最大长度。
	 * @return 清理后的字符串。一定不为 <code>null</code>。
	 */
	public static String cleanUp(String text, int maxLength) {
		text = makeSafe(text, true);
		if (text.isEmpty()) {
			return text;
		}

		text = TWO_OR_MORE_SPACES.matcher(text).replaceAll(" ");
		text = NON_BREAKING_SPACES.matcher(text).replaceAll(" ");
		text = HTML_LINE_BREAKS.matcher(text).replaceAll(NEW_LINE);
		text = ANY_HTML_TAGS.matcher(text).replaceAll("");

		return truncate(text, maxLength);
	}

	public static final String cleanUp(String text) {
		return cleanUp(text, 0);
	}

	public static final String truncate(String s, int maxLength) {
		return truncate(s, maxLength, null);
	}

	public static final String truncateAndAddEllipsisDots(String s, int maxLength) {
		return truncate(s, maxLength, "...");
	}

	public static final String truncate(String s, int maxLength, String ellipsisChars) {
		if (s == null) {
			return null;
		}

		if (maxLength > 0 && s.length() > maxLength) {
			s = s.substring(0, maxLength);
			return isNullOrEmpty(ellipsisChars) ? s : s + ellipsisChars;
		}
		return s;
	}

	public static List<Map<String, Object>> list2ArrayMap(List list, String... key) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			Object val = list.get(i);
			if (val instanceof Object[]) {
				Object[] v = (Object[]) val;
				for (int j = 0; j < key.length; j++) {
					map.put(key[j], v[j]);
				}
				result.add(map);
			}
		}

		return result;
	}

	public static Map<String, Object> array2Map(Object obj, String... key) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (obj instanceof Object[]) {
			Object[] v = (Object[]) obj;
			for (int j = 0; j < key.length; j++) {
				result.put(key[j], v[j]);
			}
		}

		return result;
	}

	public static void main(String[] args) {

		// List list = new ArrayList();
		// String[] data = { "人1", "名1", "钱1" };
		// list.add(data);
		// String[] data2 = { "人2", "名2", "钱2" };
		// list.add(data2);
		// String[] data3 = { "人3", "名3", "钱3" };
		// list.add(data3);
		// List<Map<String, Object>> map = list2ArrayMap(list, "people", "name",
		// "money");

		Object[] list = { "avd", 112, 125.3 };
		Map<String, Object> map = array2Map(list, "people", "name", "money");

		String res = new JsonMapper().toJson(map);

		System.out.println(res);
	}

}
