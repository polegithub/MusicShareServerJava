package eric.clapton.musician.core.entity.dto;

import java.io.Serializable;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class BasePageRequest implements Serializable {
	private static final long serialVersionUID = 2232382308741567013L;

	public static final int MAX_PAGE_SIZE = 500;

	private int pageIndex;
	private int pageSize = 20;
	private boolean noCount;

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		if (pageIndex < 0) {
			throw new IllegalArgumentException("Property 'pageIndex' cannot be less than zero.");
		}

		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize < 1) {
			throw new IllegalArgumentException("Property 'pageSize' cannot be less than 1.");
		}
		if (pageSize > MAX_PAGE_SIZE) {
			throw new IllegalArgumentException("Property 'pageSize' cannot be greater than " + MAX_PAGE_SIZE + ".");
		}

		this.pageSize = pageSize;
	}

	public boolean isNoCount() {
		return noCount;
	}

	public void setNoCount(boolean noCount) {
		this.noCount = noCount;
	}

	public Pageable getPageable() {
		return new PageRequest(pageIndex, pageSize);
	}
}
