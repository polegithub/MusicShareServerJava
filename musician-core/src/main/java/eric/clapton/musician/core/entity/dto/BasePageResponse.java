package eric.clapton.musician.core.entity.dto;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 返回给 APP 的分页响应对象。它包含页信息（索引、分页大小、总记录数）以及当前页的列表内容。
 * 
 * @author cheer
 *
 */
public class BasePageResponse implements Serializable {
	private static final long serialVersionUID = 8775842754731761009L;

	private PageInfo page;
	private List<?> content;

	public BasePageResponse() {

	}

	public BasePageResponse(int pageIndex, int pageSize, long total) {
		this(pageIndex, pageSize, total, null);
	}

	public BasePageResponse(PageInfo page) {
		this(page, null);
	}

	public BasePageResponse(int pageIndex, int pageSize, long total, List<?> content) {
		this(new PageInfo(pageIndex, pageSize, total), content);
	}

	public BasePageResponse(PageInfo page, List<?> content) {
		this.page = page;
		this.content = content;
	}

	public PageInfo getPage() {
		return page;
	}

	public void setPage(PageInfo page) {
		this.page = page;
	}

	public List<?> getContent() {
		if (content == null) {
			content = Collections.emptyList();
		}
		return content;
	}

	public void setContent(List<?> content) {
		this.content = content;
	}
}
