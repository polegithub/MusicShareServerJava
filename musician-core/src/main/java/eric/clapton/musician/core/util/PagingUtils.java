package eric.clapton.musician.core.util;

import java.util.Collections;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class PagingUtils {
	protected PagingUtils() {

	}

	/**
	 * 返回一个空的页。
	 * 
	 * @param pageable
	 *            包含分页信息的对象。不能为 <code>null</code>。
	 * @return
	 */
	public static final <T> Page<T> emptyPage(Pageable pageable) {
		return new PageImpl<T>(Collections.<T> emptyList(), pageable, 0L);
	}

	public static boolean hasSort(Pageable pageable) {
		return pageable != null && pageable.getSort() != null && pageable.getSort().iterator().hasNext();
	}
}
