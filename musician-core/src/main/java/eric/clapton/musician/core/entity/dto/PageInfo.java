package eric.clapton.musician.core.entity.dto;

import java.io.Serializable;

public class PageInfo implements Serializable {
	private static final long serialVersionUID = -6854053447233153875L;

	private int pageIndex;
	private int pageSize;
	private long total;

	public PageInfo() {

	}

	public PageInfo(int pageIndex, int pageSize, long total) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.total = total;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
}
