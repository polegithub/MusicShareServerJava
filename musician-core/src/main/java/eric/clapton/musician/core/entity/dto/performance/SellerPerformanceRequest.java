package eric.clapton.musician.core.entity.dto.performance;

import eric.clapton.musician.core.entity.dto.BasePageRequest;

public class SellerPerformanceRequest extends BasePageRequest {
	private static final long serialVersionUID = 333858554522393254L;

	private String keyword;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
