package eric.clapton.infrastructure.entity.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import eric.clapton.infrastructure.util.callback.Func1;

/**
 * 提供方法将创建 {@link HtmlSelectOption} 的列表。
 * 
 * @author xuw
 *
 */
public class HtmlSelectOptions {
	public static interface HtmlSelectOptionBuilder<T> {
		HtmlSelectOption build(T value);
	}

	@SafeVarargs
	public static <T> HtmlSelectOption[] from(Func1<T, HtmlSelectOption> builder, T... items) {
		int length = items.length;
		HtmlSelectOption[] options = new HtmlSelectOption[length];
		for (int i = 0; i < length; i++) {
			options[i] = builder.invoke(items[i]);
		}
		return options;
	}

	public static <T> List<HtmlSelectOption> from(Func1<T, HtmlSelectOption> builder, Iterable<T> items) {
		List<HtmlSelectOption> options;
		if (items instanceof Collection<?>) {
			options = new ArrayList<HtmlSelectOption>(((Collection<?>) items).size());
		} else {
			options = new ArrayList<HtmlSelectOption>();
		}
		for (T item : items) {
			options.add(builder.invoke(item));
		}

		return options;
	}

	public static <T> List<HtmlSelectOption> from(Func1<T, HtmlSelectOption> builder, Iterator<T> iterator) {
		List<HtmlSelectOption> options = new ArrayList<HtmlSelectOption>();
		while (iterator.hasNext()) {
			options.add(builder.invoke(iterator.next()));
		}
		return options;
	}
}
