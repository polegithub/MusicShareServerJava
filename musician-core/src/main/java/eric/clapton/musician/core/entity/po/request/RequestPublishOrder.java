package eric.clapton.musician.core.entity.po.request;

import java.util.List;

import eric.clapton.musician.core.entity.po.publish.PublishStyle;
import eric.clapton.musician.core.entity.po.publish.PublishTime;

public class RequestPublishOrder {

	private String title;
	private String description;
	private Long accountid;
	private String created;
	private String state;

	private Long addressid;

	private List<PublishTime> times;
	private List<PublishStyle> styles;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getAccountid() {
		return accountid;
	}

	public void setAccountid(Long accountid) {
		this.accountid = accountid;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getAddressid() {
		return addressid;
	}

	public void setAddressid(Long addressid) {
		this.addressid = addressid;
	}

	public List<PublishTime> getTimes() {
		return times;
	}

	public void setTimes(List<PublishTime> times) {
		this.times = times;
	}

	public List<PublishStyle> getStyles() {
		return styles;
	}

	public void setStyles(List<PublishStyle> styles) {
		this.styles = styles;
	}

}
