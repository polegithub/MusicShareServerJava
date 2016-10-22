package eric.clapton.musician.core.entity.po.publish;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import eric.clapton.infrastructure.entity.po.BaseEntity;

@Entity
@Table(name = "ms_publish_common")
public class OrderComment extends BaseEntity {
	private static final long serialVersionUID = -8173121387383620490L;

	private Long orderid;
	private Long timeid;
	private Long puserid;
	private Long peeuser;
	private Date time;
	private double score;
	private String remark;

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

	
	@Column(name = "puserid")
	public Long getPuserid() {
		return puserid;
	}

	public void setPuserid(Long puserid) {
		this.puserid = puserid;
	}
	@Column(name = "peeuser")
	public Long getPeeuser() {
		return peeuser;
	}

	public void setPeeuser(Long peeuser) {
		this.peeuser = peeuser;
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
	
	@Column(name = "score")
	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	
	
}
