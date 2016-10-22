package eric.clapton.musician.core.entity.po.publish;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import eric.clapton.infrastructure.entity.po.BaseEntity;
import eric.clapton.musician.core.entity.po.account.Account;
import eric.clapton.musician.core.entity.po.account.AddressBookEntry;
import eric.clapton.musician.core.entity.po.order.OrderState;

@Entity
@Table(name = "ms_field_order")
public class FieldOrder extends BaseEntity {
	private static final long serialVersionUID = -6992193477925440646L;

	private String title;
	private String description;
	private AddressBookEntry address;
	private Account account;
	private Date starttime;
	private Date endtime;
	private Integer needcount;
	private Date deadline;
	private Date created;
	private OrderState state;

	private List<PublishStyle> styles;

	public FieldOrder() {

	}

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Long getId() {
		return super.getId();
	}

	@Column(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JoinColumn(name = "address_id", updatable = false)
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
	public AddressBookEntry getAddress() {
		return address;
	}

	public void setAddress(AddressBookEntry address) {
		this.address = address;
	}

	public Integer getNeedcount() {
		return needcount;
	}

	public void setNeedcount(Integer needcount) {
		this.needcount = needcount;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	@JoinColumn(name = "account_id", updatable = false)
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
	public Account getAccount() {
		return account;
	}

	@Transient
	public Long getAccountId() {
		Account account = getAccount();
		return account == null ? null : account.getId();
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@OneToMany(mappedBy = "orderid", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	public List<PublishStyle> getStyles() {
		return styles;
	}

	public void setStyles(List<PublishStyle> styles) {
		this.styles = styles;
	}

	@Column(updatable = false)
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Column(name = "state")
	@Enumerated(EnumType.STRING)
	public OrderState getState() {
		return state;
	}

	public void setState(OrderState state) {
		this.state = state;
	}

}
