package eric.clapton.musician.core.entity.dto.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * “我的订单”列表项。
 * 
 * @author cheer
 *
 */
public class SellerOrderListItem implements Serializable {
	private static final long serialVersionUID = 1846397211784784721L;

	private Long id;
	private String title;
	private String timeStart;
	private String description;
	private String timeEnd;
	private String deadline;
	private List<String> musicType;
	private int peopleCount;
	private int signedUp;
	private int recruited;
	private BigDecimal price;
	private String created;
	private String stateText;
	private int state;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public List<String> getMusicType() {
		return musicType;
	}

	public void setMusicType(List<String> musicType) {
		this.musicType = musicType;
	}

	public int getPeopleCount() {
		return peopleCount;
	}

	public void setPeopleCount(int peopleCount) {
		this.peopleCount = peopleCount;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getStateText() {
		return stateText;
	}

	public void setStateText(String stateText) {
		this.stateText = stateText;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getSignedUp() {
		return signedUp;
	}

	public void setSignedUp(int signedUp) {
		this.signedUp = signedUp;
	}

	public int getRecruited() {
		return recruited;
	}

	public void setRecruited(int recruited) {
		this.recruited = recruited;
	}
}
