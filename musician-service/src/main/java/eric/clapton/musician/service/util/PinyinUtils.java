package eric.clapton.musician.service.util;

import static eric.clapton.infrastructure.util.StringUtils.isNullOrEmpty;

import java.util.Locale;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 拼音实用工具。
 * 
 * @author cheer
 *
 */
public class PinyinUtils {
    private PinyinUtils() {

    }

    private static final HanyuPinyinOutputFormat OUTPUT_FORMAT;
    /**
     * 默认的中文拼音音节之间的分隔符：{@value} 。
     */
    public static final String DEFAULT_SILLABLE_SEPARATOR = "-";

    private static final Logger logger = LoggerFactory
            .getLogger(PinyinUtils.class);

    static {
        final HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        OUTPUT_FORMAT = format;
    }

    public static final String toPinyin(String s) {
        return toPinyin(s, null);
    }

    /**
     * 输出字符串的拼音。
     * 
     * @param s
     *            要输出拼音的字符串。
     * @param sillableSeparator
     *            中文拼音音节之间的分隔符。如果为 <code>null</code>，则为
     *            {@link #DEFAULT_SILLABLE_SEPARATOR}。
     * @return
     */
    public static String toPinyin(String s, String sillableSeparator) {
        if (isNullOrEmpty(s)) {
            return s;
        }

        if (sillableSeparator == null) {
            sillableSeparator = DEFAULT_SILLABLE_SEPARATOR;
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0, l = s.length(); i < l; i++) {
            char c = s.charAt(i);

            String pinyin = null;

            try {
                String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c,
                        OUTPUT_FORMAT);
                if (pinyinArray != null && pinyinArray.length > 0) {
                    pinyin = pinyinArray[0];
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                if (logger.isWarnEnabled()) {
                    logger.warn(String.format(Locale.CHINA,
                            "无法检索字符 '%s' (0x%x) 的汉语拼音。", c, (int) c), e);
                }
            }

            if (isNullOrEmpty(pinyin)) {
                pinyin = Character.toString(c);
            }

            if (result.length() > 0) {
                result.append(sillableSeparator);
            }
            result.append(pinyin);
        }

        return result.toString();
    }
}
