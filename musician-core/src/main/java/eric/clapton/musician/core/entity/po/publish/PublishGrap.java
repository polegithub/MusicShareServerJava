package eric.clapton.musician.core.entity.po.publish;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import eric.clapton.infrastructure.entity.po.BaseEntity;

@Entity
@Table(name = "ms_publish_grap")
public class PublishGrap extends BaseEntity {
	private static final long serialVersionUID = -8173121387383620490L;

	private Long orderid;
	private Long timeid;
	private Long userid;
	private String remark;
	private OrderType type;
	private Date time;
	private GrabState state;

	@Override
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return super.getId();
	}

	@Column(name = "orderid")
	public Long getOrderid() {
		return orderid;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}

	@Column(name = "timeid")
	public Long getTimeid() {
		return timeid;
	}

	public void setTimeid(Long timeid) {
		this.timeid = timeid;
	}

	@Column(name = "userid")
	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "time")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public OrderType getType() {
		return type;
	}

	@Column(name = "type")
	@Enumerated
	public void setType(OrderType type) {
		this.type = type;
	}

	public GrabState getState() {
		return state;
	}

	public void setState(GrabState state) {
		this.state = state;
	}

	public enum GrabState {
		WAIT, SUCCESS, UNINTERESTED
	}

	public enum OrderType {
		TIME, FIELD
	}
}
