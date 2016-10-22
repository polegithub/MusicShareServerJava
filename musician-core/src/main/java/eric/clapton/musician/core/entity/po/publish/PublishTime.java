package eric.clapton.musician.core.entity.po.publish;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import eric.clapton.infrastructure.entity.po.BaseEntity;
import eric.clapton.musician.core.entity.po.order.OrderState;

@Entity
@Table(name = "ms_publish_time")
public class PublishTime extends BaseEntity {
	private static final long serialVersionUID = -8173121387383620490L;

	private Long orderid;
	private double price;
	private Date starttime;
	private Date endtime;
	private Date deadline;
	private int need;
	private int recruited;
	private String remark;
	private OrderState state;


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

	@Column(name = "price")
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Column(name = "starttime")
	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	@Column(name = "endtime")
	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	@Column(name = "need")
	public int getNeed() {
		return need;
	}

	public void setNeed(int need) {
		this.need = need;
	}

	@Column(name = "recruited")
	public int getRecruited() {
		return recruited;
	}

	public void setRecruited(int recruited) {
		this.recruited = recruited;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "state")
	@Enumerated(EnumType.STRING)
	public OrderState getState() {
		return state;
	}

	public void setState(OrderState state) {
		this.state = state;
	}
	
	@Column(name = "deadline")
	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

}
