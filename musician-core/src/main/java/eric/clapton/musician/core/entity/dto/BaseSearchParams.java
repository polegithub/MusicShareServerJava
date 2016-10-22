package eric.clapton.musician.core.entity.dto;

import java.io.Serializable;

public class BaseSearchParams implements Serializable {
	private boolean pageCountDisabled;

	public boolean isPageCountDisabled() {
		return pageCountDisabled;
	}

	public void setPageCountDisabled(boolean pageCountDisabled) {
		this.pageCountDisabled = pageCountDisabled;
	}
}
