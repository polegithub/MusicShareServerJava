package eric.clapton.infrastructure.entity.vo;

import java.io.Serializable;

import com.google.common.escape.Escaper;
import com.google.common.html.HtmlEscapers;

import eric.clapton.infrastructure.util.StringUtils;

/**
 * 表示 HTML <code>SELECT</code> 元素中的一个选项。
 * 
 * @author xuw
 *
 */
public class HtmlSelectOption implements Serializable {
    private static final long serialVersionUID = 3826173580400669623L;

    private final String value;
    private final String text;

    private static final Escaper HTML_ESCAPER = HtmlEscapers.htmlEscaper();

    public HtmlSelectOption(String value, String text) {
        this(value, text, true);
    }

    public HtmlSelectOption(String value, String text, boolean htmlEscape) {
        this.value = value == null ? null : htmlEscape ? HTML_ESCAPER.escape(value) : value;
        this.text = StringUtils.isNullOrEmpty(text) ? "" : htmlEscape ? HTML_ESCAPER.escape(text)
                : text;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        if (value == null) {
            return "<option>" + text + "</option>";
        }
        return "<option value=\"" + value + "\">" + text + "</option>";
    }
}
